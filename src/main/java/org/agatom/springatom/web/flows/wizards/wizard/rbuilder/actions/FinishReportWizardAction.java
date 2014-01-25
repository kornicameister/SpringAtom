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

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.web.flows.wizards.actions.WizardAction;
import org.agatom.springatom.web.flows.wizards.wizard.rbuilder.ReportWizard;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.exception.ReportBuilderServiceException;
import org.agatom.springatom.web.rbuilder.data.service.ReportBuilderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * {@code FinishMultiAction} is an action called when submitting the final step of {@code flow id=wizard/NewReportWizard}.
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@WizardAction(value = "finishReportWizardAction")
public class FinishReportWizardAction
        implements Action {
    private static final Logger LOGGER = Logger.getLogger(FinishReportWizardAction.class);

    @Autowired
    private ReportWizard         reportWizard;
    @Autowired
    private ReportBuilderService builderService;

    @Override
    public Event execute(final RequestContext context) throws Exception {
        LOGGER.debug(String.format("/saveReport reportWizard=%s,context=%s", this, context));
        final EventFactorySupport eventFactorySupport = new EventFactorySupport();
        try {
            final ReportConfiguration reportConfiguration = this.removeExcludedColumns(this.reportWizard.getReportConfiguration());
            final Object reports = this.builderService.newReportInstance(reportConfiguration);
            LOGGER.info(String.format("Persisted new report => %s", reports));
            return eventFactorySupport.success(this, reports);
        } catch (Exception persistException) {
            LOGGER.fatal(persistException);
            return eventFactorySupport.error(this, new ReportBuilderServiceException(persistException));
        }
    }

    /**
     * Runs trough all {@code entities} and subsequent {@code columns} picking up these columns where {@link
     * org.agatom.springatom.web.rbuilder.bean.RBuilderColumnOptions#excluded} is {@link java.lang.Boolean#TRUE}.
     * After lookup removes these columns from final list submitted to {@link org.agatom.springatom.web.rbuilder.data.service.ReportBuilderService}
     *
     * @param cfg
     *         reportConfiguration to be used
     *
     * @return updated reportConfiguration
     *
     * @throws Exception
     *         in case if particular entity has no columns set
     */
    private ReportConfiguration removeExcludedColumns(final ReportConfiguration cfg) throws Exception {
        Assert.notNull(cfg);
        for (RBuilderEntity rBuilderEntity : cfg.getEntities()) {
            if (rBuilderEntity.hasColumns()) {
                Set<RBuilderColumn> columns = FluentIterable
                        .from(rBuilderEntity.getColumns())
                        .filter(new Predicate<RBuilderColumn>() {
                            @Override
                            public boolean apply(@Nullable final RBuilderColumn input) {
                                assert input != null;
                                return input.getOptions().isExcluded();
                            }
                        }).toSet();
                if (!columns.isEmpty()) {
                    LOGGER.debug(String.format("%s contains %d columns which were marked as excluded", rBuilderEntity.getName(), columns.size()));
                    cfg.popColumns(rBuilderEntity, columns);
                }
            } else {
                throw new Exception(String.format("No columns in entity %s - that should not happen", rBuilderEntity.getName()));
            }
        }
        return cfg;
    }
}
