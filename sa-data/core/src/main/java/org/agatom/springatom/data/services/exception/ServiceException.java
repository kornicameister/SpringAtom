package org.agatom.springatom.data.services.exception;

import com.google.common.base.Throwables;
import org.joda.time.DateTime;
import org.springframework.util.ClassUtils;
import sun.reflect.Reflection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class ServiceException
        extends Exception {
    private static final long     serialVersionUID = -305935297735529733L;
    private              Class<?> origin           = null;
    private              DateTime timestamp        = null;

    public ServiceException(final String message) {
        super(message);
        this.init();
    }

    private void init() {
        this.origin = ClassUtils.getUserClass(Reflection.getCallerClass());
        this.timestamp = DateTime.now();
    }

    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
        this.init();
    }

    public ServiceException(final Throwable cause) {
        super(cause);
        this.init();
    }

    public ServiceException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.init();
    }

    public final Class<?> getOrigin() {
        return this.origin;
    }

    public final DateTime getTimestamp() {
        return this.timestamp.toDateTime();
    }

    public final Throwable getRootCause() {
        return Throwables.getRootCause(this);
    }
}
