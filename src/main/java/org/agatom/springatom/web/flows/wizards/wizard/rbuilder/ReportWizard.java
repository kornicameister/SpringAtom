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
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.descriptors.EntityDescriptorColumn;
import org.agatom.springatom.server.model.descriptors.SlimEntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.web.flows.wizards.wizard.AbstractWizard;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.rbuilder.bean.ReportableBean;
import org.agatom.springatom.web.rbuilder.bean.ReportableColumn;
import org.agatom.springatom.web.rbuilder.bean.ReportableEntity;
import org.agatom.springatom.web.rbuilder.exception.ReportBuilderServiceException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Identifiable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
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
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component(value = "reportWizard")
public class ReportWizard
        extends AbstractWizard
        implements Iterable<ReportableEntity> {
    private static final Logger                                       LOGGER            = Logger.getLogger(ReportWizard.class);
    private static final String                                       BEAN_ID           = "reportDescriptor";
    private              Set<ReportableEntity>                        entities          = Sets.newTreeSet();
    private              Map<ReportableEntity, Set<ReportableColumn>> entityToColumns   = Maps.newTreeMap();
    private              Map<Integer, Identifiable<Integer>>          cache             = Maps.newHashMap();
    private              SReport                                      report            = null;
    @Autowired
    private              EntityDescriptors                            entityDescriptors = null;
    @Autowired
    private              SMessageSource                               messageSource     = null;

    @Override
    public void init(final RequestContext context) {
        super.init(context);
        this.report = null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.createdBy = this.getCreator();
        this.setEntities(this.getReportableEntities());
        this.setEntityToColumns(this.getReportableColumns(this.entities));
    }

    public SReport getReport() {
        if (this.report == null) {
            this.report = new SReport();
            this.report.setCreatedBy(this.createdBy);
            this.report.setCreatedAt(DateTime.now());
            this.report.setCreatedDate(DateTime.now());
        }
        return report;
    }

    public SReport getReportForSave() {
        this.report.setLastModifiedBy(this.createdBy);
        this.report.setLastModifiedDate(DateTime.now());
        return this.report;
    }

    public Set<ReportableEntity> getEntities() {
        return this.entities;
    }

    public ReportWizard setEntities(final Set<ReportableEntity> entities) {
        this.entities.addAll(entities);
        this.putToCache(entities);
        return this;
    }

    public Map<ReportableEntity, Set<ReportableColumn>> getEntityToColumnForReport() {
        final Map<ReportableEntity, Set<ReportableColumn>> picked = Maps.newTreeMap();
        final int size = this.report.getEntities().size();
        int flag = 0;
        for (final ReportableEntity entity : this.entities) {
            if (this.report.hasEntity(entity.getJavaClass())) {
                picked.put(entity, this.entityToColumns.get(entity));
                flag++;
            }
            if (flag == size) {
                break;
            }
        }
        return picked;
    }

    public ReportableEntity getEntity(final Class<?> clazz) {
        final Optional<ReportableEntity> firstMatch = FluentIterable.from(this.entities).firstMatch(new Predicate<ReportableEntity>() {
            @Override
            public boolean apply(@Nullable final ReportableEntity input) {
                return input != null && input.getJavaClass().equals(clazz);
            }
        });
        return firstMatch.isPresent() ? firstMatch.get() : null;
    }

    public ReportableBean getReportableBean(final Integer id) {
        return (ReportableBean) this.cache.get(id);
    }


    private <X extends Identifiable<Integer>> void putToCache(final Collection<X> entities) {
        for (final Identifiable<Integer> identifiable : entities) {
            this.cache.put(identifiable.getId(), identifiable);
        }
    }

    private ReportWizard setEntityToColumns(final Map<ReportableEntity, Set<ReportableColumn>> entityToColumns) {
        this.entityToColumns.putAll(entityToColumns);
        for (final Set<ReportableColumn> columns : entityToColumns.values()) {
            this.putToCache(columns);
        }
        return this;
    }

    private Set<ReportableEntity> getReportableEntities() {
        final Set<ReportableEntity> entities = Sets.newTreeSet();
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
                    .localize(new ReportableEntity().setJavaClass(descriptor.getJavaClass()).setName(descriptor.getName()), locale)
            );
        }
        LOGGER.trace(String.format("Available %s at count %d", ClassUtils.getShortName(ReportableEntity.class), entities.size()));
        return entities;
    }

    private Map<ReportableEntity, Set<ReportableColumn>> getReportableColumns(final Set<ReportableEntity> entities) {

        final Map<ReportableEntity, Set<ReportableColumn>> map = Maps.newTreeMap();
        final Locale locale = LocaleContextHolder.getLocale();

        for (final ReportableEntity entity : entities) {

            final TreeSet<ReportableColumn> columns = Sets.newTreeSet();
            for (EntityDescriptorColumn<?> column : this.entityDescriptors.getColumns(entity.getJavaClass())) {
                final ReportableColumn reportableColumn = new ReportableColumn()
                        .setEntity(entity)
                        .setColumnName(column.getName())
                        .setColumnClass(column.getColumnClass());
                columns.add(this.messageSource.localize(reportableColumn, locale));
            }

            LOGGER.trace(String
                    .format("Available %s for entity %s at count %d", ClassUtils.getShortName(ReportableColumn.class),
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

    @Override
    public String getBeanId() {
        return BEAN_ID;
    }

    @Override
    public Iterator<ReportableEntity> iterator() {
        return this.getEntities().iterator();
    }

    public ReportWizard setSavedReport(final SReport report) {
        Preconditions.checkNotNull(report);
        Preconditions.checkArgument(!report.isNew());
        this.report = report;
        return this;
    }
}
