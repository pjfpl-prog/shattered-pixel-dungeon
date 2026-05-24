/*
 * Projeto Roguelike Narrativo Procedural — one-shot RPG mode
 *
 * Capítulo final (boss): herda SewerBossLevel pra reusar a infra do Goo
 * (boss já presente na sala de boss). Trocamos só visual (tileset/cor)
 * pra parecer um lugar diferente do sewer original.
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
		// Dourado/ocre — sala do antagonista.
		color1 = 0x6b5318;
		color2 = 0xc9a13a;
	}

	@Override
	public void unseal() {
		boolean wasSealed = locked;
		super.unseal();
		// unseal só roda quando o boss morre e a sala destrava. Disparamos
		// o epílogo da one-shot aqui.
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
