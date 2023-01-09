package ua.top.testData;


import ua.top.bootjava.TestUtil;
import ua.top.bootjava.model.Rate;

import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static ua.top.bootjava.model.AbstractBaseEntity.START_SEQ;

public class RateTestData {
    public static final TestUtil.Matcher<Rate> rate_matcher = TestUtil.usingIgnoringFieldsComparator(Rate.class);

    public static final int RATE1_ID = START_SEQ + 8;
    public static final Rate rate1 = new Rate(RATE1_ID,  "USDUSD", 1.0, of(2020,10,25));
    public static final Rate rate2 = new Rate(RATE1_ID + 1,  "USDUAH", 36.53, of(2020,10,25));
    public static final Rate rate3 = new Rate(RATE1_ID + 2,  "USDPLN", 4.8544, of(2020,10,25));
    public static final Rate rate4 = new Rate(RATE1_ID + 3,  "USDKZT", 469.5, of(2020,10,25));
    public static final Rate rate5 = new Rate(RATE1_ID + 4,  "USDGBP", 0.87148, of(2020,10,25));
    public static final Rate rate6 = new Rate(RATE1_ID + 5,  "USDEUR", 1.00711, of(2020,10,25));
    public static final Rate rate7 = new Rate(RATE1_ID + 6,  "USDCZK", 24.7275, of(2020,10,25));
    public static final Rate rate8 = new Rate(RATE1_ID + 7,  "USDCAD", 1.35791, of(2020,10,25));
    public static final Rate rate9 = new Rate(RATE1_ID + 9,  "USDBYR", 2.52, of(2020,10,25));
    public static final Rate rate10 = new Rate(RATE1_ID + 8,  "USDBGN", 1.9701, of(2020,10,25));

    public static Rate getNew() {
        return new Rate(null, "UUUAAA", 77.7777, now());
    }

    public static Rate getUpdated() {
        return new Rate(null,"USDGBP",0.897, now());
    }

    public static List<Rate> allRates(){
        return Arrays.asList(rate10, rate9, rate8, rate7, rate6, rate5, rate4, rate3, rate2, rate1);
    }
}
