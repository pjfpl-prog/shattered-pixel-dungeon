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
		"o Profeta Mudo", "o Tecelão dos Ossos", "a Viúva de Osso",
		"a Mãe das Moscas", "o Cartógrafo Cego", "o Rei Mendigo",
		"o Deus Submerso", "o Sonhador Acordado", "a Costureira de Carne",
		"o Sacerdote Afogado", "o Doente Que Não Morre", "o Hospedeiro Original",
		"a Mente Coletiva"
	};

	private static final String[] PLACES = {
		"as Catacumbas", "o Abismo", "a Capela", "a Necrópole",
		"o Santuário", "a Forja", "a Biblioteca", "a Cripta",
		"o Trono Vazio", "o Salão Real", "a Colmeia", "o Jardim de Ossos",
		"a Cisterna", "o Confessionário", "a Forja Fria", "o Acampamento",
		"o Tear", "a Sala dos Espelhos"
	};

	private static final String[] RELICS = {
		"a Coroa", "o Sino", "o Espelho", "o Selo", "o Pacto",
		"o Sudário", "o Cálice", "o Lamento", "o Voto", "o Sussurro",
		"a Mortalha", "o Diário", "a Engrenagem", "a Lâmpada", "a Balança",
		"o Sino Afogado", "a Coroa de Pó", "a Última Página", "o Coração de Bronze"
	};

	// Atos em pretérito perfeito, 3ª pessoa do singular — encaixam após "Depois,".
	private static final String[] ACTS = {
		"queimou tudo", "selou as portas", "fugiu pelo oeste",
		"se enterrou vivo", "rasgou as próprias páginas", "desceu sozinho",
		"calou as mil bocas", "afundou de propósito", "costurou a própria boca",
		"parou o relógio", "regou os ossos até florirem", "abdicou do trono",
		"velou onze anos sem dormir", "desfez a trama inteira"
	};

	// Imperativos negativos — encaixam como aviso isolado seguido de lugar.
	private static final String[] WARNINGS = {
		"Não escute", "Não olhe para trás", "Não toque nas paredes",
		"Não responda quando chamarem", "Feche os olhos", "Não durma",
		"Não conte seus pecados", "Não abra a porta do seu tamanho",
		"Não responda ao coro", "Não confie no mapa", "Não regue o que cresce branco",
		"Não diga seu nome em voz alta"
	};

	// Verbos no futuro 3ª pessoa — encaixam após "<owner> ".
	private static final String[] PROPHECY_VERBS = {
		"voltará", "será libertado", "vai despertar", "encontrará o caminho",
		"fechará o círculo", "fechará os olhos enfim", "voltará coroado",
		"afogará o santuário", "calará o coro", "terminará a trama",
		"descerá por último", "lembrará de tudo"
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
				switch (Random.Int(3)) {
					case 0:
						// "O Rei Cinza escreveu sobre a Coroa. Depois, queimou tudo."
						return PtBr.cap(Random.element(OWNERS)) + " escreveu sobre "
								+ Random.element(RELICS) + ". Depois, "
								+ Random.element(ACTS) + ".";
					case 1:
						// "As últimas páginas do Rei Cinza falam da Coroa sem parar."
						return "As últimas páginas " + PtBr.contractDe(Random.element(OWNERS))
								+ " falam " + PtBr.contractDe(Random.element(RELICS)) + " sem parar.";
					default:
						// "O Rei Cinza dedicou este volume à Coroa."
						return PtBr.cap(Random.element(OWNERS)) + " dedicou este volume "
								+ PtBr.contractA(Random.element(RELICS)) + ".";
				}

			case NOTE:
				switch (Random.Int(3)) {
					case 0:
						// "Aviso ao próximo: o Sacerdote Insone esteve aqui."
						return "Aviso ao próximo: " + Random.element(OWNERS) + " esteve aqui.";
					case 1:
						// "Se você lê isto, o Sacerdote Insone já sabe que você chegou."
						return "Se você lê isto, " + Random.element(OWNERS)
								+ " já sabe que você chegou.";
					default:
						// "Não siga o Coveiro Antigo. Eu segui."
						return "Não siga " + Random.element(OWNERS) + ". Eu segui.";
				}

			case INSCRIPTION:
				switch (Random.Int(3)) {
					case 0:
						// "Aqui esconderam a Coroa. Não confie em ninguém."
						return "Aqui esconderam " + Random.element(RELICS) + ". Não confie em ninguém.";
					case 1:
						// "Esta pedra pertence ao Rei Cinza."
						return "Esta pedra pertence " + PtBr.contractA(Random.element(OWNERS)) + ".";
					default:
						// "A Coroa repousa nas Catacumbas. Que assim permaneça."
						return PtBr.cap(Random.element(RELICS)) + " repousa "
								+ PtBr.contractEm(Random.element(PLACES)) + ". Que assim permaneça.";
				}

			case PROPHECY:
				switch (Random.Int(3)) {
					case 0:
						// "Quando tocarem a Coroa, o Rei Cinza voltará."
						return "Quando tocarem " + Random.element(RELICS) + ", "
								+ Random.element(OWNERS) + " " + Random.element(PROPHECY_VERBS) + ".";
					case 1:
						// "No fim de tudo, o Rei Cinza voltará."
						return "No fim de tudo, " + Random.element(OWNERS) + " "
								+ Random.element(PROPHECY_VERBS) + ".";
					default:
						// "Está escrito: o Rei Cinza voltará, e ninguém poderá impedir."
						return "Está escrito: " + Random.element(OWNERS) + " "
								+ Random.element(PROPHECY_VERBS) + ", e ninguém poderá impedir.";
				}

			case WARNING:
				switch (Random.Int(3)) {
					case 0:
						// "Não escute nas Catacumbas." (contractEm corrige "em as" → "nas")
						return Random.element(WARNINGS) + " " + PtBr.contractEm(Random.element(PLACES)) + ".";
					case 1:
						// "Não durma. Foi assim que o Rei Cinza se perdeu."
						return Random.element(WARNINGS) + ". Foi assim que "
								+ Random.element(OWNERS) + " se perdeu.";
					default:
						// "Não durma perto da Coroa."
						return Random.element(WARNINGS) + " perto "
								+ PtBr.contractDe(Random.element(RELICS)) + ".";
				}

			default:
				return "Silêncio.";
		}
	}
}
