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
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.model.types.report.ReportSetting;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * {@code SReportSetting} is a placeholder for settings specified for {@link SReportSetting#report}.
 * This class defines the name and the associated report but it does not specify what kind of the value will property take.
 * <p/>
 * In order to create types property use:
 * <ul>
 * <li>{@link java.lang.String} -> {@link SReportStringSetting}</li>
 * <li>{@link java.lang.Number} -> {@link SReportNumberSetting}</li>
 * <li>{@link java.lang.Boolean} -> {@link SReportBooleanSetting}</li>
 * </ul>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SReportSetting.TABLE_NAME)
@Entity(name = SReportSetting.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "idSReportSetting", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
@Inheritance(strategy = InheritanceType.JOINED)
abstract class SReportSetting<HOLDS extends Serializable>
        extends PersistentObject<Long>
        implements ReportSetting<HOLDS> {
    private static final   long   serialVersionUID = -1700305423116775408L;
    protected static final String TABLE_NAME       = "reports_settings";
    protected static final String ENTITY_NAME      = "SReportSetting";
    @NotNull
    @Length(min = 3, max = 200)
    @Column(name = "report_setting_name", nullable = false, unique = false, updatable = true, insertable = true, length = 200)
    protected String  name;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    protected SReport report;

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Report getReport() {
        return report;
    }

    public final SReportSetting setName(final String name) {
        this.name = name;
        return this;
    }

    public final SReportSetting setReport(final SReport report) {
        this.report = report;
        return this;
    }
}
