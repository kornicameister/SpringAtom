package org.agatom.springatom.data.types.error;

import org.agatom.springatom.data.types.PersistentBean;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-27</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Error
        extends PersistentBean<Long> {
    Throwable getError();

    String getMessage();

    StackTraceElement[] getStackTrace();

    Throwable getCause();
}
