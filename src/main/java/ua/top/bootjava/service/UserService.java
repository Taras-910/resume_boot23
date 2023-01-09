package ua.top.bootjava.service;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.top.bootjava.model.Role;
import ua.top.bootjava.model.User;
import ua.top.bootjava.repository.UserRepository;

import java.util.List;

import static ua.top.bootjava.util.InformUtil.must_not_null;
import static ua.top.bootjava.util.UsersUtil.prepareToSave;
import static ua.top.bootjava.util.validation.ValidationUtil.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @CacheEvict(value = "users", allEntries = true)
    public User create(@NotEmpty User user) {
        log.info("create {}", user);
        Assert.notNull(user, must_not_null);
        checkNew(user);
        return prepareAndSave(user, null);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public User get(int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Assert.notNull(email, must_not_null);
        return repository.getByEmail(email);
    }

    @Cacheable("users")
    public List<User> getAll() {
        log.info("getAll");
        return repository.findAll(SORT_NAME_EMAIL);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        Assert.notNull(user, must_not_null);
        User userDb = user.getId() == null ? null : repository.get(user.getId());
        checkNotFoundWithId(prepareAndSave(user, userDb), user.id());
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(user);  // !! need only for JDBC implementation
    }

    private User prepareAndSave(User user, User userDb) {
        return repository.save(prepareToSave(user, passwordEncoder, userDb));
    }

    public User asAdmin() {
        log.info("findAdmin");
        return  getAll().stream()
                .filter(u -> u.getRoles().contains(Role.ADMIN))
                .findFirst().get();
    }
}
