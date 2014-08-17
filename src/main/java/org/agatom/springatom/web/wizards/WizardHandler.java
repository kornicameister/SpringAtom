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

package org.agatom.springatom.web.wizards;

import org.agatom.springatom.web.wizards.data.InitStepData;
import org.agatom.springatom.web.wizards.data.SubmitStepData;
import org.agatom.springatom.web.wizards.data.WizardDescriptor;

import java.util.Locale;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface WizardHandler<T> {
    /**
     * Initializes wizard bean. {@link org.agatom.springatom.web.wizards.data.WizardDescriptor} contains
     * information about wizard title as well description of steps. Steps are vital for client side processing
     * because they contain information about names of the steps, their state keys as well labels or intel
     * if step is required
     *
     * @param locale current locale
     *
     * @return initialized {@link org.agatom.springatom.web.wizards.data.WizardDescriptor}
     */
    WizardDescriptor initialize(final Locale locale);

    /**
     * Initializes {@code step}. This method is designed to retrieve all data required by the step. For instance
     * it can be a set of values for select boxes
     *
     * @param step step to initialize
     *
     * @return init data
     */
    InitStepData initStep(final String step);

    /**
     * Submits the step, sets all values in the context object and runs validation.
     *
     * @param step step to submit
     *
     * @return feedback data of submission
     */
    SubmitStepData submitStep(final String step);

    /**
     * Submits the form. May call a service or execute any other persisting job. Nevertheless it will always
     * return a populated object for which {@link org.agatom.springatom.web.wizards.WizardHandler} was created for
     *
     * @return submitted object
     */
    T submit();

    /**
     * Returns context object of current step
     *
     * @return context object
     */
    T getContextObject();
}
