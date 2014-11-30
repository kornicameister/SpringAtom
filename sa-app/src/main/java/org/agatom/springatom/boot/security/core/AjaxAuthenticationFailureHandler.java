package org.agatom.springatom.boot.security.core;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface AjaxAuthenticationFailureHandler
        extends AjaxAuthenticationHandler, AuthenticationFailureHandler {
}
