package ua.top.bootjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.repository.FreshenRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ua.top.bootjava.aggregator.Installation.reasonPeriodKeeping;
import static ua.top.bootjava.util.FreshenUtil.getExceedLimit;
import static ua.top.bootjava.util.validation.ValidationUtil.*;

@Slf4j
@Service
@AllArgsConstructor
public class FreshenService {
    private FreshenRepository repository;
//    @Autowired
//    private AggregatorService aggregatorService;

    public Freshen get(int id) {
        log.info("get by id {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Freshen> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    public Freshen create(Freshen freshen) {
        log.info("create {}", freshen);
        Assert.notNull(freshen, " must not be null ");
        checkNew(freshen);
        return repository.save(freshen);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public void update(Freshen freshen, int id) {
        log.info("update {} with id={}", freshen, id);
        assureIdConsistent(freshen, id);
        Assert.notNull(freshen, " must not be null ");
        checkNotFoundWithId(repository.save(freshen), freshen.id());
    }

    public void deleteExceed() {
        log.info("deleteExceed");
        repository.deleteAll(getExceedLimit(getAll()));
    }

/*    public void refreshDB(Freshen freshen) {
        log.info("refreshDB freshen {}", freshen);
        aggregatorService.refreshDB(freshen);
    }*/

    @Transactional
    public void deleteOutDated() {
        log.info("deleteOutDated reasonPeriodToKeep {}", LocalDateTime.of(reasonPeriodKeeping, LocalTime.MIN));
//        repository.deleteOutDated(LocalDateTime.of(reasonPeriodKeeping, LocalTime.MIN));
        repository.deleteAll(repository.getOutDated(LocalDateTime.of(reasonPeriodKeeping, LocalTime.MIN)));
    }

}
