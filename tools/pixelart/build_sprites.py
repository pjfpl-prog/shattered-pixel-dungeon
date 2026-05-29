#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
build_sprites.py — Converte arte conceitual 1024x1024 (tools/concepts/) em
spritesheets 16x16 horizontais no estilo Shattered Pixel Dungeon.

Pipeline por personagem:
  1. Carrega o PNG conceitual (RGB sobre fundo cinza escuro uniforme).
  2. Remove o fundo por flood-fill a partir das bordas (-> alfa 0).
  3. Crop na bounding box do personagem + pad pra quadrado.
  4. Downscale 1024 -> 16 com LANCZOS sobre canais PREMULTIPLICADOS
     (evita sangrar a cor do fundo nas bordas).
  5. Quantiza pra paleta limitada (~12 cores) -> visual chapado do SPD.
  6. Corta o alfa em hard-edge (sem anti-aliasing).
  7. Gera frames procedurais (idle/run/attack/die) a partir do frame base.
  8. Monta o spritesheet horizontal e salva em assets/sprites/narrative/.

Layout do sheet (16x16 por frame, 11 frames -> 176x16):
  idle:   0,1
  run:    2,3,4,5
  attack: 6,7
  die:    8,9,10

Uso:
  python tools/pixelart/build_sprites.py
