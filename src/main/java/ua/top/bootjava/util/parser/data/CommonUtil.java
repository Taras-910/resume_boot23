package ua.top.bootjava.util.parser.data;

import ua.top.bootjava.aggregator.Installation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ua.top.bootjava.aggregator.Installation.maxLengthText;


public class CommonUtil {
    private static StringBuilder builder;
    public static final String link = "see the card";
    public static String getJoin(String ...text) {
        if (builder == null) {
            builder = new StringBuilder(1024);
        }
        //https://stackoverflow.com/questions/5192512/how-can-i-clear-or-empty-a-stringbuilder
        builder.setLength(0);
        Arrays.stream(text).forEach(s -> builder.append(s));
        return builder.toString();
    }

    public static boolean isMatches(List<List<String>> lists, String text) {
        return isMatch(getCommonList(lists), text.toLowerCase());
    }

    public static boolean isMatch(List<String> list, String text) {
        return list.stream().anyMatch(l -> isContains(text.toLowerCase(), l));
    }

    public static List<String> getCommonList(List<List<String>> lists) {
        List<String> list = new ArrayList();
        lists.forEach(l -> list.addAll(l));
        return list;
    }

    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty() || text.trim().equals("•");
    }

    public static String getLinkIfEmpty(String text) {
        return isEmpty(text) ? link : text;
    }

    public static String getUpperStart(String text) {
        return !isEmpty(text) && text.length() > 1 ? getJoin(text.substring(0, 1).toUpperCase(),text.substring(1)) : link;
    }

    public static String getReplace(String text, List<String> names, String replacement) {
        for (String name : names) {
            text = !name.equals(replacement) ? text.replaceAll(name, replacement) : text;
        }
        return text.trim();
    }

    public static String getLimitation(String text) {
        return isEmpty(text) ? link : text.length() > maxLengthText ? text.substring(0, maxLengthText) : text;
    }

    public static boolean isAgeValid(String age) {
        return age.equals(link) || Integer.parseInt(age.substring(0, age.indexOf(" "))) >= Installation.maxAge;
    }

    public static boolean isContains(String text, String s) {
        return text.indexOf(s) != -1;
    }
}
