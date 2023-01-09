package ua.top.bootjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.model.Resume;
import ua.top.bootjava.repository.ResumeRepository;
import ua.top.bootjava.to.ResumeTo;
import ua.top.bootjava.util.ResumeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ua.top.bootjava.SecurityUtil.authId;
import static ua.top.bootjava.aggregator.Installation.limitResumesKeeping;
import static ua.top.bootjava.aggregator.Installation.reasonPeriodKeeping;
import static ua.top.bootjava.model.Goal.FILTER;
import static ua.top.bootjava.util.FreshenUtil.getFreshenFromTo;
import static ua.top.bootjava.util.InformUtil.exist_end_replace;
import static ua.top.bootjava.util.InformUtil.update_error_and_redirect;
import static ua.top.bootjava.util.ResumeCheckUtil.*;
import static ua.top.bootjava.util.ResumeUtil.*;
import static ua.top.bootjava.util.validation.ValidationUtil.checkNotFoundWithId;

@Slf4j
@Service
@AllArgsConstructor
public class ResumeService {
    private final ResumeRepository repository;
    private final VoteService voteService;
    private final FreshenService freshenService;
    private static final Sort SORT_DATE_NAME = Sort.by(Sort.Direction.DESC, "releaseDate", "id");

    public Resume get(int id) {
        log.info("get id {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public ResumeTo getTo(int id) {
        log.info("getTo resume {}", id);
        return ResumeUtil.getTo(repository.get(id), voteService.getAllForAuth());
    }

    public List<Resume> getAll() {
        log.info("getAll");
        return repository.findAll(SORT_DATE_NAME);
    }

    public List<ResumeTo> getAllTos() {
        log.info("getAllTos for user {}", authId());
        return ResumeUtil.getTos(getAll(), voteService.getAllForAuth());
    }

    @Transactional
    public List<ResumeTo> getTosByFilter(Freshen f) {
        log.info("getTosByFilter language={} level={} workplace={}", f.getLanguage(), f.getLevel(), f.getWorkplace());
        f.setGoals(Collections.singleton(FILTER));
        freshenService.create(f);
        return getTos(getFilter(repository.findAll(), f), voteService.getAllForAuth());
    }

    public List<Resume> getByUserId(int userId) {
        return Optional.of(repository.getByUserId(userId)).orElse(new ArrayList<>());
    }

    @Transactional
    public Resume updateTo(ResumeTo resumeTo) {
        log.info("update resumeTo {}", resumeTo);
//        checkValidUrl(resumeTo.getUrl());
        Resume resumeDb = repository.get(resumeTo.id());
        Resume resume = fromToForUpdate(resumeTo, resumeDb);
        checkNotOwnUpdate(resumeDb.getFreshen().getUserId());
        if (isNotSimilar(resumeDb, resumeTo)) {
            voteService.deleteListByResumeId(resumeTo.id());
        }
        return repository.save(resume);
    }

    @Transactional
    public Resume create(ResumeTo resumeTo) {
        log.info("create resumeTo={}", resumeTo);
        isNullPointerException(resumeTo);
        checkExistResumeForUser(repository.getByUserId(authId()));
//        checkValidUrl(resumeTo.getUrl());
        Freshen newFreshen = freshenService.create(getFreshenFromTo(resumeTo));
        Resume resume = new Resume(fromTo(resumeTo));
        resume.setFreshen(newFreshen);
        return save(resume);
    }

    @Transactional
    public List<Resume> createUpdateList(List<Resume> resumes) {
        log.info("createUpdateList {}", resumes);
        List<Resume> resumesDb = new ArrayList<>();
        try {
            resumesDb = repository.saveAll(resumes);
        } catch (Exception e) {
            for (Resume r : resumes) {
                log.error(update_error_and_redirect, r);
                resumesDb.add(save(r));
            }
        }
        return resumesDb;
    }

    @Transactional
    public Resume save(Resume resume) {
        Resume resumeDouble = null;
        try {
            resumeDouble = repository.getByParams(resume.getTitle(), resume.getName(), resume.getWorkBefore());
        } catch (Exception ignored) {
        }
        if (resumeDouble != null && (resume.isNew() || resumeDouble.getId() != resume.getId())) {
            delete(resumeDouble.getId());
            log.error(exist_end_replace, resumeDouble, resume);
        }
        return repository.save(resume);
    }

    @Transactional
    public void delete(int id) {
        log.info("delete {}", id);
        int userId = checkNotFoundWithId(repository.get(id), id).getFreshen().getUserId();
        checkNotOwnDelete(userId);
        repository.deleteById(id);
    }

    @Transactional
    public void deleteOutDated() {
        log.info("deleteOutDated reasonPeriodKeeping {}", reasonPeriodKeeping);
        repository.deleteAll(repository.getOutDated(reasonPeriodKeeping));
    }

    @Transactional
    public void deleteExceedLimitDb() {
        log.info("deleteExceedLimitDb exceed {}", limitResumesKeeping);

        List<Resume> exceed = repository.findAll().stream()
                .skip(limitResumesKeeping)
                .collect(Collectors.toList());
        repository.deleteAll(exceed);
//        freshenService.deleteExceed();
//        voteService.deleteExceed();
    }
}

