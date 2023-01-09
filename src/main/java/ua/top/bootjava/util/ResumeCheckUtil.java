package ua.top.bootjava.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.top.bootjava.model.Resume;
import ua.top.bootjava.to.ResumeTo;

import java.util.List;

import static ua.top.bootjava.SecurityUtil.authId;
import static ua.top.bootjava.util.CommonUtil.isEmpty;
import static ua.top.bootjava.util.InformUtil.*;
import static ua.top.bootjava.util.UsersUtil.asAdmin;

public class ResumeCheckUtil {
    public static Logger log = LoggerFactory.getLogger(ResumeCheckUtil.class);

    public static boolean isNotSimilar(Resume r, ResumeTo rTo) {
        return !r.getTitle().equals(rTo.getTitle()) ||
                !r.getName().equals(rTo.getName());
    }


    public static void isNullPointerException(ResumeTo resumeTo) {
        if (!checkNullDataResumeTo(resumeTo)) {
            throw new NullPointerException(must_not_null + resumeTo);
        }
    }

    public static boolean checkNullDataResumeTo(ResumeTo v) {
        String[] line = {v.getTitle(), v.getName(), v.getAddress(), v.getSkills(), v.getUrl(), v.getWorkBefore()};
        for (String text : line) {
            if (isEmpty(text)) {
                log.error(check_error_data, v);
                return false;
            }
        }
        return true;
    }

    public static void checkExistResumeForUser(List<Resume> resumes) {
        if (resumes.size() != 0 && authId() != asAdmin().getId()) {
            throw new IllegalArgumentException(resume_exist_already);
        }
    }

    public static void checkNotOwnUpdate(int userId) {
        if (userId != authId()) {
            throw new IllegalArgumentException(not_own_data);
        }
    }

    public static void checkNotOwnDelete(int userId) {
        if (userId != authId() || userId != asAdmin().getId()) {
            throw new IllegalArgumentException(not_own_data);
        }
    }
}
