#!/usr/bin/env python3
"""Gera arte conceitual dos NPCs narrativos via OpenAI gpt-image-1.

Mesma chave/pattern do guia-agentes/gerar_imagens.py.
Sprites de SPD são 16x16 pixelart em spritesheet — esta primeira geração
produz arte conceitual 1024x1024 pra avaliação visual; a quantização pra
spritesheet 16x16 fica como passo seguinte (PIL.quantize + scale).
"""
from __future__ import annotations

import base64
import concurrent.futures as cf
import sys
from pathlib import Path

import requests

BASE = Path(__file__).resolve().parent
OUT = BASE / "concepts"
OUT.mkdir(exist_ok=True)

ENV = Path("C:/Users/pedro/.openclaw/.env.local")
KEY = None
for line in ENV.read_text(encoding="utf-8", errors="ignore").splitlines():
    if line.startswith("OPENAI_API_KEY="):
        KEY = line.split("=", 1)[1].strip().strip('"').strip("'")
        break
if not KEY:
    print("sem chave em", ENV)
    sys.exit(1)

URL = "https://api.openai.com/v1/images/generations"
HEAD = {"Authorization": f"Bearer {KEY}", "Content-Type": "application/json"}

JOBS = {
    "ghost": (
        "16-bit pixel art portrait of a translucent ghost adventurer for a dark fantasy "
        "roguelike. A pale fading warrior, half-transparent, eyes lowered with regret, "
        "wearing the ghost of leather armor. Hair like smoke. Faint cyan-white inner glow. "
        "Background plain charcoal gray #222222. Centered headshot, clear silhouette, "
        "limited palette pixel art, no text, no letters, no border."
    ),
    "wandmaker": (
        "16-bit pixel art portrait of an elderly hooded wandmaker for a dark fantasy "
        "roguelike. A wrinkled cunning face peering out of a hood, holding a small carved "
        "wand close to the chest. Robes in earth-brown and forest-green with bone and "
        "feather charms. Eyes sharp, suspicious, wise. Background plain charcoal gray "
        "#222222. Centered headshot, limited palette pixel art, no text, no letters."
    ),
    "blacksmith": (
        "16-bit pixel art portrait of a dwarven blacksmith for a dark fantasy roguelike. "
        "Massive bearded figure with arms folded, leather apron scorched by fire, soot "
        "on the cheeks, one eye slightly larger from a forge accident. Hammer slung over "
        "the shoulder. Background plain charcoal gray #222222. Centered headshot, limited "
        "palette pixel art, no text, no letters."
    ),
    "imp": (
        "16-bit pixel art portrait of a mischievous trickster imp for a dark fantasy "
        "roguelike. A small grinning humanoid with crimson skin, golden eyes, pointy "
        "ears, a tiny pair of horns, holding a coin between two fingers. Mocking smile "
        "with sharp teeth. Background plain charcoal gray #222222. Centered headshot, "
        "limited palette pixel art, no text, no letters."
    ),
    "oraculo": (
        "16-bit pixel art sprite of a 'Silent Oracle' character for a dark fantasy "
        "roguelike dungeon game. A hooded figure with no visible face, only two glowing "
        "white eyes deep in the hood shadow. Robes in deep purple and tarnished gold, "
        "with mystical runes embroidered along the hem. The figure floats slightly above "
        "the ground. Centered composition on a neutral charcoal-gray background (#222222), "
        "clear silhouette, limited palette suitable for pixel art, soft inner glow. "
        "No text, no letters, no border."
    ),
    "boss-rei-cinza": (
        "16-bit pixel art sprite of 'The Gray King', the final boss of a dark fantasy "
        "roguelike. A gaunt regal figure wearing a cracked iron crown tilted to one side, "
        "tattered royal robes in faded gray and rust-red, holding a broken scepter. "
        "Empty hollow eyes that emit pale blue ghost-flames. Skin like dried parchment. "
        "Background plain charcoal gray #222222. Centered, clear silhouette, limited "
        "palette pixel art, no text, no letters, no border."
    ),
    "boss-profeta-mudo": (
        "16-bit pixel art sprite of 'The Silent Prophet', dark fantasy roguelike boss. "
        "A robed figure with mouth sewn shut with golden thread, pale skin, eyes covered "
        "by a strip of blood-stained cloth, hands holding torn pages floating around in "
        "the air. Robes in dirty white and faded crimson. Background plain charcoal gray "
        "#222222. Centered, clear silhouette, pixel art limited palette, no text, no "
        "letters, no border."
    ),
    "boss-coveiro-antigo": (
        "16-bit pixel art portrait of 'The Old Gravedigger', a boss for a dark fantasy "
        "roguelike. Ancient bony figure leaning on a tall iron shovel, eyes sunken and "
        "glowing dim white, dirty long beard tangled with roots and small bones, dressed "
        "in soil-stained patched robes the color of grave dirt and rust. Background plain "
        "charcoal gray #222222. Centered headshot, limited palette pixel art, no text, no "
        "letters."
    ),
    "boss-mente-raiz": (
        "16-bit pixel art portrait of 'The Root Mind', dark fantasy roguelike boss. A "
        "humanoid shape almost entirely made of pale fungal roots and tendrils wrapped "
        "around what used to be a body, no visible face, just a faint glowing core "
        "pulsing soft blue-green light at the chest. Background plain charcoal gray "
        "#222222. Centered, limited palette pixel art, no text, no letters."
    ),
    "boss-santo-oco": (
        "16-bit pixel art portrait of 'The Hollow Saint', dark fantasy roguelike boss. "
        "A figure in pristine white-and-gold ecclesiastic robes with a tall mitre, but "
        "the face beneath the cowl is a void of darkness with two thin slits of cold "
        "white light. Holds a censer that emits black smoke. Background plain charcoal "
        "gray #222222. Centered headshot, limited palette pixel art, no text, no letters."
    ),
    "boss-sacerdote-insone": (
        "16-bit pixel art portrait of 'The Sleepless Priest', dark fantasy roguelike "
        "boss. Gaunt face, bloodshot wide-open eyes that haven't slept in years, dark "
        "circles, sweat on the brow. Wearing tattered ceremonial robes in deep purple "
        "and tarnished bronze. Holding a small bell with shaking hand. Background plain "
        "charcoal gray #222222. Centered headshot, limited palette pixel art, no text, "
        "no letters."
    ),
    "boss-mae-das-moscas": (
        "16-bit pixel art portrait of 'The Mother of Flies', dark fantasy roguelike "
        "boss. A bloated figure of a once-beautiful woman now covered in writhing "
        "black flies, eyes replaced by compound-eye orbs, dressed in ragged plague-doctor "
        "robes in olive-green and rusty orange. Flies orbit the head. Background plain "
        "charcoal gray #222222. Centered headshot, limited palette pixel art, no text, "
        "no letters."
    ),
}


def gen(name_prompt):
    name, prompt = name_prompt
    payload = {
        "model": "gpt-image-1",
        "prompt": prompt,
        "size": "1024x1024",
        "quality": "medium",
        "background": "opaque",
        "n": 1,
    }
    r = requests.post(URL, headers=HEAD, json=payload, timeout=180)
    if r.status_code != 200:
        return name, f"ERRO {r.status_code}: {r.text[:300]}"
    b64 = r.json()["data"][0]["b64_json"]
    (OUT / f"{name}.png").write_bytes(base64.b64decode(b64))
    return name, "ok"


def main():
    with cf.ThreadPoolExecutor(max_workers=3) as ex:
        for name, status in ex.map(gen, JOBS.items()):
            print(f"{name}: {status}")


if __name__ == "__main__":
    main()
