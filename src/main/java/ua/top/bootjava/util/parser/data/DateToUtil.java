package ua.top.bootjava.util.parser.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static java.time.LocalDate.now;
import static java.time.LocalDate.parse;
import static java.util.List.of;
import static ua.top.bootjava.util.CommonUtil.*;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.*;
import static ua.top.bootjava.util.parser.data.PatternUtil.*;

public class DateToUtil {
    private final static Logger log = LoggerFactory.getLogger(DateToUtil.class);
    public static final LocalDate defaultDate = LocalDate.now().minusDays(7);

    public static LocalDate getToLocalDate(String originText) {
        String preText = formatToNumAndWord(originText.toLowerCase());
        String text = getExtract(preText);
        boolean isDateNumber = pattern_date_is_numbers.matcher(preText).find();
        if (isEmpty(preText) || !text.contains(" ") && !isDateNumber) {
            return defaultDate;
        }
        try {
            if (!isDateNumber) {
                String[] parts = text.split(" ");
                int number = Integer.parseInt(parts[pattern_is_numb.matcher(parts[0]).find() ? 0 : 1]);
                String name = parts[pattern_is_numb.matcher(parts[1]).find() ? 0 : 1];
                LocalDate localDate = getLocalDate(number, name);
                return localDate.isBefore(now()) || localDate.isEqual(now()) ? localDate : localDate.minusYears(1);
            }
            return parse(text.contains("t") ? text.substring(0, text.indexOf("t")) : text);
        } catch (Exception e) {
            log.error(ConstantsUtil.error, e.getMessage(), originText);
            return defaultDate;
        }
    }

    public static String getExtract(String text) {
        //https://stackoverflow.com/questions/63964529/a-regex-to-get-any-price-string
        Matcher m = pattern_extract_date.matcher(text);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group());
        }
        return list.size() > 0 ? list.get(0) : text;
    }

    static LocalDate getLocalDate(int number, String name) {
        return isMatch(monthsOfYearAria, name) ? LocalDate.of(now().getYear(), getMonth(name), number) :
                isMatch(minuteAriaDate, name) ? LocalDateTime.now().minusMinutes(number).toLocalDate() :
                        isMatch(hourAria, name) ? LocalDateTime.now().minusHours(number).toLocalDate() :
                                isMatch(dayAria, name) ? now().minusDays(number) :
                                        isMatch(weekAriaDate, name) ? now().minusWeeks(number) :
                                                isMatch(monthAriaDate, name) ? now().minusMonths(number) : defaultDate;
    }

    static String formatToNumAndWord(String originText) {
        originText = getReplace(originText, of("einem", "Il y a un", "temu", "einem"), "1");
        originText = getReplace(originText, of("nowa", "нове", "сейчас", "только что"), "0 минут");
        originText = getReplace(originText, of("сьогодні", "сегодня", "today", "днес", "heute"), "0 сьогодні");
        originText = getReplace(originText, of("yesterday", "вчера", "вчора", "gestern"), "1 сьогодні");
        return originText;
    }

    public static int getMonth(String month) {
        return switch (month) {
            case "january", "jan", "січня", "января", "январь" -> 1;
            case "february", "feb", "лютого", "февраля", "февраль" -> 2;
            case "march", "mar", "березня", "марта", "март" -> 3;
            case "april", "apr", "квітня", "апреля", "апрель" -> 4;
            case "may", "травня", "мая", "май" -> 5;
            case "june", "jun", "червня", "июня", "июнь" -> 6;
            case "july", "jul", "липня", "июля", "июль" -> 7;
            case "august", "aug", "серпня", "августа", "август" -> 8;
            case "september", "sep", "вересня", "сентября", "сентябрь" -> 9;
            case "october", "oct", "жовтня", "октября", "октябрь" -> 10;
            case "november", "nov", "листопада", "ноября", "ноябрь" -> 11;
            case "december", "dec", "грудня", "декабря", "декабрь" -> 12;
            default -> LocalDate.now().getMonth().getValue();
        };
    }
}
