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

import org.agatom.springatom.cmp.wizards.StepHelper;
import org.agatom.springatom.cmp.wizards.data.WizardStepDescriptor;
import org.springframework.ui.ModelMap;
import org.springframework.validation.DataBinder;

import java.util.Locale;

/**
 * {@code AbstractStepHelper} is a convient abstract class combining logic
 * to handle particular step in {@link AbstractWizardProcessor}
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-30</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class AbstractStepHelper
        implements StepHelper {
    private final String  step;
    private final boolean finalStep;

    public AbstractStepHelper(final String step) {
        this(step, false);
    }

    public AbstractStepHelper(final String step, final boolean finalStep) {
        this.step = step;
        this.finalStep = finalStep;
    }

    @Override
    public WizardStepDescriptor getStepDescriptor(final Locale locale) {
        return new WizardStepDescriptor().setStep(this.getStep());
    }

    @Override
    public ModelMap initialize(final Locale locale) throws Exception {
        return new ModelMap();
    }

    @Override
    public void initializeBinder(final DataBinder binder) {

    }

    @Override
    public final String getStep() {
        return this.step;
    }

    @Override
    public boolean isFinalStep() {
        return this.finalStep;
    }

    @Override
    public boolean isValidationEnabled() {
        return false;
    }
}
