/*
 * Projeto Roguelike Narrativo Procedural — sistema de eventos
 *
 * Efeito singular que uma opção do evento aplica. Imutável após construção.
 * Não é Bundlable: é parte da definição estática do evento (EventBank).
 * O que persiste no save é o EventInstance (qual opção foi escolhida).
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.events;

public final class EventEffect {

	public final EventEffectKind kind;
	public final String stringArg;
	public final int intArg;

	public EventEffect(EventEffectKind kind, String stringArg, int intArg) {
		this.kind      = kind;
		this.stringArg = stringArg == null ? "" : stringArg;
		this.intArg    = intArg;
	}

	public static EventEffect text() {
		return new EventEffect(EventEffectKind.TEXT_ONLY, "", 0);
	}
	public static EventEffect heal(int pct)         { return new EventEffect(EventEffectKind.HEAL_PCT,        "", pct); }
	public static EventEffect damage(int pct)       { return new EventEffect(EventEffectKind.DAMAGE_PCT,      "", pct); }
	public static EventEffect gold(int amount)      { return new EventEffect(EventEffectKind.GIVE_GOLD,       "", amount); }
	public static EventEffect xp(int amount)        { return new EventEffect(EventEffectKind.GIVE_XP,         "", amount); }
	public static EventEffect item(String cls)      { return new EventEffect(EventEffectKind.GIVE_ITEM,       cls, 0); }
	public static EventEffect npc(String kind, int attitudeOrdinal) {
		return new EventEffect(EventEffectKind.NPC_ATTITUDE, kind, attitudeOrdinal);
	}
	public static EventEffect markQuestStep(int index) {
		return new EventEffect(EventEffectKind.MARK_QUEST_STEP, "", index);
	}
	public static EventEffect buff(String cls, int duration) {
		return new EventEffect(EventEffectKind.ADD_BUFF, cls, duration);
	}
}
