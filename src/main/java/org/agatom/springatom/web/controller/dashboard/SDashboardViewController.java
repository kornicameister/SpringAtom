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

package org.agatom.springatom.web.controller.dashboard;

import org.agatom.springatom.web.support.controllers.SDefaultController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(value = SDashboardViewController.CONTROLLER_NAME)
@RequestMapping(value = "/app/dashboard")
public class SDashboardViewController
        extends SDefaultController {
    protected static final String CONTROLLER_NAME = "DashboardViewController";

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public String getCarsPage() throws Exception {
        return "springatom.tiles.dashboard.cars";
    }

    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public String getCalendarPage() throws Exception {
        return "springatom.tiles.dashboard.calendar";
    }

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String getReportsPage() throws Exception {
        return "springatom.tiles.dashboard.reports";
    }

    @Override
    public String getControllerName() {
        return CONTROLLER_NAME;
    }
}
