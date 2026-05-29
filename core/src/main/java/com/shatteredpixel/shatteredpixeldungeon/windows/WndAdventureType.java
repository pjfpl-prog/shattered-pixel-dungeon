/*
 * Projeto Roguelike Narrativo Procedural — menu inicial de tipo de aventura
 *
 * Mostrado ao começar New Game. Define em SPDSettings.oneShotLength
 * qual modo o jogador escolheu (0=clássico SPD, 5/15/20=narrativo).
 */

package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.scenes.HeroSelectScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.watabou.noosa.Image;

public class WndAdventureType extends WndOptions {

	public WndAdventureType() {
		super((Image) Icons.ENTER.get(),
			"Escolha sua aventura",
			"Clássica = SPD original. As demais são one-shots narrativas.",
			"Clássica — 26 níveis",
			"Conto Breve — 5 níveis",
			"Saga — 15 níveis",
			"Lenda Épica — 20 níveis");
	}

	@Override
	protected void onSelect(int index) {
		switch (index) {
			case 0: SPDSettings.oneShotLength(0);  break;
			case 1: SPDSettings.oneShotLength(5);  break;
			case 2: SPDSettings.oneShotLength(15); break;
			case 3: SPDSettings.oneShotLength(20); break;
		}
		ShatteredPixelDungeon.switchScene(HeroSelectScene.class);
	}

	@Override
	public void onBackPressed() {
		// no-op: força escolha pra deixar a flag setada.
	}
}
