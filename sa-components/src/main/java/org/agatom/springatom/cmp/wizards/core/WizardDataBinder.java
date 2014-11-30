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

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.web.bind.WebDataBinder;

/**
 * {@code WizardDataBinder} is customized {@link org.springframework.web.bind.WebDataBinder}
 * that is aware of updating {@link #requiredFields} and {@link #allowedFields} instead of replacing them.
 * It is crucial to update due to binder is initialized in the loop via {@link org.agatom.springatom.cmp.wizards.core.StepHelperDelegate}
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-31</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class WizardDataBinder
        extends WebDataBinder {

    WizardDataBinder(final Object target, final String objectName) {
        super(target, objectName);
    }

    @Override
    public void setAllowedFields(final String... allowedFields) {
        final String[] oldFields = this.getAllowedFields();
        String[] newFields = PropertyAccessorUtils.canonicalPropertyNames(allowedFields);
        if (this.areFieldsSet(oldFields)) {
            // update
            newFields = ArrayUtils.addAll(oldFields, newFields);
        }
        super.setAllowedFields(newFields);
    }

    @Override
    public void setRequiredFields(final String... requiredFields) {
        final String[] oldFields = this.getRequiredFields();
        String[] newFields = PropertyAccessorUtils.canonicalPropertyNames(requiredFields);
        if (this.areFieldsSet(oldFields)) {
            // update
            newFields = ArrayUtils.addAll(oldFields, newFields);
        }
        super.setRequiredFields(newFields);
    }

    private boolean areFieldsSet(final String[] fields) {
        return fields != null && fields.length > 0;
    }
}
