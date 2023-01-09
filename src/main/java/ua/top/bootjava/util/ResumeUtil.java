package ua.top.bootjava.util;

import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.model.Resume;
import ua.top.bootjava.model.Vote;
import ua.top.bootjava.to.ResumeTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;
import static ua.top.bootjava.util.CommonUtil.getJoin;
import static ua.top.bootjava.util.CommonUtil.isMatch;
import static ua.top.bootjava.util.parser.data.ConstantsUtil.*;
import static ua.top.bootjava.util.parser.data.DataUtil.*;

public class ResumeUtil {

    private static List<ResumeTo> getEmpty() {
        return of(new ResumeTo(0, "", "", "", "", -1,
                "No filtering records found, refresh DB",
                "", "", LocalDate.now(), "", "", "", false));
    }

    public static List<ResumeTo> getTos(List<Resume> resumes, List<Vote> votes) {
        return resumes.isEmpty() ? getEmpty() : resumes.stream()
                .map(r -> getTo(r, votes))
                .sorted(ResumeTo::compareTo)
                .collect(Collectors.toList());
    }

    public static ResumeTo getTo(Resume r, List<Vote> votes) {
        boolean toVote = votes.stream().anyMatch(vote -> r.getId().equals(vote.getResumeId()));
        return new ResumeTo(r.getId(), r.getTitle(), r.getName(), r.getAge(), r.getAddress(),
                r.getSalary(), r.getWorkBefore(), r.getUrl(), r.getSkills(), r.getReleaseDate(),
                r.getFreshen().getLanguage(), r.getFreshen().getLevel(), r.getFreshen().getWorkplace(), toVote);
    }

    public static List<Resume> fromTos(List<ResumeTo> vTos) {
        return vTos.stream().map(ResumeUtil::fromTo).collect(Collectors.toList());
    }

    public static Resume fromTo(ResumeTo rTo) {
        return new Resume(rTo.getId(), rTo.getTitle(), rTo.getName(), rTo.getAge(), rTo.getAddress(),
                rTo.getSalary(), rTo.getWorkBefore(), rTo.getUrl(), rTo.getSkills(),
                rTo.getReleaseDate() != null ? rTo.getReleaseDate() : LocalDate.now().minusDays(7));
    }

    public static Resume fromToForUpdate(ResumeTo rTo, Resume r) {
        assert r != null;
        Resume resume = new Resume(
                r.getId(),
                rTo.getTitle(),
                r.getName().equals(link) ? link : rTo.getName(),
                r.getAge().equals(link) ? link : rTo.getAge(),
                r.getAddress().equals(link) ? link : rTo.getAddress(),
                rTo.getSalary(),
                rTo.getWorkBefore(),
                rTo.getUrl(),
                r.getSkills().equals(link) ? link : rTo.getSkills(),
                r.getReleaseDate());
        resume.setFreshen(r.getFreshen());
        return resume;
    }

    public static List<Resume> getFilter(List<Resume> resumes, Freshen f) {
        return resumes.stream().
                filter(r -> isSuit(r, f.getLanguage(), "language") && isSuit(r, f.getLevel(), "level") &&
                        isSuit(r, f.getWorkplace(), "workplace"))
                .collect(Collectors.toList());
    }

    public static boolean isSuit(Resume r, String field, String fieldKind) {
        String text = fieldKind.equals("language") ?
                getJoin(r.getSkills(), r.getTitle(), r.getFreshen().getLanguage()).toLowerCase():
                fieldKind.equals("level") ?
                        getJoin(r.getTitle(), r.getWorkBefore(), r.getFreshen().getLevel()).toLowerCase() :
                        getJoin(r.getAddress(), r.getWorkBefore()).toLowerCase();
        return switch (field.toLowerCase()) {
            case "all" -> true;
            case "java", "react", "ruby" -> text.matches(".*\\b" + field + "\\b.*");
            case "trainee", "стажировка", "стажер", "internship", "интерн",
                    "intern" -> getAria(field).stream()
                    .anyMatch(a -> text.matches(".*\\b" + a + "\\b.*"));
            default -> getAria(field).size() == 1 ? text.indexOf(field) > -1 : getAria(field).stream()
                    .anyMatch(a -> !isMatch(getForeign(), field) ? text.indexOf(a) > -1 : text.indexOf(a) > -1
                            && uaAria.stream().noneMatch(cityUA -> text.toLowerCase().indexOf(cityUA) > -1));
        };
    }

    private static List<String> getAria(String text) {
        return switch (text) {
            case "intern", "trainee", "интерн", "стажировка", "стажер" -> traineeAria;
            case "junior" -> juniorAria;
            case "middle" -> middleAria;
            case "senior" -> seniorAria;
            case "expert", "lead", "тимлид", "team lead" -> expertAria;
            case "ukraine", "україна", "украина", "ua" -> citiesUA;
            case "київ", "киев", "kiev", "kyiv" -> kievAria;
            case "foreign", "за_рубежем", "за рубежем", "за кордоном", "другие страны" -> getForeign();
            case "remote", "relocate", "релокейт", "удаленно", "віддалено" -> remoteAria;
            case "харків", "харьков", "kharkiv" -> kharkivAria;
            case "дніпро", "днепр", "dnipro" -> dniproAria;
            case "одеса", "одесса", "odesa" -> odesaAria;
            case "львів", "львов", "lviv" -> lvivAria;
            case "запоріжжя", "запорожье", "zaporizhzhya" -> zaporizhzhyaAria;
            case "миколаїв", "николаев", "mykolaiv" -> mykolaivAria;
            case "чорновці", "черновцы", "chernivtsi" -> chernivtsiAria;
            case "чернігів", "чернигов", "chernigiv" -> chernigivAria;
            case "вінниця", "винница", "vinnitsia" -> vinnitsiaAria;
            case "ужгород", "uzhgorod" -> uzhgorodAria;
            case "івано-франківськ", "ивано-франковск", "ivano-frankivsk" -> ivano_frankivskAria;
            case "польша", "poland", "polski" -> polandAria;
            case "варшава", "warszawa" -> warszawaAria;
            case "krakow", "краков" -> krakowAria;
            case "wroclaw", "вроцлав" -> wroclawAria;
            case "gdansk", "гданськ", "гданск" -> gdanskAria;
            case "poznan", "познань" -> poznanAria;
            case "minsk", "минск", "мінськ" -> minskAria;
            default -> of(text);
        };
    }

    public static List<String> getForeign() {
        List<String> foreign = new ArrayList<>(otherAria);
        foreign.addAll(citiesPl);
        foreign.addAll(foreignAria);
        return foreign;
    }
}
