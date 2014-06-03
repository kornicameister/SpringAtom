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

package org.agatom.springatom.server.model.conversion.converter.impl;

import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.conversion.annotation.PersistableConverterUtility;
import org.agatom.springatom.server.model.conversion.converter.PersistableConverterImpl;
import org.apache.log4j.Logger;

/**
 * {@code AppointmentConverter} is a default converter for {@link org.agatom.springatom.server.model.beans.appointment.SAppointment}
 * Returns String representation containing:
 * <ul>
 * <li>{@link org.agatom.springatom.server.model.beans.appointment.SAppointment#getBegin()}</li>
 * <li>{@link org.agatom.springatom.server.model.beans.appointment.SAppointment#getEnd()}</li>
 * <li>{@link org.agatom.springatom.server.model.beans.appointment.SAppointment#getCar()}</li>
 * </ul>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@PersistableConverterUtility(selector = "car")
public class AppointmentCarConverter
		extends PersistableConverterImpl<SAppointment> {

	/** {@inheritDoc} */
	@Override
	public String convert(final SAppointment source) {
		try {
			final SCar car = source.getCar();
			if (car != null) {
				return car.getLicencePlate();
			}
		} catch (Exception exp) {
			Logger.getLogger(this.getClass()).warn(String.format("Failed to extract car value for %s", source), exp);
		}
		return null;
	}

}
