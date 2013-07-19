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

package org.agatom.springatom.model.beans.appointment;

import com.google.common.base.Objects;
import org.agatom.springatom.model.beans.PersistentObject;
import org.agatom.springatom.model.beans.meta.SAppointmentTaskType;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SAppointmentTask")
@Table(name = "SAppointmentTask")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSAppointmentTask",
                updatable = false,
                nullable = false)
)
public class SAppointmentTask extends PersistentObject<Long> {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointment", referencedColumnName = "idSAppointment", updatable = false)
    private SAppointment         appointment;
    @NaturalId
    @ManyToOne(optional = false)
    @JoinColumn(name = "type", referencedColumnName = "idSMetaData", updatable = true)
    private SAppointmentTaskType type;
    @Column(name = "task", nullable = false, length = 444)
    private String               task;

    public SAppointment getAppointment() {
        return appointment;
    }

    public void setAppointment(final SAppointment appointment) {
        this.appointment = appointment;
    }

    public SAppointmentTaskType getType() {
        return type;
    }

    public void setType(final SAppointmentTaskType type) {
        this.type = type;
    }

    public String getTask() {
        return task;
    }

    public void setTask(final String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("appointment", appointment)
                .add("type", type)
                .add("task", task)
                .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SAppointmentTask)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        SAppointmentTask that = (SAppointmentTask) o;

        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
