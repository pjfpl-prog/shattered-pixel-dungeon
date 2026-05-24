/*
 * Projeto Roguelike Narrativo Procedural — UI dos eventos
 *
 * Window de escolha tipo livro-jogo: mostra título + prompt do NarrativeEvent
 * + botões para cada EventOption. Quando o jogador escolhe, fecha e dispara
 * a sequência (efeitos + texto de resultado) via callback no GameScene.
 *
 * Subclasse de WndOptions pra herdar o layout de N botões verticais.
 */

package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.narrative.events.EventOption;
import com.shatteredpixel.shatteredpixeldungeon.narrative.events.NarrativeEvent;

import java.util.ArrayList;
import java.util.List;

public class WndChoice extends WndOptions {

	public interface OnChosen {
		void chosen(int index);
	}

	private final OnChosen callback;

	public WndChoice(NarrativeEvent event, OnChosen callback) {
		super(event.title, event.prompt, buttonLabels(event.options));
		this.callback = callback;
	}

	private static String[] buttonLabels(List<EventOption> options) {
		ArrayList<String> labels = new ArrayList<>();
		for (EventOption o : options) labels.add(o.buttonText);
		return labels.toArray(new String[0]);
	}

	@Override
	protected void onSelect(int index) {
		if (callback != null) callback.chosen(index);
	}

	// Evita que o jogador feche a janela pelo "voltar" sem escolher.
	@Override
	public void onBackPressed() {
		// no-op: jogador precisa escolher uma opção.
	}
}
