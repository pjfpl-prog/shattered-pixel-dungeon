/*
 * Projeto Roguelike Narrativo Procedural — Fase 1
 *
 * NpcStatesGenerator monta um NpcState para cada NpcKind:
 *   - motivação (pool por arquétipo de NPC + leve viés por tom)
 *   - afiliação a uma das facções da run (ou neutra)
 *   - atitude inicial em relação ao herói
 *
 * Cada NPC mantém o alinhamento mecânico original — atitude aqui só
 * colore o diálogo gerado em NpcLines. Nenhum impacto em combate.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.generators;

import com.shatteredpixel.shatteredpixeldungeon.narrative.dialogue.NpcKind;
import com.shatteredpixel.shatteredpixeldungeon.narrative.factions.Faction;
import com.shatteredpixel.shatteredpixeldungeon.narrative.npcs.NpcState;
import com.shatteredpixel.shatteredpixeldungeon.narrative.npcs.NpcState.Attitude;
import com.watabou.utils.Random;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NpcStatesGenerator {

	private NpcStatesGenerator() {}

	private static final String[] GHOST_MOTIVES = {
		"busca redenção pelo que falhou em fazer",
		"espera o próximo capaz de terminar a missão",
		"não consegue se lembrar do próprio nome",
		"jura ter visto o boss antes de morrer"
	};

	private static final String[] WANDMAKER_MOTIVES = {
		"refugiou-se aqui depois que sua oficina queimou",
		"vende relíquias pra qualquer um que pague",
		"diz que cada cliente é o último",
		"esconde algo nas costas o tempo todo"
	};

	private static final String[] BLACKSMITH_MOTIVES = {
		"forja apenas pra calar os pesadelos",
		"considera o herói mais uma estatística",
		"recusou ofertas dos dois lados da guerra",
		"trabalha pra pagar uma dívida antiga"
	};

	private static final String[] IMP_MOTIVES = {
		"diverte-se vendo aventureiros entrarem em rota de colisão",
		"coleta histórias de heróis mortos",
		"aposta com algo invisível sobre seu destino",
		"trocaria a alma do herói por um chá decente"
	};

	public static Map<NpcKind, NpcState> generate(String tone, List<Faction> factions) {
		HashMap<NpcKind, NpcState> out = new HashMap<>();
		for (NpcKind kind : NpcKind.values()) {
			out.put(kind, makeState(kind, tone, factions));
		}
		return out;
	}

	private static NpcState makeState(NpcKind kind, String tone, List<Faction> factions) {
		String motivation = Random.element(motivesFor(kind));
		String affiliation = pickAffiliation(factions);
		Attitude attitude = pickAttitude(kind, tone);
		return new NpcState(motivation, affiliation, attitude);
	}

	private static String[] motivesFor(NpcKind kind) {
		switch (kind) {
			case GHOST:      return GHOST_MOTIVES;
			case WANDMAKER:  return WANDMAKER_MOTIVES;
			case BLACKSMITH: return BLACKSMITH_MOTIVES;
			case IMP:        return IMP_MOTIVES;
			default:         return new String[]{""};
		}
	}

	// ~40% chance de ser neutro; resto sorteia uma das facções da run.
	private static String pickAffiliation(List<Faction> factions) {
		if (factions == null || factions.isEmpty()) return "";
		if (Random.Int(10) < 4) return "";
		return Random.element(factions).name;
	}

	// IMP tende ao hostile, GHOST tende ao friendly; demais ao wary.
	// Tons "horror/loucura/paranoia" empurram pra hostile/wary; "esperança" amolece.
	private static Attitude pickAttitude(NpcKind kind, String tone) {
		Attitude base;
		switch (kind) {
			case GHOST:      base = Attitude.FRIENDLY; break;
			case IMP:        base = Attitude.HOSTILE;  break;
			default:         base = Attitude.WARY;     break;
		}
		if (tone == null) return base;
		if ("esperança".equalsIgnoreCase(tone)) {
			if (base == Attitude.HOSTILE) return Attitude.WARY;
			if (base == Attitude.WARY)    return Attitude.FRIENDLY;
		} else if ("horror".equalsIgnoreCase(tone)
				|| "loucura".equalsIgnoreCase(tone)
				|| "paranoia".equalsIgnoreCase(tone)) {
			if (base == Attitude.FRIENDLY) return Attitude.WARY;
			if (base == Attitude.WARY)     return Attitude.HOSTILE;
		}
		return base;
	}
}
