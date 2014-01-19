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

package org.agatom.springatom.web.rbuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.beans.WebBean;
import org.agatom.springatom.web.rbuilder.bean.ReportableColumn;
import org.agatom.springatom.web.rbuilder.bean.ReportableEntity;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.springframework.binding.collection.MapAdaptable;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportConfiguration
        implements WebBean,
                   Iterable<ReportableEntity>,
                   MapAdaptable<String, ReportableEntity> {
    private static final String                    BEAN_ID          = "reportConfiguration";
    private static final Logger                    LOGGER           = Logger.getLogger(ReportConfiguration.class);
    private static final long                      serialVersionUID = 772657130362339934L;
    @NotNull
    @Length(min = 5, max = 50)
    protected            String                    title            = null;
    @Length(max = 50)
    protected            String                    subtitle         = null;
    @Length(max = 200)
    protected            String                    description      = null;
    protected            Map<String, Serializable> settings         = Maps.newHashMap();
    @NotNull
    @Size(min = 1)
    protected            Set<ReportableEntity>     entities         = Sets.newTreeSet();

    public ReportConfiguration setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ReportConfiguration setSubtitle(final String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public ReportConfiguration setDescription(final String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ReportConfiguration setSettings(final Map<String, Serializable> settings) {
        this.settings = settings;
        return this;
    }

    public Map<String, Serializable> getSettings() {
        if (this.settings == null) {
            this.settings = Maps.newHashMap();
        }
        return settings;
    }

    public ReportConfiguration setEntities(final Set<ReportableEntity> entities) {
        for (final ReportableEntity entity : entities) {
            this.putEntity(entity);
        }
        return this;
    }

    public boolean hasEntity(final ReportableEntity entity) {
        return this.entities.contains(entity);
    }

    public boolean hasEntity(final Class<?> javaClass) {
        return FluentIterable
                .from(this.entities)
                .firstMatch(new Predicate<ReportableEntity>() {
                    @Override
                    public boolean apply(@Nullable final ReportableEntity input) {
                        return input != null && input.getJavaClass().equals(javaClass);
                    }
                }).isPresent();
    }

    public boolean hasEntities() {
        return !this.entities.isEmpty();
    }

    public ReportConfiguration clearEntities() {
        this.clearColumns();
        this.entities.clear();
        return this;
    }

    public List<ReportableEntity> getEntities() {
        return ImmutableList.copyOf(this.entities);
    }

    public ReportConfiguration putEntity(final ReportableEntity entity) {
        final boolean add = this.entities.add(entity);
        if (!add) {
            LOGGER.trace(String.format("%s already exists in context", entity));
        }
        return this;
    }

    public ReportConfiguration putColumn(final ReportableEntity entity, final ReportableColumn reportableColumn) {
        this.putEntity(entity);
        entity.addColumn(reportableColumn);
        return this;
    }

    public ReportConfiguration putColumns(final ReportableEntity entity, final Collection<ReportableColumn> reportableColumn) {
        if (CollectionUtils.isEmpty(reportableColumn)) {
            return this;
        }
        this.putEntity(entity);
        for (final ReportableColumn column : reportableColumn) {
            this.putColumn(entity, column);
        }
        return this;
    }

    public ReportableEntity hasColumn(final ReportableColumn reportableColumn) {
        final Optional<ReportableEntity> present = FluentIterable
                .from(this.entities)
                .firstMatch(new Predicate<ReportableEntity>() {
                    @Override
                    public boolean apply(@Nullable final ReportableEntity input) {
                        return input != null && input.hasColumn(reportableColumn);
                    }
                });
        return present.get();
    }

    public boolean hasColumn(final ReportableEntity entity) {
        return entity.hasColumns();
    }

    public void clearColumns() {
        for (final ReportableEntity entity : this.entities) {
            if (this.hasColumn(entity)) {
                entity.clearColumns();
            }
        }
    }

    @Override
    public String getBeanId() {
        return BEAN_ID;
    }

    public int getSize() {
        return this.entities.size();
    }

    public int getTotalSize() {
        int size = 0;
        for (final ReportableEntity entity : this.entities) {
            size += entity.getColumns().size();
        }
        return size;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(entities)
                      .toString();
    }

    @Override
    public Iterator<ReportableEntity> iterator() {
        return this.entities.iterator();
    }

    @Override
    public Map<String, ReportableEntity> asMap() {
        final Map<String, ReportableEntity> entityMap = Maps.newLinkedHashMap();
        for (final ReportableEntity entity : this.entities) {
            entityMap.put(entity.getName(), entity);
        }
        return Collections.unmodifiableMap(entityMap);
    }
}
