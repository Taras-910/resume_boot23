package ua.top.bootjava.aggregator.strategy;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.to.ResumeTo;
import ua.top.bootjava.util.parser.DocumentUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static ua.top.bootjava.aggregator.Installation.reCall;
import static ua.top.bootjava.util.CommonUtil.*;
import static ua.top.bootjava.util.parser.ElementUtil.getResumesWork;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.*;
import static ua.top.bootjava.util.parser.data.DataUtil.get_resume;
import static ua.top.bootjava.util.parser.data.PagesUtil.getMaxPages;
import static ua.top.bootjava.util.parser.data.PagesUtil.getPage;
import static ua.top.bootjava.util.parser.data.UrlUtil.getLevel;
import static ua.top.bootjava.util.parser.data.WorkplaceUtil.getUA_en;

public class WorkStrategy implements Strategy {
    private final static Logger log = LoggerFactory.getLogger(WorkStrategy.class);
    private final static String URL = "https://www.work.ua/resumes%s-it%s/%s%s";
//      https://www.work.ua/ru/resumes-kyiv-java/?employment=76&experience=0&page=2

    protected Document getDocument(String workspace, String language, String level, String page) {
        String city = isMatch(citiesUA, workspace) ? getUA_en(workspace).toLowerCase() :
                isMatch(remoteAria, workspace) ? "remote" : "other";
        return DocumentUtil.getDocument(format(URL,
                isMatch(uaAria, workspace) || workspace.equals("all") ? "" : getJoin("-", city),
                language.equals("all") ? "" : getJoin("-", language),
                level.equals("all") ? "" : getJoin("?experience=", getLevel(work, level)),
                getJoin(level.equals("all") ? (page.equals("1") ? "" : "?") : "&", getPage(work, page))));
    }

    @Override
    public List<ResumeTo> getResumes(Freshen freshen) throws IOException {
        String workplace = freshen.getWorkplace(), language = freshen.getLanguage(), level = freshen.getLevel();

        log.info(get_resume, language, level, workplace);
        Set<ResumeTo> set = new LinkedHashSet<>();
        int page = 1;
        while (true) {
            Document doc = getDocument(workplace, language, freshen.getLevel(), valueOf(page));
            Elements elements = doc == null ?
                    null : doc.getElementsByClass("card card-hover resume-link card-visited wordwrap");
            if (elements == null || elements.size() == 0) break;
            set.addAll(getResumesWork(elements, freshen));
            if (page < getMaxPages(work, workplace)) page++;
            else break;
        }
        reCall(set.size(), new WorkStrategy());
        return new ArrayList<>(set);
    }

    public static String getToAddressWork(String address) {
        return isContains(address,"·") ? address.substring(address.lastIndexOf("·") + 1).trim() : address;
    }
}
