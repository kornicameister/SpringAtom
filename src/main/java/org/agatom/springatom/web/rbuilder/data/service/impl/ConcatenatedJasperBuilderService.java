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
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.*;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.service.domain.SUserService;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.exception.ReportGenerationException;
import org.agatom.springatom.web.rbuilder.data.service.JasperBuilderService;
import org.agatom.springatom.web.rbuilder.support.PropertiesConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * {@linkplain ConcatenatedJasperBuilderService} is {@link org.agatom.springatom.web.rbuilder.data.service.JasperBuilderService}
 * designated to generate {@link net.sf.jasperreports.engine.JasperReport} with {@code concatenated} sub reports if any
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@Service("concatenatedReportsBuilder")
public class ConcatenatedJasperBuilderService
        implements JasperBuilderService {

    private static final Logger LOGGER = Logger.getLogger(ConcatenatedJasperBuilderService.class);

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

    @Override
    public Set<RBuilderColumn> getGroupByColumns(final Set<RBuilderColumn> columns) {
        return FluentIterable
                .from(columns)
                .filter(new Predicate<RBuilderColumn>() {
                    @Override
                    public boolean apply(@Nullable final RBuilderColumn input) {
                        final boolean groupingColumn = isGroupingColumn(input);
                        if (groupingColumn) {
                            assert input != null;
                            LOGGER.trace(String.format("Column [%s] is grouping capable", input.getColumnName()));
                        }
                        return groupingColumn;
                    }
                })
                .toSet();
    }

    private DynamicReport buildReport(@NotNull final Report report, @NotNull final RBuilderEntity entity) {
        LOGGER.trace(String.format("Building report instance of %s from pair=[\n\treport->%s\n\tentities->%s",
                ClassUtils.getShortName(FastReportBuilder.class),
                report,
                entity)
        );

        final Integer margin = Integer.valueOf(this.propertiesHolder.getProperty(PropertiesConstants.REPORTS_DOCUMENT_META_MARGIN));
        final Integer detailHeight = Integer.valueOf(this.propertiesHolder.getProperty(PropertiesConstants.REPORTS_DOCUMENT_META_DETAIL_HEIGHT));
        final DynamicReportBuilder builder = new SafeReportBuilder();

        builder.setTitle(report.getTitle())
               .setSubtitle(report.getSubtitle()).setMargins(margin, margin, margin, margin)
               .setReportName(String.format("%s - %s", report.getTitle(), this.userService.getAuthenticatedUser().getUsername()))
               .setReportLocale(LocaleContextHolder.getLocale())
               .setDetailHeight(detailHeight)
               .setWhenNoDataAllSectionNoDetail()
               .addAutoText("SpringAtom", AutoText.POSITION_HEADER, AutoText.ALIGNMENT_CENTER)
               .addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_CENTER)
               .addAutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT, AutoText.PATTERN_DATE_DATE_TIME);

        final Set<RBuilderColumn> columns = entity.getColumns();
        final Set<RBuilderColumn> groupByColumns = this.getGroupByColumns(columns);
        final Map<RBuilderColumn, AbstractColumn> reportColumns = Maps.newHashMap();

        for (final RBuilderColumn column : columns) {

            if (this.isExcludedColumn(column)) {
                LOGGER.trace(String.format("Columns %s is excluded from being shown in report", column.getColumnName()));
                continue;
            }

            if (this.isReportColumn(column)) {
                LOGGER.warn("Not implemented at the moment");
            } else {
                LOGGER.trace(String.format("Building column [%s]", column.getColumnName()));
                final AbstractColumn abstractColumn = this.buildColumn(column, builder);
                {
                    reportColumns.put(column, abstractColumn);
                    builder.addColumn(abstractColumn);
                }
                LOGGER.trace(String.format("Built column [%s]", column.getColumnName()));
            }
        }

        if (!groupByColumns.isEmpty()) {
            final Style groupLabelStyle = new Style("groupLabel");
            groupLabelStyle.setVerticalAlign(VerticalAlign.BOTTOM);
            for (RBuilderColumn groupByColumn : groupByColumns) {
                final AbstractColumn abstractColumn = reportColumns.get(groupByColumn);
                if (ClassUtils.isAssignable(PropertyColumn.class, abstractColumn.getClass())) {
                    final DJGroup djGroup = new GroupBuilder().setCriteriaColumn((PropertyColumn) abstractColumn)
                                                              .setGroupLayout(GroupLayout.VALUE_IN_HEADER)
                                                              .setReprintHeaderOnEachPage(Boolean.valueOf(this.propertiesHolder
                                                                      .getProperty(PropertiesConstants.REPORTS_COLUMN_GROUP_REPRINT_IN_NEW_PAGE)))
                                                              .setStartInNewPage(Boolean.valueOf(this.propertiesHolder
                                                                      .getProperty(PropertiesConstants.REPORTS_COLUMN_GROUP_START_IN_NEW_PAGE)))
                                                              .build();
                    builder.addGroup(djGroup);
                }
            }
        }

        this.styleBuilder(builder);

        return builder.build();
    }

    private void styleBuilder(final DynamicReportBuilder builder) {
        builder.setIgnorePagination(Boolean.valueOf(this.propertiesHolder.getProperty(PropertiesConstants.REPORTS_DOCUMENT_META_IGNORE_PAGINATION)))
               .setUseFullPageWidth(Boolean.valueOf(this.propertiesHolder.getProperty(PropertiesConstants.REPORTS_DOCUMENT_META_FULL_PAGE_WIDTH)))
               .setPrintColumnNames(Boolean.valueOf(this.propertiesHolder.getProperty(PropertiesConstants.REPORTS_DOCUMENT_META_SHOW_COL_NAMES)))
               .setAllowDetailSplit(Boolean.valueOf(this.propertiesHolder.getProperty(PropertiesConstants.REPORTS_DOCUMENT_META_ALLOW_DETAIL_SPLIT)))
               .setPrintBackgroundOnOddRows(Boolean
                       .valueOf(this.propertiesHolder.getProperty(PropertiesConstants.REPORTS_DOCUMENT_META_PRINT_ODD_ROWS)));
    }

    private AbstractColumn buildColumn(final RBuilderColumn column, final DynamicReportBuilder fbr) {
        LOGGER.trace(String.format("Building column instance for %s=%s", column.getColumnName(), column.getColumnClass().getSimpleName()));
        return new ColumnInBuilt().newColumn(column);
    }

    private boolean isExcludedColumn(final RBuilderColumn column) {
        return column != null && column.getOptions().isExcluded();
    }

    private boolean isReportColumn(final RBuilderColumn column) {
        return column != null && ClassUtils.isAssignable(Report.class, column.getColumnClass());
    }

    private boolean isGroupingColumn(final RBuilderColumn column) {
        return column != null && column.getOptions().isGroupBy();
    }

    private static class ColumnInBuilt {
        private final ColumnBuilder builder           = ColumnBuilder.getNew();
        private       boolean       isCollectionBased = false;

        public AbstractColumn newColumn(final RBuilderColumn column) {
            this.applyCommonProperties(column)
                .applyColumnsAsList(column)
                .applyStyle(column);
            return this.builder.build();
        }

        private ColumnInBuilt applyStyle(final RBuilderColumn column) {
            builder.setHeaderStyle(this.getHeaderStyle());
            return this;
        }

        private ColumnInBuilt applyColumnsAsList(final RBuilderColumn column) {
            final Class<?> renderClass = column.getRenderClass();
            if (this.isCollectionBased = ClassUtils.isAssignable(Collection.class, renderClass)) {
            }
            return this;
        }

        private ColumnInBuilt applyCommonProperties(final RBuilderColumn column) {
            this.builder.setColumnProperty(column.getColumnName(), column.getRenderClass())
                        .setTitle(column.getLabel())
                        .setWidth(50);
            return this;
        }

        private Style getHeaderStyle() {
            final Style headerStyle = new Style();
            headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
            headerStyle.setBorder(Border.PEN_2_POINT());
            headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
            headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
            headerStyle.setBackgroundColor(Color.darkGray);
            return headerStyle;
        }
    }
}
