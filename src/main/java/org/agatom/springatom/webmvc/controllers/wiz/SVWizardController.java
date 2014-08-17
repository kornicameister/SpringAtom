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

import org.agatom.springatom.web.wizards.WizardHandler;
import org.agatom.springatom.web.wizards.data.WizardDescriptor;
import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.agatom.springatom.webmvc.data.DataResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(value = SVWizardController.CTRL_NAME)
@RequestMapping(value = "/cmp/wiz",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
@Description(value = "Controller to initialize wizards and submits them")
public class SVWizardController
        extends SVDefaultController {
    protected static final String                        CTRL_NAME        = "wizardController";
    private static final   String                        ACTIVE_WIZARD    = "activeWizard";
    private static final   Logger                        LOGGER           = Logger.getLogger(SVWizardController.class);
    @Autowired
    private                Map<String, WizardHandler<?>> wizardHandlerSet = null;

    public SVWizardController() {
        super(CTRL_NAME);
    }

    @PostConstruct
    protected void init() {
        Assert.notEmpty(this.wizardHandlerSet);
    }

    @ResponseBody
    @RequestMapping(value = "/init/{key}", method = RequestMethod.GET)
    protected Object onWizardInit(@PathVariable("key") final String key, final HttpSession session, final Locale locale) {
        final long startTime = System.nanoTime();
        final WizardHandler<?> wizardHandler = this.wizardHandlerSet.get(key);
        DataResource<?> resource = null;
        WizardDescriptor descriptor = null;
        try {
            Assert.notNull(wizardHandler, String.format("Failed to find wizardHandler for key=%s", key));
            if (session.isNew() || session.getAttribute(ACTIVE_WIZARD) != null) {
                session.removeAttribute(ACTIVE_WIZARD);
            }
            descriptor = wizardHandler.initialize(locale);
            session.setAttribute(ACTIVE_WIZARD, key);
        } catch (Exception exp) {
            resource = WizardResource.newErrorResource(exp);
        }
        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        LOGGER.trace(String.format("onWizardInit(key=%s) completed in %d ms", key, endTime));
        if (descriptor != null) {
            resource = WizardResource.newWizardDescriptorResource(descriptor).setTime(endTime);
        }
        return resource;
    }

    @ResponseBody
    @RequestMapping(value = "/init/step/{step}", method = RequestMethod.GET)
    protected Object onStepInit(@PathVariable("step") final String step, final HttpSession session, final Locale locale) {
        final String activeWizard = (String) session.getAttribute(ACTIVE_WIZARD);
        return null;
    }

    @RequestMapping(value = "/submit/{key}")
    protected Object onWizardSubmit(@PathVariable("key") final String key, final HttpSession session, final Locale locale) {
        session.removeAttribute(ACTIVE_WIZARD);
        session.invalidate();
        return null;
    }

    @RequestMapping(value = "/submit/step/{step}")
    protected Object onStepSubmit(@PathVariable("step") final String step, final HttpSession session, final Locale locale) {
        final String activeWizard = (String) session.getAttribute(ACTIVE_WIZARD);
        return null;
    }
}
