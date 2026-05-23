/*
 * Projeto Roguelike Narrativo Procedural — Fase 1 / Passo 5
 *
 * NpcLines gera a saudação narrativa única que cada NPC dá ao herói
 * na primeira interação. A frase referencia o estado atual do
 * AdventureSeed (objetivo, tom emocional, tema), de modo que o NPC
 * "pertença à história atual" como pede o briefing.
 *
 * Cada arquétipo tem várias linhas — sorteio determinístico via Random
 * (sem pushGenerator, herda o contexto do chamador: o RNG do gameplay
 * vai variar a fala sem afetar reprodutibilidade do mundo).
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.dialogue;

import com.shatteredpixel.shatteredpixeldungeon.narrative.NarrativeDirector;
import com.watabou.utils.Random;

public final class NpcLines {

	private NpcLines() {}

	public static String greet(NpcKind kind) {
		String quest = NarrativeDirector.mainQuest();
		String tone  = NarrativeDirector.emotionalTone();
		String theme = NarrativeDirector.dungeonTheme();

		String questFragment = quest.isEmpty()
				? "o que procura"
				: lowerFirst(quest);

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
					tone.substring(0, 1).toUpperCase() + tone.substring(1)
							+ "? Adorei. Você vai longe — pra baixo, claro.",
					"Sabe o que dizem de " + theme + "? Eu sei. Mas vai te custar."
				});
			default:
				return "...";
		}
	}

	private static String lowerFirst(String s) {
		if (s == null || s.isEmpty()) return s;
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}
}
