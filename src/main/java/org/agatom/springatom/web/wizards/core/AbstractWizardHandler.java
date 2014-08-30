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

package org.agatom.springatom.web.wizards.core;

import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.wizards.Wizard;
import org.agatom.springatom.web.wizards.WizardProcessor;
import org.agatom.springatom.web.wizards.data.result.WizardResult;
import org.agatom.springatom.web.wizards.validation.ValidationService;
import org.agatom.springatom.web.wizards.validation.model.ValidationBean;
import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.binding.message.Message;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.style.StylerUtils;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-18</small>
 *
 * @author trebskit
 * @version 0.0.3
 * @since 0.0.1
 */
abstract class AbstractWizardHandler {
    private static final Logger                      LOGGER             = Logger.getLogger(AbstractWizardHandler.class);
    @Autowired
    @Qualifier(value = "springAtomConversionService")
    protected            FormattingConversionService conversionService  = null;
    @Autowired
    protected            LocalValidatorFactoryBean   delegatedValidator = null;
    @Autowired
    protected            SMessageSource              messageSource      = null;
    @Autowired
    private              ValidationService           validationService  = null;

    protected WizardResult bind(final DataBinder binder, final String step, final Map<String, Object> params) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Executing bind");
        }

        final WizardResult localResult = new WizardResult().setStepId(step).setWizardId(this.getWizardID());
        final long startTime = System.nanoTime();

        try {
            this.doBind(binder, params);
        } catch (Exception exp) {
            LOGGER.error("Binding error detected", exp);
            localResult.addError(exp);
        }

        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        localResult.addDebugData("bindingTime", endTime);
        localResult.addDebugData("stepBinding", StringUtils.hasText(step));
        localResult.addDebugData("dataSize", params.size());

        if ((this.isValidationEnabled() || this.isValidationEnabledForStep(step))) {
            // Do not validate if bindingErrors
            this.doValidate(localResult, binder, params);
        }

        return localResult;
    }

    protected String getWizardID() {
        final String value = this.getWizardAnnotation().value();
        if (!StringUtils.hasText(value)) {
            return StringUtils.uncapitalize(ClassUtils.getShortName(this.getClass()));
        }
        return value;
    }

    private void doBind(final DataBinder binder, Map<String, Object> params) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Binding allowed request parameters in %s to form object with name '%s', pre-bind formObject toString = %s", params, binder.getObjectName(), binder.getTarget()));
            if (binder.getAllowedFields() != null && binder.getAllowedFields().length > 0) {
                LOGGER.debug(String.format("(Allowed fields are %s)", StylerUtils.style(binder.getAllowedFields())));
            } else {
                LOGGER.debug("(Any field is allowed)");
            }
        }
        binder.bind(new MutablePropertyValues(params));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Binding completed for form object with name '%s', post-bind formObject toString = %s", binder.getObjectName(), binder.getTarget()));
            LOGGER.debug(String.format("There are [%d] errors, details: %s", binder.getBindingResult().getErrorCount(), binder.getBindingResult().getAllErrors()));
        }
    }

    protected boolean isValidationEnabled() {
        return this.getWizardAnnotation().validate();
    }

    protected abstract boolean isValidationEnabledForStep(final String step);

    private void doValidate(final WizardResult result, final DataBinder binder, final Map<String, Object> formData) throws Exception {

        final BindingResult bindingResult = binder.getBindingResult();
        final ValidationBean bean = new ValidationBean();

        bean.setPartialResult(result);
        bean.setStepId(result.getStepId());
        bean.setCommandBean(bindingResult.getTarget());
        bean.setCommandBeanName(this.getContextObjectName());
        bean.setFormData(formData);
        bean.setBindingModel(formData);

        if (this.validationService.canValidate(bean)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Validating via validation service for validationBean=%s", bean));
            }
            this.validationService.validate(bean);
        } else if (binder.getValidator() != null) {
            final Validator validator = binder.getValidator();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Validating via Validator instance=%s", validator));
            }
            validator.validate(bean.getCommandBean(), bindingResult);
        }

        if (LOGGER.isDebugEnabled()) {
            final Set<Message> messages = result.getValidationMessages();
            final short count = (short) (messages == null ? 0 : messages.size());
            LOGGER.debug(String.format("Validation completed, found %d validation errors", count));
        }
    }

    private Wizard getWizardAnnotation() {
        return AnnotationUtils.findAnnotation(this.getClass(), Wizard.class);
    }

    protected String getContextObjectName() {
        return WizardProcessor.DEFAULT_FORM_OBJECT_NAME;
    }

    protected DataBinder createBinder(final Object contextObject, final String contextObjectName) {
        LOGGER.debug(String.format("createBinder(contextObject=%s,contextObjectName=%s)", contextObject, contextObjectName));

        Assert.notNull(contextObject, "contextObject must not be null");
        Assert.notNull(contextObjectName, "contextObjectName must not be null");

        final DataBinder binder = new WebDataBinder(contextObject, contextObjectName);

        binder.setIgnoreUnknownFields(true);
        binder.setAutoGrowNestedPaths(true);
        binder.setConversionService(this.conversionService);
        binder.setValidator(this.delegatedValidator);
        binder.setMessageCodesResolver(new DefaultMessageCodesResolver());

        return binder;
    }

}
