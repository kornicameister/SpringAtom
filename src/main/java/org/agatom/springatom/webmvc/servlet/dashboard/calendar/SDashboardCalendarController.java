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

package org.agatom.springatom.webmvc.servlet.dashboard.calendar;

import org.agatom.springatom.web.controllers.SDefaultController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(value = SDashboardCalendarController.CONTROLLER_NAME)
@RequestMapping(value = "/data/dashboard", produces = {
        MediaType.APPLICATION_JSON_VALUE
})
public class SDashboardCalendarController
        extends SDefaultController {
    protected static final String CONTROLLER_NAME = "DashboardDataController";

    public SDashboardCalendarController() {
        super(CONTROLLER_NAME);
    }
}
