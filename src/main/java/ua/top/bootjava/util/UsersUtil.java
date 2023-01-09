package ua.top.bootjava.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.top.bootjava.model.Role;
import ua.top.bootjava.model.User;
import ua.top.bootjava.to.UserTo;

import java.util.Collections;

import static ua.top.bootjava.config.SecurityConfiguration.PASSWORD_ENCODER;
import static ua.top.bootjava.model.Role.USER;

@UtilityClass
public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder, User userDb) {
        if (user.getRoles() == null) {
            user.setRoles(Collections.singleton(Role.USER));
        }
        String passwordDb = userDb == null ? null : userDb.getPassword();
        String password = user.getPassword();
        if (passwordDb == null) {
            user.setPassword(passwordEncoder.encode(password));
        } else {
            if (passwordDb.equals(password)) {
                user.setPassword(passwordDb);
            } else {
                user.setPassword(passwordEncoder.encode(password));
            }
        }
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static User asAdmin() {
        return new User(100000, "Admin", "admin@gmail.com", "admin", Role.ADMIN);
    }

}
