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

import com.google.common.collect.Maps;
import org.agatom.springatom.web.wizards.StepHelper;
import org.agatom.springatom.web.wizards.data.WizardStepDescriptor;
import org.springframework.ui.ModelMap;
import org.springframework.validation.DataBinder;

import java.util.Locale;
import java.util.Map;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-30</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class StepHelperDelegate {

    final Map<String, StepHelper> helperMap;

    StepHelperDelegate(StepHelper... helpers) {
        this.helperMap = Maps.newHashMapWithExpectedSize(helpers.length);
        for (StepHelper helper : helpers) {
            this.helperMap.put(helper.getStep(), helper);
        }
    }

    WizardStepDescriptor getStepDescriptor(final String step, final Locale locale) {
        return this.helperMap.get(step).getStepDescriptor(locale);
    }

    ModelMap initialize(final String step, final Locale locale) throws Exception {
        return this.helperMap.get(step).initialize(locale);
    }

    void initializeBinder(final String step, final DataBinder binder) {
        this.helperMap.get(step).initializeBinder(binder);
    }

    void initializeGlobalBinder(final DataBinder binder) {
        for (StepHelper stepHelper : this.helperMap.values()) {
            stepHelper.initializeBinder(binder);
        }
    }

    boolean isValidationEnabled(final String step) {
        return this.helperMap.get(step).isValidationEnabled();
    }
}
