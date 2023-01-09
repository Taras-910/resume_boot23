package ua.top.bootjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.top.bootjava.model.User;

import java.util.Optional;

import static ua.top.bootjava.util.validation.ValidationUtil.checkNotFound;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    default User getByEmail(String email) {
        return checkNotFound(findByEmailIgnoreCase(email).orElse(null), email);
    }
}
