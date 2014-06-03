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

import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.agatom.springatom.server.model.types.appointment.AppointmentTask;
import org.agatom.springatom.server.model.types.appointment.AppointmentTaskType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * <p>SAppointmentTask class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SAppointmentTask.TABLE_NAME)
@Entity(name = SAppointmentTask.ENTITY_NAME)
@ReportableEntity
@AttributeOverride(name = "id", column = @Column(name = "idSAppointmentTask", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SAppointmentTask
		extends PersistentObject<Long>
		implements AppointmentTask {
	/** Constant <code>TABLE_NAME="appointment_task"</code> */
	public static final  String TABLE_NAME       = "appointment_task";
	/** Constant <code>ENTITY_NAME="SAppointmentTask"</code> */
	public static final  String ENTITY_NAME      = "SAppointmentTask";
	private static final long   serialVersionUID = -300491275397373687L;
	@NotEmpty
	@Length(min = 10, max = 444)
	@Column(name = "sat_task", nullable = false, length = 444)
	private String              task;
	@Type(type = "org.hibernate.type.EnumType")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "sat_type", updatable = true, unique = false, length = 50, nullable = false)
	private AppointmentTaskType type;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sat_app", referencedColumnName = "idSAppointment", updatable = false)
	private SAppointment        appointment;

	/** {@inheritDoc} */
	@Override
	public String getTask() {
		return task;
	}

	/** {@inheritDoc} */
	@Override
	public SAppointment getAppointment() {
		return this.appointment;
	}

	/**
	 * <p>Setter for the field <code>appointment</code>.</p>
	 *
	 * @param appointments a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointmentTask} object.
	 */
	public SAppointmentTask setAppointment(final SAppointment appointments) {
		this.appointment = appointments;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public AppointmentTaskType getType() {
		return type;
	}

	/**
	 * <p>Setter for the field <code>type</code>.</p>
	 *
	 * @param type a {@link org.agatom.springatom.server.model.types.appointment.AppointmentTaskType} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointmentTask} object.
	 */
	public SAppointmentTask setType(final AppointmentTaskType type) {
		this.type = type;
		return this;
	}

	/**
	 * <p>Setter for the field <code>task</code>.</p>
	 *
	 * @param task a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointmentTask} object.
	 */
	public SAppointmentTask setTask(final String task) {
		this.task = task;
		return this;
	}

	/**
	 * <p>Setter for the field <code>type</code>.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointmentTask} object.
	 */
	public SAppointmentTask setType(final String type) {
		return this.setType(AppointmentTaskType.valueOf(type));
	}

	/** {@inheritDoc} */
	@Override
	public String getIdentity() {
		return this.getType().toString();
	}
}
