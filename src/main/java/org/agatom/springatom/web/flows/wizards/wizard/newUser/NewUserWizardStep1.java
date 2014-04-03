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

package org.agatom.springatom.web.flows.wizards.wizard.newUser;

import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.user.QSUser;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.web.flows.wizards.actions.WizardAction;
import org.agatom.springatom.web.flows.wizards.wizard.WizardFormAction;
import org.apache.log4j.Logger;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * {@code NewUserWizardStep1} handles data preparation for first step of new {@link org.agatom.springatom.server.model.beans.user.SUser}
 * creation
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WizardAction("newUserWizardStep1")
public class NewUserWizardStep1
		extends WizardFormAction<SUser> {
	private static final Logger LOGGER           = Logger.getLogger(NewUserWizardStep1.class);
	private static final String FORM_OBJECT_NAME = "user";

	public NewUserWizardStep1() {
		super();
		this.setFormObjectName(FORM_OBJECT_NAME);
	}

	public SUser getNewUser(final RequestContext context) throws Exception {
		return this.getCommandBean(context);
	}

	@Override
	protected WebDataBinder doInitBinder(final WebDataBinder binder, final FormattingConversionService conversionService) {
		final QSUser user = QSUser.sUser;
		binder.setRequiredFields(
				user.credentials.username.getMetadata().getName(),
				user.credentials.password.getMetadata().getName(),
				user.person.getMetadata().getName() + "." + user.person.firstName.getMetadata().getName(),
				user.person.getMetadata().getName() + "." + user.person.lastName.getMetadata().getName(),
				user.person.getMetadata().getName() + "." + user.person.primaryMail.getMetadata().getName()
		);
		return super.doInitBinder(binder, conversionService);
	}

	@Override
	public Event setupForm(final RequestContext context) throws Exception {
		final Event event = super.setupForm(context);
		if (this.isSuccessEvent(event)) {
			final SUser user = this.getCommandBean(context);
			if (user.getPerson() == null) {
				LOGGER.trace(String.format("Setting %s instance for user %s", ClassUtils.getShortName(SPerson.class), user));
				user.setPerson(new SPerson());
			}
		}
		return event;
	}
}
