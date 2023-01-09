package ua.top.bootjava.util;

import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.model.Resume;
import ua.top.bootjava.to.ResumeTo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ua.top.bootjava.util.CommonUtil.*;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.link;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.workersIT;
import static ua.top.bootjava.util.parser.data.DataUtil.*;

public class AggregatorUtil {

    public static ResumeTo createTo(ResumeTo resumeTo, Freshen freshen) {
        resumeTo.setWorkplace(freshen.getWorkplace());
        resumeTo.setLevel(freshen.getLevel());
        resumeTo.setLanguage(freshen.getLanguage());
        resumeTo.setToVote(false);
        return resumeTo;
    }

    public static String getAnchor(Resume r) {
        String work = r.getWorkBefore();
        for (String period : getWorkPeriod(work)) {
            work = work.replaceAll(period, "");
        }
        return getJoin(r.getTitle(), " ", work).toLowerCase();
    }

    public static Resume getForUpdate(Resume r, Resume resumeDb) {
        r.setId(resumeDb.getId());
        r.setTitle(resumeDb.getTitle().equals(link) ? r.getTitle() : resumeDb.getTitle());
        r.setAge(resumeDb.getAge().equals(link) ? r.getAge() : resumeDb.getAge());
        r.setAddress(resumeDb.getAddress().equals(link) ? r.getAddress() : resumeDb.getAddress());
        r.setSalary(resumeDb.getSalary() == 1 ? r.getSalary() : resumeDb.getSalary());
        r.setWorkBefore(resumeDb.getWorkBefore().equals(link) ? r.getWorkBefore() : resumeDb.getWorkBefore());
        r.setSkills(resumeDb.getSkills().equals(link) ? r.getSkills() : resumeDb.getSkills());
        return r;
    }

    public static boolean isToValid(Freshen f, String text) {
        String temp = text.toString().toLowerCase();
        return (temp.indexOf(f.getLanguage()) > -1 || isMatch(workersIT, temp))
                && wasteWorkBefore.stream().noneMatch(temp::contains);
    }

    public static String getExtract(String regexName, String text) {
        if (isEmpty(text)) {
            return link;
        }
        List<String> list = new ArrayList<>();
        //https://stackoverflow.com/questions/63964529/a-regex-to-get-any-price-string
        Matcher m = Pattern.compile(regexName, Pattern.CASE_INSENSITIVE).matcher(text);
        while (m.find()) {
            list.add(m.group());
        }
        return list.size() > 0 ? list.get(0) : regexName.indexOf("field") < 0 ? text : link;
    }

    public static List<String> getWorkPeriod(String text) {
        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile(date_period_extract, Pattern.CASE_INSENSITIVE).matcher(text);
        while (m.find()) {
            String s = m.group();
            if (s.matches(is_period_work)) {
                list.add(s);
            }
        }
        return list;
    }
}
