/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.web.controller.auth;

import org.agatom.springatom.web.controller.SDefaultController;
import org.agatom.springatom.web.support.beans.login.SLoginCommandBean;
import org.agatom.springatom.web.support.beans.login.SLoginResultBean;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;


@Controller(value = SAuthController.CONTROLLER_NAME)
@RequestMapping(value = "/app/auth")
public class SAuthController
        extends SDefaultController {
    protected static final String CONTROLLER_NAME = "AuthorizationController";
    private static final   Logger LOGGER          = Logger.getLogger(SAuthController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "springatom.tiles.auth.login";
    }

    @RequestMapping(value = "/failed", method = RequestMethod.GET)
    public String getLoginFailedPage() {
        return "springatom.tiles.auth.login.failed";
    }

    @RequestMapping(value = "/access/denied")
    public String getAccessDeniedPage() {
        return "springatom.tiles.auth.access.denied";
    }

    @RequestMapping(value = "/forgotten/password", method = RequestMethod.GET)
    public String getForgottenPasswordPage() {
        return "springatom.tiles.auth.forgotten.password";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "springatom.tiles.auth.register";
    }

    //SpringAtom-#38 Ajax backend authorization service
    /*
    Method below is a initiated part of SpringAtom-#38 but was not completed, left here for
    the record of started job.
     */
    @ResponseBody
    @RequestMapping(
            value = "/login.ajax.do",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Callable<SLoginResultBean> performLogin(
            @RequestBody final SLoginCommandBean user,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
//        LOGGER.trace(String.format("Login call for=%s", user.getUsername()));
//        return new Callable<SLoginResultBean>() {
//            @Override
//            public SLoginResultBean call() throws Exception {
//                final Authentication authenticationToken =
//                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//                final Locale locale = LocaleContextHolder.getLocale();
//                final SecurityContext context = SecurityContextHolder.getContext();
//
//                try {
//
//                    final Authentication authentication = authenticationManager.authenticate(authenticationToken);
//                    context.setAuthentication(authentication);
//                    rememberMeServices.loginSuccess(request, response, authentication);
//
//                    return new SLoginResultBean()
//                            .setUsername(authentication.getName())
//                            .setMessage(
//                                    messageSource.getMessage(
//                                            "sa.msg.login.success",
//                                            new Object[]{user.getUsername()},
//                                            locale
//                                    )
//                            )
//                            .success();
//                } catch (AuthenticationException ex) {
//                    LOGGER.error("Failure during authorization", ex);
//                    return new SLoginResultBean()
//                            .setMessage(
//                                    messageSource.getMessage(
//                                            "sa.msg.login.failure",
//                                            new Object[]{ex.getLocalizedMessage()},
//                                            locale
//                                    )
//                            )
//                            .failure();
//                }
//
//            }
//        };
        return null;
    }

    @Override
    public String getControllerName() {
        return CONTROLLER_NAME;
    }

}