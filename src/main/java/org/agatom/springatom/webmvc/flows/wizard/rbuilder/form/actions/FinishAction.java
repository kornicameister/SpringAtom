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

package org.agatom.springatom.webmvc.flows.wizard.rbuilder.form.actions;

import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.repository.repositories.report.SReportRepository;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.webmvc.flows.wizard.rbuilder.exception.ReportBuilderServiceException;
import org.agatom.springatom.webmvc.flows.wizard.rbuilder.form.ReportWizard;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.Locale;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Lazy
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component(value = "finishReportAction")
public class FinishAction
        extends MultiAction {

    private static final Logger LOGGER = Logger.getLogger(FinishAction.class);
    @Autowired
    @Qualifier(SReportRepository.REPO_NAME)
    private SReportRepository reportRepository;
    @Autowired
    private SMessageSource    messageSource;
    @Autowired
    private ReportWizard      reportWizard;

    public Event saveReport(final RequestContext flowContext) {
        LOGGER.debug(String.format("/saveReport reportWizard=%s,context=%s", this, flowContext));

        final MessageContext messageContext = flowContext.getMessageContext();
        final Locale locale = LocaleContextHolder.getLocale();
        final EventFactorySupport eventFactorySupport = new EventFactorySupport();

        try {
            final SReport report = this.reportRepository.save(this.reportWizard.getReportForSave());
            if (report != null && !report.isNew()) {
                LOGGER.info(String.format("Persisted new report => %s", report));
                messageContext.addMessage(new MessageBuilder()
                        .info()
                        .source(report)
                        .defaultText(
                                this.messageSource.getMessage(
                                        "wizard.NewReportWizard.info.s",
                                        new Object[]{report.getName()},
                                        locale
                                )
                        )
                        .build()
                );
            }
            this.reportWizard.setSavedReport(report);
            return eventFactorySupport.success(this, report);
        } catch (Exception persistException) {
            LOGGER.fatal(persistException);
            return eventFactorySupport.error(this, new ReportBuilderServiceException(persistException));
        }
    }

    public Event saveAndGenerateReport(final RequestContext flowContext) {
        return this.saveReport(flowContext);
    }

}
