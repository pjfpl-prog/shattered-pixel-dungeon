/*
 * Projeto Roguelike Narrativo Procedural — escolha de retrato do boss
 *
 * Mapeia bossIdentity → caminho do PNG em assets/narrative/.
 * Heurística simples por palavras-chave na identidade — extensível
 * adicionando entradas em MAPPINGS.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.util;

public final class BossPortraits {

	private BossPortraits() {}

	private static final String[][] MAPPINGS = {
		// {palavra-chave (lowercase), path do retrato 64x64}
		{"profeta",   "narrative/boss-profeta-mudo_portrait.png"},
		{"profecia",  "narrative/boss-profeta-mudo_portrait.png"},
		{"página",    "narrative/boss-profeta-mudo_portrait.png"},
		{"sino",      "narrative/boss-profeta-mudo_portrait.png"},
		{"coveiro",   "narrative/boss-coveiro-antigo_portrait.png"},
		{"cripta",    "narrative/boss-coveiro-antigo_portrait.png"},
		{"voltam",    "narrative/boss-coveiro-antigo_portrait.png"},
		{"eu mesmo",  "narrative/boss-coveiro-antigo_portrait.png"},
		{"mente",     "narrative/boss-mente-raiz_portrait.png"},
		{"raiz",      "narrative/boss-mente-raiz_portrait.png"},
		{"coletiva",  "narrative/boss-mente-raiz_portrait.png"},
		{"hospedeiro","narrative/boss-mente-raiz_portrait.png"},
		{"santo",     "narrative/boss-santo-oco_portrait.png"},
		{"afogado",   "narrative/boss-santo-oco_portrait.png"},
		{"convocado", "narrative/boss-santo-oco_portrait.png"},
		{"insone",    "narrative/boss-sacerdote-insone_portrait.png"},
		{"sacerdote", "narrative/boss-sacerdote-insone_portrait.png"},
		{"praga",     "narrative/boss-mae-das-moscas_portrait.png"},
		{"mãe",       "narrative/boss-mae-das-moscas_portrait.png"},
		{"moscas",    "narrative/boss-mae-das-moscas_portrait.png"},
		{"doente",    "narrative/boss-mae-das-moscas_portrait.png"},
		{"apodrecido","narrative/boss-mae-das-moscas_portrait.png"},
		// Default cobre Rei Cinza, Coroa, Trono Vazio, etc.
	};

	private static final String DEFAULT_PATH = "narrative/boss-rei-cinza_portrait.png";

	public static String pathFor(String bossIdentity) {
		if (bossIdentity == null || bossIdentity.isEmpty()) return DEFAULT_PATH;
		String id = bossIdentity.toLowerCase();
		for (String[] m : MAPPINGS) {
			if (id.contains(m[0])) return m[1];
		}
		return DEFAULT_PATH;
	}
}
