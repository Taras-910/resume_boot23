package ua.top.bootjava;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.top.bootjava.model.User;

import static java.util.Objects.requireNonNull;
import static ua.top.bootjava.util.InformUtil.no_authorized_user_found;

@UtilityClass
public class SecurityUtil {
    public static AuthUser authTest = null;

    public static void setTestAuthUser(User user) {
        authTest = new AuthUser(user);
    }

    public static AuthUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthUser) ? (AuthUser) principal : null;
    }

    public static AuthUser get() {
        AuthUser authPrincipal = safeGet();
        AuthUser authUser = authPrincipal == null ? authTest : authPrincipal;
        return requireNonNull(authUser, no_authorized_user_found);
    }

    public static User authUser() {
        return get().getUser();
    }

    public static int authId() {
        return get().getUser().id();
    }
}
