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

package org.agatom.springatom.webmvc.controllers.wizard;

import org.agatom.springatom.cmp.wizards.WizardProcessor;
import org.agatom.springatom.cmp.wizards.core.Submission;
import org.agatom.springatom.cmp.wizards.data.WizardSubmission;
import org.agatom.springatom.cmp.wizards.data.result.WizardResult;
import org.agatom.springatom.cmp.wizards.repository.WizardProcessorNotFoundException;
import org.agatom.springatom.cmp.wizards.repository.WizardsRepository;
import org.agatom.springatom.web.api.WizardController;
import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * {@code SVWizardController} allows to communicate and executes {@link WizardProcessor} from the client.
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * Changelog:
 * - adjusted to use feedback messaging system in wizard
 *
 * @author trebskit
 * @version 0.0.2
 * @since 0.0.1
 */
@Controller
public class SVWizardController
        extends SVDefaultController
        implements WizardController {
    private static final Logger            LOGGER       = LoggerFactory.getLogger(SVWizardController.class);
    @Autowired
    private              WizardsRepository processorMap = null;

    @ResponseBody
    @ExceptionHandler(WizardProcessorNotFoundException.class)
    public ResponseEntity<?> onWizardProcessorNotFoundException(final WizardProcessorNotFoundException exp) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("onWizardProcessorNotFoundException(exp={}) kicked in...", exp);
        }
        return this.errorResponse(exp, HttpStatus.NOT_FOUND);
    }

    /**
     * <b>onWizardInit</b> is called as the first method when new wizard is launched. Selects {@link WizardProcessor}
     * out of {@link #processorMap} and calls {@link WizardProcessor#onWizardInit(java.util.Locale)} in order to
     * retrieve {@link org.agatom.springatom.cmp.wizards.data.WizardDescriptor} for the {@code key} wizard.
     *
     * <b>URI: /cmp/wiz/initialize/{wizard}</b>
     *
     * @param wizard unique id of the {@link WizardProcessor}
     * @param locale current locale (vital to return descriptor with valid labels etc.)
     *
     * @return {@link WizardSubmission} the submission
     */
    public WizardSubmission onWizardInit(final String wizard, final Locale locale) throws Exception {
        LOGGER.debug(String.format("onWizardInit(key=%s,locale=%s)", wizard, locale));
        final long startTime = System.nanoTime();

        WizardSubmission submission = null;
        WizardResult result;

        try {

            final WizardProcessor wizardProcessor = this.processorMap.get(wizard);
            result = wizardProcessor.onWizardInit(locale);

        } catch (Exception exp) {
            LOGGER.debug(String.format("onWizardInit(wizard=%s) failed", wizard), exp);
            throw exp;
        }
        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        if (result != null) {
            submission = (WizardSubmission) new WizardSubmission(result, Submission.INIT).setSize(1).setSuccess(true).setTime(endTime);
        }

        LOGGER.trace(String.format("onWizardInit(wizard=%s) completed in %d ms", wizard, endTime));

        return submission;
    }

    /**
     * <b>onStepInit</b> picks up {@link WizardProcessor} according to the {@code wizard} (corresponds to value in {@link #processorMap}) and calls
     * {@link WizardProcessor#onStepInit(String, java.util.Locale)}.
     * Returned value contains all <b>data</b> to properly sets up active step in client.
     *
     * @param wizard unique id of the {@link WizardProcessor}
     * @param step   step id (unique within wizard)
     * @param locale current locale (vital to return descriptor with valid labels etc.)
     *
     * @return {@link .WizardSubmission} the submission
     */
    public WizardSubmission onStepInit(final String wizard, final String step, final Locale locale) throws Exception {
        LOGGER.debug(String.format("onStepInit(wizard=%s,step=%s,locale=%s)", wizard, step, locale));

        final long startTime = System.nanoTime();

        WizardSubmission submission = null;
        WizardResult result;

        try {

            final WizardProcessor wizardProcessor = this.processorMap.get(wizard);
            result = wizardProcessor.onStepInit(step, locale);

        } catch (Exception exp) {
            LOGGER.debug(String.format("onStepInit(wizard=%s,step=%s) failed", wizard, step), exp);
            throw exp;
        }
        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        if (result != null) {
            submission = (WizardSubmission) new WizardSubmission(result, Submission.INIT_STEP).setSize(1).setSuccess(true).setTime(endTime);
        }

        LOGGER.trace(String.format("onStepInit(wizard=%s,step=%s,locale=%s) completed in %d ms", wizard, step, locale, endTime));

        return submission;
    }

    /**
     * <b>onWizardSubmit</b> is the last method called for a single {@link WizardProcessor}. Its job is to pick
     * up {@link WizardProcessor} out of {@link #processorMap} and call {@link WizardProcessor#onWizardSubmit(java.util.Map, java.util.Locale)}
     * in order to finalize the processing job
     *
     * @param wizard   unique id of the {@link WizardProcessor}
     * @param formData form data
     *
     * @return {@link WizardSubmission} the submission
     */
    @Override
    public WizardSubmission onWizardSubmit(final String wizard, final ModelMap formData, final Locale locale) throws Exception {
        LOGGER.debug(String.format("onWizardSubmit(wizard=%s,formData=%s)", wizard, formData));

        final long startTime = System.nanoTime();

        WizardSubmission submission = null;
        WizardResult result;

        try {

            final WizardProcessor wizardProcessor = this.processorMap.get(wizard);
            result = wizardProcessor.onWizardSubmit(formData, locale);

        } catch (Exception exp) {
            LOGGER.debug(String.format("onWizardSubmit(wizard=%s) failed", wizard), exp);
            throw exp;
        }
        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        if (result != null) {
            submission = (WizardSubmission) new WizardSubmission(result, Submission.SUBMIT).setSize(1).setSuccess(true).setTime(endTime);
        }

        LOGGER.trace(String.format("onWizardSubmit(wizard=%s) completed in %d ms", wizard, endTime));

        return submission;
    }

    @Override
    public WizardSubmission onStepSubmit(final String wizard, final String step, final ModelMap formData, final Locale locale) throws Exception {
        LOGGER.debug(String.format("onStepSubmit(wizard=%s,step=%s,formData=%s)", wizard, step, formData));

        final long startTime = System.nanoTime();

        WizardSubmission submission = null;
        WizardResult result;

        try {

            final WizardProcessor wizardProcessor = this.processorMap.get(wizard);
            result = wizardProcessor.onStepSubmit(step, formData, locale);

        } catch (Exception exp) {
            LOGGER.debug(String.format("onStepSubmit(wizard=%s,step=%s) failed", wizard, step), exp);
            throw exp;
        }
        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        if (result != null) {
            submission = (WizardSubmission) new WizardSubmission(result, Submission.SUBMIT_STEP).setSize(1).setSuccess(true).setTime(endTime);
        }

        LOGGER.trace(String.format("onStepSubmit(wizard=%s,step=%s) completed in %d ms", wizard, step, endTime));

        return submission;
    }

}
