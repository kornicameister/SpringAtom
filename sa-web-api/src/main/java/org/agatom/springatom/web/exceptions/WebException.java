package org.agatom.springatom.web.exceptions;

import org.springframework.http.HttpStatus;

/**
 * {@code WebException} is an abstract subclass of all {@link java.lang.Exception} that
 * contain a {@link org.springframework.http.HttpStatus} within as a major
 * indicator of what went wrong during processing.
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-28</small>
 * </p>
 *
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class WebException
// TODO must extends normal exception
        extends ControllerTierException {
    private static final long       serialVersionUID = -4107749426296885326L;

    public WebException(final String message) {
        this(message, null);
    }

    public WebException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WebException(final Throwable cause) {
        this(null, cause);
    }

    // TODO javadoc
    public abstract HttpStatus getHttpStatus();
}
