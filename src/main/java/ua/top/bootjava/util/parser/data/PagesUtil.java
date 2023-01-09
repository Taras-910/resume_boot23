package ua.top.bootjava.util.parser.data;

import static java.lang.Math.min;
import static ua.top.bootjava.aggregator.Installation.limitPages;
import static ua.top.bootjava.util.CommonUtil.getJoin;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.*;

public class PagesUtil {

    public static String getPage(String site, String page) {
//        return page.equals("1") ? "" : getJoin("&page=", page);
        return switch (site) {
            case djinni, grc -> page.equals("1") ? "" : getJoin("&page=", page);
            case work -> page.equals("1") ? "" : getJoin("page=", page);
            default -> "";
        };
    }

    public static int getMaxPages(String site, String workplace) {
        int pages = switch (workplace) {
            case "all" -> switch (site) {
                case djinni -> 49;
                case rabota -> 6;
                case work -> 15;
                default -> 1;
            };
            case "foreign" -> switch (site) {
                case djinni -> 49;
                default -> 1;
            };
            case "remote" -> switch (site) {
                case rabota -> 3;
                case work -> 12;
                default -> 1;
            };
            case "украина" -> switch (site) {
                case djinni -> 32;
                case rabota -> 6;
                case work -> 30;
                default -> 1;
            };
            case "киев" -> switch (site) {
                case djinni, work  -> 15;
                case rabota -> 3;
                default -> 1;
            };
            case "одесса" -> switch (site) {
                case djinni -> 3;
                case rabota -> 2;
                case work -> 4;
                default -> 1;
            };
            case "харьков" -> switch (site) {
                case djinni, work -> 5;
                case rabota -> 2;
                default -> 1;
            };
            case "минск" -> switch (site) {
                case rabota -> 6;
                default -> 1;
            };
            case "москва" -> switch (site) {
                default -> 1;
            };
            case "львов" -> switch (site) {
                case djinni -> 6;
                case rabota -> 8;
                case work -> 2;
                default -> 1;
            };
            default -> 1;
        };
        return min(limitPages, pages);
    }
}
