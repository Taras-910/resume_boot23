package ua.top.bootjava.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ua.top.bootjava.model.User;
import ua.top.bootjava.repository.UserRepository;
import ua.top.bootjava.service.UserService;
import ua.top.bootjava.to.UserTo;
import ua.top.bootjava.util.UsersUtil;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static ua.top.bootjava.util.validation.ValidationUtil.checkNew;

public abstract class AbstractUserController {

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected UserRepository repository;
    @Autowired
    protected UserService service;
    @Autowired
    private UserValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(Optional.of(repository.getExisted(id)));
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    public User create(User user) {
        log.info("user {}", user);
        return service.create(user);
    }

    public User create(UserTo userTo) {
        log.info("create {}", userTo);
        checkNew(userTo);
        return service.create(UsersUtil.createNewFromTo(userTo));
    }

    protected User prepareAndSave(User user) {
        return repository.save(UsersUtil.prepareToSave(user));
    }
}
