/*
 * Projeto Roguelike Narrativo Procedural — Fase 1
 *
 * NarrativeDirector é a fachada estática do subsistema narrativo.
 * Espelha o padrão dos demais módulos do jogo (Statistics, Notes, Generator):
 *   - newRun()              → chamado por Dungeon.init()
 *   - storeInBundle(b)      → chamado por Dungeon.saveGame()
 *   - restoreFromBundle(b)  → chamado por Dungeon.loadGame()
 *
 * O estado narrativo da run vive em currentSeed (AdventureSeed).
 *
 * Fase 1 implementa apenas a geração de adventureTitle. Os outros campos
 * existem em AdventureSeed mas ainda não são preenchidos.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative;

import com.shatteredpixel.shatteredpixeldungeon.narrative.dialogue.NpcKind;
import com.shatteredpixel.shatteredpixeldungeon.narrative.dialogue.NpcLines;
import com.shatteredpixel.shatteredpixeldungeon.narrative.events.EventBank;
import com.shatteredpixel.shatteredpixeldungeon.narrative.events.EventInstance;
import com.shatteredpixel.shatteredpixeldungeon.narrative.events.EventScheduleGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.events.NarrativeEvent;
import com.shatteredpixel.shatteredpixeldungeon.narrative.factions.Faction;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.ArtifactLoreGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.BossIdentityGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.DungeonThemeGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.EmotionalToneGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.EndingGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.FactionsGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.LoreGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.NpcStatesGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.QuestChainGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.TitleGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.lore.LoreFragment;
import com.shatteredpixel.shatteredpixeldungeon.narrative.models.AdventureSeed;
import com.shatteredpixel.shatteredpixeldungeon.narrative.npcs.NpcState;
import com.shatteredpixel.shatteredpixeldungeon.narrative.quests.QuestStep;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class NarrativeDirector {

	private static AdventureSeed currentSeed = new AdventureSeed();

	private NarrativeDirector() {}

	public static AdventureSeed seed() {
		return currentSeed;
	}

	public static String adventureTitle() {
		return currentSeed != null ? currentSeed.adventureTitle : "";
	}

	public static String dungeonTheme() {
		return currentSeed != null ? currentSeed.dungeonTheme : "";
	}

	public static String emotionalTone() {
		return currentSeed != null ? currentSeed.emotionalTone : "";
	}

	public static String bossIdentity() {
		return currentSeed != null ? currentSeed.finalBossIdentity : "";
	}

	public static List<Faction> factions() {
		if (currentSeed == null) return Collections.emptyList();
		return Collections.unmodifiableList(currentSeed.factions);
	}

	// Descrição procedural anexada a Artifact.info() — null/vazio se nada gerado.
	public static String artifactLoreFor(Class<?> artifactClass) {
		if (currentSeed == null || artifactClass == null) return null;
		return currentSeed.artifactLore.get(artifactClass.getSimpleName());
	}

	// Resumo curto para a intro: nomes articulados separados por vírgula.
	// Usa Faction.articledName pra produzir "Os Monges do Véu" / "A Ordem das Cinzas".
	public static String factionsSummary() {
		if (currentSeed == null || currentSeed.factions.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < currentSeed.factions.size(); i++) {
			if (i > 0) sb.append(", ");
			sb.append(currentSeed.factions.get(i).articledName(true));
		}
		return sb.toString();
	}

	// Texto multi-linha pra WndStory na intro da run.
	// Retorna "" se não há aventura ainda.
	public static String introText() {
		if (currentSeed == null || currentSeed.adventureTitle.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		sb.append("_").append(currentSeed.adventureTitle).append("_\n\n");
		if (!currentSeed.dungeonTheme.isEmpty()) {
			sb.append("Tema: ").append(currentSeed.dungeonTheme).append("\n");
		}
		if (!currentSeed.emotionalTone.isEmpty()) {
			sb.append("Tom: ").append(currentSeed.emotionalTone).append("\n");
		}
		String fac = factionsSummary();
		if (!fac.isEmpty()) {
			sb.append("\nFacções desta run: ").append(fac).append(".\n");
		}
		String quest = mainQuest();
		if (!quest.isEmpty()) {
			sb.append("\nSeu objetivo: ").append(quest).append(".\n");
		}
		if (!currentSeed.finalBossIdentity.isEmpty()) {
			sb.append("\nNo fundo da dungeon espera: _").append(currentSeed.finalBossIdentity).append("_.");
		}
		return sb.toString();
	}

	// Texto extenso pra página "Aventura" no Journal: tudo que a intro mostra
	// + role das facções + cadeia completa de quests + lore já descoberto.
	public static String journalText() {
		if (currentSeed == null || currentSeed.adventureTitle.isEmpty()) {
			return "Sua aventura ainda não foi gerada.";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("_").append(currentSeed.adventureTitle).append("_\n");
		if (!currentSeed.dungeonTheme.isEmpty()) {
			sb.append("Tema: ").append(currentSeed.dungeonTheme).append("\n");
		}
		if (!currentSeed.emotionalTone.isEmpty()) {
			sb.append("Tom: ").append(currentSeed.emotionalTone).append("\n");
		}
		if (!currentSeed.finalBossIdentity.isEmpty()) {
			sb.append("Adversário final: _").append(currentSeed.finalBossIdentity).append("_\n");
		}

		if (!currentSeed.factions.isEmpty()) {
			sb.append("\n_Facções_\n");
			for (com.shatteredpixel.shatteredpixeldungeon.narrative.factions.Faction f : currentSeed.factions) {
				sb.append("- ").append(f.articledName(true));
				if (f.role != null && !f.role.isEmpty()) {
					sb.append(" — ").append(f.role);
				}
				sb.append("\n");
			}
		}

		if (!currentSeed.mainQuestChain.isEmpty()) {
			sb.append("\n_Cadeia da Aventura_\n");
			int i = 1;
			for (QuestStep s : currentSeed.mainQuestChain) {
				sb.append(i++).append(". ");
				if (s.completed) sb.append("~");
				sb.append(s.description).append(s.completed ? "~" : "").append("\n");
			}
		}

		boolean anyDiscovered = false;
		for (LoreFragment f : currentSeed.loreFragments) {
			if (f.discovered) { anyDiscovered = true; break; }
		}
		if (anyDiscovered) {
			sb.append("\n_Fragmentos Descobertos_\n");
			for (LoreFragment f : currentSeed.loreFragments) {
				if (f.discovered) {
					sb.append("- (piso ").append(f.triggerDepth).append(") ")
							.append(f.formatted()).append("\n");
				}
			}
		} else if (!currentSeed.loreFragments.isEmpty()) {
			sb.append("\n_Fragmentos Descobertos_\nNenhum até agora.\n");
		}

		// Histórico de escolhas — eventos triggered com chosenOption válida.
		boolean anyChoice = false;
		for (java.util.Map.Entry<Integer, ArrayList<EventInstance>> e
				: currentSeed.scheduledEvents.entrySet()) {
			for (EventInstance inst : e.getValue()) {
				if (inst.triggered && inst.chosenOption >= 0) { anyChoice = true; break; }
			}
			if (anyChoice) break;
		}
		if (anyChoice) {
			sb.append("\n_Suas Escolhas_\n");
			// Ordena por piso ascendente.
			ArrayList<Integer> depths = new ArrayList<>(currentSeed.scheduledEvents.keySet());
			java.util.Collections.sort(depths);
			for (int d : depths) {
				for (EventInstance inst : currentSeed.scheduledEvents.get(d)) {
					if (!inst.triggered || inst.chosenOption < 0) continue;
					NarrativeEvent ev = EventBank.get(inst.eventId);
					if (ev == null) continue;
					if (inst.chosenOption >= ev.options.size()) continue;
					String chosen = ev.options.get(inst.chosenOption).buttonText;
					sb.append("- (piso ").append(d).append(") ")
							.append(ev.title).append(": ").append(chosen).append("\n");
				}
			}
		}

		return sb.toString();
	}

	public static String mainQuest() {
		if (currentSeed == null) return "";
		if (!currentSeed.mainQuest.isEmpty()) return currentSeed.mainQuest;
		if (!currentSeed.mainQuestChain.isEmpty()) return currentSeed.mainQuestChain.get(0).description;
		return "";
	}

	public static List<QuestStep> questChain() {
		if (currentSeed == null) return Collections.emptyList();
		return Collections.unmodifiableList(currentSeed.mainQuestChain);
	}

	// Retorna (e marca como descobertos) os fragmentos de lore associados ao piso.
	// Chamado por GameScene quando o jogador chega num piso novo.
	public static List<LoreFragment> revealForDepth(int depth) {
		if (currentSeed == null) return Collections.emptyList();
		ArrayList<LoreFragment> revealed = new ArrayList<>();
		for (LoreFragment f : currentSeed.loreFragments) {
			if (!f.discovered && f.triggerDepth == depth) {
				f.discovered = true;
				revealed.add(f);
			}
		}
		return revealed;
	}

	public static List<LoreFragment> loreFragments() {
		if (currentSeed == null) return Collections.emptyList();
		return Collections.unmodifiableList(currentSeed.loreFragments);
	}

	// Devolve a fala narrativa do NPC se for o primeiro encontro, ou null.
	// Marca o NPC como cumprimentado em caso afirmativo.
	public static String greetNpc(NpcKind kind) {
		if (currentSeed == null || kind == null) return null;
		if (currentSeed.npcGreeted.contains(kind.name())) return null;
		currentSeed.npcGreeted.add(kind.name());
		return NpcLines.greet(kind);
	}

	public static NpcState npcState(NpcKind kind) {
		if (currentSeed == null || kind == null) return null;
		return currentSeed.npcStates.get(kind.name());
	}

	// Gera a aventura no início de uma nova run.
	// Empurra um gerador determinístico a partir da dungeonSeed para que toda
	// a aventura seja reproduzível por seed e independente do RNG do gameplay.
	// Ordem importa: tom → tema → título → cadeia de quests; passos futuros
	// vão usar tom/tema para enviesar pools dos demais geradores.
	public static void newRun(long dungeonSeed) {
		currentSeed = new AdventureSeed();

		Random.pushGenerator(dungeonSeed ^ 0x4E_41_52_52_41_54_56L); // "NARRATV"
		try {
			currentSeed.emotionalTone     = EmotionalToneGenerator.generate();
			currentSeed.dungeonTheme      = DungeonThemeGenerator.generate();
			currentSeed.finalBossIdentity = BossIdentityGenerator.generate(currentSeed.dungeonTheme);
			currentSeed.factions          = new ArrayList<>(FactionsGenerator.generate(currentSeed.dungeonTheme));
			currentSeed.npcStates.clear();
			for (java.util.Map.Entry<NpcKind, NpcState> e
					: NpcStatesGenerator.generate(currentSeed.emotionalTone, currentSeed.factions).entrySet()) {
				currentSeed.npcStates.put(e.getKey().name(), e.getValue());
			}
			currentSeed.artifactLore      = new HashMap<>(ArtifactLoreGenerator.generate(currentSeed.dungeonTheme, currentSeed.emotionalTone));
			currentSeed.adventureTitle    = TitleGenerator.generate();
			currentSeed.mainQuestChain    = new ArrayList<>(QuestChainGenerator.generate());
			if (!currentSeed.mainQuestChain.isEmpty()) {
				currentSeed.mainQuest = currentSeed.mainQuestChain.get(0).description;
			}
			currentSeed.loreFragments     = new ArrayList<>(LoreGenerator.generate());
			currentSeed.scheduledEvents.clear();
			for (java.util.Map.Entry<Integer, java.util.List<EventInstance>> e
					: EventScheduleGenerator.generate(currentSeed.dungeonTheme, currentSeed.emotionalTone).entrySet()) {
				currentSeed.scheduledEvents.put(e.getKey(), new ArrayList<>(e.getValue()));
			}
		} finally {
			Random.popGenerator();
		}
	}

	public static boolean introShown() {
		return currentSeed != null && currentSeed.introShown;
	}

	public static void markIntroShown() {
		if (currentSeed != null) currentSeed.introShown = true;
	}

	public static boolean bossRevealed() {
		return currentSeed != null && currentSeed.bossRevealed;
	}

	public static void markBossRevealed() {
		if (currentSeed != null) currentSeed.bossRevealed = true;
	}

	public static boolean endingShown() {
		return currentSeed != null && currentSeed.endingShown;
	}

	// Modo one-shot RPG: ativo quando o jogador escolheu um tamanho (5/15/20)
	// no menu inicial. Length 0 = aventura clássica do SPD.
	public static boolean oneShotMode() {
		return com.shatteredpixel.shatteredpixeldungeon.SPDSettings.oneShotLength() > 0;
	}

	public static int oneShotLength() {
		return com.shatteredpixel.shatteredpixeldungeon.SPDSettings.oneShotLength();
	}

	public static boolean isBossDepth(int depth) {
		return oneShotMode() && depth == oneShotLength();
	}

	public static void markEndingShown() {
		if (currentSeed != null) currentSeed.endingShown = true;
	}

	public static String endingText() {
		return EndingGenerator.generate();
	}

	private static final String SEED_BUNDLE_KEY = "narrative_seed";

	public static void storeInBundle(Bundle bundle) {
		if (currentSeed == null) currentSeed = new AdventureSeed();
		Bundle inner = new Bundle();
		currentSeed.storeInBundle(inner);
		bundle.put(SEED_BUNDLE_KEY, inner);
	}

	public static void restoreFromBundle(Bundle bundle) {
		currentSeed = new AdventureSeed();
		if (bundle.contains(SEED_BUNDLE_KEY)) {
			Bundle inner = bundle.getBundle(SEED_BUNDLE_KEY);
			if (!inner.isNull()) {
				currentSeed.restoreFromBundle(inner);
			}
		}
		// Saves antigos (pré-narrativa) não terão o bundle: deixa título vazio.
	}
}
