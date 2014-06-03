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

import org.agatom.springatom.server.model.beans.issue.SIssue;
import org.agatom.springatom.server.model.types.ReportableEntity;

import javax.persistence.*;

/**
 * <p>SAppointmentIssue class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = SAppointmentIssue.ENTITY_NAME)
@ReportableEntity
@DiscriminatorValue(value = "appointment")
public class SAppointmentIssue
		extends SIssue {
	/** Constant <code>ENTITY_NAME="SAppointmentIssue"</code> */
	public static final  String ENTITY_NAME      = "SAppointmentIssue";
	private static final long   serialVersionUID = 8658810841216821601L;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "sai_app", referencedColumnName = "idSAppointment", updatable = false)
	private SAppointment appointment;

	/**
	 * <p>Getter for the field <code>appointment</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	public SAppointment getAppointment() {
		return appointment;
	}

	/**
	 * <p>Setter for the field <code>appointment</code>.</p>
	 *
	 * @param appointment a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointmentIssue} object.
	 */
	public SAppointmentIssue setAppointment(final SAppointment appointment) {
		this.appointment = appointment;
		return this;
	}
}
