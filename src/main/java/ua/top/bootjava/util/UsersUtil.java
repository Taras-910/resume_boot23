package ua.top.bootjava.util;

import lombok.experimental.UtilityClass;
import ua.top.bootjava.model.User;
import ua.top.bootjava.to.UserTo;

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
}
