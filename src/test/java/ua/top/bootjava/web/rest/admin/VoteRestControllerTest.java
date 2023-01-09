package ua.top.bootjava.web.rest.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.top.bootjava.AbstractControllerTest;
import ua.top.bootjava.error.NotFoundException;
import ua.top.bootjava.model.Vote;
import ua.top.bootjava.service.VoteService;
import ua.top.bootjava.util.JsonUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.top.bootjava.SecurityUtil.setTestAuthUser;
import static ua.top.bootjava.TestUtil.Matcher.readFromJson;
import static ua.top.bootjava.web.rest.admin.VoteRestController.REST_URL;
import static ua.top.testData.ResumeTestData.resume2_id;
import static ua.top.testData.UserTestData.*;
import static ua.top.testData.VoteTestData.*;

class VoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';
    @Autowired
    private VoteService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + vote1_id))
                .andDo(print())
                .andExpect(status().isOk())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(vote_matcher.contentJson(vote1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + not_found))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + vote1_id))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        Iterable<Vote> iterable = List.of(vote1, vote2);
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(vote_matcher.contentJson(iterable));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllForAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "auth"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(vote_matcher.contentJson(List.of(vote1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Vote newVote = new Vote(null, LocalDate.now(), resume2_id, admin_id);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("resumeId", String.valueOf(resume2_id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        Vote created = readFromJson(action, Vote.class);
        newVote.setId(created.getId());
        vote_matcher.assertMatch(created, newVote);
        setTestAuthUser(admin);
        vote_matcher.assertMatch(service.get(created.getId()), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Vote updated = new Vote(vote1);
        updated.setResumeId(resume2_id);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + vote1_id)
                .param("resumeId", String.valueOf(resume2_id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        setTestAuthUser(admin);
        updated.setLocalDate(LocalDate.now());
        vote_matcher.assertMatch(service.get(vote1_id), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + vote1_id))
                .andDo(print())
                .andExpect(status().isNoContent());
        setTestAuthUser(admin);
        assertThrows(NotFoundException.class, () -> service.get(vote1_id));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + not_found)
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void setVote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + resume2_id)
                .param("toVote", String.valueOf(true))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
