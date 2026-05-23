/*
 * Projeto Roguelike Narrativo Procedural — Fase 1 / Passo 2
 *
 * EmotionalToneGenerator escolhe o tom emocional da run.
 * Os tons vêm dos exemplos do briefing. Pool fixo, sorteio uniforme.
 *
 * Em passos futuros, o tom vai influenciar pesos dos demais
 * geradores (epítetos do título, vocabulário do lore, etc).
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.generators;

import com.watabou.utils.Random;

public final class EmotionalToneGenerator {

	private EmotionalToneGenerator() {}

	public static final String[] TONES = {
		"horror",
		"mistério",
		"tragédia",
		"ganância",
		"loucura",
		"esperança",
		"decadência",
		"paranoia"
	};

	public static String generate() {
		return Random.element(TONES);
	}
}
