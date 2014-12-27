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

package org.agatom.springatom.cmp.wizards.validation;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;
import org.agatom.springatom.cmp.wizards.WizardProcessor;
import org.agatom.springatom.cmp.wizards.data.result.WizardDebugDataKeys;
import org.agatom.springatom.cmp.wizards.data.result.WizardResult;
import org.agatom.springatom.cmp.wizards.validation.model.ValidationBean;
import org.agatom.springatom.core.locale.SMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.binding.message.DefaultMessageContext;
import org.springframework.binding.message.Message;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-27</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service("validationService")
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Role(BeanDefinition.ROLE_SUPPORT)
class ValidationServiceImpl
        implements ValidationService {
    public static final  Comparator<Message>  MESSAGE_COMPARATOR = new Comparator<Message>() {
        @Override
        @SuppressWarnings("unchecked")
        public int compare(final Message o1, final Message o2) {
            int result = ComparisonChain.start()
                    .compare(o1.getSeverity(), o2.getSeverity())
                    .result();
            if (result == 0) {
                final Object source1 = o1.getSource();
                final Object source2 = o2.getSource();
                if (ClassUtils.isAssignableValue(Comparable.class, source1) && ClassUtils.isAssignableValue(Comparable.class, source2)) {
                    result = ((Comparable) source1).compareTo(source2);
                }
            }
            return result;
        }
    };
    private static final String               VALIDATION_TIME    = "validationTime";
    private static final Logger               LOGGER             = LoggerFactory.getLogger(ValidationServiceImpl.class);
    @Autowired
    private              ValidatorsRepository repository         = null;
    @Autowired
    private              SMessageSource       messageSource      = null;

    @Override
    public void validate(final ValidationBean validationBean) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("validate(validationBean=%s)", validationBean));
        }
        final long startTime = System.nanoTime();

        {
            final String commandBeanName = validationBean.getCommandBeanName();
            if (!StringUtils.hasText(commandBeanName) || WizardProcessor.DEFAULT_FORM_OBJECT_NAME.equals(commandBeanName)) {
                LOGGER.warn(String.format("Validation is not possible because commandBeanName(name=%s) is invalid", commandBeanName));
                return;
            }

            final Object validator = this.repository.getValidator(commandBeanName);
            if (validator == null) {
                LOGGER.debug(String.format("For commandBeanName=%s there is no associated validator", commandBeanName));
                return;
            }

            final String stepId = validationBean.getStepId();
            WizardResult partialResult = validationBean.getPartialResult();
            MessageContext messageContext;

            if (StringUtils.hasText(stepId)) {
                messageContext = this.validateStep(validator, stepId, validationBean);
            } else {
                messageContext = this.validateGlobal(validator, validationBean);
            }

            partialResult = this.applyMessages(messageContext, partialResult);
            partialResult.addDebugData(WizardDebugDataKeys.VALIDATOR, ClassUtils.getShortName(validator.getClass()));
            validationBean.setPartialResult(partialResult);
        }

        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        validationBean.getPartialResult().addDebugData(VALIDATION_TIME, endTime);

        LOGGER.debug(String.format("validate(validationBean=%s) took %d ms", validationBean, endTime));
    }

    @Override
    public boolean canValidate(final ValidationBean validationBean) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("canValidate(validationBean=%s)", validationBean));
        }
        final String commandBeanName = validationBean.getCommandBeanName();
        Object validator;
        if ((validator = this.repository.getValidator(commandBeanName)) != null) {
            final String stepId = validationBean.getStepId();
            if (!StringUtils.hasText(stepId)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(String.format("Validation possible for key=%s", commandBeanName));
                }
                // found validator, therefore if not step validation, return true
                return true;
            }
            final String methodName = this.getStepValidateMethodName(stepId);
            final Object model = validationBean.getCommandBean();
            if (this.findValidationMethod(model, validator, methodName, ValidationContext.class) == null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(String.format("Validation not possible for key=%s and stepKey=%s", commandBeanName, stepId));
                }
                return false;
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(String.format("Validation possible for key=%s and stepKey=%s", commandBeanName, stepId));
                }
                return true;
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("No suitable validator for key=%s", commandBeanName));
        }
        return false;
    }

    @Override
    public void validate(final Validator localValidator, final Errors errors, final WizardResult result) {

        final long startTime = System.nanoTime();
        localValidator.validate(localValidator, errors);
        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        result.addDebugData(WizardDebugDataKeys.VALIDATOR, ClassUtils.getShortName(localValidator.getClass()));
        result.addDebugData(WizardDebugDataKeys.VALIDATION_TIME, endTime);

    }

    private MessageContext validateStep(final Object validator, final String stepId, final ValidationBean validationBean) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("validateStep(validator=%s,stepId=%s,validationBean=%s)", validator, stepId, validationBean));
        }
        final String methodName = this.getStepValidateMethodName(stepId);
        final Object model = validationBean.getCommandBean();
        final MessageContext messageContext = this.getMessageContext();

        try {
            Method validateMethod = this.findValidationMethod(model, validator, methodName, ValidationContext.class);

            if (validateMethod != null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(
                            String.format("Invoking current state validator method '%s.%s(%s, ValidationContext)'",
                                    ClassUtils.getShortName(validator.getClass()),
                                    methodName,
                                    ClassUtils.getShortName(model.getClass())
                            )
                    );
                }
                ReflectionUtils.invokeMethod(validateMethod, validator, model, this.getValidationContext(validationBean, messageContext));
            }

        } catch (Exception logAndIgnore) {
            LOGGER.warn(String.format("validateStep(stepId=%s) failed...", stepId), logAndIgnore);
        }

        return messageContext;
    }

    private MessageContext validateGlobal(final Object validator, final ValidationBean validationBean) {
        final MessageContext messageContext = this.getMessageContext();
        final Method validateMethod = this.findValidationMethod(validationBean.getCommandBean(), validator, "validate", ValidationContext.class);
        if (validateMethod != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoking default model validation method 'validate(ValidationContext)'");
            }
            ReflectionUtils.invokeMethod(validateMethod, validator, validationBean.getCommandBean(), messageContext);
        }
        return messageContext;
    }

    private WizardResult applyMessages(final MessageContext messageContext, final WizardResult partialResult) {
        final WizardResult local = new WizardResult();

        final Set<Message> messages = Sets.newTreeSet(MESSAGE_COMPARATOR);
        Collections.addAll(messages, messageContext.getAllMessages());
        local.setValidationMessages(messages);

        return partialResult.merge(local);
    }

    private String getStepValidateMethodName(final String stepId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("getStepValidateMethodName(stepId=%s)", stepId));
        }
        return String.format("validate%s", StringUtils.capitalize(stepId));
    }

    private MessageContext getMessageContext() {
        return new DefaultMessageContext(this.messageSource);
    }

    private Method findValidationMethod(Object model, Object validator, String methodName, Class<?> context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("findValidationMethod(model=%s,validator=%s,methodName=%s,context=%s)", model, validator, methodName, context));
        }

        final List<Class<?>> modelSearchClasses = new ArrayList<>();
        Class<?> modelClass = AopUtils.getTargetClass(model);

        while (modelClass != null) {
            modelSearchClasses.add(modelClass);
            modelClass = modelClass.getSuperclass();
        }
        for (final Class<?> searchClass : modelSearchClasses) {
            final Method method = ReflectionUtils.findMethod(validator.getClass(), methodName, searchClass, context);
            if (method != null) {
                return method;
            }
        }
        return null;
    }

    private ValidationContext getValidationContext(final ValidationBean validationBean, final MessageContext messageContext) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("getValidationContext(validationBean=%s,messageContext=%s)", validationBean, messageContext));
        }
        return new DefaultValidationContext()
                .setPrincipal(null) // TODO add retrieving principal
                .setStepId(validationBean.getStepId())
                .setBindingModel(new ModelMap(validationBean.getBindingModel()))
                .setFormData(new ModelMap(validationBean.getFormData()))
                .setMessageContext(messageContext);

    }
}
