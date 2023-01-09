package ua.top.bootjava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.AbstractServiceTest;
import ua.top.bootjava.error.IllegalRequestDataException;
import ua.top.bootjava.error.NotFoundException;
import ua.top.bootjava.model.Freshen;
import ua.top.testData.FreshenTestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.top.testData.FreshenTestData.*;
import static ua.top.testData.UserTestData.not_found;

class FreshenServiceTest extends AbstractServiceTest {

    @Autowired
    private FreshenService service;

    @Test
    void get() {
        Freshen freshen = service.get(freshen1_id);
        freshen_matcher.assertMatch(freshen, freshen1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(not_found));
    }

    @Test
    void getAll() {
        List<Freshen> all = service.getAll();
        freshen_matcher.assertMatch(all, freshen2, freshen1);
    }

    @Test
    @Transactional
    void delete() {
        service.delete(freshen1_id);
        assertThrows(NotFoundException.class, () -> service.get(freshen1_id));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(not_found));
    }

    @Test
    void create() {
        Freshen created = service.create(FreshenTestData.getNew());
        int newId = created.id();
        Freshen newFreshen = FreshenTestData.getNew();
        newFreshen.setId(newId);
        freshen_matcher.assertMatch(created, newFreshen);
        freshen_matcher.assertMatch(service.get(newId), newFreshen);
    }

    @Test
    void createInvalid() {
        Freshen invalid = new Freshen(freshen1);
        invalid.setWorkplace("n".repeat(101));
        validateRootCause(IllegalRequestDataException.class, () -> service.create(invalid));
    }

    @Test
    void update() {
        Freshen updated = FreshenTestData.getUpdated();
        service.update(updated, freshen1_id);
        freshen_matcher.assertMatch(service.get(freshen1_id), FreshenTestData.getUpdated());
    }

}
