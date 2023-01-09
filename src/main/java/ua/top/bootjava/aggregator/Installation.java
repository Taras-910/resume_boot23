package ua.top.bootjava.aggregator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.top.bootjava.aggregator.strategy.Strategy;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ua.top.bootjava.aggregator.Provider.getRates;
import static ua.top.bootjava.aggregator.dispatcher.Dispatcher.allProviders;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.recall;

public class Installation {
    private static final Logger log = LoggerFactory.getLogger(Installation.class);
    public static final String baseCurrency = "usd";
    public static int
            maxAge = 21,
            maxLengthText = 250,
            limitResumesKeeping = 3100,
            limitFreshensFilterKeeping = 150,
            limitVoteKeeping = 500,
    //            limitPages = 50,
//            repeatOfCalls = 3;
    limitPages = 1,
            repeatOfCalls = 1;

    public static LocalDate
            reasonDateLoading = LocalDateTime.now().toLocalDate().minusDays(90),
            reasonPeriodKeeping = LocalDateTime.now().toLocalDate().minusDays(120);

    //    public static boolean testProvider = true;
    public static boolean testProvider = false;

    public static void setTestProvider() { Installation.testProvider = true; }
    public static void offTestProvider() {
        Installation.testProvider = false;
    }

    public static boolean autoRefreshProviders = false;
    public static void setAutoRefreshProviders() { Installation.autoRefreshProviders = true; }
    public static void offAutoRefreshProviders() { Installation.autoRefreshProviders = false; }

    public static void setTestReasonPeriodToKeep() {
        Installation.reasonPeriodKeeping = LocalDateTime.now().toLocalDate().minusDays(365);
    }

    public static void reCall(int listSize, Strategy strategy){
        if (listSize == 0 && repeatOfCalls > 0){
            log.info("reCall attemptToCall={}", repeatOfCalls);
            allProviders.addLast(new Provider(strategy));
            repeatOfCalls--;
        }
    }

    public static void reCallRate(int size) {
        if (size == 0 && repeatOfCalls > 0){
            log.info(recall, repeatOfCalls);
            repeatOfCalls--;
            getRates();
        }
    }
}
