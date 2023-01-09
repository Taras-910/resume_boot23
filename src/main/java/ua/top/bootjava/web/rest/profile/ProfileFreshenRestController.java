package ua.top.bootjava.web.rest.profile;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.model.Goal;
import ua.top.bootjava.service.FreshenService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ua.top.bootjava.SecurityUtil.authId;
import static ua.top.bootjava.util.UsersUtil.asAdmin;

@RestController
@RequestMapping(value = ProfileFreshenRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class ProfileFreshenRestController {
    static final String REST_URL = "/rest/profile/freshen";

    private FreshenService service;

    @GetMapping
    public List<Freshen> getAllOwn() {
        return service.getAll().stream().filter(f -> authId() == f.getUserId()).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Freshen get(@PathVariable int id) {
        return getAllOwn().stream().filter(f -> f.getId() == id).findFirst().orElse(null);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refreshDB(@Valid @RequestBody Freshen freshen) {
        log.info("refreshDB freshen {}", freshen);
        freshen.setUserId(asAdmin().id());
        freshen.setGoals(Collections.singleton(Goal.UPGRADE));
//        service.refreshDB(asNewFreshen(freshen));
    }

}
