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

package org.agatom.springatom.server.service.domain;

import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.car.SCarMaster;
import org.agatom.springatom.server.service.support.constraints.BrandOrModel;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>SCarMasterService interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SCarMasterService
		extends SBasicService<SCarMaster, Long> {

	/**
	 * <p>findByBrand.</p>
	 *
	 * @param brand a {@link java.lang.String} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<SCarMaster> findByBrand(
			@BrandOrModel
			final String... brand);

	/**
	 * <p>findByModel.</p>
	 *
	 * @param model a {@link java.lang.String} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<SCarMaster> findByModel(
			@BrandOrModel
			final String... model);

	/**
	 * <p>findOne.</p>
	 *
	 * @param brand a {@link java.lang.String} object.
	 * @param model a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCarMaster} object.
	 */
	SCarMaster findOne(
			@BrandOrModel
			final String brand,
			@BrandOrModel
			final String model);

	/**
	 * <p>findChildren.</p>
	 *
	 * @param masterIds a {@link java.lang.Long} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<SCar> findChildren(
			@NotNull
			final Long... masterIds);
}
