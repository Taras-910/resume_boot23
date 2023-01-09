package ua.top.bootjava.util.validation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ua.top.bootjava.HasId;
import ua.top.bootjava.error.ErrorType;
import ua.top.bootjava.error.IllegalRequestDataException;
import ua.top.bootjava.error.NotFoundException;

@UtilityClass
public class ValidationUtil {
    public static final Logger log = LoggerFactory.getLogger(ValidationUtil.class);
    public static final String not_found = "Not found entity with {}";
    public static final String error_request_url = "{} at request {} {}";

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new NotFoundException("Entity with id=" + id + " not found");
        }
    }

    public static <T> T checkExisted(T obj, int id) {
        if (obj == null) {
            throw new NotFoundException("Entity with id=" + id + " not found");
        }
        return obj;
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }

    public static Throwable logAndGetRootCause(Logger log, HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(error_request_url, errorType, req.getRequestURL(), rootCause);
        } else {
            log.warn(error_request_url, errorType, req.getRequestURL(), rootCause.toString());
        }
        return rootCause;
    }

    public static void checkNotFoundData(boolean found, Object id) {
        if (!found) {
            log.error(not_found, id);
        }
    }

    public static String getMessage(String uri, String value, MessageSourceAccessor messageSourceAccessor) {
        return uri.contains("/rest") ? value : messageSourceAccessor.getMessage(value);
    }

}
