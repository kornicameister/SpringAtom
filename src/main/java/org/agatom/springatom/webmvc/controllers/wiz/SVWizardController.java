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

package org.agatom.springatom.webmvc.controllers.wiz;

import org.agatom.springatom.web.wizards.WizardProcessor;
import org.agatom.springatom.web.wizards.core.Submission;
import org.agatom.springatom.web.wizards.data.WizardSubmission;
import org.agatom.springatom.web.wizards.data.result.WizardResult;
import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code SVWizardController} allows to communicate and executes {@link org.agatom.springatom.web.wizards.WizardProcessor} from the client.
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * Changelog:
 * - adjusted to use feedback messaging system in wizard
 *
 * @author trebskit
 * @version 0.0.2
 * @since 0.0.1
 */
@Controller(value = SVWizardController.CTRL_NAME)
@RequestMapping(value = "/cmp/wiz",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
@Description(value = "Controller to initialize wizards and submits them")
public class SVWizardController
        extends SVDefaultController {
    protected static final String                          CTRL_NAME    = "wizardController";
    private static final   Logger                          LOGGER       = Logger.getLogger(SVWizardController.class);
    @Autowired
    private                Map<String, WizardProcessor<?>> processorMap = null;

    public SVWizardController() {
        super(CTRL_NAME);
    }

    /**
     * <b>onWizardInit</b> is called as the first method when new wizard is launched. Selects {@link org.agatom.springatom.web.wizards.WizardProcessor}
     * out of {@link #processorMap} and calls {@link org.agatom.springatom.web.wizards.WizardProcessor#initialize(java.util.Locale)} in order to
     * retrieve {@link org.agatom.springatom.web.wizards.data.WizardDescriptor} for the {@code key} wizard.
     *
     * <b>URI: /cmp/wiz/init/{key}</b>
     *
     * @param key    unique id of the {@link org.agatom.springatom.web.wizards.WizardProcessor}
     * @param locale current locale (vital to return descriptor with valid labels etc.)
     *
     * @return {@link org.agatom.springatom.web.wizards.data.WizardSubmission} the submission
     */
    @ResponseBody
    @RequestMapping(value = "/init/{key}", method = RequestMethod.GET)
    protected WizardSubmission onWizardInit(@PathVariable("key") final String key, final Locale locale) {
        LOGGER.debug(String.format("onWizardInit(key=%s,locale=%s)", key, locale));
        final long startTime = System.nanoTime();

        WizardSubmission submission = null;
        WizardResult result = null;

        try {

            final WizardProcessor<?> wizardProcessor = this.processorMap.get(key);
            result = wizardProcessor.initialize(locale);

        } catch (Exception exp) {
            submission = (WizardSubmission) new WizardSubmission(null, Submission.INIT).setError(exp).setSuccess(false).setSize(1);
        }
        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        if (result != null) {
            submission = (WizardSubmission) new WizardSubmission(result, Submission.INIT).setSize(1).setSuccess(true).setTime(endTime);
        }

        LOGGER.trace(String.format("onWizardInit(key=%s) completed in %d ms", key, endTime));

        return submission;
    }

    /**
     * <b>onStepInit</b> picks up {@link org.agatom.springatom.web.wizards.WizardProcessor} according to the {@code wizard} (corresponds to value in {@link #processorMap}) and calls
     * {@link org.agatom.springatom.web.wizards.WizardProcessor#initializeStep(String, java.util.Locale)}.
     * Returned value contains all <b>data</b> to properly sets up active step in client.
     *
     * @param wizard unique id of the {@link org.agatom.springatom.web.wizards.WizardProcessor}
     * @param step   step id (unique within wizard)
     * @param locale current locale (vital to return descriptor with valid labels etc.)
     *
     * @return {@link org.agatom.springatom.web.wizards.data.WizardSubmission} the submission
     */
    @ResponseBody
    @RequestMapping(value = "/init/{wizard}/step/{step}", method = RequestMethod.GET)
    protected WizardSubmission onStepInit(@PathVariable("wizard") final String wizard, @PathVariable("step") final String step, final Locale locale) {
        LOGGER.debug(String.format("onStepInit(wizard=%s,step=%s,locale=%s)", wizard, step, locale));
        final long startTime = System.nanoTime();

        WizardSubmission submission = null;
        WizardResult result = null;

        try {

            final WizardProcessor<?> wizardProcessor = this.processorMap.get(wizard);
            result = wizardProcessor.initializeStep(step, locale);

        } catch (Exception exp) {
            submission = (WizardSubmission) new WizardSubmission(null, Submission.INIT_STEP).setError(exp).setSuccess(false).setSize(1);
        }
        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        if (result != null) {
            submission = (WizardSubmission) new WizardSubmission(result, Submission.INIT_STEP).setSize(1).setSuccess(true).setTime(endTime);
        }

        LOGGER.trace(String.format("onStepInit(wizard=%s,step=%s,locale=%s) completed in %d ms", wizard, step, locale, endTime));

        return submission;
    }

    /**
     * <b>onWizardSubmit</b> is the last method called for a single {@link org.agatom.springatom.web.wizards.WizardProcessor}. Its job is to pick
     * up {@link org.agatom.springatom.web.wizards.WizardProcessor} out of {@link #processorMap} and call {@link org.agatom.springatom.web.wizards.WizardProcessor#submit(java.util.Map, java.util.Locale)}
     * in order to finalize the processing job
     *
     * @param key      unique id of the {@link org.agatom.springatom.web.wizards.WizardProcessor}
     * @param formData form data
     *
     * @return {@link org.agatom.springatom.web.wizards.data.WizardSubmission} the submission
     */
    @ResponseBody
    @RequestMapping(value = "/submit/{key}")
    protected WizardSubmission onWizardSubmit(@PathVariable("key") final String key, @RequestBody final Map<String, Object> formData, final Locale locale) {
        LOGGER.debug(String.format("onWizardSubmit(key=%s,formData=%s)", key, formData));
        final long startTime = System.nanoTime();

        WizardSubmission submission = null;
        WizardResult result = null;

        try {

            final WizardProcessor<?> wizardProcessor = this.processorMap.get(key);
            result = wizardProcessor.submit(formData, locale);

        } catch (Exception exp) {
            submission = (WizardSubmission) new WizardSubmission(null, Submission.SUBMIT).setError(exp).setSuccess(false).setSize(1);
        }
        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        if (result != null) {
            submission = (WizardSubmission) new WizardSubmission(result, Submission.SUBMIT).setSize(1).setSuccess(true).setTime(endTime);
        }

        LOGGER.trace(String.format("onWizardInit(key=%s) completed in %d ms", key, endTime));

        return submission;
    }

    @ResponseBody
    @RequestMapping(value = "/submit/{wizard}/step/{step}")
    public WizardSubmission onStepSubmit(@PathVariable("wizard") final String wizard,
                                         @PathVariable("step") final String step,
                                         @RequestBody final Map<String, Object> formData,
                                         final Locale locale) {
        LOGGER.debug(String.format("onStepSubmit(wizard=%s,step=%s,formData=%s)", wizard, step, formData));
        final long startTime = System.nanoTime();

        WizardSubmission submission = null;
        WizardResult result = null;

        try {

            final WizardProcessor<?> wizardProcessor = this.processorMap.get(wizard);
            result = wizardProcessor.submitStep(step, formData, locale);

        } catch (Exception exp) {
            submission = (WizardSubmission) new WizardSubmission(null, Submission.SUBMIT_STEP).setError(exp).setSuccess(false).setSize(1);
        }
        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        if (result != null) {
            submission = (WizardSubmission) new WizardSubmission(result, Submission.SUBMIT_STEP).setSize(1).setSuccess(true).setTime(endTime);
        }

        LOGGER.trace(String.format("onStepSubmit(wizard=%s,step=%s) completed in %d ms", wizard, step, endTime));

        return submission;
    }

}
