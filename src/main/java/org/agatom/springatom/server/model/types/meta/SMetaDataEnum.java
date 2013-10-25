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

package org.agatom.springatom.server.model.types.meta;

import org.agatom.springatom.server.model.beans.meta.*;
import org.agatom.springatom.server.populators.DatabaseEnumPopulable;

/**
 * {@code SMetaDataEnum} provides overall description over meta data that is used throughout beans
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public enum SMetaDataEnum
        implements DatabaseEnumPopulable {
    //SCT
    SCT_FAX("fax", Constants.CONTACT_TYPE_CLASS),
    SCT_CELL_PHONE("cellPhone", Constants.CONTACT_TYPE_CLASS),
    SCT_MAIL("mail", Constants.CONTACT_TYPE_CLASS),
    SCT_PHONE("phone", Constants.CONTACT_TYPE_CLASS),
    //SCT

    //SAT
    SAT_REPAIR("repair", Constants.APPOINTMENT_TASK_TYPE_CLASS),
    SAT_NORMAL("normal", Constants.APPOINTMENT_TASK_TYPE_CLASS),
    SAT_OIL_CHANGE("oil_change", Constants.APPOINTMENT_TASK_TYPE_CLASS),
    //SAT

    //SCPR
    SCPR_DEBTS("debts", Constants.CLIENT_PROBLEM_REPORT_TYPE_CLASS),
    SCPR_FAKE_ID("fake_id", Constants.CLIENT_PROBLEM_REPORT_TYPE_CLASS),
    SCPR_MISSED_APP("missed_app", Constants.CLIENT_PROBLEM_REPORT_TYPE_CLASS),
    SCPR_NO_PAYMENT("no_payment", Constants.CLIENT_PROBLEM_REPORT_TYPE_CLASS),
    //SCPR

    //SNT
    SNT_APP_CANCELED("app_canceled", Constants.NOTIFICATION_TYPE_CLASS),
    SNT_APP_DONE("app_done", Constants.NOTIFICATION_TYPE_CLASS),
    SNT_APP_REJECTED("app_rejected", Constants.NOTIFICATION_TYPE_CLASS);
    //SNT
    private final String                     type;
    private final Class<? extends SMetaData> target;

    SMetaDataEnum(final String type, Class<? extends SMetaData> target) {
        this.type = type;
        this.target = target;
    }

    @Override
    public String[] getColumns() {
        return new String[]{"type", "meta"};
    }

    @Override
    public String getTable() {
        return "smetadata";
    }

    @Override
    public String[] getData() {
        return new String[]{this.toString(), this.getMetaType()};
    }

    public static SMetaDataEnum fromType(final String type) {
        for (final SMetaDataEnum anEnum : SMetaDataEnum.values()) {
            if (anEnum.type.equals(type)) {
                return anEnum;
            }
        }
        return null;
    }

    private String getMetaType() {
        final String clazzName = this.target.getSimpleName();
        final String[] splintedByDot = clazzName.split("[a-z]+");
        final StringBuilder builder = new StringBuilder();
        for (final String part : splintedByDot) {
            builder.append(part);
        }
        return builder.toString();
    }

    public Class<? extends SMetaData> getTarget() {
        return target;
    }

    public String getType() {
        return type;
    }

    private static class Constants {
        private static final Class<SContactType>             CONTACT_TYPE_CLASS               = SContactType.class;
        private static final Class<SAppointmentTaskType>     APPOINTMENT_TASK_TYPE_CLASS      = SAppointmentTaskType.class;
        private static final Class<SClientProblemReportType> CLIENT_PROBLEM_REPORT_TYPE_CLASS = SClientProblemReportType.class;
        private static final Class<SNotificationType>        NOTIFICATION_TYPE_CLASS          = SNotificationType.class;
    }
}
