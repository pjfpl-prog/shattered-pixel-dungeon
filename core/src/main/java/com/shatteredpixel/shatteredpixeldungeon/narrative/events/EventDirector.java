/*
 * Projeto Roguelike Narrativo Procedural — runtime de eventos
 *
 * EventDirector é a ponte entre o estado narrativo persistido
 * (scheduledEvents no AdventureSeed) e os sistemas do jogo
 * (Hero, Buff, Item, Dungeon). Tudo passa por aqui.
 *
 *   popNextForDepth(depth) → próxima EventInstance não-triggered
 *   apply(instance, optionIdx) → aplica os efeitos da opção escolhida
 *
 * Item / Buff são identificados por simple class name; o mapeamento
 * pra Class<?> fica num catálogo estático aqui (whitelist).
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.events;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.narrative.NarrativeDirector;
import com.shatteredpixel.shatteredpixeldungeon.narrative.dialogue.NpcKind;
import com.shatteredpixel.shatteredpixeldungeon.narrative.lore.LoreFragment;
import com.shatteredpixel.shatteredpixeldungeon.narrative.models.AdventureSeed;
import com.shatteredpixel.shatteredpixeldungeon.narrative.npcs.NpcState;
import com.shatteredpixel.shatteredpixeldungeon.narrative.npcs.NpcState.Attitude;
import com.shatteredpixel.shatteredpixeldungeon.narrative.quests.QuestStep;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventDirector {

	private EventDirector() {}

	// Whitelist de itens dispensáveis via evento.
	private static final Map<String, Class<? extends Item>> ITEMS = new HashMap<>();
	static {
		ITEMS.put("PotionOfHealing",     PotionOfHealing.class);
		ITEMS.put("PotionOfMindVision",  PotionOfMindVision.class);
		ITEMS.put("ScrollOfIdentify",    ScrollOfIdentify.class);
		ITEMS.put("ScrollOfMagicMapping",ScrollOfMagicMapping.class);
	}

	// Whitelist de buffs aplicáveis via evento.
	// FlavourBuff porque é a hierarquia que aceita duração em Buff.affect(...).
	private static final Map<String, Class<? extends FlavourBuff>> BUFFS = new HashMap<>();
	static {
		BUFFS.put("Bless",       Bless.class);
		BUFFS.put("Recharging",  Recharging.class);
	}

	public static EventInstance popNextForDepth(int depth) {
		AdventureSeed seed = NarrativeDirector.seed();
		if (seed == null) return null;
		List<EventInstance> list = seed.scheduledEvents.get(depth);
		if (list == null) return null;
		for (EventInstance inst : list) {
			if (inst.triggered) continue;
			NarrativeEvent ev = EventBank.get(inst.eventId);
			if (ev == null) continue;
			if (!flagsSatisfied(seed, ev)) {
				// Não pode disparar agora — marca como triggered "skip" pra não bloquear próximo.
				inst.triggered    = true;
				inst.chosenOption = -1;
				continue;
			}
			return inst;
		}
		return null;
	}

	private static boolean flagsSatisfied(AdventureSeed seed, NarrativeEvent ev) {
		if (ev.requiredFlags.length == 0) return true;
		for (String f : ev.requiredFlags) {
			if (!seed.eventFlags.contains(f)) return false;
		}
		return true;
	}

	public static void apply(EventInstance instance, int optionIndex) {
		if (instance == null) return;
		NarrativeEvent event = EventBank.get(instance.eventId);
		if (event == null) {
			instance.triggered    = true;
			instance.chosenOption = optionIndex;
			return;
		}
		if (optionIndex < 0 || optionIndex >= event.options.size()) {
			instance.triggered    = true;
			instance.chosenOption = -1;
			return;
		}

		instance.triggered    = true;
		instance.chosenOption = optionIndex;

		EventOption chosen = event.options.get(optionIndex);
		applyEffects(chosen.effects);

		if (chosen.resultText != null && !chosen.resultText.isEmpty()) {
			GLog.i(chosen.resultText);
		}
	}

	public static List<String> applyEffects(Iterable<EventEffect> effects) {
		ArrayList<String> log = new ArrayList<>();
		Hero hero = Dungeon.hero;
		for (EventEffect eff : effects) {
			applyOne(hero, eff, log);
		}
		return log;
	}

	private static void applyOne(Hero hero, EventEffect eff, List<String> log) {
		if (hero == null && eff.kind != EventEffectKind.NPC_ATTITUDE
				&& eff.kind != EventEffectKind.MARK_QUEST_STEP
				&& eff.kind != EventEffectKind.TEXT_ONLY) {
			return;
		}
		switch (eff.kind) {
			case TEXT_ONLY:
				break;
			case HEAL_PCT: {
				int amount = Math.max(1, hero.HT * eff.intArg / 100);
				hero.HP = Math.min(hero.HT, hero.HP + amount);
				GLog.p("Você se cura em %d.", amount);
				break;
			}
			case DAMAGE_PCT: {
				int amount = Math.max(1, hero.HT * eff.intArg / 100);
				hero.HP = Math.max(1, hero.HP - amount); // não mata aqui
				GLog.n("Você perde %d de vida.", amount);
				break;
			}
			case GIVE_GOLD: {
				int amount = eff.intArg;
				if (amount > 0) {
					Dungeon.gold += amount;
					Statistics.goldCollected += amount;
					GLog.p("+%d ouro.", amount);
				} else if (amount < 0) {
					int spent = Math.min(Dungeon.gold, -amount);
					Dungeon.gold -= spent;
					GLog.n("-%d ouro.", spent);
				}
				break;
			}
			case GIVE_XP: {
				if (eff.intArg > 0) {
					hero.earnExp(eff.intArg, EventDirector.class);
					GLog.p("+%d XP.", eff.intArg);
				}
				break;
			}
			case GIVE_ITEM: {
				Class<? extends Item> cls = ITEMS.get(eff.stringArg);
				if (cls != null) {
					try {
						Item it = cls.getConstructor().newInstance();
						if (it.doPickUp(hero, hero.pos)) {
							GLog.p("Você ganha: %s.", it.name());
						} else {
							GLog.i("Você não consegue carregar %s.", it.name());
						}
					} catch (Exception e) {
						// Silencioso — item provavelmente removido/renomeado entre versões.
					}
				}
				break;
			}
			case NPC_ATTITUDE: {
				try {
					NpcKind kind = NpcKind.valueOf(eff.stringArg);
					NpcState state = NarrativeDirector.npcState(kind);
					if (state != null && eff.intArg >= 0 && eff.intArg < Attitude.values().length) {
						state.attitude = Attitude.values()[eff.intArg];
					}
				} catch (IllegalArgumentException ignored) {}
				break;
			}
			case MARK_QUEST_STEP: {
				List<QuestStep> chain = new ArrayList<>(NarrativeDirector.questChain());
				if (eff.intArg >= 0 && eff.intArg < chain.size()) {
					QuestStep step = chain.get(eff.intArg);
					if (!step.completed) {
						step.completed = true;
						GLog.p("Passo da aventura cumprido: %s.", step.description);
					}
				}
				break;
			}
			case ADD_BUFF: {
				Class<? extends FlavourBuff> cls = BUFFS.get(eff.stringArg);
				if (cls != null) {
					try {
						Buff.affect(hero, cls, (float) Math.max(1, eff.intArg));
					} catch (Exception ignored) {}
				}
				break;
			}
			case ADD_LORE: {
				AdventureSeed seed = NarrativeDirector.seed();
				if (seed != null && eff.stringArg != null && !eff.stringArg.isEmpty()) {
					int d = Dungeon.depth;
					LoreFragment lf = new LoreFragment(LoreFragment.Kind.NOTE, eff.stringArg, d);
					lf.discovered = true;
					seed.loreFragments.add(lf);
					GLog.i("Você encontra uma nota: \"%s\"", eff.stringArg);
				}
				break;
			}
			case SET_FLAG: {
				AdventureSeed seed = NarrativeDirector.seed();
				if (seed != null && eff.stringArg != null && !eff.stringArg.isEmpty()) {
					seed.eventFlags.add(eff.stringArg);
				}
				break;
			}
			case HT_BONUS: {
				if (hero != null && eff.intArg > 0) {
					hero.HT += eff.intArg;
					hero.HP += eff.intArg;
					GLog.p("+%d PV máximos.", eff.intArg);
				}
				break;
			}
		}
	}
}
