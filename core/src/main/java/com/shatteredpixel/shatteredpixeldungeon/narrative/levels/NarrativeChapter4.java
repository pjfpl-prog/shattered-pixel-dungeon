package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Gnoll;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Swarm;

public class NarrativeChapter4 extends NarrativeChapter {

	{
		// Azul-gelo profundo.
		color1 = 0x1a3a4f;
		color2 = 0x3879a0;
	}

	@Override
	protected void createMobs() {
		super.createMobs();
		NarrativeSpawns.spawnExtra(this, new Gnoll());
		NarrativeSpawns.spawnExtra(this, new Gnoll());
		NarrativeSpawns.spawnExtra(this, new Swarm());
		NarrativeSpawns.spawnExtra(this, new Swarm());
	}
}
