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
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.service.FreshenService;
import ua.top.bootjava.util.JsonUtil;
import ua.top.testData.FreshenTestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.top.bootjava.TestUtil.Matcher.readFromJson;
import static ua.top.bootjava.error.ErrorType.VALIDATION_ERROR;
import static ua.top.testData.FreshenTestData.*;
import static ua.top.testData.UserTestData.*;

class FreshenRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = FreshenRestController.REST_URL;
    private static final String REST_URL_SLASH = FreshenRestController.REST_URL + '/';
    @Autowired
    private FreshenService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + freshen1_id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(freshen_matcher.contentJson(freshen1));
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
        perform(MockMvcRequestBuilders.get(REST_URL + admin_id))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        Iterable<Freshen> iterable = List.of(freshen2, freshen1);
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(freshen_matcher.contentJson(iterable));
    }

    @Test
    @Transactional
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Freshen newFreshen = new Freshen(FreshenTestData.getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newFreshen)))
                .andDo(print())
                .andExpect(status().isCreated());
        Freshen created = readFromJson(action, Freshen.class);
        newFreshen.setId(created.getId());
        freshen_matcher.assertMatch(created, newFreshen);
        freshen_matcher.assertMatch(service.get(created.getId()), newFreshen);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Freshen invalid = new Freshen(freshen1);
        invalid.setWorkplace("n".repeat(101));
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + freshen1_id))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(freshen1_id));
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
    void update() throws Exception {
        Freshen updated = new Freshen(FreshenTestData.getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + freshen1_id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        freshen_matcher.assertMatch(service.get(freshen1_id), updated);
    }
}
