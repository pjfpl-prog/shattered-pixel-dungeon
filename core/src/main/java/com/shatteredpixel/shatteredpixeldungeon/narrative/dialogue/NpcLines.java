/*
 * Projeto Roguelike Narrativo Procedural — Fase 1 / Passo 5
 *
 * NpcLines gera a saudação narrativa única que cada NPC dá ao herói
 * na primeira interação. A frase referencia o estado atual do
 * AdventureSeed (objetivo, tom, tema) E o NpcState (motivação,
 * afiliação, atitude) do NPC, de modo que o NPC "pertença à história
 * atual" e tenha uma voz distinta na run.
 *
 * Sorteio determinístico via Random (herda contexto do chamador: o
 * RNG do gameplay vai variar a fala sem afetar reprodutibilidade
 * do mundo).
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.dialogue;

import com.shatteredpixel.shatteredpixeldungeon.narrative.NarrativeDirector;
import com.shatteredpixel.shatteredpixeldungeon.narrative.factions.Faction;
import com.shatteredpixel.shatteredpixeldungeon.narrative.npcs.NpcState;
import com.shatteredpixel.shatteredpixeldungeon.narrative.npcs.NpcState.Attitude;
import com.shatteredpixel.shatteredpixeldungeon.narrative.util.PtBr;
import com.watabou.utils.Random;

public final class NpcLines {

	private NpcLines() {}

	public static String greet(NpcKind kind) {
		String quest = NarrativeDirector.mainQuest();
		String tone  = NarrativeDirector.emotionalTone();
		String theme = NarrativeDirector.dungeonTheme();
		NpcState state = NarrativeDirector.npcState(kind);

		String questFragment = quest.isEmpty() ? "o que procura" : PtBr.lowerFirst(quest);
		String motivation    = (state != null && state.motivation != null && !state.motivation.isEmpty())
				? state.motivation : "";
		String affiliation   = (state != null && state.affiliationFaction != null && !state.affiliationFaction.isEmpty())
				? state.affiliationFaction : "";
		Attitude attitude    = state != null ? state.attitude : Attitude.WARY;

		String base = baseLine(kind, questFragment, tone, theme);
		String tail = tailFromState(motivation, affiliation, attitude);

		return tail.isEmpty() ? base : base + " " + tail;
	}

	// Saudação base — neutra em relação ao estado.
	private static String baseLine(NpcKind kind, String questFragment, String tone, String theme) {
		switch (kind) {
			case GHOST:
				return Random.element(new String[]{
					"...você também veio por causa de " + questFragment + "? Eu falhei.",
					"Há " + tone + " neste lugar. Sinto desde antes de morrer.",
					"Conte-me, herói: quem ainda lembra de " + theme + "?",
					"Eu desci por " + questFragment + " também. Olhe onde parei.",
					"Não pise onde eu caí. O chão guarda " + tone + " que não some.",
					"Faz tanto tempo que ninguém fala comigo. Diga: lá fora, ainda há sol?",
					"Você tem o rosto de quem ainda acredita em " + questFragment + ". Tinha o meu.",
					"Cada um que passa carrega um pouco de " + tone + ". Você carrega muito.",
					"Eu morri sem terminar. Talvez por isso ainda esteja de pé. Talvez você termine por mim.",
					"Não tenha medo de mim, herói. Tenha medo de virar o que eu virei.",
					"Lembro de " + theme + " como quem lembra de um sonho que doeu. Você vai lembrar também.",
					"Se um dia subir, diga meu nome em voz alta lá em cima. É só o que peço."
				});
			case WANDMAKER:
				return Random.element(new String[]{
					"Outro aventureiro tentando " + questFragment + ". Você não é o primeiro.",
					"Se for sobreviver, lembre-se: este lugar respira " + tone + ".",
					"Diga que veio por causa de " + theme + " e eu te escuto.",
					"Cuidado com o que pede aqui embaixo. " + PtBr.cap(theme) + " responde — do jeito errado.",
					"Eu vendo conhecimento, não esperança. " + PtBr.cap(tone) + " é de graça, infelizmente.",
					"Tem coisas neste lugar mais velhas que " + theme + ". Eu aprendi a não acordá-las.",
					"Você quer " + questFragment + "? Eu quero entender por quê. Comece por aí.",
					"Já catalogou o medo de hoje? Aqui, " + tone + " vem em doses. Anote as suas.",
					"Não confie em portas que se abrem fácil. Confie nas que resistem.",
					"Trago e levo segredos. O seu sobre " + questFragment + " parece pesado. Posso aliviá-lo — por um preço.",
					"Há um padrão em tudo isto, se você olhar de longe. " + PtBr.cap(theme) + " é só uma página.",
					"Sobrevivi mais que os corajosos. Saiba o que isso diz sobre coragem."
				});
			case BLACKSMITH:
				return Random.element(new String[]{
					"Não me incomode com " + questFragment + ". Tenho meu próprio peso.",
					"Você cheira a " + tone + ". Mantenha distância da minha forja.",
					"Já vi muitos chegarem por causa de " + theme + ". Poucos saíram.",
					"Aço honesto não mente como " + questFragment + " mente. Traga-me metal, não promessas.",
					"O fogo daqui é teimoso, igual a mim. Some pra antes que eu te ache útil pra martelo.",
					"Toda lâmina que forjei desceu e não voltou. " + PtBr.cap(theme) + " come ferro e gente igual.",
					"Você fala muito pra quem ainda não sangrou de verdade. Volte quando " + tone + " for sua.",
					"Conserto o que quebra, menos gente. Gente que desce já vem rachada.",
					"Tem fome no seu olhar. Fome é boa pra forja, péssima pra herói.",
					"Não toque na bigorna. É a única coisa neste buraco que ainda obedece a mim.",
					"Já bati metal escutando " + theme + " no escuro. As marteladas abafam, mas não calam.",
					"Quer um conselho de graça? Aqui não tem nada de graça. Esse foi o conselho."
				});
			case IMP:
				return Random.element(new String[]{
					"Ahá! Mais um curioso atrás de " + questFragment + ". Que delícia.",
					PtBr.cap(tone) + "? Adorei. Você vai longe — pra baixo, claro.",
					"Sabe o que dizem de " + theme + "? Eu sei. Mas vai te custar.",
					"Aventureiro! Posso te vender um atalho, um segredo, ou uma má ideia. Sortudo, hoje tem os três.",
					"Você quer " + questFragment + ", eu quero me divertir. Vamos nos dar tão bem.",
					"Adoro o cheiro de " + tone + " de manhã. Quer dizer, aqui não tem manhã. Detalhes!",
					"Já apostei com mortos mais espertos que você. Perderam. Vai ser divertidíssimo.",
					"Todo mundo pergunta de " + theme + ". Ninguém pergunta de mim. Que rude, não?",
					"Te dou uma dica sobre " + questFragment + " de graça — a primeira é sempre de graça, hehe.",
					"Você desce, eu observo, alguém chora. Geralmente é você. Mas eu torço, prometo!",
					"Que tal um trato? Você finge que não me viu, e eu finjo que não anotei seu nome.",
					"Pobre coisinha viva. Tão cheia de planos. " + PtBr.cap(theme) + " também tinha planos pra você."
				});
			default:
				return "...";
		}
	}

	// Cauda contextual: 1-2 frases curtas que expõem motivação/afiliação/atitude.
	// Vazia em ~30% dos casos pra evitar verbosidade.
	private static String tailFromState(String motivation, String affiliation, Attitude attitude) {
		if (motivation.isEmpty() && affiliation.isEmpty()) return "";
		if (Random.Int(10) < 3) return "";

		StringBuilder sb = new StringBuilder();

		if (!motivation.isEmpty() && Random.Int(2) == 0) {
			sb.append("Dizem que ").append(motivation).append(".");
		}

		if (!affiliation.isEmpty()) {
			if (sb.length() > 0) sb.append(" ");
			Faction f = findFaction(affiliation);
			String articledCap   = f != null ? f.articledName(true)  : "Os " + affiliation;
			String articledLower = f != null ? f.articledName(false) : "os " + affiliation;
			switch (attitude) {
				case FRIENDLY:
					sb.append("Eu sirvo ").append(prefixWithA(articledLower, f))
							.append(", se isso ajuda.");
					break;
				case HOSTILE:
					sb.append("Não me confunda com ")
							.append(prefixWithCom(articledLower, f))
							.append(" — são eles que mandam aqui.");
					break;
				case WARY:
				default:
					sb.append(articledCap).append(" ").append(verbVigiar(f))
							.append(" estas passagens.");
					break;
			}
		}

		return sb.toString();
	}

	// Localiza a Faction completa pelo nome (pra ter acesso a gênero/número).
	private static Faction findFaction(String name) {
		for (Faction f : NarrativeDirector.factions()) {
			if (name.equals(f.name)) return f;
		}
		return null;
	}

	// "ao Círculo" / "à Voz Coletiva" / "aos Monges" / "às Cinzas".
	// Recebe já no formato "<artigo> <nome>" em minúsculo.
	private static String prefixWithA(String articledLower, Faction f) {
		if (f == null) return PtBr.contractA(articledLower);
		return PtBr.contractA(articledLower);
	}

	// "com os Monges" / "com a Ordem". Sem contração — só prepende.
	private static String prefixWithCom(String articledLower, Faction f) {
		return "com " + articledLower;
	}

	private static String verbVigiar(Faction f) {
		return (f != null && f.plural) ? "vigiam" : "vigia";
	}
}
