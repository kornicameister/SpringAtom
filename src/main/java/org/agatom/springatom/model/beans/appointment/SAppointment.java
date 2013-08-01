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

import com.google.common.collect.Sets;
import org.agatom.springatom.model.beans.PersistentObject;
import org.agatom.springatom.model.beans.car.SCar;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInterval;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Iterator;
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
    private DateTime              begin;
    @Index(name = "sa_end")
    @Type(type = DATE_TIME_TYPE)
    @Column(name = "end", nullable = false)
    @NotNull(message = END_NULL_MSG)
    private DateTime              end;
    @OneToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            },
            orphanRemoval = true
    )
    @JoinColumn(name = "appointment", nullable = false)
    private Set<SAppointmentTask> tasks;
    @NotNull(message = CAR_NULL_MSG)
    @ManyToOne(optional = false)
    @JoinColumn(name = "car", referencedColumnName = "idScar")
    private SCar                  car;

    public Set<SAppointmentTask> getTasks() {
        return tasks;
    }

    public SAppointment addTask(final SAppointmentTask... tasks) {
        if (this.tasks == null) {
            this.tasks = Sets.newHashSet(tasks);
        }
        return this;
    }

    public SAppointment removeTask(final SAppointmentTask... tasks) {
        if (!this.tasks.removeAll(Arrays.asList(tasks))) {
            return null;
        }
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
            this.begin.withDurationAdded(duration, scalar);
            this.end.withDurationAdded(duration, scalar);
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
