/*
 * Projeto Roguelike Narrativo Procedural — Fase 1 / Passo 3
 *
 * QuestChainGenerator monta a cadeia narrativa principal:
 *   3-5 passos compostos por (verbo + objeto).
 *
 * Cada cadeia segue o arco do briefing: investigação → confronto → resolução.
 * O último step é o confronto final com a identidade do boss (placeholder
 * neutro nesta fase; o Passo de boss vai substituir).
 *
 * Pools são fixos. Cada step da cadeia escolhe verbo+objeto sem repetir
 * combinações no mesmo arco.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.generators;

import com.shatteredpixel.shatteredpixeldungeon.narrative.quests.QuestStep;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class QuestChainGenerator {

	private QuestChainGenerator() {}

	private static final String[] OPENING_VERBS = {
		"Recuperar", "Encontrar", "Decifrar", "Resgatar"
	};

	private static final String[] MIDDLE_VERBS = {
		"Purificar", "Selar", "Reunir", "Restaurar", "Banir"
	};

	private static final String[] CLIMAX_VERBS = {
		"Eliminar", "Confrontar", "Despertar", "Quebrar"
	};

	private static final String[] OBJECTS = {
		"a relíquia perdida",
		"o altar amaldiçoado",
		"o guardião corrompido",
		"o selo quebrado",
		"o explorador desaparecido",
		"o grimório proibido",
		"o sino primordial",
		"a máquina antiga",
		"o sacerdote insone",
		"o sussurro do abismo",
		"a coroa enterrada",
		"o pacto esquecido"
	};

	public static List<QuestStep> generate() {
		ArrayList<QuestStep> chain = new ArrayList<>();
		HashSet<String> usedObjects = new HashSet<>();

		int length = 3 + Random.Int(3); // 3..5

		for (int i = 0; i < length; i++) {
			String verb = pickVerbForPosition(i, length);
			String obj  = pickUniqueObject(usedObjects);
			chain.add(new QuestStep(verb + " " + obj));
		}

		return chain;
	}

	private static String pickVerbForPosition(int index, int length) {
		if (index == 0) {
			return Random.element(OPENING_VERBS);
		}
		if (index == length - 1) {
			return Random.element(CLIMAX_VERBS);
		}
		return Random.element(MIDDLE_VERBS);
	}

	private static String pickUniqueObject(HashSet<String> used) {
		// Pequena loop de rejeição. Pool é folgado (12 itens, máx 5 picks).
		for (int tries = 0; tries < 10; tries++) {
			String candidate = Random.element(OBJECTS);
			if (!used.contains(candidate)) {
				used.add(candidate);
				return candidate;
			}
		}
		// Fallback impossível na prática.
		return Random.element(OBJECTS);
	}
}
