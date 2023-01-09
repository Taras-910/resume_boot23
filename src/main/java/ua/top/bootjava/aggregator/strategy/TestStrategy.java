package ua.top.bootjava.aggregator.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.to.ResumeTo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static ua.top.bootjava.util.parser.data.DataUtil.get_resume;


public class TestStrategy implements Strategy {
    private final static Logger log = LoggerFactory.getLogger(TestStrategy.class);

    @Override
    public List<ResumeTo> getResumes(Freshen freshen) throws IOException {
        String city = freshen.getWorkplace(), language = freshen.getWorkplace();
        log.info(get_resume, city, language);
        return getTestList();
    }

    public static List<ResumeTo> getTestList() {
        ResumeTo resumeTo1 = new ResumeTo(null, "Junior", "Андрей", "21","Киев, центр",
                80000, "IBM, 1 год и 5 месяцев",
                "https://example1.ua/resume/40006938?query=java","Java, Spring",
                LocalDate.now().minusDays(5), null, "middle", null, false);

        ResumeTo resumeTo2 = new ResumeTo(null, "Middle", "Анатолий", "22","Киев, Независимости",
                90000, "ЭКСПО, 2 года, 1 месяц", "https://example2.net/vacancy?123","SpringBoot, Java",
                LocalDate.now().minusDays(7), null, "middle", null, false);

        ResumeTo resumeTo3 = new ResumeTo(null, "Java-Developer", "Борис", "23", "Харьков",
                150000,"Company, 3 года", "https://example3.com/vacancy/123a","Java, JavaScript",
                LocalDate.now().minusDays(3), null, "middle", null, false);

        ResumeTo resumeTo4 = new ResumeTo(null, "Middle C++ Engineer", "Дмитрий", "24","Киев",
                160000, "Factory",
                "https://example4.com/%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B2%D0%B0%D0%BA%D0%B0%D0%BD%D1%81%D0%B8%D0%B8?jk=b25fbd86b8fb297e",
                "Experience with java app server, e.g. Weblogic, Tomcat", LocalDate.now().minusDays(3), "java", "middle", null, false);

        ResumeTo resumeTo5 = new ResumeTo(null, "Middle/Senior", "Евгений", "25","Варшава",
                170000, "GlobalLogic, 5 лет и два месяца",
                "https://example4.com/%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B2%D0%B0%D0%BA%D0%B0%D0%BD%D1%81%D0%B8%D0%B8?jk=b25fbd86b8fb297e",
                "Apache HTTP Server, bash scripting, Docker", LocalDate.now().minusDays(3), "java", "middle", null, false);

        ResumeTo resumeTo6 = new ResumeTo(null, "Senior/Engineer", "Леонид", "26","Минск",
                180000, "GlobalLogic, 2 года и 6 месяцев",
                "https://example4.com/%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B2%D0%B0%D0%BA%D0%B0%D0%BD%D1%81%D0%B8%D0%B8?jk=b25fbd86b8fb297e",
                " Kubernetes, Oracle-DBA, Perl, Tomcat", LocalDate.now().minusDays(3), "java", "middle", null, false);
        return List.of(resumeTo1, resumeTo2, resumeTo3, resumeTo4);
    }
}
