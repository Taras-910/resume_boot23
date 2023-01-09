package ua.top.bootjava.web.rest.profile;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.service.ResumeService;
import ua.top.bootjava.to.ResumeTo;

import java.util.List;

import static ua.top.bootjava.util.FreshenUtil.asNewFreshen;

@RestController
@RequestMapping(value = ProfileResumeRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class ProfileResumeRestController {
    static final String REST_URL = "/rest/profile/resumes";
    private ResumeService resumeService;

    @GetMapping("/{id}")
    public ResumeTo get(@PathVariable int id) {
        return resumeService.getTo(id);
    }

    @GetMapping
    public List<ResumeTo> getAll() {
        return resumeService.getAllTos();
    }

    @Transactional
    @GetMapping(value = "/filter")
    public List<ResumeTo> getByFilter(@Valid Freshen freshen) {
        log.info("getByFilter freshen={}", freshen);
        return resumeService.getTosByFilter(asNewFreshen(freshen));
    }
}
