package ua.top.bootjava.web.rest.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.top.bootjava.model.Vote;
import ua.top.bootjava.repository.VoteRepository;
import ua.top.bootjava.service.VoteService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteRestController {
    static final String REST_URL = "/rest/admin/votes";
    private final VoteService service;
    private final VoteRepository repository;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll votes");
        return service.getAll();
    }

    @GetMapping("/auth")
    public List<Vote> getAllForAuthUser() {
        log.info("get all for authUserId");
        return service.getAllForAuth();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@RequestParam int resumeId) {
        log.info("create vote for employerId {}", resumeId);
        Vote created = service.create(resumeId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "id") int voteId, @RequestParam int resumeId) {
        log.info("update voteId {} for resumeId {}", voteId, resumeId);
        service.update(voteId, resumeId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.info("delete vote {}", id);
        service.delete(id);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setVote(@PathVariable(name = "id") int resumeId, @RequestParam boolean toVote) {
        log.info(toVote ? "enable {}" : "disable {}", resumeId);
        service.setVote(resumeId, toVote);
    }
}
