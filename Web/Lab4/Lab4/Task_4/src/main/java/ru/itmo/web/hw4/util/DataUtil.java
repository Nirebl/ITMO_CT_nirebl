package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.User;
import ru.itmo.web.hw4.model.Post;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov"),
            new User(6, "pashka", "Pavel Mavrin"),
            new User(9, "geranazavr555", "Georgiy Nazarov"),
            new User(11, "tourist", "Gennady Korotkevich")
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(10540,
                    "Codeforces Round #826 (Div. 3)",
                    "Привет! В вторник, 11 октября 2022 г. в 17:35 начнётся Codeforces Round #826 (Div. 3) — очередной Codeforces раунд для третьего дивизиона. В этом раунде будет 6-8 задач, которые подобраны по сложности так, чтобы составить интересное соревнование для участников с рейтингами до 1600. Однако все желающие, чей рейтинг 1600 и выше могут зарегистрироваться на раунд вне конкурса.\n" +
                            "\n" +
                            "Раунд пройдет по правилам образовательных раундов. Таким образом, во время раунда задачи будут тестироваться на предварительных тестах, а после раунда будет 12-ти часовая фаза открытых взломов. Мы постарались сделать приличные тесты — так же как и вы, мы будем расстроены, если у многих будут падать решения после окончания контеста.\n" +
                            "\n" +
                            "Вам будет предложено 6-8 задач и 2 часа 15 минут на их решение.\n" +
                            "\n" +
                            "Штраф за неверную попытку в этом раунде будет равняться 10 минутам.\n" +
                            "\n" +
                            "Напоминаем, что в таблицу официальных результатов попадут только достоверные участники третьего дивизиона. Как написано по ссылке — это вынужденная мера для борьбы с неспортивным поведением. Для квалификации в качестве достоверного участника третьего дивизиона надо:\n" +
                            "\n" +
                            "принять участие не менее чем в пяти рейтинговых раундах (и решить в каждом из них хотя бы одну задачу)\n" +
                            "не иметь в рейтинге точку 1900 или выше.\n" +
                            "Независимо от того являетесь вы достоверными участниками третьего дивизиона или нет, если ваш рейтинг менее 1600, то раунд для вас будет рейтинговым.\n" +
                            "\n" +
                            "Спасибо MikeMirzayanov за платформы, помощь с идеями для задач и координацией нашей работы. Задачи были придуманы и написаны командой Университета ИТМО: MikeMirzayanov, myav, Gol_D, Aris, Gornak40, SixtyWithoutExam и Vladosiya.\n" +
                            "\n" +
                            "Также большое спасибо: Mangooste, Ormlis, Bugman, Be_dos, t4m0fey, _Stefan_, ace5, BledDest, _Absurd_, toxabuk, Kniaz, goncharovmike, -MXCode-, pashka и great_fortune за тестирование раунда и весьма полезные замечания. Список тестеров будет пополняться.\n" +
                            "\n" +
                            "Всем удачи!",
                    11),
            new Post(11203,
                    "Codeforces Round #827 (Div. 4)",
                    "Hello Codeforces!\n" +
                            "\n" +
                            "mesanu, flamestorm, MikeMirzayanov and I are glad to invite you to Codeforces Round #827 (Div. 4)! It starts on четверг, 13 октября 2022 г. в 17:35.\n" +
                            "\n" +
                            "The format of the event will be identical to Div. 3 rounds:\n" +
                            "\n" +
                            "5-8 tasks;\n" +
                            "ICPC rules with a penalty of 10 minutes for an incorrect submission;\n" +
                            "12-hour phase of open hacks after the end of the round (hacks do not give additional points)\n" +
                            "after the end of the open hacking phase, all solutions will be tested on the updated set of tests, and the ratings recalculated\n" +
                            "by default, only \"trusted\" participants are shown in the results table (but the rating will be recalculated for all with initial ratings less than 1400 or you are an unrated participant/newcomer).\n" +
                            "We urge participants whose rating is 1400+ not to register new accounts for the purpose of narcissism but to take part unofficially. Please do not spoil the contest for the official participants.\n" +
                            "\n" +
                            "Only trusted participants of the fourth division will be included in the official standings table. This is a forced measure for combating unsporting behavior. To qualify as a trusted participant of the fourth division, you must:\n" +
                            "\n" +
                            "take part in at least five rated rounds (and solve at least one problem in each of them),\n" +
                            "do not have a point of 1400 or higher in the rating.",
                    1),
            new Post(12409,
                    "Codeforces Global Round 23",
                    "Hello Codeforces!\n" +
                            "\n" +
                            "On суббота, 15 октября 2022 г. в 17:35 we will host Codeforces Global Round 23.\n" +
                            "\n" +
                            "It is the fifth round of a 2022 series of Codeforces Global Rounds. The rounds are open and rated for everybody.\n" +
                            "\n" +
                            "The prizes for this round:\n" +
                            "\n" +
                            "30 best participants get a t-shirt.\n" +
                            "20 t-shirts are randomly distributed among those with ranks between 31 and 500, inclusive.\n" +
                            "The prizes for the 6-round series in 2022:\n" +
                            "\n" +
                            "In each round top-100 participants get points according to the table.\n" +
                            "The final result for each participant is equal to the sum of points he gets in the four rounds he placed the highest.\n" +
                            "The best 20 participants over all series get sweatshirts and place certificates.\n" +
                            "Thanks to XTX, which in 2022 supported the global rounds initiative!\n" +
                            "\n" +
                            "The problems were written and prepared by AmirrzwM, MohammadParsaElahimanesh and napstablook, AquaMoon, Cirno_9baka, mejiamejia, ChthollyNotaSeniorious, SSerxhs, TomiokapEace, SomethingNew, pakhomovee, SirRembocodina, SirShokoladina.\n" +
                            "\n" +
                            "We would also like to thank:\n" +
                            "\n" +
                            "74TrAkToR, for coordinating and helping a lot to make this round happen (and rejecting all of my problems).\n" +
                            "er888kh and Mp007mp for giving ideas for some problems.\n" +
                            "Our testers for testing and providing helpful feedbacks: er888kh, sinamhdv, SoheilE, DeadlyCritic, _MMT_, gamegame, Kostroma, Endagorion, AndreySergunin, MELNIKOFF_OLEG, zemen, iwalainz, halin.george, -skyline-, mejiamejia, triple__a, TomiokapEace, SomethingNew, efimovpaul, Rhodoks, macneil, lox123, Milesian, 351F44, SSerxhs, LLL_2820, AHF, RUSH_D_CAT, willy108, _Veritas, oolimry, q-w-q-w-q and Ynoi.\n" +
                            "MikeMirzayanov for creating great platforms Codeforces and Polygon.",
                    11),
            new Post(12544,
                    "Codeforces Round #828 (Div. 3)",
                    "Привет! В воскресенье, 16 октября 2022 г. в 17:35 начнётся Codeforces Round #828 (Div. 3) — очередной Codeforces раунд для третьего дивизиона. В этом раунде будет 6-8 задач, которые подобраны по сложности так, чтобы составить интересное соревнование для участников с рейтингами до 1600. Однако все желающие, чей рейтинг 1600 и выше могут зарегистрироваться на раунд вне конкурса.\n" +
                            "\n" +
                            "Раунд пройдет по правилам образовательных раундов. Таким образом, во время раунда задачи будут тестироваться на предварительных тестах, а после раунда будет 12-ти часовая фаза открытых взломов. Мы постарались сделать приличные тесты — так же как и вы, мы будем расстроены, если у многих будут падать решения после окончания контеста.\n" +
                            "\n" +
                            "Вам будет предложено 6-8 задач и 2 часа 15 минут на их решение.\n" +
                            "\n" +
                            "Штраф за неверную попытку в этом раунде будет равняться 10 минутам.\n" +
                            "\n" +
                            "Напоминаем, что в таблицу официальных результатов попадут только достоверные участники третьего дивизиона. Как написано по ссылке — это вынужденная мера для борьбы с неспортивным поведением. Для квалификации в качестве достоверного участника третьего дивизиона надо:\n" +
                            "\n" +
                            "принять участие не менее чем в пяти рейтинговых раундах (и решить в каждом из них хотя бы одну задачу)\n" +
                            "не иметь в рейтинге точку 1900 или выше.\n" +
                            "Независимо от того являетесь вы достоверными участниками третьего дивизиона или нет, если ваш рейтинг менее 1600, то раунд для вас будет рейтинговым.\n" +
                            "\n" +
                            "Спасибо MikeMirzayanov за платформы, помощь с идеями для задач и координацией моей работы, pashka за помощь с идеями для задач и координацией моей работы. Задачи были придуманы и написаны MikeMirzayanov, 74TrAkToR и pashka. Также стоит отметить, что в этот же день будет проходить командная олимпиада ЮМШ, где будет большая часть задач раунда.\n" +
                            "\n" +
                            "Также большое спасибо: Gornak40, macneil, Vladosiya, SixtyWithoutExam, efimovpaul, powergee101, tanus_era за тестирование раунда и весьма полезные замечания. Список тестеров будет пополняться.\n" +
                            "\n" +
                            "Всем удачи!",
                    9),
            new Post(14371,
                    "Educational Codeforces Round 137 [рейтинговый для Div. 2]",
                    "Привет, Codeforces!\n" +
                            "\n" +
                            "В понедельник, 17 октября 2022 г. в 17:35 состоится Educational Codeforces Round 137 (Rated for Div. 2).\n" +
                            "\n" +
                            "Продолжается серия образовательных раундов в рамках инициативы Harbour.Space University! Подробности о сотрудничестве Harbour.Space University и Codeforces можно прочитать в посте.\n" +
                            "\n" +
                            "Этот раунд будет рейтинговым для участников с рейтингом менее 2100. Соревнование будет проводиться по немного расширенным правилам ICPC. Штраф за каждую неверную посылку до посылки, являющейся полным решением, равен 10 минутам. После окончания раунда будет период времени длительностью в 12 часов, в течение которого вы можете попробовать взломать абсолютно любое решение (в том числе свое). Причем исходный код будет предоставлен не только для чтения, но и для копирования.\n" +
                            "\n" +
                            "Вам будет предложено 6 или 7 задач на 2 часа. Мы надеемся, что вам они покажутся интересными.\n" +
                            "\n" +
                            "Задачи вместе со мной придумывали и готовили BledDest Андросов и Александр fcspartakm Фролов. Также большое спасибо Михаилу MikeMirzayanov Мирзаянову за системы Polygon и Codeforces.\n" +
                            "\n" +
                            "Раунд будет проводиться на задачах Квалификационного этапа Чемпионата Юга и Поволжья, так что убедительно просим его участников не принимать участие в раунде.\n" +
                            "\n" +
                            "Удачи в раунде! Успешных решений!",
                    6)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
                break;
            }
        }

        data.put("posts", POSTS);
    }
}
