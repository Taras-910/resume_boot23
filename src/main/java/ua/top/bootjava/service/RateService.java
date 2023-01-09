package ua.top.bootjava.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.model.Rate;
import ua.top.bootjava.repository.RateRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ua.top.bootjava.aggregator.Installation.baseCurrency;
import static ua.top.bootjava.aggregator.Provider.getRates;
import static ua.top.bootjava.util.validation.ValidationUtil.checkNew;
import static ua.top.bootjava.util.validation.ValidationUtil.checkNotFound;

@Slf4j
@Service
@AllArgsConstructor
public class RateService {
    public static Map<String, Rate> mapRates = new HashMap<>();

    private RateRepository repository;

    public Rate get(int id) {
        log.info("get by id {}", id);
        return repository.getExisted(id);
    }

    public Rate getByName(String name) {
        log.info("get by name {}", name);
        return checkNotFound(repository.getByName(name), name);
    }

    public List<Rate> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    @Transactional
    public Rate create(Rate rate) {
        log.info("create {}", rate);
//        Assert.notNull(rate, not_be_null);
        checkNew(rate);
        return repository.save(rate);
    }

    @Transactional
    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @Transactional
    public void deleteAll() {
        log.info("deleteAll");
        repository.deleteAll();
    }

    @Transactional
    public void updateRateDB() {
        log.info("update rate by baseCurrency {}", baseCurrency);
        List<Rate> ratesNew = getRates();
        List<Rate> ratesDB = getAll();
        if(!ratesDB.isEmpty()){
            ratesNew.forEach(ratesDB::remove);
            ratesNew.addAll(ratesDB);
        }
        if(!ratesNew.isEmpty() && repository != null){
            repository.deleteAll();
        }
        if(repository != null) repository.saveAll(ratesNew);
    }

    @PostConstruct
    public void CurrencyRatesMapInit() {
        log.info("currency rates map init \n{}\n", ": <|> ".repeat(20));
        List<Rate> rates = getAll();
        rates.forEach(r -> mapRates.put(r.getName(), r));
        System.out.println("\nmapRates=\n"+mapRates.toString());
    }
}
