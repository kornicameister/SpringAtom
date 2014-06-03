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

package org.agatom.springatom.server.model.types.car;

import org.agatom.springatom.server.model.beans.user.SUser;

import java.io.Serializable;

/**
 * {@code Car} wraps information about the single {@link org.agatom.springatom.server.model.types.car.Car}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 08.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Car
		extends Serializable {
	/**
	 * <p>getLicencePlate.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLicencePlate();

	/**
	 * <p>getOwner.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 */
	SUser getOwner();

	/**
	 * <p>getBrand.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBrand();

	/**
	 * <p>getModel.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getModel();

	/**
	 * <p>getFuelType.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.car.FuelType} object.
	 */
	FuelType getFuelType();

	/**
	 * <p>getYearOfProduction.</p>
	 *
	 * @return a {@link java.lang.Long} object.
	 */
	Long getYearOfProduction();
}
