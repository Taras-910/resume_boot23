package ua.top.bootjava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import ua.top.bootjava.AbstractServiceTest;
import ua.top.bootjava.error.NotFoundException;
import ua.top.bootjava.model.Vote;
import ua.top.testData.VoteTestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.top.bootjava.util.DateTimeUtil.thisDay;
import static ua.top.testData.ResumeTestData.resume2_id;
import static ua.top.testData.UserTestData.*;
import static ua.top.testData.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    VoteService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() {
        Vote vote = service.get(vote1_id);
        vote_matcher.assertMatch(vote1, vote);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(not_found));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() {
        List<Vote> all = service.getAllForAuth();
        vote_matcher.assertMatch(allVotes(), all);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllForAuthUser() {
        List<Vote> all = service.getAllForAuth();
        vote_matcher.assertMatch(List.of(vote1), all);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() {
        service.delete(vote1_id);
        assertThrows(NotFoundException.class, () -> service.get(vote1_id));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(not_found));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(vote2_id));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() {
        service.update(vote1_id, resume2_id);
        Vote expected = service.get(vote1_id);
        vote_matcher.assertMatch(expected, VoteTestData.getUpdated());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateIllegalArgument() {
        assertThrows(NotFoundException.class, () -> service.update(not_found, resume2_id));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() {
        Vote created = service.create(resume2_id);
        int newId = created.id();
        Vote newVote = new Vote(null, thisDay, resume2_id, admin_id);
        newVote.setId(newId);
        vote_matcher.assertMatch(newVote, created);
        vote_matcher.assertMatch(newVote, service.get(newId));
    }
}
