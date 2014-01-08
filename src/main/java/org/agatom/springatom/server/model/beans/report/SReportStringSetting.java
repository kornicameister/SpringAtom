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

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * {@code SReportStringSetting} takes any {@link java.lang.String} as the value
 * for given {@link SReportSetting}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = SReportStringSetting.ENTITY_NAME)
@Table(name = SReportStringSetting.TABLE_NAME)
@PrimaryKeyJoinColumn(name = "idSReportSetting")
public class SReportStringSetting
        extends SReportSetting<String> {
    private static final long   serialVersionUID = -1700305423116775408L;
    public static final  String ENTITY_NAME      = "SReportStringSetting";
    public static final  String TABLE_NAME       = "reports_settings_string";
    @NotNull
    @Length(min = 3, max = 200)
    @Column(name = "report_setting_string_value", unique = false, updatable = true, insertable = true, length = 300)
    private String value;

    @Override
    public String getValue() {
        return this.value;
    }

    public SReportStringSetting setValue(final String value) {
        this.value = value;
        return this;
    }

}
