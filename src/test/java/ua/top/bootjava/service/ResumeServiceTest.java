package ua.top.bootjava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import ua.top.bootjava.AbstractServiceTest;
import ua.top.bootjava.error.NotFoundException;
import ua.top.bootjava.model.Resume;
import ua.top.bootjava.to.ResumeTo;
import ua.top.bootjava.util.ResumeUtil;
import ua.top.testData.ResumeToTestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.top.bootjava.util.DateTimeUtil.testDate;
import static ua.top.bootjava.util.ResumeUtil.fromTo;
import static ua.top.testData.FreshenTestData.freshen1;
import static ua.top.testData.ResumeTestData.*;
import static ua.top.testData.ResumeToTestData.resume_to_matcher;
import static ua.top.testData.UserTestData.ADMIN_MAIL;
import static ua.top.testData.UserTestData.not_found;

class ResumeServiceTest extends AbstractServiceTest {

    @Autowired
    private ResumeService resumeService;
    @Autowired
    private VoteService voteService;

    @Test
    void get() {
        Resume resume = resumeService.get(resume1_id);
        resume_matcher.assertMatch(resume, resume1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> resumeService.get(not_found));
    }

    @Test
    void getAll() {
        List<Resume> all = resumeService.getAll();
        resume_matcher.assertMatch(all, RESUMES_GET_ALL);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() {
        resumeService.delete(resume1_id);
        assertThrows(NotFoundException.class, () -> resumeService.delete(resume1_id));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> resumeService.delete(not_found));
    }

    @Test
    void createErrorData() {
        assertThrows(Exception.class, () -> resumeService.create(new ResumeTo(null, null, "Виктор", "20", "London", 100, "IBM", "https://www.ukr.net/1/11", "Microsoft", testDate, "java", "middle", "киев", false)));
        assertThrows(Exception.class, () -> resumeService.create(new ResumeTo(null, "Developer", null, "21", "London", 100, "IBM", "https://www.ukr.net/1/11", "Microsoft", testDate, "java", "middle", "киев", false)));
        assertThrows(Exception.class, () -> resumeService.create(new ResumeTo(null, "Developer", "Виктор", "22", null, 100, "IBM", "https://www.ukr.net/1/11", "Microsoft", testDate, "java", "middle", "киев", false)));
        assertThrows(Exception.class, () -> resumeService.create(new ResumeTo(null, "Developer", "Виктор", "23", "London", 100, "IBM", "https://www.ukr.net/1/11", null, testDate, "java", "middle", "киев", false)));
    }

    @Test
    void createUpdateList() {
        List<Resume> actual = List.of(resume3, resume4);
        actual.forEach(r -> r.setFreshen(freshen1));
        List<Resume> created = resumeService.createUpdateList(actual);
        for (int i = 0; i < created.size(); i++) {
            actual.get(i).setId(created.get(i).getId());
        }
        resume_matcher.assertMatch(created, actual);
    }

    @Test
    void createListErrorData() {
        assertThrows(NullPointerException.class, () -> resumeService.createUpdateList(List.of(null, new Resume(resume3))));
        assertThrows(NullPointerException.class, () -> resumeService.createUpdateList(null));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() {
        ResumeTo vTo = ResumeToTestData.getUpdate();
        Resume updated = resumeService.updateTo(vTo);
        resume_matcher.assertMatch(fromTo(vTo), updated);
    }

    @Test
    void updateErrorData() {
        assertThrows(IllegalArgumentException.class, () -> resumeService.updateTo(new ResumeTo()));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() {
        ResumeTo newResumeTo = new ResumeTo(ResumeToTestData.getNew());
        Resume createdResume = resumeService.create(newResumeTo);
        int newIdResume = createdResume.id();
        newResumeTo.setId(newIdResume);
        resume_matcher.assertMatch(createdResume, fromTo(newResumeTo));
        resume_to_matcher.assertMatch(ResumeUtil.getTo(resumeService.get(newIdResume),
                voteService.getAllForAuth()), newResumeTo);
    }
}
