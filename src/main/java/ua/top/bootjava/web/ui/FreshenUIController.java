package ua.top.bootjava.web.ui;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.service.FreshenService;

import static ua.top.bootjava.util.UsersUtil.asAdmin;

//@ApiIgnore
@Schema(hidden = true)
@RestController
@RequestMapping(value = "/profile/freshen", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class FreshenUIController {

    private FreshenService service;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refreshDB(@Valid Freshen freshen) {
        log.info("refreshDB freshen {}", freshen);

        freshen.setUserId(asAdmin().getId());
//        service.refreshDB(asNewFreshen(freshen));
    }
}
