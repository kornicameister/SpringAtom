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

import org.agatom.springatom.model.beans.PersistentObject;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SAppointment")
@Table(name = "SAppointment")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSAppointment",
                updatable = false,
                nullable = false)
)
public class SAppointment extends PersistentObject<Long> {

    @Type(type = "timestamp")
    @Column(name = "startDate")
    private Date                  startDate;
    @Type(type = "timestamp")
    @Column(nullable = false,
            name = "endDate")
    private Date                  endDate;
    @Type(type = "time")
    @Column(nullable = false,
            name = "startTime")
    private Time                  startTime;
    @Type(type = "time")
    @Column(nullable = false,
            name = "endTime")
    private Time                  endTime;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appointment")
    private Set<SAppointmentTask> tasks;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(final Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(final Time endTime) {
        this.endTime = endTime;
    }

    public Set<SAppointmentTask> getTasks() {
        return tasks;
    }

    public void setTasks(final Set<SAppointmentTask> tasks) {
        this.tasks = tasks;
    }

    public boolean addAppointment(final SAppointmentTask sAppointmentTask) {
        if (this.tasks == null) {
            this.tasks = new HashSet<>();
        }
        return this.tasks.add(sAppointmentTask);
    }

    public boolean removeAppointment(final SAppointmentTask sAppointmentTask) {
        return this.tasks != null && this.tasks.remove(sAppointmentTask);
    }

}
