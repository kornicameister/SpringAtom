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

package org.agatom.springatom.webmvc.controllers.data;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.agatom.springatom.core.UnixTimestamp;
import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.service.domain.SAppointmentService;
import org.agatom.springatom.web.beans.calendar.CalendarEvent;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.webmvc.controllers.user.SVUserController;
import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.agatom.springatom.webmvc.exceptions.ControllerTierException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * <p>SVAppointmentController class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(value = SVAppointmentController.CONTROLLER_NAME)
@RequestMapping(value = "/data/appointment")
public class SVAppointmentController
        extends SVDefaultController {
    /** Constant <code>CONTROLLER_NAME="AppointmentController"</code> */
    public static final  String CONTROLLER_NAME = "AppointmentController";
    private static final Logger LOGGER          = Logger.getLogger(SVAppointmentController.class);
    @Autowired
    private SAppointmentService appointmentService;
    @Value("#{T(java.lang.Boolean).valueOf(webProperties['org.agatom.springatom.server.model.beans.appointment.SAppointment.currentUserOnly']).booleanValue()}")
    private Boolean             currentUserOnly;
    @Autowired
    private SMessageSource      messageSource;

    /**
     * <p>Constructor for SVAppointmentController.</p>
     */
    public SVAppointmentController() {
        super(CONTROLLER_NAME);
    }

    /**
     * <p>getAllAppointmentsInRange.</p>
     *
     * @param domainClass a {@link java.lang.String} object.
     * @param start       a {@link org.agatom.springatom.core.UnixTimestamp} object.
     * @param end         a {@link org.agatom.springatom.core.UnixTimestamp} object.
     * @param locale      a {@link java.util.Locale} object.
     *
     * @return a {@link java.util.List} object.
     *
     * @throws org.agatom.springatom.webmvc.exceptions.ControllerTierException if any.
     */
    @ResponseBody
    @RequestMapping(
            value = "/feed",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public List<CalendarEvent> getAllAppointmentsInRange(
            @RequestParam(value = "domainClass", required = true) final String domainClass,
            @RequestParam(value = "start", required = true) final UnixTimestamp start,
            @RequestParam(value = "end", required = true) final UnixTimestamp end,
            final Locale locale
    ) throws ControllerTierException {
        LOGGER.debug(String.format("/feed request [%s]", Lists.newArrayList(domainClass, start, end)));
        final List<SAppointment> list;
        try {
            list = this.appointmentService.findBetween(
                    start.getTime(),
                    end.getTime(),
                    this.currentUserOnly
            );
        } catch (Exception exception) {
            throw new ControllerTierException("Exception in processing /feed", exception);
        }
        LOGGER.debug(String.format("/feed will return %d for %s", list.size(), Lists.newArrayList(domainClass, start, end)));

        final String eventTitleRBKey = String.format("%s.title", CalendarEvent.class.getName());

        return FluentIterable
                .from(list)
                .transform(new Function<SAppointment, CalendarEvent>() {
                    @Nullable
                    @Override
                    public CalendarEvent apply(@Nullable final SAppointment input) {
                        Assert.notNull(input);
                        assert input != null;
                        final Object[] args = {
                                input.getId(),
                                input.getCar().getLicencePlate()
                        };
                        final CalendarEvent event = new CalendarEvent()
                                .setPrimaryKey(input.getId())
                                .setTitle(messageSource.getMessage(eventTitleRBKey, args, locale))
                                .setStart(input.getBegin())
                                .setEnd(input.getEnd());

                        event.add(linkTo(SVAppointmentController.class).slash(input.getId()).withSelfRel());
                        event.add(linkTo(SVAppointmentController.class).slash(input.getId()).withRel("domainClazzId"));
                        event.add(linkTo(SVUserController.class).slash(input.getAssignee().getId()).withRel("assignee"));
                        event.add(linkTo(SVUserController.class).slash(input.getReporter().getId()).withRel("reporter"));
                        event.add(linkTo(SVAppointmentController.class).slash(input.getCar().getId()).withRel("car"));

                        return event;
                    }
                })
                .toList();
    }


}
