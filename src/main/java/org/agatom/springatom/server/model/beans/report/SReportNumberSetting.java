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

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * {@code SReportNumberSetting} supports any type of a {@link java.lang.Number} derived classes
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = SReportNumberSetting.ENTITY_NAME)
@Table(name = SReportNumberSetting.TABLE_NAME)
@PrimaryKeyJoinColumn(name = "idSReportSetting")
public class SReportNumberSetting
		extends SReportSetting<Number> {
	public static final  String ENTITY_NAME      = "SReportNumberSetting";
	public static final  String TABLE_NAME       = "reports_settings_number";
	private static final long   serialVersionUID = -1700305423116775408L;
	@NotNull
	@Range(min = Long.MIN_VALUE, max = Long.MAX_VALUE)
	@Column(name = "report_setting_number_val", unique = false, updatable = true, insertable = true)
	private Number value;

	@Override
	public Number getValue() {
		return this.value;
	}

	public SReportNumberSetting setValue(final Number value) {
		this.value = value;
		return this;
	}

	@Override
	public String getIdentity() {
		return String.format("%s - %s", super.getIdentity(), this.value);
	}

}
