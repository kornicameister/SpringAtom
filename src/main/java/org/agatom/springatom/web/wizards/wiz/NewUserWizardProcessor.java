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

package org.agatom.springatom.web.wizards.wiz;

import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.beans.person.QSPerson;
import org.agatom.springatom.server.model.beans.person.SPersonContact;
import org.agatom.springatom.server.model.beans.user.QSUser;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.beans.user.authority.SAuthority;
import org.agatom.springatom.server.model.types.contact.ContactType;
import org.agatom.springatom.server.model.types.user.SRole;
import org.agatom.springatom.server.service.domain.SUserService;
import org.agatom.springatom.web.component.ComponentCompilationException;
import org.agatom.springatom.web.component.select.SelectComponent;
import org.agatom.springatom.web.component.select.factory.SelectComponentFactory;
import org.agatom.springatom.web.wizards.StepHelper;
import org.agatom.springatom.web.wizards.Wizard;
import org.agatom.springatom.web.wizards.core.AbstractStepHelper;
import org.agatom.springatom.web.wizards.core.CreateObjectWizardProcessor;
import org.agatom.springatom.web.wizards.data.WizardDescriptor;
import org.agatom.springatom.web.wizards.data.WizardStepDescriptor;
import org.agatom.springatom.web.wizards.data.result.FeedbackMessage;
import org.agatom.springatom.web.wizards.data.result.WizardDebugDataKeys;
import org.agatom.springatom.web.wizards.data.result.WizardResult;
import org.apache.log4j.Logger;
import org.springframework.beans.PropertyValuesEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.converters.StringToEnum;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.2
 * @since 0.0.1
 */
