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

package org.agatom.springatom.server.model.beans.calendar;

import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;

/**
 * {@code SCalendar} is a gathering component that is associated with {@link org.agatom.springatom.server.model.beans.appointment.SAppointment}
 * created by the user who reported the event <b>{@link org.agatom.springatom.server.model.beans.appointment.SAppointment#getReporter()}</b>.
 * It is worth to notice that an instance of an {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} is also visible in the
 * {@code SCalendar} of the {@link org.agatom.springatom.server.model.beans.appointment.SAppointment#getAssignee()} but hence such instance
 * must be immutable.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Table(name = SCalendar.TABLE_NAME)
@Entity(name = SCalendar.ENTITY_NAME)
@ReportableEntity
@AttributeOverride(name = "id", column = @Column(name = "id_user_calendar", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SCalendar
		extends PersistentObject<Long> {
	/** Constant <code>TABLE_NAME="suser_calendar"</code> */
	public static final  String TABLE_NAME       = "suser_calendar";
	/** Constant <code>ENTITY_NAME="org.springatom.calendar.SCalendar"</code> */
	public static final  String ENTITY_NAME      = "org.springatom.calendar.SCalendar";
	private static final long   serialVersionUID = -3784841008638697396L;
	/**
	 * {@code name} is this calendar name assigned via {@link #owner}
	 */
	@Column(name = "cal_name", nullable = false, length = 50)
	private String name;
	/**
	 * {@code color} is the {@link java.awt.Color} representation of the color associated with calendar
	 */
	@Column(name = "cal_hex_color", nullable = false)
	private Color  color;
	/**
	 * {@code owner} of the <b>this</b> calendar
	 */
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "cal_owner", referencedColumnName = "idSUser", updatable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private SUser  owner;

	/**
	 * <p>Getter for the field <code>color</code>.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * <p>Setter for the field <code>color</code>.</p>
	 *
	 * @param color a {@link java.awt.Color} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.calendar.SCalendar} object.
	 */
	public SCalendar setColor(final Color color) {
		this.color = color;
		return this;
	}

	/**
	 * <p>Getter for the field <code>owner</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 */
	public SUser getOwner() {
		return owner;
	}

	/**
	 * <p>Setter for the field <code>owner</code>.</p>
	 *
	 * @param owner a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.calendar.SCalendar} object.
	 */
	public SCalendar setOwner(final SUser owner) {
		this.owner = owner;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getIdentity() {
		return this.getName();
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.calendar.SCalendar} object.
	 */
	public SCalendar setName(final String name) {
		this.name = name;
		return this;
	}
}
