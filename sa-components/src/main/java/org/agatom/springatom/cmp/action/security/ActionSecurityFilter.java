package org.agatom.springatom.cmp.action.security;

import org.agatom.springatom.cmp.action.model.Action;

import java.security.Principal;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-14</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ActionSecurityFilter {
    boolean isEnabled(final Action action, final Principal principal);
}
