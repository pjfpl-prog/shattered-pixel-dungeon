/*
 * Projeto Roguelike Narrativo Procedural — utilitários pt-BR
 *
 * Helpers compartilhados pelos geradores narrativos para evitar texto
 * gramaticalmente errado quando preposições e artigos são concatenados
 * a substantivos articulados (ex.: "a Mente Raiz" + prep "de" → "da Mente Raiz").
 *
 * Convenção: substantivos articulados são strings que JÁ começam com
 * o artigo definido em minúscula seguido de espaço — "o X", "a Y",
 * "os Z", "as W". Os helpers reconhecem esse prefixo para aplicar
 * a contração correta.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.util;

public final class PtBr {

	private PtBr() {}

	public enum Gender { M, F }

	// "de" + artigo: "o X" → "do X", "a X" → "da X", "os X" → "dos X", "as X" → "das X".
	public static String contractDe(String articled) {
		if (articled == null) return "de";
		if (articled.startsWith("os ")) return "d" + articled;
		if (articled.startsWith("as ")) return "d" + articled;
		if (articled.startsWith("o "))  return "d" + articled;
		if (articled.startsWith("a "))  return "d" + articled;
		return "de " + articled;
	}

	// "em" + artigo: "o X" → "no X", etc.
	public static String contractEm(String articled) {
		if (articled == null) return "em";
		if (articled.startsWith("os ")) return "n" + articled;
		if (articled.startsWith("as ")) return "n" + articled;
		if (articled.startsWith("o "))  return "n" + articled;
		if (articled.startsWith("a "))  return "n" + articled;
		return "em " + articled;
	}

	// "a" (preposição) + artigo: "o X" → "ao X", "a X" → "à X" (crase),
	// "os X" → "aos X", "as X" → "às X".
	public static String contractA(String articled) {
		if (articled == null) return "a";
		if (articled.startsWith("os ")) return "a" + articled;
		if (articled.startsWith("o "))  return "a" + articled;
		if (articled.startsWith("as ")) return "às " + articled.substring(3);
		if (articled.startsWith("a "))  return "à "  + articled.substring(2);
		return "a " + articled;
	}

	public static String article(Gender g)        { return g == Gender.F ? "A" : "O"; }
	public static String demonstrative(Gender g)  { return g == Gender.F ? "Esta" : "Este"; }

	// Concatena "a" ou "o" ao radical para concordar com o gênero.
	// Ex.: pastParticiple("Encontrad", F) → "Encontrada".
	public static String pastParticiple(String stem, Gender g) {
		return stem + (g == Gender.F ? "a" : "o");
	}

	public static String cap(String s) {
		if (s == null || s.isEmpty()) return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static String lowerFirst(String s) {
		if (s == null || s.isEmpty()) return s;
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}
}
