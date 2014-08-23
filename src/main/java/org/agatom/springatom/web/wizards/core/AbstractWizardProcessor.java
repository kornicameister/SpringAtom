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

import org.agatom.springatom.web.wizards.WizardProcessor;
import org.agatom.springatom.web.wizards.data.WizardDescriptor;
import org.agatom.springatom.web.wizards.data.result.FeedbackMessage;
import org.agatom.springatom.web.wizards.data.result.WizardResult;
import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.GenericTypeResolver;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.Locale;
import java.util.Map;

/**
 * {@code AbstractWizardProcessor} contains basic set of logic to process single wizard.
 * Logic for initializing, submitting is enclosed within and it delegates further processing to subclasses.
 * It is done like this to separate creation of {@link org.agatom.springatom.web.wizards.data.result.WizardResult}
 * from actual logic required to execute particular job.
 *
 * <b>Changelog</b>
 * - adjusted to feedback messaging system
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @param <T> params object {@link org.agatom.springatom.web.wizards.WizardProcessor} submits upon finish
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractWizardProcessor<T>
        extends AbstractWizardHandler
        implements WizardProcessor<T> {
    private static final Logger LOGGER              = Logger.getLogger(AbstractWizardProcessor.class);
    private static final String WIZ_INITIALIZED_MSG = "sa.msg.wizard.initialized";
    private static final String DESCRIPTOR_KEY      = "descriptor";

    @Override
    public final WizardResult initialize(final Locale locale) {
        LOGGER.debug(String.format("initialize(locale=%s)", locale));
        final WizardDescriptor descriptor = this.getDescriptor(locale);

        final WizardResult result = new WizardResult();
        result.addWizardData(DESCRIPTOR_KEY, descriptor);
        result.addFeedbackMessage(
                FeedbackMessage
                        .newInfo()
                        .setMessage(this.messageSource.getMessage(WIZ_INITIALIZED_MSG, locale))
        );

        return result;
    }

    @Override
    public WizardResult submit(final Map<String, Object> stepData, final Locale locale) throws Exception {
        LOGGER.debug(String.format("submit(stepData=%s)", stepData));

        final T contextObject = this.getContextObject();
        final DataBinder binder = this.createBinder(contextObject);
        final WizardResult result = new WizardResult();

        binder.bind(new MutablePropertyValues(stepData));
        final Errors errors = binder.getBindingResult();

        if (!errors.hasErrors()) {
            LOGGER.debug(String.format("Bound to context object=%s without errors", contextObject));
            final WizardResult localResult = this.submitWizard(contextObject, stepData, locale);
            result.merge(localResult);
        } else {
            LOGGER.warn(String.format("Found %d binding errors for context object=%s", errors.getErrorCount(), contextObject));
            result.addValidationError(errors);
            for (final ObjectError objectError : errors.getAllErrors()) {
                result.addFeedbackMessage(this.getBindErrorFM(objectError, locale));
            }
        }

        binder.close();

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

        if (!msg.equals(objectError.getCode())) {
            for (final String code : codes) {
                msg = this.messageSource.getMessage(code, locale);
                if (msg.equals(code)) {
                    msg = this.messageSource.getMessage(code, objectError.getArguments(), locale);
                    found = true;
                }
                if (!msg.equals(code)) {
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

    protected String getContextObjectName() {
        return DataBinder.DEFAULT_OBJECT_NAME;
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

}
