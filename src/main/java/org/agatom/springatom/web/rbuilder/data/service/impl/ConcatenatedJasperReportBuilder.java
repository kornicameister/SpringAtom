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

package org.agatom.springatom.web.rbuilder.data.service.impl;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.*;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.service.domain.SUserService;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.exception.ReportGenerationException;
import org.agatom.springatom.web.rbuilder.data.service.ReportJasperBuilderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.validation.constraints.NotNull;
import java.util.Properties;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service("concatenatedReportsBuilder")
public class ConcatenatedJasperReportBuilder
        implements ReportJasperBuilderService {

    private static final Logger LOGGER                                  = Logger.getLogger(ConcatenatedJasperReportBuilder.class);
    private static final String REPORTS_DOCUMENT_META_MARGIN            = "reports.document.meta.margin";
    private static final String REPORTS_DOCUMENT_META_DETAIL_HEIGHT     = "reports.document.meta.detailHeight";
    private static final String REPORTS_DOCUMENT_META_IGNORE_PAGINATION = "reports.document.meta.ignorePagination";
    private static final String REPORTS_DOCUMENT_META_FULL_PAGE_WIDTH   = "reports.document.meta.useFullPageWidth";
    private static final String REPORTS_DOCUMENT_META_SHOW_COL_NAMES    = "reports.document.meta.printColumnNames";

    @Autowired(required = false)
    private SUserService      userService;
    @Autowired(required = false)
    private EntityDescriptors entityDescriptors;
    @Autowired(required = false)
    @Qualifier(value = "rbuilderProperties")
    private Properties propertiesHolder = null;

    @Override
    public JasperReport generateReport(@NotNull final Report report, @NotNull final RBuilderEntity entity) throws ReportGenerationException {
        LOGGER.trace(String.format("Generating report[%s] from pair=[\n\treport->%s\n\tentity->%s",
                ClassUtils.getShortName(DynamicReport.class),
                report,
                entity)
        );
        final DynamicReport dynamicReport;
        try {
            dynamicReport = this.buildReport(report, entity);
            return DynamicJasperHelper.generateJasperReport(dynamicReport, new ClassicLayoutManager(), Maps.newHashMap());
        } catch (DJBuilderException | ColumnBuilderException | JRException exception) {
            final String message = String.format("Failed to generate report from %s", report);
            LOGGER.error(message, exception);
            throw new ReportGenerationException(message, Throwables.getRootCause(exception));
        }
    }

    private DynamicReport buildReport(@NotNull final Report report, @NotNull final RBuilderEntity entity) {
        LOGGER.trace(String.format("Building report instance of %s from pair=[\n\treport->%s\n\tentities->%s",
                ClassUtils.getShortName(FastReportBuilder.class),
                report,
                entity)
        );

        final Integer margin = Integer.valueOf(this.propertiesHolder.getProperty(REPORTS_DOCUMENT_META_MARGIN));
        final Integer detailHeight = Integer.valueOf(this.propertiesHolder.getProperty(REPORTS_DOCUMENT_META_DETAIL_HEIGHT));
        final FastReportBuilder builder = new FastReportBuilder();

        builder.setIgnorePagination(Boolean.valueOf(this.propertiesHolder.getProperty(REPORTS_DOCUMENT_META_IGNORE_PAGINATION)))
               .setUseFullPageWidth(Boolean.valueOf(this.propertiesHolder.getProperty(REPORTS_DOCUMENT_META_FULL_PAGE_WIDTH)))
               .setPrintColumnNames(Boolean.valueOf(this.propertiesHolder.getProperty(REPORTS_DOCUMENT_META_SHOW_COL_NAMES)))
               .setMargins(margin, margin, margin, margin)
               .setDetailHeight(detailHeight)
               .setWhenNoDataAllSectionNoDetail()
               .setReportName(String.format("%s - %s", report.getTitle(), this.userService.getAuthenticatedUser().getUsername()))
               .setReportLocale(LocaleContextHolder.getLocale())
               .setTitle(report.getTitle())
               .setSubtitle(report.getSubtitle())
               .addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_CENTER);

        for (final RBuilderColumn column : entity.getColumns()) {

            if (this.isReportColumn(column)) {

            } else {
                LOGGER.trace(String.format("Building column [%s]", column.getColumnName()));

                final AbstractColumn ac = ColumnBuilder.getNew()
                                                       .setColumnProperty(column.getColumnName(), column.getRenderClass())
                                                       .setTitle(column.getLabel())
                                                       .setWidth(50)
                                                       .build();

                builder.addColumn(ac);
                LOGGER.trace(String.format("Built column [%s]", column.getColumnName()));

                if (this.isGroupingColumn(entity, column)) {
                    LOGGER.trace(String.format("Column [%s] is grouping capable", column.getColumnName()));
                    final GroupBuilder groupBuilder = new GroupBuilder();

                    groupBuilder.setCriteriaColumn((PropertyColumn) ac)
                                .setGroupLayout(GroupLayout.DEFAULT_WITH_HEADER);

                    builder.addGroup(groupBuilder.build());
                }

            }
        }

        return builder.build();
    }

    private boolean isReportColumn(final RBuilderColumn column) {
        return ClassUtils.isAssignable(Report.class, column.getColumnClass());
    }

    private boolean isGroupingColumn(final RBuilderEntity entity, final RBuilderColumn column) {
        final EntityDescriptor<?> descriptor = this.entityDescriptors.getDescriptor(entity.getJavaClass());
        if (descriptor != null) {
            final EntityType<?> entityType = descriptor.getEntityType();
            final Attribute<?, ?> attribute = entityType.getAttribute(column.getColumnName());
            return attribute != null && ClassUtils.isAssignable(Enum.class, attribute.getJavaType());
        }
        return false;
    }
}
