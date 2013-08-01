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

package org.agatom.springatom.model.dto;

import org.agatom.springatom.model.types.meta.SMetaDataEnum;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SAppointmentTaskDTO
        implements DTO {
    final private SMetaDataEnum type;
    final private String        task;

    public SAppointmentTaskDTO(final SMetaDataEnum type, final String task) {
        this.type = type;
        this.task = task;
    }

    public SMetaDataEnum getType() {
        return type;
    }

    public String getTask() {
        return task;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + task.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SAppointmentTaskDTO)) {
            return false;
        }

        final SAppointmentTaskDTO that = (SAppointmentTaskDTO) o;

        return task.equals(that.task) && type.equals(that.type);
    }

    @Override
    public String toString() {
        return "SAppointmentTaskDTO{" +
                "type=" + type +
                ", task='" + task + '\'' +
                "} " + super.toString();
    }
}
