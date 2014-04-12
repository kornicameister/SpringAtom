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

import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.server.model.types.car.FuelType;
import org.agatom.springatom.server.model.types.car.ManufacturingData;

import java.io.Serializable;
import java.util.List;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 08.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class VinNumberData
		implements Serializable {
	private static final long                 serialVersionUID  = -6905013953824625804L;
	private              VinNumber            vinNumber         = null;
	private              VinManufacturingData manufacturingData = new VinManufacturingData();
	private              String               type              = null;
	private              String               engineSeries      = null;
	private              String               engineType        = null;
	private              FuelType             fuelType          = null;
	private              List<Integer>        years             = null;

	public String getBrand() {
		return this.manufacturingData.getBrand();
	}

	public String getModel() {
		return this.manufacturingData.getModel();
	}

	public CountryCode getManufacturedIn() {
		return this.manufacturingData.getManufacturedIn();
	}

	public String getManufacturedBy() {
		return this.manufacturingData.getManufacturedBy();
	}

	public ManufacturingData setBrand(final String brand) {
		return this.manufacturingData.setBrand(brand);
	}

	public ManufacturingData setModel(final String model) {
		return this.manufacturingData.setModel(model);
	}

	public VinManufacturingData setManufacturedBy(final String manufacturedBy) {
		return this.manufacturingData.setManufacturedBy(manufacturedBy);
	}

	public VinManufacturingData setManufacturedIn(final CountryCode manufacturedIn) {
		return this.manufacturingData.setManufacturedIn(manufacturedIn);
	}

	public List<Integer> getYears() {
		return this.years;
	}

	public VinNumberData setYears(final List<Integer> years) {
		this.years = years;
		return this;
	}
}
