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

package org.agatom.springatom.server.service.vinNumber.model;

import com.google.common.base.Objects;
import org.agatom.springatom.server.model.types.car.ManufacturingData;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 08.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class VinManufacturingData
		implements ManufacturingData {
	private static final long   serialVersionUID = -4128505949300532237L;
	private              String brand            = null;
	private              String model            = null;

	public ManufacturingData setBrand(final String brand) {
		this.brand = brand;
		return this;
	}

	public ManufacturingData setModel(final String model) {
		this.model = model;
		return this;
	}

	@Override
	public String getBrand() {
		return this.brand;
	}

	@Override
	public String getModel() {
		return this.model;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(brand, model);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		VinManufacturingData that = (VinManufacturingData) o;

		return Objects.equal(this.brand, that.brand) &&
				Objects.equal(this.model, that.model);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("brand", brand)
				.add("model", model)
				.toString();
	}
}
