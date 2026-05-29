# CLAUDE.md — Roguelike Narrativo Procedural sobre Shattered Pixel Dungeon

Este projeto é um fork do **Shattered Pixel Dungeon** (Evan Debenham, GPLv3) que adiciona uma camada narrativa procedural por cima. O briefing original do Bernardo está em `C:\Users\pedro\Downloads\projeto-bernardo-roguelike-narrativo.md`.

## Arquitetura narrativa

Toda a camada narrativa vive em `core/src/main/java/com/shatteredpixel/shatteredpixeldungeon/narrative/`. **Não dispersar conteúdo narrativo fora desse pacote**, exceto pelos hooks mínimos descritos abaixo.

```
narrative/
├── NarrativeDirector.java   — fachada estática. APIs públicas pra:
│                              • newRun(seed) / storeInBundle / restoreFromBundle
│                              • adventureTitle / dungeonTheme / emotionalTone /
│                                bossIdentity / mainQuest / questChain / factions
│                              • introText / journalText / endingText
│                              • greetNpc / npcState / revealForDepth /
│                                introShown/markIntroShown etc.
│                              • oneShotMode / oneShotLength / isBossDepth
├── models/
│   └── AdventureSeed.java   — Bundlable. Estado COMPLETO da run:
│                              adventureTitle, dungeonTheme, emotionalTone,
│                              finalBossIdentity, mainQuestChain, factions,
│                              loreFragments, npcStates, artifactLore,
│                              scheduledEvents, eventFlags + flags introShown/
│                              bossRevealed/endingShown
├── generators/              — geração ponderada com templates (NÃO LLM em runtime)
│   ├── TitleGenerator.java
│   ├── DungeonThemeGenerator.java
│   ├── EmotionalToneGenerator.java
│   ├── BossIdentityGenerator.java
│   ├── FactionsGenerator.java
│   ├── QuestChainGenerator.java
│   ├── LoreGenerator.java
│   ├── NpcStatesGenerator.java
│   ├── ArtifactLoreGenerator.java
│   └── EndingGenerator.java
├── dialogue/
│   ├── NpcKind.java         — enum GHOST/WANDMAKER/BLACKSMITH/IMP
│   └── NpcLines.java        — falas contextuais por arquétipo + tom
├── factions/
│   └── Faction.java         — name+role+gender+plural (concordância pt-BR)
├── lore/
│   └── LoreFragment.java    — BOOK/NOTE/INSCRIPTION/PROPHECY/WARNING
├── quests/
│   └── QuestStep.java
├── npcs/
│   ├── NpcState.java        — motivation/affiliationFaction/attitude (FRIENDLY/WARY/HOSTILE)
│   └── EnigmaticOracle.java — NPC custom (sala secreta, 8 enigmas)
├── events/
│   ├── EventBank.java       — pool estática de NarrativeEvent (~100+ entradas
│   │                          após sessões de conteúdo)
│   ├── NarrativeEvent.java  — id + title + prompt + options + depth range +
│   │                          themesPreferred + tonesPreferred + requiredFlags
│   ├── EventOption.java     — buttonText + resultText + effects[]
│   ├── EventEffect.java     — POJO immutable
│   ├── EventEffectKind.java — TEXT_ONLY, HEAL_PCT, DAMAGE_PCT, GIVE_GOLD,
│   │                          GIVE_XP, GIVE_ITEM, NPC_ATTITUDE, MARK_QUEST_STEP,
│   │                          ADD_BUFF, ADD_LORE, SET_FLAG, HT_BONUS
│   ├── EventInstance.java   — Bundlable: eventId, depth, triggered, chosenOption
│   ├── EventScheduleGenerator.java — distribui 1-2 eventos por piso 1-25
│   └── EventDirector.java   — popNextForDepth + apply (whitelist de Item/Buff classes)
├── levels/                  — one-shot mode (5/15/20 níveis)
│   ├── NarrativeChapter.java        — base abstrata. SecretRoom extra +
│   │                                  drops mágicos extras escalados por length.
│   ├── NarrativeChapter1..5.java    — variações de cor1/cor2 + música por tema +
│   │                                  mob extras via NarrativeSpawns
│   ├── NarrativeBossChapter.java    — extends SewerBossLevel, troca Goo por
│   │                                  NarrativeBoss, addVisuals com torches,
│   │                                  unseal dispara EndingGenerator
│   ├── NarrativeBoss.java           — extends Goo. Override name (= bossIdentity),
│   │                                  notice (WndTitledMessage com retrato),
│   │                                  die (yell por tom)
│   ├── NarrativeSpawns.java         — helper pra spawnar mob em sala standard
│   └── SecretOracleRoom.java        — SecretRoom variante que spawna EnigmaticOracle
└── util/
    └── PtBr.java            — contractDe/contractEm/contractA, Gender M/F,
                                article, demonstrative, pastParticiple, cap, lowerFirst
```

