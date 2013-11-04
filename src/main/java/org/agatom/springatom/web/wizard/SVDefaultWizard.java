/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
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

package org.agatom.springatom.web.wizard;

import com.google.common.base.Objects;
import org.agatom.springatom.web.wizard.util.SVWizardHelper;
import org.agatom.springatom.web.wizard.util.SVWizardModelVariables;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
abstract public class SVDefaultWizard
        implements SVWizard {
    private static final long serialVersionUID = -3857823814215380400L;
    protected final String wizardId;
    protected final String viewName;
    protected final String wizardName;

    protected SVDefaultWizard(final String cmpName, final String viewName) {
        this.wizardId = SVWizardHelper.generateWizardId(this.getClass().getName());
        this.wizardName = cmpName;
        this.viewName = viewName;
    }

    @Override
    public String getId() {
        return this.wizardId;
    }

    @Override
    public String getName() {
        return this.wizardName;
    }

    @Override
    public String getViewName() {
        return this.viewName;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public String getView(final ModelMap modelMap) {
        modelMap.put(SVWizardModelVariables.WIZARD_ID, this.wizardId);
        modelMap.put(SVWizardModelVariables.WIZARD_NAME, this.wizardName);
        modelMap.put(SVWizardModelVariables.WIZARD_SUBMIT_URL, this.getWizardSubmitUrl());
        return this.viewName;
    }

    protected abstract String getWizardSubmitUrl();

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(wizardId)
                      .addValue(wizardName)
                      .toString();
    }
}
