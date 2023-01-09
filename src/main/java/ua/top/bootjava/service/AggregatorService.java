package ua.top.bootjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.model.Resume;
import ua.top.bootjava.to.ResumeTo;
import ua.top.bootjava.util.AggregatorUtil;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ua.top.bootjava.SecurityUtil.setTestAuthUser;
import static ua.top.bootjava.aggregator.ProviderUtil.getAllProviders;
import static ua.top.bootjava.model.Goal.UPGRADE;
import static ua.top.bootjava.util.AggregatorUtil.getAnchor;
import static ua.top.bootjava.util.AggregatorUtil.getForUpdate;
import static ua.top.bootjava.util.CommonUtil.isMatch;
import static ua.top.bootjava.util.FreshenUtil.asNewFreshen;
import static ua.top.bootjava.util.ResumeUtil.fromTos;
import static ua.top.bootjava.util.UsersUtil.asAdmin;
import static ua.top.bootjava.util.parser.data.DataUtil.common_number;
import static ua.top.bootjava.util.parser.data.DataUtil.finish_message;

@Slf4j
@Service
@AllArgsConstructor
@EnableScheduling
public class AggregatorService {
    private final ResumeService resumeService;
    private final FreshenService freshenService;
    private final RateService rateService;
    private final VoteService voteService;

    public void refreshDB(Freshen freshen) {
        log.info("refreshDB by freshen {}", freshen);
        Instant start = Instant.now();
        List<Resume> resumesNet = fromTos(getAllProviders().selectBy(freshen));
        if (!resumesNet.isEmpty()) {
            Freshen newFreshen = freshenService.create(freshen);
            List<Resume>
                    resumesDb = resumeService.getAll(),
                    resumesForCreate = new ArrayList<>(),
                    resumesForUpdate = new ArrayList<>();
            Map<String, Resume> mapDb = resumesDb.stream()
                    .collect(Collectors.toMap(AggregatorUtil::getAnchor, r -> r));
            resumesNet.forEach(r -> {
                r.setFreshen(newFreshen);
                if (mapDb.containsKey(getAnchor(r))) {
                    resumesForUpdate.add(getForUpdate(r, mapDb.get(getAnchor(r))));
                } else {
                    if (!isMatch(resumesForCreate.stream().map(AggregatorUtil::getAnchor).collect(Collectors.toList()), getAnchor(r))) {
                        resumesForCreate.add(r);
                    }
                }
            });
            executeRefreshDb(resumesDb, resumesForCreate, resumesForUpdate);
            long timeElapsed = Duration.between(start, Instant.now()).toMillis();
            log.info(finish_message, timeElapsed, resumesForCreate.size(), resumesForUpdate.size(), freshen);
        }
    }

    @Transactional
    protected void executeRefreshDb(List<Resume> resumesDb, List<Resume> resumesForCreate, List<Resume> resumesForUpdate) {
//        resumeService.deleteExceedLimitDb(resumesDb.size() + resumesForCreate.size() - limitResumesKeeping);
        Set<Resume> resumes = new HashSet<>(resumesForUpdate);
        resumes.addAll(resumesForCreate);
        if (!resumes.isEmpty()) {
            resumeService.createUpdateList(new ArrayList<>(resumes));
        }
    }

    @Transactional
    public void deleteOutDated() {
        resumeService.deleteOutDated();
        freshenService.deleteOutDated();
        voteService.deleteOutDated();
    }

/*
    public void updateRateDB() {
        rateService.updateRateDB();
    }
*/

    public static void main(String[] args) throws IOException {
        setTestAuthUser(asAdmin());

        List<ResumeTo> resumeTos = getAllProviders().selectBy(asNewFreshen("all", "all", "all", UPGRADE));
        AtomicInteger i = new AtomicInteger(1);
        resumeTos.forEach(vacancyNet -> log.info("\nvacancyNet № {}\n{}\n", i.getAndIncrement(), vacancyNet.toString()));
        log.info(common_number, resumeTos.size());


    }

}
//        work address
//
//	      djinni   grc*10   habr  rabota   work  linkedin  total
//all	    49	  49(0)	     1	     6	    16	   (100)	121
//Украина	32	   4(0)	     -	     6	    30	     -	     72
//remote 	 -	  49(0)	     1	     3	    12	    (5)	     65
//foreign	49	   2(0)	     1	     1	     -	     -	     53
//Киев	    15	   1(0)	     1	     3	    15	     -	     35
//Минск	     1	  10(0)	     1	     6	     -	     -	     18
//Львов	     6	    -	     -	     8	     2	     -	     16
//Харьков	 5	    -	     -	     2	     5	     -	     12
//Одесса	 5	    -	     -	     2	     4	     -	     11
//Санкт-П	 5	   5(0)	     1	     -	     -	     -	     11
//Москва	 -	   8(0)	     1	     -	     -	     -	      9



