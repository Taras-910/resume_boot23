package ua.top.bootjava.service;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import ua.top.bootjava.AbstractServiceTest;
import ua.top.bootjava.error.NotFoundException;
import ua.top.bootjava.model.Rate;

import java.util.List;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.top.testData.RateTestData.*;
import static ua.top.testData.UserTestData.ADMIN_MAIL;
import static ua.top.testData.UserTestData.not_found;

@WithUserDetails(value = ADMIN_MAIL)
class RateServiceTest extends AbstractServiceTest {
    @Autowired
    RateService service;

    @Test
    void get() {
        Rate rate = service.get(RATE1_ID);
        rate_matcher.assertMatch(rate1, rate);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(not_found));
    }

    @Test
    void getAll() {
        List<Rate> all = service.getAll();
        rate_matcher.assertMatch(allRates(), all);
    }

    @Test
    void create() {
        Rate testRate = new Rate(getNew());
        Rate createdRate = service.create(testRate);
        int newId = createdRate.id();
        testRate.setId(newId);
        rate_matcher.assertMatch(testRate, createdRate);
    }

    @Test
    void delete() {
        service.delete(RATE1_ID);
        assertThrows(NotFoundException.class, () -> service.get(RATE1_ID));
    }

    @Test
    void deleteInvalid() {
        assertThrows(NotFoundException.class, () -> service.delete(not_found));
    }

    @Test
    void deleteAll() {
        service.deleteAll();
        List<Rate> all = service.getAll();
        assertEquals(0, all.size());
    }

    @Test
    void getByName() {
        Rate rate = service.getByName(rate10.getName());
        rate_matcher.assertMatch(rate, rate10);
    }

    @Test
    void getByNameInvalid() {
        assertThrows(NotFoundException.class, () -> service.getByName("NameInvalid"));
    }

    @Test
    void createInvalid(){
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Rate(null,"   ",0.897, now())));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Rate(null,null,0.897, now())));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Rate(null,"USDGBP",null, now())));
    }
}
