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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller(value = SAuthController.CONTROLLER_NAME)
@RequestMapping(value = "/app/auth")
public class SAuthController
        extends SDefaultController {
    protected static final String CONTROLLER_NAME = "AuthorizationController";

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

    @Override
    public String getControllerName() {
        return CONTROLLER_NAME;
    }

}