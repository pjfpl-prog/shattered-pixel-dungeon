/*
 * Projeto Roguelike Narrativo Procedural — Fase 1 / Passo 2
 *
 * DungeonThemeGenerator escolhe o arquétipo da aventura.
 * Os temas vêm direto dos exemplos do briefing — pool fixo,
 * sorteio uniforme. A coerência simbólica fica nas frases
 * dos demais geradores que vão se alinhar ao tema escolhido.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.generators;

import com.watabou.utils.Random;

public final class DungeonThemeGenerator {

	private DungeonThemeGenerator() {}

	public static final String[] THEMES = {
		"Reino Caído",
		"Ritual de Invocação",
		"Despertar de Máquina Antiga",
		"Dungeon da Praga",
		"Sonho Corrompido",
		"Catacumbas em Loop Temporal",
		"Expedição Esquecida",
		"Colmeia Parasita",
		"Guerra Civil dos Necromantes",
		"Profecia Interrompida",
		"Santuário Afogado"
	};

	public static String generate() {
		return Random.element(THEMES);
	}
}
