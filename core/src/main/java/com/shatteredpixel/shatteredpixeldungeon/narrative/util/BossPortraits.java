/*
 * Projeto Roguelike Narrativo Procedural — escolha de retrato do boss
 *
 * Mapeia bossIdentity → caminho do PNG em assets/narrative/.
 * Heurística por palavras-chave: itera MAPPINGS na ordem e retorna o
 * primeiro match. Como a iteração respeita a ordem do array, palavras
 * mais específicas devem vir antes das mais genéricas.
 *
 * Retratos disponíveis (8 + default = 9 visuais cobrindo ~80% das
 * identidades geradas por BossIdentityGenerator):
 *   - boss-rei-cinza           (default — reino caído, rei, trono)
 *   - boss-profeta-mudo        (profecia, sino, página, voz que cala)
 *   - boss-coveiro-antigo      (catacumbas, cava, cova, expedição)
 *   - boss-mente-raiz          (máquina antiga, raiz, colmeia, motor)
 *   - boss-santo-oco           (ritual de invocação, santuário afogado)
 *   - boss-sacerdote-insone    (sacerdote sem dormir, dúvida sagrada)
 *   - boss-mae-das-moscas      (praga, doença, putrefação)
 *   - boss-tecelao-ossos       (necromancia, ossos, casa dividida)
 *   - boss-sonhador-acordado   (sonho corrompido, pesadelo)
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.util;

public final class BossPortraits {

	private BossPortraits() {}

	private static final String PROFETA   = "narrative/boss-profeta-mudo_portrait.png";
	private static final String COVEIRO   = "narrative/boss-coveiro-antigo_portrait.png";
	private static final String MENTE     = "narrative/boss-mente-raiz_portrait.png";
	private static final String SANTO     = "narrative/boss-santo-oco_portrait.png";
	private static final String INSONE    = "narrative/boss-sacerdote-insone_portrait.png";
	private static final String MOSCAS    = "narrative/boss-mae-das-moscas_portrait.png";
	private static final String TECELAO   = "narrative/boss-tecelao-ossos_portrait.png";
	private static final String SONHADOR  = "narrative/boss-sonhador-acordado_portrait.png";
	private static final String DEFAULT_PATH = "narrative/boss-rei-cinza_portrait.png";

	private static final String[][] MAPPINGS = {
		// Ordem importa — mais específicas primeiro.

		// Sacerdote Insone (a palavra "sacerdote" sozinha vai aqui ANTES de "santo" puxar errado)
		{"insone", INSONE},
		{"sacerdote insone", INSONE},

		// Profeta Mudo / Profecia Interrompida
		{"profeta", PROFETA},
		{"profecia", PROFETA},
		{"última página", PROFETA},
		{"página arrancada", PROFETA},
		{"editou o fim", PROFETA},
		{"verso", PROFETA},
		{"sino", PROFETA},

		// Sonhador Acordado / Sonho Corrompido
		{"sonhador", SONHADOR},
		{"sonha você", SONHADOR},
		{"sonho", SONHADOR},
		{"pesadelo", SONHADOR},
		{"noite em claro", SONHADOR},
		{"atrás dos olhos", SONHADOR},
		{"seu sono", SONHADOR},

		// Coveiro Antigo / Catacumbas em Loop
		{"coveiro", COVEIRO},
		{"cripta", COVEIRO},
		{"cava", COVEIRO},
		{"cova", COVEIRO},
		{"sua cova", COVEIRO},
		{"voltam", COVEIRO},
		{"eu mesmo", COVEIRO},
		{"próximo eu", COVEIRO},
		{"número mil", COVEIRO},
		{"diário sem fim", COVEIRO},
		{"último nome", COVEIRO},
		{"expedição buscava", COVEIRO},

		// Mente Raiz / Máquina Antiga / Colmeia Parasita
		{"mente", MENTE},
		{"raiz", MENTE},
		{"motor", MENTE},
		{"autômato", MENTE},
		{"engrenagem", MENTE},
		{"cálculo final", MENTE},
		{"conta os vivos", MENTE},
		{"voz coletiva", MENTE},
		{"coro de mil", MENTE},
		{"rainha oca", MENTE},
		{"hospedeiro", MENTE},
		{"primeiro a abrir", MENTE},

		// Santo Oco / Santuário Afogado / Ritual de Invocação
		{"santo oco", SANTO},
		{"santo", SANTO},
		{"convocado", SANTO},
		{"quinto selo", SANTO},
		{"círculo que não fecha", SANTO},
		{"prece errada", SANTO},
		{"atende ao chamado", SANTO},
		{"sacerdote afogado", SANTO},
		{"deus submerso", SANTO},
		{"maré antiga", SANTO},
		{"sino no fundo", SANTO},
		{"não bóia", SANTO},
		{"prece que afundou", SANTO},
		{"afogado", SANTO},

		// Mãe das Moscas / Dungeon da Praga
		{"moscas", MOSCAS},
		{"mãe das moscas", MOSCAS},
		{"profeta apodrecido", MOSCAS},
		{"apodrecido", MOSCAS},
		{"doente", MOSCAS},
		{"contágio", MOSCAS},
		{"confessor de pus", MOSCAS},
		{"cura que veio tarde", MOSCAS},
		{"praga", MOSCAS},

		// Tecelão dos Ossos / Guerra Civil dos Necromantes
		{"tecelão", TECELAO},
		{"ossos", TECELAO},
		{"último mestre", TECELAO},
		{"aprendiz traída", TECELAO},
		{"coração ainda batendo", TECELAO},
		{"mestre dividido", TECELAO},
		{"casa que ruiu", TECELAO},

		// Reino Caído (fallback explícito antes do default)
		{"rei", DEFAULT_PATH},
		{"coroa", DEFAULT_PATH},
		{"trono", DEFAULT_PATH},
		{"corte", DEFAULT_PATH},
		{"herdeiro", DEFAULT_PATH}
	};

	public static String pathFor(String bossIdentity) {
		if (bossIdentity == null || bossIdentity.isEmpty()) return DEFAULT_PATH;
		String id = bossIdentity.toLowerCase();
		for (String[] m : MAPPINGS) {
			if (id.contains(m[0])) return m[1];
		}
		return DEFAULT_PATH;
	}
}
