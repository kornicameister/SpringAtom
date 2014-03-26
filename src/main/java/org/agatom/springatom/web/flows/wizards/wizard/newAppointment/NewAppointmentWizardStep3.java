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

package org.agatom.springatom.web.flows.wizards.wizard.newAppointment;

import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.service.domain.SAppointmentService;
import org.agatom.springatom.web.flows.wizards.actions.WizardAction;
import org.agatom.springatom.web.flows.wizards.wizard.WizardFormAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WizardAction("newAppointmentStep3")
public class NewAppointmentWizardStep3
		extends WizardFormAction<SAppointment> {
	private static final Logger              LOGGER           = Logger.getLogger(NewAppointmentWizardStep3.class);
	private static final String              FORM_OBJECT_NAME = "appointment";
	@Autowired
	private              SAppointmentService service          = null;

	public NewAppointmentWizardStep3() {
		super();
		this.setFormObjectName(FORM_OBJECT_NAME);
		this.setValidator(new SAppointmentValidator());
	}

	@Override
	public Event resetForm(final RequestContext context) throws Exception {
		final SAppointment appointment = this.getCommandBean(context);
		appointment.setComment(null);
		return success(appointment);
	}

	@Override
	public Event bindAndValidate(final RequestContext context) throws Exception {
		final Event event = super.bindAndValidate(context);
		if (event.getId().equals(this.success().getId())) {
			final SAppointment sAppointment = this.service.save(this.getCommandBean(context));
			Assert.notNull(sAppointment, "Failed to save appointment, null returned");
			Assert.isTrue(!sAppointment.isNew(), "Failed to save appointment, isNew() returned true");
		}
		return event;
	}

	private class SAppointmentValidator
			implements Validator {

		@Override
		public boolean supports(final Class<?> clazz) {
			return ClassUtils.isAssignable(SAppointment.class, clazz);
		}

		@Override
		public void validate(final Object target, final Errors errors) {
			Assert.notNull(target, "Target must not be null");
			Assert.isAssignable(SAppointment.class, target.getClass());
			delegatedValidator.validate(target, errors);
			if (!errors.hasErrors()) {
				LOGGER.debug(String.format("%s validated without errors", target));
			} else {
				LOGGER.error(String.format("%s validated wit errors => %s", target, errors));
			}
		}
	}

}
