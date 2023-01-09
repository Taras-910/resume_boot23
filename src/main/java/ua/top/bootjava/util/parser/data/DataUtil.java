package ua.top.bootjava.util.parser.data;

import java.time.LocalDate;
import java.util.List;

import static java.util.List.of;
import static ua.top.bootjava.util.CommonUtil.isEmpty;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.is_age;

public class DataUtil {
    public static final LocalDate defaultDate = LocalDate.now().minusMonths(1);
    private static StringBuilder builder;

    public static StringBuilder getBuild(String text) {
        if (builder == null) {
            builder = new StringBuilder(1024);
        }
        builder.setLength(0); //https://stackoverflow.com/questions/5192512/how-can-i-clear-or-empty-a-stringbuilder
        return builder.append(text);
    }

    public static final String
            extract_salary = "((?:[\\d,\\.[–до\\-k-  ]+\\s  &nbsp]+\\b)(\\s*)?[  ]?(\\p{Sc}|ƒ))|(" +
            "(?:\\p{Sc}|ƒ)(\\s*)?[  ]?[\\d,\\.[–до\\-k-  ]\\s  &nbsp]+\\b)",
            monetary_amount_regex = "[-–—k(дот-]",
            is_date = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])T\\d{2}:\\d{2}:\\d{2}\\+\\d{2}:\\d{2}$",
            is_kilo = ".*\\d[\\d\\.-]+k.*",
            is_number_format = "[\\d\\.]+",
            is_period_work = "(.*\\d?\\d\\s?[годалетрківи]{3,})?\\s?и?\\s?1?\\d\\s?[месяцевіь]{3,}.*",
            date_period_extract = "((?:\\s?\\d?\\d)\\s?\\(?\\s?([летгодаyears])+)?(\\s?[иі,]?\\s?)?(?:\\s?\\d?\\d)\\s?\\(?\\s?([месяцеваmonths])+\\.*?",
            age_field_extract = "(?:[1-7]\\d)\\s([годалетрківи])+",
            address_field_extract = "(?:[а-яА-ЯіїєA-Za-z,\\s·]+)\\b",
            local_date_extract = "(?:\\d){1,2}\\s([а-яіїє])+|^[а-яіїє]{3,11}",
            document_user_agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.2 Safari/605.1.15",
            internet_connection_error = "There may be no internet connection or exception={} by url={} ",
            finish_message = "\nfinish is ok,\ntime: {}ms\ncreated: {}\nupdated: {}\nFreshen: {}\n" + ":".repeat(125),
            common_number = "Common number resumeTos = {}",
            get_resume = "GetResumes language={} level={} workplace={} ",
            error_parse = "Error parse field={} e={}",
            document_url = "GetDocument url={}\n",
            error_select = "Select error e {}";
//            djinni = "DjinniStrategy", habr = "HabrStrategy",
//            grc = "GrcStrategy", work = "WorkStrategyStrategy", rabota = "RabotaStrategy",
//            middle = "middle", trainee = "trainee", junior = "junior", senior = "senior", expert = "expert";

    public static final List<String>
            wasteSalary = of(" ", " ", "&nbsp;", "[.]{2,}", "(\\p{Sc}|ƒ)", "\\s+"),
    //            traineeAria = of("intern", "trainee", "интерн", "internship", "стажировка", "стажер"),
    juniorAria = of("junior", "младший", "без опыта", "обучение"),
            middleAria = of("middle", "средний"),
            seniorAria = of("senior", "старший"),
            expertAria = of("expert", "lead", "team lead", "ведущий", "тимлид"),
            kievAria = of("kyiv", "kiev", "київ", "киев"),
            dniproAria = of("дніпро", "днепр", "dnipro"),
            kharkivAria = of("харків", "харьков", "kharkiv"),
            lvivAria = of("львів", "львов", "lviv"),
            odesaAria = of("одесса", "odesa", "одеса"),
            mykolaivAria = of("mykolaiv", "миколаїв", "николаев"),
            vinnitsiaAria = of("винница", "vinnitsia", "вінниця"),
            zaporizhzhyaAria = of("запоріжжя", "запорожье", "zaporizhzhya"),
            chernivtsiAria = of("chernivtsi", "чернівці", "черновцы"),
            chernigivAria = of("чернігів", "чернигов", "chernigiv"),
            ivano_frankivskAria = of("івано-франківськ", "ивано-франковск", "ivano-frankivsk"),
            uzhgorodAria = of("ужгород", "uzhgorod"),
            polandAria = of("польша", "poland", "polski"),
            krakowAria = of("krakow", "краков"),
            warszawaAria = of("варшава", "warszawa"),
            wroclawAria = of("wroclaw", "вроцлав"),
            gdanskAria = of("гданськ", "гданск"),
            poznanAria = of("poznan", "познань"),
            minskAria = of("minsk", "минск", "мінськ"),
    monthsOfYear = of("січня", "января", "лютого", "февраля", "березня", "марта", "квітня", "апреля", "травня",
            "мая", "червня", "июня", "липня", "июля", "серпня", "августа", "вересня", "сентября", "жовтня",
            "октября", "листопада", "ноября", "грудня", "декабря"),
            wasteWorkBefore = of("продав", "бармен", "ресто", "студент");

    public static boolean isAge(String text) {
        return !isEmpty(text) && text.matches(is_age);
    }

    public static boolean isEquals(String workplace, List<String> list) {
        return list.stream().anyMatch(workplace.toLowerCase()::equals);
    }
}
