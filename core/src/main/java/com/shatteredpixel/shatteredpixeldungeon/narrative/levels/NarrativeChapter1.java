/*
 * Projeto Roguelike Narrativo Procedural — one-shot RPG mode
 *
 * Capítulo 1: visual vermelho-sangue + cobras extras como encontros-chave.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;
import com.watabou.noosa.audio.Music;

public class NarrativeChapter1 extends NarrativeChapter {

	{
		color1 = 0x7c2a2a;
		color2 = 0xb84141;
	}

	@Override
	public void playLevelMusic() {
		Music.INSTANCE.play(Assets.Music.SEWERS_1, true);
	}

	@Override
	protected void createMobs() {
		super.createMobs();
		NarrativeSpawns.spawnExtra(this, new Snake());
		NarrativeSpawns.spawnExtra(this, new Snake());
	}
}
