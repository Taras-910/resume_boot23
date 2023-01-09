package ua.top.bootjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.model.Rate;

import java.util.List;

@Transactional(readOnly = true)
public interface RateRepository extends BaseRepository<Rate> {

    @Query("SELECT e FROM Rate e ORDER BY e.name ASC")
    List<Rate> getAll();

    @Query("SELECT e FROM Rate e WHERE e.name=:name")
    Rate getByName(@Param("name") String name);
}
