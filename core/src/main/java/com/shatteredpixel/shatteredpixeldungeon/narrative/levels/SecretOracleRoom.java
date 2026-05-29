/*
 * Projeto Roguelike Narrativo Procedural — sala secreta com Oráculo
 *
 * Variante de SecretRoom específica da one-shot: spawn de um
 * EnigmaticOracle no centro, sem outras decorações que desviem o foco.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.secret.SecretRoom;
import com.shatteredpixel.shatteredpixeldungeon.narrative.npcs.EnigmaticOracle;
import com.watabou.utils.Point;

public class SecretOracleRoom extends SecretRoom {

	@Override
	public int minWidth()  { return 5; }
	@Override
	public int minHeight() { return 5; }

	@Override
	public void paint(Level level) {
		Painter.fill(level, this, Terrain.WALL);
		Painter.fill(level, this, 1, Terrain.EMPTY_SP);

		Point c = center();
		Painter.set(level, c, Terrain.PEDESTAL);

		EnigmaticOracle oracle = new EnigmaticOracle();
		oracle.pos = level.pointToCell(c);
		level.mobs.add(oracle);

		entrance().set(Door.Type.HIDDEN);
	}
}
