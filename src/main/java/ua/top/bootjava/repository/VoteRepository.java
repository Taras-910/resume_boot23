package ua.top.bootjava.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.userId=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.resumeId=:resumeId AND v.userId=:userId")
    int deleteByResumeId(@Param("resumeId") int resumeId, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.resumeId=:resumeId")
    int deleteListByResumeId(@Param("resumeId") int resumeId);

    @Query("SELECT v FROM Vote v WHERE v.userId=:userId")
    List<Vote> getAllForAuth(@Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.localDate<:reasonPeriodToKeep")
    List<Vote> getOutDated(@Param("reasonPeriodToKeep") LocalDate reasonPeriodToKeep);


    @Transactional
    default boolean deleteAllByResumeId(int vacancyId) {
        try {
            return Optional.of(deleteListByResumeId(vacancyId) != 0).orElse(false);
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

}
