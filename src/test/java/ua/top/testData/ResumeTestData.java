package ua.top.testData;

import ua.top.bootjava.TestUtil;
import ua.top.bootjava.model.Resume;
import ua.top.bootjava.to.ResumeTo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static ua.top.bootjava.model.AbstractBaseEntity.START_SEQ;
import static ua.top.testData.FreshenTestData.freshen1;
import static ua.top.testData.FreshenTestData.freshen2;

public class ResumeTestData {
    public static final TestUtil.Matcher<Resume> resume_matcher = TestUtil.usingIgnoringFieldsComparator(Resume.class, "freshen", "recordedDate");

    public static final int resume1_id = START_SEQ + 4;
    public static final int resume2_id = resume1_id + 1;
    public static final Resume resume1 = getResume1();
    public static final Resume resume2 = getResume2();

    private static Resume getResume1() {
        Resume resume =new Resume(resume1_id,"Middle Game Developer", "Василь Васильевич", "21",
                "Киев", 3000, "КСПО, 10 лет и 2 месяца",
                "https://grc.ua/resume/40006938?query=java", "Spring, SQL, REST, PHP",
                LocalDate.of(2021, 10, 20));
        resume.setFreshen(freshen1);
        return resume;
    }

    private static Resume getResume2() {
        Resume vacancy = new Resume(resume2_id, "Middle Java-разработчик", "Виктор Михайлович", "22",
                "Днепр", 2000, "Днепросталь, 4 года и 5 месяцев",
                "https://grc.ua/resume/40006938?query=java",
                "Понимание JVM. Умение отлаживать и профилировать java-приложения",
                LocalDate.of(2021, 10, 20));
        vacancy.setFreshen(freshen2);
        return vacancy;
    }

    public static final Resume resume3 = new Resume(null,"new PHP Developer", "Петр Петрович", "23",
            "Харьков", 500, "Колледж 3 года и 4 месяца",
            "https://www.w3schools.com/1/1.asp", "РНР, HTTP", LocalDate.now().minusDays(5));

    public static final Resume resume4 = new Resume(null,"new Developer", "Сергей Сергеевич", "24",
            "Киев", 1500, "Тракторный завод, 7 лет и 10 месяцев",
            "https://www.w3schools.com/2/2.asp", "Ruby, JavaScript", LocalDate.now().minusDays(5));

    public static final List<Resume> RESUMES_GET_ALL = getAll();

    private static List<Resume> getAll() {
        return  Arrays.asList(resume2, resume1);
    }

    public static List<Resume> getListResumes() {
        return Arrays.asList(resume3, resume4);
    }

    public static ResumeTo getNew() {
        return new ResumeTo(null, "Junior", "Саша", "25","киев, метро Вокзальная", 500,
                "Университет", "https://www.baeldung.com/spring-type-conversions",
                "Java Core, Angular", LocalDate.of(2020, 10, 26), "java",
                "junior", "киев", false);
    }
}
