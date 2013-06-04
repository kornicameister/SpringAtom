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

package org.agatom.springatom.mvc.model.dao;

import org.agatom.springatom.model.beans.meta.*;
import org.springframework.data.repository.NoRepositoryBean;

import javax.validation.constraints.NotNull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@NoRepositoryBean
public interface SMetaDataDAO {
    Iterable findAll(@NotNull final Class<? extends SMetaData> clazz);

    SMetaData findOne(@NotNull final Long id,
                      @NotNull final Class<? extends SMetaData> clazz);

    SMetaData findByType(@NotNull final MetaType type);

    public static enum MetaType {
        //SCT
        SCT_FAX("fax", SContactType.class),
        SCT_CELL_PHONE("cellPhone", SContactType.class),
        SCT_MAIL("mail", SContactType.class),
        SCT_PHONE("phone", SContactType.class),
        //SCT

        //SAT
        SAT_REPAIR("repair", SAppointmentTaskType.class),
        SAT_NORMAL("normal", SAppointmentTaskType.class),
        //SAT

        //SCPR
        SCPR_DEBTS("debts", SClientProblemReportType.class),
        SCPR_FAKE_ID("fake_id", SClientProblemReportType.class),
        SCPR_MISSED_APP("missed_app", SClientProblemReportType.class),
        SCPR_NO_PAYMENT("no_payment", SClientProblemReportType.class),
        //SCPR

        //SNT
        SNT_APP_CANCELED("app_canceled", SNotificationType.class),
        SNT_APP_DONE("app_done", SNotificationType.class),
        SNT_APP_REJECTED("app_rejected", SNotificationType.class);
        //SNT

        private final String type;

        private final Class<? extends SMetaData> target;

        MetaType(final String type, Class<? extends SMetaData> target) {
            this.type = type;
            this.target = target;
        }

        public String getType() {
            return type;
        }

        public Class<? extends SMetaData> getTarget() {
            return target;
        }
    }
}
