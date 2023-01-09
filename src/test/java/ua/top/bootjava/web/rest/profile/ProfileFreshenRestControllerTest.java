package ua.top.bootjava.web.rest.profile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.AbstractControllerTest;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.model.Goal;
import ua.top.bootjava.service.FreshenService;
import ua.top.bootjava.service.ResumeService;
import ua.top.bootjava.util.JsonUtil;
import ua.top.testData.FreshenTestData;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.top.bootjava.TestUtil.Matcher.userHttpBasic;
import static ua.top.bootjava.aggregator.Installation.setTestProvider;
import static ua.top.bootjava.aggregator.Installation.setTestReasonPeriodToKeep;
import static ua.top.bootjava.util.FreshenUtil.asNewFreshen;
import static ua.top.bootjava.web.rest.profile.ProfileFreshenRestController.REST_URL;
import static ua.top.testData.FreshenTestData.*;
import static ua.top.testData.UserTestData.*;

class ProfileFreshenRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';
    @Autowired
    private FreshenService freshenService;
    @Autowired
    private ResumeService resumeService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + freshen2_id)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(freshen_matcher.contentJson(freshen2));
    }

    @Test
    void getOwn() throws Exception {
        Iterable<Freshen> freshens = List.of(freshen2);
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(freshen_matcher.contentJson(freshens));
    }

    @WithUserDetails(value = ADMIN_MAIL)
    @Test
    @Transactional
    void refreshDB() throws Exception {
        Freshen freshen = new Freshen(null, null, "Java", "middle", "Киев", Collections.singleton(Goal.UPGRADE), null);
//        List<Resume> resumesDbBefore = resumeService.getAll();
        List<Freshen> freshensDbBefore = freshenService.getAll();
        setTestProvider();
        setTestReasonPeriodToKeep();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(asNewFreshen(freshen))))
                .andDo(print())
                .andExpect(status().isNoContent());

        Freshen fr = freshenService.create(FreshenTestData.getNew());
        List<Freshen> allFreshens = freshenService.getAll();
//https://stackoverflow.com/questions/9933403/subtracting-one-arraylist-from-another-arraylist

        freshensDbBefore.forEach(allFreshens::remove);
        Freshen newFreshen = allFreshens.get(0);
        fr.setRecordedDate(newFreshen.getRecordedDate());
        fr.setUserId(newFreshen.getUserId());
        fr.setId(newFreshen.getId());
        freshen_matcher.assertMatch(newFreshen, fr);

//        List<Resume> resumesTest = fromTos(getTestList());
//        List<Resume> resumesDB = resumeService.getAll();
//        List<Resume> newResumes = resumesDB.stream()
//                .filter(r -> !resumesDbBefore.contains(r))
//                .collect(Collectors.toList());
//        assertEquals(0, (int) resumesTest.stream()
//                .filter(r -> !newResumes.contains(r))
//                .count());
    }
}

