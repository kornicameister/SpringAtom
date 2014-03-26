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

package org.agatom.springatom.web.flows.wizards.wizard.newAppointment.validator;

import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentIssue;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.issue.QSIssue;
import org.agatom.springatom.server.model.beans.issue.SIssue;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.repository.repositories.issue.SIssueRepository;
import org.agatom.springatom.web.locale.SMessageSource;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
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
@Component
@Lazy(value = false)
@Role(BeanDefinition.ROLE_SUPPORT)
public class AppointmentValidator {
	@Value(value = "#{sAppointmentProperties['sappointment.minDiffBetweenDatesMs']}")
	private long             minDiffBetweenDates = 0;
	@Value(value = "#{sAppointmentProperties['sappointment.maxDiffBetweenDatesMs']}")
	private long             maxDiffBetweenDates = 0;
	@Value(value = "#{applicationPropertiesBean['component.calendar.minTime']}")
	private Integer          minTime             = 0;
	@Value(value = "#{applicationPropertiesBean['component.calendar.maxTime']}")
	private Integer          maxTime             = 0;
	@Autowired
	private SIssueRepository repository          = null;
	@Autowired
	private SMessageSource   messageSource       = null;

	public void validateStep1(final SAppointment appointment, final ValidationContext context) {
		this.validateDates(appointment, context);
		this.validateCar(appointment, context);
	}

	private void validateDates(final SAppointment appointment, final ValidationContext context) {
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

	private void validateCar(final SAppointment appointment, final ValidationContext context) {
		final SCar car = appointment.getCar();
		final MessageContext messageContext = context.getMessageContext();
		final MessageBuilder messageBuilder = new MessageBuilder();
		final Locale locale = LocaleContextHolder.getLocale();

		if (car != null) {
			this.validateOwner(messageContext, messageBuilder, locale, car.getOwner());
		}
	}

	private void validateOwner(final MessageContext messageContext, final MessageBuilder messageBuilder, final Locale locale, final SUser owner) {
		if (owner != null) {
			final Iterable<SIssue> all = this.repository.findAll(QSIssue.sIssue.assignee.eq(owner));
			final Iterator<SIssue> iterator = all.iterator();
			if (iterator.hasNext()) {
				final StringBuilder stringBuilder = new StringBuilder();
				while (iterator.hasNext()) {
					final SIssue issue = iterator.next();

					stringBuilder.append(this.messageSource.getMessage(issue.getType().name(), locale))
							.append(": ")
							.append(owner.getPerson().getIdentity())
							.append(" ")
							.append(issue.getMessage());

					if (ClassUtils.isAssignableValue(SAppointmentIssue.class, issue)) {
						final SAppointmentIssue appointmentIssue = (SAppointmentIssue) issue;
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
