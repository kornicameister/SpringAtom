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

import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.types.report.ReportColumn;
import org.agatom.springatom.server.model.types.report.ReportEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SReportColumn.TABLE_NAME)
@Entity(name = SReportColumn.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "rc_id", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SReportColumn
        extends PersistentObject<Long>
        implements ReportColumn {
    public static final String TABLE_NAME  = "report_columns";
    public static final String ENTITY_NAME = "SReportColumn";
    @Length(max = 50, min = 1)
    @NotNull
    @Column(name = "rc_columnName", nullable = false, length = 50)
    private   String       columnName;
    @Length(max = 50, min = 1)
    @Column(name = "rc_propertyName", nullable = false, length = 50)
    private   String       propertyName;
    @NotNull
    @Column(name = "rc_propertyClazz", nullable = false, length = 300)
    private   Class<?>     propertyClass;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = SReportEntity.class)
    @JoinColumn(name = "re_reportEntity", referencedColumnName = "re_id", updatable = false)
    protected ReportEntity reportEntity;

    @Override
    public SReportColumn setReportEntity(final ReportEntity report) {
        this.reportEntity = report;
        return this;
    }

    @Override
    public ReportEntity getReportEntity() {
        return reportEntity;
    }

    @Override
    public String getEntityName() {
        return this.reportEntity.getName();
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public ReportColumn setColumnName(final String columnName) {
        this.columnName = columnName;
        return this;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public ReportColumn setPropertyName(final String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    @Override
    public Class<?> getPropertyClass() {
        return propertyClass;
    }

    @Override
    public ReportColumn setPropertyClass(final Class<?> columnClazz) {
        this.propertyClass = columnClazz;
        return this;
    }
}
