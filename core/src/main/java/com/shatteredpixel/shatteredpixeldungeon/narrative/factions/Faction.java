/*
 * Projeto Roguelike Narrativo Procedural — Fase 1
 *
 * Uma facção da aventura atual. Nesta fase só carrega texto;
 * em fases futuras pode passar a influenciar NPCs (alinhamento)
 * e drops (relíquias temáticas).
 *
 * Bundlable + ctor sem args para o sistema Reflection do Bundle.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.factions;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class Faction implements Bundlable {

	public String name    = "";
	public String role    = "";
	public boolean hostile = false;

	public Faction() {}

	public Faction(String name, String role, boolean hostile) {
		this.name    = name;
		this.role    = role;
		this.hostile = hostile;
	}

	private static final String NAME    = "name";
	private static final String ROLE    = "role";
	private static final String HOSTILE = "hostile";

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(NAME,    name);
		bundle.put(ROLE,    role);
		bundle.put(HOSTILE, hostile);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		name    = bundle.getString(NAME);
		role    = bundle.getString(ROLE);
		hostile = bundle.getBoolean(HOSTILE);
	}
}
