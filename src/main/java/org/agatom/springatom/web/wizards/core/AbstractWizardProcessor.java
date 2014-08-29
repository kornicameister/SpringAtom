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

import org.agatom.springatom.core.exception.SException;
import org.agatom.springatom.server.model.OID;
import org.agatom.springatom.web.wizards.WizardProcessor;
import org.agatom.springatom.web.wizards.data.WizardDescriptor;
import org.agatom.springatom.web.wizards.data.WizardStepDescriptor;
import org.agatom.springatom.web.wizards.data.result.FeedbackMessage;
import org.agatom.springatom.web.wizards.data.result.WizardResult;
import org.apache.log4j.Logger;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Persistable;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code AbstractWizardProcessor} contains basic set of logic to process single wizard.
 * Logic for initializing, submitting is enclosed within and it delegates further processing to subclasses.
 * It is done like this to separate creation of {@link org.agatom.springatom.web.wizards.data.result.WizardResult}
 * from actual logic required to execute particular job.
 *
 * <b>Changelog</b>
 * <ol>
 * <li>adjusted to feedback messaging system</li>
 * <li>{@link #initialize(java.util.Locale)} updated to check for {@link java.lang.Exception} and if any would be thrown returned appropriate {@link org.agatom.springatom.web.wizards.data.result.WizardResult}</li>
 * <li>added expection checking for submitWizard method</li>
 * </ol>
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 * </p>
 *
 * @param <T> params object {@link org.agatom.springatom.web.wizards.WizardProcessor} submits upon finish
 *
 * @author trebskit
 * @version 0.0.2
 * @since 0.0.1
 */
abstract public class AbstractWizardProcessor<T>
        extends AbstractWizardHandler
        implements WizardProcessor<T> {
    private static final Logger LOGGER              = Logger.getLogger(AbstractWizardProcessor.class);
    private static final String WIZ_INITIALIZED_MSG = "sa.msg.wizard.initialized";
    private static final String DESCRIPTOR_KEY      = "descriptor";
    private static final String DEBUG_COMPILE_TIME  = "compilationTime";
    private static final String DEBUG_HANDLER       = "handler";
    private static final String DEBUG_LOCALE        = "locale";

    @Override
    public final WizardResult initialize(final Locale locale) throws SException {
        LOGGER.debug(String.format("initialize(locale=%s)", locale));

        final long startTime = System.nanoTime();
        final WizardResult result = new WizardResult();

        try {
            final WizardDescriptor descriptor = this.getDescriptor(locale);
            final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

            result.addWizardData(DESCRIPTOR_KEY, descriptor);
            result.addFeedbackMessage(
                    FeedbackMessage
                            .newInfo()
                            .setMessage(this.messageSource.getMessage(WIZ_INITIALIZED_MSG, locale))
            );

            result.addDebugData(DEBUG_COMPILE_TIME, endTime)
                    .addDebugData(DEBUG_HANDLER, ClassUtils.getShortName(this.getClass()))
                    .addDebugData(DEBUG_LOCALE, locale);

        } catch (Exception exp) {
            LOGGER.error("Failed to build descriptor for wizard", exp);
            throw new SException(exp.getMessage(), exp);
        }

        return result;
    }

    /**
     * Returns {@link org.agatom.springatom.web.wizards.data.WizardDescriptor} that contain full definition
     * of a step.
     *
     * @param locale current locale
     *
     * @return the descriptor
     */
    protected abstract WizardDescriptor getDescriptor(final Locale locale);

    @Override
    public final WizardResult initializeStep(final String step, final Locale locale) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("initializeStep(step=%s,locale=%s)", step, locale));
        }

        final long startTime = System.nanoTime();
        final WizardResult result = new WizardResult().setWizardId(this.getWizardID()).setStepId(step);
        result.addDebugData("locale", locale);

        try {
            final ModelMap modelMap = this.getStepInitData(step, locale);
            if (!CollectionUtils.isEmpty(modelMap)) {
                result.addStepData(modelMap);
            } else {
                LOGGER.trace(String.format("%s does not initialized step=%s", ClassUtils.getShortName(this.getClass()), step));
            }
        } catch (Exception exp) {
            LOGGER.error(String.format("initializeStep(step=%s) has failed", step), exp);
            result.addError(exp);
        }

        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("initializeStep(step=%s,locale=%s) executed in %d ms", step, locale, endTime));
        }

        result.addDebugData("time", endTime);

        return result;
    }

    @Override
    public WizardResult submit(final Map<String, Object> stepData, final Locale locale) throws Exception {
        LOGGER.debug(String.format("submit(stepData=%s)", stepData));
        return this.submitStep(null, stepData, locale);
    }

    @Override
    @SuppressWarnings("UnusedAssignment")
    public WizardResult submitStep(final String step, final Map<String, Object> stepData, final Locale locale) throws Exception {
        LOGGER.debug(String.format("submitStep(step=%s, stepData=%s)", step, stepData));

        T contextObject = this.getContextObject();
        DataBinder binder = this.createBinder(contextObject);

        if (StringUtils.hasText(step)) {
            // temporary solution
            binder.setAllowedFields();
            binder.setRequiredFields();
        }

        final WizardResult result = this.bind(binder, step, stepData);
        final Errors errors = binder.getBindingResult();

        // If there are no errors and this is not step submit --> finish up wizard
        if (!result.hasErrors() && !StringUtils.hasText(step)) {
            LOGGER.debug(String.format("Bound to context object=%s without bindingResult", contextObject));
            try {
                result.merge(this.submitWizard(contextObject, stepData, locale));
            } catch (Exception submitExp) {
                LOGGER.error(String.format("submitWizard failed for contextObject=%s", contextObject), submitExp);
                result.addError(submitExp);
                result.addFeedbackMessage(FeedbackMessage.newError().setMessage(submitExp.getLocalizedMessage()));
            } finally {
                binder.close();
            }
        } else if (errors.hasErrors()) {
            LOGGER.warn(String.format("Found %d binding bindingResult for context object=%s", errors.getErrorCount(), contextObject));
            for (final ObjectError objectError : errors.getAllErrors()) {
                result.addFeedbackMessage(this.getBindErrorFM(objectError, locale));
                result.addBindingError(objectError);
            }
        }

        // collect garbage
        binder = null;
        contextObject = null;
        System.gc();

        return result;
    }

    @SuppressWarnings("unchecked")
    protected T getContextObject() throws Exception {
        return (T) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractWizardProcessor.class).newInstance();
    }

    private DataBinder createBinder(final T contextObject) throws Exception {
        return this.createBinder(contextObject, this.getContextObjectName());
    }

    protected abstract WizardResult submitWizard(final T contextObject, final Map<String, Object> stepData, final Locale locale) throws Exception;

    /**
     * Creates {@link org.agatom.springatom.web.wizards.data.result.FeedbackMessage#newError()} message out of {@link org.springframework.validation.ObjectError}.
     * Runs through {@link org.springframework.validation.ObjectError#getCodes()}} and loops until it locates a valid message (i.e. localized one). In first place
     * it tries to resolve such message out of {@link org.springframework.validation.ObjectError#getCode()}. If both attempts fails {@link org.springframework.validation.ObjectError#getDefaultMessage()}
     * is used to create feedback
     *
     * @param objectError current object error
     * @param locale      current locale
     *
     * @return new feedback message
     */
    private FeedbackMessage getBindErrorFM(final ObjectError objectError, final Locale locale) {
        final FeedbackMessage message = FeedbackMessage.newError();
        final String[] codes = objectError.getCodes();
        boolean found = false;
        String msg = this.messageSource.getMessage(objectError.getCode(), locale);

        if (msg.equals(objectError.getCode())) {
            for (final String code : codes) {
                msg = this.messageSource.getMessage(code, locale);
                if (!msg.equals(code)) {
                    msg = this.messageSource.getMessage(code, objectError.getArguments(), locale);
                    found = true;
                }
                if (found) {
                    break;
                }
            }
        } else {
            found = true;
        }

        if (!found) {
            msg = objectError.getDefaultMessage();
        }
        message.setMessage(msg);
        return message;
    }

    protected abstract ModelMap getStepInitData(final String step, final Locale locale) throws Exception;

    protected OID getOID(final T contextObject) {
        final OID oid = new OID();
        oid.setObjectClass(ClassUtils.getUserClass(contextObject.getClass()).getSimpleName());
        oid.setPrefix("M");
        if (ClassUtils.isAssignableValue(Persistable.class, contextObject)) {
            oid.setId((Long) ((Persistable<?>) contextObject).getId());
        } else {
            oid.setId(System.nanoTime());
        }
        return oid;
    }

    protected static interface StepHelper {
        WizardStepDescriptor getDescriptor(final Locale locale);

        ModelMap init(final Locale locale) throws Exception;
    }

    protected abstract static class AbstractStepHelper
            implements StepHelper {
        @Override
        public ModelMap init(final Locale locale) throws Exception {
            return new ModelMap();
        }
    }
}
