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

package org.agatom.springatom.web.controller.event;

import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.web.controller.event.model.SAppointmentMA;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/app/event")
public class SAppointmentController {
    private static final Logger LOGGER = Logger.getLogger(SAppointmentController.class);

    @ResponseBody
    @RequestMapping(
            value = "/appointment/read",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Callable<Set<SAppointment>> getAppointments(
            @ModelAttribute final SAppointmentMA ma
    ) {
        LOGGER.info(ma);
        return new Callable<Set<SAppointment>>() {
            @Override public Set<SAppointment> call() throws Exception {
                return new HashSet<>();
            }
        };
    }

    @ResponseBody
    @RequestMapping(
            value = "/calendar/read",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Callable<Set<SAppointment>> getCalendars() {
        return new Callable<Set<SAppointment>>() {
            @Override public Set<SAppointment> call() throws Exception {
                return new HashSet<>();
            }
        };
    }

}
