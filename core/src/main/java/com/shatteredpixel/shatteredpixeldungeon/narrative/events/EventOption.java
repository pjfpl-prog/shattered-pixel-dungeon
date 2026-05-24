/*
 * Projeto Roguelike Narrativo Procedural — sistema de eventos
 *
 * Uma opção que o jogador pode escolher num NarrativeEvent.
 * Texto curto pro botão, texto de resultado (mostrado após escolher),
 * e a lista de efeitos a aplicar.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class EventOption {

	public final String buttonText;
	public final String resultText;
	public final List<EventEffect> effects;

	public EventOption(String buttonText, String resultText, EventEffect... effects) {
		this.buttonText = buttonText;
		this.resultText = resultText == null ? "" : resultText;
		this.effects    = Collections.unmodifiableList(Arrays.asList(effects));
	}
}
