package ua.top.bootjava.web.rest.profile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.top.bootjava.model.Vote;
import ua.top.bootjava.service.VoteService;

import java.util.List;

import static ua.top.bootjava.SecurityUtil.authId;

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class ProfileVoteRestController {
    static final String REST_URL = "/rest/profile/votes";
    private final VoteService service;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get by id {} for user {}", id, authId());
        return service.get(id);
    }

    @GetMapping
    public List<Vote> getAllForAuth() {
        log.info("get all for authUserId");
        return service.getAllForAuth();
    }

    @PostMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setVote(@PathVariable(name = "id") int resumeId, @RequestParam boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", resumeId);
        service.setVote(resumeId, enabled);
    }
}
