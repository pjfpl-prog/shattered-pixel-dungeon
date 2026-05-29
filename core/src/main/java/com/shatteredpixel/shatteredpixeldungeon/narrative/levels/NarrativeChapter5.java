package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Gnoll;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Swarm;

public class NarrativeChapter5 extends NarrativeChapter {

	{
		// Roxo-magenta sobrenatural.
		color1 = 0x55205b;
		color2 = 0x9a3fb0;
	}

	@Override
	protected void createMobs() {
		super.createMobs();
		NarrativeSpawns.spawnExtra(this, new Gnoll());
		NarrativeSpawns.spawnExtra(this, new Gnoll());
		NarrativeSpawns.spawnExtra(this, new Swarm());
		NarrativeSpawns.spawnExtra(this, new Swarm());
		NarrativeSpawns.spawnExtra(this, new Swarm());
	}
}
