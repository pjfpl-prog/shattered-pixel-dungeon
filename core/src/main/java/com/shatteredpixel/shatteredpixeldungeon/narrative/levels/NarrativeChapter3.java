/*
 * Projeto Roguelike Narrativo Procedural — one-shot RPG mode
 *
 * Capítulo 3: visual de cavernas, paleta verde-musgo escura.
 * Setup do confronto final — mobs em maior densidade.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Gnoll;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Swarm;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.StandardRoom;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class NarrativeChapter3 extends SewerLevel {

	{
		// Verde-musgo profundo — caverna apodrecida.
		color1 = 0x2a3e1c;
		color2 = 0x4f7034;
	}

	@Override
	protected void createMobs() {
		super.createMobs();
		spawnExtra(new Swarm());
		spawnExtra(new Gnoll());
		spawnExtra(new Gnoll());
		spawnExtra(new Gnoll());
	}

	protected void spawnExtra(Mob mob) {
		for (int tries = 0; tries < 30; tries++) {
			Room target = null;
			for (Room r : rooms) {
				if (r instanceof StandardRoom && r != roomEntrance) {
					target = r;
					if (Random.Int(rooms.size()) == 0) break;
				}
			}
			if (target == null) return;
			Point p = target.random();
			int pos = pointToCell(p);
			if (!solid[pos] && passable[pos] && findMob(pos) == null && pos != entrance() && pos != exit()) {
				mob.pos = pos;
				mobs.add(mob);
				return;
			}
		}
	}
}
