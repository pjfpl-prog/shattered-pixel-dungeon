/*
 * Projeto Roguelike Narrativo Procedural — Fase 1
 *
 * AdventureSeed é o estado narrativo de uma run.
 * É gerado uma vez em Dungeon.init() e persiste pelo Bundle até o fim da run.
 *
 * Estado coberto até aqui (Passos 1-3 da estratégia incremental):
 *   - adventureTitle  (Passo 1)
 *   - dungeonTheme    (Passo 2)
 *   - emotionalTone   (Passo 2)
 *   - mainQuestChain  (Passo 3)
 *
 * Os demais campos (finalBossIdentity, factions, lore, ...) ficam como
 * placeholders para os próximos passos.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.models;

import com.shatteredpixel.shatteredpixeldungeon.narrative.factions.Faction;
import com.shatteredpixel.shatteredpixeldungeon.narrative.lore.LoreFragment;
import com.shatteredpixel.shatteredpixeldungeon.narrative.quests.QuestStep;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class AdventureSeed implements Bundlable {

	public String adventureTitle = "";

	public String dungeonTheme        = "";
	public String emotionalTone       = "";

	// Cadeia narrativa principal — gerada uma vez por run.
	// A descrição agregada (e.g. primeiro step) fica em mainQuest para legado/preview.
	public ArrayList<QuestStep> mainQuestChain = new ArrayList<>();
	public String mainQuest           = "";

	// Fragmentos de lore espalhados por pisos. Cada um dispara uma vez quando
	// o jogador chega no triggerDepth correspondente.
	public ArrayList<LoreFragment> loreFragments = new ArrayList<>();

	// NPCs já saudados nesta run (nome do NpcKind enum). Garante que a fala
	// narrativa única só dispare na primeira interação.
	public HashSet<String> npcGreeted = new HashSet<>();

	// Facções ativas na aventura. 2-3 por run, sorteadas a partir do tema.
	public ArrayList<Faction> factions = new ArrayList<>();

	// Descrições procedurais para artefatos do jogo.
	// Chave: nome simples da classe do Artifact (ex.: "EtherealChains"); valor: texto.
	public HashMap<String, String> artifactLore = new HashMap<>();

	// Placeholders para passos futuros.
	public String finalBossIdentity   = "";

	// Marca se a introdução narrativa (título) já foi mostrada ao jogador nesta run.
	public boolean introShown = false;

	// Marca se a revelation do boss final já disparou (piso 25).
	public boolean bossRevealed = false;

	private static final String ADVENTURE_TITLE     = "adventure_title";
	private static final String DUNGEON_THEME       = "dungeon_theme";
	private static final String MAIN_QUEST          = "main_quest";
	private static final String MAIN_QUEST_CHAIN    = "main_quest_chain";
	private static final String LORE_FRAGMENTS      = "lore_fragments";
	private static final String NPC_GREETED         = "npc_greeted";
	private static final String FACTIONS            = "factions";
	private static final String ARTIFACT_LORE       = "artifact_lore";
	private static final String FINAL_BOSS_IDENTITY = "final_boss_identity";
	private static final String EMOTIONAL_TONE      = "emotional_tone";
	private static final String INTRO_SHOWN         = "intro_shown";
	private static final String BOSS_REVEALED       = "boss_revealed";

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(ADVENTURE_TITLE,     adventureTitle);
		bundle.put(DUNGEON_THEME,       dungeonTheme);
		bundle.put(MAIN_QUEST,          mainQuest);
		bundle.put(MAIN_QUEST_CHAIN,    mainQuestChain);
		bundle.put(LORE_FRAGMENTS,      loreFragments);
		bundle.put(NPC_GREETED,         npcGreeted.toArray(new String[0]));
		bundle.put(FACTIONS,            factions);

		Bundle artBundle = new Bundle();
		for (java.util.Map.Entry<String, String> e : artifactLore.entrySet()) {
			artBundle.put(e.getKey(), e.getValue());
		}
		bundle.put(ARTIFACT_LORE, artBundle);
		bundle.put(FINAL_BOSS_IDENTITY, finalBossIdentity);
		bundle.put(EMOTIONAL_TONE,      emotionalTone);
		bundle.put(INTRO_SHOWN,         introShown);
		bundle.put(BOSS_REVEALED,       bossRevealed);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		adventureTitle    = bundle.getString(ADVENTURE_TITLE);
		dungeonTheme      = bundle.getString(DUNGEON_THEME);
		mainQuest         = bundle.getString(MAIN_QUEST);
		finalBossIdentity = bundle.getString(FINAL_BOSS_IDENTITY);
		emotionalTone     = bundle.getString(EMOTIONAL_TONE);
		introShown        = bundle.getBoolean(INTRO_SHOWN);
		bossRevealed      = bundle.getBoolean(BOSS_REVEALED);

		mainQuestChain.clear();
		if (bundle.contains(MAIN_QUEST_CHAIN)) {
			Collection<Bundlable> stored = bundle.getCollection(MAIN_QUEST_CHAIN);
			for (Bundlable b : stored) {
				if (b instanceof QuestStep) mainQuestChain.add((QuestStep) b);
			}
		}

		loreFragments.clear();
		if (bundle.contains(LORE_FRAGMENTS)) {
			Collection<Bundlable> stored = bundle.getCollection(LORE_FRAGMENTS);
			for (Bundlable b : stored) {
				if (b instanceof LoreFragment) loreFragments.add((LoreFragment) b);
			}
		}

		npcGreeted.clear();
		if (bundle.contains(NPC_GREETED)) {
			String[] arr = bundle.getStringArray(NPC_GREETED);
			if (arr != null) npcGreeted.addAll(Arrays.asList(arr));
		}

		factions.clear();
		if (bundle.contains(FACTIONS)) {
			Collection<Bundlable> stored = bundle.getCollection(FACTIONS);
			for (Bundlable b : stored) {
				if (b instanceof Faction) factions.add((Faction) b);
			}
		}

		artifactLore.clear();
		if (bundle.contains(ARTIFACT_LORE)) {
			Bundle artBundle = bundle.getBundle(ARTIFACT_LORE);
			if (!artBundle.isNull()) {
				for (String key : artBundle.getKeys()) {
					artifactLore.put(key, artBundle.getString(key));
				}
			}
		}
	}
}
