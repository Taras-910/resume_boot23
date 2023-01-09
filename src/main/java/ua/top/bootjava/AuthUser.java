package ua.top.bootjava;

import lombok.Getter;
import org.springframework.lang.NonNull;
import ua.top.bootjava.model.User;

public class AuthUser extends org.springframework.security.core.userdetails.User {

    @Getter
    private User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }

    public int getId() {
        return user.id();
    }

    public void update(User newUser) {
        user = newUser;
    }

    @Override
    public String toString() {
        return "AuthUser:" + user.getId() + '[' + user.getEmail() + ']';
    }
}
