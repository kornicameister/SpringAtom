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

import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.web.flows.wizards.wizard.rbuilder.ReportWizard;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.ClassUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.RequestContext;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract class ReportWizardFormAction
        extends FormAction {

    private static final Logger                             LOGGER                   = Logger.getLogger(ReportWizardFormAction.class);
    private static final Class<FormattingConversionService> CONVERSION_SERVICE_CLASS = FormattingConversionService.class;
    private static final Class<LocalValidatorFactoryBean>   VALIDATOR_CLASS          = LocalValidatorFactoryBean.class;
    protected FormattingConversionService conversionService;
    protected LocalValidatorFactoryBean   delegatedValidator;
    protected ReportWizard                reportWizard;
    @Autowired
    protected ApplicationContext          applicationContext;

    protected ReportWizardFormAction() {
        this.setFormObjectClass(SReport.class);
        this.setFormObjectName(SReport.ENTITY_NAME);
    }

    public final void init(final ReportWizard reportWizard) {
        this.reportWizard = reportWizard;
        this.delegatedValidator = this.applicationContext.getBean(VALIDATOR_CLASS);
        this.conversionService = this.applicationContext.getBean(CONVERSION_SERVICE_CLASS);
        LOGGER.debug(String.format("%s - created for reportWizard=%s", ClassUtils.getShortName(this.getClass()), reportWizard));
    }

    @Override
    protected void initBinder(final RequestContext context, final DataBinder binder) {
        LOGGER.trace("initBinder not set...setting");

        binder.setIgnoreUnknownFields(true);
        binder.setAutoGrowNestedPaths(true);
        binder.setConversionService(this.conversionService);
        binder.setValidator(this.delegatedValidator);

        this.doInitBinder(((WebDataBinder) binder), this.conversionService);
    }

    @Override
    protected Object getFormObject(final RequestContext context) throws Exception {
        return this.reportWizard.getReport();
    }

    protected abstract WebDataBinder doInitBinder(final WebDataBinder binder, final FormattingConversionService conversionService);

    protected abstract static class MatcherConverter
            implements ConditionalConverter {
    }
}
