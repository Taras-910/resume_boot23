package ua.top.bootjava.util.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static ua.top.bootjava.util.DateTimeUtil.*;

public class DateTimeFormatters {

    public static class DateFormatter implements Formatter<Date> {

        @Override
        public Date parse(String text, Locale locale) {
            Date localDate;
            try {
                localDate = new SimpleDateFormat("yyyy-MM-dd").parse(text);
            } catch (ParseException e) {
                throw new IllegalArgumentException("error argument localDate=" + text);
            }
            return localDate;
        }

        @Override
        public String print(Date dt, Locale locale) {
            return new SimpleDateFormat("yyyy-MM-dd").format(dt);
        }
    }

    public static class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

        @Override
        public LocalDateTime parse(String text, Locale locale) {
            return parseLocalDateTime(text);
        }

        @Override
        public String print(LocalDateTime lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) {
            return parseLocalDate(text);
        }

        @Override
        public String print(LocalDate lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) {
            return parseLocalTime(text);
        }

        @Override
        public String print(LocalTime lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }
}
