/*
 * Projeto Roguelike Narrativo Procedural — one-shot RPG mode
 *
 * Base abstrata dos capítulos da one-shot. Estende SewerLevel
 * (mantém builder/painter/transitions do sewer pra preservar
 * geração correta) e adiciona:
 *   - 1 SecretRoom adicional garantida em todo capítulo (passagem secreta)
 *   - +1 item mágico extra spawnado (drop bom melhorado)
 *
 * Subclasses Chapter1..5 só configuram color1/color2 e mob extras
 * via NarrativeSpawns.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.levels;

import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.secret.SecretRoom;
import com.watabou.utils.Random;

import java.util.ArrayList;

public abstract class NarrativeChapter extends SewerLevel {

	@Override
	protected ArrayList<Room> initRooms() {
		ArrayList<Room> rooms = super.initRooms();
		// Passagem secreta garantida — 50% Oráculo, 50% sala padrão.
		Room extra;
		if (Random.Int(2) == 0) {
			extra = new SecretOracleRoom();
		} else {
			extra = SecretRoom.createRoom();
		}
		if (extra != null) rooms.add(extra);
		return rooms;
	}

	@Override
	protected void createItems() {
		super.createItems();
		int length = com.shatteredpixel.shatteredpixeldungeon.narrative.NarrativeDirector.oneShotLength();
		// Quantos extras por piso: Conto (5) = 3; Saga (15) = 2; Lenda (20) = 1.
		int extras = length <= 5 ? 3 : (length <= 15 ? 2 : 1);
		for (int i = 0; i < extras; i++) {
			Item it;
			switch (Random.Int(3)) {
				case 0:  it = new PotionOfHealing();    break;
				case 1:  it = new ScrollOfIdentify();   break;
				default: it = new ScrollOfMagicMapping();
			}
			addItemToSpawn(it);
		}
		// Bônus aleatório da pool padrão de scrolls/potions
		// — mais comum em runs curtas.
		int bonusChance = length <= 5 ? 100 : (length <= 15 ? 50 : 30);
		if (Random.Int(100) < bonusChance) {
			addItemToSpawn(Generator.random(Random.Int(2) == 0
					? Generator.Category.POTION
					: Generator.Category.SCROLL));
		}
	}
}
