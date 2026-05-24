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
import com.shatteredpixel.shatteredpixeldungeon.narrative.factions.Faction;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.ArtifactLoreGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.BossIdentityGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.DungeonThemeGenerator;
import com.shatteredpixel.shatteredpixeldungeon.narrative.generators.EmotionalToneGenerator;
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

	// Resumo curto para a intro: nomes separados por vírgula.
	public static String factionsSummary() {
		if (currentSeed == null || currentSeed.factions.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < currentSeed.factions.size(); i++) {
			if (i > 0) sb.append(", ");
			sb.append(currentSeed.factions.get(i).name);
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
