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

		// ============================================================
		// BLOCO 1 — Necromancia, Ossos e a Guerra Civil dos Necromantes
		// ============================================================

		// === Cadeia 6: O Tecelão dos Ossos ===
		register(new NarrativeEvent(
			"tecelao_encontro",
			"O Tear de Ossos",
			"Numa sala sem porta, um vulto encurvado passa fios brancos por um tear. Os fios são tendões; a trama, um rosto que você quase reconhece. Ele não se vira: \"Falta uma costela na borda. Você tem de sobra.\"",
			3, 8, new String[]{}, new String[]{"horror", "mistério"},
			new EventOption("Oferecer uma costela sua.",
				"A dor é breve e limpa. O rosto no tear abre os olhos e te encara com gratidão obscena.",
				EventEffect.damage(15), EventEffect.flag("tecelao_visto"), EventEffect.flag("dei_costela"),
				EventEffect.lore("Dei uma costela ao Tecelão. O rosto no tear era quase o meu.")),
			new EventOption("Perguntar quem ele tece.",
				"\"Todos que descem. Você já está aqui há mais tempo do que pensa.\" O tear range. Você sai sem responder.",
				EventEffect.flag("tecelao_visto"), EventEffect.xp(90)),
			new EventOption("Cortar os fios.",
				"Você rasga a trama. O rosto se desfaz num grito sem boca. O vulto finalmente se vira — e não tem cabeça.",
				EventEffect.damage(10), EventEffect.flag("tecelao_visto"), EventEffect.flag("cortei_o_tear"))
		));

		register(new NarrativeEvent(
			"tecelao_pedido",
			"O Tecelão Cobra",
			"O vulto encurvado te encontra de novo, mais fundo. \"O tear cresce. Preciso de nomes agora, não de ossos. Me dê o nome de alguém que você deixou morrer.\"",
			9, 15, new String[]{}, new String[]{},
			new String[]{"tecelao_visto"},
			new EventOption("Dar o nome verdadeiro.",
				"Você fala baixo. O tear absorve as sílabas e fica mais quente. Algo em você desencardece.",
				EventEffect.xp(220), EventEffect.heal(25), EventEffect.flag("tecelao_servo")),
			new EventOption("Dar um nome falso.",
				"O tear hesita, depois aceita. Mas o vulto te observa com a cabeça que não tem.",
				EventEffect.flag("tecelao_servo"), EventEffect.flag("menti_ao_tecelao")),
			new EventOption("Recusar e mandar tecer o próprio nome.",
				"\"Eu já teci. Há muito.\" Ele aponta o tear: no centro da trama, está você. O fio ainda não acabou.",
				EventEffect.damage(20), EventEffect.flag("tecelao_inimigo"))
		));

		register(new NarrativeEvent(
			"tecelao_divida",
			"O Que o Tear Devolve",
			"O tear terminou uma peça: uma mortalha leve como respiração. O Tecelão a estende. \"Vista. É sua medida exata. Sempre foi.\"",
			14, 19, new String[]{}, new String[]{"decadência", "tragédia"},
			new String[]{"tecelao_servo"},
			new EventOption("Vestir a mortalha.",
				"Cai sobre você como água morna. Você não sente mais frio na masmorra — nem nada parecido com medo.",
				EventEffect.htBonus(12), EventEffect.buff("Bless", 120), EventEffect.flag("vesti_mortalha")),
			new EventOption("Guardar dobrada.",
				"Você dobra a peça e a guarda. Pesa pouco. Pesa o tempo todo.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.flag("guardei_mortalha")),
			new EventOption("Devolver: \"Ainda não.\"",
				"O Tecelão assenta com a ausência de cabeça. \"Paciência é a única coisa que ensino bem.\"",
				EventEffect.xp(160))
		));

		register(new NarrativeEvent(
			"tecelao_verdade",
			"A Trama Inteira",
			"Você encontra a sala maior do tear: tapeçaria do teto ao chão, milhares de rostos. No alto, o Tecelão tem sua própria face na trama — está se tecendo a si mesmo, fio após fio, há eras.",
			18, 23, new String[]{}, new String[]{},
			new String[]{"tecelao_servo"},
			new EventOption("Perguntar por que ele não termina.",
				"\"Porque o último fio é a morte, herói. E eu prometi a alguém que não morreria antes dela voltar.\" A tapeçaria treme.",
				EventEffect.xp(280), EventEffect.flag("soube_da_promessa"),
				EventEffect.lore("O Tecelão se tece há eras porque prometeu não morrer antes de alguém voltar.")),
			new EventOption("Procurar seu próprio rosto na trama.",
				"Você o acha. Ao lado, há um fio solto esperando — o próximo a ser puxado.",
				EventEffect.htBonus(8), EventEffect.flag("vi_meu_fio"))
		));

		register(new NarrativeEvent(
			"tecelao_escolha",
			"O Último Fio",
			"O Tecelão te chama ao tear pela última vez. \"Estou cansado. Termine minha trama — ou desfaça-a. Os dois me libertam. Só um te custa.\"",
			21, 24, new String[]{}, new String[]{},
			new String[]{"soube_da_promessa"},
			new EventOption("Puxar o último fio e deixá-lo morrer.",
				"O vulto desmorona num monte de linha. A mortalha em você fica mais leve. Algo, enfim, descansa.",
				EventEffect.xp(350), EventEffect.heal(40), EventEffect.flag("tecelao_em_paz")),
			new EventOption("Desfazer a trama inteira pra libertar os rostos.",
				"Você puxa do avesso. Mil faces se desmancham em alívio — a sua entre elas, por um segundo apavorante.",
				EventEffect.htBonus(15), EventEffect.damage(25), EventEffect.flag("desfiz_a_trama")),
			new EventOption("Recusar: \"Teça você mesmo o seu fim.\"",
				"O Tecelão ri pela primeira vez. \"Então me faça companhia até lá.\" Ele volta ao tear, e seu fio fica perto do dele.",
				EventEffect.flag("recusei_o_tear"), EventEffect.htBonus(6))
		));

		register(new NarrativeEvent(
			"tecelao_inimigo_volta",
			"A Trama Te Procura",
			"Fios brancos escorrem pelas paredes atrás de você — o tear veio inteiro, arrastando-se. O Tecelão sem cabeça caminha dentro dele. \"Você se recusou a ser tecido. Tudo bem. Eu teço à força.\"",
			20, 24, new String[]{}, new String[]{"horror"},
			new String[]{"tecelao_inimigo"},
			new EventOption("Queimar os fios com o que tiver.",
				"O cheiro é insuportável, mas funciona. O tear recua chiando. Você ganha terreno e fôlego.",
				EventEffect.damage(20), EventEffect.xp(260)),
			new EventOption("Oferecer o próprio nome de graça.",
				"\"Tarde demais pra ser dado. Agora eu tomo.\" Ele puxa — e algo seu sai. Mas o que sai, ele não consegue segurar.",
				EventEffect.htBonus(10), EventEffect.damage(30), EventEffect.lore("O Tecelão tomou meu nome à força. Não soube o que fazer com ele."))
		));

		// === Avulsos: necromancia, ossos, decomposição ===

		register(new NarrativeEvent(
			"ossos_que_cantam",
			"Ossos Que Cantam",
			"Um monte de ossos empilhados no canto entoa uma nota baixa e contínua, como se respirasse a mesma canção há séculos.",
			4, 12, new String[]{}, new String[]{"mistério", "tragédia"},
			new EventOption("Acompanhar a canção com a voz.",
				"Você cantarola junto. Os ossos respondem com um acorde, e por um momento você lembra de uma canção da sua infância.",
				EventEffect.heal(30), EventEffect.flag("cantei_com_os_ossos")),
			new EventOption("Procurar de onde vem a nota.",
				"No fundo da pilha, um crânio com a mandíbula presa por um arame. Você o solta. A nota muda de tom.",
				EventEffect.item("ScrollOfIdentify")),
			new EventOption("Calar os ossos.",
				"Você espalha a pilha com o pé. O silêncio que segue é pior que a canção.",
				EventEffect.damage(8), EventEffect.npc(NpcKind.GHOST.name(), Attitude.HOSTILE.ordinal()))
		));

		register(new NarrativeEvent(
			"aprendiz_de_necromante",
			"O Aprendiz Que Sobrou",
			"Um jovem pálido remexe cadáveres com pressa de quem foi deixado pra trás. \"O mestre fugiu na guerra. Me ensinaram metade da arte. Me ajuda a terminar um?\"",
			6, 14, new String[]{"Guerra Civil dos Necromantes"}, new String[]{},
			new EventOption("Ajudar a erguer o morto.",
				"Vocês conseguem. A coisa se levanta torta, mas obedece. O aprendiz chora de alívio e te dá o que tem.",
				EventEffect.item("PotionOfMindVision"), EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.FRIENDLY.ordinal())),
			new EventOption("Ensinar a parar.",
				"\"Largue isso. Você ainda está vivo.\" Ele olha as próprias mãos como se fossem de outro. Assente devagar.",
				EventEffect.xp(140), EventEffect.flag("salvei_o_aprendiz")),
			new EventOption("Tomar o livro e ir.",
				"Você arranca o grimório dele e segue. Os gritos do garoto te seguem por dois corredores.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.damage(10))
		));

		register(new NarrativeEvent(
			"viuva_de_osso",
			"A Viúva de Osso",
			"Uma mulher de luto cava com as unhas num chão de pedra. \"Enterraram meu marido sem nome. Sem nome eles não descansam. Me empresta o seu?\"",
			5, 13, new String[]{}, new String[]{"tragédia", "decadência"},
			new EventOption("Dar seu nome ao túmulo.",
				"Você fala. Ela escreve com o dedo sangrando. O chão para de tremer. Você se sente estranhamente mais leve — e mais nu.",
				EventEffect.heal(25), EventEffect.flag("emprestei_meu_nome")),
			new EventOption("Inventar um nome bonito.",
				"Você diz \"Lúmen\". Ela aceita, grata. O nome falso fica boiando no ar como mentira gentil.",
				EventEffect.xp(110)),
			new EventOption("Cavar junto.",
				"Vocês cavam horas. Encontram o marido — que abre os olhos. A viúva sorri. Você não.",
				EventEffect.damage(15), EventEffect.gold(90))
		));

		register(new NarrativeEvent(
			"exercito_adormecido",
			"O Exército Adormecido",
			"Uma sala vasta de soldados mortos em pé, alinhados, esperando uma ordem que nunca veio. A poeira sobre eles tem a idade de um reino.",
			12, 20, new String[]{"Guerra Civil dos Necromantes", "Reino Caído"}, new String[]{},
			new EventOption("Dar a ordem de descanso.",
				"\"Dispensados.\" Sua voz ecoa. Um a um, os soldados se ajoelham e viram pó. O ar enche de alívio antigo.",
				EventEffect.xp(240), EventEffect.markQuestStep(3), EventEffect.flag("dispensei_o_exercito")),
			new EventOption("Tomar uma arma da fileira.",
				"Você escolhe uma lança. O soldado solta sem resistir. Os outros viram a cabeça, todos juntos, para você.",
				EventEffect.gold(120), EventEffect.damage(15), EventEffect.flag("armei_me_com_os_mortos")),
			new EventOption("Sair de costas, sem virar.",
				"Você recua olhando-os. Eles não se mexem. Mas você jura que respiram no mesmo ritmo que você.",
				EventEffect.text())
		));

		register(new NarrativeEvent(
			"sudario_em_branco",
			"Sudário em Branco",
			"Pendurado num gancho, um sudário limpo, com um bilhete: \"Para o próximo. Escreva nele a morte que prefere.\"",
			8, 17, new String[]{}, new String[]{"horror", "paranoia"},
			new EventOption("Escrever \"velho, em paz, longe daqui\".",
				"A tinta seca instantânea. Você sente uma promessa morna se firmar — ou uma armadilha se fechar.",
				EventEffect.buff("Bless", 100), EventEffect.flag("escrevi_minha_morte")),
			new EventOption("Deixar em branco.",
				"Você o pendura de volta. Recusar-se a escolher também é uma escolha — e essa o sudário respeita.",
				EventEffect.xp(130)),
			new EventOption("Rasgar o sudário.",
				"Você rasga. Cada tira cai e se mexe sozinha um instante antes de parar. Algo ficou sem destino.",
				EventEffect.damage(12), EventEffect.gold(60))
		));

		register(new NarrativeEvent(
			"mao_que_escreve",
			"A Mão Que Ainda Escreve",
			"Sobre uma escrivaninha, uma mão decepada continua escrevendo num livro, molhando a pena num tinteiro de sangue seco que ela reabre com a própria unha.",
			7, 16, new String[]{}, new String[]{"horror", "mistério"},
			new EventOption("Ler o que ela escreve.",
				"São seus passos. Tudo o que você fez na masmorra, em tempo real, com um capítulo já escrito à frente: o que você fará agora.",
				EventEffect.item("PotionOfMindVision"), EventEffect.flag("li_o_livro_da_mao")),
			new EventOption("Tomar a pena.",
				"A mão para. Te encara — mãos podem encarar. Depois aponta uma linha em branco: agora você escreve.",
				EventEffect.xp(160), EventEffect.flag("tomei_a_pena")),
			new EventOption("Fechar o livro sobre a mão.",
				"Você bate o livro. A mão se contorce e fica imóvel. O capítulo seguinte nunca será escrito. Você decide isso.",
				EventEffect.heal(20), EventEffect.damage(10))
		));

		register(new NarrativeEvent(
			"banquete_dos_mortos",
			"O Banquete Que Não Acaba",
			"Uma longa mesa de convivas mortos, garfos suspensos no ar, mastigando nada há tanto tempo que esqueceram que estão mortos. Há um lugar vago. Com seu nome.",
			10, 18, new String[]{}, new String[]{"decadência"},
			new EventOption("Sentar e fingir comer.",
				"Você levanta o garfo vazio. Os convivas viram-se, satisfeitos: enfim alguém os acompanha. Algo te enche sem que você coma.",
				EventEffect.heal(35), EventEffect.flag("comi_com_os_mortos")),
			new EventOption("Virar a cadeira vazia.",
				"Você recusa o assento e o derruba. O banquete inteiro para de mastigar ao mesmo tempo. O silêncio dura.",
				EventEffect.npc(NpcKind.GHOST.name(), Attitude.HOSTILE.ordinal()), EventEffect.xp(120)),
			new EventOption("Apagar as velas.",
				"Uma a uma. A cada vela morta, um conviva descansa de verdade. Quando a última apaga, você está sozinho — e em paz.",
				EventEffect.xp(180), EventEffect.markQuestStep(1))
		));

		register(new NarrativeEvent(
			"poco_de_dentes",
			"O Poço de Dentes",
			"Um poço seco até a borda de dentes humanos. Cada um, dizem as marcas na pedra, é uma mentira que alguém contou aqui.",
			9, 17, new String[]{}, new String[]{"horror", "decadência"},
			new EventOption("Confessar uma mentira ao poço.",
				"Você fala. Um dente seu cai na palma da mão, indolor. Você o joga lá. O peso de algo antigo te deixa.",
				EventEffect.heal(30), EventEffect.flag("confessei_ao_poco")),
			new EventOption("Pegar um punhado de dentes.",
				"Você enche o bolso. Eles tilintam como moedas e valem como tal — para quem compra esse tipo de coisa.",
				EventEffect.gold(110), EventEffect.damage(8)),
			new EventOption("Recuar.",
				"Você se afasta. Atrás de você, um dente novo cai sozinho na pilha. Você não contou nada. Não ainda.",
				EventEffect.text())
		));

		register(new NarrativeEvent(
			"o_coveiro_sem_nome",
			"O Coveiro Sem Nome",
			"Um homem de pá larga abre covas que ninguém pediu. \"Cavo pra todo mundo que vai descer. A sua tá quase pronta. Quer ver?\"",
			11, 19, new String[]{}, new String[]{"paranoia", "tragédia"},
			new EventOption("Olhar a própria cova.",
				"É funda, limpa, do seu tamanho. No fundo, sua silhueta já está marcada na terra. Você sobe sabendo demais.",
				EventEffect.xp(200), EventEffect.flag("vi_minha_cova")),
			new EventOption("Pedir pra ele parar de cavar a sua.",
				"\"Posso atrasar. Não posso cancelar.\" Ele tampa metade. Algo no seu peito relaxa um pouco — só um pouco.",
				EventEffect.heal(25), EventEffect.npc(NpcKind.GHOST.name(), Attitude.FRIENDLY.ordinal())),
			new EventOption("Empurrá-lo na cova que ele cavou.",
				"Ele cai sem protestar. Lá do fundo: \"Agora é a sua que sobra livre. Obrigado.\" Você não dorme bem.",
				EventEffect.damage(15), EventEffect.gold(80))
		));

		register(new NarrativeEvent(
			"relicario_de_cinzas",
			"Relicário de Cinzas",
			"Uma caixa de prata cheia de cinzas mornas, marcada pelo símbolo da Ordem das Cinzas. As cinzas se mexem como se respirassem.",
			6, 15, new String[]{"Dungeon da Praga"}, new String[]{},
			new EventOption("Espalhar as cinzas no chão, como manda o rito.",
				"Você as solta. Elas sobem em vez de cair, formam um rosto agradecido por um instante, e somem.",
				EventEffect.xp(160), EventEffect.buff("Bless", 80), EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.FRIENDLY.ordinal())),
			new EventOption("Levar o relicário fechado.",
				"Prata é prata. Você guarda. Algo lá dentro range a noite toda contra a tampa.",
				EventEffect.gold(140), EventEffect.flag("roubei_as_cinzas")),
			new EventOption("Soprar as cinzas na própria mão.",
				"Você as sopra e elas grudam. Por um dia inteiro você sabe, sem saber por quê, onde estão os mortos.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.damage(10))
		));

		register(new NarrativeEvent(
			"o_que_resta_do_mestre",
			"O Que Resta do Mestre",
			"Num trono de costelas, um necromante antigo, reduzido a torso e voz. \"A guerra me deixou assim. Meus aprendizes me dividiram. Você parece inteiro. Que luxo.\"",
			15, 22, new String[]{"Guerra Civil dos Necromantes"}, new String[]{},
			new EventOption("Perguntar onde estão as outras partes dele.",
				"\"Espalhadas, em servos diferentes, brigando. Junte-me e eu te ensino o que ninguém mais sabe.\" Ele te dá um mapa de carne.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.xp(220), EventEffect.flag("prometi_juntar_o_mestre")),
			new EventOption("Dar a ele um fim digno.",
				"Você encerra o torso com cuidado. Os olhos agradecem. O trono de costelas se acomoda como quem enfim deita.",
				EventEffect.markQuestStep(4), EventEffect.heal(30)),
			new EventOption("Roubar o coração ainda batendo.",
				"Você o arranca. Bate na sua mão como um pássaro preso. Vale uma fortuna. Custa um sono tranquilo.",
				EventEffect.gold(200), EventEffect.damage(20), EventEffect.flag("roubei_o_coracao"))
		));

		register(new NarrativeEvent(
			"a_costureira_de_carne",
			"A Costureira de Carne",
			"Uma velha costura pedaços de gente diferente numa só figura sobre a mesa. \"Tô fazendo um companheiro pra mim. Falta um sorriso bom. O seu serve.\"",
			13, 21, new String[]{}, new String[]{"horror"},
			new EventOption("Recusar e cobrir o rosto.",
				"\"Que pena. Era um sorriso honesto.\" Ela volta à costura. Você sai sentindo a própria boca, conferindo se ainda é sua.",
				EventEffect.text(), EventEffect.flag("neguei_o_sorriso")),
			new EventOption("Oferecer um sorriso seu.",
				"Não dói como você temia. Ela costura no rosto da criatura. Ela acorda, sorri pra você com a sua boca, e te abençoa.",
				EventEffect.htBonus(10), EventEffect.damage(15), EventEffect.buff("Bless", 100)),
			new EventOption("Cortar as linhas da criatura.",
				"Você desfaz a figura antes que ela acorde. A velha grita como mãe. As partes voltam a ser várias pessoas mortas.",
				EventEffect.xp(170), EventEffect.npc(NpcKind.GHOST.name(), Attitude.HOSTILE.ordinal()))
		));

		register(new NarrativeEvent(
			"o_arquivista_osseo",
			"O Arquivista Ósseo",
			"Estantes de fêmures gravados a fogo. Um bibliotecário de dedos longos passa o polegar nos ossos como quem lê lombadas. \"Cada osso, uma vida. Quer saber a sua?\"",
			10, 19, new String[]{}, new String[]{"mistério"},
			new EventOption("Pedir pra ler seu osso.",
				"Ele acha um fêmur na prateleira mais alta. Lê em voz baixa. Conta coisas que você fez e uma que ainda não fez.",
				EventEffect.item("PotionOfMindVision"), EventEffect.flag("li_meu_osso")),
			new EventOption("Pedir o osso de alguém que você perdeu.",
				"Ele hesita, depois entrega. Você ouve a vida inteira de quem se foi. Chora. Devolve. Algo cicatriza.",
				EventEffect.heal(45), EventEffect.xp(120)),
			new EventOption("Queimar o arquivo.",
				"Você ateia fogo às estantes. Mil vidas viram fumaça e gritam juntas. O arquivista te observa arder por dentro.",
				EventEffect.damage(20), EventEffect.gold(100), EventEffect.flag("queimei_o_arquivo"))
		));

		register(new NarrativeEvent(
			"trato_com_o_sudario",
			"Trato com o Sudário",
			"Um sudário flutua sozinho à sua frente, no formato de alguém que não está mais lá. \"Eu te visto agora e você não sente dor lá embaixo. Mas eu fico com a última hora.\"",
			16, 23, new String[]{}, new String[]{"tragédia"},
			new EventOption("Aceitar o trato.",
				"O pano te envolve e desaparece sob a pele. Você não sente mais nada doer — e sabe que comprou isso caro.",
				EventEffect.htBonus(15), EventEffect.flag("vendi_a_ultima_hora")),
			new EventOption("Recusar e seguir sentindo tudo.",
				"\"Corajoso. Ou tolo.\" O sudário murcha no chão. A dor segue sua — toda sua, ainda.",
				EventEffect.xp(180)),
			new EventOption("Vestir o sudário em outro morto pra libertá-lo.",
				"Você o coloca sobre um corpo no chão. O morto suspira e some. O sudário some junto, satisfeito com a troca.",
				EventEffect.heal(30), EventEffect.markQuestStep(2))
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
