/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.server.model.types.appointment;

import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.types.activity.AssignedActivity;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.List;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 02.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Appointment
		extends AssignedActivity {
	/**
	 * <p>isClosed.</p>
	 *
	 * @return a boolean.
	 */
	boolean isClosed();

	/**
	 * <p>isAllDay.</p>
	 *
	 * @return a boolean.
	 */
	boolean isAllDay();

	/**
	 * <p>getTasks.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<SAppointmentTask> getTasks();

	/**
	 * <p>getBegin.</p>
	 *
	 * @return a {@link org.joda.time.DateTime} object.
	 */
	DateTime getBegin();

	/**
	 * <p>getEnd.</p>
	 *
	 * @return a {@link org.joda.time.DateTime} object.
	 */
	DateTime getEnd();

	/**
	 * <p>getInterval.</p>
	 *
	 * @return a {@link org.joda.time.Interval} object.
	 */
	Interval getInterval();

	/**
	 * <p>getCar.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 */
	SCar getCar();
}
