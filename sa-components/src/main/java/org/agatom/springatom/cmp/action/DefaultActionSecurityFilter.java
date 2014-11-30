package org.agatom.springatom.cmp.action;

import org.agatom.springatom.cmp.action.model.Action;
import org.agatom.springatom.cmp.action.security.ActionSecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

/**
 * {@code DefaultActionSecurityFilter} is a default implementation of the {@link org.agatom.springatom.cmp.action.security.ActionSecurityFilter}
 * which is responsible for evaluating if principal has rights to visit particular place
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-14</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class DefaultActionSecurityFilter
        implements ActionSecurityFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultActionSecurityFilter.class);

    @Override
    public boolean isEnabled(final Action action, final Principal principal) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("isAuthenticated(action=%s, principal=%s)", action, principal));
        }
        final boolean isAuthenticated = this.isAuthenticated(principal);
        return true;
    }

    private boolean isAuthenticated(final Principal principal) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("isAuthenticated(principal={})", principal);
        }
        if (principal == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Principal is not authenticated, reason ==> principal==null");
            }
            return false;
        }
        return false;
    }
}
