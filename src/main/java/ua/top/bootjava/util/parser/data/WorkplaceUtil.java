package ua.top.bootjava.util.parser.data;

import static java.util.List.of;
import static ua.top.bootjava.util.CommonUtil.getJoin;
import static ua.top.bootjava.util.CommonUtil.isMatch;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.otherAria;

public class WorkplaceUtil {

    public static String getGrc(String workplace) {
        return switch (workplace) {
            case "київ", "киев" -> "115";
            case "дніпро", "днепр" -> "2126";
            case "одеса", "одесса" -> "2188";
            case "львів", "львов" -> "2180";
            case "россия" -> "113";
            case "москва" -> "1";
            case "казань" -> "1624";
            case "пермь" -> "1317";
            case "томск" -> "1255";
            case "самара" -> "1586";
            case "воронеж" -> "1844";
            case "краснодар" -> "1438";
            case "ульяновск" -> "1614";
            case "харьків", "харьков" -> "2206";
            case "санкт-петербург" -> "2";
            case "нижний новгород" -> "1679";
            case "мінськ", "минск" -> "1002";
            case "ростов-на-дону" -> "1530";
            case "новосибирск" -> "1202";
            case "екатеринбург" -> "1261";
            case "германія", "германия" -> "27";
            case "норвегия", "норвегія" -> "207";
            case "україна", "украина" -> "5";
            case "ізраїль", "израиль" -> "33";
            case "швеція", "швеция" -> "149";
            case "польща", "польша" -> "74";
            case "сша" -> "85";
            case "all" -> "all";
            default -> workplace.equals("remote") ?
                    "&schedule=remote" : getJoin("&area=", workplace);
        };
    }

    public static String getHabr(String workplace) {
        return switch (workplace) {
            case "київ", "киев" -> "908";
            case "новосибирск" -> "717";
            case "санкт-петербург" -> "679";
            case "нижний новгород" -> "715";
            case "минск", "мінськ" -> "713";
            case "ростов-на-дону" -> "726";
            case "екатеринбург" -> "693";
            case "краснодар" -> "707";
            case "ульяновск" -> "739";
            case "воронеж" -> "692";
            case "москва" -> "678";
            case "казань" -> "698";
            case "самара" -> "728";
            case "пермь" -> "722";
            case "томск" -> "736";
            case "remote", "all" -> "all";
            default -> "-1";
        };
    }

    public static String getRabota(String workplace) {
        return switch (workplace) {
            case "київ", "киев" -> "киев";
            case "львів", "львов" -> "львов";
            case "дніпро", "днепр" -> "днепр";
            case "одеса", "одесса" -> "одесса";
            case "харків", "харьков" -> "харьков";
            default -> DataUtil.isEquals(workplace, of("foreign", "россия", "минск")) || isMatch(otherAria, workplace) ?
                    "другие_страны" : "вся_украина";
        };
    }

    /*public static String getDjinni(String workplace) {
        return switch (workplace) {
            case "київ", "киев", "kiev" -> "kyiv";
            case "дніпро", "днепр", "dnepr" -> "dnipro";
            case "харків", "харьков" -> "kharkiv";
            case "одеса", "одесса" -> "odesa";
            case "львів", "львов" -> "lviv";
            case "миколаїв", "николаев" -> "mykolaiv";
            case "вінниця", "винница" -> "vinnitsia";
            case "запоріжжя", "запорожье" -> "zaporizhzhya";
            case "чорновці", "черновцы" -> "chernivtsi";
            case "чернігів", "чернигов" -> "chernigiv";
            case "івано-франківськ", "ивано-франковск" -> "ivano-frankivsk";
            case "ужгород" -> "uzhgorod";
            case "минск" -> "minsk";
            default -> workplace;
        };
    }*/
    public static String getUA_en(String workplace) {
        return switch (workplace) {
            case "київ", "киев", "kiev", "kyiv" -> "Kyiv";
            case "запоріжжя", "запорожье", "zaporizhzhya" -> "Zaporizhzhya";
            case "миколаїв", "николаев", "mykolaiv" -> "Mykolaiv";
            case "чорновці", "черновцы", "chernivtsi" -> "Chernivtsi";
            case "чернігів", "чернигов", "chernigiv" -> "Chernigiv";
            case "вінниця", "винница", "vinnitsia" -> "Vinnitsia";
            case "харків", "харьков", "kharkiv" -> "Kharkiv";
            case "дніпро", "днепр", "dnipro", "dnepr" -> "Dnipro";
            case "одеса", "одесса", "odessa" -> "Odesa";
            case "львів", "львов", "lviv" -> "Lviv";
            case "ужгород", "uzhgorod" -> "Uzhgorod";
            case "івано-франківськ", "ивано-франковск" -> "Ivano-Frankivsk";
            case "тернопіль", "тернополь", "ternopil" -> "Ternopil";
            case "минск", "мінськ", "minsk" -> "minsk";
            default -> "";
        };
    }



}
