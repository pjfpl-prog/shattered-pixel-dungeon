package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Gnoll;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Swarm;

public class NarrativeChapter3 extends NarrativeChapter {

	{
		// Verde-musgo apodrecido.
		color1 = 0x2a3e1c;
		color2 = 0x4f7034;
	}

	@Override
	protected void createMobs() {
		super.createMobs();
		NarrativeSpawns.spawnExtra(this, new Swarm());
		NarrativeSpawns.spawnExtra(this, new Gnoll());
		NarrativeSpawns.spawnExtra(this, new Snake());
	}
}
