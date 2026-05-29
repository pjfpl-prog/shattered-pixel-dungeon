# Handoff — Sessão Pixel Art (sprites in-game narrativos)

Sprites 16x16 estilo SPD para os personagens narrativos, gerados a partir das
artes conceituais / retratos via `tools/pixelart/build_sprites.py`.

## Entregue (território desta sessão)

- **Spritesheets** `core/src/main/assets/sprites/narrative/`
  - `oraculo.png` (176x16, 11 frames)
  - `grave_boss_rei_cinza.png` (176x16, 11 frames)
  - `grave_boss_profeta_mudo.png` (176x16, 11 frames)
  - Layout por sheet: idle `0,1` · run `2,3,4,5` · attack `6,7` · die `8,9,10`
- **Classes de sprite** `narrative/sprites/`
  - `OraculoSprite` (extends `MobSprite`)
  - `GraveBossSprite` (extends `GooSprite`; escolhe a textura pela identidade
    da run — contém "profeta" → Profeta Mudo, senão → Rei Cinza)
- **Pipeline** `tools/pixelart/build_sprites.py` — reprodutível: remove fundo,
  crop, downscale premultiplicado, quantiza, hard-edge alpha, frames procedurais.
- **GooSprite.java**: `pump`/`pumpAttack` passaram de `private` p/ `protected`
  (mudança só de visibilidade, comportamento idêntico). Necessário pra
  `GraveBossSprite` reconstruir as animações na grade 16x16 sem sangrar UV.

Compila: `./gradlew :core:compileJava` → BUILD SUCCESSFUL.

## Pendente de wiring — território da Sessão A (NÃO editei)

A regra de território do briefing diz "NÃO toque" em `narrative/npcs/` e
`narrative/levels/`. Por isso deixei o plug final pra Sessão A. São 2 mudanças
de 1 linha cada:

1. **`narrative/npcs/EnigmaticOracle.java`**
   ```java
   import com.shatteredpixel.shatteredpixeldungeon.narrative.sprites.OraculoSprite;
   // ...
   spriteClass = OraculoSprite.class;   // era RatKingSprite.class
   ```
   (pode remover o import de `RatKingSprite`)

2. **`narrative/levels/NarrativeBoss.java`** — adicionar o initializer:
   ```java
   import com.shatteredpixel.shatteredpixeldungeon.narrative.sprites.GraveBossSprite;
   // ...
   {
       spriteClass = GraveBossSprite.class;   // reskin do Goo (mecânica intacta)
   }
   ```
   `GraveBossSprite` estende `GooSprite`, então todos os casts
   `((GooSprite)sprite)` do `Goo` continuam válidos.

## Regenerar / ajustar

```
python tools/pixelart/build_sprites.py
```
Parâmetros por personagem no dict `JOBS` (flood, keep_frac, sat/bri, eyes).
Previews ampliados em `tools/pixelart/preview/` (não versionados).
