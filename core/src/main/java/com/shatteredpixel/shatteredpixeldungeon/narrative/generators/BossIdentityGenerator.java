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
		"O Profeta Mudo", "O Tecelão dos Ossos", "A Coisa Que Resta",
		"O Primeiro a Descer", "A Boca do Fundo"
	};

	private static final Map<String, String[]> BY_THEME = new HashMap<>();
	static {
		BY_THEME.put("Reino Caído", new String[]{
			"O Último Rei", "A Coroa Sem Cabeça", "O Trono Vazio",
			"O Rei Que Desceu e Não Voltou", "A Corte de Pó", "O Herdeiro de Nada"
		});
		BY_THEME.put("Ritual de Invocação", new String[]{
			"O Sacerdote Insone", "O Convocado", "O Círculo Que Não Fecha",
			"Aquilo Que Atende ao Chamado", "O Quinto Selo", "A Resposta à Prece Errada"
		});
		BY_THEME.put("Despertar de Máquina Antiga", new String[]{
			"A Mente Raiz", "O Motor Eterno", "O Primeiro Autômato",
			"A Engrenagem-Mãe", "O Cálculo Final", "Aquele Que Conta os Vivos"
		});
		BY_THEME.put("Dungeon da Praga", new String[]{
			"O Profeta Apodrecido", "A Mãe das Moscas", "O Doente Que Nunca Morre",
			"O Primeiro Contágio", "O Confessor de Pus", "A Cura Que Veio Tarde"
		});
		BY_THEME.put("Sonho Corrompido", new String[]{
			"O Sonhador Acordado", "A Coisa Atrás dos Olhos", "O Pesadelo Antigo",
			"O Dono do Seu Sono", "A Última Noite em Claro", "Aquilo Que Sonha Você"
		});
		BY_THEME.put("Catacumbas em Loop Temporal", new String[]{
			"O Coveiro Antigo", "Eu Mesmo", "O Que Já Voltou",
			"O Próximo Eu", "A Volta de Número Mil", "Aquele Que Cava a Sua Cova"
		});
		BY_THEME.put("Expedição Esquecida", new String[]{
			"O Líder Perdido", "O Cartógrafo Cego", "O Diário Sem Fim",
			"O Que a Expedição Buscava", "O Último Nome da Lista", "A Raiz Que Sustenta"
		});
		BY_THEME.put("Colmeia Parasita", new String[]{
			"A Rainha Oca", "O Hospedeiro Original", "A Voz Coletiva",
			"O Primeiro a Abrir Espaço", "O Coro de Mil Bocas", "Nós"
		});
		BY_THEME.put("Guerra Civil dos Necromantes", new String[]{
			"O Tecelão dos Ossos", "O Último Mestre", "A Aprendiz Traída",
			"O Coração Ainda Batendo", "O Mestre Dividido", "A Casa Que Ruiu por Dentro"
		});
		BY_THEME.put("Profecia Interrompida", new String[]{
			"O Profeta Mudo", "O Escolhido Tarde", "A Página Arrancada",
			"Aquele Que Editou o Fim", "O Verso Que Faltava", "O Último a Descer"
		});
		BY_THEME.put("Santuário Afogado", new String[]{
			"O Sacerdote Afogado", "O Deus Submerso", "A Maré Antiga",
			"O Sino no Fundo", "Aquele Que Não Bóia", "A Prece Que Afundou"
		});
	}

	public static String generate(String theme) {
		String[] pool = BY_THEME.getOrDefault(theme, DEFAULT_POOL);
		return Random.element(pool);
	}
}
