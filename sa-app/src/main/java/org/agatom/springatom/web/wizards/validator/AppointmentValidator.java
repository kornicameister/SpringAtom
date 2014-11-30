/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.web.wizards.validator;

import org.agatom.springatom.cmp.locale.SMessageSource;
import org.agatom.springatom.cmp.wizards.validation.annotation.WizardValidator;
import org.agatom.springatom.data.hades.model.appointment.NAppointment;
import org.agatom.springatom.data.hades.model.appointment.NAppointmentIssue;
import org.agatom.springatom.data.hades.model.car.NCar;
import org.agatom.springatom.data.hades.model.issue.NIssue;
import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.service.NIssueService;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.ClassUtils;

import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 25.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WizardValidator
class AppointmentValidator {
    @Value(value = "#{applicationProperties['appointment.minDiffBetweenDatesMs']}")
    private long           minDiffBetweenDates = 0;
    @Value(value = "#{applicationProperties['appointment.maxDiffBetweenDatesMs']}")
    private long           maxDiffBetweenDates = 0;
    @Value(value = "#{applicationProperties['component.calendar.minTime']}")
    private Integer        minTime             = 0;
    @Value(value = "#{applicationProperties['component.calendar.maxTime']}")
    private Integer        maxTime             = 0;
    @Autowired
    private NIssueService  issuesService       = null;
    @Autowired
    private SMessageSource messageSource       = null;

    @SuppressWarnings("UnusedDeclaration")
    public void validateDefinition(final NAppointment appointment, final ValidationContext context) {
        this.validateDates(appointment, context);
        this.validateCar(appointment, context);
    }

    private void validateDates(final NAppointment appointment, final ValidationContext context) {
        final MessageContext messageContext = context.getMessageContext();
        final MessageBuilder messageBuilder = new MessageBuilder();

        final DateTime begin = appointment.getBegin();
        final DateTime end = appointment.getEnd();

        final int beginHourOfDay = begin.getHourOfDay();
        final int endHourOfDay = end.getHourOfDay();
        if (beginHourOfDay < this.minTime) {
            messageContext.addMessage(
                    messageBuilder.source("begin").error().defaultText(String.format("Begin hour must not be lower than %d", this.minTime)).build()
            );
        }
        if (endHourOfDay > this.maxTime) {
            messageContext.addMessage(
                    messageBuilder.source("end").error().defaultText(String.format("End hour must not be higher than %d", this.maxTime)).build()
            );
        }
        if (begin.isAfter(end)) {
            messageContext.addMessage(
                    messageBuilder.source("begin").error().defaultText("Begin must be before End").build()
            );
        } else {
            final Duration duration = new Duration(end.minus(begin.getMillis()).getMillis());
            if (duration.isShorterThan(new Duration(this.minDiffBetweenDates))) {
                messageContext.addMessage(
                        messageBuilder
                                .source("interval")
                                .warning()
                                .defaultText(String.format("Time of appointment is shorter than %d minutes", TimeUnit.MILLISECONDS.toMinutes(this.minDiffBetweenDates)))
                                .build()
                );
            } else if (duration.isLongerThan(new Duration(this.maxDiffBetweenDates))) {
                messageContext.addMessage(
                        messageBuilder
                                .source("interval")
                                .warning()
                                .defaultText(String.format("Time of appointment is longer than %d days", TimeUnit.MILLISECONDS.toDays(this.maxDiffBetweenDates)))
                                .build()
                );
            }
        }
    }

    private void validateCar(final NAppointment appointment, final ValidationContext context) {
        final NCar car = appointment.getCar();
        final MessageContext messageContext = context.getMessageContext();
        final MessageBuilder messageBuilder = new MessageBuilder();
        final Locale locale = LocaleContextHolder.getLocale();

        if (car != null) {
            this.validateOwner(messageContext, messageBuilder, locale, car.getOwner());
        }
    }

    private void validateOwner(final MessageContext messageContext, final MessageBuilder messageBuilder, final Locale locale, final NUser owner) {
        if (owner != null) {
            final Iterable<NIssue> all = this.issuesService.findForAssignee(owner);
            final Iterator<NIssue> iterator = all.iterator();
            if (iterator.hasNext()) {
                final StringBuilder stringBuilder = new StringBuilder();
                while (iterator.hasNext()) {
                    final NIssue issue = iterator.next();

                    stringBuilder.append(this.messageSource.getMessage(issue.getType(), locale))
                            .append(": ")
                            .append(owner.getUsername())
                            .append(" ")
                            .append(issue.getMessage());

                    if (ClassUtils.isAssignableValue(NAppointmentIssue.class, issue)) {
                        final NAppointmentIssue appointmentIssue = (NAppointmentIssue) issue;
                        stringBuilder
                                .append("\n")
                                .append(appointmentIssue.getAppointment().getBeginDate().toString(this.messageSource.getMessage("data.format.value", locale)))
                                .append("\n");
                    }
                }
                messageContext.addMessage(
                        messageBuilder
                                .warning()
                                .source("car.owner")
                                .defaultText(stringBuilder.toString())
                                .build()
                );
            }
        }
    }

}
