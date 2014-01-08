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

package org.agatom.springatom.server.model.beans.report;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.beans.PersistentVersionedObject;
import org.agatom.springatom.server.model.beans.report.column.SReportColumn;
import org.agatom.springatom.server.model.beans.report.entity.SReportEntity;
import org.agatom.springatom.server.model.beans.report.links.entity.SReportEntityLink;
import org.agatom.springatom.server.model.beans.report.setting.SReportBooleanSetting;
import org.agatom.springatom.server.model.beans.report.setting.SReportNumberSetting;
import org.agatom.springatom.server.model.beans.report.setting.SReportSetting;
import org.agatom.springatom.server.model.beans.report.setting.SReportStringSetting;
import org.agatom.springatom.server.model.support.EntityColumn;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.model.types.report.column.ReportColumnLink;
import org.agatom.springatom.server.model.types.report.entity.ReportEntity;
import org.agatom.springatom.server.model.types.report.entity.ReportEntityLink;
import org.agatom.springatom.server.model.types.report.resource.ReportResource;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@code SReport} describes {@code report} by specyfying following properties:
 * <ul>
 * <li>mandatory {@link org.agatom.springatom.server.model.beans.report.SReport#title}</li>
 * <li>mandatory {@link org.agatom.springatom.server.model.beans.report.SReport#entities}</li>
 * <li>mandatory {@link org.agatom.springatom.server.model.beans.report.SReport#resource}</li>
 * <li>optional {@link org.agatom.springatom.server.model.beans.report.SReport#subtitle}</li>
 * <li>optional {@link org.agatom.springatom.server.model.beans.report.SReport#description}</li>
 * <li>optional {@link org.agatom.springatom.server.model.beans.report.SReport#settings}</li>
 * </ul>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SReport.TABLE_NAME)
@Entity(name = SReport.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = SReport.ID_COLUMN, nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SReport
        extends PersistentVersionedObject
        implements Report {
    private static final long   serialVersionUID = -2542100437128982892L;
    public static final  String TABLE_NAME       = "reports";
    public static final  String ENTITY_NAME      = "SReport";
    public static final  String ID_COLUMN        = "idSReport";
    @NotNull
    @Length(min = 5, max = 50)
    @NaturalId(mutable = false)
    @Column(name = "report_title", nullable = false, unique = true, updatable = false, length = 50)
    protected               String                    title;
    @Length(min = 5, max = 50)
    @Column(name = "report_subtitle", nullable = true, insertable = true, updatable = true, length = 50)
    protected               String                    subtitle;
    @Length(max = 200)
    @Column(name = "report_description", nullable = true, updatable = true, insertable = true, length = 200)
    protected               String                    description;
    @Embedded
    protected               SReportResource           resource;
    @NotNull
    @Size(min = 1, message = MSG.NO_ENTITIES_IN_REPORT)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected               List<SReportEntityLink>   entities;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected               Set<SReportSetting<?>>    settings;
    @Transient
    private final transient Map<String, Serializable> mappedSettings;

    public SReport() {
        this.mappedSettings = Maps.newHashMap();
    }

    @Override
    public ReportResource getResource() {
        return this.resource;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public List<ReportEntity> getEntities() {
        this.requireEntities();
        return FluentIterable.from(this.entities)
                             .transform(new Function<SReportEntityLink, ReportEntity>() {
                                 @Nullable
                                 @Override
                                 public ReportEntity apply(@Nullable final SReportEntityLink input) {
                                     assert input != null;
                                     return input.getReportEntity();
                                 }
                             })
                             .toList();
    }

    @Override
    public Set<SReportSetting<?>> getSettings() {
        this.requireSettings();
        return this.settings;
    }

    @Override
    public Map<String, Serializable> getSettingsAsMap() {
        this.getSettings();
        if (this.settings.size() != this.mappedSettings.size()) {
            this.mappedSettings.clear();
        }
        for (final SReportSetting<?> setting : this.settings) {
            this.mappedSettings.put(setting.getName(), setting.getValue());
        }
        return this.mappedSettings;
    }

    @Override
    public Serializable getSetting(final String key) {
        return this.getSettingsAsMap().get(key);
    }

    @Override
    public boolean hasEntity(final Class<?> javaClass) {
        this.requireEntities();
        return FluentIterable
                .from(this.entities)
                .filter(new Predicate<ReportEntityLink>() {
                    @Override
                    public boolean apply(@Nullable final ReportEntityLink input) {
                        return input != null
                                && input.getReportEntity() != null
                                && input.getReportEntity().getEntityClass() != null
                                && input.getReportEntity().getEntityClass().equals(javaClass);
                    }
                })
                .first()
                .isPresent();
    }

    @Override
    public boolean hasEntities() {
        return this.entities != null && !this.entities.isEmpty();
    }

    @Override
    public boolean hasSetting(@Nonnull final String key) {
        return this.getSettingsAsMap().containsKey(key);
    }

    @Override
    public boolean hasSettings() {
        return this.settings != null && !this.settings.isEmpty();
    }

    public SReport setResource(final String reportPath) {
        this.resource = new SReportResource().setReportPath(reportPath);
        return this;
    }

    public SReport setDescription(final String description) {
        this.description = description;
        return this;
    }

    public SReport setTitle(final String name) {
        this.title = name;
        return this;
    }

    public SReport setSubtitle(final String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public SReport setEntities(final List<SReportEntity> entities) {
        for (final SReportEntity entity : entities) {
            this.addEntity(entity);
        }
        return this;
    }

    public SReportEntityLink addEntity(final SReportEntity entity) {
        return this.addEntity(SReportEntityLink.newSReportEntityLink(this, entity));
    }

    public SReportEntityLink addEntity(final SReportEntityLink entityLink) {
        Assert.isInstanceOf(
                SReportEntityLink.class,
                entityLink,
                String.format("%s is not valid instance of %s",
                        entityLink,
                        ClassUtils.getShortName(SReportEntityLink.class))
        );
        this.requireEntities();
        if (this.entities.add(entityLink)) {
            return entityLink;
        }
        throw new IllegalStateException(String.format("Failed to append new %s", entityLink));
    }

    public ReportColumnLink addColumn(final SReportEntity entity, final SReportColumn reportColumn) {
        return this.addEntity(entity).addColumn(reportColumn);
    }

    public SReportSetting<?> putSetting(final SReportSetting<?> setting) {
        this.requireSettings();
        this.settings.add(setting.setReport(this));
        this.mappedSettings.put(setting.getName(), setting.getValue());
        return setting;
    }

    public <VAL extends Serializable> SReportSetting<?> putSetting(final String key, final VAL value) throws IllegalArgumentException {
        final Class<? extends Serializable> valueClass = value.getClass();
        SReportSetting<?> setting = null;
        if (ClassUtils.isAssignable(String.class, valueClass)) {
            setting = new SReportStringSetting().setValue((String) value).setName(key);
        } else if (ClassUtils.isAssignable(Number.class, valueClass)) {
            setting = new SReportNumberSetting().setValue((Number) value).setName(key);
        } else if (ClassUtils.isAssignable(Boolean.class, valueClass)) {
            setting = new SReportBooleanSetting().setValue((Boolean) value).setName(key);
        }
        if (setting == null) {
            throw new IllegalArgumentException(String.format("%s is not assignable to any of following types = [%s]",
                    ClassUtils.getShortName(valueClass),
                    Lists.newArrayList(
                            ClassUtils.getShortName(String.class),
                            ClassUtils.getShortName(Number.class),
                            ClassUtils.getShortName(Boolean.class)
                    )
            ));
        }
        return this.putSetting(setting);
    }

    private void requireEntities() {
        if (this.entities == null) {
            this.entities = Lists.newArrayList();
        }
    }

    private void requireSettings() {
        if (this.settings == null) {
            this.settings = Sets.newHashSet();
        }
    }

    private class MSG {
        public static final String NO_ENTITIES_IN_REPORT = "Report does not specify any entities";
    }

    public static enum Columns
            implements EntityColumn {
        NAME {
            @Override
            public String getName() {
                return "name";
            }
        },
        DESCRIPTION {
            @Override
            public String getName() {
                return "description";
            }
        },
        CREATED_AT {
            @Override
            public String getName() {
                return "createdAt";
            }
        },
        ENTITIES {
            @Override
            public String getName() {
                return "entities";
            }
        }
    }
}
