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

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.agatom.springatom.server.model.beans.PersistentVersionedObject;
import org.agatom.springatom.server.model.support.EntityColumn;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.model.types.report.ReportColumn;
import org.agatom.springatom.server.model.types.report.ReportEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SReport.TABLE_NAME)
@Entity(name = SReport.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "report_id", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SReport
        extends PersistentVersionedObject
        implements Report {
    public static final  String TABLE_NAME     = "reports";
    public static final  String ENTITY_NAME    = "SReport";
    private static final String DATE_TIME_TYPE = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
    @Past
    @NotNull
    @Type(type = DATE_TIME_TYPE)
    @Column(name = "report_createdAt", nullable = false)
    protected DateTime           createdAt;
    @NotNull
    @Length(min = 5, max = 50)
    @Column(name = "report_name", nullable = false, length = 50)
    protected String             name;
    @NotNull
    @Length(max = 200)
    @Column(name = "report_description", nullable = false, length = 200)
    protected String             description;
    @NotNull
    @Size(min = 1, message = MSG.NO_ENTITIES_IN_REPORT)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.ALL, targetEntity = SReportEntity.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected List<ReportEntity> entities;

    @Override
    public SReport setDescription(final String description) {
        this.description = description;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public DateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public SReport setCreatedAt(final DateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SReport setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public List<ReportEntity> getEntities() {
        this.requireEntities();
        return entities;
    }

    @Override
    public SReport setEntities(final List<ReportEntity> entities) {
        for (final ReportEntity entity : entities) {
            this.addEntity(entity);
        }
        return this;
    }

    @Override
    public ReportEntity addEntity(final ReportEntity entity) {
        this.requireEntities();
        entity.setReport(this);
        this.entities.add(entity);
        return entity;
    }

    @Override
    public ReportColumn addColumn(final ReportEntity entity, final ReportColumn reportColumn) {
        return this.addEntity(entity).addColumn(reportColumn);
    }

    @Override
    public boolean hasEntity(final Class<?> javaClass) {
        this.requireEntities();
        return FluentIterable
                .from(this.entities)
                .filter(new Predicate<ReportEntity>() {
                    @Override
                    public boolean apply(@Nullable final ReportEntity input) {
                        return input != null && input.getClazz() != null && input.getClazz().equals(javaClass);
                    }
                })
                .first()
                .isPresent();
    }

    @Override
    public boolean hasEntities() {
        return this.entities != null && !this.entities.isEmpty();
    }

    private void requireEntities() {
        if (this.entities == null) {
            this.entities = Lists.newArrayList();
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
