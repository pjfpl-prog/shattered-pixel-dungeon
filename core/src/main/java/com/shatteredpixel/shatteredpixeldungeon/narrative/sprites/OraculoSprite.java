/*
 * Projeto Roguelike Narrativo Procedural — sprite custom do Oraculo Silencioso
 *
 * Spritesheet 16x16 gerado a partir da arte conceitual via
 * tools/pixelart/build_sprites.py (fonte: assets/narrative/oraculo_portrait_large.png).
 *
 * Layout do sheet (sprites/narrative/oraculo.png, 176x16 = 11 frames):
 *   idle:   0,1     run:    2,3,4,5
 *   attack: 6,7     die:    8,9,10
 *
 * Substitui o uso provisorio de RatKingSprite em EnigmaticOracle.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.sprites;

import com.shatteredpixel.shatteredpixeldungeon.sprites.MobSprite;
import com.watabou.noosa.TextureFilm;

public class OraculoSprite extends MobSprite {

	private static final String TEXTURE = "sprites/narrative/oraculo.png";

	public OraculoSprite() {
		super();

		texture( TEXTURE );

		TextureFilm frames = new TextureFilm( texture, 16, 16 );

		idle = new Animation( 3, true );
		idle.frames( frames, 0, 1 );

		run = new Animation( 8, true );
		run.frames( frames, 2, 3, 4, 5 );

		attack = new Animation( 12, false );
		attack.frames( frames, 6, 7 );

		die = new Animation( 8, false );
		die.frames( frames, 8, 9, 10 );

		play( idle );
	}
}
