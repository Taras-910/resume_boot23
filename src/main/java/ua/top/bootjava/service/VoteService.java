package ua.top.bootjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.model.Vote;
import ua.top.bootjava.repository.UserRepository;
import ua.top.bootjava.repository.VoteRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ua.top.bootjava.SecurityUtil.authId;
import static ua.top.bootjava.aggregator.Installation.limitVoteKeeping;
import static ua.top.bootjava.aggregator.Installation.reasonPeriodKeeping;
import static ua.top.bootjava.util.DateTimeUtil.thisDay;
import static ua.top.bootjava.util.validation.ValidationUtil.checkNotFoundData;
import static ua.top.bootjava.util.validation.ValidationUtil.checkNotFoundWithId;

@Slf4j
@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository repository;
    private final UserRepository userRepository;

    public Vote get(int id) {
        int userId = authId();
        log.info("get by id {} for user {}", id, userId);
        return checkNotFoundWithId(get(id, userId), id);
    }

    public List<Vote> getAll() {
        log.info("getAll votes");
        return repository.findAll();
    }

    public List<Vote> getAllForAuth() {
        log.info("get all for User {}", authId());
        return repository.getAllForAuth(authId());
    }

    public Vote create(int resumeId) {
        log.info("create vote for resumeId {}", resumeId);
        Vote vote = new Vote(null, thisDay, resumeId, authId());
        return save(vote);
    }

    public void update(int voteId, int resumeId) {
        log.info("update vote {} for resumeId {} of user {}", voteId, resumeId, authId());
        Vote vote = new Vote(voteId, thisDay, resumeId, authId());
        checkNotFoundWithId(save(vote), voteId);
    }

    @Transactional
    public Vote save(Vote vote) {
        vote.setUserId(userRepository.getOne(authId()).getId());
        return vote.isNew() || get(vote.id()) != null ? repository.save(vote) : null;
    }

    public Vote get(int id, int userId) {
        Vote vote = repository.findById(id).orElse(null);
        return vote != null && vote.getUserId() == userId ? vote : null;
    }

    @Transactional
    public void delete(int id) {
        log.info("delete vote {} for userId {}", id, authId());
        checkNotFoundWithId(repository.delete(id, authId()) != 0, id);
    }

    @Transactional
    public void deleteListByResumeId(int resumeId) {
        checkNotFoundData(repository.deleteAllByResumeId(resumeId), resumeId);
    }

    @Transactional
    public void setVote(int resumeId, boolean toVote) {
        log.info(toVote ? "enable {}" : "disable {}", resumeId);
        if (!toVote) {
            log.info("deleteByResumeId {}", resumeId);
            checkNotFoundWithId(repository.deleteByResumeId(resumeId, authId()), resumeId);
        } else {
            log.info("create vote for resumeId {} userId {}", resumeId, authId());
            create(resumeId);
        }
    }

    @Transactional
    public void deleteOutDated() {
        log.info("deleteOutDated reasonPeriodToKeep {}", reasonPeriodKeeping);
        repository.deleteAll(repository.getOutDated(reasonPeriodKeeping));
    }

    @Transactional
    public void deleteExceed() {
        log.info("deleteExceedLimit limitVoteKeeping {}", limitVoteKeeping);
        List<Vote> exceedLimit = getAll().stream()
                .skip(limitVoteKeeping)
                .collect(Collectors.toList());
        repository.deleteAll(exceedLimit);
    }
}
