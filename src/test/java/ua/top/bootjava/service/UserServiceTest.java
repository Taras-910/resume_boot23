package ua.top.bootjava.service;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ua.top.bootjava.AbstractServiceTest;
import ua.top.bootjava.error.NotFoundException;
import ua.top.bootjava.model.Role;
import ua.top.bootjava.model.User;
import ua.top.testData.UserTestData;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ua.top.testData.UserTestData.*;

class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        user_matcher.assertMatch(created, newUser);
        user_matcher.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }

    @Test
    void delete() {
        service.delete(user_id);
        assertThrows(NotFoundException.class, () -> service.get(user_id));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(not_found));
    }

    @Test
    void get() {
        User user = service.get(user_id);
        user_matcher.assertMatch(user, UserTestData.user);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(not_found));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        user_matcher.assertMatch(user, admin);
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated, user_id);
        user_matcher.assertMatch(service.get(user_id), getUpdated());
    }

    @Test
    void getAll() {
        List<User> all = service.getAll();
        user_matcher.assertMatch(all, admin, user);
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "  ", "password",  Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "mail@yandex.ru", "password", true, null, Set.of())));
    }

    @Test
    void enable() {
        service.enable(user_id, false);
        assertFalse(service.get(user_id).isEnabled());
        service.enable(user_id, true);
        assertTrue(service.get(user_id).isEnabled());
    }

}
