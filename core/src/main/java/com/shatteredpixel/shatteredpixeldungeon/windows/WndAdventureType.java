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

public class WndAdventureType extends WndOptions {

	private static final String TITLE   = "Escolha sua aventura";
	private static final String MESSAGE = "Que tipo de jornada você quer viver hoje?";

	public WndAdventureType() {
		super(TITLE, MESSAGE,
			"Aventura Clássica (26 níveis, SPD original)",
			"Conto Breve (5 níveis narrativos)",
			"Saga (15 níveis narrativos)",
			"Lenda Épica (20 níveis narrativos)");
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
		// no-op: forçar escolha pra deixar a flag setada.
	}
}
