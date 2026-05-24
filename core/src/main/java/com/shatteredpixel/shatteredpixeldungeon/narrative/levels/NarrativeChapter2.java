/*
 * Projeto Roguelike Narrativo Procedural — one-shot RPG mode
 *
 * Capítulo 2: visual de prisão (tileset PRISON) com paleta amarelada.
 * Mais inimigos pra criar tensão crescente.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Gnoll;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.StandardRoom;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class NarrativeChapter2 extends SewerLevel {

	{
		// Roxo escuro — tom mais opressivo.
		color1 = 0x3a2d52;
		color2 = 0x6b4e95;
	}

	@Override
	protected void createMobs() {
		super.createMobs();
		spawnExtra(new Gnoll());
		spawnExtra(new Gnoll());
		spawnExtra(new Snake());
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
