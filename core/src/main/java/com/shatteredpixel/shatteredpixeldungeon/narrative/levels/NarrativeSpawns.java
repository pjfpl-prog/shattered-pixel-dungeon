/*
 * Projeto Roguelike Narrativo Procedural — one-shot RPG mode
 *
 * Helper compartilhado pra posicionar mobs "encontro-chave" extras em
 * salas standard dos NarrativeChapter*. Usa só API pública do Level.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.levels.RegularLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.StandardRoom;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;

public final class NarrativeSpawns {

	private NarrativeSpawns() {}

	// Posiciona o mob numa sala standard aleatória (não a de entrada) e o
	// adiciona à lista de mobs do nível. No-op se não achar célula válida.
	public static void spawnExtra(RegularLevel level, Mob mob) {
		Point entranceP = level.cellToPoint(level.entrance());
		ArrayList<Room> standards = new ArrayList<>();
		for (Room r : level.rooms()) {
			// Pula a sala de entrada (a que contém a célula de entrada).
			if (r instanceof StandardRoom && !r.inside(entranceP)) {
				standards.add(r);
			}
		}
		if (standards.isEmpty()) return;

		for (int tries = 0; tries < 30; tries++) {
			Room target = Random.element(standards);
			Point p = target.random();
			int pos = level.pointToCell(p);
			if (pos < 0 || pos >= level.length()) continue;
			if (!level.solid[pos] && level.passable[pos]
					&& level.findMob(pos) == null
					&& pos != level.entrance()
					&& pos != level.exit()) {
				mob.pos = pos;
				level.mobs.add(mob);
				return;
			}
		}
	}
}
