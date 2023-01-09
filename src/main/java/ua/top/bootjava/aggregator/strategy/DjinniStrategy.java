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
import static java.util.List.of;
import static ua.top.bootjava.aggregator.Installation.reCall;
import static ua.top.bootjava.util.CommonUtil.*;
import static ua.top.bootjava.util.parser.ElementUtil.getResumesDjinni;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.*;
import static ua.top.bootjava.util.parser.data.DataUtil.get_resume;
import static ua.top.bootjava.util.parser.data.PagesUtil.getMaxPages;
import static ua.top.bootjava.util.parser.data.PagesUtil.getPage;
import static ua.top.bootjava.util.parser.data.WorkplaceUtil.getUA_en;

public class DjinniStrategy implements Strategy {
    private final static Logger log = LoggerFactory.getLogger(DjinniStrategy.class);
    public static final String url = "https://djinni.co/developers/?%s%s%s%s%s";
    //    https://djinni.co/developers/?region=UKR&title=Java&keywords=middle&options=full_text&location=kyiv&page=2
//https://djinni.co/developers/?%s%s%s%s%s
//
//region (workplace.equals(all) ? “ :   workplace.equals(remote) ? employment=remote :
// getJoin(region= , isMatches(of(uaAria, citiesU, workplace) ?   UKR :   isMatches(of(plAria, citiesPl), workplace) ?   POL :  isMatches(of(deAria, citiesDe), workplace) ?   DEU : isMatches(of(deAria, citiesDe), workplace) ?   DEU :  isMatch(foreignAria, workplace) ?   eu :   other   )
//language  (language.equals(all) ? “ : getJoin(workplace.equals(all) ? “ : &, title=, language  )
//
//level(all?   “” :   getJoin(language.equals(all) && workplace.equals(all) ? “ : &, keywords=, level, &options=full_text ) ),
//
//workplace(  workplace.equals(all) || !isMatch(citiesUA, workplace) ? “ : getJoin(&location=, workplace  )   )
//page(page.equals(1) ? “ : getJoin( &page=,page)
    protected Document getDocument(String workplace, String language, String page, String level) {
        return DocumentUtil.getDocument(format(url,
                getRegion(workplace),
                language.equals("all") ? "" : getJoin(workplace.equals("all") ? "" : "&", "title=", getUpperStart(language)),
                level.equals("all") ? "" : getJoin(language.equals("all") && workplace.equals("all") ? "" : "&", "keywords=", level, "&options=full_text" ),
                workplace.equals("all") || !isMatch(citiesUA, workplace) ? "" : getJoin("&location=", getUA_en(workplace).toLowerCase()),
                getPage(djinni, page)
        ));
    }

    @Override
    public List<ResumeTo> getResumes(Freshen freshen) throws IOException {
        String language = freshen.getLanguage(), workplace = freshen.getWorkplace(), level=freshen.getLevel();
        log.info(get_resume, language, level, workplace);
        Set<ResumeTo> set = new LinkedHashSet<>();
        int page = 1;
        while (true) {
            Document doc = getDocument(workplace, language, String.valueOf(page), freshen.getLevel());
            Elements elements = doc == null ? null : doc.select("div.candidate-header");
            if (elements == null || elements.size() == 0) break;
            set.addAll(getResumesDjinni(elements, freshen));
            if (page < getMaxPages(djinni, workplace)) page++;
            else break;
        }
        reCall(set.size(), new DjinniStrategy());
        return new ArrayList<>(set);
    }

    private String getRegion(String workplace) {
        return (workplace.equals("all") ? "" :   workplace.equals("remote") ? "employment=remote" :
                getJoin("region=", isMatches(of(uaAria, citiesUA), workplace) ?
                        "UKR" : isMatches(of(plAria, citiesPl), workplace) ?
                        "POL" : isMatches(of(deAria, citiesDe), workplace) ?
                        "DEU" : isMatch(foreignAria, workplace) ? "eu" : "other"));
    }

    public static String getDjinniAddr(String address) {
        address = isContains(address," · Будь") ? address.replaceAll(" · Будь", "") : address;
        return address;
    }
}
