package ua.top.bootjava.web.rest.admin;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.top.bootjava.model.Rate;
import ua.top.bootjava.service.RateService;

import java.net.URI;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = RateRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RateRestController {
    static final String REST_URL = "/rest/admin/rates";
    private final RateService service;

    @GetMapping("/{id}")
    public Rate get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping
    public List<Rate> getAll() {
        return service.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rate> create(@RequestBody @Valid Rate rate) {
        Rate created = service.create(rate);
        ResponseEntity<Rate> entity;
        try {
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            entity = ResponseEntity.created(uriOfNewResource).body(created);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("data error");
        }
        return entity;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
