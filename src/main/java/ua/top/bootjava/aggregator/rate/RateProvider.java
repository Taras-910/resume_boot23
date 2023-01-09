package ua.top.bootjava.aggregator.rate;


import ua.top.bootjava.model.Rate;

import java.util.List;

public interface RateProvider {
    List<Rate> getRates (String baseCurrency);
}
