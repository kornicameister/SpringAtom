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

package org.agatom.springatom.web.flows.wizards.wizard.rbuilder;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.descriptors.EntityDescriptorCollectionColumn;
import org.agatom.springatom.server.model.descriptors.EntityDescriptorColumn;
import org.agatom.springatom.server.model.descriptors.SlimEntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.web.flows.wizards.wizard.AbstractWizard;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.bean.RBuilderBean;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.exception.ReportBuilderServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Identifiable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.webflow.execution.RequestContext;

import javax.annotation.Nullable;
import java.util.*;

/**
 * {@code ReportDescriptor} is a bean describing the report structure and reusable values
 * used throughout the flow.
 *
 * @author kornicameister
 * @version 0.0.1
 * @see org.springframework.webflow.engine.Flow
 * @since 0.0.1
 */

@Lazy
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Scope(WebApplicationContext.SCOPE_SESSION)
@Component(value = "reportWizard")
public class ReportWizard
        extends AbstractWizard
        implements Iterable<RBuilderEntity> {
    private static final Logger                                   LOGGER              = Logger.getLogger(ReportWizard.class);
    private static final String                                   BEAN_ID             = "reportDescriptor";
    private static final long                                     serialVersionUID    = -8974230926174261438L;
    private              Set<RBuilderEntity>                      entities            = Sets.newTreeSet();
    private              Map<RBuilderEntity, Set<RBuilderColumn>> entityToColumns     = Maps.newTreeMap();
    private              Map<Integer, Identifiable<Integer>>      cache               = Maps.newHashMap();
    @Autowired
    private              EntityDescriptors                        entityDescriptors   = null;
    @Autowired
    private              SMessageSource                           messageSource       = null;
    @Autowired @Qualifier(value = "rbuilderProperties")
    private              Properties                               rBuilderProperties  = null;
    @Value("#{webProperties['sa.delimiter']}")
    private              String                                   valueDelimiter      = null;
    private              ReportConfiguration                      reportConfiguration = null;
    private              Collection<String>                       excludedColumns     = null;

    @Override
    public void init(final RequestContext context) {
        super.init(context);
        this.reportConfiguration = null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.createdBy = this.getCreator();
        this.initializeExcludedColumns();
        this.setEntities(this.getReportableEntities());
        this.setEntityToColumns(this.getReportableColumns(this.entities));
    }

    public ReportConfiguration getReportConfiguration() {
        if (this.reportConfiguration == null) {
            this.reportConfiguration = new ReportConfiguration();
        }
        return this.reportConfiguration;
    }

    public Set<RBuilderEntity> getEntities() {
        return this.entities;
    }

    public ReportWizard setEntities(final Set<RBuilderEntity> entities) {
        this.entities.addAll(entities);
        this.putToCache(entities);
        return this;
    }

    public Map<RBuilderEntity, Set<RBuilderColumn>> getEntityToColumnForReport() {
        final Map<RBuilderEntity, Set<RBuilderColumn>> picked = Maps.newTreeMap();
        final int size = this.reportConfiguration.getSize();
        int flag = 0;
        for (final RBuilderEntity entity : this.entities) {
            if (this.reportConfiguration.hasEntity(entity.getJavaClass())) {
                picked.put(entity, this.entityToColumns.get(entity));
                this.reportConfiguration.putColumns(entity, picked.get(entity));
                flag++;
            }
            if (flag == size) {
                break;
            }
        }
        return picked;
    }

    public RBuilderEntity getEntity(final Class<?> clazz) {
        final Optional<RBuilderEntity> firstMatch = FluentIterable.from(this.entities).firstMatch(new Predicate<RBuilderEntity>() {
            @Override
            public boolean apply(@Nullable final RBuilderEntity input) {
                return input != null && input.getJavaClass().equals(clazz);
            }
        });
        return firstMatch.isPresent() ? firstMatch.get() : null;
    }

    public RBuilderBean getReportableBean(final Integer id) {
        return (RBuilderBean) this.cache.get(id);
    }

    private <X extends Identifiable<Integer>> void putToCache(final Collection<X> entities) {
        for (final Identifiable<Integer> identifiable : entities) {
            if (this.cache.put(identifiable.getId(), identifiable) != null) {
                throw new RuntimeException(String.format("Overridden values in cache detected...bad situation"));
            }
        }
    }

    private ReportWizard setEntityToColumns(final Map<RBuilderEntity, Set<RBuilderColumn>> entityToColumns) {
        this.entityToColumns.putAll(entityToColumns);
        for (final Set<RBuilderColumn> columns : entityToColumns.values()) {
            this.putToCache(columns);
        }
        return this;
    }

    private Set<RBuilderEntity> getReportableEntities() {
        final Set<RBuilderEntity> entities = Sets.newTreeSet();
        final Locale locale = LocaleContextHolder.getLocale();
        final Set<SlimEntityDescriptor<?>> descriptors = FluentIterable
                .from(this.entityDescriptors.getSlimDescriptors())
                .filter(new Predicate<SlimEntityDescriptor<?>>() {
                    @Override
                    public boolean apply(@Nullable final SlimEntityDescriptor<?> input) {
                        return input != null && input.getJavaClass()
                                                     .isAnnotationPresent(org.agatom.springatom.server.model.types.ReportableEntity.class);
                    }
                })
                .toSet();
        for (SlimEntityDescriptor<?> descriptor : descriptors) {
            entities.add(this.messageSource
                    .localize(new RBuilderEntity().setJavaClass(descriptor.getJavaClass()).setName(descriptor.getName()), locale)
            );
        }
        LOGGER.trace(String.format("Available %s at count %d", ClassUtils.getShortName(RBuilderEntity.class), entities.size()));
        return entities;
    }

    private Map<RBuilderEntity, Set<RBuilderColumn>> getReportableColumns(final Set<RBuilderEntity> entities) {

        final Map<RBuilderEntity, Set<RBuilderColumn>> map = Maps.newTreeMap();
        final Locale locale = LocaleContextHolder.getLocale();

        for (final RBuilderEntity entity : entities) {

            final TreeSet<RBuilderColumn> columns = Sets.newTreeSet();
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
                columns.add(this.messageSource.localize(reportableColumn, locale));
            }

            LOGGER.trace(String
                    .format("Available %s for entity %s at count %d", ClassUtils.getShortName(RBuilderColumn.class),
                            entity.getName(), columns
                            .size())
            );

            map.put(entity, columns);
        }
        return map;
    }

    private SUser getCreator() throws ReportBuilderServiceException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();
        if (principal instanceof SUser) {
            return (SUser) principal;
        }
        throw new ReportBuilderServiceException(SUser.class, String.format("Principal\n\t[%s]\nis not authenticated", principal));
    }

    private void initializeExcludedColumns() {
        final String property = this.rBuilderProperties.getProperty("reports.objects.columns.exclude.names");
        final Collection<String> excludedColumns = Sets.newHashSet();
        if (StringUtils.hasText(property)) {
            final String[] columns = StringUtils.tokenizeToStringArray(property, this.valueDelimiter);
            CollectionUtils.mergeArrayIntoCollection(columns, excludedColumns);
        }
        this.excludedColumns = excludedColumns;
        LOGGER.debug(String.format("Excluded columns set to %s", excludedColumns));
    }

    @Override
    public String getBeanId() {
        return BEAN_ID;
    }

    @Override
    public Iterator<RBuilderEntity> iterator() {
        return this.getEntities().iterator();
    }

    public void reset() {
        this.reportConfiguration = null;
    }
}
