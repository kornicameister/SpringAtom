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

import com.google.common.collect.Lists;
import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.contact.ContactType;
import org.agatom.springatom.server.service.domain.SUserService;
import org.agatom.springatom.web.flows.wizards.actions.WizardAction;
import org.agatom.springatom.web.flows.wizards.wizard.WizardFormAction;
import org.agatom.springatom.web.locale.SMessageSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.ClassUtils;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@WizardAction("newUserWizardStep3")
public class NewUserWizardStep3
		extends WizardFormAction<SPerson> {
	private static final Logger         LOGGER                   = Logger.getLogger(NewUserWizardStep3.class);
	private static final String         FORM_OBJECT_NAME         = "person";
	/**
	 * Localized attributes key under which {@link org.agatom.springatom.web.flows.wizards.wizard.newUser.NewUserWizardStep3.LocalizedContact}
	 * are available in the view
	 */
	private static final String         LOCALIZED_CONTACTS_TYPES = "localizedContactsTypes";
	@Autowired
	private              SMessageSource messageSource            = null;
	@Autowired
	private              SUserService   userService              = null;

	public NewUserWizardStep3() {
		super();
		this.setFormObjectName(FORM_OBJECT_NAME);
	}

	@Override
	public Event setupForm(final RequestContext context) throws Exception {
		LOGGER.trace(String.format("setupForm(context=%s)", context));
		final Locale locale = LocaleContextHolder.getLocale();

		final ContactType[] contactTypes = ContactType.values();
		final List<LocalizedContact> localizedContactsTypes = Lists.newArrayListWithExpectedSize(contactTypes.length);
		for (ContactType contactType : contactTypes) {
			localizedContactsTypes.add(new LocalizedContact().setLabel(this.messageSource.getMessage(contactType.name(), locale)).setContactType(contactType));
		}

		final MutableAttributeMap<Object> viewScope = context.getViewScope();
		viewScope.put(LOCALIZED_CONTACTS_TYPES, localizedContactsTypes);
		return super.setupForm(context);
	}

	@Override
	public Event bindAndValidate(final RequestContext context) throws Exception {
		final Event event = super.bindAndValidate(context);
		if (this.isSuccessEvent(event)) {
			this.registerUser(context);
		}
		return event;
	}

	/**
	 * Effectively register new {@link org.agatom.springatom.server.model.beans.user.SUser}
	 * by calling {@link org.agatom.springatom.server.service.domain.SUserService#registerNewUser(org.agatom.springatom.server.model.beans.user.SUser)}.
	 * <p/>
	 * Failure may occur due to:
	 * <ul>
	 * <li>{@link javax.validation.ConstraintViolationException}</li>
	 * <li>{@link org.agatom.springatom.server.service.exceptions.UserRegistrationException}</li>
	 * </ul>
	 *
	 * @param context context to extract user from
	 *
	 * @return {@link #success()} or {@link #error(Exception)}
	 *
	 * @throws Exception if any
	 */
	private Event registerUser(final RequestContext context) throws Exception {
		final MutableAttributeMap<Object> scope = this.getFormObjectScope().getScope(context);
		final Object object = scope.get("user");
		if (object != null && ClassUtils.isAssignableValue(SUser.class, object)) {
			final SUser user = (SUser) object;
			final SPerson person = this.getCommandBean(context);
			user.setPerson(person);
			try {
				this.userService.registerNewUser(user);
			} catch (Exception exp) {
				final MessageContext messageContext = context.getMessageContext();
				messageContext.addMessage(
						new MessageBuilder()
								.source(user)
								.error()
								.code("newUser.user.registrationFailed")
								.args(user.getUsername(), exp.getMessage())
								.build()
				);
				return error(exp);
			}
		}
		return success();
	}

	@Override
	public Event resetForm(final RequestContext context) throws Exception {
		final SPerson person = this.getCommandBean(context);
		if (person != null) {
			person.clearContacts();
		}
		return super.resetForm(context);
	}

	@Override
	protected Object getFormObject(final RequestContext context) throws Exception {
		final MutableAttributeMap<Object> scope = this.getFormObjectScope().getScope(context);
		final Object object = scope.get("user");
		if (object != null && ClassUtils.isAssignableValue(SUser.class, object)) {
			final SUser user = (SUser) object;
			final SPerson person = user.getPerson();
			if (person != null) {
				return person;
			}
		}
		return super.getFormObject(context);
	}

	/**
	 * {@code LocalizedContact} holds a pair of {@link org.agatom.springatom.server.model.types.contact.ContactType}
	 * and its localized value under {@link org.agatom.springatom.web.flows.wizards.wizard.newUser.NewUserWizardStep3.LocalizedContact#label}
	 * value
	 */
	private class LocalizedContact implements Serializable {
		private static final long        serialVersionUID = -1940243718109904998L;
		private              String      label            = null;
		private              ContactType contactType      = null;

		public String getLabel() {
			return label;
		}

		public LocalizedContact setLabel(final String label) {
			this.label = label;
			return this;
		}

		public ContactType getContactType() {
			return contactType;
		}

		public LocalizedContact setContactType(final ContactType role) {
			this.contactType = role;
			return this;
		}
	}
}
