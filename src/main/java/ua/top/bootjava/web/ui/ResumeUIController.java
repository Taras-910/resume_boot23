package ua.top.bootjava.web.ui;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.service.ResumeService;
import ua.top.bootjava.service.VoteService;
import ua.top.bootjava.to.ResumeTo;

import java.util.List;

import static ua.top.bootjava.util.FreshenUtil.asNewFreshen;

//@ApiIgnore
@Schema(hidden = true)
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "profile/resumes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResumeUIController {
    private ResumeService resumeService;
    private VoteService voteService;

    @GetMapping("/{id}")
    public ResumeTo get(@PathVariable int id) {
        return resumeService.getTo(id);
    }

    @GetMapping
    public List<ResumeTo> getAll() {
        return resumeService.getAllTos();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        resumeService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Valid ResumeTo resumeTo) {
        log.info("createOrUpdate resumeTo={}", resumeTo);
        if (resumeTo.isNew()) {
            resumeService.create(resumeTo);
        } else {
            resumeService.updateTo(resumeTo);
        }
    }

    @Transactional
    @GetMapping(value = "/filter")
    public List<ResumeTo> getByFilter(@Valid Freshen freshen) {
        log.info("getByFilter language={} level={} workplace={}", freshen.getLanguage(), freshen.getLevel(), freshen.getWorkplace());
        return resumeService.getTosByFilter(asNewFreshen(freshen));
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setVote(@PathVariable(name = "id") int vacancyId, @RequestParam boolean enabled) {
        voteService.setVote(vacancyId, enabled);
    }
}
