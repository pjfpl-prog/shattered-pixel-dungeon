/*
 * Projeto Roguelike Narrativo Procedural — Fase 1 / Passo 3
 *
 * QuestStep é um passo da cadeia narrativa principal.
 * Bundlable + construtor sem argumentos para que o Bundle do jogo
 * possa restaurar via Reflection (ver Bundle.getCollection / put).
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.quests;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class QuestStep implements Bundlable {

	public String description = "";
	public boolean completed  = false;

	public QuestStep() {}

	public QuestStep(String description) {
		this.description = description;
	}

	private static final String DESCRIPTION = "description";
	private static final String COMPLETED   = "completed";

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(DESCRIPTION, description);
		bundle.put(COMPLETED,   completed);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		description = bundle.getString(DESCRIPTION);
		completed   = bundle.getBoolean(COMPLETED);
	}
}
