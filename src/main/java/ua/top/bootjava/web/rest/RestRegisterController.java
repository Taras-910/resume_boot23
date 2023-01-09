package ua.top.bootjava.web.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.top.bootjava.model.User;
import ua.top.bootjava.to.UserTo;
import ua.top.bootjava.util.UsersUtil;
import ua.top.bootjava.web.AbstractUserController;

import java.net.URI;

import static ua.top.bootjava.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestRegisterController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestRegisterController extends AbstractUserController {
    static final String REST_URL = "/rest/register";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = prepareAndSave(UsersUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
