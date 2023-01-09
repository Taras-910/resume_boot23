package ua.top.bootjava.util;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.model.Goal;
import ua.top.bootjava.to.ResumeTo;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static java.util.Collections.singleton;
import static org.springframework.util.StringUtils.hasText;
import static ua.top.bootjava.SecurityUtil.authId;
import static ua.top.bootjava.aggregator.Installation.limitFreshensFilterKeeping;
import static ua.top.bootjava.model.Goal.FILTER;
import static ua.top.bootjava.model.Goal.UPGRADE;

@UtilityClass
public class FreshenUtil {
    public static Logger log = LoggerFactory.getLogger(FreshenUtil.class);

    public static Freshen getFreshenFromTo(ResumeTo rTo) {
        return new Freshen(null, now(), rTo.getLanguage(), rTo.getLevel(),
                hasText(rTo.getWorkplace()) ? rTo.getWorkplace() : rTo.getAddress(), singleton(UPGRADE), authId());
    }

    public static Freshen asNewFreshen(Freshen f) {
        return new Freshen(f.getId(), now(), f.getLanguage(), f.getLevel(), f.getWorkplace(),
                f.getGoals() == null ? singleton(UPGRADE) : f.getGoals(),
                f.getUserId() == null ? authId() : f.getUserId());
    }

    public static Freshen asNewFreshen(String language, String level, String workplace, Goal goal) {
        return new Freshen(null, now(), language, level, workplace, singleton(goal == null ? UPGRADE : goal), authId());
    }

    public static List<Freshen> getExceedLimit(List<Freshen> freshensDb) {
        return freshensDb.stream()
                .filter(f -> f.getGoals().contains(FILTER))
                .sorted(FreshenUtil::compareDate)
                .skip(limitFreshensFilterKeeping)
                .collect(Collectors.toList());
    }

    public static int compareDate(Freshen f1, Freshen f2) {
        return f2.getRecordedDate().isAfter(f1.getRecordedDate()) ? 1 : -1;
    }


}
