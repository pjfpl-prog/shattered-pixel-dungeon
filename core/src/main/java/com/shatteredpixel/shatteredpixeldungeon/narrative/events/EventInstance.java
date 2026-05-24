/*
 * Projeto Roguelike Narrativo Procedural — sistema de eventos
 *
 * Estado persistido de uma ocorrência de evento numa run específica.
 * Bundlable + ctor sem args para Reflection do Bundle.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.events;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class EventInstance implements Bundlable {

	public String eventId      = "";
	public int depth           = 0;
	public boolean triggered   = false;
	public int chosenOption    = -1;

	public EventInstance() {}

	public EventInstance(String eventId, int depth) {
		this.eventId = eventId;
		this.depth   = depth;
	}

	private static final String EVENT_ID       = "event_id";
	private static final String DEPTH          = "depth";
	private static final String TRIGGERED      = "triggered";
	private static final String CHOSEN_OPTION  = "chosen_option";

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(EVENT_ID,      eventId);
		bundle.put(DEPTH,         depth);
		bundle.put(TRIGGERED,     triggered);
		bundle.put(CHOSEN_OPTION, chosenOption);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		eventId      = bundle.getString(EVENT_ID);
		depth        = bundle.getInt(DEPTH);
		triggered    = bundle.getBoolean(TRIGGERED);
		chosenOption = bundle.getInt(CHOSEN_OPTION);
	}
}
