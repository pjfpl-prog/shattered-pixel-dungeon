/*
 * Projeto Roguelike Narrativo Procedural — sistema de eventos
 *
 * Distribui eventos do EventBank ao longo dos pisos 1..25 para a run.
 *
 * Garantia: pelo menos 1 evento por piso onde houver candidatos.
 * ~30% chance de o piso ter 2 eventos em vez de 1.
 *
 * Não repete eventos dentro da mesma run; se o pool se esgotar antes
 * de cobrir todos os pisos, abre-se uma 2ª passada reutilizando eventos
 * (de pisos diferentes) para garantir cobertura.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.events;

import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class EventScheduleGenerator {

	private EventScheduleGenerator() {}

	private static final int MIN_DEPTH = 1;
	private static final int MAX_DEPTH = 25;
	private static final int SECOND_EVENT_PCT = 30;

	public static Map<Integer, List<EventInstance>> generate(String theme, String tone) {
		HashMap<Integer, List<EventInstance>> schedule = new HashMap<>();
		HashSet<String> usedIds = new HashSet<>();

		for (int d = MIN_DEPTH; d <= MAX_DEPTH; d++) {
			ArrayList<EventInstance> list = new ArrayList<>();

			NarrativeEvent first = pick(d, theme, tone, usedIds);
			if (first != null) {
				usedIds.add(first.id);
				list.add(new EventInstance(first.id, d));

				if (Random.Int(100) < SECOND_EVENT_PCT) {
					NarrativeEvent second = pick(d, theme, tone, usedIds);
					if (second != null) {
						usedIds.add(second.id);
						list.add(new EventInstance(second.id, d));
					}
				}
			}

			if (!list.isEmpty()) schedule.put(d, list);
		}

		return schedule;
	}

	private static NarrativeEvent pick(int depth, String theme, String tone, HashSet<String> used) {
		List<NarrativeEvent> candidates = EventBank.candidatesFor(depth, theme, tone);
		// Filtra já usados.
		ArrayList<NarrativeEvent> available = new ArrayList<>();
		for (NarrativeEvent e : candidates) {
			if (!used.contains(e.id)) available.add(e);
		}

		// Se nenhum match estrito, relaxar: qualquer evento do pool que aceite o depth.
		if (available.isEmpty()) {
			for (NarrativeEvent e : EventBank.all()) {
				if (e.acceptsDepth(depth) && !used.contains(e.id)) {
					available.add(e);
				}
			}
		}

		// Última tentativa: ignorar "used" pra garantir cobertura.
		if (available.isEmpty()) {
			for (NarrativeEvent e : EventBank.all()) {
				if (e.acceptsDepth(depth)) available.add(e);
			}
		}

		if (available.isEmpty()) return null;
		return Random.element(available);
	}
}
