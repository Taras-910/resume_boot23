package ua.top.bootjava.util.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.to.ResumeTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ua.top.bootjava.aggregator.Installation.reasonDateLoading;
import static ua.top.bootjava.aggregator.strategy.DjinniStrategy.getDjinniAddr;
import static ua.top.bootjava.util.AggregatorUtil.getExtract;
import static ua.top.bootjava.util.AggregatorUtil.isToValid;
import static ua.top.bootjava.util.CommonUtil.*;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.*;
import static ua.top.bootjava.util.parser.data.DataUtil.*;
import static ua.top.bootjava.util.parser.data.DateToUtil.getToLocalDate;
import static ua.top.bootjava.util.parser.data.SalaryUtil.getToSalaries;
import static ua.top.bootjava.util.parser.data.UrlUtil.getToUrl;

public class ElementUtil {
    public static final Logger log = LoggerFactory.getLogger(ElementUtil.class);

    public static List<ResumeTo> getResumesDjinni(Elements elements, Freshen freshen) {
        List<ResumeTo> list = new ArrayList<>();
        elements.forEach(element -> {
            try {
                LocalDate localDate = getToLocalDate(element.getElementsByTag("small").text());
                if (localDate.isAfter(reasonDateLoading)) {
                    String skills, address, workBefore, title = getUpperStart(element.getElementsByClass("profile").tagName("a").text());
                    address = getDjinniAddr(getExtract(address_field_extract, element.getElementsByAttributeValueStarting("class", "tiny-profile-details").text()));
                    workBefore = getLimitation(element.nextElementSibling().ownText());
                    skills = getLimitation(element.nextElementSibling().nextElementSibling().ownText());
                    Integer[] salaries = getToSalaries(element.getElementsByAttributeValueStarting("class", "profile-details-salary").text());
                    if (isToValid(freshen, getJoin(title, workBefore, skills))) {
                        ResumeTo r = new ResumeTo();
                        r.setTitle(title);
                        r.setName(link);
                        r.setAge(link);
                        r.setAddress(address);
                        r.setSalary(salaries[0]);
                        r.setWorkBefore(workBefore);
                        r.setUrl(getToUrl(djinni, element.getElementsByTag("a").attr("href")));
                        r.setSkills(skills);
                        r.setReleaseDate(localDate);
                        list.add(r);
                    }
                }
            } catch (Exception e) {
                log.error(error, e.getLocalizedMessage(), element);
            }
        });
        return list;
    }

    public static List<ResumeTo> getResumesGrc(Elements elements, Freshen freshen) {
        List<ResumeTo> list = new ArrayList<>();
        elements.forEach(element -> {
            try {
                LocalDate localDate = getToLocalDate(element.getElementsByClass("resume-search-item__date").text());
                if (localDate.isAfter(reasonDateLoading)) {
                    String address, skills,  workBefore, age, title = getUpperStart(element.getElementsByClass("resume-search-item__name").text());
                    workBefore = getLimitation(element.getElementsByAttributeValueStarting("data-qa", "resume-serp_resume-item-content").text());
                    age = element.getElementsByAttributeValueStarting("data-qa", "resume-serp__resume-age").text();
                    age = isAge(age) ? age : link;
                    address = "";
                    skills = "";
                    Integer[] salaries = getToSalaries(element.getElementsByClass("bloko-text bloko-text_large bloko-text_strong").text());
                    if (isToValid(freshen, getJoin(title, workBefore)) && isAgeValid(age) && !workBefore.equals(link)) {
                        ResumeTo r = new ResumeTo();
                        r.setTitle(title);
                        r.setName(link);
                        r.setAge(link);
                        r.setAddress(address);
                        r.setSalary(salaries[0]);
                        r.setWorkBefore(workBefore);
                        r.setUrl(getToUrl(grc, element.getElementsByClass("resume-search-item__name").attr("href")));
                        r.setSkills(skills);
                        r.setReleaseDate(localDate);
                        list.add(r);


                    }
                }
            } catch (Exception e) {
                log.error(error, e.getLocalizedMessage(), element);
            }
        });
        return list;
    }


