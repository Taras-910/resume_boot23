package ua.top.bootjava.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.model.Freshen;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface FreshenRepository extends BaseRepository<Freshen>{

    @Transactional
    @Modifying
    @Query("DELETE FROM Freshen f WHERE f.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT f FROM Freshen f WHERE f.recordedDate<=:finish AND f.recordedDate>=:start")
    List<Freshen> getBetween(@Param("finish") LocalDateTime finish, @Param("start") LocalDateTime start);

    @Query("SELECT f FROM Freshen f WHERE f.recordedDate<:reasonLocalDateTime")
    List<Freshen> getOutDated(@Param("reasonLocalDateTime") LocalDateTime reasonLocalDateTime);

    @Query("SELECT f FROM Freshen f ORDER BY f.recordedDate DESC")
    List<Freshen> getAll();
}
