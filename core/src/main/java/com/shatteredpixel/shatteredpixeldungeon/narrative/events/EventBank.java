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

		// === Cadeia 1: Pacto com o Eco ===
		register(new NarrativeEvent(
			"eco_oferece_pacto",
			"O Eco Ofereceu",
			"Uma voz idêntica à sua sussurra do escuro: \"Eu te ajudo lá embaixo, se você me deixar te lembrar de algo lá em cima.\"",
			2, 5, new String[]{}, new String[]{"mistério", "paranoia"},
			new EventOption("Aceitar o pacto.",
				"Você sente algo se prender ao seu nome — como uma sombra à sua sombra.",
				EventEffect.flag("pacto_eco"), EventEffect.lore("Eu aceitei o pacto do eco no piso onde a voz era minha.")),
			new EventOption("Recusar com firmeza.",
				"O silêncio que segue é mais frio que antes.",
				EventEffect.damage(5)),
			new EventOption("Cuspir no chão e seguir.",
				"Você ouve a voz rir, longe.",
				EventEffect.npc(NpcKind.GHOST.name(), Attitude.WARY.ordinal()))
		));

		register(new NarrativeEvent(
			"eco_cobra",
			"O Eco Cobra",
			"A voz volta. Você precisa pagar agora — ela quer que você lembre.",
			11, 16, new String[]{}, new String[]{},
			new String[]{"pacto_eco"},
			new EventOption("Honrar o pacto: contar um nome que machuca.",
				"Você fala em voz alta. Algo se solta dentro de você. A voz parece se afastar, satisfeita.",
				EventEffect.xp(250), EventEffect.heal(40)),
			new EventOption("Mentir um nome inventado.",
				"A voz fica em silêncio. Depois ri.",
				EventEffect.damage(30), EventEffect.flag("eco_traido")),
			new EventOption("Gritar com a voz que ela suma.",
				"Ela some. Mas seu próprio nome não responde quando você o pensa.",
				EventEffect.damage(15), EventEffect.flag("eco_traido"))
		));

		register(new NarrativeEvent(
			"eco_se_vinga",
			"O Eco Se Vinga",
			"Você acorda. A voz sentou no peito.",
			18, 22, new String[]{}, new String[]{},
			new String[]{"eco_traido"},
			new EventOption("Pedir perdão.",
				"O peso continua, mas você consegue respirar.",
				EventEffect.damage(20)),
			new EventOption("Empurrar.",
				"Ela vai embora — mas leva alguma coisa.",
				EventEffect.damage(10), EventEffect.lore("Empurrei o eco. Ele levou parte do que eu era."))
		));

		// === Cadeia 2: Promessa ao Morto ===
		register(new NarrativeEvent(
			"explorador_pede",
			"Um Pedido Em Voz Baixa",
			"O explorador caído abre os olhos por um instante: \"Diga a Alma que eu cheguei até aqui. Promete?\"",
			1, 4, new String[]{}, new String[]{},
			new EventOption("Prometer.",
				"Ele suspira e fecha os olhos. Você guarda o nome.",
				EventEffect.flag("promessa_alma"), EventEffect.npc(NpcKind.GHOST.name(), Attitude.FRIENDLY.ordinal())),
			new EventOption("Pegar a corrente do pescoço dele.",
				"Você sai com o pingente. Não pergunta de quem é.",
				EventEffect.gold(30), EventEffect.npc(NpcKind.GHOST.name(), Attitude.HOSTILE.ordinal())),
			new EventOption("Fingir que não ouviu.",
				"Os olhos dele continuam abertos. Não piscam mais.",
				EventEffect.damage(5))
		));

		register(new NarrativeEvent(
			"fantasma_lembra",
			"Alma Te Encontra",
			"Uma figura translúcida cruza o corredor sem olhar pra você. Você reconhece o rosto da corrente.",
			10, 16, new String[]{}, new String[]{},
			new String[]{"promessa_alma"},
			new EventOption("Cumprir a promessa: contar o que viu.",
				"A figura para. Sorri por dentro. Some.",
				EventEffect.xp(300), EventEffect.htBonus(8),
				EventEffect.lore("Eu cumpri a promessa. Alma soube que ele tentou.")),
			new EventOption("Ficar quieto.",
				"Ela passa direto. Você sente o peso ficar.",
				EventEffect.damage(15))
		));

		// === Cadeia 3: A Lâmina ===
		register(new NarrativeEvent(
			"lamina_inacabada",
			"A Lâmina Inacabada",
			"Você passa pela forja vazia. Uma lâmina inacabada espera. Sua mão coça.",
			7, 12, new String[]{}, new String[]{},
			new EventOption("Terminar com cuidado.",
				"Hora e meia de trabalho. Você nunca foi ferreiro, mas hoje foi.",
				EventEffect.flag("lamina_terminada"), EventEffect.xp(150),
				EventEffect.npc(NpcKind.BLACKSMITH.name(), Attitude.FRIENDLY.ordinal())),
			new EventOption("Roubar o ferro bruto.",
				"Você sai com o material. A forja parece se lembrar de você.",
				EventEffect.gold(60), EventEffect.npc(NpcKind.BLACKSMITH.name(), Attitude.HOSTILE.ordinal())),
			new EventOption("Não tocar.",
				"Você segue. A coceira passa.",
				EventEffect.text())
		));

		register(new NarrativeEvent(
			"lamina_volta",
			"A Lâmina Volta",
			"Um morto-vivo te ataca empunhando a lâmina que você terminou. Ele para por um segundo, como se reconhecesse a mão dela.",
			17, 22, new String[]{}, new String[]{},
			new String[]{"lamina_terminada"},
			new EventOption("Falar com ele.",
				"Ele baixa a arma. Te entrega a lâmina e se desfaz.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.xp(200),
				EventEffect.lore("A lâmina voltou pra mão que a fez. Algo se fechou.")),
			new EventOption("Matar e tomar a lâmina.",
				"Você toma a lâmina. Mas a forja não vai te receber bem na próxima.",
				EventEffect.gold(100), EventEffect.flag("lamina_corrompida"))
		));

		// === Cadeia 4: Marca de Sangue ===
		register(new NarrativeEvent(
			"altar_oferenda_sangue",
			"Altar Pede Sangue",
			"Outro altar fendido. Esse pulsa quando você passa.",
			3, 6, new String[]{"Ritual de Invocação", "Dungeon da Praga"}, new String[]{},
			new EventOption("Oferecer sangue seu.",
				"O altar bebe. Algo se acomoda dentro de você.",
				EventEffect.damage(20), EventEffect.flag("marca_sangue"), EventEffect.htBonus(5)),
			new EventOption("Quebrar a pedra.",
				"Você bate com força. A pedra se parte. O ar fica mais limpo.",
				EventEffect.markQuestStep(1)),
			new EventOption("Cuspir e seguir.",
				"O altar para de pulsar. Talvez tenha entendido o recado.",
				EventEffect.text())
		));

		register(new NarrativeEvent(
			"sangue_volta",
			"Marcado",
			"Um morto se levanta do nada e não te ataca. Sussurra: \"Você é dos nossos.\"",
			14, 20, new String[]{}, new String[]{},
			new String[]{"marca_sangue"},
			new EventOption("Aceitar a marca.",
				"Ele assenta e some. Sua sombra está um pouco mais escura agora.",
				EventEffect.htBonus(10), EventEffect.flag("inimigo_dos_vivos")),
			new EventOption("Negar.",
				"Você nega. Ele cospe sangue no chão e some.",
				EventEffect.damage(25))
		));

		// === Cadeia 5: A Profecia ===
		register(new NarrativeEvent(
			"profecia_lida_inicial",
			"O Profeta Te Conhecia",
			"Um pergaminho velho cita seu nome — mas em uma versão que você ainda não usou.",
			6, 11, new String[]{"Profecia Interrompida"}, new String[]{},
			new EventOption("Adotar o nome novo.",
				"Você sussurra o nome pra si. Algo lá em cima anota.",
				EventEffect.flag("nome_profetico"), EventEffect.xp(120)),
			new EventOption("Queimar o pergaminho.",
				"Cinzas. Algumas letras flutuam mais que outras.",
				EventEffect.text()),
			new EventOption("Esconder o pergaminho no inventário.",
				"Você guarda. Não vai esquecer dele.",
				EventEffect.item("ScrollOfIdentify"))
		));

		register(new NarrativeEvent(
			"profecia_se_realiza",
			"A Profecia Reconhece",
			"Você ouve seu nome novo ecoar das paredes. Não foi você que disse.",
			20, 24, new String[]{}, new String[]{},
			new String[]{"nome_profetico"},
			new EventOption("Responder ao chamado.",
				"O corredor se abre. Você ganha um talento — e perde algo que não vai nomear.",
				EventEffect.htBonus(15), EventEffect.damage(20), EventEffect.flag("profecia_cumprida")),
			new EventOption("Recusar ser o tal.",
				"O eco para. Mas algo na masmorra parece... desapontado.",
				EventEffect.text())
		));

		// === Eventos avulsos (sem cadeia) ===

		register(new NarrativeEvent(
			"fonte_clara",
			"Uma Fonte Clara",
			"Água limpa demais pra esse lugar. Reflete uma versão sua que sorri.",
			1, 8, new String[]{}, new String[]{},
			new EventOption("Beber.",
				"A água é boa. Você não sabe explicar.",
				EventEffect.heal(35)),
			new EventOption("Encher uma bota e seguir.",
				"Você não vai precisar parar tão cedo de novo.",
				EventEffect.item("PotionOfHealing")),
			new EventOption("Quebrar a fonte.",
				"A água escorre. A masmorra absorve. Talvez ela precisasse.",
				EventEffect.xp(80))
		));

		register(new NarrativeEvent(
			"prisioneiro_grita",
			"Um Prisioneiro Grita",
			"Atrás de uma porta trancada, alguém grita seu nome. Você jura nunca ter dito o seu nome aqui.",
			5, 11, new String[]{}, new String[]{"horror", "paranoia"},
			new EventOption("Quebrar a porta.",
				"A sala está vazia. As paredes estão arranhadas de dentro.",
				EventEffect.damage(10), EventEffect.lore("Quebrei a porta. Quem gritava meu nome não estava lá.")),
			new EventOption("Conversar pela porta.",
				"A voz para de gritar e diz seu nome com afeto. Você não dorme bem por dois andares.",
				EventEffect.damage(5), EventEffect.flag("conheci_o_preso")),
			new EventOption("Seguir reto.",
				"Os gritos duram até você descer.",
				EventEffect.text())
		));

		register(new NarrativeEvent(
			"ratos_oferenda",
			"Ratos em Procissão",
			"Uma fila de ratos carrega algo brilhante para um buraco.",
			2, 7, new String[]{}, new String[]{},
			new EventOption("Pisar na fila.",
				"Eles fogem. Você pega a moeda. Algo na parede te observa.",
				EventEffect.gold(35), EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.HOSTILE.ordinal())),
			new EventOption("Seguir os ratos.",
				"Eles desaparecem por uma fresta. Você encontra um pergaminho seco.",
				EventEffect.item("ScrollOfIdentify")),
			new EventOption("Deixar a procissão.",
				"Você passa por cima. Não pisa. Os ratos olham por um segundo.",
				EventEffect.xp(60))
		));

		register(new NarrativeEvent(
			"espelho_quebrado",
			"Espelho Quebrado",
			"Um espelho quebrado em pedaços. Cada caco mostra uma versão sua diferente.",
			4, 12, new String[]{"Sonho Corrompido"}, new String[]{"loucura", "mistério"},
			new EventOption("Pegar o caco mais feliz.",
				"Você o guarda. Não vai olhar de novo.",
				EventEffect.heal(20), EventEffect.flag("caco_feliz")),
			new EventOption("Pegar o caco mais furioso.",
				"Você o guarda. Vai precisar dele.",
				EventEffect.htBonus(5), EventEffect.flag("caco_furia")),
			new EventOption("Quebrar todos.",
				"Você pisa em cada um. As versões somem uma por uma.",
				EventEffect.xp(100))
		));

		register(new NarrativeEvent(
			"medico_errante",
			"Médico Errante",
			"Um homem de máscara de pássaro oferece tratamento — sem perguntar do que.",
			6, 14, new String[]{}, new String[]{},
			new EventOption("Aceitar o tratamento.",
				"As mãos dele são frias. Você se sente melhor.",
				EventEffect.heal(50), EventEffect.gold(-30)),
			new EventOption("Vender informação por gold.",
				"Você conta tudo que viu. Ele anota com pena de pena.",
				EventEffect.gold(60), EventEffect.flag("informei_medico")),
			new EventOption("Atacar.",
				"A máscara cai. Você não esquece o que vê.",
				EventEffect.damage(15), EventEffect.xp(120))
		));

		register(new NarrativeEvent(
			"livro_amaldicoado",
			"Livro Amaldiçoado",
			"Um livro vibra ao ser tocado. Páginas pulam sozinhas.",
			8, 16, new String[]{}, new String[]{},
			new EventOption("Ler até o fim.",
				"Você lê. As palavras grudam.",
				EventEffect.xp(180), EventEffect.damage(15)),
			new EventOption("Guardar pra estudar depois.",
				"Você guarda. Ele para de vibrar.",
				EventEffect.item("ScrollOfIdentify")),
			new EventOption("Queimar imediatamente.",
				"O fogo dura mais do que devia. Quando passa, algo cai do teto: uma moeda.",
				EventEffect.gold(80))
		));

		register(new NarrativeEvent(
			"sala_dos_espelhos",
			"Sala dos Espelhos",
			"Uma sala cheia de espelhos. Suas imagens não se movem juntas com você.",
			12, 20, new String[]{}, new String[]{"paranoia", "loucura"},
			new EventOption("Sair sem olhar.",
				"Você fecha os olhos e atravessa. Funciona.",
				EventEffect.text()),
			new EventOption("Estudar cada um.",
				"Você aprende algo sobre si que vai te servir.",
				EventEffect.htBonus(5), EventEffect.xp(100)),
			new EventOption("Quebrar todos.",
				"O som não para por minutos. Você ganha algo. Você perde algo.",
				EventEffect.gold(120), EventEffect.damage(25))
		));

		register(new NarrativeEvent(
			"jardim_secreto",
			"Jardim Improvável",
			"No meio da pedra, um jardim com uma flor que abre só pra você.",
			9, 18, new String[]{}, new String[]{"esperança"},
			new EventOption("Pegar a flor.",
				"Ela some na sua mão. O cheiro fica.",
				EventEffect.heal(60), EventEffect.flag("flor_unica")),
			new EventOption("Deixar e cuidar do jardim.",
				"Você tira ervas daninhas por um tempo. O jardim agradece.",
				EventEffect.xp(150), EventEffect.npc(NpcKind.GHOST.name(), Attitude.FRIENDLY.ordinal())),
			new EventOption("Queimar o jardim.",
				"Cheira a outono na masmorra agora. Você não saberia explicar por quê.",
				EventEffect.gold(60), EventEffect.damage(10))
		));

		register(new NarrativeEvent(
			"oferenda_da_facao",
			"Mesa Preparada",
			"Uma mesa com três pratos. Os assentos têm nomes — um é o seu.",
			11, 18, new String[]{}, new String[]{},
			new EventOption("Sentar e comer.",
				"A comida é boa. Você sente um peso a menos por dias.",
				EventEffect.heal(40), EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.FRIENDLY.ordinal()),
				EventEffect.flag("comi_com_eles")),
			new EventOption("Virar a mesa.",
				"O ruído ecoa. Quem ia comer ali não vai gostar.",
				EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.HOSTILE.ordinal()), EventEffect.xp(100)),
			new EventOption("Trocar de assento e sentar.",
				"Você sentou no lugar de outro. Ninguém aparece. Você come duas porções.",
				EventEffect.heal(60), EventEffect.flag("sentei_no_lugar_errado"))
		));

		register(new NarrativeEvent(
			"convite_da_imp",
			"O Imp Te Convida",
			"Um Imp aponta uma fresta: \"Aqui tem um atalho, aventureiro. Mas custa.\"",
			14, 20, new String[]{}, new String[]{},
			new EventOption("Pagar 150 ouro pelo atalho.",
				"Você passa. Sai do outro lado um andar mais experiente.",
				EventEffect.gold(-150), EventEffect.xp(300)),
			new EventOption("Tentar enganar.",
				"Ele percebe. Você sai com menos do que entrou.",
				EventEffect.gold(-50), EventEffect.damage(15),
				EventEffect.npc(NpcKind.IMP.name(), Attitude.HOSTILE.ordinal())),
			new EventOption("Recusar com gentileza.",
				"Ele sorri. Você ganha um respeito que pode importar.",
				EventEffect.npc(NpcKind.IMP.name(), Attitude.FRIENDLY.ordinal()))
		));

		register(new NarrativeEvent(
			"cripta_falante",
			"Cripta Que Fala",
			"Uma lápide fala seu nome — não em voz, em pensamento.",
			17, 23, new String[]{}, new String[]{},
			new EventOption("Responder mentalmente.",
				"Você sente a presença ficar mais firme. Vai te ajudar — ou cobrar, depois.",
				EventEffect.htBonus(8), EventEffect.flag("ouvi_a_cripta")),
			new EventOption("Tapar os ouvidos.",
				"Não funciona. Você desce com a voz junto.",
				EventEffect.damage(10)),
			new EventOption("Selar a lápide com pedra.",
				"Você empilha pedras até a voz parar. Demora.",
				EventEffect.xp(150))
		));

		register(new NarrativeEvent(
			"crianca_no_corredor",
			"A Criança Volta",
			"A mesma criança de antes — ou outra. Pergunta de novo onde está a mãe.",
			10, 18, new String[]{}, new String[]{},
			new EventOption("Acompanhar até a escada.",
				"Vocês sobem juntos um lance. Ela some no meio. Algo cai do bolso dela.",
				EventEffect.gold(70), EventEffect.lore("A criança caiu. Eu peguei a moeda quente.")),
			new EventOption("Negar tudo.",
				"Você diz que nunca viu ninguém. Ela acredita. Você não.",
				EventEffect.damage(8)),
			new EventOption("Perguntar quem é a mãe.",
				"Ela responde um nome que você conhece — talvez seu. Talvez não.",
				EventEffect.flag("nome_da_mae"), EventEffect.xp(120))
		));

		register(new NarrativeEvent(
			"mercador_no_fim",
			"Último Mercador",
			"O mercador vendado ainda está aqui. Diz que aceita troca de coisas que não cabem no inventário.",
			15, 22, new String[]{}, new String[]{},
			new EventOption("Vender uma memória ruim.",
				"Você não lembra mais do que vendeu. Ganha tempo.",
				EventEffect.gold(120), EventEffect.buff("Bless", 50)),
			new EventOption("Vender uma promessa.",
				"Você ouve o tilintar das moedas. Algo te aperta o peito por uns minutos.",
				EventEffect.gold(180), EventEffect.flag("vendi_promessa")),
			new EventOption("Comprar de volta o que foi vendido.",
				"Ele sorri sem dentes. Te devolve uma sensação esquecida.",
				EventEffect.gold(-100), EventEffect.heal(30))
		));

		register(new NarrativeEvent(
			"sino_silencioso",
			"O Sino Silencioso",
			"Um sino enorme. Sem badalo. Mas a corda ainda está aqui.",
			18, 24, new String[]{}, new String[]{},
			new EventOption("Puxar a corda.",
				"Um som que não é som ecoa pela masmorra. Algo lá embaixo escuta.",
				EventEffect.damage(15), EventEffect.markQuestStep(3),
				EventEffect.flag("toquei_o_sino")),
			new EventOption("Cortar a corda.",
				"O sino cai e quebra o chão. Você descobre um buraco que leva a algum lugar mais perto.",
				EventEffect.xp(200)),
			new EventOption("Ler a inscrição na base.",
				"Você lê. As letras desaparecem depois de lidas.",
				EventEffect.lore("Li a base do sino: 'O fim chama quem soube ouvir.'"),
				EventEffect.htBonus(5))
		));

		register(new NarrativeEvent(
			"sussurro_do_boss",
			"O Boss Te Sussurra",
			"Você sente a voz do boss final pela primeira vez. Calma. Educada. Te chama pelo apelido de infância.",
			20, 24, new String[]{}, new String[]{},
			new EventOption("Responder com seu nome verdadeiro.",
				"Silêncio do outro lado. Vocês se reconheceram.",
				EventEffect.flag("falei_meu_nome"), EventEffect.htBonus(10)),
			new EventOption("Ignorar e seguir.",
				"A voz volta, pior, mais perto.",
				EventEffect.damage(20)),
			new EventOption("Negociar.",
				"Você propõe um acordo. A voz ri. Algumas moedas caem do teto.",
				EventEffect.gold(150), EventEffect.flag("tentei_negociar"))
		));

		register(new NarrativeEvent(
			"pedra_da_paciencia",
			"Pedra da Paciência",
			"Uma pedra lisa, amornada por algo. Diz a história de que se você contar tudo a ela, ela carrega pra você.",
			6, 14, new String[]{}, new String[]{"tragédia", "decadência"},
			new EventOption("Contar tudo.",
				"Você fala por horas. Quando termina, está mais leve. A pedra está mais pesada.",
				EventEffect.heal(45), EventEffect.buff("Bless", 80)),
			new EventOption("Contar só uma coisa.",
				"Você escolhe a pior. A pedra absorve. Funciona em parte.",
				EventEffect.heal(20)),
			new EventOption("Atirar a pedra ao chão.",
				"Ela racha. Não conta nada de volta.",
				EventEffect.xp(100))
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
