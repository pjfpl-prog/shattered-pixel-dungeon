/*
 * Projeto Roguelike Narrativo Procedural — pool inicial de eventos
 *
 * Cada evento é definido estaticamente aqui. Para uma run específica,
 * EventScheduleGenerator escolhe quais entrarão e em que piso.
 *
 * Pool inicial — ~15 eventos cobrindo todos os efeitos básicos.
 * Pode crescer livremente: basta adicionar entradas em ALL e dar id único.
 *
 * Cores temáticas via prompt; efeitos mecânicos via EventEffect.
 */

package com.shatteredpixel.shatteredpixeldungeon.narrative.events;

import com.shatteredpixel.shatteredpixeldungeon.narrative.dialogue.NpcKind;
import com.shatteredpixel.shatteredpixeldungeon.narrative.npcs.NpcState.Attitude;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventBank {

	private EventBank() {}

	private static final Map<String, NarrativeEvent> BY_ID = new HashMap<>();
	private static final List<NarrativeEvent> ALL = new ArrayList<>();

	static {
		register(new NarrativeEvent(
			"poco_de_agua_obscura",
			"O Poço",
			"Você desce ao poço da masmorra. A água é negra e reflete sua própria sombra de um modo que não devia. Algo do fundo te chama.",
			1, 4, new String[]{}, new String[]{"horror", "mistério", "paranoia"},
			new EventOption("Beber.",
				"Você bebe. Por um instante vê tudo da masmorra de uma só vez, depois esquece.",
				EventEffect.heal(20), EventEffect.damage(10)),
			new EventOption("Atirar uma moeda.",
				"A moeda some sem som. Algo lhe paga de volta com gentileza.",
				EventEffect.gold(-20), EventEffect.xp(50)),
			new EventOption("Recuar.",
				"Você se afasta. O barulho do poço fica nas suas costas o dia inteiro.",
				EventEffect.text())
		));

		register(new NarrativeEvent(
			"corpo_no_corredor",
			"Um Corpo no Corredor",
			"Um aventureiro caído segura uma poção fechada. O selo ainda está intacto.",
			1, 6, new String[]{"Expedição Esquecida"}, new String[]{},
			new EventOption("Pegar a poção.",
				"Você guarda a poção. As mãos do morto ficam vazias, como deviam.",
				EventEffect.item("PotionOfHealing")),
			new EventOption("Procurar pertences.",
				"Você encontra moedas escondidas no forro do casaco.",
				EventEffect.gold(40)),
			new EventOption("Cobrir o corpo e seguir.",
				"Você cobre o rosto dele com o próprio mantelete. Algo dentro de você se acalma.",
				EventEffect.npc(NpcKind.GHOST.name(), Attitude.FRIENDLY.ordinal()),
				EventEffect.markQuestStep(0))
		));

		register(new NarrativeEvent(
			"altar_quebrado",
			"Altar Quebrado",
			"Um altar fendido no meio. Marcas de sangue antigo. O símbolo ainda é legível para quem souber ler.",
			3, 9, new String[]{"Ritual de Invocação", "Dungeon da Praga"}, new String[]{},
			new EventOption("Restaurar o símbolo com sangue seu.",
				"O símbolo brilha por um instante. Você sente algo se prender ao seu nome.",
				EventEffect.damage(15), EventEffect.buff("Bless", 100)),
			new EventOption("Apagar o símbolo.",
				"Você esfrega tudo até a pedra ficar nua. O ar parece menos pesado.",
				EventEffect.markQuestStep(1)),
			new EventOption("Ignorar.",
				"Você passa direto. O símbolo te observa nas costas.",
				EventEffect.text())
		));

		register(new NarrativeEvent(
			"sobrevivente_amarrado",
			"Um Sobrevivente",
			"Você encontra uma mulher amarrada a uma coluna. Ela diz que pertence a uma facção, mas não diz qual.",
			2, 8, new String[]{}, new String[]{},
			new EventOption("Libertar.",
				"Ela some pelas sombras prometendo lembrar do seu rosto.",
				EventEffect.xp(80), EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.FRIENDLY.ordinal())),
			new EventOption("Interrogar antes.",
				"Ela cospe nomes que você não esquecerá.",
				EventEffect.text(), EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.WARY.ordinal())),
			new EventOption("Deixar como está.",
				"Você segue. Os gritos abafados duram dois corredores.",
				EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.HOSTILE.ordinal()))
		));

		register(new NarrativeEvent(
			"livro_sussurrante",
			"Livro Sussurrante",
			"Um livro aberto sobre uma estante. As páginas viram sozinhas quando você não olha.",
			4, 12, new String[]{"Profecia Interrompida", "Sonho Corrompido"}, new String[]{"loucura", "mistério"},
			new EventOption("Ler em voz alta.",
				"Você lê. As palavras grudam no seu pensamento.",
				EventEffect.xp(120), EventEffect.damage(8)),
			new EventOption("Rasgar uma página.",
				"Você guarda o pedaço. O livro fecha sozinho atrás de você.",
				EventEffect.item("ScrollOfIdentify")),
			new EventOption("Queimar o livro.",
				"Cinzas. O ar fica mais leve. Algo, em algum lugar, cala a boca.",
				EventEffect.markQuestStep(2))
		));

		register(new NarrativeEvent(
			"forja_acesa",
			"Forja Acesa Sem Ninguém",
			"Uma forja aceita sem ninguém ao redor. Um martelo apoiado, ainda quente. Uma lâmina inacabada.",
			8, 14, new String[]{"Despertar de Máquina Antiga", "Guerra Civil dos Necromantes"}, new String[]{},
			new EventOption("Terminar a lâmina.",
				"Você bate por uma hora. A lâmina some quando você dorme, mas a habilidade fica.",
				EventEffect.xp(150)),
			new EventOption("Levar o martelo.",
				"Pesa mais do que devia.",
				EventEffect.item("Gold"), EventEffect.gold(80)),
			new EventOption("Apagar o fogo.",
				"O carvão estala protestando. Você não sabe explicar por que fez isso.",
				EventEffect.npc(NpcKind.BLACKSMITH.name(), Attitude.HOSTILE.ordinal()))
		));

		register(new NarrativeEvent(
			"mercador_vendado",
			"Mercador Vendado",
			"Um mercador de olhos vendados oferece negócio. Não pergunta seu nome.",
			5, 15, new String[]{}, new String[]{},
			new EventOption("Trocar 60 de ouro por uma poção.",
				"Você paga. Ele te entrega uma poção que não estava na mesa um segundo atrás.",
				EventEffect.gold(-60), EventEffect.item("PotionOfHealing")),
			new EventOption("Trocar 100 de ouro por um pergaminho.",
				"Ele sorri sem mostrar dentes.",
				EventEffect.gold(-100), EventEffect.item("ScrollOfMagicMapping")),
			new EventOption("Recusar.",
				"Ele guarda tudo lentamente. Você sente que algo foi anotado contra você.",
				EventEffect.text())
		));

		register(new NarrativeEvent(
			"voz_no_proprio_nome",
			"Sua Voz Te Chama",
			"Você ouve sua própria voz no fundo do corredor — chamando seu nome com afeto.",
			6, 18, new String[]{"Catacumbas em Loop Temporal", "Sonho Corrompido"}, new String[]{"paranoia", "loucura"},
			new EventOption("Responder.",
				"O eco para. Você sente um peso a menos por dois dias.",
				EventEffect.heal(40)),
			new EventOption("Ignorar.",
				"A voz repete por mais alguns segundos e depois cala.",
				EventEffect.text()),
			new EventOption("Gritar de volta com raiva.",
				"O corredor inteiro grita junto. Algo entende.",
				EventEffect.damage(20), EventEffect.xp(100))
		));

		register(new NarrativeEvent(
			"crianca_perdida",
			"A Criança",
			"Uma criança pequena, calçada para cima, caminha sozinha. Pergunta se você viu sua mãe.",
			3, 10, new String[]{}, new String[]{"horror", "tragédia"},
			new EventOption("Levar com você.",
				"Ela some ao virar o corredor. Você fica com uma moeda quente na mão.",
				EventEffect.gold(50)),
			new EventOption("Apontar o caminho de volta.",
				"Ela agradece e sobe a escada errada.",
				EventEffect.text()),
			new EventOption("Atacar.",
				"Não havia ninguém ali. Sua espada está pingando.",
				EventEffect.damage(25), EventEffect.npc(NpcKind.GHOST.name(), Attitude.HOSTILE.ordinal()))
		));

		register(new NarrativeEvent(
			"oferenda_de_facao",
			"Oferenda",
			"Sobre um pedestal, uma oferenda fresca: pão, sal, um pequeno espelho. Uma facção marcou ali.",
			7, 16, new String[]{}, new String[]{},
			new EventOption("Aceitar e jurar gratidão.",
				"Você come e se sente reconhecido — ou vigiado, depende da hora.",
				EventEffect.heal(15), EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.FRIENDLY.ordinal())),
			new EventOption("Comer e seguir.",
				"Era bom. Você não pensa no espelho por enquanto.",
				EventEffect.heal(10)),
			new EventOption("Quebrar o espelho.",
				"O som ecoa por muito tempo. Alguém anotou.",
				EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.HOSTILE.ordinal()))
		));

		register(new NarrativeEvent(
			"escolha_do_imp",
			"Uma Aposta do Imp",
			"Um Imp aparece de uma fresta. Aposta um item contra a sua sorte.",
			14, 22, new String[]{}, new String[]{},
			new EventOption("Apostar com gold.",
				"Você joga. Ele ri. Você ganha mas não tem certeza do que perdeu.",
				EventEffect.gold(-50), EventEffect.item("PotionOfMindVision")),
			new EventOption("Apostar com sangue.",
				"Você se corta. Ele aplaude.",
				EventEffect.damage(20), EventEffect.item("ScrollOfMagicMapping")),
			new EventOption("Empurrar de volta na fresta.",
				"Ele some xingando. Esse Imp tem boa memória.",
				EventEffect.npc(NpcKind.IMP.name(), Attitude.HOSTILE.ordinal()))
		));

		register(new NarrativeEvent(
			"cripta_aberta",
			"Cripta Aberta",
			"Uma cripta foi recentemente arrombada. Coisas faltam — coisas estão.",
			15, 22, new String[]{"Guerra Civil dos Necromantes", "Reino Caído"}, new String[]{},
			new EventOption("Selar de volta.",
				"Você empurra a lápide. O alívio é mútuo.",
				EventEffect.xp(180), EventEffect.markQuestStep(3)),
			new EventOption("Saquear.",
				"Você sai com bolsos pesados e algo lhe seguindo, talvez.",
				EventEffect.gold(150), EventEffect.damage(10)),
			new EventOption("Profanar mais.",
				"Você quebra o que sobrou. Algo lá dentro agradece.",
				EventEffect.xp(80), EventEffect.damage(20))
		));

		register(new NarrativeEvent(
			"profecia_lida",
			"Uma Profecia Lida em Voz Alta",
			"Você encontra uma página solta com uma profecia clara — clara demais. Ela cita seu nome.",
			18, 25, new String[]{"Profecia Interrompida"}, new String[]{},
			new EventOption("Acreditar.",
				"Você acredita. As mãos param de tremer pela primeira vez na masmorra.",
				EventEffect.heal(50)),
			new EventOption("Rasgar a página.",
				"Você rasga. Sente o nome se desfazer no ar.",
				EventEffect.xp(200)),
			new EventOption("Escrever um final diferente em cima.",
				"Você escreve. A tinta dura mais do que parecia.",
				EventEffect.markQuestStep(4))
		));

		register(new NarrativeEvent(
			"porta_do_fim",
			"A Porta do Fim",
			"Uma porta que não devia estar aqui. Atrás dela, algo respira no mesmo ritmo que você.",
			22, 24, new String[]{}, new String[]{},
			new EventOption("Bater três vezes.",
				"Algo bate de volta. Vocês dois esperam.",
				EventEffect.damage(10), EventEffect.xp(150)),
			new EventOption("Falar o nome do boss.",
				"O nome ecoa do outro lado, mais alto. Vocês se reconheceram.",
				EventEffect.markQuestStep(4)),
			new EventOption("Passar reto sem olhar.",
				"A porta range atrás de você por horas.",
				EventEffect.text())
		));
	}

	private static void register(NarrativeEvent e) {
		BY_ID.put(e.id, e);
		ALL.add(e);
	}

	public static NarrativeEvent get(String id) {
		return BY_ID.get(id);
	}

	public static List<NarrativeEvent> all() {
		return new ArrayList<>(ALL);
	}

	public static List<NarrativeEvent> candidatesFor(int depth, String theme, String tone) {
		ArrayList<NarrativeEvent> out = new ArrayList<>();
		for (NarrativeEvent e : ALL) {
			if (!e.acceptsDepth(depth)) continue;
			// Se o evento marca preferências, requer match. Sem preferências = sempre OK.
			boolean themeOk = e.themesPreferred.length == 0
					|| Arrays.asList(e.themesPreferred).contains(theme);
			boolean toneOk  = e.tonesPreferred.length == 0
					|| Arrays.asList(e.tonesPreferred).contains(tone);
			if (themeOk && toneOk) out.add(e);
		}
		return out;
	}
}