## Hooks no código original do SPD

Modificamos APENAS estes arquivos fora de `narrative/`:

- **`Dungeon.java`**:
  - `init()` chama `NarrativeDirector.newRun(seed)`
  - `saveGame()` chama `NarrativeDirector.storeInBundle(bundle)`
  - `loadGame()` chama `NarrativeDirector.restoreFromBundle(bundle)`
  - `newLevel()`: branch one-shot retornando NarrativeChapter1..5/BossChapter
- **`Hero.java::earnExp`**: multiplicador 1.2x/1.5x/2.5x conforme oneShotLength
- **`scenes/GameScene.java::create`**: intro WndStory + lore por piso + boss reveal + queue de NarrativeEvent
- **`scenes/StartScene.java`**: botão "Nova Aventura" sempre visível
- **`scenes/TitleScene.java::btnPlay.onClick`**: WndAdventureType direto quando não há saves
- **`actors/mobs/npcs/{Ghost,Wandmaker,Blacksmith,Imp}.java::interact`**:
  saudação via WndTitledMessage com retrato (depois do guard `c != hero`)
- **`items/artifacts/Artifact.java::info`**: anexa `NarrativeDirector.artifactLoreFor(getClass())`
- **`windows/`**: `WndAdventureType.java`, `WndChoice.java`

## Modo One-Shot

`SPDSettings.oneShotLength` = 0 (clássica) / 5 / 15 / 20. Setado no `WndAdventureType` chamado pelo TitleScene/StartScene.

Quando length > 0:
- `Dungeon.newLevel()` usa `NarrativeChapter1..5` (cíclico) + `NarrativeBossChapter` no piso `oneShotLength`
- `Hero.earnExp` multiplica conforme tabela
- `NarrativeChapter.createItems` adiciona 1-3 itens mágicos fixos + bônus aleatório (mais agressivo em length=5)
- `WndAdventureType` → `HeroSelectScene` → run normal

## Sprites/Retratos

- **`core/src/main/assets/narrative/*.png`** — retratos 64x64 e 128x128 (PNG fotorrealistas reduzidos da arte gerada via gpt-image-1 1024x1024)
- **`tools/concepts/*.png`** — arte conceitual 1024x1024 (input pra pixel art)
- **`tools/gerar_sprites.py`** — script de geração via gpt-image-1. Chave lida automaticamente de `C:\Users\pedro\.openclaw\.env.local` (`OPENAI_API_KEY=...`)
- **`core/src/main/assets/sprites/narrative/*.png`** (sessão de pixel art) — spritesheets 16x16 estilo SPD pra uso no mapa
- **`core/src/main/java/com/shatteredpixel/shatteredpixeldungeon/narrative/sprites/`** (sessão de pixel art) — Sprite Java classes que apontam pros PNGs acima

## Coordenação multi-sessão (paralelas)

Quando há sessões paralelas em diferentes territórios:

1. **Antes de cada bloco de edição**: `git pull origin master`
2. **Stage SÓ os arquivos do seu território** — NUNCA `git add -A`
3. **Commit message prefixado** por território (`narrative content: ...`, `narrative levels: ...`, `pixelart: ...`)
4. **Push ao final de cada bloco**

Briefings de sessão estão em `C:\Users\pedro\OneDrive\Área de Trabalho\briefing-bernardo-roguelike-sessao-*.md`.

## Build/Run

- JDK 17+ obrigatório (Gradle 9.x)
- Roda: `.\gradlew.bat desktop:debug` (a task `desktop:run` falha por `Implementation-Version` null)
- Saves em `%APPDATA%\.shatteredpixel\Shattered Pixel Dungeon\game{1,2,3}`. Limpar `game*` se um save de teste ficar corrompido durante experimentação.

## Princípios de design

Do briefing original (manter):
- Geração procedural ESTRUTURADA com templates ponderados — não LLM em runtime
- NÃO redesenhar combate, sprites originais ou geração de mapas (one-shot é exceção opcional)
- Persistência VIA BUNDLE — toda a aventura vive em AdventureSeed
- Determinismo por dungeonSeed — mesma seed gera mesma aventura
- Coerência simbólica entre tema/boss/facções/lore/quest — pools sobrepostos propositais
