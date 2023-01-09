package ua.top.testData;


import ua.top.bootjava.TestUtil;
import ua.top.bootjava.model.Role;
import ua.top.bootjava.model.User;
import ua.top.bootjava.util.JsonUtil;

import java.util.Collections;
import java.util.Date;

import static ua.top.bootjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final TestUtil.Matcher<User> user_matcher = TestUtil.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int admin_id = START_SEQ;
    public static final int user_id = START_SEQ + 1;
    public static final int not_found = 10;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User admin = new User(admin_id, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User user = new User(user_id, "User", USER_MAIL, "password", Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
