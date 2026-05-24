/*
 * Projeto Roguelike Narrativo Procedural — Fase 1
 *
 * Estado narrativo de um NPC para a run atual:
 *   - motivation: motivo curto pelo qual o NPC está naquele lugar
 *   - affiliationFaction: nome da facção a que pertence (ou "" se neutro)
 *   - attitude: tom inicial em relação ao herói
 *
 * NÃO altera alignment do Mob nem combate — só colore o diálogo.
 *
 * Bundlable + ctor sem args para Reflection do Bundle.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.npcs;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class NpcState implements Bundlable {

	public enum Attitude {
		FRIENDLY, WARY, HOSTILE
	}

	public String motivation         = "";
	public String affiliationFaction = "";
	public Attitude attitude         = Attitude.WARY;

	public NpcState() {}

	public NpcState(String motivation, String affiliationFaction, Attitude attitude) {
		this.motivation         = motivation;
		this.affiliationFaction = affiliationFaction;
		this.attitude           = attitude;
	}

	private static final String MOTIVATION  = "motivation";
	private static final String AFFILIATION = "affiliation";
	private static final String ATTITUDE    = "attitude";

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(MOTIVATION,  motivation);
		bundle.put(AFFILIATION, affiliationFaction);
		bundle.put(ATTITUDE,    attitude.name());
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		motivation         = bundle.getString(MOTIVATION);
		affiliationFaction = bundle.getString(AFFILIATION);
		try {
			attitude = Attitude.valueOf(bundle.getString(ATTITUDE));
		} catch (IllegalArgumentException e) {
			attitude = Attitude.WARY;
		}
	}
}
