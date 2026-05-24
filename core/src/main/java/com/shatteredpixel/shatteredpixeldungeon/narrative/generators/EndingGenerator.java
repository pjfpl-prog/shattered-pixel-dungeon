/*
 * Projeto Roguelike Narrativo Procedural — ending procedural
 *
 * Monta o epílogo da aventura combinando:
 *   - flags setadas pelas escolhas em eventos (cadeias)
 *   - steps da quest chain marcados como completed
 *   - atitude geral dos NPCs (FRIENDLY/WARY/HOSTILE)
 *   - título da aventura e identidade do boss
 *
 * Cada bloco é opcional — se nenhuma das condições disparou, o ending
 * fica curto e neutro. Quanto mais o jogador interagiu com a narrativa,
 * mais rico o final.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.generators;

import com.shatteredpixel.shatteredpixeldungeon.narrative.NarrativeDirector;
import com.shatteredpixel.shatteredpixeldungeon.narrative.dialogue.NpcKind;
import com.shatteredpixel.shatteredpixeldungeon.narrative.models.AdventureSeed;
import com.shatteredpixel.shatteredpixeldungeon.narrative.npcs.NpcState;
import com.shatteredpixel.shatteredpixeldungeon.narrative.npcs.NpcState.Attitude;
import com.shatteredpixel.shatteredpixeldungeon.narrative.quests.QuestStep;

public final class EndingGenerator {

	private EndingGenerator() {}

	public static String generate() {
		AdventureSeed seed = NarrativeDirector.seed();
		if (seed == null) return "Você sobe à superfície carregando algo que ninguém mais entenderá.";

		StringBuilder sb = new StringBuilder();
		sb.append("_").append(seed.adventureTitle).append("_ — Epílogo\n\n");

		sb.append("Você sobe à superfície. ");
		if (!seed.finalBossIdentity.isEmpty()) {
			sb.append(seed.finalBossIdentity).append(" ficou pra trás, em silêncio definitivo. ");
		}
		sb.append("Mas algo de você ficou junto.\n");

		// === Cadeias / flags ===

		if (seed.eventFlags.contains("pacto_eco")) {
			if (seed.eventFlags.contains("eco_traido")) {
				sb.append("\nO eco que te seguia desde o piso de cima nunca foi pago. ")
				  .append("Você sente, mesmo no sol, que ele continua sussurrando seu nome.\n");
			} else {
				sb.append("\nO eco que firmou pacto contigo se desfez quando você cumpriu sua parte. ")
				  .append("Você não vai esquecer do nome que entregou, mas pelo menos não ouve mais ele.\n");
			}
		}

		if (seed.eventFlags.contains("promessa_alma")) {
			sb.append("\nVocê cumpriu a promessa ao explorador caído. ")
			  .append("Alma, quem quer que ela tenha sido, soube que ele chegou longe.\n");
		}

		if (seed.eventFlags.contains("lamina_terminada")) {
			sb.append("\nUma lâmina inacabada virou sua nas profundezas. ")
			  .append("O ferreiro lembra. Vai te receber bem se você voltar.\n");
		}

		if (seed.eventFlags.contains("marca_sangue")) {
			if (seed.eventFlags.contains("inimigo_dos_vivos")) {
				sb.append("\nO altar te marcou e você aceitou a herança. ")
				  .append("Os vivos olham torto pra você agora. Os mortos, não.\n");
			} else {
				sb.append("\nO altar te marcou, mas você recusou ser dos deles. ")
				  .append("Eles vão tentar de novo.\n");
			}
		}

		if (seed.eventFlags.contains("profecia_cumprida")) {
			sb.append("\nA profecia te reconheceu — e você respondeu. ")
			  .append("O nome que adotou lá embaixo é o que você usa agora.\n");
		} else if (seed.eventFlags.contains("nome_profetico")) {
			sb.append("\nVocê adotou um nome profético mas nunca o respondeu de volta. ")
			  .append("Ainda há uma página em branco em algum lugar.\n");
		}

		if (seed.eventFlags.contains("toquei_o_sino")) {
			sb.append("\nO sino sem badalo ainda ressoa onde antes não havia som.\n");
		}

		if (seed.eventFlags.contains("falei_meu_nome")) {
			sb.append("\nVocê disse seu nome verdadeiro ao boss antes de derrotá-lo. ")
			  .append("Quem entende esse tipo de coisa diz que foi por isso que venceu.\n");
		}

		// === Quest chain ===

		int completed = 0;
		int total = seed.mainQuestChain.size();
		for (QuestStep s : seed.mainQuestChain) {
			if (s.completed) completed++;
		}
		if (total > 0) {
			sb.append("\nDa cadeia desta aventura, você cumpriu ").append(completed)
			  .append(" de ").append(total).append(" passos. ");
			if (completed == total) {
				sb.append("Tudo no lugar — uma raridade.\n");
			} else if (completed == 0) {
				sb.append("Nada foi cumprido formalmente, mas o boss caiu mesmo assim.\n");
			} else {
				sb.append("Alguns capítulos ficaram em aberto.\n");
			}
		}

		// === Atitude dos NPCs ===

		int friendly = 0, hostile = 0;
		for (NpcKind kind : NpcKind.values()) {
			NpcState st = NarrativeDirector.npcState(kind);
			if (st == null) continue;
			if (st.attitude == Attitude.FRIENDLY) friendly++;
			else if (st.attitude == Attitude.HOSTILE) hostile++;
		}
		if (friendly >= 3) {
			sb.append("\nVocê desce um herói. Os habitantes da masmorra que sobreviveram falam bem do seu nome.\n");
		} else if (hostile >= 3) {
			sb.append("\nVocê desce alguém que ninguém vai querer ver de novo. Funcionou.\n");
		}

		// === Fechamento ===

		sb.append("\nA aventura ").append(seed.adventureTitle.toLowerCase()).append(" acabou. ");
		sb.append("Uma outra começa quando a próxima run for sorteada.");

		return sb.toString();
	}
}
