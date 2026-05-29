/*
 * Projeto Roguelike Narrativo Procedural — one-shot RPG mode
 *
 * Capítulo 2: visual roxo escuro + densidade maior de inimigos
 * (gnolls + cobra) pra escalar a tensão.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Gnoll;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;
import com.watabou.noosa.audio.Music;

public class NarrativeChapter2 extends NarrativeChapter {

	{
		color1 = 0x3a2d52;
		color2 = 0x6b4e95;
	}

	@Override
	public void playLevelMusic() {
		Music.INSTANCE.play(Assets.Music.PRISON_1, true);
	}

	@Override
	protected void createMobs() {
		super.createMobs();
		NarrativeSpawns.spawnExtra(this, new Gnoll());
		NarrativeSpawns.spawnExtra(this, new Gnoll());
		NarrativeSpawns.spawnExtra(this, new Snake());
	}
}
