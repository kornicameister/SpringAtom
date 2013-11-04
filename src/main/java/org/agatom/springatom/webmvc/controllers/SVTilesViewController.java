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

package org.agatom.springatom.webmvc.controllers;

import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller(value = SVTilesViewController.CONTROLLER_NAME)
@RequestMapping(value = "/app")
public class SVTilesViewController
        extends SVDefaultController {
    public static final String CONTROLLER_NAME = "sa.controller.view.TilesController";

    public SVTilesViewController() {
        super(CONTROLLER_NAME);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage() {
        return "springatom.tiles.index";
    }

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String getFreeReportsPage() {
        return "springatom.tiles.reports";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String getAboutPage() {
        return "springatom.tiles.about";
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "springatom.tiles.auth.login";
    }

    @RequestMapping(value = "/auth/failed", method = RequestMethod.GET)
    public String getLoginFailedPage() {
        return "springatom.tiles.auth.login.failed";
    }

    @RequestMapping(value = "/auth/access/denied")
    public String getAccessDeniedPage() {
        return "springatom.tiles.auth.access.denied";
    }

    @RequestMapping(value = "/auth/forgotten/password", method = RequestMethod.GET)
    public String getForgottenPasswordPage() {
        return "springatom.tiles.auth.forgotten.password";
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "springatom.tiles.auth.register";
    }

    @RequestMapping(value = "/dashboard/cars", method = RequestMethod.GET)
    public String getCarsPage() throws Exception {
        return "springatom.tiles.dashboard.cars";
    }

    @RequestMapping(value = "/dashboard/calendar", method = RequestMethod.GET)
    public String getCalendarPage(final ModelMap modelMap) throws Exception {
        return "springatom.tiles.dashboard.calendar";
    }

    @RequestMapping(value = "/dashboard/reports", method = RequestMethod.GET)
    public String getReportsPage() throws Exception {
        return "springatom.tiles.dashboard.reports";
    }
}