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

package org.agatom.springatom.server.model.beans.report.column;

import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.types.report.column.ReportColumn;
import org.hibernate.validator.constraints.Length;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SReportColumn.TABLE_NAME)
@Entity(name = SReportColumn.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "idSReportColumn", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SReportColumn
        extends PersistentObject<Long>
        implements ReportColumn {
    public static final  String TABLE_NAME       = "reports_c";
    public static final  String ENTITY_NAME      = "SReportColumn";
    private static final long   serialVersionUID = 2086990797761876723L;
    @NotNull
    @Length(max = 50)
    @Column(name = "reportColumn_name", nullable = false, updatable = false, length = 50)
    private String   columnName;
    @NotNull
    @Length(min = 10, max = 300)
    @Column(name = "reportColumn_clazz", nullable = false, updatable = false, length = 300)
    private Class<?> columnClass;

    public SReportColumn setColumnName(final String propertyName) {
        this.columnName = propertyName;
        return this;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public Class<?> getColumnClass() {
        return columnClass;
    }

    public SReportColumn setPropertyClass(final Class<?> columnClazz) {
        this.columnClass = columnClazz;
        return this;
    }
}
