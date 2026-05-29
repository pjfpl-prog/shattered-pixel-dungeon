/*
 * Projeto Roguelike Narrativo Procedural — Enigma interativo
 *
 * Oráculo silencioso que aparece em ~50% dos capítulos da one-shot.
 * Interagir abre uma pergunta com 3 respostas. A certa concede uma
 * recompensa boa; a errada dá dano e marca o Oráculo como ofendido.
 *
 * Sprite e propriedades inspirados em RatKing (NPC imune, sem perseguir).
 * Banco interno de enigmas — autoral pt-BR, sem dependência do EventBank
 * (que está sendo expandido por outra sessão).
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RatKingSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class EnigmaticOracle extends NPC {

	{
		spriteClass = RatKingSprite.class;
		state       = SLEEPING;
	}

	private int riddleIndex     = -1;
	private boolean answered    = false;

	@Override
	public int defenseSkill(Char enemy) { return INFINITE_EVASION; }

	@Override
	public void damage(int dmg, Object src) { /* imune */ }

	@Override
	public boolean add(Buff buff) { return false; }

	@Override
	public boolean reset() { return true; }

	@Override
	protected Char chooseEnemy() { return null; }

	@Override
	public String name() { return "Oráculo Silencioso"; }

	private void ensureRiddle() {
		if (riddleIndex < 0) {
			riddleIndex = Random.Int(RIDDLES.length);
		}
	}

	@Override
	public boolean interact(Char c) {
		sprite.turnTo(pos, c.pos);
		if (c != Dungeon.hero) return true;

		if (answered) {
			GLog.i("O Oráculo te observa em silêncio. Já respondeu o que tinha a responder.");
			return true;
		}

		ensureRiddle();
		final Riddle r = RIDDLES[riddleIndex];
		Game.runOnRenderThread(new Callback() {
			@Override
			public void call() {
				GameScene.show(new WndOptions("O Oráculo Pergunta", r.question,
						r.options[0], r.options[1], r.options[2]) {
					@Override
					protected void onSelect(int index) {
						resolve(r, index);
					}

					@Override
					public void onBackPressed() { /* força resposta */ }
				});
			}
		});
		return true;
	}

	private void resolve(Riddle r, int chosen) {
		answered = true;
		Hero hero = Dungeon.hero;
		if (chosen == r.correct) {
			GLog.p("O Oráculo assente. \"Você sabia.\"");
			grantReward(hero);
		} else {
			GLog.n("O Oráculo te encara em silêncio. Você sente o erro como peso no peito.");
			if (hero != null) {
				int dmg = Math.max(1, hero.HT / 10);
				hero.HP = Math.max(1, hero.HP - dmg);
				GLog.n("Você perde %d de vida.", dmg);
			}
		}
	}

	private void grantReward(Hero hero) {
		if (hero == null) return;
		Item gift;
		switch (Random.Int(3)) {
			case 0:  gift = new PotionOfHealing();    break;
			case 1:  gift = new ScrollOfIdentify();   break;
			default: gift = new ScrollOfMagicMapping();
		}
		if (gift.doPickUp(hero, hero.pos)) {
			GLog.p("Você ganha: %s.", gift.name());
		}
		hero.earnExp(100, EnigmaticOracle.class);
	}

	private static final String RIDDLE_INDEX = "riddle_index";
	private static final String ANSWERED     = "answered";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(RIDDLE_INDEX, riddleIndex);
		bundle.put(ANSWERED,     answered);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		riddleIndex = bundle.getInt(RIDDLE_INDEX);
		answered    = bundle.getBoolean(ANSWERED);
	}

	private static final class Riddle {
		final String question;
		final String[] options;
		final int correct;
		Riddle(String question, int correct, String a, String b, String c) {
			this.question = question;
			this.options  = new String[]{a, b, c};
			this.correct  = correct;
		}
	}

	private static final Riddle[] RIDDLES = {
		new Riddle(
			"Caminho sem pés, fala sem boca, ouvido sem cabeça. O que sou?",
			1, "Uma sombra.", "Um eco.", "Um rio."),
		new Riddle(
			"Quanto mais me tomas, mais cresço. Sou o que falta. O que sou?",
			0, "Um buraco.", "Sede.", "Memória."),
		new Riddle(
			"Quem mata a si mesmo para que outros vivam, e morre quando ninguém o vê?",
			2, "Um santo.", "Um covarde.", "Um vela."),
		new Riddle(
			"Tenho mil rostos e nenhum próprio. Sigo onde vais e antecipo a tua queda.",
			0, "Um espelho.", "A morte.", "A fome."),
		new Riddle(
			"Sou rei sem reino e juiz sem lei. Sento no fundo de cada um.",
			2, "O silêncio.", "O esquecimento.", "O medo."),
		new Riddle(
			"Sou irmão do som mas só apareço quando ele cala. Quem me chama, me perde.",
			1, "O grito.", "O silêncio.", "O sono."),
		new Riddle(
			"Quem me carrega não me sente, quem me sente não me carrega.",
			0, "Um peso esquecido.", "Uma promessa.", "Um anel."),
		new Riddle(
			"Sou nascida de duas mãos e morro pela boca. Quanto mais me dizem, menos sou.",
			2, "A verdade.", "A oração.", "A confidência.")
	};
}
