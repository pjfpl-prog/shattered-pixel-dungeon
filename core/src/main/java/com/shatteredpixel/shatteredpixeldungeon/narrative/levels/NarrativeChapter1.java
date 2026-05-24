/*
 * Projeto Roguelike Narrativo Procedural — one-shot RPG mode
 *
 * Capítulo 1 da aventura semi-linear. Visual e tileset alterados
 * em relação ao SewerLevel padrão (mais vermelho/sangrento).
 * Spawna mobs extras pra criar "encontros chave" por piso.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.StandardRoom;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class NarrativeChapter1 extends SewerLevel {

	{
		// Vermelho-sangue: visual distinto do sewer verde padrão.
		color1 = 0x7c2a2a;
		color2 = 0xb84141;
	}

	@Override
	protected void createMobs() {
		super.createMobs();
		// Encontros-chave extras: 2 cobras extras em salas standard aleatórias.
		spawnExtraInStandardRoom(new Snake());
		spawnExtraInStandardRoom(new Snake());
	}

	protected void spawnExtraInStandardRoom(Mob mob) {
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
