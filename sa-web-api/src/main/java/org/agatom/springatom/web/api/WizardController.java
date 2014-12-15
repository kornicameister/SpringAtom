package org.agatom.springatom.web.api;

import org.springframework.context.annotation.Description;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * {@code WizardController} defines API for the {@link org.springframework.stereotype.Controller} for {@code wizard processing}
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-13</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Description(value = "Controller defined API for the clients that allows to initialize wizard, steps and send data")
@RequestMapping(value = WizardController.Api.ROOT, produces = {MediaType.APPLICATION_JSON_VALUE})
public interface WizardController {

    @ResponseBody
    @RequestMapping(value = "/init/{wizard}", method = RequestMethod.GET)
    Resource<?> onWizardInit(@PathVariable("wizard") final String wizard, final Locale locale) throws Exception;

    @ResponseBody
    @RequestMapping(value = "/init/{wizard}/step/{step}", method = RequestMethod.GET)
    Resource<?> onStepInit(@PathVariable("wizard") final String wizard, @PathVariable("step") final String step, final Locale locale) throws Exception;

    @ResponseBody
    @RequestMapping(value = "/submit/{wizard}")
    Resource<?> onWizardSubmit(@PathVariable("wizard") final String wizard, @RequestBody final ModelMap formData, final Locale locale) throws Exception;

    @ResponseBody
    @RequestMapping(value = "/submit/{wizard}/step/{step}")
    Resource<?> onStepSubmit(@PathVariable("wizard") final String wizard,
                             @PathVariable("step") final String step,
                             @RequestBody final ModelMap formData,
                             final Locale locale) throws Exception;

    /**
     * {@code Api} is an wrapping class containing all end points hrefs
     */
    static final class Api {
        public static final String ROOT = "/rest/wizard";
    }
}
