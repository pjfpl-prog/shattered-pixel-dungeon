/*
 * Projeto Roguelike Narrativo Procedural — sistema de eventos
 *
 * Tipos de efeito que uma opção de evento pode aplicar.
 * O EventDirector decide o que cada um faz a partir de (kind, stringArg, intArg).
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.events;

public enum EventEffectKind {

	TEXT_ONLY,         // sem mecânica — só uma frase de resultado
	HEAL_PCT,          // intArg = % do HP máximo a curar (positivo)
	DAMAGE_PCT,        // intArg = % do HP máximo a dano (positivo, é subtraído)
	GIVE_GOLD,         // intArg = quantidade
	GIVE_XP,           // intArg = quantidade
	GIVE_ITEM,         // stringArg = simple class name de Item
	NPC_ATTITUDE,      // stringArg = NpcKind.name(); intArg = ordinal de Attitude
	MARK_QUEST_STEP,   // intArg = índice na mainQuestChain
	ADD_BUFF,          // stringArg = simple class name de Buff; intArg = duração (turnos)
	ADD_LORE,          // stringArg = corpo do fragmento; revelado imediatamente
	SET_FLAG,          // stringArg = nome da flag (eventFlags do AdventureSeed)
	HT_BONUS           // intArg = +HT permanente
}
