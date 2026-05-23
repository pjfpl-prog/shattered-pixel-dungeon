/*
 * Projeto Roguelike Narrativo Procedural — Fase 1
 *
 * TitleGenerator monta o título da aventura combinando peças simbólicas
 * em estruturas gramaticais reutilizáveis. Cada peça é escolhida via
 * Random.element(), portanto o título é determinístico para uma dada seed.
 *
 * Princípio (do briefing): NÃO gerar texto totalmente aleatório.
 * Usar pools temáticos + templates ponderados.
 *
 * Concordância: cada substantivo carrega gênero e número (plural) para
 * que os templates apliquem artigo correto e contrações via PtBr.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.generators;

import com.shatteredpixel.shatteredpixeldungeon.narrative.util.PtBr;
import com.shatteredpixel.shatteredpixeldungeon.narrative.util.PtBr.Gender;
import com.watabou.utils.Random;

public final class TitleGenerator {

	private TitleGenerator() {}

	private static final class Word {
		final String name;
		final Gender gender;
		final boolean plural;
		Word(String name, Gender gender)                  { this(name, gender, false); }
		Word(String name, Gender gender, boolean plural)  { this.name = name; this.gender = gender; this.plural = plural; }
	}

	private static final Word[] RELICS = {
		new Word("Coroa",      Gender.F),
		new Word("Sino",       Gender.M),
		new Word("Espelho",    Gender.M),
		new Word("Selo",       Gender.M),
		new Word("Pacto",      Gender.M),
		new Word("Sudário",    Gender.M),
		new Word("Cálice",     Gender.M),
		new Word("Lamento",    Gender.M),
		new Word("Voto",       Gender.M),
		new Word("Sussurro",   Gender.M),
		new Word("Sonho",      Gender.M),
		new Word("Trono",      Gender.M),
		new Word("Sepultura",  Gender.F),
		new Word("Profecia",   Gender.F),
		new Word("Canção",     Gender.F),
		new Word("Espinho",    Gender.M),
		new Word("Vigília",    Gender.F)
	};

	// Pares (masc, fem). Epítetos "comum-de-dois" (mesma forma) repetem.
	private static final String[][] EPITHET_PAIRS = {
		{"Caído",        "Caída"},
		{"Quebrado",     "Quebrada"},
		{"Esquecido",    "Esquecida"},
		{"Insone",       "Insone"},
		{"Afogado",      "Afogada"},
		{"Cego",         "Cega"},
		{"Tardio",       "Tardia"},
		{"Vazio",        "Vazia"},
		{"Soterrado",    "Soterrada"},
		{"Inacabado",    "Inacabada"},
		{"Apodrecido",   "Apodrecida"},
		{"Silencioso",   "Silenciosa"},
		{"Sangrento",    "Sangrenta"},
		{"Petrificado",  "Petrificada"}
	};

	private static final Word[] PLACES = {
		new Word("Catacumbas",   Gender.F, true),
		new Word("Abismo",       Gender.M),
		new Word("Capela",       Gender.F),
		new Word("Necrópole",    Gender.F),
		new Word("Santuário",    Gender.M),
		new Word("Forja",        Gender.F),
		new Word("Biblioteca",   Gender.F),
		new Word("Cripta",       Gender.F),
		new Word("Jardim",       Gender.M),
		new Word("Observatório", Gender.M)
	};

	private static final Word[] ACTS = {
		new Word("Despertar",     Gender.M),
		new Word("Queda",         Gender.F),
		new Word("Vigília",       Gender.F),
		new Word("Retorno",       Gender.M),
		new Word("Selamento",     Gender.M),
		new Word("Confissão",     Gender.F),
		new Word("Procissão",     Gender.F),
		new Word("Última Ronda",  Gender.F)
	};

	// OWNERS já vêm articulados em minúsculo (padrão exigido por PtBr.contract*).
	private static final String[] OWNERS = {
		"o Rei Cinza", "o Sacerdote Insone", "a Mente Raiz", "o Santo Oco",
		"o Arquivista Ósseo", "o Monge do Véu", "o Coveiro Antigo",
		"o Profeta Mudo", "o Tecelão dos Ossos"
	};

	private enum Template {
		RELIC_EPITHET,       // "A Coroa Caída"           — relic + epíteto concordante
		RELIC_OF_OWNER,      // "O Sussurro do Rei Cinza" — relic + de + owner
		ACT_OF_PLACE,        // "A Queda das Catacumbas"  — ato + de + lugar
		PLACE_OF_RELIC,      // "As Catacumbas do Sino"   — lugar + de + relic
		RELIC_OF_PLACE       // "O Sino do Abismo"        — relic + de + lugar
	}

	public static String generate() {
		Template t = Random.element(Template.values());
		switch (t) {
			case RELIC_EPITHET: {
				Word relic = Random.element(RELICS);
				return article(relic) + " " + relic.name + " " + pickEpithet(relic.gender);
			}
			case RELIC_OF_OWNER: {
				Word relic = Random.element(RELICS);
				String owner = Random.element(OWNERS);
				return article(relic) + " " + relic.name + " " + PtBr.contractDe(owner);
			}
			case ACT_OF_PLACE: {
				Word act   = Random.element(ACTS);
				Word place = Random.element(PLACES);
				return article(act) + " " + act.name + " " + PtBr.contractDe(articulated(place));
			}
			case PLACE_OF_RELIC: {
				Word place = Random.element(PLACES);
				Word relic = Random.element(RELICS);
				return article(place) + " " + place.name + " " + PtBr.contractDe(articulated(relic));
			}
			case RELIC_OF_PLACE: {
				Word relic = Random.element(RELICS);
				Word place = Random.element(PLACES);
				return article(relic) + " " + relic.name + " " + PtBr.contractDe(articulated(place));
			}
			default:
				return "A Expedição Sem Nome";
		}
	}

	private static String article(Word w) {
		if (w.plural) return w.gender == Gender.F ? "As" : "Os";
		return PtBr.article(w.gender);
	}

	private static String lcArticle(Word w) {
		if (w.plural) return w.gender == Gender.F ? "as" : "os";
		return w.gender == Gender.F ? "a" : "o";
	}

	private static String articulated(Word w) {
		return lcArticle(w) + " " + w.name;
	}

	private static String pickEpithet(Gender g) {
		String[] pair = Random.element(EPITHET_PAIRS);
		return g == Gender.F ? pair[1] : pair[0];
	}
}
