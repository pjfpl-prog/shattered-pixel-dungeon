/*
 * Projeto Roguelike Narrativo Procedural — one-shot RPG mode
 *
 * Boss final da one-shot. Reusa toda a mecânica do Goo (movimento,
 * pump-up, ataques) mas veste o nome e as falas da identidade narrativa
 * gerada para a run (NarrativeDirector.bossIdentity / emotionalTone).
 *
 * Sem mudança de balanceamento — só nome + diálogos de entrada e morte.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Goo;
import com.shatteredpixel.shatteredpixeldungeon.narrative.NarrativeDirector;
import com.shatteredpixel.shatteredpixeldungeon.narrative.sprites.GraveBossSprite;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTitledMessage;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

public class NarrativeBoss extends Goo {

	{
		spriteClass = GraveBossSprite.class;
	}

	private boolean greeted = false;

	@Override
	public String name() {
		String id = NarrativeDirector.bossIdentity();
		return (id != null && !id.isEmpty()) ? id : super.name();
	}

	@Override
	public void notice() {
		super.notice();
		if (!greeted) {
			greeted = true;
			final String line = entranceLine();
			final String title = name();
			final String portraitPath = pickPortraitPath();
			Game.runOnRenderThread(new Callback() {
				@Override public void call() {
					Image p = null;
					try { p = new Image(portraitPath); } catch (Exception ignored) {}
					GameScene.show(new WndTitledMessage(p, title, line));
				}
			});
		}
	}

	private String pickPortraitPath() {
		return com.shatteredpixel.shatteredpixeldungeon.narrative.util.BossPortraits
				.pathFor(NarrativeDirector.bossIdentity());
	}

	@Override
	public void die(Object cause) {
		yell(deathLine());
		super.die(cause);
	}

	private String entranceLine() {
		String tone = NarrativeDirector.emotionalTone();
		if ("esperança".equalsIgnoreCase(tone)) {
			return "Você veio cedo demais. Ainda há tempo de voltar.";
		} else if ("horror".equalsIgnoreCase(tone) || "loucura".equalsIgnoreCase(tone)) {
			return "Eu sonhei com você. Em todos os sonhos você morre aqui.";
		} else if ("tragédia".equalsIgnoreCase(tone)) {
			return "Nós dois sabemos como isto termina. Termine logo.";
		}
		return "Então você chegou até aqui. Que pena.";
	}

	private String deathLine() {
		String tone = NarrativeDirector.emotionalTone();
		if ("tragédia".equalsIgnoreCase(tone)) {
			return "Finalmente... obrigado.";
		} else if ("paranoia".equalsIgnoreCase(tone) || "loucura".equalsIgnoreCase(tone)) {
			return "Você não me matou. Você só virou eu.";
		}
		return "Você venceu... mas a masmorra lembra.";
	}

	private static final String GREETED = "narrative_greeted";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(GREETED, greeted);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		greeted = bundle.getBoolean(GREETED);
	}
}
