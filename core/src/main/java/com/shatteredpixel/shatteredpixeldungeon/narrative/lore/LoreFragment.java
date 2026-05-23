/*
 * Projeto Roguelike Narrativo Procedural — Fase 1 / Passo 4
 *
 * LoreFragment é um pedaço de narrativa espalhado pela dungeon.
 * Cada fragmento tem um piso-gatilho e é revelado uma vez por run.
 *
 * Bundlable + ctor sem args para o sistema Reflection do Bundle.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.lore;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class LoreFragment implements Bundlable {

	public enum Kind {
		BOOK, NOTE, INSCRIPTION, PROPHECY, WARNING
	}

	public Kind kind          = Kind.NOTE;
	public String body        = "";
	public int triggerDepth   = 1;
	public boolean discovered = false;

	public LoreFragment() {}

	public LoreFragment(Kind kind, String body, int triggerDepth) {
		this.kind = kind;
		this.body = body;
		this.triggerDepth = triggerDepth;
	}

	private static final String KIND          = "kind";
	private static final String BODY          = "body";
	private static final String TRIGGER_DEPTH = "trigger_depth";
	private static final String DISCOVERED    = "discovered";

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(KIND,          kind.name());
		bundle.put(BODY,          body);
		bundle.put(TRIGGER_DEPTH, triggerDepth);
		bundle.put(DISCOVERED,    discovered);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		try {
			kind = Kind.valueOf(bundle.getString(KIND));
		} catch (IllegalArgumentException e) {
			kind = Kind.NOTE;
		}
		body         = bundle.getString(BODY);
		triggerDepth = bundle.getInt(TRIGGER_DEPTH);
		discovered   = bundle.getBoolean(DISCOVERED);
	}

	// Prefixo formatado para mostrar ao jogador.
	public String formatted() {
		switch (kind) {
			case BOOK:        return "Você folheia um livro: \"" + body + "\"";
			case NOTE:        return "Você encontra uma nota: \"" + body + "\"";
			case INSCRIPTION: return "Uma inscrição na parede diz: \"" + body + "\"";
			case PROPHECY:    return "Sussurros antigos ecoam: \"" + body + "\"";
			case WARNING:     return "Um aviso rabiscado adverte: \"" + body + "\"";
			default:          return body;
		}
	}
}
