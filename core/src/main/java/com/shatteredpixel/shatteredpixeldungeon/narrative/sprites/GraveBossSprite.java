/*
 * Projeto Roguelike Narrativo Procedural — sprite custom do boss da one-shot
 *
 * NarrativeBoss estende Goo e reusa TODA a mecanica do Goo (movimento,
 * pump-up/telegrafo, spray, ataque, morte). Por isso este sprite estende
 * GooSprite: o Goo faz casts ((GooSprite)sprite) e chamadas a pumpUp/
 * pumpAttack/triggerEmitters/spray que precisam continuar funcionando.
 *
 * O construtor chama super() (que monta a textura/anim/emitter do Goo) e
 * entao TROCA a textura pelo spritesheet narrativo e RECONSTROI todas as
 * animacoes — inclusive pump/pumpAttack (tornados protected em GooSprite) —
 * para a grade 16x16, evitando o sangramento de UV que ocorreria se as
 * animacoes do Goo (grade 20x14) apontassem pro sheet novo.
 *
 * A textura e escolhida pela identidade da run (mesma heuristica dos
 * retratos em NarrativeBoss): identidade contendo "profeta" -> Profeta Mudo;
 * senao -> Rei Cinza.
 *
 * Spritesheets 16x16 gerados por tools/pixelart/build_sprites.py.
 * Layout (176x16 = 11 frames):
 *   idle: 0,1   run: 2,3,4,5   attack: 6,7   die: 8,9,10
 *   (pump/pumpAttack reusam os frames de ataque como carga/golpe)
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.sprites;

import com.shatteredpixel.shatteredpixeldungeon.narrative.NarrativeDirector;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GooSprite;
import com.watabou.noosa.TextureFilm;

public class GraveBossSprite extends GooSprite {

	private static final String TEX_REI_CINZA    = "sprites/narrative/grave_boss_rei_cinza.png";
	private static final String TEX_PROFETA_MUDO = "sprites/narrative/grave_boss_profeta_mudo.png";

	public GraveBossSprite() {
		super(); // monta textura/anim/emitter do Goo; reconstruimos a seguir

		texture( pickTexture() );

		TextureFilm frames = new TextureFilm( texture, 16, 16 );

		idle = new Animation( 8, true );
		idle.frames( frames, 0, 1 );

		run = new Animation( 12, true );
		run.frames( frames, 2, 3, 4, 5 );

		// telegrafo de carga (looping) — usa os frames de ataque como "respiro" tenso
		pump = new Animation( 12, true );
		pump.frames( frames, 6, 7, 6, 7 );

		// golpe carregado (nao-loop) — onComplete do Goo dispara os emitters
		pumpAttack = new Animation( 12, false );
		pumpAttack.frames( frames, 6, 7, 6, 7 );

		attack = new Animation( 10, false );
		attack.frames( frames, 6, 7 );

		die = new Animation( 8, false );
		die.frames( frames, 8, 9, 10 );

		play( idle );
	}

	private static String pickTexture() {
		String id = NarrativeDirector.bossIdentity();
		if (id != null && id.toLowerCase().contains("profeta")) {
			return TEX_PROFETA_MUDO;
		}
		return TEX_REI_CINZA;
	}
}