@Wizard(value = "newUser", validate = true)
public class NewUserWizardProcessor
        extends CreateObjectWizardProcessor<SUser> {
    private static final Logger                 LOGGER                 = Logger.getLogger(NewUserWizardProcessor.class);
    private static final String                 FORM_OBJECT_NAME       = "user";
    private final        UserSteps              steps                  = new UserSteps();
    @Autowired
    private              SelectComponentFactory selectComponentFactory = null;
    @Autowired
    private              SUserService           userService            = null;

    @Override
    protected WizardResult submitWizard(SUser contextObject, final ModelMap stepData, final Locale locale) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("submitWizard(contextObject=%s)", contextObject));
        }
        final long startTime = System.nanoTime();
        final WizardResult result = new WizardResult()
                .setWizardId(this.getWizardID());

        try {
            contextObject = this.userService.registerNewUser(contextObject);
            result.setOid(this.getOID(contextObject));
        } catch (Exception exp) {
            result.addError(Throwables.getRootCause(exp));
            result.addFeedbackMessage(
                    FeedbackMessage
                            .newError()
                            .setMessage(
                                    this.messageSource.getMessage("newUser.user.registrationFailed",
                                            new Object[]{contextObject.getUsername(), exp.getMessage()}, locale)
                            )
            );
        }

        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("submitWizard(contextObject=%s) took %d ms", contextObject, endTime));
        }
        result.addDebugData(WizardDebugDataKeys.SUBMISSION_TIME, endTime);
        return result;
    }

    @Override
    protected WizardDescriptor initializeWizard(final Locale locale) {
        LOGGER.debug(String.format("initializeWizard(locale=%s)", locale));

        final WizardDescriptor descriptor = new WizardDescriptor();

        descriptor.setLabel(this.messageSource.getMessage("wizard.NewUserWizard.title", locale));
        descriptor.addStep(this.steps.CREDENTIALS.getStepDescriptor(locale));
        descriptor.addStep(this.steps.AUTHORITIES.getStepDescriptor(locale));
        descriptor.addStep(this.steps.CONTACTS.getStepDescriptor(locale));

        return descriptor;
    }

    @Override
    protected String getContextObjectName() {
        return FORM_OBJECT_NAME;
    }

    @Override
    protected StepHelper[] getStepHelpers() {
        return new StepHelper[]{
                this.steps.CREDENTIALS,
                this.steps.AUTHORITIES,
                this.steps.CONTACTS
        };
    }

    /**
     * Inner per step definition helpers
     */
    private class UserSteps {
        final StringToEnum stringToEnum = new StringToEnum();
        final StepHelper   CREDENTIALS  = new AbstractStepHelper("credentials") {

            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) super.getStepDescriptor(locale)
                        .setRequired(true)
                        .addLabel("username", messageSource.getMessage("susercredentials.username", locale))
                        .addLabel("password", messageSource.getMessage("susercredentials.password", locale))
                        .addLabel("firstname", messageSource.getMessage("sperson.firstname", locale))
                        .addLabel("lastname", messageSource.getMessage("sperson.lastname", locale))
                        .addLabel("primarymail", messageSource.getMessage("sperson.primarymail", locale))
                        .addLabel("user", messageSource.getMessage("suser", locale))
                        .addLabel("person", messageSource.getMessage("sperson", locale))
                        .setLabel(messageSource.getMessage("wizard.NewUserWizard.step1.desc", locale));
            }

            @Override
            public boolean isValidationEnabled() {
                return true;
            }


            @Override
            public void initializeBinder(final DataBinder binder) {

                final QSUser user = QSUser.sUser;
                final QSPerson person = QSPerson.sPerson;

                binder.setRequiredFields(
                        getPropertyName(user.credentials.password),
                        getPropertyName(user.credentials.username),
                        getPropertyName(user.person) + getPropertyName(person.firstName),
                        getPropertyName(user.person) + getPropertyName(person.lastName),
                        getPropertyName(user.person) + getPropertyName(person.primaryMail)
                );

                super.initializeBinder(binder);
            }
        };

        final StepHelper AUTHORITIES = new AbstractStepHelper("authorities") {
            final Logger logger = Logger.getLogger(this.getClass());
            final Set<SRole> excludedRoles = Sets.newHashSet();

            @Override
            public ModelMap initialize(final Locale locale) throws Exception {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug(String.format("initialize(locale=%s)", locale));
                }
                final ModelMap map = super.initialize(locale);
                map.addAttribute("roles", this.getRoles(locale));
                map.addAttribute("extraLabels", this.getExtraLabels(locale));
                return map;
            }

            private SelectComponent<SRole, String> getRoles(final Locale locale) throws Exception {
                if (this.excludedRoles.isEmpty()) {
                    this.logger.trace("roles to exclude not yet loaded");
                    this.getRolesToExcludeInWizard();
                }

                final SRole[] sRoles = SRole.values();
                final List<SRole> toInclude = Lists.newArrayList();
                for (SRole sRole : sRoles) {
                    if (this.excludedRoles.contains(sRole)) {
                        this.logger.trace(String.format("%s suppressed, it was found in excluded roles", sRole));
                        continue;
                    }
                    toInclude.add(sRole);
                }

                return selectComponentFactory
                        .<SRole, String, SRole>newSelectComponent()
                        .from(toInclude)
                        .usingLabelFunction(new Function<SRole, String>() {
                            @Nullable
                            @Override
                            public String apply(@Nullable final SRole input) {
                                assert input != null;
                                return messageSource.getMessage(input.name(), locale);
                            }
                        })
                        .usingValueFunction(new Function<SRole, SRole>() {
                            @Nullable
                            @Override
                            public SRole apply(@Nullable final SRole input) {
                                return input;
                            }
                        })
                        .get();
            }

            private Map<String, String> getExtraLabels(final Locale locale) {
                final Map<String, String> labels = Maps.newHashMap();
                labels.put("selectOnePlease", messageSource.getMessage("sa.msg.selectAtLeastOne", locale));
                return labels;
            }

            /**
             * Reads the set of {@link org.agatom.springatom.server.model.types.user.SRole} to be excluded in
             * wizard from being assigned to new {@link org.agatom.springatom.server.model.beans.user.SUser}
             */
            private void getRolesToExcludeInWizard() throws Exception {
                try {
                    final File file = ResourceUtils.getFile("classpath:org/agatom/springatom/web/wizards/wizard/newUser/excluded-roles.properties");
                    if (file.canRead()) {
                        final Properties properties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(file));
                        final String property = properties.getProperty("springatom.excludedRolesInCreate", "");
                        if (StringUtils.hasText(property)) {
                            final StringToEnum stringToEnum = new StringToEnum();
                            final String[] split = StringUtils.split(property, ",");
                            for (String propRaw : split) {
                                final SRole role = (SRole) stringToEnum.convertSourceToTargetClass(propRaw, SRole.class);
                                this.excludedRoles.add(role);
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    this.logger.error("Failed to access excluded roles properties", e);
                    throw e;
                } catch (IOException e) {
                    this.logger.error("Failed to read properties", e);
                    throw e;
                } catch (Exception e) {
                    this.logger.error("General failure in reading excluded properties", e);
                    throw e;
                }
                if (!this.excludedRoles.isEmpty()) {
                    this.logger.trace(String.format("Read %d roles to be excluded = %s", this.excludedRoles.size(), this.excludedRoles));
                }
            }

            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) super.getStepDescriptor(locale)
                        .setRequired(true)
                        .addLabel("authorities", messageSource.getMessage("suser.roles", locale))
                        .setLabel(messageSource.getMessage("wizard.NewUserWizard.step2.desc", locale));
            }

            @Override
            public void initializeBinder(final DataBinder binder) {
                binder.registerCustomEditor(Set.class, "authorities", new PropertyValuesEditor() {

                    @Override
                    public Object getValue() {
                        final List<?> list = (List<?>) super.getValue();
                        final Set<GrantedAuthority> authorities = Sets.newHashSet();
                        for (final Object rawRole : list) {
                            final String role = (String) rawRole;
                            try {
                                final SRole sRole = (SRole) stringToEnum.convertSourceToTargetClass(role, SRole.class);
                                final SAuthority sAuthority = new SAuthority();
                                sAuthority.setRole(sRole);
                                authorities.add(sAuthority);
                                LOGGER.trace(String.format("Resolved authority from %s to %s", sRole, sAuthority));
                            } catch (Exception e) {
                                logger.error("initializeBinder failed", e);
                            }
                        }
                        return authorities;
                    }

                });

                binder.setRequiredFields("authorities");
            }

        };

        final StepHelper CONTACTS = new AbstractStepHelper("contacts", true) {

            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) super.getStepDescriptor(locale)
                        .setRequired(true)
                        .addLabel("contacts", messageSource.getMessage("sperson.contacts", locale))
                        .addLabel("contacts.contact", messageSource.getMessage("sabstractcontact.contact", locale))
                        .addLabel("contacts.type", messageSource.getMessage("sabstractcontact.type", locale))
                        .setLabel(messageSource.getMessage("wizard.NewUserWizard.step3.desc", locale));
            }

            @Override
            public ModelMap initialize(final Locale locale) throws Exception {
                final ModelMap map = super.initialize(locale);
                map.put("contactTypes", this.getContactTypes(locale));
                return map;
            }

            private Object getContactTypes(final Locale locale) throws ComponentCompilationException {
                final ContactType[] contactTypes = ContactType.values();
                final List<ContactType> list = Lists.newArrayListWithExpectedSize(contactTypes.length);
                Collections.addAll(list, contactTypes);

                return selectComponentFactory
                        .<ContactType, String, ContactType>newSelectComponent()
                        .from(list)
                        .usingLabelFunction(new Function<ContactType, String>() {
                            @Nullable
                            @Override
                            public String apply(@Nullable final ContactType input) {
                                assert input != null;
                                return messageSource.getMessage(input.name(), locale);
                            }
                        })
                        .usingValueFunction(new Function<ContactType, ContactType>() {
                            @Nullable
                            @Override
                            public ContactType apply(@Nullable final ContactType input) {
                                return input;
                            }
                        })
                        .get();
            }

            @Override
            public void initializeBinder(final DataBinder binder) {
                final String personContactsPropertyKye = "person.contacts";

                binder.registerCustomEditor(List.class, personContactsPropertyKye, new PropertyValuesEditor() {

                    @Override
                    @SuppressWarnings("unchecked")
                    public Object getValue() {
                        final List<?> value = (List<?>) super.getValue();
                        final List<SPersonContact> contacts = Lists.newArrayList();
                        for (Object map : value) {
                            try {
                                final Map<String, String> roleAsMap = (Map<String, String>) map;
                                final SPersonContact contact = new SPersonContact();
                                contact.setContact(roleAsMap.get("contact"));
                                contact.setType((ContactType) stringToEnum.convertSourceToTargetClass(roleAsMap.get("type".toUpperCase()), ContactType.class));
                                contacts.add(contact);
                            } catch (Exception exp) {
                                Logger.getLogger(this.getClass()).error("PropertyValuesEditor failed", exp);
                            }
                        }
                        return contacts;
                    }
                });

                binder.setRequiredFields(personContactsPropertyKye);
            }
        };
    }
}
