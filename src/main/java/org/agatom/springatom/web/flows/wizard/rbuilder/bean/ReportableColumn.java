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

package org.agatom.springatom.web.flows.wizard.rbuilder.bean;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import javax.annotation.Nonnull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ReportableColumn
        extends ReportableBean
        implements Comparable<ReportableColumn> {
    private ReportableEntity entity;
    private String           columnName;
    private Class<?>         columnClass;

    public ReportableColumn setColumnClass(final Class<?> columnClass) {
        this.columnClass = columnClass;
        return this;
    }

    public Class<?> getColumnClass() {
        return columnClass;
    }

    public ReportableEntity getEntity() {
        return entity;
    }

    public ReportableColumn setEntity(final ReportableEntity entity) {
        this.entity = entity;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public ReportableColumn setColumnName(final String columnName) {
        this.columnName = columnName;
        return this;
    }

    @Override
    public int compareTo(@Nonnull final ReportableColumn column) {
        return ComparisonChain
                .start()
                .compare(this.entity, column.entity)
                .compare(this.columnName, column.columnName)
                .result();
    }

    @Override
    public String getMessageKey() {
        return String.format("%s.%s", this.entity.getMessageKey(), this.columnName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReportableColumn that = (ReportableColumn) o;

        return Objects.equal(this.entity, that.entity) &&
                Objects.equal(this.columnName, that.columnName) &&
                Objects.equal(this.label, that.label) &&
                Objects.equal(this.columnClass, that.columnClass) &&
                Objects.equal(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entity, columnName, label, id, columnClass);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(entity)
                      .addValue(columnName)
                      .addValue(label)
                      .addValue(id)
                      .addValue(columnClass)
                      .toString();
    }
}
