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

package org.agatom.springatom.server.model.beans.appointment;

import com.google.common.collect.Lists;
import org.agatom.springatom.server.model.beans.activity.SAssignedActivity;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.agatom.springatom.server.model.types.appointment.Appointment;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.joda.time.*;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * {@code SAppointment} is the business object describing the event.
 * <b>Appointment</b> is an object that can be placed in time and in particular assigned to the {@link org.agatom.springatom.server.model.beans.car.SCar}.
 * The business requirement dictates that it also holds the list od tasks ({@link org.agatom.springatom.server.model.beans.appointment.SAppointmentTask}).
 *
 * @author kornicamaister
 * @version 0.0.2
 * @since 0.0.1
 */
@Table(name = SAppointment.TABLE_NAME)
@Entity(name = SAppointment.ENTITY_NAME)
@ReportableEntity
@AttributeOverride(name = "id", column = @Column(name = "idSAppointment", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SAppointment
		extends SAssignedActivity<Long>
		implements Iterable<SAppointmentTask>, Appointment {
	/** Constant <code>TABLE_NAME="appointment"</code> */
	public static final  String                 TABLE_NAME       = "appointment";
	/** Constant <code>ENTITY_NAME="SAppointment"</code> */
	public static final  String                 ENTITY_NAME      = "SAppointment";
	private static final String                 DATE_TIME_TYPE   = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
	private static final long                   serialVersionUID = -3158182089097228777L;
	@Type(type = DATE_TIME_TYPE)
	@Column(name = "begin", nullable = false)
	@NotNull(message = MSG.BEGIN_NULL_MSG)
	private              DateTime               begin            = null;
	@Type(type = DATE_TIME_TYPE)
	@Column(name = "end", nullable = false)
	@NotNull(message = MSG.END_NULL_MSG)
	private              DateTime               end              = null;
	@Size(min = 1, message = MSG.NO_TASK_MSG)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appointment", cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private              List<SAppointmentTask> tasks            = null;
	@NotNull(message = MSG.CAR_NULL_MSG)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "car", referencedColumnName = "idScar")
	private              SCar                   car              = null;
	@Formula(value = "UNIX_TIMESTAMP(begin)")
	private              Long                   beginTs          = null;
	@Formula(value = "UNIX_TIMESTAMP(end)")
	private              Long                   endTs            = null;
	@Column(name = "allDay", nullable = true)
	private              boolean                allDay           = false;
	@Column(name = "closed", nullable = true)
	private              boolean                closed           = false;

	/** {@inheritDoc} */
	@Override
	public boolean isClosed() {
		return this.closed;
	}

	/**
	 * <p>Setter for the field <code>closed</code>.</p>
	 *
	 * @param closed a boolean.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment setClosed(final boolean closed) {
		this.closed = closed;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAllDay() {
		return allDay;
	}

	/**
	 * <p>Setter for the field <code>allDay</code>.</p>
	 *
	 * @param allDay a boolean.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment setAllDay(final boolean allDay) {
		this.allDay = allDay;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public List<SAppointmentTask> getTasks() {
		this.requireTaskList();
		return tasks;
	}

	/**
	 * <p>Setter for the field <code>tasks</code>.</p>
	 *
	 * @param tasks a {@link java.util.Collection} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment setTasks(final Collection<SAppointmentTask> tasks) {
		Assert.notNull(tasks);
		return this.addTask(tasks);
	}

	/**
	 * <p>addTask.</p>
	 *
	 * @param tasks a {@link java.util.Collection} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment addTask(final Collection<SAppointmentTask> tasks) {
		this.requireTaskList();
		for (final SAppointmentTask task : tasks) {
			if (!this.tasks.contains(task)) {
				this.tasks.add(task.setAppointment(this));
			}
		}
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DateTime getBegin() {
		this.requireBeginDate();
		return this.begin.toDateTime();
	}

	/**
	 * <p>Setter for the field <code>begin</code>.</p>
	 *
	 * @param begin a {@link org.joda.time.DateTime} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment setBegin(final DateTime begin) {
		this.begin = begin;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DateTime getEnd() {
		this.requireEndDate();
		return this.end.toDateTime();
	}

	/**
	 * <p>Setter for the field <code>end</code>.</p>
	 *
	 * @param end a {@link org.joda.time.DateTime} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment setEnd(final DateTime end) {
		this.end = end;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public Interval getInterval() {
		return new Interval(this.begin, this.end);
	}

	/**
	 * <p>setInterval.</p>
	 *
	 * @param duration a {@link org.joda.time.ReadableInterval} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment setInterval(final ReadableInterval duration) {
		this.setBegin(duration.getStart());
		this.setEnd(duration.getEnd());
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public SCar getCar() {
		if (this.car == null) {
			this.car = new SCar();
		}
		return this.car;
	}

	/**
	 * <p>Setter for the field <code>car</code>.</p>
	 *
	 * @param car a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment setCar(final SCar car) {
		this.car = car;
		return this;
	}

	private void requireTaskList() {
		if (this.tasks == null) {
			this.tasks = Lists.newLinkedList();
		}
	}

	/**
	 * <p>removeTask.</p>
	 *
	 * @param tasks a {@link org.agatom.springatom.server.model.beans.appointment.SAppointmentTask} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment removeTask(final SAppointmentTask... tasks) {
		if (this.tasks == null) {
			return this;
		}
		this.tasks.removeAll(Lists.newArrayList(tasks));
		return this;
	}

	/**
	 * <p>clearTasks.</p>
	 */
	public void clearTasks() {
		this.tasks.clear();
		this.tasks = null;
	}

	/**
	 * <p>assignTasks.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment assignTasks() {
		if (!CollectionUtils.isEmpty(this.tasks)) {
			for (final SAppointmentTask task : this.tasks) {
				task.setAppointment(this);
			}
		}
		return this;
	}

	/**
	 * <p>Getter for the field <code>beginTs</code>.</p>
	 *
	 * @return a long.
	 */
	public long getBeginTs() {
		if (this.beginTs != null) {
			return this.beginTs;
		}
		return TimeUnit.MILLISECONDS.toSeconds(this.begin.getMillis());
	}

	/**
	 * <p>Getter for the field <code>endTs</code>.</p>
	 *
	 * @return a long.
	 */
	public long getEndTs() {
		if (this.endTs != null) {
			return this.endTs;
		}
		return TimeUnit.MILLISECONDS.toSeconds(this.end.getMillis());
	}

	/**
	 * <p>postpone.</p>
	 *
	 * @param duration a {@link org.joda.time.ReadableDuration} object.
	 * @param toFuture a boolean.
	 *
	 * @return a boolean.
	 */
	public boolean postpone(final ReadableDuration duration, final boolean toFuture) {
		if (this.begin != null && this.end != null) {
			final int scalar = toFuture ? 1 : -1;
			this.begin = this.begin.withDurationAdded(duration, scalar);
			this.end = this.end.withDurationAdded(duration, scalar);
			return true;
		}
		return false;
	}

	/**
	 * <p>getBeginTime.</p>
	 *
	 * @return a {@link org.joda.time.LocalTime} object.
	 */
	public LocalTime getBeginTime() {
		this.requireBeginDate();
		return this.begin.toLocalTime();
	}

	/**
	 * <p>setBeginTime.</p>
	 *
	 * @param localTime a {@link org.joda.time.LocalTime} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment setBeginTime(final LocalTime localTime) {
		this.requireBeginDate();
		final MutableDateTime mutableDateTime = this.begin.toMutableDateTime();
		mutableDateTime.setTime(localTime.toDateTimeToday());
		this.begin = mutableDateTime.toDateTime();
		return this;
	}

	private void requireBeginDate() {
		if (this.begin == null) {
			this.begin = DateTime.now();
		}
	}

	/**
	 * <p>getEndTime.</p>
	 *
	 * @return a {@link org.joda.time.LocalTime} object.
	 */
	public LocalTime getEndTime() {
		this.requireEndDate();
		return this.end.toLocalTime();
	}

	/**
	 * <p>setEndTime.</p>
	 *
	 * @param localTime a {@link org.joda.time.LocalTime} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment setEndTime(final LocalTime localTime) {
		this.requireEndDate();
		final MutableDateTime mutableDateTime = this.end.toMutableDateTime();
		mutableDateTime.setTime(localTime.toDateTimeToday());
		this.end = mutableDateTime.toDateTime();
		return this;
	}

	private void requireEndDate() {
		if (this.end == null) {
			this.end = DateTime.now();
		}
	}

	/**
	 * <p>getBeginDate.</p>
	 *
	 * @return a {@link org.joda.time.LocalDate} object.
	 */
	public LocalDate getBeginDate() {
		return this.getBegin().toLocalDate();
	}

	/**
	 * <p>setBeginDate.</p>
	 *
	 * @param localDate a {@link org.joda.time.LocalDate} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment setBeginDate(final LocalDate localDate) {
		this.requireBeginDate();
		final MutableDateTime mutableDateTime = this.begin.toMutableDateTime();
		mutableDateTime.setDate(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
		this.begin = mutableDateTime.toDateTime();
		return this;
	}

	/**
	 * <p>getEndDate.</p>
	 *
	 * @return a {@link org.joda.time.LocalDate} object.
	 */
	public LocalDate getEndDate() {
		return this.getEnd().toLocalDate();
	}

	/**
	 * <p>setEndDate.</p>
	 *
	 * @param localDate a {@link org.joda.time.LocalDate} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment setEndDate(final LocalDate localDate) {
		this.requireEndDate();
		final MutableDateTime mutableDateTime = this.end.toMutableDateTime();
		mutableDateTime.setDate(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
		this.end = mutableDateTime.toDateTime();
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getIdentity() {
		return String.format("%s - %s", this.assignee.getIdentity(), this.getCar().getIdentity());
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<SAppointmentTask> iterator() {
		return this.tasks.iterator();
	}

	private static class MSG {
		static final String BEGIN_NULL_MSG = "Begin dateTime for event must not be null";
		static final String END_NULL_MSG   = "End dateTime for event must not be null";
		static final String CAR_NULL_MSG   = "Car for event must not be null";
		static final String NO_TASK_MSG    = "SAppointment must contain at least one task [SAppointmentTask]";
	}
}
