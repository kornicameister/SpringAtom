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

package org.agatom.springatom.server.model.types.report;

import org.agatom.springatom.server.model.beans.report.SReportColumn;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ReportColumn {
    SReportColumn setReportEntity(ReportEntity report);

    ReportEntity getReportEntity();

    String getEntityName();

    String getColumnName();

    ReportColumn setColumnName(String columnName);

    String getPropertyName();

    ReportColumn setPropertyName(String propertyName);

    Class<?> getPropertyClass();

    ReportColumn setPropertyClass(Class<?> columnClazz);

}
