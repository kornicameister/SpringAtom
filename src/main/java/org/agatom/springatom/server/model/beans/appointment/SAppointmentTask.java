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
	public static final  String TABLE_NAME       = "appointment_task";
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

	@Override
	public String getTask() {
		return task;
	}

	@Override
	public SAppointment getAppointment() {
		return this.appointment;
	}

	public SAppointmentTask setAppointment(final SAppointment appointments) {
		this.appointment = appointments;
		return this;
	}

	@Override
	public AppointmentTaskType getType() {
		return type;
	}

	public SAppointmentTask setType(final String type) {
		return this.setType(AppointmentTaskType.valueOf(type));
	}

	public SAppointmentTask setType(final AppointmentTaskType type) {
		this.type = type;
		return this;
	}

	public SAppointmentTask setTask(final String task) {
		this.task = task;
		return this;
	}

	@Override
	public String getIdentity() {
		return this.getType().toString();
	}
}
