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

package org.agatom.springatom.web.wizards.wiz.validator;

import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.person.SPersonContact;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.contact.ContactType;
import org.agatom.springatom.server.service.domain.SPersonService;
import org.agatom.springatom.server.service.domain.SUserService;
import org.agatom.springatom.server.service.support.constraints.PhoneNumber;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.wizards.validation.annotation.WizardValidator;
import org.apache.log4j.Logger;
import org.hibernate.validator.internal.constraintvalidators.EmailValidator;
import org.hibernate.validator.internal.constraintvalidators.PatternValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageResolver;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 25.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WizardValidator
public class UserValidator {
    private static final Logger         LOGGER         = Logger.getLogger(UserValidator.class);
    private static final String         PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    @Autowired
    private              SUserService   userService    = null;
    @Autowired
    private              SPersonService personService  = null;
    @Autowired
    private SMessageSource messageSource = null;

    /**
     * Performs first step validation
     * Validation steps:
     * <ol>
     * <li>checks if user for the {@link org.agatom.springatom.server.model.beans.user.SUser#getUsername()} already exists</li>
     * <li>{@link #validatePassword(String, org.springframework.binding.message.MessageContext, org.springframework.binding.message.MessageBuilder)}</li>
     * <li>{@link #validatePersonExistence(org.agatom.springatom.server.model.beans.person.SPerson, org.springframework.binding.message.MessageContext, org.springframework.binding.message.MessageBuilder)}</li>
     * </ol>
     *
     * @param user    {@link org.agatom.springatom.server.model.beans.user.SUser} to validate
     * @param context current {@link org.springframework.binding.validation.ValidationContext}
     */
    public void validateCredentials(final SUser user, final ValidationContext context) {
        LOGGER.info(String.format("validateCredentials(user=%s,context=%s)", user, context));

        final MessageContext messageContext = context.getMessageContext();
        final MessageBuilder messageBuilder = new MessageBuilder();

        boolean userExists = false;
        try {
            this.userService.loadUserByUsername(user.getUsername());
            userExists = true;
        } catch (UsernameNotFoundException exp) {
            LOGGER.trace(String.format("User %s not exists", user.getUsername()));
        }
        if (!userExists) {
            this.validatePersonExistence(user.getPerson(), messageContext, messageBuilder);
            this.validatePassword(user.getPassword(), messageContext, messageBuilder);
        } else {
            messageContext.addMessage(messageBuilder.source("username").code("newUser.user.alreadyDefined").arg(user.getUsername()).error().build());
        }
    }

    /**
     * Checks if {@link org.agatom.springatom.server.model.beans.person.SPerson} for {@link org.agatom.springatom.server.model.beans.user.SUser}
     * already exists in the system
     *
     * @param person         person to check if it already exists in the database
     * @param messageContext messageContext to put error message
     * @param messageBuilder builder to create new message
     */
    private void validatePersonExistence(final SPerson person,
                                         final MessageContext messageContext,
                                         final MessageBuilder messageBuilder) {
        final SPerson byEmail = this.personService.findByEmail(person.getPrimaryMail());
        if (byEmail != null) {
            messageContext.addMessage(messageBuilder.source("person.primaryMail").code("newUser.user.person.duplicateMail").arg(person.getPrimaryMail()).error().build());
        }
    }

    /**
     * Validates password against given regex
     *
     * @param password       password to validate
     * @param messageContext messageContext to put error message
     * @param messageBuilder builder to create new message
     */
    private void validatePassword(final String password,
                                  final MessageContext messageContext,
                                  final MessageBuilder messageBuilder) {
        final Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        final Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            messageContext.addMessage(messageBuilder.source("password").code("newUser.user.invalidPassword").arg(password).error().build());
        }
    }

    public void validateContacts(final SUser user, final ValidationContext context) {
        LOGGER.info(String.format("validateContacts(user=%s,context=%s)", user, context));

        final MessageContext messageContext = context.getMessageContext();
        final MessageBuilder messageBuilder = new MessageBuilder();

        final List<SPersonContact> contacts = user.getPerson().getContacts();
        for (final SPersonContact contact : contacts) {
            final MessageResolver resolver = this.validateContact(contact, messageBuilder);
            if (resolver != null) {
                messageContext.addMessage(resolver);
            }
        }
    }

    private MessageResolver validateContact(final SPersonContact spc, final MessageBuilder messageBuilder) {
        final ContactType type = spc.getType();
        final String contact = spc.getContact();

        switch (type) {
            case SCT_MAIL:
                if (!new EmailValidator().isValid(contact, null)) {
                    return messageBuilder
                            .error()
                            .source(type)
                            .code("newUser.user.invalidEmail")
                            .arg(contact)
                            .build();
                }
                break;
            default:
                return PhoneNumberValidator.isValid(type, contact, messageBuilder, this.messageSource);
        }

        return null;
    }

    private static class PhoneNumberValidator {
        private static javax.validation.constraints.Pattern pattern;
        private static PatternValidator                     validator;

        static MessageResolver isValid(final ContactType type, final String number, final MessageBuilder messageBuilder, final SMessageSource messageSource) {
            if (!validator.isValid(number, null)) {
                final String message = messageSource.getMessage(type.toString(), LocaleContextHolder.getLocale());
                return messageBuilder
                        .error()
                        .source(type)
                        .code("newUser.user.invalidContact")
                        .args(message, number)
                        .build();
            }
            return null;
        }


        static {
            PhoneNumberValidator.pattern = AnnotationUtils.findAnnotation(PhoneNumber.class, javax.validation.constraints.Pattern.class);
            PhoneNumberValidator.validator = new PatternValidator();
            PhoneNumberValidator.validator.initialize(PhoneNumberValidator.pattern);
        }
    }

}
