package org.agatom.springatom.boot.security.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Returns a 401 error code (Unauthorized) to the client.
 */
@Component
class Http401UnauthorizedEntryPoint
        extends LoginUrlAuthenticationEntryPoint {
    private final Logger LOGGER = LoggerFactory.getLogger(Http401UnauthorizedEntryPoint.class);

    public Http401UnauthorizedEntryPoint() {
        super("/app/authentication/exec");
    }

    /**
     * Always returns a 401 error code to the client.
     */
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException,
            ServletException {
        super.commence(request, response, authException);
        LOGGER.debug("Pre-authenticated entry point called. Rejecting access");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
    }
}
