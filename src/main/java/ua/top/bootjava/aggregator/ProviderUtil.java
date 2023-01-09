package ua.top.bootjava.aggregator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.top.bootjava.aggregator.dispatcher.Dispatcher;
import ua.top.bootjava.aggregator.strategy.DjinniStrategy;
import ua.top.bootjava.aggregator.strategy.TestStrategy;
import ua.top.bootjava.aggregator.strategy.WorkStrategy;

import static ua.top.bootjava.aggregator.Installation.autoRefreshProviders;
import static ua.top.bootjava.aggregator.Installation.testProvider;
import static ua.top.bootjava.util.AutoRefreshUtil.getKey;
import static ua.top.bootjava.util.AutoRefreshUtil.mapStrategies;

public class ProviderUtil {
    public static final Logger log = LoggerFactory.getLogger(ProviderUtil.class);

    public static Dispatcher getAllProviders() {
        if (testProvider) {
            return new Dispatcher(new Provider(new TestStrategy()));
        } else if (autoRefreshProviders) {
            log.info("autoRefreshProviders");
            return new Dispatcher(
                    mapStrategies.get(getKey(3)))
                    /*,
                    mapStrategies.get(getKey(2) + 2),
                    mapStrategies.get(4)*/
                    ;
        } else {
            log.info("allProviders");
            return new Dispatcher(
                    new Provider(new DjinniStrategy()),      /*ua, foreign, remote, all  50pages */
                    new Provider(new WorkStrategy())         /*нет за_рубежем*/
            );
        }
    }
}

//https://career.softserveinc.com/en-us/vacancies
//https://jobs.ua/vacancy/kiev/rabota-java-developer
//https://kiev.careerist.ru/jobs-java-developer/
//https://kiev.jobcareer.ru/jobs/java/?feed=
//https://app.headz.io/candidates/new
//https://distillery.com/careers/senior-backend-developer-java-tg/
//https://edc.sale
//https://www.olx.ua
//https://www.ria.com
//https://trud.ua
//http://trudbox.com.ua/kiev/jobs-programmist
