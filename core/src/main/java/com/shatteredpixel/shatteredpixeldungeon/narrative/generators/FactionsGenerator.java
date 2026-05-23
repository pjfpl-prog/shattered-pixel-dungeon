/*
 * Projeto Roguelike Narrativo Procedural — Fase 1
 *
 * FactionsGenerator escolhe 2-3 facções para a run, com pool por tema.
 *
 * Mesma filosofia do BossIdentityGenerator: tema mapeia para um conjunto
 * de facções coerentes; temas desconhecidos caem no pool DEFAULT.
 *
 * Cada facção carrega um nome E um papel curto (uma frase que diz "o que
 * eles fazem"). O papel é usado em telas/diálogos para o jogador entender
 * o lugar da facção sem precisar de telas extras.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.generators;

import com.shatteredpixel.shatteredpixeldungeon.narrative.factions.Faction;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class FactionsGenerator {

	private FactionsGenerator() {}

	// Pool universal — usado como fallback e para misturar nos temas pequenos.
	private static final Faction[] DEFAULT_POOL = {
		new Faction("Ordem das Cinzas",      "queimam os mortos antes que voltem", false),
		new Faction("Monges do Véu",         "selam o que vê demais",              false),
		new Faction("Arquivistas Ósseos",    "guardam memórias em ossos",          true),
		new Faction("Sacerdotes Afogados",   "rezam pelo deus submerso",           true),
		new Faction("Coveiros Sem Nome",     "abrem os túmulos quando ninguém vê", false),
		new Faction("Caçadores do Silêncio", "matam quem fala em voz alta",        true)
	};

	private static final Map<String, Faction[]> BY_THEME = new HashMap<>();
	static {
		BY_THEME.put("Reino Caído", new Faction[]{
			new Faction("Guarda da Coroa Quebrada", "ainda jura lealdade ao trono vazio", false),
			new Faction("Ladrões do Salão",          "roubam o que sobrou da realeza",    true),
			new Faction("Heralds Sem Voz",           "lembram dos nomes esquecidos",      false)
		});
		BY_THEME.put("Ritual de Invocação", new Faction[]{
			new Faction("Círculo do Quinto Selo",  "guardam o ritual incompleto",    true),
			new Faction("Monges do Véu",           "selam o que vê demais",          false),
			new Faction("Convocadores Tardios",    "tentam terminar o que começaram", true)
		});
		BY_THEME.put("Despertar de Máquina Antiga", new Faction[]{
			new Faction("Engrenagistas Ímpios", "alimentam a máquina com sangue", true),
			new Faction("Silenciadores",        "tentam desligar o que despertou", false),
			new Faction("Os Numerados",         "vivem segundo os cálculos do motor", true)
		});
		BY_THEME.put("Dungeon da Praga", new Faction[]{
			new Faction("Lavradores de Pus",   "cultivam a doença como agricultura", true),
			new Faction("Ordem das Cinzas",    "queimam os mortos antes que voltem", false),
			new Faction("Médicos Errantes",    "tratam quem ainda pode ser salvo",   false)
		});
		BY_THEME.put("Sonho Corrompido", new Faction[]{
			new Faction("Vigias do Sono",   "impedem os outros de dormir aqui", false),
			new Faction("Coletores de Sonhos", "vendem pesadelos como mercadoria", true),
			new Faction("Os Acordados",     "nunca mais fecharam os olhos",     true)
		});
		BY_THEME.put("Catacumbas em Loop Temporal", new Faction[]{
			new Faction("Coveiros Sem Nome",  "abrem os túmulos quando ninguém vê", false),
			new Faction("Os Que Voltam",      "já morreram aqui antes",             true),
			new Faction("Arquivistas Ósseos", "guardam memórias em ossos",          true)
		});
		BY_THEME.put("Expedição Esquecida", new Faction[]{
			new Faction("Última Caravana",    "carregam o que ninguém mais lembra", false),
			new Faction("Cartógrafos Cegos",  "desenham mapas pelo tato",           false),
			new Faction("Saqueadores de Diários", "leem o que não devia ser lido",  true)
		});
		BY_THEME.put("Colmeia Parasita", new Faction[]{
			new Faction("Hospedeiros Voluntários", "ofereceram seus corpos por fé", true),
			new Faction("Exterminadores",          "queimam tudo que se mexe",      false),
			new Faction("A Voz Coletiva",          "falam por uma só boca",         true)
		});
		BY_THEME.put("Guerra Civil dos Necromantes", new Faction[]{
			new Faction("Casa do Mestre Antigo", "defendem a antiga escola",      true),
			new Faction("Aprendizes Traídos",    "querem queimar o legado",       true),
			new Faction("Neutros do Sudário",    "negociam com os dois lados",    false)
		});
		BY_THEME.put("Profecia Interrompida", new Faction[]{
			new Faction("Os Que Esperam",        "ainda creem na vinda",          false),
			new Faction("Hereges da Última Página", "queimaram a profecia",       true),
			new Faction("Escribas Mudos",        "copiam o texto perdido de cor", false)
		});
		BY_THEME.put("Santuário Afogado", new Faction[]{
			new Faction("Sacerdotes Afogados",   "rezam pelo deus submerso",     true),
			new Faction("Pescadores de Almas",   "trazem corpos à superfície",   false),
			new Faction("Os Que Não Bóiam",      "afundaram por escolha",        true)
		});
	}

	public static List<Faction> generate(String theme) {
		Faction[] themed = BY_THEME.get(theme);
		Faction[] pool   = themed != null ? themed : DEFAULT_POOL;

		ArrayList<Faction> out = new ArrayList<>();
		HashSet<String> seenNames = new HashSet<>();

		int count = 2 + Random.Int(2); // 2..3

		// Primeiro, tenta puxar do pool temático.
		for (int tries = 0; tries < pool.length * 2 && out.size() < count; tries++) {
			Faction picked = Random.element(pool);
			if (seenNames.add(picked.name)) {
				out.add(new Faction(picked.name, picked.role, picked.hostile));
			}
		}

		// Se o pool temático for pequeno, complementa com DEFAULT_POOL.
		for (int tries = 0; tries < DEFAULT_POOL.length * 2 && out.size() < count; tries++) {
			Faction picked = Random.element(DEFAULT_POOL);
			if (seenNames.add(picked.name)) {
				out.add(new Faction(picked.name, picked.role, picked.hostile));
			}
		}

		return out;
	}
}
