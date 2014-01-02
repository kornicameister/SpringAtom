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

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.types.report.ReportColumn;
import org.agatom.springatom.server.model.types.report.ReportEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SReportEntity.TABLE_NAME)
@Entity(name = SReportEntity.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "re_id", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SReportEntity
        extends PersistentObject<Long>
        implements ReportEntity {
    public static final String TABLE_NAME  = "report_entity";
    public static final String ENTITY_NAME = "SReportEntity";
    @NotNull
    @Length(min = 1, max = 50)
    @Column(name = "re_entityName", nullable = false, length = 50)
    protected String             name;
    @NotNull
    @Column(name = "re_entityClazz", nullable = false, length = 300)
    protected Class<?>           clazz;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "re_report", referencedColumnName = "report_id", updatable = false)
    protected SReport            report;
    @Size(min = 1)
    @NotNull
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reportEntity", cascade = CascadeType.ALL, targetEntity = SReportColumn.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private   List<ReportColumn> columns;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SReportEntity setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public SReportEntity setClazz(final Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    @Override
    public SReport getReport() {
        return report;
    }

    @Override
    public SReportEntity setReport(final SReport report) {
        this.report = report;
        return this;
    }

    @Override
    public List<ReportColumn> getColumns() {
        this.requireColumns();
        return this.columns;
    }

    @Override
    public ReportEntity setColumns(final List<ReportColumn> columns) {
        Preconditions.checkNotNull(columns);
        Preconditions.checkArgument(!columns.isEmpty(), "Columns must not be empty");
        for (final ReportColumn reportColumn : columns) {
            this.addColumn(reportColumn);
        }
        return this;
    }

    @Override
    public ReportColumn addColumn(final ReportColumn column) {
        this.requireColumns();
        column.setReportEntity(this);
        this.columns.add(column);
        return column;
    }

    private void requireColumns() {
        if (this.columns == null) {
            this.columns = Lists.newLinkedList();
        }
    }

    @Override
    public boolean hasColumn(final String columnName) {
        this.requireColumns();
        return FluentIterable
                .from(this.columns)
                .filter(new Predicate<ReportColumn>() {
                    @Override
                    public boolean apply(@Nullable final ReportColumn input) {
                        return input != null && input.getColumnName() != null && input.getColumnName().equals(columnName);
                    }
                })
                .first()
                .isPresent();
    }

    @Override
    public boolean hasColumn(final Class<?> columnClazz) {
        this.requireColumns();
        return FluentIterable
                .from(this.columns)
                .filter(new Predicate<ReportColumn>() {
                    @Override
                    public boolean apply(@Nullable final ReportColumn input) {
                        return input != null && input.getPropertyClass() != null && input.getPropertyClass().equals(columnClazz);
                    }
                })
                .first()
                .isPresent();
    }

    @Override
    public boolean hasColumns() {
        return this.columns != null && !this.columns.isEmpty();
    }

    @Override
    public void clearColumns() {
        this.columns.clear();
    }
}
