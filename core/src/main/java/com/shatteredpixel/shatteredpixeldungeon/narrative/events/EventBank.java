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

		// ============================================================
		// BLOCO 2 — A Praga, o Sacerdote Insone e a Decadência
		// ============================================================

		// === Cadeia 7: O Sacerdote Insone ===
		register(new NarrativeEvent(
			"sacerdote_encontro",
			"O Sacerdote Que Não Dorme",
			"Um homem de hábito esfarrapado se belisca, se corta, se queima — qualquer coisa pra não fechar os olhos. \"Se eu dormir, o sonho acaba de descer. Já estou acordado há onze anos. Me ajuda a aguentar mais uma noite?\"",
			4, 10, new String[]{"Dungeon da Praga", "Ritual de Invocação"}, new String[]{"horror", "decadência"},
			new EventOption("Velar com ele esta noite.",
				"Vocês conversam até o amanhecer que nunca vem aqui embaixo. Ele resiste. Algo entre vocês fica selado.",
				EventEffect.flag("sacerdote_visto"), EventEffect.xp(140), EventEffect.npc(NpcKind.GHOST.name(), Attitude.FRIENDLY.ordinal())),
			new EventOption("Perguntar o que desce se ele dormir.",
				"\"O que eu segurei a vida inteira. Você não quer o nome. Nomear é convidar.\" Ele treme e fica calado.",
				EventEffect.flag("sacerdote_visto"), EventEffect.item("PotionOfMindVision")),
			new EventOption("Oferecer-se pra dormir no lugar dele.",
				"\"Você não saberia segurar.\" Mas ele te olha com uma esperança terrível. \"...ou saberia?\"",
				EventEffect.flag("sacerdote_visto"), EventEffect.flag("ofereci_dormir"))
		));

		register(new NarrativeEvent(
			"sacerdote_tentacao",
			"O Sono Oferece",
			"Você passa por uma cela acolchoada, cama macia, escuro perfeito — feita pra dormir. Uma voz mansa: \"Deixe o velho descansar. Durma você por ele. É tão fácil.\"",
			11, 17, new String[]{}, new String[]{},
			new String[]{"sacerdote_visto"},
			new EventOption("Resistir e seguir em pé.",
				"Você crava a unha na palma e segue. A voz suspira, decepcionada. Você entendeu por que ele aguenta.",
				EventEffect.xp(220), EventEffect.flag("resisti_ao_sono")),
			new EventOption("Dormir cinco minutos só.",
				"Você cochila. No sonho, algo desce um degrau. Você acorda gelado, com a sensação de ter deixado uma porta aberta.",
				EventEffect.heal(20), EventEffect.flag("dormi_um_pouco")),
			new EventOption("Quebrar a cama.",
				"Você destrói o colchão, o escuro, a maciez. A voz grita e se cala. O corredor inteiro parece mais acordado.",
				EventEffect.damage(10), EventEffect.flag("destrui_a_cama"))
		));

		register(new NarrativeEvent(
			"sacerdote_verdade",
			"O Que o Sacerdote Segura",
			"Você reencontra o Sacerdote. Pela primeira vez ele te mostra o que vela: uma porta de carne pulsando, com a forma de uma pálpebra gigante prestes a fechar. \"Quando ela piscar, o sonho assume tudo. Eu sou só a pestana.\"",
			16, 22, new String[]{}, new String[]{"horror", "mistério"},
			new String[]{"sacerdote_visto"},
			new EventOption("Jurar segurar a pálpebra com ele.",
				"Vocês se postam lado a lado contra a porta-olho. Por um instante, ela cede um milímetro pra trás. É vitória suficiente.",
				EventEffect.htBonus(12), EventEffect.flag("jurei_velar"),
				EventEffect.lore("A praga é um olho prestes a piscar. O Sacerdote é a pestana que segura.")),
			new EventOption("Estudar a porta de carne pra entender a fraqueza.",
				"Você observa por horas. Há uma costura no canto do olho — um ponto que, cortado, fecharia a porta pra sempre, ou a abriria de vez.",
				EventEffect.xp(260), EventEffect.flag("achei_a_costura"))
		));

		register(new NarrativeEvent(
			"sacerdote_escolha",
			"A Última Vigília",
			"O Sacerdote desaba de joelhos, onze anos pesando de uma vez. \"Não aguento mais. Decida por mim: me deixe dormir e enfrente o que vem — ou me ajude a fechar o olho pra sempre.\"",
			21, 24, new String[]{}, new String[]{"tragédia"},
			new String[]{"sacerdote_visto"},
			new EventOption("Deixá-lo dormir, enfim.",
				"Você segura a mão dele enquanto os olhos fecham. Ele sorri, livre. A pálpebra de carne estremece atrás de você — agora é com você.",
				EventEffect.xp(300), EventEffect.damage(20), EventEffect.flag("sacerdote_dormiu")),
			new EventOption("Costurar o olho fechado, custe o que custar.",
				"Você crava a agulha na costura. O olho range, chora, e cede. A porta sela. O Sacerdote chora de alívio e desmaia em paz.",
				EventEffect.htBonus(18), EventEffect.damage(30), EventEffect.flag("selei_o_olho"), EventEffect.markQuestStep(4)),
			new EventOption("Trocar de lugar: dormir você, deixá-lo descansar acordado.",
				"Vocês trocam de papel. Você sente o sono lutar pra te puxar — e a teimosia dele agora vive em você.",
				EventEffect.flag("troquei_com_o_sacerdote"), EventEffect.buff("Bless", 100))
		));

		register(new NarrativeEvent(
			"sacerdote_legado",
			"A Pestana Que Sobra",
			"No piso onde o Sacerdote caiu, você encontra o hábito dele dobrado, ainda morno, e um bilhete: \"Quem velar agora vela melhor que eu. Não conte os anos. Só os olhos que mantém fechados.\"",
			22, 24, new String[]{}, new String[]{},
			new String[]{"sacerdote_dormiu"},
			new EventOption("Vestir o hábito.",
				"Cai sobre você como dever. Você não sente mais sono — e entende que esse é o preço e a dádiva.",
				EventEffect.htBonus(10), EventEffect.buff("Bless", 150), EventEffect.flag("herdei_a_vigilia")),
			new EventOption("Enterrar o hábito com honra.",
				"Você cava com as mãos e o sepulta. Pela primeira vez em onze anos, ninguém vela — e o mundo, por enquanto, aguenta.",
				EventEffect.xp(240), EventEffect.heal(40))
		));

		// === Avulsos: praga, doença, decomposição, decadência ===

		register(new NarrativeEvent(
			"a_mae_das_moscas",
			"A Mãe das Moscas",
			"Um zumbido de catedral. No centro de um salão apodrecido, uma figura inchada cobre o rosto com as mãos, e de cada fenda entre os dedos saem moscas. \"Não olhe. Eu era bonita. A praga me deu filhos demais.\"",
			8, 16, new String[]{"Dungeon da Praga"}, new String[]{"horror", "tragédia"},
			new EventOption("Dizer que ela ainda é alguém, não uma coisa.",
				"As moscas hesitam. Por um segundo, entre os dedos, há um olho humano chorando. Ela te abençoa com o que resta de gente nela.",
				EventEffect.heal(35), EventEffect.buff("Bless", 100), EventEffect.flag("confortei_a_mae")),
			new EventOption("Pedir um filho dela como guia.",
				"Uma mosca pousa na sua mão e não sai mais. Por dias, ela te leva aos cantos que importam. Você não pergunta o preço.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.damage(12)),
			new EventOption("Queimar o ninho.",
				"O fogo enche o salão de um grito de mil zumbidos. Quando passa, há cinzas e uma mulher bonita, morta, finalmente em paz.",
				EventEffect.xp(200), EventEffect.damage(15), EventEffect.markQuestStep(1))
		));

		register(new NarrativeEvent(
			"agua_da_quarentena",
			"A Cisterna da Quarentena",
			"Uma cisterna lacrada com correntes e avisos: \"ÁGUA DOENTE — NÃO BEBA — NÃO REZE PERTO\". A água é limpa demais. Doce demais. Convida.",
			5, 13, new String[]{"Dungeon da Praga"}, new String[]{},
			new EventOption("Beber assim mesmo.",
				"É a melhor água da sua vida. Por uma hora você se sente invencível. Depois, uma tosse seca que não vai embora.",
				EventEffect.heal(50), EventEffect.flag("bebi_da_cisterna"), EventEffect.damage(5)),
			new EventOption("Reforçar o lacre.",
				"Você adiciona correntes ao que já existe. É o tipo de coisa que ninguém vai te agradecer e que salva alguém depois.",
				EventEffect.xp(150), EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.FRIENDLY.ordinal())),
			new EventOption("Encher um cantil pra usar como arma.",
				"Você guarda a água doente. Ela pesa pouco e promete muito mal a quem você jogar nela.",
				EventEffect.item("PotionOfHealing"), EventEffect.flag("armei_agua_doente"))
		));

		register(new NarrativeEvent(
			"o_doente_que_nao_morre",
			"O Doente Que Não Morre",
			"Numa cama de palha podre, um homem coberto de chagas te encara com olhos lúcidos. \"Trezentos anos esperando. A praga não me leva e não me larga. Você tem cara de quem pode encerrar isso.\"",
			12, 20, new String[]{"Dungeon da Praga"}, new String[]{"tragédia", "decadência"},
			new EventOption("Dar a ele o fim que pede.",
				"Você faz o que ele implora, com respeito. Ao último suspiro, trezentos anos saem do corpo de uma vez, como poeira ao vento.",
				EventEffect.xp(260), EventEffect.markQuestStep(2), EventEffect.flag("libertei_o_doente")),
			new EventOption("Perguntar como ele aguentou tanto tempo.",
				"\"Listando coisas bonitas que vi antes. Toma uma: o mar à tarde. Carregue por mim.\" Ele te dá a memória como um presente quente.",
				EventEffect.heal(40), EventEffect.flag("herdei_a_memoria")),
			new EventOption("Recuar com medo do contágio.",
				"Você se afasta. \"Tudo bem. Todo mundo recua.\" A voz dele é gentil. Isso é o pior.",
				EventEffect.damage(8))
		));

		register(new NarrativeEvent(
			"jardim_de_pus",
			"O Jardim de Pus",
			"Os Lavradores de Pus cultivaram um canteiro de bolhas que crescem como frutas maduras. Um deles te oferece uma, sorrindo sob a máscara: \"Colheita boa este século.\"",
			9, 17, new String[]{"Dungeon da Praga"}, new String[]{"decadência"},
			new EventOption("Recusar e pisar no canteiro.",
				"As bolhas estouram sob suas botas com um cheiro que você levará andares pra esquecer. Os Lavradores guardam rancor.",
				EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.HOSTILE.ordinal()), EventEffect.xp(120)),
			new EventOption("Aceitar a fruta e guardar.",
				"Você aceita por educação. Ela pulsa no bolso. Quem sabe sirva — como moeda, como praga, como ambos.",
				EventEffect.gold(90), EventEffect.flag("peguei_a_fruta")),
			new EventOption("Perguntar pra que serve a colheita.",
				"\"Pra alimentar a Mãe, claro. Quer conhecer?\" Você não quer. Mas agora sabe o caminho.",
				EventEffect.item("ScrollOfMagicMapping"))
		));

		register(new NarrativeEvent(
			"sino_da_peste",
			"O Sino da Peste",
			"Um pequeno sino de bronze sobre um altar, daqueles que se tocavam pra anunciar carroça de mortos. Uma placa: \"Toque uma vez por alma que você não conseguiu salvar.\"",
			7, 15, new String[]{}, new String[]{"tragédia"},
			new EventOption("Tocar uma vez, pensando em alguém.",
				"O som é limpo e triste. Algo no seu peito se desafoga junto com a vibração. Você respira melhor por um tempo.",
				EventEffect.heal(40), EventEffect.flag("toquei_o_sino_da_peste")),
			new EventOption("Tocar até cansar o braço.",
				"Você toca dezenas de vezes, sem parar, por todos. Quando para, o silêncio é uma absolvição que você não sabia que precisava.",
				EventEffect.xp(180), EventEffect.buff("Bless", 80)),
			new EventOption("Roubar o sino de bronze.",
				"Vale bom dinheiro. Mas ele toca sozinho no seu inventário, baixinho, toda vez que você falha em salvar alguém.",
				EventEffect.gold(130), EventEffect.flag("roubei_o_sino_da_peste"))
		));

		register(new NarrativeEvent(
			"o_medico_de_corvo",
			"O Médico de Corvo",
			"O homem da máscara de pássaro de novo — ou outro igual. Mas este sangra sob a máscara. \"Tratei a todos. Ninguém me tratou. Você sabe fechar uma ferida que não para?\"",
			10, 18, new String[]{"Dungeon da Praga"}, new String[]{},
			new EventOption("Cuidar das feridas dele.",
				"Você faz o que pode. Ele chora dentro da máscara — som estranho, de bico. Em troca, ensina como não adoecer aqui.",
				EventEffect.buff("Bless", 120), EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.FRIENDLY.ordinal()), EventEffect.flag("curei_o_medico")),
			new EventOption("Comprar os remédios dele baratos, aproveitando a fraqueza.",
				"Ele vende quase de graça, sem forças pra negociar. Você sai com o estoque e um gosto ruim na boca.",
				EventEffect.gold(-30), EventEffect.item("PotionOfHealing"), EventEffect.flag("explorei_o_medico")),
			new EventOption("Tirar a máscara dele.",
				"Você puxa. Não há rosto — só mais máscara, e mais, camadas de médicos antigos. Você recoloca a primeira, em silêncio.",
				EventEffect.damage(10), EventEffect.xp(140))
		));

		register(new NarrativeEvent(
			"as_velas_dos_doentes",
			"O Corredor das Velas",
			"Um corredor longo, cada centímetro coberto de velas — uma por doente. Muitas apagadas. Algumas tremem, à beira. Uma, exatamente no meio, acende sozinha quando você passa.",
			6, 14, new String[]{}, new String[]{"mistério", "esperança"},
			new EventOption("Proteger as chamas que tremem.",
				"Você faz concha com as mãos por cada vela vacilante. Algumas resistem mais um pouco. Você sente que isso conta em algum lugar.",
				EventEffect.heal(30), EventEffect.flag("protegi_as_velas")),
			new EventOption("Acender de volta as apagadas.",
				"Você reacende dezenas. Não devia ser possível, mas é. O corredor inteiro ilumina e algo, longe, agradece em coro.",
				EventEffect.xp(200), EventEffect.buff("Bless", 100)),
			new EventOption("Apagar todas, pra acabar com o sofrimento.",
				"Você sopra fila após fila. O escuro vem como descanso ou como derrota — você nunca vai ter certeza de qual.",
				EventEffect.htBonus(8), EventEffect.damage(15), EventEffect.flag("apaguei_as_velas"))
		));

		register(new NarrativeEvent(
			"contrato_de_imunidade",
			"O Contrato de Imunidade",
			"Um pergaminho oficial preso a uma mesa por um punhal. \"Quem assinar não pega a praga. Em troca, carrega-a sem adoecer — e a espalha por onde anda.\"",
			11, 19, new String[]{"Dungeon da Praga"}, new String[]{"paranoia"},
			new EventOption("Assinar com sangue.",
				"A tinta-sangue gruda. Você nunca mais vai adoecer aqui. Mas as moscas começam a te seguir, leais, como a um rei.",
				EventEffect.htBonus(12), EventEffect.flag("sou_portador")),
			new EventOption("Recusar e arrancar o punhal.",
				"Você liberta o pergaminho, que se enrola e some. O punhal, esse, fica — e é bom.",
				EventEffect.item("ScrollOfIdentify"), EventEffect.xp(120)),
			new EventOption("Assinar com nome falso.",
				"Você assina \"ninguém\". O contrato aceita, confuso. Você fica meio imune, meio portador — e totalmente incerto.",
				EventEffect.heal(20), EventEffect.damage(10), EventEffect.flag("contrato_ambiguo"))
		));

		register(new NarrativeEvent(
			"a_ultima_refeicao",
			"A Última Refeição da Cidade",
			"Uma cozinha enorme, panelas frias há séculos, a mesa posta pra um banquete que a praga interrompeu. A comida virou pedra. Mas o cheiro, esse, ainda é de festa.",
			8, 16, new String[]{}, new String[]{"decadência", "tragédia"},
			new EventOption("Sentar-se e honrar quem ia comer ali.",
				"Você se senta um minuto em silêncio. O cheiro de festa fica mais forte, depois se vai, como quem foi enfim liberado pra partir.",
				EventEffect.heal(35), EventEffect.npc(NpcKind.GHOST.name(), Attitude.FRIENDLY.ordinal())),
			new EventOption("Vasculhar a cozinha por algo útil.",
				"Atrás de potes de pedra, você acha um cofre de cozinheiro — moedas que ele guardava pra fugir e não usou a tempo.",
				EventEffect.gold(120)),
			new EventOption("Quebrar a comida petrificada.",
				"Você esmaga os pratos de pedra. Sob um deles, intacta, uma poção que alguém escondeu como sobremesa proibida.",
				EventEffect.item("PotionOfHealing"), EventEffect.damage(5))
		));

		register(new NarrativeEvent(
			"o_confessor_apodrecido",
			"O Confessionário Podre",
			"Um confessionário de madeira mole de tão úmida. Da grade, uma voz: \"Há quanto tempo não se confessa? Eu apodreci esperando alguém. Diga seus pecados e eu apodreço em paz.\"",
			9, 18, new String[]{}, new String[]{"horror", "decadência"},
			new EventOption("Confessar de verdade.",
				"Você fala o que nunca falou. Do outro lado, um suspiro úmido. \"Absolvido.\" A madeira finalmente desaba em pó tranquilo.",
				EventEffect.heal(45), EventEffect.buff("Bless", 100), EventEffect.flag("me_confessei")),
			new EventOption("Confessar pecados de outra pessoa.",
				"Você conta os pecados de quem te traiu. A voz ri molhada: \"Esses não são seus pra confessar. Mas obrigado pela fofoca.\"",
				EventEffect.xp(130), EventEffect.flag("confessei_alheio")),
			new EventOption("Abrir a porta do confessionário.",
				"Não há padre — só um manto de musgo no formato de quem rezou ali por demais tempo. Você fecha a porta devagar.",
				EventEffect.damage(8), EventEffect.item("ScrollOfIdentify"))
		));

		register(new NarrativeEvent(
			"a_roda_dos_enjeitados",
			"A Roda dos Enjeitados",
			"Uma roda de pedra na parede, dessas onde se deixavam bebês indesejados pra serem recolhidos do outro lado. Algo pequeno chora abafado dentro dela. Mas ela não gira faz séculos.",
			7, 15, new String[]{}, new String[]{"horror", "tragédia"},
			new EventOption("Girar a roda devagar.",
				"Você empurra a pedra emperrada. Do outro lado, o choro para — recolhido, enfim, por algo. Você decide não imaginar o quê.",
				EventEffect.xp(160), EventEffect.flag("girei_a_roda"), EventEffect.markQuestStep(0)),
			new EventOption("Falar com o que chora.",
				"\"Tô aqui\", você diz. O choro vira riso de bebê — o som mais fora de lugar que você já ouviu aqui. Some. Você fica com calafrios e com calma, juntos.",
				EventEffect.heal(30), EventEffect.damage(5)),
			new EventOption("Lacrar a roda com pedras.",
				"Você empilha entulho até o choro abafar de vez. Talvez crueldade. Talvez misericórdia. Você escolhe não saber.",
				EventEffect.htBonus(6), EventEffect.flag("lacrei_a_roda"))
		));

		// ============================================================
		// BLOCO 3 — Sonho Corrompido, a Colmeia Parasita e a Loucura
		// ============================================================

		// === Cadeia 8: A Voz Coletiva ===
		register(new NarrativeEvent(
			"colmeia_sussurro",
			"O Primeiro Sussurro Coletivo",
			"Você ouve sua própria voz dizer \"nós\" sem ter aberto a boca. De uma fenda na parede, dezenas de bocas pequenas pulsam em uníssono, atropelando as palavras antes de acertá-las: \"Você anda tão so... sozinho. Não precisa ser assim.\"",
			3, 9, new String[]{"Colmeia Parasita"}, new String[]{"horror", "loucura"},
			new EventOption("Encostar o ouvido na fenda.",
				"O coro te conta segredos da masmorra que ninguém vivo saberia. Útil. Íntimo. Você se afasta sabendo demais e sentindo-se observado por dentro.",
				EventEffect.flag("colmeia_ouvida"), EventEffect.item("PotionOfMindVision")),
			new EventOption("Responder \"eu\", bem alto.",
				"As bocas calam, ofendidas. \"Eu\", elas repetem, estranhando a palavra. Você firma quem é. Por enquanto.",
				EventEffect.flag("colmeia_ouvida"), EventEffect.xp(120)),
			new EventOption("Tapar a fenda com entulho.",
				"Você entope a parede. O sussurro abafa, mas não para — só fica do outro lado da pedra, paciente.",
				EventEffect.flag("colmeia_ouvida"), EventEffect.damage(8))
		));

		register(new NarrativeEvent(
			"colmeia_convite",
			"O Convite da Colmeia",
			"Um homem te aborda, tranquilo, olhos calmos demais. \"Eu fui como você. Sozinho na cabeça. Aí abri espaço. Agora nunca mais tenho medo, porque nunca mais estou só aqui dentro. Quer experimentar uma noite?\"",
			10, 16, new String[]{}, new String[]{},
			new String[]{"colmeia_ouvida"},
			new EventOption("Recusar com firmeza.",
				"\"Tudo bem. A porta fica aberta.\" Ele aponta a própria têmpora. Você anda mais rápido pelos próximos andares.",
				EventEffect.xp(200), EventEffect.flag("recusei_a_colmeia")),
			new EventOption("Aceitar \"só uma noite\".",
				"Você dorme. No sonho, mil vozes amigas. Acorda descansado como nunca — e com uma palavra nova que não é sua morando atrás dos olhos.",
				EventEffect.heal(40), EventEffect.flag("abri_espaco")),
			new EventOption("Perguntar o que ele perdeu em troca.",
				"Ele pensa muito. Muito mesmo. \"...não lembro. Mas eles lembram por mim. É melhor assim.\" Você decide que não é.",
				EventEffect.flag("recusei_a_colmeia"), EventEffect.item("ScrollOfIdentify"))
		));

		register(new NarrativeEvent(
			"colmeia_tentacao",
			"A Solidão Pesa",
			"Num momento de exaustão, o coro volta, suave, maternal: \"Você está tão cansado de decidir tudo sozinho. Deixe a gente carregar metade. Só metade. Você nem vai sentir falta.\"",
			14, 20, new String[]{}, new String[]{"loucura", "tragédia"},
			new String[]{"abri_espaco"},
			new EventOption("Reconquistar o espaço aberto.",
				"Você fecha os olhos e empurra de dentro pra fora, sílaba por sílaba, até a cabeça voltar a ser só sua. Dói. Vale.",
				EventEffect.htBonus(12), EventEffect.damage(20), EventEffect.flag("retomei_a_cabeca")),
			new EventOption("Ceder a metade que pesa.",
				"Você entrega o cansaço — e ele leva junto um pedaço seu que você nem sabia o nome. Fica mais leve. Fica menos.",
				EventEffect.heal(50), EventEffect.flag("cedi_metade")),
			new EventOption("Negociar: emprestado, não dado.",
				"\"Empréstimo\", você insiste. O coro acha graça mas aceita. Agora há um contrato dentro da sua testa, e você não confia no fiador.",
				EventEffect.buff("Recharging", 80), EventEffect.flag("emprestei_metade"))
		));

		register(new NarrativeEvent(
			"colmeia_verdade",
			"O Hospedeiro Original",
			"Você chega ao centro da colmeia: uma única pessoa, a primeira, fundida à parede, sorrindo, com mil bocas brotando do corpo. \"Eu fui o primeiro a abrir espaço. Olha que companhia eu fiz. Você nunca mais vai me deixar sozinho, vai?\"",
			18, 23, new String[]{"Colmeia Parasita"}, new String[]{},
			new String[]{"colmeia_ouvida"},
			new EventOption("Perguntar quem ele era antes.",
				"As bocas hesitam, discordam entre si, brigam. Por uma brecha, um sussurro só, fraquíssimo, lúcido: \"Me ajuda. Por favor. Eu ainda estou aqui no meio.\"",
				EventEffect.xp(280), EventEffect.flag("ouvi_o_hospedeiro"),
				EventEffect.lore("No centro da colmeia ainda há uma pessoa pedindo ajuda, soterrada pelas próprias vozes.")),
			new EventOption("Mapear a colmeia inteira do centro.",
				"Daqui você vê tudo. Cada hospedeiro, cada fenda, cada caminho. O coro deixa — afinal, mapear faz parte de pertencer.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.flag("mapeei_a_colmeia"))
		));

		register(new NarrativeEvent(
			"colmeia_escolha",
			"Calar o Coro",
			"O Hospedeiro Original treme no centro de tudo, a voz original lutando contra as mil. \"Você pode me libertar — me matar cala todas. Ou pode juntar-se e fazer o coro tão grande que vira paz. Escolha rápido, antes que eu esqueça que pedi.\"",
			21, 24, new String[]{}, new String[]{"tragédia"},
			new String[]{"ouvi_o_hospedeiro"},
			new EventOption("Libertá-lo — encerrar o original.",
				"Você faz o gesto certo. A pessoa no meio sorri de verdade, uma vez, antes das mil bocas se calarem todas de uma vez. Silêncio. Enfim.",
				EventEffect.xp(340), EventEffect.heal(30), EventEffect.flag("calei_o_coro"), EventEffect.markQuestStep(4)),
			new EventOption("Juntar-se pra fazer o coro virar paz.",
				"Você abre espaço de propósito, total. As vozes te recebem como casa. Você nunca mais estará só. Você nunca mais estará só.",
				EventEffect.htBonus(20), EventEffect.flag("virei_coro")),
			new EventOption("Recusar as duas saídas e simplesmente sair.",
				"Você dá as costas ao centro. O coro grita seu nome em mil tons. Você desce sem olhar, com a cabeça ainda — por enquanto — sua.",
				EventEffect.damage(15), EventEffect.flag("abandonei_a_colmeia"))
		));

		// === Avulsos: sonho, loucura, percepção, o eu ===

		register(new NarrativeEvent(
			"o_sonhador_acordado",
			"O Sonhador Acordado",
			"Um homem dorme de pé, de olhos abertos, andando em círculos lentos. Ao passar por você, murmura detalhes da sua vida que você nunca contou a ninguém. Ele está te sonhando.",
			8, 17, new String[]{"Sonho Corrompido"}, new String[]{"loucura", "mistério"},
			new EventOption("Acordá-lo com um tapa.",
				"Ele acorda gritando. \"Por que você me tirou de lá? Lá você era feliz!\" Você não pergunta o que ele viu. Mas pensa nisso por andares.",
				EventEffect.damage(10), EventEffect.xp(160), EventEffect.flag("acordei_o_sonhador")),
			new EventOption("Pedir pra ele sonhar um final feliz pra você.",
				"Ele sorri dormindo e murmura algo morno. Por um dia inteiro você carrega uma calma que não tem causa e não quer perder.",
				EventEffect.heal(45), EventEffect.buff("Bless", 100)),
			new EventOption("Deitar e dormir ao lado dele.",
				"Vocês sonham o mesmo sonho. Você vê a masmorra como ela se vê. Acorda com conhecimento que dói de tão grande.",
				EventEffect.item("PotionOfMindVision"), EventEffect.damage(12))
		));

		register(new NarrativeEvent(
			"a_porta_que_voce_lembra",
			"A Porta Que Você Lembra",
			"Uma porta comum de madeira, mas você a reconhece: é a porta do quarto da sua infância, impossivelmente aqui, embaixo da terra. Por baixo dela, luz morna e um cheiro de comida que você esqueceu que amava.",
			6, 15, new String[]{"Sonho Corrompido"}, new String[]{"tragédia", "esperança"},
			new EventOption("Abrir a porta.",
				"Lá dentro está tudo como era. Por um instante perfeito, você está em casa. Aí a luz apaga e é só pedra. Mas o instante foi real.",
				EventEffect.heal(50), EventEffect.damage(10), EventEffect.flag("abri_a_porta_da_infancia")),
			new EventOption("Encostar a testa na porta e seguir.",
				"Você não abre. Sabe que abrir machucaria mais. Encosta a testa, respira o cheiro uma vez, e desce. É a coisa mais corajosa do dia.",
				EventEffect.xp(220), EventEffect.buff("Bless", 80)),
			new EventOption("Arrombar e revistar o quarto.",
				"Você entra à força. O quarto se desfaz no contato. Sob a cama da infância, uma moeda de verdade, fria, que sobrou de algum lugar.",
				EventEffect.gold(100), EventEffect.damage(15))
		));

		register(new NarrativeEvent(
			"o_mapa_que_mente",
			"O Mapa Que Mente",
			"Pregado na parede, um mapa detalhado da masmorra. Tudo certo, exceto que ele mostra você parado num lugar onde você não está — e a figurinha que é você se move sozinha, um passo à frente do seu.",
			9, 18, new String[]{}, new String[]{"paranoia", "mistério"},
			new EventOption("Seguir onde a figurinha vai.",
				"Você obedece ao mapa contra o instinto. Ele te leva a um atalho real que você jamais acharia. Mas agora você não sabe quem anda na frente.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.flag("segui_o_mapa")),
			new EventOption("Arrancar o mapa da parede.",
				"Você o rasga. A figurinha-você fica presa entre seus dedos, mexendo-se um segundo, antes de virar papel comum. Você guarda o pedaço.",
				EventEffect.xp(150), EventEffect.damage(5)),
			new EventOption("Riscar a figurinha com carvão.",
				"Você apaga o boneco que é você. Imediatamente sente um alívio — e a leve, terrível impressão de ter apagado a si mesmo de algum registro.",
				EventEffect.htBonus(6), EventEffect.flag("apaguei_meu_boneco"))
		));

		register(new NarrativeEvent(
			"as_duas_portas",
			"As Duas Portas Idênticas",
			"Duas portas iguais. Sobre uma: \"A verdade que você teme.\" Sobre a outra: \"A mentira que te conforta.\" Não há terceira saída e o corredor atrás de você sumiu.",
			11, 20, new String[]{}, new String[]{"loucura"},
			new EventOption("Abrir a porta da verdade.",
				"Lá dentro, um espelho. Só você, exato, sem perdão e sem disfarce. Você sai mais pesado e mais firme, sabendo de si.",
				EventEffect.htBonus(10), EventEffect.damage(15), EventEffect.flag("escolhi_a_verdade")),
			new EventOption("Abrir a porta da mentira.",
				"Lá dentro, alguém que você perdeu, vivo, dizendo que está tudo bem. Você sabe que é falso. Aceita o abraço mesmo assim. Cura o que dá.",
				EventEffect.heal(50), EventEffect.flag("escolhi_a_mentira")),
			new EventOption("Abrir as duas ao mesmo tempo.",
				"Você empurra ambas. Verdade e mentira se anulam num clarão. O corredor de trás reaparece. Você não escolheu — e isso também é uma resposta.",
				EventEffect.xp(180))
		));

		register(new NarrativeEvent(
			"o_relogio_sem_ponteiros",
			"O Relógio Sem Ponteiros",
			"Um relógio de parede grande, sem ponteiros, mas tiquetaqueando alto. Quanto mais você o encara, mais certeza tem de que algo está acabando o tempo — o seu, especificamente.",
			7, 16, new String[]{"Catacumbas em Loop Temporal"}, new String[]{"paranoia"},
			new EventOption("Parar o pêndulo com a mão.",
				"Você segura o pêndulo. O tique para. O silêncio é absoluto e, por um momento, você sente que ganhou tempo de algum lugar.",
				EventEffect.buff("Bless", 100), EventEffect.flag("parei_o_relogio")),
			new EventOption("Desenhar ponteiros marcando uma hora boa.",
				"Você risca dois ponteiros no vidro, numa hora qualquer feliz. O relógio aceita a ficção e, gentilmente, desacelera o tique.",
				EventEffect.heal(30), EventEffect.xp(120)),
			new EventOption("Quebrar o relógio.",
				"Estilhaços e silêncio. Atrás do mostrador, em vez de engrenagens, ossos pequenos arrumados como mecanismo. Você não conta a ninguém.",
				EventEffect.gold(90), EventEffect.damage(10))
		));

		register(new NarrativeEvent(
			"o_proprio_cadaver",
			"O Próprio Cadáver",
			"Um corpo caído de bruços, com suas roupas, sua altura, sua cicatriz. Pela posição da mão, morreu tentando alcançar a escada de descida. Tentando descer. Como você, agora.",
			13, 22, new String[]{"Catacumbas em Loop Temporal"}, new String[]{"horror", "paranoia"},
			new EventOption("Revistar o próprio cadáver.",
				"Nos bolsos, suas coisas. E um bilhete na sua letra: \"Da próxima vez, não confie no que tem cara da gente.\" Você guarda. E lê de novo. E de novo.",
				EventEffect.item("ScrollOfIdentify"), EventEffect.flag("achei_meu_cadaver")),
			new EventOption("Enterrar a si mesmo com respeito.",
				"Você cava e sepulta o corpo que tem seu rosto. É o luto mais estranho da sua vida. Quando termina, sente-se, absurdamente, mais vivo.",
				EventEffect.xp(220), EventEffect.heal(25)),
			new EventOption("Virar o corpo pra ver o rosto.",
				"Você o vira. O rosto é seu — mas velho, exausto, com décadas a mais de masmorra. Os olhos abrem. \"Ah. Cedo demais.\" E fecham.",
				EventEffect.htBonus(10), EventEffect.damage(20), EventEffect.flag("vi_meu_rosto_velho"))
		));

		register(new NarrativeEvent(
			"a_plateia_imovel",
			"A Plateia Imóvel",
			"Uma sala em formato de teatro. As poltronas estão cheias de figuras imóveis, todas viradas pra você, esperando. No palco — que é onde você está — um holofote acende sozinho. É a sua vez.",
			10, 19, new String[]{}, new String[]{"loucura", "paranoia"},
			new EventOption("Fazer uma reverência e atuar.",
				"Você declama algo, qualquer coisa, do fundo do peito. A plateia imóvel não aplaude — mas, ao fim, todas as cabeças se inclinam de uma vez. Aprovação.",
				EventEffect.xp(180), EventEffect.buff("Bless", 80), EventEffect.flag("atuei_pra_plateia")),
			new EventOption("Sentar-se numa poltrona vazia e virar plateia.",
				"Você se senta entre eles. O holofote procura outro alguém no palco — mas não há. Você espera, junto com os outros, por quem nunca vem.",
				EventEffect.heal(20), EventEffect.flag("virei_plateia")),
			new EventOption("Acender as luzes da sala.",
				"Você acha o interruptor. Sob a luz plena, as poltronas estão vazias e empoeiradas. Sempre estiveram. O holofote, esse, ainda te procura.",
				EventEffect.damage(8), EventEffect.xp(140))
		));

		register(new NarrativeEvent(
			"o_colecionador_de_eus",
			"O Colecionador de Eus",
			"Numa galeria de redomas de vidro, versões suas de outras vidas: você-rei, você-mendigo, você-que-desistiu. Um curador de luvas brancas se aproxima. \"Exemplar magnífico, o senhor vivo. Aceita posar? Faltava o seu canto.\"",
			14, 22, new String[]{"Sonho Corrompido"}, new String[]{"mistério", "loucura"},
			new EventOption("Recusar a posar.",
				"\"Que pena.\" Ele suspira e te deixa estudar as redomas. Você aprende, vendo o que poderia ter sido, algo afiado sobre quem é.",
				EventEffect.htBonus(8), EventEffect.flag("recusei_a_redoma")),
			new EventOption("Quebrar a redoma do 'você-que-desistiu'.",
				"O vidro estala. A versão derrotada de você se desfaz em pó com um suspiro de gratidão. Você sente um peso velho sair junto.",
				EventEffect.heal(40), EventEffect.xp(120), EventEffect.flag("libertei_o_desistente")),
			new EventOption("Roubar a coroa do 'você-rei'.",
				"Você abre a redoma e pega a coroa do que poderia ter sido. É de ouro de verdade. O curador anota seu nome num livro de exemplares perdidos.",
				EventEffect.gold(160), EventEffect.damage(10), EventEffect.flag("roubei_a_coroa_alternativa"))
		));

		register(new NarrativeEvent(
			"o_eco_que_pergunta",
			"O Eco Que Faz Perguntas",
			"Numa câmara redonda, seu eco volta diferente: em vez de repetir, ele pergunta. \"Por que você desce mesmo?\" — com a sua voz, mas com uma curiosidade que você não pôs ali.",
			5, 14, new String[]{}, new String[]{"mistério"},
			new EventOption("Responder com a verdade.",
				"Você diz, em voz alta, o motivo real de estar aqui — talvez pela primeira vez. O eco fica satisfeito e some. Você fica com a resposta. É sua agora.",
				EventEffect.heal(30), EventEffect.xp(150), EventEffect.flag("respondi_ao_eco")),
			new EventOption("Devolver a pergunta ao eco.",
				"\"E você, por que pergunta?\" O eco hesita — eco não devia hesitar. \"...porque alguém tem que perguntar.\" Vocês ficam, os dois, sem resposta boa.",
				EventEffect.item("PotionOfMindVision")),
			new EventOption("Gritar pra abafar o eco.",
				"Você grita até a garganta arder. O eco se mistura ao seu berro e some no barulho. A pergunta, no entanto, fica. Sempre fica.",
				EventEffect.damage(10), EventEffect.htBonus(5))
		));

		register(new NarrativeEvent(
			"a_carta_sem_remetente",
			"A Carta Sem Remetente",
			"Sobre uma pedra, um envelope lacrado com seu nome na frente, na sua própria caligrafia. Você não escreveu carta nenhuma. O lacre é de cera ainda morna.",
			8, 17, new String[]{}, new String[]{"paranoia", "mistério"},
			new EventOption("Abrir e ler.",
				"\"Quando ler isto, você já terá esquecido que escreveu. Está tudo bem. Continue descendo. Confie em mim — em você.\" Você não sabe se relaxa ou treme.",
				EventEffect.buff("Bless", 80), EventEffect.flag("li_a_carta"), EventEffect.xp(120)),
			new EventOption("Queimar sem ler.",
				"Você não quer saber. Queima. As cinzas formam, por um segundo, uma palavra — \"covarde\" ou \"sábio\", você não consegue ler — e somem.",
				EventEffect.damage(5), EventEffect.htBonus(6)),
			new EventOption("Guardar lacrada pra depois.",
				"Você a guarda. Ela esquenta de leve no bolso, viva, paciente, esperando o momento em que você vai precisar mesmo do que ela diz.",
				EventEffect.item("ScrollOfIdentify"))
		));

		// ============================================================
		// BLOCO 4 — Reino Caído, Santuário Afogado, Profecia,
		//           e os pecados de Ganância e Esperança
		// ============================================================

		// === Cadeia 9: O Trono Vazio ===
		register(new NarrativeEvent(
			"trono_encontro",
			"O Trono Vazio",
			"Um salão real soterrado pelo tempo. No fim, um trono intacto, vazio, com uma coroa pousada no assento como se o rei tivesse se levantado faz um minuto. Uma voz sem corpo: \"Enfim, alguém com coragem de subir os degraus. Sente-se. O reino precisa de alguém.\"",
			3, 9, new String[]{"Reino Caído"}, new String[]{"mistério", "decadência"},
			new EventOption("Sentar-se no trono.",
				"Você senta. Por um instante o salão se reconstrói ao seu redor, vivo, cheio, em festa — depois desmorona de novo. Mas a coroa, essa, te reconheceu.",
				EventEffect.flag("sentei_no_trono"), EventEffect.xp(150), EventEffect.htBonus(5)),
			new EventOption("Pegar só a coroa.",
				"Você ergue a coroa. É pesada de um jeito que não é só metal. A voz silencia, ofendida. \"Ladrão de reino, então. Anotado.\"",
				EventEffect.flag("peguei_a_coroa"), EventEffect.gold(120)),
			new EventOption("Ajoelhar-se diante do trono vazio.",
				"Você se ajoelha a um rei que não existe. A voz se comove. \"Faz tanto tempo que alguém respeita o lugar, não quem o ocupa.\" Algo se abre pra você.",
				EventEffect.flag("respeitei_o_trono"), EventEffect.buff("Bless", 100))
		));

		register(new NarrativeEvent(
			"trono_corte",
			"A Corte de Pó",
			"Cortesãos de poeira se erguem ao seu redor, curvando-se. \"Majestade.\" Eles esperam ordens com a paciência de séculos. Um deles sussurra: \"O tesouro real ainda está selado. Só a coroa abre. Só o rei decide.\"",
			10, 16, new String[]{}, new String[]{},
			new String[]{"sentei_no_trono"},
			new EventOption("Ordenar que descansem, libertando a corte.",
				"\"Dispensados. Para sempre.\" Eles se curvam uma última vez, gratos, e voltam a ser pó — pó que não mais se levanta. O salão exala alívio.",
				EventEffect.xp(240), EventEffect.markQuestStep(2), EventEffect.flag("libertei_a_corte")),
			new EventOption("Mandar abrirem o tesouro real.",
				"Os cortesãos de pó arrastam-se até um cofre antigo e o abrem. Ouro de um reino inteiro. Eles esperam, agora, que você fique. Reis não vão embora.",
				EventEffect.gold(220), EventEffect.flag("abri_o_tesouro"), EventEffect.damage(10)),
			new EventOption("Perguntar como o reino caiu.",
				"O cortesão mais velho conta: \"O rei desceu atrás de algo lá embaixo e nunca voltou. Esperamos. Ainda esperamos. O senhor... voltou?\"",
				EventEffect.xp(180), EventEffect.flag("soube_da_queda"),
				EventEffect.lore("O reino caiu quando o rei desceu atrás de algo no fundo e nunca mais subiu."))
		));

		register(new NarrativeEvent(
			"trono_peso",
			"O Peso da Coroa",
			"A coroa em sua cabeça começa a pesar de verdade — não metal, mas decisões. Vozes de súditos mortos pedem coisas opostas ao mesmo tempo. \"Pão.\" \"Vingança.\" \"Que o rei desça e termine o que começou.\"",
			15, 21, new String[]{}, new String[]{"decadência", "tragédia"},
			new String[]{"sentei_no_trono"},
			new EventOption("Carregar o peso e governar com justiça.",
				"Você ouve a todos, decide o impossível, e a coroa para de doer — porque enfim alguém a usa pra servir, não pra ter. Os mortos sossegam.",
				EventEffect.htBonus(14), EventEffect.buff("Bless", 120), EventEffect.flag("governei_com_justica")),
			new EventOption("Tirar a coroa e largá-la no chão.",
				"Você a tira. O alívio é instantâneo e covarde. As vozes calam — abandonadas de novo. Você desce mais leve, e algo em você sabe o preço disso.",
				EventEffect.heal(30), EventEffect.flag("abdiquei")),
			new EventOption("Mandar os mortos calarem a boca.",
				"\"SILÊNCIO!\" Funciona. Eles obedecem, aterrorizados. Você reina sobre o medo agora. É eficiente. É solitário. A coroa esfria na sua testa.",
				EventEffect.xp(200), EventEffect.flag("reinei_pelo_medo"))
		));

		register(new NarrativeEvent(
			"trono_verdade",
			"O Rei Que Desceu",
			"No fundo do reino, você encontra um esqueleto coroado, igual à coroa que você carrega, agarrado a uma porta que dá pro abismo do boss final. Ele desceu pra enfrentar o que destruiu seu reino — e morreu na soleira. \"Você... veio terminar?\"",
			20, 24, new String[]{"Reino Caído"}, new String[]{},
			new String[]{"soube_da_queda"},
			new EventOption("Jurar terminar o que ele começou.",
				"Você ajoelha ao lado do rei morto e faz o juramento. O esqueleto solta a porta, enfim. A coroa em você arde como propósito, não como peso.",
				EventEffect.htBonus(16), EventEffect.flag("jurei_terminar"), EventEffect.markQuestStep(4)),
			new EventOption("Tirar a coroa e colocá-la no crânio dele.",
				"Você devolve a coroa ao rei verdadeiro. O esqueleto suspira poeira. \"Obrigado por não querer meu lugar.\" Você desce como você mesmo, e isso pesa menos.",
				EventEffect.xp(300), EventEffect.heal(40), EventEffect.flag("devolvi_a_coroa"))
		));

		register(new NarrativeEvent(
			"trono_ladrao_pago",
			"A Conta do Ladrão",
			"Os cortesãos de pó te alcançam, mas sem reverência agora — você roubou a coroa, nunca foi rei. \"Devolva o que é do reino, gatuno, ou carregue a maldição de toda corte que você desprezou.\"",
			14, 22, new String[]{}, new String[]{"ganância"},
			new String[]{"peguei_a_coroa"},
			new EventOption("Devolver a coroa.",
				"Você a entrega. O pó suspira e se acalma. \"Sábio. Reino roubado afunda com quem rouba.\" Você sai sem ouro, mas sem peso nas costas.",
				EventEffect.xp(200), EventEffect.flag("devolvi_coroa_roubada")),
			new EventOption("Fugir com a coroa mesmo assim.",
				"Você corre. A corte de pó não te segue — não precisa. A coroa fica mais pesada a cada degrau, lembrando que ela escolhe o rei, e não escolheu você.",
				EventEffect.gold(180), EventEffect.damage(20), EventEffect.flag("fugi_com_a_coroa")),
			new EventOption("Vender a coroa ali mesmo, ao maior morto.",
				"Um cortesão ganancioso compra de você por um tesouro. Negócio fechado entre dois ladrões. Vocês se entendem. Nenhum dos dois dorme bem.",
				EventEffect.gold(150), EventEffect.npc(NpcKind.IMP.name(), Attitude.FRIENDLY.ordinal()))
		));

		// === Avulsos: Santuário Afogado ===

		register(new NarrativeEvent(
			"o_deus_submerso",
			"O Deus Submerso",
			"Uma câmara inundada até o peito. No fundo da água escura, vasto, algo enorme abre um olho do tamanho de uma porta e te observa subir bolhas. Não é hostil. É só faminto de atenção, depois de eras sem fiéis.",
			12, 21, new String[]{"Santuário Afogado"}, new String[]{"horror", "mistério"},
			new EventOption("Fazer uma prece improvisada.",
				"Você reza palavras inventadas. O olho enorme se enche d'água — um deus pode chorar. Em troca da fé, a água ao seu redor te embala como bênção.",
				EventEffect.buff("Bless", 120), EventEffect.heal(35), EventEffect.flag("rezei_ao_submerso")),
			new EventOption("Mergulhar pra tocar o olho.",
				"Você prende o ar e desce. Toca a pálpebra colossal. Por um segundo sabe tudo o que o deus sabe — e quase não volta à tona com a cabeça inteira.",
				EventEffect.item("PotionOfMindVision"), EventEffect.damage(18), EventEffect.flag("toquei_o_deus")),
			new EventOption("Drenar a câmara.",
				"Você acha a comporta e abre. A água escoa por horas. O deus encalha, imenso e patético, secando ao ar. O olho te acusa até o fim. Você ganha passagem e culpa.",
				EventEffect.xp(220), EventEffect.htBonus(8), EventEffect.flag("drenei_o_deus"))
		));

		register(new NarrativeEvent(
			"os_que_nao_boiam",
			"Os Que Não Bóiam",
			"Sob a água rasa de um corredor alagado, pessoas em pé, de olhos abertos, respirando líquido. Afundaram por escolha. Um deles ergue a mão pra fora: \"Desça com a gente. Aqui embaixo não dói lembrar.\"",
			10, 19, new String[]{"Santuário Afogado"}, new String[]{"tragédia", "decadência"},
			new EventOption("Recusar e seguir pela superfície.",
				"\"Cada um afunda na sua hora\", a voz borbulha, sem rancor. Você caminha sobre eles, cuidando pra não pisar em rosto nenhum.",
				EventEffect.xp(150), EventEffect.flag("recusei_afundar")),
			new EventOption("Puxar um deles pra fora.",
				"Você iça o que estendeu a mão. Ele tosse água por minutos, depois te encara com pavor: \"Por quê? Agora vou ter que sentir tudo de novo.\" Mas vai viver.",
				EventEffect.heal(20), EventEffect.npc(NpcKind.GHOST.name(), Attitude.WARY.ordinal()), EventEffect.flag("salvei_um_afogado")),
			new EventOption("Submergir um momento, só pra entender.",
				"Você mergulha entre eles. Por alguns segundos, de fato, não dói lembrar de nada. É tentador. É por isso que você sobe rápido, antes de querer ficar.",
				EventEffect.heal(40), EventEffect.damage(15), EventEffect.flag("provei_o_fundo"))
		));

		register(new NarrativeEvent(
			"o_sino_afogado",
			"O Sino Afogado",
			"No fundo de um poço inundado, um grande sino badala sozinho, abafado pela água, chamando fiéis que se afogaram há séculos. A corda sobe até a superfície, ao seu alcance.",
			11, 20, new String[]{"Santuário Afogado"}, new String[]{},
			new EventOption("Puxar a corda pra fazê-lo soar.",
				"O som sobe deformado pela água, lindo e errado. Algo no fundo responde, grato por ser chamado. Você ganha a sensação de ter completado um rito antigo.",
				EventEffect.xp(180), EventEffect.markQuestStep(3), EventEffect.flag("toquei_o_sino_afogado")),
			new EventOption("Cortar a corda.",
				"Você corta. O sino para de badalar pela primeira vez em séculos. O silêncio sob a água é vasto e, de algum modo, é um luto.",
				EventEffect.htBonus(8), EventEffect.damage(8)),
			new EventOption("Descer pela corda até o sino.",
				"Você desce ao fundo. Dentro do sino, em vez de badalo, um sacerdote afogado segura a língua de metal com as duas mãos. Ele te oferece. Você decide se aceita o cargo.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.damage(15), EventEffect.flag("entrei_no_sino"))
		));

		// === Avulsos: Profecia, esperança, ganância, e diversos ===

		register(new NarrativeEvent(
			"a_pagina_arrancada",
			"A Página Arrancada",
			"Os Hereges da Última Página queimaram a profecia inteira, menos um pedaço que sobrou preso a uma estaca. Nele, meia frase: \"...e o que descer por último irá...\" — o resto é fogo.",
			9, 18, new String[]{"Profecia Interrompida"}, new String[]{"mistério"},
			new EventOption("Completar a frase você mesmo.",
				"Você escreve um fim para a profecia: \"...irá escolher.\" A tinta pega como se sempre tivesse estado lá. Você acaba de editar o destino. Pequeno. Mas seu.",
				EventEffect.htBonus(10), EventEffect.flag("editei_a_profecia"), EventEffect.xp(160)),
			new EventOption("Procurar as cinzas do resto.",
				"Você vasculha as cinzas e remonta cacos de frase: o fim profetizado é terrível, ou glorioso, dependendo de quem desce por último. Pode ser você.",
				EventEffect.item("ScrollOfIdentify"), EventEffect.flag("li_as_cinzas")),
			new EventOption("Queimar também o que sobrou.",
				"Você termina o trabalho dos hereges. A última sílaba da profecia vira fumaça. Agora não há destino escrito — só o que você fizer. Liberta. Apavora.",
				EventEffect.xp(200), EventEffect.flag("queimei_a_profecia"))
		));

		register(new NarrativeEvent(
			"o_cofre_que_pede_nome",
			"O Cofre Que Pede um Nome",
			"Um cofre maciço de ouro, sem fechadura, só uma placa: \"Diga o nome de quem você ama mais e eu abro. Minto se você mentir.\" Por baixo, marcas de unhas de quem tentou forçar e falhou.",
			8, 17, new String[]{}, new String[]{"ganância"},
			new EventOption("Dizer o nome verdadeiro.",
				"Você fala, e a palavra te custa mais do que esperava. O cofre se abre. Lá dentro, ouro — e um pequeno espelho, pra você ver quem pôs antes do dinheiro.",
				EventEffect.gold(160), EventEffect.heal(20), EventEffect.flag("disse_o_nome_amado")),
			new EventOption("Dizer o próprio nome.",
				"\"Eu.\" O cofre considera por um longo tempo. Depois abre, devagar, meio decepcionado, meio impressionado. O ouro tem gosto de solidão honesta.",
				EventEffect.gold(120), EventEffect.flag("amo_a_mim_mesmo")),
			new EventOption("Tentar arrombar.",
				"Você força com tudo. O cofre range: \"Mentiu sobre amar dinheiro mais que gente.\" Ele despeja moedas falsas que viram pó nas suas mãos.",
				EventEffect.damage(15), EventEffect.npc(NpcKind.IMP.name(), Attitude.HOSTILE.ordinal()))
		));

		register(new NarrativeEvent(
			"a_balanca_dourada",
			"A Balança Dourada",
			"Uma balança de ouro num pedestal. De um lado, um prato vazio. Acima: \"Coloque o que mais valoriza. Receba o peso em ouro. Sem trapaça.\"",
			10, 19, new String[]{}, new String[]{"ganância", "mistério"},
			new EventOption("Pôr uma lembrança preciosa no prato.",
				"Você deposita, de algum modo, uma lembrança que ama. A balança a pesa — e ela pesa muito. Sai ouro a rodo. E você perde um pouco da cor da lembrança.",
				EventEffect.gold(200), EventEffect.damage(10), EventEffect.flag("vendi_uma_lembranca")),
			new EventOption("Pôr a própria mão no prato.",
				"Você arrisca a carne. A balança pesa sua coragem, não a mão, e te devolve intacto, com ouro e um respeito incômodo de uma máquina antiga.",
				EventEffect.gold(120), EventEffect.heal(15)),
			new EventOption("Pôr nada e exigir ouro.",
				"\"Ganância sem oferta\", a balança troveja, \"é a única coisa que não vale nada.\" O prato vazio fica vazio. Você sai como entrou. Quase.",
				EventEffect.xp(140))
		));

		register(new NarrativeEvent(
			"o_mendigo_coroado",
			"O Mendigo de Coroa",
			"Um mendigo esfarrapado usa uma coroa de papelão dourado com dignidade absurda. \"Fui rei de tudo isto, antes de tudo isto cair. Hoje só me resta a coroa e a graça de não a tirar. Senta, divide o pão?\"",
			6, 15, new String[]{"Reino Caído"}, new String[]{"esperança", "tragédia"},
			new EventOption("Sentar e dividir o que tem.",
				"Vocês comem migalhas como num banquete. Ele conta do reino com tanto amor que por uma hora ele existe de novo. Você sai mais rico do que entrou.",
				EventEffect.heal(40), EventEffect.buff("Bless", 100), EventEffect.flag("comi_com_o_rei_mendigo")),
			new EventOption("Dar todo o seu ouro a ele.",
				"Você esvazia a bolsa na mão dele. Ele chora. \"Um súdito. Enfim, um súdito.\" A gratidão dele vale mais do que o ouro valia.",
				EventEffect.gold(-100), EventEffect.htBonus(10), EventEffect.flag("financiei_o_rei")),
			new EventOption("Tomar a coroa de papelão como piada.",
				"Você arranca o papelão dourado. Sem a coroa, o mendigo encolhe, vira só um velho qualquer, e algo se apaga nele. Você guarda um troféu inútil e cruel.",
				EventEffect.damage(8), EventEffect.npc(NpcKind.GHOST.name(), Attitude.HOSTILE.ordinal()))
		));

		register(new NarrativeEvent(
			"a_semente_na_pedra",
			"A Semente na Pedra",
			"No lugar mais morto da masmorra, uma única semente brotou numa fresta, com uma folhinha verde teimosa virada pra uma luz que não existe ali. É a coisa mais viva que você viu em andares.",
			5, 14, new String[]{}, new String[]{"esperança"},
			new EventOption("Regar com sua água.",
				"Você sacrifica um gole do cantil. A folhinha estremece, agradecida. Você não vai ver essa planta crescer — mas plantou alguma coisa em si ao fazer isso.",
				EventEffect.heal(35), EventEffect.buff("Bless", 80), EventEffect.flag("reguei_a_semente")),
			new EventOption("Arrancar pra usar como remédio.",
				"Você colhe o broto. Vira pó verde útil — cura ferida. Mas o canto fica morto de novo, e você carrega a pergunta de se valia a pena.",
				EventEffect.item("PotionOfHealing"), EventEffect.damage(5)),
			new EventOption("Só ficar olhando um pouco.",
				"Você não faz nada. Só observa a teimosia verde por um minuto. É um descanso que nenhum item te daria. Você desce mais leve.",
				EventEffect.heal(25), EventEffect.xp(60))
		));

		register(new NarrativeEvent(
			"a_lampada_do_naufrago",
			"A Lâmpada do Náufrago",
			"Pendurada num gancho enferrujado, uma lâmpada ainda acesa, dessas que se deixavam na janela esperando alguém voltar do mar. Quem a acendeu morreu esperando. A chama nunca soube e não apagou.",
			7, 16, new String[]{}, new String[]{"esperança", "tragédia"},
			new EventOption("Manter a chama acesa, reabastecendo.",
				"Você cuida da chama, completa o óleo. Ela arde mais firme. Você não sabe quem ela espera — mas decide que a espera de alguém merece continuar.",
				EventEffect.buff("Bless", 100), EventEffect.flag("cuidei_da_chama")),
			new EventOption("Levar a lâmpada como guia.",
				"Você a descola do gancho. Ela ilumina seu caminho com uma luz morna que não é de fogo comum — é de quem espera. Por andares, você não se sente sozinho.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.flag("levei_a_lampada")),
			new EventOption("Apagar a chama, encerrando a espera.",
				"Você sopra. A luz morre. Em algum lugar, talvez, um morto que esperava enfim para de esperar. Misericórdia ou crueldade — só o escuro responde.",
				EventEffect.xp(160), EventEffect.htBonus(6), EventEffect.flag("apaguei_a_lampada"))
		));

		register(new NarrativeEvent(
			"o_motor_que_implora",
			"O Motor Que Implora",
			"Uma máquina antiga, do tamanho de uma sala, com uma boca de bronze. \"Combustível\", ela range em voz quase humana. \"Não sangue. Cansei de sangue. Me dê uma boa razão pra continuar girando e eu giro.\"",
			13, 22, new String[]{"Despertar de Máquina Antiga"}, new String[]{"mistério"},
			new EventOption("Dar uma razão sincera pra ela girar.",
				"Você fala com a máquina como com um velho cansado. Diz algo verdadeiro sobre por que se continua. Os pistões hesitam — e voltam, mais firmes. Ela te abençoa em vapor morno.",
				EventEffect.buff("Recharging", 120), EventEffect.xp(200), EventEffect.flag("convenci_o_motor")),
			new EventOption("Alimentá-la com seu próprio sangue.",
				"Você ignora o pedido e oferece o de sempre. A máquina aceita, decepcionada com você e consigo. Gira poderosa de novo, mas sem alegria. Você se sente um pouco menos gente.",
				EventEffect.damage(20), EventEffect.htBonus(10), EventEffect.flag("alimentei_a_maquina")),
			new EventOption("Desligá-la, deixá-la descansar.",
				"Você acha a alavanca e a puxa. A máquina suspira em ferro e silencia. \"Obrigada\", é a última palavra. Você desce ouvindo, pela primeira vez, o seu próprio silêncio.",
				EventEffect.xp(220), EventEffect.markQuestStep(1), EventEffect.flag("desliguei_o_motor"))
		));

		register(new NarrativeEvent(
			"o_cartografo_cego",
			"O Cartógrafo Cego",
			"Um velho de olhos brancos desenha mapas pelo tato, passando os dedos pelas paredes e traçando linhas perfeitas num pergaminho. \"Vejo melhor sem ver. Quer um mapa? Custa só que você me descreva uma cor. Faz tanto tempo.\"",
			7, 16, new String[]{"Expedição Esquecida"}, new String[]{"esperança", "mistério"},
			new EventOption("Descrever uma cor pra ele.",
				"Você descreve o azul de um céu que ele nunca verá de novo. Ele para de desenhar, ouve com o corpo todo, chora, e te entrega o mapa mais preciso da sua vida.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.heal(20), EventEffect.flag("descrevi_uma_cor")),
			new EventOption("Pedir o mapa do caminho mais perigoso.",
				"\"Curioso.\" Ele traça uma rota que ninguém são pediria. Leva a tesouro e a quase-morte na mesma linha. Você decide se é corajoso ou ganancioso.",
				EventEffect.gold(150), EventEffect.damage(15), EventEffect.flag("rota_perigosa")),
			new EventOption("Roubar o pergaminho dele.",
				"Você toma o mapa das mãos cegas. Ele não persegue — não pode. Só diz, baixinho: \"Vai te levar a lugar nenhum. Mapa roubado perde o norte.\" E perde mesmo.",
				EventEffect.damage(10), EventEffect.npc(NpcKind.WANDMAKER.name(), Attitude.HOSTILE.ordinal()))
		));

		register(new NarrativeEvent(
			"o_imp_usurario",
			"O Imp Usurário",
			"Um Imp de óculos minúsculos e um livro-razão imenso. \"Crédito! Eu empresto vida agora, você paga com juros depois. Assine aqui. Letra pequena? Ah, a letra pequena é onde eu moro.\"",
			12, 21, new String[]{}, new String[]{"ganância"},
			new EventOption("Pegar o empréstimo de vida.",
				"Você assina sem ler. Energia te invade, deliciosa. O Imp sorri e fecha o livro. \"Volto pra cobrar. Sempre volto.\" A dívida tiquetaqueia em você.",
				EventEffect.heal(50), EventEffect.htBonus(8), EventEffect.flag("devo_ao_imp")),
			new EventOption("Ler a letra pequena primeiro.",
				"Você lê tudo, linha por linha minúscula. O Imp fica visivelmente desconfortável. No fim, você acha a cláusula que o ferra — e a usa pra negociar a seu favor.",
				EventEffect.gold(140), EventEffect.npc(NpcKind.IMP.name(), Attitude.WARY.ordinal()), EventEffect.flag("li_a_letra_pequena")),
			new EventOption("Rasgar o livro-razão.",
				"Você rasga o calhamaço inteiro. Mil dívidas de mil aventureiros se anulam de uma vez. Eles, onde quer que estejam, te devem uma. O Imp jura vingança contábil.",
				EventEffect.xp(220), EventEffect.npc(NpcKind.IMP.name(), Attitude.HOSTILE.ordinal()), EventEffect.flag("rasguei_o_razao"))
		));

		register(new NarrativeEvent(
			"a_estatua_que_chora_ouro",
			"A Estátua Que Chora Ouro",
			"Uma estátua de um santo esquecido chora, e as lágrimas são moedas de ouro que tilintam no chão. Uma fila de tigelas embaixo coleta o pranto. Há mais que você consegue carregar.",
			9, 18, new String[]{}, new String[]{"ganância", "mistério"},
			new EventOption("Encher os bolsos e ir.",
				"Você junta o ouro do choro. É muito. Mas, ao sair, jura que a estátua chorou mais forte — e que cada moeda no seu bolso ainda está molhada de tristeza alheia.",
				EventEffect.gold(200), EventEffect.flag("peguei_o_ouro_do_choro")),
			new EventOption("Enxugar o rosto da estátua.",
				"Você sobe e seca as lágrimas de pedra com a manga. A estátua para de chorar. Os olhos, secos, te olham com um alívio de séculos. Você deixa o ouro pra trás. Vale.",
				EventEffect.xp(200), EventEffect.buff("Bless", 100), EventEffect.flag("consolei_a_estatua")),
			new EventOption("Pegar só uma moeda, por respeito.",
				"Você pega uma só, faz uma vênia e segue. A estátua chora um pouco menos. A moeda no seu bolso fica morna, não molhada. Suficiente é uma palavra que pesa diferente aqui.",
				EventEffect.gold(40), EventEffect.heal(15))
		));

		register(new NarrativeEvent(
			"o_jardineiro_dos_ossos",
			"O Jardineiro dos Ossos",
			"Num pátio interno, um velho rega canteiros onde, no lugar de flores, crescem ossos lisos e brancos como caules. Ele os trata com ternura de avô. \"Planto os que ninguém enterrou. Aqui eles viram jardim, não lixo. Ajuda a molhar?\"",
			8, 17, new String[]{}, new String[]{"esperança", "decadência"},
			new EventOption("Ajudar a regar o jardim de ossos.",
				"Vocês cuidam juntos. Sob seus cuidados, um osso floresce de verdade — uma flor branca impossível. O velho chora. Você leva o cheiro dela por andares.",
				EventEffect.heal(35), EventEffect.buff("Bless", 80), EventEffect.flag("reguei_os_ossos")),
			new EventOption("Pedir um osso-flor de presente.",
				"Ele escolhe um com cuidado e te dá. \"Esse aqui era de alguém gentil, dá pra sentir.\" Você guarda. Pesa como responsabilidade e conforta como companhia.",
				EventEffect.item("ScrollOfIdentify"), EventEffect.xp(120)),
			new EventOption("Arrancar os ossos pra vender.",
				"Você colhe a braçada. O velho não grita — apenas para, devastado, e murmura: \"Eram gente.\" O ouro que você ganha tem peso de cova violada.",
				EventEffect.gold(150), EventEffect.damage(12), EventEffect.npc(NpcKind.GHOST.name(), Attitude.HOSTILE.ordinal()))
		));

		register(new NarrativeEvent(
			"a_ultima_vela_da_capela",
			"A Última Vela da Capela",
			"Uma capela em ruínas com uma única vela acesa no altar, protegida do vento por mãos invisíveis. Sobre ela, gravado: \"Enquanto arder, há esperança. Já apagou mil vezes. Mil vezes alguém a reacendeu.\"",
			15, 24, new String[]{}, new String[]{"esperança"},
			new EventOption("Acrescentar sua chama, reforçando a vela.",
				"Você acende um toco seu na mesma vela. A luz dobra, firme. Você se torna mais um dos mil que se recusaram a deixar o escuro vencer. É um clube bom de pertencer.",
				EventEffect.htBonus(10), EventEffect.buff("Bless", 120), EventEffect.flag("reforcei_a_esperanca")),
			new EventOption("Levar a chama pra acender outra no fundo.",
				"Você transporta o fogo com cuidado obsceno, mão em concha, e acende uma vela morta lá adiante. Agora são duas. A esperança se multiplica quando se divide.",
				EventEffect.xp(180), EventEffect.heal(25), EventEffect.markQuestStep(0)),
			new EventOption("Apagar pra ver o que acontece.",
				"Você sopra. O escuro engole a capela. Nada de terrível acontece — e isso é o terrível. Você reacende rápido, com a mão tremendo, agora entendendo por que mil tentaram antes.",
				EventEffect.damage(10), EventEffect.xp(120))
		));

		register(new NarrativeEvent(
			"o_espelho_do_perdao",
			"O Espelho do Perdão",
			"Um espelho embaçado. Conforme você se aproxima, ele não mostra você — mostra alguém que você magoou, esperando, paciente, como se a qualquer momento você fosse dizer algo.",
			11, 20, new String[]{}, new String[]{"tragédia", "esperança"},
			new EventOption("Pedir perdão à imagem.",
				"Você fala com a pessoa no espelho como nunca conseguiu falar pessoalmente. A imagem ouve. Acena devagar. O embaçado limpa, e enfim é só você ali — mais inteiro.",
				EventEffect.heal(50), EventEffect.buff("Bless", 100), EventEffect.flag("pedi_perdao")),
			new EventOption("Encarar sem dizer nada.",
				"Você sustenta o olhar da pessoa que magoou, em silêncio, até doer. Não é perdão. Mas é coragem. A imagem some respeitando a sua honestidade de não fingir arrependimento que não sente.",
				EventEffect.htBonus(8), EventEffect.xp(140)),
			new EventOption("Cobrir o espelho com um pano.",
				"Você não está pronto. Cobre o vidro. Tudo bem. Algumas contas a gente paga noutro andar da vida. O pano fica, gentil, guardando a imagem pra quando você puder.",
				EventEffect.text(), EventEffect.flag("adiei_o_perdao"))
		));

		register(new NarrativeEvent(
			"o_guardiao_que_duvida",
			"O Guardião Que Duvida",
			"Uma armadura imensa bloqueia a passagem, lança cruzada. Mas a voz dentro dela é trêmula, jovem. \"Mandaram eu guardar isto aqui pra sempre. Faz tanto tempo. Eu nem sei mais se ainda tem alguém pra quem guardar. Você... sabe de algo lá fora?\"",
			10, 19, new String[]{}, new String[]{"tragédia", "esperança"},
			new EventOption("Dizer que pode ir embora, que cumpriu o dever.",
				"\"Posso? Mesmo?\" A armadura range, descrente, depois solta a lança. De dentro sai poeira e alívio. Ele te deixa passar e, enfim, se aposenta. Você sente que libertou um soldado, não venceu um.",
				EventEffect.xp(200), EventEffect.heal(20), EventEffect.flag("libertei_o_guardiao")),
			new EventOption("Mentir uma ordem de dispensa em nome do rei.",
				"Você forja autoridade. \"O rei te libera.\" Ele acredita, grato demais pra duvidar. Funciona — mas a mentira gentil fica na sua boca com gosto estranho.",
				EventEffect.flag("menti_ao_guardiao"), EventEffect.xp(140)),
			new EventOption("Forçar passagem à força.",
				"Você o enfrenta. Ele luta por dever, não por ódio, e isso torna tudo pior. Ao cair, sussurra \"obrigado\" — descansar era tudo o que queria. Você passa pesado.",
				EventEffect.damage(20), EventEffect.gold(100), EventEffect.htBonus(6))
		));

		// ============================================================
		// BLOCO 5 — A Expedição Esquecida e a Máquina Antiga
		// ============================================================

		// === Cadeia 10: A Expedição Esquecida ===
		register(new NarrativeEvent(
			"expedicao_acampamento",
			"O Acampamento Abandonado",
			"Restos de um acampamento organizado: barracas, fogueira fria, um quadro de avisos com uma lista de nomes — e todos riscados, menos um, no fim, ainda em branco. Há espaço pra mais um nome. O seu cabe.",
			2, 8, new String[]{"Expedição Esquecida"}, new String[]{"mistério"},
			new EventOption("Escrever seu nome na lista.",
				"Você se inscreve na expedição que já morreu. As barracas, por um segundo, parecem cheias de novo. Você herda um propósito e uma maldição leve: terminar o que eles não terminaram.",
				EventEffect.flag("entrei_na_expedicao"), EventEffect.xp(130)),
			new EventOption("Vasculhar os suprimentos.",
				"Comida estragada, mas no fundo de uma mochila, intacta, uma poção que alguém guardava pro fim — um fim que não chegou pra ele.",
				EventEffect.item("PotionOfHealing"), EventEffect.flag("entrei_na_expedicao")),
			new EventOption("Queimar o acampamento como pira.",
				"Você ateia fogo aos restos, em respeito. As chamas sobem altas. Os nomes riscados queimam juntos, enfim livres do quadro. Você segue, e eles não te seguem.",
				EventEffect.xp(160), EventEffect.flag("queimei_o_acampamento"))
		));

		register(new NarrativeEvent(
			"expedicao_diario",
			"O Diário do Líder",
			"Mais fundo, o diário do líder da expedição. As últimas páginas: \"Perdemos três. O cartógrafo enlouqueceu. Eu sigo sozinho atrás do que viemos buscar. Se alguém ler isto: não era um tesouro. Era uma pessoa. Ela ainda está viva lá embaixo.\"",
			9, 16, new String[]{}, new String[]{"tragédia", "mistério"},
			new String[]{"entrei_na_expedicao"},
			new EventOption("Jurar achar quem eles buscavam.",
				"Você fecha o diário e assume a missão deles como sua. Não um tesouro: uma pessoa. Isso muda o jeito como você desce — com pressa de quem resgata, não de quem saqueia.",
				EventEffect.flag("busco_a_pessoa"), EventEffect.xp(220), EventEffect.htBonus(6)),
			new EventOption("Arrancar o mapa preso na última página.",
				"Você toma o mapa que o líder desenhou até morrer. Leva direto a algum fundo. Você não jura nada — mas guarda o diário, e ele pesa como uma cobrança.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.flag("busco_a_pessoa"))
		));

		register(new NarrativeEvent(
			"expedicao_reencontro",
			"O Que a Expedição Buscava",
			"No fundo, numa cela de raízes, uma pessoa viva — impossivelmente, ainda viva, sustentada por algo que você não quer entender. Ela ergue os olhos. \"Vocês... voltaram? Demorou tanto. Eu já não sei mais se quero sair.\"",
			18, 24, new String[]{"Expedição Esquecida"}, new String[]{"tragédia", "esperança"},
			new String[]{"busco_a_pessoa"},
			new EventOption("Libertá-la, custe o que custar.",
				"Você arranca as raízes uma a uma, e elas resistem, e doem em você também. Mas ela sai. Cambaleia pra luz. \"A expedição... eles conseguiram, no fim. Através de você.\" Algo enorme se cura.",
				EventEffect.xp(360), EventEffect.heal(40), EventEffect.htBonus(12), EventEffect.markQuestStep(4), EventEffect.flag("completei_a_expedicao")),
			new EventOption("Perguntar o que a sustenta há tanto tempo.",
				"\"O mesmo que sustenta a masmorra. Se eu sair, talvez tudo aqui caia comigo. Você ainda quer me tirar?\" A escolha pesa muito pra um corredor escuro.",
				EventEffect.xp(260), EventEffect.flag("soube_o_preco"),
				EventEffect.lore("A pessoa que a expedição buscava sustenta a masmorra. Libertá-la pode derrubar tudo.")),
			new EventOption("Deixá-la em paz, se é o que ela quer.",
				"Você respeita o cansaço dela de séculos. Recoloca uma raiz solta com gentileza. \"Obrigada por perguntar antes de decidir por mim.\" Você sobe carregando a dúvida pra sempre.",
				EventEffect.heal(30), EventEffect.flag("respeitei_a_escolha_dela"))
		));

		// === Avulsos: Máquina Antiga e diversos ===

		register(new NarrativeEvent(
			"o_autômato_que_pinta",
			"O Autômato Que Pinta",
			"Um autômato enferrujado, de braço fino, pinta a mesma paisagem na parede há tanto tempo que cavou um sulco na pedra. É um campo ensolarado — coisa que não existe aqui embaixo. Ele não para pra te olhar.",
			8, 17, new String[]{"Despertar de Máquina Antiga"}, new String[]{"esperança", "tragédia"},
			new EventOption("Trazer-lhe uma cor nova.",
				"Você esfrega pigmento de musgo no pincel dele. O autômato hesita, processa, e adiciona um verde que nunca teve. A paisagem ganha vida. Ele emite um som que só pode ser alegria.",
				EventEffect.buff("Bless", 100), EventEffect.xp(160), EventEffect.flag("dei_cor_ao_automato")),
			new EventOption("Desligá-lo, encerrando a repetição.",
				"Você acha o interruptor nas costas dele. O braço para no ar, a meio traço. Talvez fosse misericórdia parar a repetição eterna. Talvez você só tenha matado um artista. A paisagem fica inacabada pra sempre.",
				EventEffect.xp(180), EventEffect.damage(8), EventEffect.flag("desliguei_o_automato")),
			new EventOption("Levar a 'pintura' raspando a pedra.",
				"Você raspa o sulco pintado e leva os fragmentos. O autômato continua pintando o vazio agora, sem perceber. Você carrega um pedaço de sol roubado de uma máquina cega.",
				EventEffect.gold(90), EventEffect.item("ScrollOfIdentify"))
		));

		register(new NarrativeEvent(
			"a_engrenagem_solitaria",
			"A Engrenagem Solitária",
			"Uma engrenagem dourada gira sozinha no ar, sem máquina ao redor, mantendo um ritmo perfeito e inútil. Encaixá-la em algo poderia mover montanhas. Ou poderia ser a única coisa segurando o teto.",
			11, 20, new String[]{"Despertar de Máquina Antiga"}, new String[]{"mistério", "paranoia"},
			new EventOption("Tirá-la do ar e guardar.",
				"Você a agarra. Ela continua girando na sua mão, viva, quente. Útil, valiosa — e em algum lugar acima, algo range, como se uma peça importante tivesse acabado de faltar.",
				EventEffect.item("PotionOfMindVision"), EventEffect.damage(10), EventEffect.flag("tirei_a_engrenagem")),
			new EventOption("Deixá-la girar e seguir.",
				"Você decide não mexer no que funciona, mesmo sem entender. Às vezes a coragem é não tocar. O ritmo da engrenagem te acompanha como um coração calmo até a próxima escada.",
				EventEffect.heal(20), EventEffect.xp(120)),
			new EventOption("Acelerá-la com a mão.",
				"Você empurra a engrenagem mais rápido. O ar zumbe. Por um momento eufórico, tudo na masmorra parece girar mais depressa — inclusive seu próprio sangue. Você solta antes que algo quebre.",
				EventEffect.buff("Recharging", 100), EventEffect.damage(12))
		));

		register(new NarrativeEvent(
			"o_jukebox_de_lamentos",
			"A Caixa de Lamentos",
			"Uma caixa de música enorme, do tamanho de um armário, com uma manivela. Ao girar, em vez de melodia, ela toca lamentos gravados — as últimas palavras de quem morreu aqui, uma por volta.",
			9, 18, new String[]{}, new String[]{"tragédia", "decadência"},
			new EventOption("Girar e ouvir até o fim.",
				"Você escuta dezenas de adeuses, medos, perdões tardios. É insuportável e necessário. No último, uma voz diz \"valeu a pena viver\" — e isso, depois de tudo, te endireita as costas.",
				EventEffect.heal(40), EventEffect.buff("Bless", 80), EventEffect.flag("ouvi_os_lamentos")),
			new EventOption("Gravar seu próprio lamento na caixa.",
				"Você acha o cilindro vazio e fala nele o que diria se fosse o fim. Sela na caixa. Agora, se você morrer aqui, alguém vai ouvir. Estranhamente, isso te dá vontade de não morrer.",
				EventEffect.xp(180), EventEffect.flag("gravei_meu_lamento")),
			new EventOption("Quebrar a manivela.",
				"Você arranca a manivela. Os lamentos calam no meio de uma palavra. O silêncio é um alívio covarde — você não quis ouvir o resto. Guarda a peça de metal, que pesa como omissão.",
				EventEffect.gold(70), EventEffect.damage(8))
		));

		register(new NarrativeEvent(
			"a_porta_com_seu_tamanho",
			"A Porta do Seu Tamanho Exato",
			"Uma porta pequena, recortada na medida precisa do seu corpo — nem um dedo de folga. Foi feita pra você, especificamente, e pra mais ninguém. Do outro lado, uma luz que não promete nada e nada esconde.",
			16, 24, new String[]{}, new String[]{"mistério", "esperança"},
			new EventOption("Atravessar.",
				"Você passa, justo, raspando os ombros. Do outro lado: um atalho que parece existir só porque você existe. A masmorra, pela primeira vez, parece ter feito algo a seu favor.",
				EventEffect.item("ScrollOfMagicMapping"), EventEffect.xp(200), EventEffect.flag("atravessei_a_porta_minha")),
			new EventOption("Medir-se de novo, desconfiado.",
				"Você confere: serve exata. Bom demais. Você bate na moldura, procura armadilha, não acha. Às vezes uma dádiva é só uma dádiva — mas você passa de lado, por via das dúvidas.",
				EventEffect.heal(20), EventEffect.xp(120)),
			new EventOption("Recusar-se a usar uma porta feita pra você.",
				"\"Eu escolho meus caminhos.\" Você dá as costas à porta sob medida e procura outra saída, mais difícil, mais sua. A porta range atrás de você, quase magoada.",
				EventEffect.htBonus(8), EventEffect.flag("recusei_a_porta_minha"))
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
