package ua.top.bootjava.web.rest.admin;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.model.Resume;
import ua.top.bootjava.service.ResumeService;
import ua.top.bootjava.to.ResumeTo;

import java.net.URI;
import java.util.List;

import static ua.top.bootjava.util.FreshenUtil.asNewFreshen;


@RestController
@RequestMapping(value = ResumeRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class ResumeRestController {
    static final String REST_URL = "/rest/admin/resumes";

    private ResumeService resumeService;

    @GetMapping("/{id}")
    public ResumeTo get(@PathVariable int id) {
        return resumeService.getTo(id);
    }

    @GetMapping
    public List<ResumeTo> getAll() {
        return resumeService.getAllTos();
    }

    @GetMapping("/all")
    public List<Resume> getResumes() {
        return resumeService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        resumeService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid ResumeTo resumeTo) {
        resumeService.updateTo(resumeTo);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resume> createResume(@RequestBody @Valid ResumeTo resumeTo) {
        Resume created = resumeService.create(resumeTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/filter")
    public List<ResumeTo> getByFilter(@Valid Freshen freshen) {
        log.info("getByFilter freshen={}", freshen);
        return resumeService.getTosByFilter(asNewFreshen(freshen));
    }
}
