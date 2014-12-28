package org.agatom.springatom.web.exceptions.web;

import org.agatom.springatom.web.exceptions.WebException;
import org.springframework.http.HttpStatus;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-28</small>
 * </p>
 *
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */
final public class CTNotFoundException
        extends WebException {
    private static final long serialVersionUID = 5022753847835360618L;

    public CTNotFoundException(final String message) {
        super(message);
    }

    public CTNotFoundException(final Throwable cause) {
        super(cause);
    }

    public CTNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
