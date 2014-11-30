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

package org.agatom.springatom.cmp.wizards;

import org.agatom.springatom.cmp.wizards.data.result.WizardResult;
import org.springframework.validation.DataBinder;

import java.util.Locale;
import java.util.Map;

/**
 * {@code WizardProcessor} is an interface defining a contract of a single <b>wizard processor.</b>
 * Such processor is able to:
 * <ol>
 * <li>initialize wizard by creating its {@link org.agatom.springatom.cmp.wizards.data.WizardDescriptor}</li>
 * <li>initialize particular steps</li>
 * <li>submit the wizard's form data to the persistent storage</li>
 * </ol>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface WizardProcessor {
    static String DEFAULT_FORM_OBJECT_NAME = DataBinder.DEFAULT_OBJECT_NAME;

    /**
     * Initializes wizard bean. {@link org.agatom.springatom.cmp.wizards.data.WizardDescriptor} contains
     * information about wizard title as well description of steps. Steps are vital for client side processing
     * because they contain information about names of the steps, their state keys as well labels or intel
     * if step is required
     *
     * @param locale current locale
     *
     * @return initialized {@link org.agatom.springatom.cmp.wizards.data.WizardDescriptor}
     */
    WizardResult onWizardInit(final Locale locale) throws Exception;

    /**
     * Performs {@code step} initialization. This method must be defined in order to put specific data when wizard's step
     * is being about to enter.
     *
     * @param step   step to initialize
     * @param locale active locale
     *
     * @return {@code step} initialize data
     */
    WizardResult onStepInit(final String step, final Locale locale);

    /**
     * Submits the form. May call a service or execute any other persisting job. Nevertheless it will always
     * return a populated object for which {@link org.agatom.springatom.cmp.wizards.WizardProcessor} was created for
     *
     * @return submitted object
     */
    WizardResult onWizardSubmit(final Map<String, Object> stepData, final Locale locale) throws Exception;

    WizardResult onStepSubmit(final String step, final Map<String, Object> formData, final Locale locale) throws Exception;
}
