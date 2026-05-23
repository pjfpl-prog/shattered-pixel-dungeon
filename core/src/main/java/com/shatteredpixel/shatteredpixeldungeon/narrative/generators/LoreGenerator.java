/*
 * Projeto Roguelike Narrativo Procedural — Fase 1 / Passo 4
 *
 * LoreGenerator monta 4-6 fragmentos de lore distribuídos pelos pisos 2..24.
 *
 * Cada fragmento combina:
 *   - um tipo (BOOK/NOTE/INSCRIPTION/PROPHECY/WARNING)
 *   - um corpo gerado por template ponderado
 *
 * Os pools de símbolos repetem propositalmente os do TitleGenerator —
 * a coerência inter-geradores é o que faz a run parecer um mundo, não
 * uma sopa aleatória. Quando o sistema crescer, esses pools migram para
 * uma camada vocab/ compartilhada.
 *
 * Templates evitam concordância de gênero variável: subjects e verbos
 * são escolhidos para serem neutros em relação ao gênero dos pools.
 * Contrações de preposição+artigo vêm de PtBr.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.generators;

import com.shatteredpixel.shatteredpixeldungeon.narrative.lore.LoreFragment;
import com.shatteredpixel.shatteredpixeldungeon.narrative.lore.LoreFragment.Kind;
import com.shatteredpixel.shatteredpixeldungeon.narrative.util.PtBr;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class LoreGenerator {

	private LoreGenerator() {}

	// Pools — propositalmente intersectam com TitleGenerator/QuestChainGenerator.
	// Já articulados em minúsculo (padrão PtBr).
	private static final String[] OWNERS = {
		"o Rei Cinza", "o Sacerdote Insone", "a Mente Raiz", "o Santo Oco",
		"o Arquivista Ósseo", "o Monge do Véu", "o Coveiro Antigo",
		"o Profeta Mudo", "o Tecelão dos Ossos"
	};

	private static final String[] PLACES = {
		"as Catacumbas", "o Abismo", "a Capela", "a Necrópole",
		"o Santuário", "a Forja", "a Biblioteca", "a Cripta"
	};

	private static final String[] RELICS = {
		"a Coroa", "o Sino", "o Espelho", "o Selo", "o Pacto",
		"o Sudário", "o Cálice", "o Lamento", "o Voto", "o Sussurro"
	};

	// Atos em pretérito perfeito, 3ª pessoa do singular — encaixam após "Depois,".
	private static final String[] ACTS = {
		"queimou tudo", "selou as portas", "fugiu pelo oeste",
		"se enterrou vivo", "rasgou as próprias páginas", "desceu sozinho"
	};

	// Imperativos negativos — encaixam como aviso isolado seguido de lugar.
	private static final String[] WARNINGS = {
		"Não escute", "Não olhe para trás", "Não toque nas paredes",
		"Não responda quando chamarem", "Feche os olhos", "Não durma"
	};

	// Verbos no futuro 3ª pessoa — encaixam após "<owner> ".
	private static final String[] PROPHECY_VERBS = {
		"voltará", "será libertado", "vai despertar", "encontrará o caminho",
		"fechará o círculo"
	};

	public static List<LoreFragment> generate() {
		ArrayList<LoreFragment> out = new ArrayList<>();

		int count = 4 + Random.Int(3); // 4..6 fragmentos

		ArrayList<Integer> depths = new ArrayList<>();
		for (int d = 2; d <= 24; d++) depths.add(d);
		java.util.Collections.shuffle(depths, new java.util.Random(Random.Long()));

		for (int i = 0; i < count && i < depths.size(); i++) {
			Kind kind = Random.element(Kind.values());
			out.add(new LoreFragment(kind, renderBody(kind), depths.get(i)));
		}

		out.sort((a, b) -> Integer.compare(a.triggerDepth, b.triggerDepth));

		HashSet<String> seen = new HashSet<>();
		ArrayList<LoreFragment> deduped = new ArrayList<>();
		for (LoreFragment f : out) {
			if (seen.add(f.body)) deduped.add(f);
		}
		return deduped;
	}

	private static String renderBody(Kind kind) {
		switch (kind) {
			case BOOK:
				// "O Rei Cinza escreveu sobre a Coroa. Depois, queimou tudo."
				return PtBr.cap(Random.element(OWNERS)) + " escreveu sobre "
						+ Random.element(RELICS) + ". Depois, "
						+ Random.element(ACTS) + ".";

			case NOTE:
				// "Aviso ao próximo: o Sacerdote Insone esteve aqui."
				return "Aviso ao próximo: " + Random.element(OWNERS) + " esteve aqui.";

			case INSCRIPTION:
				// "Aqui esconderam a Coroa. Não confie em ninguém."
				return "Aqui esconderam " + Random.element(RELICS) + ". Não confie em ninguém.";

			case PROPHECY:
				// "Quando tocarem a Coroa, o Rei Cinza voltará."
				return "Quando tocarem " + Random.element(RELICS) + ", "
						+ Random.element(OWNERS) + " " + Random.element(PROPHECY_VERBS) + ".";

			case WARNING:
				// "Não escute nas Catacumbas." (contractEm corrige "em as" → "nas")
				return Random.element(WARNINGS) + " " + PtBr.contractEm(Random.element(PLACES)) + ".";

			default:
				return "Silêncio.";
		}
	}
}