    public static List<ResumeTo> getResumesWork(Elements elements, Freshen freshen) {
        List<ResumeTo> list = new ArrayList<>();
        int i = 1;
        for (Element element : elements) {
            try {
                Elements elementDate = element.select("div");
                Elements elementAddress = element.select("span").next();
                LocalDate localDate = getToLocalDate(elementDate.get(elementDate.size() - 1).ownText());
                if (localDate.isAfter(reasonDateLoading)) {
                    String workBefore, skills, age, age1, title = getUpperStart(element.getElementsByTag("a").first().text());
                    skills = getLimitation(element.getElementsByClass("add-bottom").tagName("div").text());
                    workBefore = element.getElementsByTag("ul").text();
                    workBefore = isEmpty(workBefore) ? skills : getLimitation(workBefore);
                    age = element.getElementsByAttributeValueContaining("data-toggle", "popover").next().next().text().trim();
                    age1 = element.getElementsByTag("b").addClass("text-muted").next().next().text();
                    age = isAge(age) ? getExtract(age_field_extract, age) : isAge(age1) ? getExtract(age_field_extract, age1) : link;
                    Integer[] salaries = getToSalaries(element.getElementsByAttributeValueStarting("class", "nowrap").tagName("span").text());
                    if (!workBefore.equals(link)  && isAgeValid(age) &&  isToValid(freshen, getJoin(title, workBefore, skills))) {
                        ResumeTo r = new ResumeTo();
                        r.setTitle(title);
                        r.setName(element.getElementsByTag("b").text());
                        r.setAge(age);
                        r.setAddress(elementAddress.get(elementAddress.size() - 1).ownText());
                        r.setSalary(salaries[0]);
                        r.setWorkBefore(workBefore);
                        r.setUrl(getToUrl(work, element.getElementsByTag("a").attr("href")));
                        r.setSkills(skills);
                        r.setReleaseDate(localDate);
                        list.add(r);
                    }
                }
            } catch (Exception e) {
                log.error(error, e.getLocalizedMessage(), element);
            }
        }
        return list;
    }
}
// djinni
/*
                        System.out.println(i.getAndIncrement() + "-".repeat(70));
                        Elements elementList = element.getElementsByClass("profile-details-salary").prev();
                        System.out.print("elementList: "+ elementList.size()+" \n");
                        elementList.forEach(e -> System.out.println(" ::: "+ e.ownText() + " ::: "));
                        System.out.println();

                        System.out.println("dateString="+ element.getElementsByTag("small").text());
                        System.out.println("date="+r.getReleaseDate());
                        System.out.println("titleString="+ xssClear(element.getElementsByClass("profile").tagName("a").text()));
                        System.out.println("title="+r.getTitle());

                        System.out.println("nameString="+ link);
                        System.out.println("name="+r.getName());
                        System.out.println("ageString="+ link);
                        System.out.println("age="+r.getAge());

                        System.out.println("addressString="+ xssClear(element.getElementsByAttributeValueStarting("class", "tiny-profile-details").text()));
                        System.out.println("address="+r.getAddress());
                        System.out.println("salaryString="+ xssClear(element.getElementsByAttributeValueStarting("class", "profile-details-salary").text()));
                        System.out.println("salary="+r.getSalary());

                        System.out.println("workBeforeString="+ xssClear(element.nextElementSibling().ownText()));
                        System.out.println("workBefore="+r.getWorkBefore());
                        System.out.println("urlString="+ xssClear(element.getElementsByTag("a").attr("href")));
                        System.out.println("url="+r.getUrl());
                        System.out.println("skillsString="+ xssClear(element.nextElementSibling().nextElementSibling().ownText()));
                        System.out.println("skills="+r.getSkills());

*/
// work
/*                        System.out.println(i++ + "-".repeat(70));
                        elementList = element.select("span").next();
                        System.out.print("elementList: "+ elementList.size()+" \n");
                        elementList.forEach(e -> System.out.println(" ::: "+ e.ownText() + " ::: "));
                        System.out.println();

                        System.out.println("dateString="+ element.getElementsByAttributeValueEnding("class", "pull-right").text());
                        System.out.println("date="+r.getReleaseDate());
                        System.out.println("titleString="+ xssClear(element.getElementsByTag("a").first().text()));
                        System.out.println("title="+r.getTitle());

                        System.out.println("nameString="+ xssClear(element.getElementsByTag("b").text()));
                        System.out.println("name="+r.getName());

                        System.out.println("ageString="+ xssClear(element.getElementsByAttributeValueContaining("data-toggle", "popover").next().next().text()));
                        System.out.println("ageString1="+ xssClear(element.getElementsByTag("b").addClass("text-muted").next().next().text()));
                        System.out.println("age="+r.getAge());

                        System.out.println("addressString="+ xssClear(element.getElementsByAttributeValueContaining("class", "add-bottom").prev().text()));
                        System.out.println("address="+r.getAddress());
                        System.out.println("salaryString="+ xssClear(element.getElementsByAttributeValueStarting("class", "nowrap").tagName("span").text()));
                        System.out.println("salary="+r.getSalary());

                        System.out.println("workBeforeString="+ xssClear(element.getElementsByTag("ul").text()));
                        System.out.println("workBefore="+r.getWorkBefore());

                        System.out.println("urlString="+ xssClear(element.getElementsByTag("a").attr("href")));
                        System.out.println("url="+r.getUrl());
                        System.out.println("skillsString="+ xssClear(element.getElementsByClass("add-bottom").tagName("div").text()));
                        System.out.println("skills="+r.getSkills());
*/
/*
                        System.out.println(i++ + "-".repeat(70));

                        System.out.println("dateString="+ element.getElementsByAttributeValueEnding("class", "pull-right").text());
                        System.out.println("date="+rTo.getReleaseDate());
                        System.out.println("titleString="+ xssClear(element.getElementsByTag("a").first().text()));
                        System.out.println("title="+rTo.getTitle());
                        System.out.println("nameString="+ xssClear(element.getElementsByTag("b").text()));
                        System.out.println("name="+rTo.getName());
                        System.out.println("ageString="+ xssClear(element.getElementsByAttributeValueContaining("data-toggle", "popover").next().next().text()));
                        System.out.println("age="+rTo.getAge());
                        System.out.println("addressString="+ xssClear(element.getElementsByAttributeValueContaining("class", "add-bottom").prev().text()));
                        System.out.println("address="+rTo.getAddress());
                        System.out.println("salaryString="+ xssClear(element.getElementsByAttributeValueStarting("class", "nowrap").tagName("span").text()));
                        System.out.println("salary="+rTo.getSalary());

                        System.out.println("workBeforeString="+ xssClear(element.getElementsByTag("ul").text()));
                        System.out.println("workBefore="+rTo.getWorkBefore());
                        System.out.println("urlString="+ xssClear(element.getElementsByTag("a").attr("href")));
                        System.out.println("url="+rTo.getUrl());
                        System.out.println("skillsString="+ xssClear(element.getElementsByClass("add-bottom").tagName("div").text()));
                        System.out.println("skills="+rTo.getSkills());
*/
