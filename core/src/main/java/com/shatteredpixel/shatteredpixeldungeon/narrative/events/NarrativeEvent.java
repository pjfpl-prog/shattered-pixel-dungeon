/*
 * Projeto Roguelike Narrativo Procedural — sistema de eventos
 *
 * Um evento de livro-jogo: contexto (prompt) + 2-3 escolhas.
 * Imutável: definido em EventBank, referenciado por id em EventInstance.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class NarrativeEvent {

	public final String id;
	public final String title;
	public final String prompt;
	public final List<EventOption> options;
	public final int minDepth;
	public final int maxDepth;
	public final String[] themesPreferred;
	public final String[] tonesPreferred;
	public final String[] requiredFlags;   // todas devem estar setadas no AdventureSeed.eventFlags

	public NarrativeEvent(String id, String title, String prompt,
	                      int minDepth, int maxDepth,
	                      String[] themesPreferred, String[] tonesPreferred,
	                      String[] requiredFlags,
	                      EventOption... options) {
		this.id              = id;
		this.title           = title;
		this.prompt          = prompt;
		this.minDepth        = minDepth;
		this.maxDepth        = maxDepth;
		this.themesPreferred = themesPreferred == null ? new String[0] : themesPreferred;
		this.tonesPreferred  = tonesPreferred  == null ? new String[0] : tonesPreferred;
		this.requiredFlags   = requiredFlags   == null ? new String[0] : requiredFlags;
		this.options         = Collections.unmodifiableList(Arrays.asList(options));
	}

	// Construtor de conveniência sem flags.
	public NarrativeEvent(String id, String title, String prompt,
	                      int minDepth, int maxDepth,
	                      String[] themesPreferred, String[] tonesPreferred,
	                      EventOption... options) {
		this(id, title, prompt, minDepth, maxDepth,
				themesPreferred, tonesPreferred, new String[0], options);
	}

	public boolean acceptsDepth(int depth) {
		return depth >= minDepth && depth <= maxDepth;
	}
}
