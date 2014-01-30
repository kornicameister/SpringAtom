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

package org.agatom.springatom.web.flows.wizards.wizard.rbuilder;

import com.google.common.base.Preconditions;
import org.agatom.springatom.web.flows.wizards.actions.WizardAction;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@WizardAction(value = "reportDescriptionFormAction")
public class ReportDescriptionFormAction
        extends ReportWizardFormAction<ReportConfiguration> {

    public ReportDescriptionFormAction() {
        super();
        this.setValidator(new AreDescriptionPropertiesValidValidator());
    }

    @Override
    protected WebDataBinder doInitBinder(final WebDataBinder binder, final FormattingConversionService conversionService) {
        binder.setIgnoreUnknownFields(true);
        return binder;
    }

    private class AreDescriptionPropertiesValidValidator
            implements Validator {

        @Override
        public boolean supports(final Class<?> clazz) {
            return ClassUtils.isAssignable(ReportConfiguration.class, clazz);
        }

        @Override
        public void validate(final Object target, final Errors errors) {
            Preconditions.checkNotNull(target, "Target must not be null");
            Preconditions.checkArgument(ClassUtils.isAssignable(ReportConfiguration.class, target.getClass()));
            delegatedValidator.validate(target, errors);
        }
    }
}
