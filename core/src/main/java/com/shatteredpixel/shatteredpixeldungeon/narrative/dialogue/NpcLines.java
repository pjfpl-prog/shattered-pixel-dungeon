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
					"Conte-me, herói: quem ainda lembra de " + theme + "?"
				});
			case WANDMAKER:
				return Random.element(new String[]{
					"Outro aventureiro tentando " + questFragment + ". Você não é o primeiro.",
					"Se for sobreviver, lembre-se: este lugar respira " + tone + ".",
					"Diga que veio por causa de " + theme + " e eu te escuto."
				});
			case BLACKSMITH:
				return Random.element(new String[]{
					"Não me incomode com " + questFragment + ". Tenho meu próprio peso.",
					"Você cheira a " + tone + ". Mantenha distância da minha forja.",
					"Já vi muitos chegarem por causa de " + theme + ". Poucos saíram."
				});
			case IMP:
				return Random.element(new String[]{
					"Ahá! Mais um curioso atrás de " + questFragment + ". Que delícia.",
					PtBr.cap(tone) + "? Adorei. Você vai longe — pra baixo, claro.",
					"Sabe o que dizem de " + theme + "? Eu sei. Mas vai te custar."
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
			switch (attitude) {
				case FRIENDLY:
					sb.append("Eu sirvo aos ").append(affiliation).append(", se isso ajuda.");
					break;
				case HOSTILE:
					sb.append("Não me confunda com os ").append(affiliation).append(" — eles é que mandam aqui.");
					break;
				case WARY:
				default:
					sb.append("Os ").append(affiliation).append(" vigiam estas passagens.");
					break;
			}
		}

		return sb.toString();
	}
}
