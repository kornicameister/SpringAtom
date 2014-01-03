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

package org.agatom.springatom.web.flows.wizards.wizard.rbuilder.actions;

import com.google.common.base.Preconditions;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.types.report.Report;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component(value = "reportDescriptionFormAction")
public class ReportDescriptionFormAction
        extends ReportWizardFormAction {

    public ReportDescriptionFormAction() {
        super();
        this.setValidator(new AreDescriptionPropertiesValidValidator());
    }

    @Override
    public Event setupForm(final RequestContext context) throws Exception {
        return super.setupForm(context);
    }

    @Override
    protected WebDataBinder doInitBinder(final WebDataBinder binder, final FormattingConversionService conversionService) {
        binder.setRequiredFields(SReport.Columns.NAME.getName(), SReport.Columns.DESCRIPTION.getName());
        return binder;
    }

    @Override
    public Event resetForm(final RequestContext context) throws Exception {
        final SReport report = this.reportWizard.getReport();
        report.setName(null);
        report.setDescription(null);
        return success();
    }

    private class AreDescriptionPropertiesValidValidator
            implements Validator {

        @Override
        public boolean supports(final Class<?> clazz) {
            return ClassUtils.isAssignable(SReport.class, clazz);
        }

        @Override
        public void validate(final Object target, final Errors errors) {
            Preconditions.checkNotNull(target, "Target must not be null");
            Preconditions.checkArgument(ClassUtils.isAssignable(Report.class, target.getClass()));
            delegatedValidator.validate(target, errors);
        }
    }
}
