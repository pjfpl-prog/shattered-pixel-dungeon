/*
 * Projeto Roguelike Narrativo Procedural — boss minimalista pra diagnóstico.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.levels.SewerBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.narrative.NarrativeDirector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndStory;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class NarrativeBossChapter extends SewerBossLevel {

	{
		color1 = 0x6b5318;
		color2 = 0xc9a13a;
	}

	@Override
	public void unseal() {
		boolean wasSealed = locked;
		super.unseal();
		if (wasSealed && !NarrativeDirector.endingShown()) {
			final String epilog = NarrativeDirector.endingText();
			NarrativeDirector.markEndingShown();
			Game.runOnRenderThread(new Callback() {
				@Override
				public void call() {
					GameScene.show(new WndStory(epilog));
				}
			});
		}
	}
}
