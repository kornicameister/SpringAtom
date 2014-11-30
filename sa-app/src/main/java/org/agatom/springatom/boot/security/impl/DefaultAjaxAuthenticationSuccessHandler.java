package org.agatom.springatom.boot.security.impl;

import org.agatom.springatom.boot.security.core.AjaxAuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security success handler, specialized for Ajax requests.
 */
@Component
class DefaultAjaxAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler
        implements AjaxAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
