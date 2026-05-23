/*
 * Projeto Roguelike Narrativo Procedural — Fase 1
 *
 * BossIdentityGenerator escolhe a identidade narrativa do "boss final"
 * da aventura. NÃO altera o boss mecânico (Yog-Dzewa segue como o boss
 * mecânico do piso 25); apenas dá a ele um rosto coerente com o tema
 * que foi sorteado para a run.
 *
 * Cada tema mapeia para um pool de identidades plausíveis. Temas
 * desconhecidos caem no pool DEFAULT.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.generators;

import com.watabou.utils.Random;

import java.util.HashMap;
import java.util.Map;

public final class BossIdentityGenerator {

	private BossIdentityGenerator() {}

	private static final String[] DEFAULT_POOL = {
		"O Último Rei", "O Sacerdote Insone", "A Mente Raiz", "O Santo Oco",
		"O Profeta Mudo", "O Tecelão dos Ossos"
	};

	private static final Map<String, String[]> BY_THEME = new HashMap<>();
	static {
		BY_THEME.put("Reino Caído", new String[]{
			"O Último Rei", "A Coroa Sem Cabeça", "O Trono Vazio"
		});
		BY_THEME.put("Ritual de Invocação", new String[]{
			"O Sacerdote Insone", "O Convocado", "O Círculo Que Não Fecha"
		});
		BY_THEME.put("Despertar de Máquina Antiga", new String[]{
			"A Mente Raiz", "O Motor Eterno", "O Primeiro Autômato"
		});
		BY_THEME.put("Dungeon da Praga", new String[]{
			"O Profeta Apodrecido", "A Mãe das Moscas", "O Doente Que Nunca Morre"
		});
		BY_THEME.put("Sonho Corrompido", new String[]{
			"O Sonhador Acordado", "A Coisa Atrás dos Olhos", "O Pesadelo Antigo"
		});
		BY_THEME.put("Catacumbas em Loop Temporal", new String[]{
			"O Coveiro Antigo", "Eu Mesmo", "O Que Já Voltou"
		});
		BY_THEME.put("Expedição Esquecida", new String[]{
			"O Líder Perdido", "O Cartógrafo Cego", "O Diário Sem Fim"
		});
		BY_THEME.put("Colmeia Parasita", new String[]{
			"A Rainha Oca", "O Hospedeiro Original", "A Voz Coletiva"
		});
		BY_THEME.put("Guerra Civil dos Necromantes", new String[]{
			"O Tecelão dos Ossos", "O Último Mestre", "A Aprendiz Traída"
		});
		BY_THEME.put("Profecia Interrompida", new String[]{
			"O Profeta Mudo", "O Escolhido Tarde", "A Página Arrancada"
		});
		BY_THEME.put("Santuário Afogado", new String[]{
			"O Sacerdote Afogado", "O Deus Submerso", "A Maré Antiga"
		});
	}

	public static String generate(String theme) {
		String[] pool = BY_THEME.getOrDefault(theme, DEFAULT_POOL);
		return Random.element(pool);
	}
}
