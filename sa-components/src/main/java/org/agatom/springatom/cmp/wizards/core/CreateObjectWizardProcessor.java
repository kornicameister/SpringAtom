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

package org.agatom.springatom.cmp.wizards.core;

import com.mysema.query.types.Path;
import org.agatom.springatom.cmp.wizards.WizardProcessor;
import org.agatom.springatom.cmp.wizards.data.WizardDescriptor;
import org.agatom.springatom.cmp.wizards.data.result.FeedbackMessage;
import org.agatom.springatom.cmp.wizards.data.result.WizardDebugDataKeys;
import org.agatom.springatom.cmp.wizards.data.result.WizardResult;
import org.agatom.springatom.data.oid.SOid;
import org.agatom.springatom.data.oid.SOidService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code AbstractWizardProcessor} contains basic set of logic to process single wizard.
 * Logic for initializing, submitting is enclosed within and it delegates further processing to subclasses.
 * It is done like this to separate creation of {@link org.agatom.springatom.cmp.wizards.data.result.WizardResult}
 * from actual logic required to execute particular job.
 *
 * <b>Changelog</b>
 * <ol>
 * <li>adjusted to feedback messaging system</li>
 * <li>{@link #onWizardInit(java.util.Locale)}} updated to check for {@link Exception} and if any would be thrown returned appropriate {@link org.agatom.springatom.cmp.wizards.data.result.WizardResult}</li>
 * <li>added expection checking for submitWizard method</li>
 * <li>improved processing by adding submit step method</li>
 * </ol>
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 * </p>
 *
 * @param <T> params object {@link org.agatom.springatom.cmp.wizards.WizardProcessor} submits upon finish
 *
 * @author trebskit
 * @version 0.0.2
 * @since 0.0.1
 */
abstract public class CreateObjectWizardProcessor<T>
        extends AbstractWizardProcessor
        implements WizardProcessor {
    private static final Logger      LOGGER              = Logger.getLogger(CreateObjectWizardProcessor.class);
    private static final String      WIZ_INITIALIZED_MSG = "sa.msg.wizard.initialized";
    private static final String      DESCRIPTOR_KEY      = "descriptor";
    @Autowired
    protected            SOidService oidService          = null;

    protected static String getPropertyName(final Path<?> path) {
        return path.getMetadata().getName();
    }

    @Override
    public final WizardResult onWizardInit(final Locale locale) throws Exception {
        LOGGER.debug(String.format("initialize(locale=%s)", locale));

        final long startTime = System.nanoTime();
        final WizardResult result = new WizardResult();

        try {
            final WizardDescriptor descriptor = this.initializeWizard(locale);
            final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

            result.addWizardData(DESCRIPTOR_KEY, descriptor);
            result.addFeedbackMessage(
                    FeedbackMessage
                            .newInfo()
                            .setMessage(this.messageSource.getMessage(WIZ_INITIALIZED_MSG, locale))
            );

            result.addDebugData(WizardDebugDataKeys.COMPILE_TIME, endTime)
                    .addDebugData(WizardDebugDataKeys.PROCESSOR, ClassUtils.getShortName(this.getClass()))
                    .addDebugData(WizardDebugDataKeys.LOCALE, locale);

        } catch (Exception exp) {
            LOGGER.error("Failed to build descriptor for wizard", exp);
            throw new Exception(exp.getMessage(), exp);
        }

        return result;
    }

    /**
     * Returns {@link org.agatom.springatom.cmp.wizards.data.WizardDescriptor} that contain full definition
     * of a step.
     *
     * @param locale current locale
     *
     * @return the descriptor
     */
    protected abstract WizardDescriptor initializeWizard(final Locale locale);

    /**
     * {@inheritDoc}
     */
    @Override
    public final WizardResult onStepInit(final String step, final Locale locale) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("initializeStep(step=%s,locale=%s)", step, locale));
        }

        final long startTime = System.nanoTime();
        final WizardResult result = new WizardResult().setWizardId(this.getWizardID()).setStepId(step);

        try {
            final ModelMap modelMap = this.initializeStep(step, locale);
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

        result.addDebugData(WizardDebugDataKeys.INIT_TIME, endTime);
        result.addDebugData(WizardDebugDataKeys.LOCALE, locale);
        result.addDebugData(WizardDebugDataKeys.PROCESSOR, ClassUtils.getShortName(this.getClass()));

        return result;
    }

    /**
     * Returns {@link org.springframework.ui.ModelMap} map of the attributes that will be added to {@link org.agatom.springatom.cmp.wizards.data.context.DataScope#STEP}
     * in returned {@link org.agatom.springatom.cmp.wizards.data.result.WizardResult}
     *
     * @param step   actual step to init
     * @param locale current locale
     *
     * @return attributes of the step initialization phase
     *
     * @throws Exception if any
     * @see org.agatom.springatom.cmp.wizards.data.context.DataScope#STEP
     * @see org.agatom.springatom.cmp.wizards.data.result.WizardResult#addStepData(Object)
     */
    protected ModelMap initializeStep(final String step, final Locale locale) throws Exception {
        return this.stepHelperDelegate.initialize(step, locale);
    }

    @Override
    public void setLocalValidator(final Validator localValidator) {
        final Class<?> contextObjectClass = this.getContextObjectClass();
        if (!localValidator.supports(contextObjectClass)) {
            throw new IllegalArgumentException(String.format("Validator [%s] does not support form object class [%s]", localValidator, contextObjectClass));
        }
        super.setLocalValidator(localValidator);
    }

    private Class<?> getContextObjectClass() {
        return GenericTypeResolver.resolveTypeArgument(getClass(), CreateObjectWizardProcessor.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final WizardResult onWizardSubmit(final Map<String, Object> stepData, final Locale locale) throws Exception {
        LOGGER.debug(String.format("onWizardSubmit(stepData=%s)", stepData));
        return this.onStepSubmit(null, stepData, locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("UnusedAssignment")
    public final WizardResult onStepSubmit(final String step, final Map<String, Object> stepData, final Locale locale) throws Exception {
        LOGGER.debug(String.format("onStepSubmit(step=%s, stepData=%s)", step, stepData));

        final long startTime = System.nanoTime();
        final boolean isSubmitStep = StringUtils.hasText(step);

        if (LOGGER.isDebugEnabled() && isSubmitStep) {
            LOGGER.debug(String.format("step=%s therefore it is a step submit", step));
        }

        T contextObject = this.getContextObject();
        // Create appropriate binder.
        DataBinder binder = isSubmitStep ? this.createStepBinder(contextObject, step) : this.createGlobalBinder(contextObject);

        final ModelMap params = new ModelMap().addAllAttributes(stepData);
        final WizardResult finalResult = this.bind(binder, step, params, locale);

        if (!finalResult.hasErrors()) {
            LOGGER.debug(String.format("Bound to context object=%s without any error", contextObject));
            try {
                if (isSubmitStep) {
                    final WizardResult localResult = this.submitStep(contextObject, params, step, locale);
                    if (localResult != null) {
                        finalResult.merge(localResult);
                    } else if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(String.format("%s does not execute any step submission", this.getWizardID()));
                    }
                } else {
                    finalResult.merge(this.submitWizard(contextObject, params, locale));
                }
            } catch (Exception submitExp) {
                LOGGER.error(String.format("Submission failed for contextObject=%s", contextObject), submitExp);
                finalResult.addError(submitExp);
                finalResult.addFeedbackMessage(FeedbackMessage.newError().setMessage(submitExp.getLocalizedMessage()));
            } finally {
                binder.close();
            }
        }

        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("submitStep(step=%s, stepData=%s) executed in %d ms", step, stepData, endTime));
        }

        finalResult.addDebugData(WizardDebugDataKeys.TOTAL_TIME, endTime);
        finalResult.addDebugData(WizardDebugDataKeys.PROCESSOR, ClassUtils.getShortName(this.getClass()));
        finalResult.addDebugData(WizardDebugDataKeys.LOCALE, locale);

        // collect garbage
        binder = null;
        contextObject = null;
        System.gc();

        return finalResult;
    }

    @SuppressWarnings("unchecked")
    protected T getContextObject() throws Exception {
        return (T) this.getContextObjectClass().newInstance();
    }

    /**
     * By default there is no step submission. Therefore no need compute some feedback data for the client.
     * If it is required to react on data provided from the client and update the step please override this method.
     *
     * @param contextObject context object
     * @param stepData      step submission data
     * @param step          actual step
     * @param locale        current locale
     *
     * @return step wizard result
     */
    protected WizardResult submitStep(final T contextObject, final ModelMap stepData, final String step, final Locale locale) {
        return null;
    }

    protected abstract WizardResult submitWizard(final T contextObject, final ModelMap stepData, final Locale locale) throws Exception;

    protected SOid getOID(final T contextObject) throws Exception {
        return this.oidService.getOid(contextObject);
    }
}
