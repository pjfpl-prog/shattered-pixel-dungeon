/*
 * Projeto Roguelike Narrativo Procedural — Fase 1
 *
 * Uma facção da aventura atual.
 *
 * Campos gramaticais (gender + plural) permitem que os geradores
 * de fala montem o artigo certo na hora de mencionar a facção
 * (ex.: "A Ordem das Cinzas" vs "Os Monges do Véu").
 *
 * Bundlable + ctor sem args para Reflection do Bundle.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.factions;

import com.shatteredpixel.shatteredpixeldungeon.narrative.util.PtBr;
import com.shatteredpixel.shatteredpixeldungeon.narrative.util.PtBr.Gender;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class Faction implements Bundlable {

	public String name     = "";
	public String role     = "";
	public boolean hostile = false;
	public Gender gender   = Gender.F;
	public boolean plural  = true;

	public Faction() {}

	public Faction(String name, String role, boolean hostile, Gender gender, boolean plural) {
		this.name    = name;
		this.role    = role;
		this.hostile = hostile;
		this.gender  = gender;
		this.plural  = plural;
	}

	// Devolve "Os"/"As"/"O"/"A" + " " + name conforme gênero/número.
	public String articledName(boolean capitalized) {
		String art;
		if (plural) art = gender == Gender.F ? "As" : "Os";
		else        art = PtBr.article(gender);  // "A" / "O"
		if (!capitalized) art = art.toLowerCase();
		return art + " " + name;
	}

	private static final String NAME    = "name";
	private static final String ROLE    = "role";
	private static final String HOSTILE = "hostile";
	private static final String GENDER  = "gender";
	private static final String PLURAL  = "plural";

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(NAME,    name);
		bundle.put(ROLE,    role);
		bundle.put(HOSTILE, hostile);
		bundle.put(GENDER,  gender.name());
		bundle.put(PLURAL,  plural);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		name    = bundle.getString(NAME);
		role    = bundle.getString(ROLE);
		hostile = bundle.getBoolean(HOSTILE);
		try {
			gender = Gender.valueOf(bundle.getString(GENDER));
		} catch (IllegalArgumentException e) {
			gender = Gender.F;
		}
		// Saves antigos: PLURAL ausente → default false seria seguro,
		// mas o pool histórico era 90%+ plural, então default true.
		plural = bundle.contains(PLURAL) ? bundle.getBoolean(PLURAL) : true;
	}
}
