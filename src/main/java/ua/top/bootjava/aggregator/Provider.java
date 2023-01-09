package ua.top.bootjava.aggregator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.top.bootjava.aggregator.rate.RateProvider;
import ua.top.bootjava.aggregator.rate.TradingEconomicsProvider;
import ua.top.bootjava.aggregator.strategy.Strategy;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.model.Rate;
import ua.top.bootjava.to.ResumeTo;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static ua.top.bootjava.aggregator.Installation.baseCurrency;
import static ua.top.bootjava.util.InformUtil.number_inform;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.ratesAria;

public class Provider {
    private static final Logger log = LoggerFactory.getLogger(Provider.class);
    private final Strategy strategy;

    public Provider(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<ResumeTo> getResumes(Freshen freshen) throws IOException {
        List<ResumeTo> list = strategy.getResumes(freshen);
        log.info(number_inform, this.strategy.getClass().getSimpleName(), list.size());
        return list;
    }

    public static List<Rate> getRates(){
        RateProvider rateProvider = new TradingEconomicsProvider();
//        RateProvider rateProvider = new TestProvider();
        return rateProvider.getRates(baseCurrency).stream()
                .filter(r -> ratesAria.contains(r.getName()))
                .collect(Collectors.toList());
    }
}
