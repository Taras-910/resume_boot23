package ua.top.bootjava.web.rest.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.AbstractControllerTest;
import ua.top.bootjava.error.NotFoundException;
import ua.top.bootjava.model.Resume;
import ua.top.bootjava.service.ResumeService;
import ua.top.bootjava.service.VoteService;
import ua.top.bootjava.to.ResumeTo;
import ua.top.bootjava.util.JsonUtil;
import ua.top.bootjava.util.ResumeUtil;
import ua.top.testData.ResumeToTestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.top.bootjava.SecurityUtil.setTestAuthUser;
import static ua.top.bootjava.TestUtil.Matcher.readFromJson;
import static ua.top.bootjava.util.ResumeUtil.fromTo;
import static ua.top.bootjava.util.ResumeUtil.getTos;
import static ua.top.bootjava.web.rest.admin.ResumeRestController.REST_URL;
import static ua.top.testData.ResumeTestData.*;
import static ua.top.testData.ResumeToTestData.resume_to_matcher;
import static ua.top.testData.UserTestData.*;
import static ua.top.testData.VoteTestData.vote1;

@Transactional
class ResumeRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private VoteService voteService;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + resume1_id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(resume_to_matcher.contentJson(ResumeUtil.getTo(resume1, voteService.getAllForAuth())));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + not_found))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + resume1_id))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(resume_to_matcher.contentJson(getTos(RESUMES_GET_ALL, List.of(vote1))));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + resume1_id))
                .andExpect(status().isNoContent());
        setTestAuthUser(admin);
        assertThrows(NotFoundException.class, () -> resumeService.delete(resume1_id));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + not_found))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional
    void update() throws Exception {
        ResumeTo updated = new ResumeTo(ResumeToTestData.getToUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + resume1_id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        setTestAuthUser(admin);
        resume_matcher.assertMatch(fromTo(updated), resumeService.get(resume1_id));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional
    void updateInvalid() throws Exception {
        ResumeTo invalid = new ResumeTo(ResumeToTestData.getToUpdated());
        invalid.setTitle(null);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + resume2_id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional
    void create() throws Exception {
        ResumeTo newResumeTo = new ResumeTo(ResumeToTestData.getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newResumeTo)))
                .andDo(print())
                .andExpect(status().isCreated());
        Resume createdResume = readFromJson(action, Resume.class);
        int newIdResume = createdResume.id();
        newResumeTo.setId(newIdResume);
        resume_matcher.assertMatch(createdResume, fromTo(newResumeTo));
        setTestAuthUser(admin);
        resume_to_matcher.assertMatch(ResumeUtil.getTo(resumeService.get(newIdResume),
                voteService.getAllForAuth()), newResumeTo);
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional
    void createInvalid() throws Exception {
        ResumeTo invalid = new ResumeTo(ResumeToTestData.getNew());
        invalid.setTitle(null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByFilter() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "filter")
                .param("language", "php")
                .param("workplace", "киев")
                .param("level", "middle"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(resume_to_matcher.contentJson(getTos(List.of(resume1), voteService.getAllForAuth())));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByFilterInvalid() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "filter")
                .param("language", "")
                .param("workplace", "")
                .param("level", ""))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
