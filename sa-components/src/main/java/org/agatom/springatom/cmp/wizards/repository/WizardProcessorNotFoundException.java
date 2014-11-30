package org.agatom.springatom.cmp.wizards.repository;

import org.agatom.springatom.data.services.exception.ServiceException;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class WizardProcessorNotFoundException
        extends ServiceException {
    private static final long serialVersionUID = -8070554291210834390L;

    public WizardProcessorNotFoundException(final String message) {
        super(message);
    }
}
