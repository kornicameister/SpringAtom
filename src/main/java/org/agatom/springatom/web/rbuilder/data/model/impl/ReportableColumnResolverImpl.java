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

package org.agatom.springatom.web.rbuilder.data.model.impl;

import com.google.common.collect.Sets;
import com.rits.cloning.Cloner;
import org.agatom.springatom.server.model.descriptors.EntityDescriptorCollectionColumn;
import org.agatom.springatom.server.model.descriptors.EntityDescriptorColumn;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.model.ReportableColumnResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Service(value = ReportableColumnResolverImpl.SERVICE_NAME)
public class ReportableColumnResolverImpl
        implements ReportableColumnResolver {
    private static final Logger             LOGGER             = Logger.getLogger(ReportableColumnResolverImpl.class);
    public static final  String             SERVICE_NAME       = "reportableColumnResolver";
    @Autowired
    private              EntityDescriptors  entityDescriptors  = null;
    private              Collection<String> excludedColumns    = null;
    @Value("#{rbuilderProperties['reports.objects.columns.exclude.names']}")
    private              String             rawExcludedColumns = null;
    @Value("#{webProperties['sa.delimiter']}")
    private              String             valueDelimiter     = null;
    @Autowired
    private              SMessageSource     messageSource      = null;


    @PostConstruct
    private void initializeExcludedColumns() {
        final String property = this.rawExcludedColumns;
        final Collection<String> excludedColumns = Sets.newHashSet();
        if (StringUtils.hasText(property)) {
            final String[] columns = StringUtils.tokenizeToStringArray(property, this.valueDelimiter);
            CollectionUtils.mergeArrayIntoCollection(columns, excludedColumns);
        }
        this.excludedColumns = excludedColumns;
        LOGGER.debug(String.format("Excluded columns set to %s", excludedColumns));
    }


    @Override
    public Set<RBuilderColumn> getReportableColumns(@NotNull final RBuilderEntity entity) {

        final Locale locale = LocaleContextHolder.getLocale();
        final TreeSet<RBuilderColumn> columns = Sets.newTreeSet();
        final Cloner cloner = new Cloner();

        for (EntityDescriptorColumn<?> column : this.entityDescriptors.getColumns(entity.getJavaClass())) {
            if (this.excludedColumns.contains(column.getName())) {
                LOGGER.trace(String.format("Excluded column %s from entity %s due it is mentioned in %s",
                        column.getName(),
                        entity.getName(), "reports.objects.columns.exclude.names")
                );
                continue;
            }
            final RBuilderColumn reportableColumn = new RBuilderColumn()
                    .setPrefix(entity.getMessageKey())
                    .setColumnName(column.getName())
                    .setColumnClass(column.getColumnClass());
            if (ClassUtils.isAssignable(EntityDescriptorCollectionColumn.class, column.getClass())) {
                reportableColumn.setElementClass(((EntityDescriptorCollectionColumn) column).getElementClass());
            }
            columns.add(cloner.shallowClone(this.messageSource.localize(reportableColumn, locale)));
        }

        LOGGER.trace(String
                .format("Available %s for entity %s at count %d", ClassUtils.getShortName(RBuilderColumn.class),
                        entity.getName(), columns
                        .size())
        );
        return columns;
    }

}
