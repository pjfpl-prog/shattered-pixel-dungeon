/*
 * Projeto Roguelike Narrativo Procedural — Fase 1
 *
 * FactionsGenerator escolhe 2-3 facções para a run, com pool por tema.
 *
 * Cada Faction carrega gender + plural para que quem mencionar a facção
 * (NpcLines, futuro Journal, etc) possa montar o artigo correto:
 *   "Os Monges do Véu" (masc. plural)
 *   "A Ordem das Cinzas" (fem. sing.)
 *
 * Nomes são guardados SEM artigo embutido — o artigo é prepended dinâmico.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.generators;

import com.shatteredpixel.shatteredpixeldungeon.narrative.factions.Faction;
import com.shatteredpixel.shatteredpixeldungeon.narrative.util.PtBr.Gender;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class FactionsGenerator {

	private FactionsGenerator() {}

	// (name, role, hostile, gender, plural)
	private static final Faction[] DEFAULT_POOL = {
		new Faction("Ordem das Cinzas",      "queimam os mortos antes que voltem", false, Gender.F, false),
		new Faction("Monges do Véu",         "selam o que vê demais",              false, Gender.M, true),
		new Faction("Arquivistas Ósseos",    "guardam memórias em ossos",          true,  Gender.M, true),
		new Faction("Sacerdotes Afogados",   "rezam pelo deus submerso",           true,  Gender.M, true),
		new Faction("Coveiros Sem Nome",     "abrem os túmulos quando ninguém vê", false, Gender.M, true),
		new Faction("Caçadores do Silêncio", "matam quem fala em voz alta",        true,  Gender.M, true)
	};

	private static final Map<String, Faction[]> BY_THEME = new HashMap<>();
	static {
		BY_THEME.put("Reino Caído", new Faction[]{
			new Faction("Guarda da Coroa Quebrada", "ainda jura lealdade ao trono vazio", false, Gender.F, false),
			new Faction("Ladrões do Salão",          "roubam o que sobrou da realeza",    true,  Gender.M, true),
			new Faction("Heralds Sem Voz",           "lembram dos nomes esquecidos",      false, Gender.M, true)
		});
		BY_THEME.put("Ritual de Invocação", new Faction[]{
			new Faction("Círculo do Quinto Selo",  "guardam o ritual incompleto",     true,  Gender.M, false),
			new Faction("Monges do Véu",           "selam o que vê demais",           false, Gender.M, true),
			new Faction("Convocadores Tardios",    "tentam terminar o que começaram", true,  Gender.M, true)
		});
		BY_THEME.put("Despertar de Máquina Antiga", new Faction[]{
			new Faction("Engrenagistas Ímpios", "alimentam a máquina com sangue",       true,  Gender.M, true),
			new Faction("Silenciadores",        "tentam desligar o que despertou",      false, Gender.M, true),
			new Faction("Numerados",            "vivem segundo os cálculos do motor",   true,  Gender.M, true)
		});
		BY_THEME.put("Dungeon da Praga", new Faction[]{
			new Faction("Lavradores de Pus",   "cultivam a doença como agricultura", true,  Gender.M, true),
			new Faction("Ordem das Cinzas",    "queimam os mortos antes que voltem", false, Gender.F, false),
			new Faction("Médicos Errantes",    "tratam quem ainda pode ser salvo",   false, Gender.M, true)
		});
		BY_THEME.put("Sonho Corrompido", new Faction[]{
			new Faction("Vigias do Sono",     "impedem os outros de dormir aqui",     false, Gender.M, true),
			new Faction("Coletores de Sonhos","vendem pesadelos como mercadoria",     true,  Gender.M, true),
			new Faction("Acordados",          "nunca mais fecharam os olhos",         true,  Gender.M, true)
		});
		BY_THEME.put("Catacumbas em Loop Temporal", new Faction[]{
			new Faction("Coveiros Sem Nome",  "abrem os túmulos quando ninguém vê", false, Gender.M, true),
			new Faction("Que Voltam",         "já morreram aqui antes",             true,  Gender.M, true),
			new Faction("Arquivistas Ósseos", "guardam memórias em ossos",          true,  Gender.M, true)
		});
		BY_THEME.put("Expedição Esquecida", new Faction[]{
			new Faction("Última Caravana",         "carregam o que ninguém mais lembra", false, Gender.F, false),
			new Faction("Cartógrafos Cegos",       "desenham mapas pelo tato",            false, Gender.M, true),
			new Faction("Saqueadores de Diários",  "leem o que não devia ser lido",       true,  Gender.M, true)
		});
		BY_THEME.put("Colmeia Parasita", new Faction[]{
			new Faction("Hospedeiros Voluntários", "ofereceram seus corpos por fé", true,  Gender.M, true),
			new Faction("Exterminadores",          "queimam tudo que se mexe",      false, Gender.M, true),
			new Faction("Voz Coletiva",            "falam por uma só boca",         true,  Gender.F, false)
		});
		BY_THEME.put("Guerra Civil dos Necromantes", new Faction[]{
			new Faction("Casa do Mestre Antigo", "defendem a antiga escola",   true,  Gender.F, false),
			new Faction("Aprendizes Traídos",    "querem queimar o legado",    true,  Gender.M, true),
			new Faction("Neutros do Sudário",    "negociam com os dois lados", false, Gender.M, true)
		});
		BY_THEME.put("Profecia Interrompida", new Faction[]{
			new Faction("Que Esperam",              "ainda creem na vinda",          false, Gender.M, true),
			new Faction("Hereges da Última Página", "queimaram a profecia",          true,  Gender.M, true),
			new Faction("Escribas Mudos",           "copiam o texto perdido de cor", false, Gender.M, true)
		});
		BY_THEME.put("Santuário Afogado", new Faction[]{
			new Faction("Sacerdotes Afogados", "rezam pelo deus submerso",   true,  Gender.M, true),
			new Faction("Pescadores de Almas", "trazem corpos à superfície", false, Gender.M, true),
			new Faction("Que Não Bóiam",       "afundaram por escolha",      true,  Gender.M, true)
		});
	}

	public static List<Faction> generate(String theme) {
		Faction[] themed = BY_THEME.get(theme);
		Faction[] pool   = themed != null ? themed : DEFAULT_POOL;

		ArrayList<Faction> out = new ArrayList<>();
		HashSet<String> seenNames = new HashSet<>();

		int count = 2 + Random.Int(2); // 2..3

		for (int tries = 0; tries < pool.length * 2 && out.size() < count; tries++) {
			Faction picked = Random.element(pool);
			if (seenNames.add(picked.name)) {
				out.add(copy(picked));
			}
		}

		for (int tries = 0; tries < DEFAULT_POOL.length * 2 && out.size() < count; tries++) {
			Faction picked = Random.element(DEFAULT_POOL);
			if (seenNames.add(picked.name)) {
				out.add(copy(picked));
			}
		}

		return out;
	}

	private static Faction copy(Faction f) {
		return new Faction(f.name, f.role, f.hostile, f.gender, f.plural);
	}
}
