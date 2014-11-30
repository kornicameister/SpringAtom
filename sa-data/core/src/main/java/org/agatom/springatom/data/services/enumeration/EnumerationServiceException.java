package org.agatom.springatom.data.services.enumeration;


import org.agatom.springatom.data.services.exception.ServiceException;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class EnumerationServiceException
        extends ServiceException {
    private static final long serialVersionUID = 133681215179736030L;

    public EnumerationServiceException(final Throwable exp) {
        super(exp);
    }

    public EnumerationServiceException(final String message) {
        super(message);
    }

    public EnumerationServiceException(final String message, final Throwable root) {
        super(message, root);
    }

    public static EnumerationServiceException enumerationNotFound(final String key) {
        return new EnumerationServiceException(String.format("Enumeration for key=%s not found", key));
    }

    public static EnumerationServiceException enumerationEntryNotFound(final String key, final String enumerationKey) {
        return new EnumerationServiceException(String.format("Enumeration(key=%s) contains no entry of %s", key, enumerationKey));
    }
}