"""

import os
import numpy as np
from PIL import Image, ImageDraw, ImageEnhance

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.abspath(os.path.join(HERE, os.pardir, os.pardir))
CONCEPTS = os.path.join(ROOT, "tools", "concepts")
OUT_DIR = os.path.join(ROOT, "core", "src", "main", "assets", "sprites", "narrative")
PREVIEW_DIR = os.path.join(HERE, "preview")

FRAME = 16                  # lado do frame em px
PALETTE_COLORS = 12         # cores na quantizacao
ALPHA_CUTOFF = 110          # limiar pro hard-edge do alfa
BG_FLOOD_THRESH = 48        # tolerancia do flood-fill de fundo

# Fonte: os retratos 128x128 (_portrait_large) ja vem bem compostos e centrados,
# entao 128->16 (8x) preserva a silhueta MUITO melhor que o concept 1024 (64x).
PORTRAITS = os.path.join(ROOT, "core", "src", "main", "assets", "narrative")

# chave de saida -> opcoes.
#   src:       arquivo fonte (em assets/narrative/)
#   keep_frac: ilhas com area >= keep_frac * (maior ilha) sao mantidas;
#              menores viram fundo (derruba papeis flutuantes, sombra solta, specks).
#   flood:     tolerancia do flood-fill de fundo (cor escura ~uniforme).
#   sat/bri:   realce de saturacao/brilho pre-downscale (1.0 = neutro)
#   eyes:      cor RGB pra forcar "olhos brilhantes" (captura os px mais claros
#              da fonte antes do quantize esmagar) ou None.
JOBS = {
    "oraculo": {
        "src": "oraculo_portrait_large.png",
        "keep_frac": 0.15, "flood": 35, "sat": 1.7, "bri": 1.35,
        "eyes": (230, 226, 255)},
    "grave_boss_rei_cinza": {
        "src": "boss-rei-cinza_portrait_large.png",
        "keep_frac": 0.02, "flood": 52, "sat": 1.15, "bri": 1.05,
        "eyes": None},
    "grave_boss_profeta_mudo": {
        "src": "boss-profeta-mudo_portrait_large.png",
        "keep_frac": 0.25, "flood": 52, "sat": 1.15, "bri": 1.05,
        "eyes": None},
}

SENTINEL = (255, 0, 255)   # cor usada pra marcar fundo durante o flood-fill


# ---------------------------------------------------------------------------
# Etapas de processamento
# ---------------------------------------------------------------------------

def remove_background(rgb, thresh):
    """Flood-fill a partir das bordas marcando o fundo, devolve mascara bool
    (True = fundo). So remove regiao conectada a borda -> partes escuras
    DENTRO do personagem sao preservadas."""
    w, h = rgb.size
    work = rgb.copy()
    seeds = [
        (0, 0), (w - 1, 0), (0, h - 1), (w - 1, h - 1),
        (w // 2, 0), (w // 2, h - 1), (0, h // 2), (w - 1, h // 2),
    ]
    for s in seeds:
        ImageDraw.floodfill(work, s, SENTINEL, thresh=thresh)
    arr = np.asarray(work)
    mask = np.all(arr == np.array(SENTINEL), axis=-1)
    return mask


def keep_large_components(fg, keep_frac, scale=4):
    """Rotula componentes conectados (4-conn) e mantem so os com
    area >= keep_frac * maior_area. Faz o labeling numa mascara subamostrada
    (max-pool por blocos scale x scale) por velocidade; depois reamplia.
    Devolve (mascara_fg_limpa, lista_de_areas_desc)."""
    from collections import deque
    H, W = fg.shape
    sh, sw = H // scale, W // scale
    # max-pool: bloco vira fg se qualquer px do bloco for fg (preserva conexao)
    small = fg[:sh * scale, :sw * scale].reshape(sh, scale, sw, scale).any(axis=(1, 3))

    labels = np.zeros((sh, sw), np.int32)
    cur = 0
    areas = {}
    for i in range(sh):
        row = small[i]
        for j in range(sw):
            if row[j] and labels[i, j] == 0:
                cur += 1
                dq = deque([(i, j)])
                labels[i, j] = cur
                cnt = 0
                while dq:
                    y, x = dq.popleft()
                    cnt += 1
                    if y > 0 and small[y - 1, x] and labels[y - 1, x] == 0:
                        labels[y - 1, x] = cur; dq.append((y - 1, x))
                    if y < sh - 1 and small[y + 1, x] and labels[y + 1, x] == 0:
                        labels[y + 1, x] = cur; dq.append((y + 1, x))
                    if x > 0 and small[y, x - 1] and labels[y, x - 1] == 0:
                        labels[y, x - 1] = cur; dq.append((y, x - 1))
                    if x < sw - 1 and small[y, x + 1] and labels[y, x + 1] == 0:
                        labels[y, x + 1] = cur; dq.append((y, x + 1))
                areas[cur] = cnt

    if not areas:
        return fg, []
    max_area = max(areas.values())
    cutoff = max_area * keep_frac
    keep = {v for v, a in areas.items() if a >= cutoff}
    small_keep = np.isin(labels, list(keep))

    # reamplia a mascara mantida pro tamanho original e cruza com fg fino
    big_keep = np.kron(small_keep, np.ones((scale, scale), bool))
    full = np.zeros((H, W), bool)
    full[:big_keep.shape[0], :big_keep.shape[1]] = big_keep
    clean = fg & full

    areas_sorted = sorted((a * scale * scale for a in areas.values()), reverse=True)
    return clean, areas_sorted


def to_rgba_cropped(rgb, fg_mask):
    """Aplica alfa (fg=opaco), recorta na bbox do personagem e padda pra quadrado."""
    arr = np.asarray(rgb).astype(np.uint8)
    bg_mask = ~fg_mask
    alpha = np.where(bg_mask, 0, 255).astype(np.uint8)
    rgba = np.dstack([arr, alpha])

    ys, xs = np.where(fg_mask)
    if len(xs) == 0:
        raise RuntimeError("personagem nao encontrado (fundo cobriu tudo)")
    x0, x1 = xs.min(), xs.max() + 1
    y0, y1 = ys.min(), ys.max() + 1
    crop = rgba[y0:y1, x0:x1]

    ch, cw = crop.shape[:2]
    side = max(ch, cw)
    # margem de 6% pra silhueta nao encostar na borda do frame
    pad = int(round(side * 0.06))
    side += pad * 2
    square = np.zeros((side, side, 4), dtype=np.uint8)
    oy = (side - ch) // 2
    ox = (side - cw) // 2
    square[oy:oy + ch, ox:ox + cw] = crop
    return Image.fromarray(square, "RGBA")


def downscale_premult(rgba, size):
    """Downscale com LANCZOS sobre canais premultiplicados pra nao sangrar
    cor de fundo nas bordas translucidas."""
    arr = np.asarray(rgba).astype(np.float32)
    rgb = arr[..., :3]
    a = arr[..., 3:4] / 255.0
    pm = rgb * a  # premultiplicado

    pm_img = Image.fromarray(np.clip(pm, 0, 255).astype(np.uint8), "RGB")
    a_img = Image.fromarray((a[..., 0] * 255).astype(np.uint8), "L")

    pm_small = np.asarray(pm_img.resize((size, size), Image.LANCZOS)).astype(np.float32)
    a_small = np.asarray(a_img.resize((size, size), Image.LANCZOS)).astype(np.float32) / 255.0

    a_safe = np.where(a_small > 0.001, a_small, 1.0)[..., None]
    rgb_small = np.clip(pm_small / a_safe, 0, 255)

    out = np.dstack([rgb_small.astype(np.uint8),
                     (a_small * 255).astype(np.uint8)])
    return Image.fromarray(out, "RGBA")


def quantize_palette(rgba, colors):
    """Quantiza so o RGB pra paleta chapada, preservando o alfa original."""
    arr = np.asarray(rgba)
    rgb = Image.fromarray(arr[..., :3], "RGB")
    q = rgb.quantize(colors=colors, method=Image.Quantize.MAXCOVERAGE, dither=Image.Dither.NONE)
    q = q.convert("RGB")
    out = np.dstack([np.asarray(q), arr[..., 3]])
    return Image.fromarray(out, "RGBA")


def hard_alpha(rgba, cutoff):
    """Corta o alfa em 0/255 e zera o RGB onde transparente."""
    arr = np.asarray(rgba).copy()
    a = arr[..., 3]
    hard = np.where(a >= cutoff, 255, 0).astype(np.uint8)
    arr[..., 3] = hard
    arr[hard == 0, :3] = 0
    return Image.fromarray(arr, "RGBA")


def enhance_rgba(rgba, sat, bri):
    """Realca saturacao/brilho do RGB preservando o alfa."""
    if sat == 1.0 and bri == 1.0:
        return rgba
    a = np.asarray(rgba)[..., 3]
    rgb = rgba.convert("RGB")
    rgb = ImageEnhance.Color(rgb).enhance(sat)
    rgb = ImageEnhance.Brightness(rgb).enhance(bri)
    return Image.fromarray(np.dstack([np.asarray(rgb), a]), "RGBA")


def paint_hood_eyes(base, color):
    """Pinta 2 olhos brilhantes na abertura do capuz: acha a celula opaca
    mais ESCURA na faixa alto-centro (a sombra do capuz) e ilumina ela + a
    vizinha horizontal. Recupera a assinatura do Oraculo que o quantize apaga."""
    arr = np.asarray(base).copy().astype(np.int16)
    op = arr[..., 3] > 0
    lum = 0.299 * arr[..., 0] + 0.587 * arr[..., 1] + 0.114 * arr[..., 2]
    best = None
    for cy in range(2, 8):           # faixa vertical da "cabeca"
        for cx in range(4, 12):      # faixa horizontal central
            if op[cy, cx]:
                if best is None or lum[cy, cx] < best[0]:
                    best = (lum[cy, cx], cy, cx)
    if best is None:
        return base
    _, cy, cx = best
    cells = [(cy, cx)]
    nx = cx + 1 if cx + 1 < 12 and op[cy, cx + 1] else cx - 1
    if 0 <= nx < FRAME:
        cells.append((cy, nx))
    for (y, x) in cells:
        arr[y, x, 0], arr[y, x, 1], arr[y, x, 2], arr[y, x, 3] = (
            color[0], color[1], color[2], 255)
    return Image.fromarray(arr.astype(np.uint8), "RGBA")


def make_base(src_path, keep_frac, flood, sat, bri, eyes):
    rgb = Image.open(src_path).convert("RGB")
    bg = remove_background(rgb, flood)
    fg, areas = keep_large_components(~bg, keep_frac, scale=2)
    rgba = to_rgba_cropped(rgb, fg)
    rgba = enhance_rgba(rgba, sat, bri)
    small = downscale_premult(rgba, FRAME)
    small = quantize_palette(small, PALETTE_COLORS)
    small = hard_alpha(small, ALPHA_CUTOFF)
    if eyes:
        small = paint_hood_eyes(small, eyes)
    return small, areas


# ---------------------------------------------------------------------------
# Frames de animacao (procedurais a partir do frame base 16x16)
# ---------------------------------------------------------------------------

def _arr(img):
    return np.asarray(img).astype(np.uint8)


def _img(a):
    return Image.fromarray(a.astype(np.uint8), "RGBA")


def shift(img, dx, dy):
    """Translada o conteudo em (dx,dy) preenchendo com transparente."""
    a = _arr(img)
    h, w = a.shape[:2]
    out = np.zeros_like(a)
    xs0, xs1 = max(0, dx), min(w, w + dx)
    ys0, ys1 = max(0, dy), min(h, h + dy)
    sxs0, sxs1 = max(0, -dx), min(w, w - dx)
    sys0, sys1 = max(0, -dy), min(h, h - dy)
    out[ys0:ys1, xs0:xs1] = a[sys0:sys1, sxs0:sxs1]
    return _img(out)


def squash_v(img, factor):
    """Comprime verticalmente p/ factor (0-1), alinhado embaixo (queda/respiro)."""
    a = _arr(img)
    h, w = a.shape[:2]
    nh = max(1, int(round(h * factor)))
    content = Image.fromarray(a, "RGBA").resize((w, nh), Image.NEAREST)
    out = np.zeros_like(a)
    out[h - nh:h] = np.asarray(content)
    # re-hard o alfa (NEAREST mantem, mas garante)
    out[..., 3] = np.where(out[..., 3] >= 110, 255, 0)
    out[out[..., 3] == 0, :3] = 0
    return _img(out)


def brighten(img, amount):
    a = _arr(img).astype(np.int16)
    m = a[..., 3] > 0
    for c in range(3):
        ch = a[..., c]
        ch[m] = np.clip(ch[m] + amount, 0, 255)
        a[..., c] = ch
    return _img(a.astype(np.uint8))


def build_frames(base):
    """Devolve a lista de 11 frames na ordem do layout."""
    # idle: base + 'respiro' (squash 1px)
    idle0 = base
    idle1 = squash_v(base, 15.0 / 16.0)

    # run: bob vertical + leve sway horizontal (figura que desliza)
    run0 = shift(base, 0, -1)
    run1 = shift(base, 1, 0)
    run2 = shift(base, 0, -1)
    run3 = shift(base, -1, 0)

    # attack: recuo -> avanco (SPD encara pra direita por padrao)
    atk0 = shift(base, -1, 0)
    atk1 = brighten(shift(base, 2, 0), 25)

    # die: colapso progressivo
    die0 = base
    die1 = squash_v(base, 0.6)
    die2 = squash_v(base, 0.3)

    return [idle0, idle1, run0, run1, run2, run3, atk0, atk1, die0, die1, die2]


def compose_sheet(frames):
    n = len(frames)
    sheet = Image.new("RGBA", (FRAME * n, FRAME), (0, 0, 0, 0))
    for i, f in enumerate(frames):
        sheet.paste(f, (i * FRAME, 0))
    return sheet


# ---------------------------------------------------------------------------
# Main
# ---------------------------------------------------------------------------

def main():
    os.makedirs(OUT_DIR, exist_ok=True)
    os.makedirs(PREVIEW_DIR, exist_ok=True)

    for outname, opts in JOBS.items():
        src = os.path.join(PORTRAITS, opts["src"])
        if not os.path.exists(src):
            print("  ! pulando (sem fonte):", src)
            continue
        base, areas = make_base(src, opts["keep_frac"], opts["flood"],
                                opts["sat"], opts["bri"], opts["eyes"])
        frames = build_frames(base)
        sheet = compose_sheet(frames)

        out_path = os.path.join(OUT_DIR, outname + ".png")
        sheet.save(out_path)

        # preview 8x pra inspecao visual
        prev = sheet.resize((sheet.width * 8, sheet.height * 8), Image.NEAREST)
        prev.save(os.path.join(PREVIEW_DIR, outname + "_x8.png"))
        base.resize((FRAME * 16, FRAME * 16), Image.NEAREST).save(
            os.path.join(PREVIEW_DIR, outname + "_base_x16.png"))

        pix = np.asarray(base).reshape(-1, 4)
        ncolors = len({tuple(p) for p in pix if p[3] > 0})
        top = ", ".join(str(a) for a in areas[:6])
        print("  ok %-26s %s  (%d frames, %d cores) ilhas=[%s]" %
              (outname + ".png", "%dx%d" % sheet.size, len(frames), ncolors, top))


if __name__ == "__main__":
    print("Gerando spritesheets narrativos...")
    main()
    print("Feito. Sheets em core/src/main/assets/sprites/narrative/")
    print("Previews em tools/pixelart/preview/")
