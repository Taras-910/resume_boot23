package ua.top.bootjava.web.rest.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.top.bootjava.AbstractControllerTest;
import ua.top.bootjava.error.NotFoundException;
import ua.top.bootjava.model.Rate;
import ua.top.bootjava.service.RateService;
import ua.top.bootjava.util.JsonUtil;
import ua.top.testData.RateTestData;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.top.bootjava.TestUtil.Matcher.readFromJson;
import static ua.top.bootjava.error.ErrorType.VALIDATION_ERROR;
import static ua.top.bootjava.web.rest.admin.RateRestController.REST_URL;
import static ua.top.testData.RateTestData.*;
import static ua.top.testData.UserTestData.*;

class RateRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private RateService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RATE1_ID))
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(rate_matcher.contentJson(rate1));
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
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + admin_id))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        Iterable<Rate> allRates = allRates();
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(rate_matcher.contentJson(allRates));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Rate newRate = new Rate(RateTestData.getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(JsonUtil.writeValue(newRate)))
                .andExpect(status().isCreated());
        Rate created = readFromJson(action, Rate.class);
        newRate.setId(created.getId());
        rate_matcher.assertMatch(created, newRate);
        rate_matcher.assertMatch(service.get(created.getId()), newRate);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Rate invalid = new Rate(RateTestData.getNew());
        invalid.setName("n".repeat(7));
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RATE1_ID)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(RATE1_ID));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + not_found)
                .with(csrf()))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RATE1_ID))
                .andExpect(status().isForbidden());
    }
}
