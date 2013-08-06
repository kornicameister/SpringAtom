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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.model.beans.PersistentObject;
import org.agatom.springatom.model.beans.car.SCar;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInterval;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * {@code SAppointment} is the business object describing the appointment.
 * <b>Appointment</b> is an object that can be placed in time and in particular assigned to the {@link SCar}.
 * The business requirement dictates that it also holds the list od tasks ({@link SAppointmentTask}).
 *
 * @author kornicamaister
 * @version 0.0.2
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
public class SAppointment
        extends PersistentObject<Long>
        implements Iterable<SAppointmentTask> {
    private static final String BEGIN_NULL_MSG = "Begin dateTime for appointment must not be null";
    private static final String END_NULL_MSG   = "End dateTime for appointment must not be null";
    private static final String CAR_NULL_MSG   = "Car for appointment must not be null";
    private static final String DATE_TIME_TYPE = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
    @Index(name = "sa_begin")
    @Type(type = DATE_TIME_TYPE)
    @Column(name = "begin", nullable = false)
    @NotNull(message = BEGIN_NULL_MSG)
    private DateTime begin;
    @Index(name = "sa_end")
    @Type(type = DATE_TIME_TYPE)
    @Column(name = "end", nullable = false)
    @NotNull(message = END_NULL_MSG)
    private DateTime end;
    @Size(min = 1, message = "SAppointment must contain at least one task [SAppointmentTask]")
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "appointment",
            cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<SAppointmentTask> tasks = Sets.newHashSet();
    @NotNull(message = CAR_NULL_MSG)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "car", referencedColumnName = "idScar")
    private SCar car;

    public Set<SAppointmentTask> getTasks() {
        if (this.tasks == null) {
            this.tasks = Sets.newHashSet();
        }
        return tasks;
    }

    public SAppointment setTasks(final Collection<SAppointmentTask> tasks) {
        this.tasks = Sets.newHashSet(tasks);
        return this;
    }

    public SAppointment addTask(final Collection<SAppointmentTask> tasks) {
        if (this.tasks == null) {
            this.tasks = Sets.newHashSet();
        }
        for (final SAppointmentTask task : tasks) {
            if (!this.tasks.contains(task)) {
                this.tasks.add(task.setAppointment(this));
            }
        }
        return this;
    }

    public SAppointment removeTask(final SAppointmentTask... tasks) {
        if (this.tasks == null) {
            this.tasks = Sets.newHashSet();
        }
        this.tasks.removeAll(Lists.newArrayList(tasks));
        return this;
    }

    public DateTime getBegin() {
        return begin;
    }

    public SAppointment setBegin(final DateTime begin) {
        this.begin = begin;
        return this;
    }

    public DateTime getEnd() {
        return end;
    }

    public SAppointment setEnd(final DateTime end) {
        this.end = end;
        return this;
    }

    public Interval getInterval() {
        return new Interval(this.begin, this.end);
    }

    public SAppointment setInterval(final ReadableInterval duration) {
        this.begin = duration.getStart();
        this.end = duration.getEnd();
        return this;
    }

    public boolean postpone(final ReadableDuration duration, final boolean toFuture) {
        if (this.begin != null && this.end != null) {
            final int scalar = toFuture ? 1 : -1;
            this.begin = this.begin.withDurationAdded(duration, scalar);
            this.end = this.end.withDurationAdded(duration, scalar);
            return true;
        }
        return false;
    }

    public SCar getCar() {
        return this.car;
    }

    public SAppointment setCar(final SCar car) {
        this.car = car;
        return this;
    }

    @Override
    public Iterator<SAppointmentTask> iterator() {
        return this.tasks.iterator();
    }
}
