package ua.top.bootjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.model.Resume;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface ResumeRepository extends BaseRepository<Resume> {

    @Query("SELECT r FROM Resume r WHERE r.title=:title AND r.name=:name " +
            "AND LOWER(r.workBefore) LIKE CONCAT(:workBefore, '%')")
    Resume getByParams(@Param("title") String title, @Param("name") String name, @Param("workBefore") String workBefore);

    @Query("SELECT r FROM Resume r WHERE r.freshen.userId=:userId")
    List<Resume> getByUserId(@Param("userId") Integer userId);

    @Query("SELECT r FROM Resume r WHERE r.releaseDate<:reasonPeriodToKeep")
    List<Resume> getOutDated(@Param("reasonPeriodToKeep") LocalDate reasonPeriodToKeep);
}

