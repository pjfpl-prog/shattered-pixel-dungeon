/*
 * Projeto Roguelike Narrativo Procedural — Fase 1
 *
 * ArtifactLoreGenerator monta uma descrição procedural única para
 * cada um dos 15 artefatos do jogo, válida durante toda a run.
 *
 * NÃO altera mecânica nenhuma — só anexa um parágrafo descritivo
 * mostrado em Artifact.info() quando o jogador inspeciona o item.
 * Cada artefato tem um arquétipo (relíquia/instrumento/grimório/
 * vestimenta/ferramenta) com gênero gramatical, para que artigos,
 * demonstrativos e particípios concordem corretamente em pt-BR.
 *
 * Contrações de preposição+artigo vêm da camada compartilhada PtBr.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.generators;

import com.shatteredpixel.shatteredpixeldungeon.narrative.util.PtBr;
import com.shatteredpixel.shatteredpixeldungeon.narrative.util.PtBr.Gender;
import com.watabou.utils.Random;

import java.util.HashMap;
import java.util.Map;

public final class ArtifactLoreGenerator {

	private ArtifactLoreGenerator() {}

	private static final class Archetype {
		final String name;
		final Gender gender;
		Archetype(String name, Gender gender) {
			this.name   = name;
			this.gender = gender;
		}
	}

	private static final Map<String, Archetype> ARCHETYPES = new HashMap<>();
	static {
		ARCHETYPES.put("AlchemistsToolkit",    new Archetype("ferramenta",  Gender.F));
		ARCHETYPES.put("CapeOfThorns",         new Archetype("vestimenta",  Gender.F));
		ARCHETYPES.put("ChaliceOfBlood",       new Archetype("relíquia",    Gender.F));
		ARCHETYPES.put("CloakOfShadows",       new Archetype("vestimenta",  Gender.F));
		ARCHETYPES.put("DriedRose",            new Archetype("relíquia",    Gender.F));
		ARCHETYPES.put("EtherealChains",       new Archetype("instrumento", Gender.M));
		ARCHETYPES.put("HolyTome",             new Archetype("grimório",    Gender.M));
		ARCHETYPES.put("HornOfPlenty",         new Archetype("instrumento", Gender.M));
		ARCHETYPES.put("LloydsBeacon",         new Archetype("instrumento", Gender.M));
		ARCHETYPES.put("MasterThievesArmband", new Archetype("vestimenta",  Gender.F));
		ARCHETYPES.put("SandalsOfNature",      new Archetype("vestimenta",  Gender.F));
		ARCHETYPES.put("SkeletonKey",          new Archetype("ferramenta",  Gender.F));
		ARCHETYPES.put("TalismanOfForesight",  new Archetype("relíquia",    Gender.F));
		ARCHETYPES.put("TimekeepersHourglass", new Archetype("relíquia",    Gender.F));
		ARCHETYPES.put("UnstableSpellbook",    new Archetype("grimório",    Gender.M));
	}

	private static final String[] OWNERS = {
		"o Rei Cinza", "o Sacerdote Insone", "a Mente Raiz", "o Santo Oco",
		"o Arquivista Ósseo", "o Monge do Véu", "o Coveiro Antigo",
		"o Profeta Mudo", "o Tecelão dos Ossos"
	};

	private static final String[] PLACES = {
		"as Catacumbas", "o Abismo", "a Capela Inacabada", "a Necrópole",
		"o Santuário Afogado", "a Forja Vazia", "a Biblioteca Queimada", "a Cripta Tardia"
	};

	private static final String[] FATES = {
		"foi enterrado com seu dono",
		"trocou de mãos por sangue",
		"foi escondido em vão",
		"caiu de uma mão amaldiçoada",
		"passou de geração em geração até esta",
		"não devia ter sido encontrado"
	};

	public static Map<String, String> generate(String theme, String tone) {
		HashMap<String, String> out = new HashMap<>();
		for (Map.Entry<String, Archetype> entry : ARCHETYPES.entrySet()) {
			out.put(entry.getKey(), renderLine(entry.getValue(), theme, tone));
		}
		return out;
	}

	private static String renderLine(Archetype arch, String theme, String tone) {
		String owner = Random.element(OWNERS);
		String place = Random.element(PLACES);
		String fate  = Random.element(FATES);

		String dem      = PtBr.demonstrative(arch.gender);
		String demLower = dem.toLowerCase();
		String found    = PtBr.pastParticiple("Encontrad", arch.gender);

		int t = Random.Int(4);
		switch (t) {
			case 0:
				return dem + " " + arch.name + " pertenceu " + PtBr.contractA(owner)
						+ " antes de " + theme.toLowerCase() + ". " + PtBr.cap(fate) + ".";
			case 1:
				return found + " " + PtBr.contractEm(place) + ", " + demLower + " " + arch.name
						+ " ainda carrega o cheiro de " + tone + ".";
			case 2:
				return PtBr.cap(owner) + " escreveu sobre " + demLower + " " + arch.name
						+ " na noite em que tudo começou.";
			case 3:
			default:
				return "Dizem que " + demLower + " " + arch.name + " " + fate + " — e voltou.";
		}
	}
}
