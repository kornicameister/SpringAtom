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

package org.agatom.springatom.data.vin.model;

import com.neovisionaries.i18n.CountryCode;

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
    private              String               fuelType          = null;
    private              List<Integer>        years             = null;

    public String getBrand() {
        return this.manufacturingData.getBrand();
    }

    public VinNumberData setBrand(final String brand) {
        this.manufacturingData.setBrand(brand);
        return this;
    }

    public String getModel() {
        return this.manufacturingData.getModel();
    }

    public VinNumberData setModel(final String model) {
        this.manufacturingData.setModel(model);
        return this;
    }

    public CountryCode getManufacturedIn() {
        return this.manufacturingData.getManufacturedIn();
    }

    public VinNumberData setManufacturedIn(final CountryCode manufacturedIn) {
        this.manufacturingData.setManufacturedIn(manufacturedIn);
        return this;
    }

    public String getManufacturedBy() {
        return this.manufacturingData.getManufacturedBy();
    }

    public VinNumberData setManufacturedBy(final String manufacturedBy) {
        this.manufacturingData.setManufacturedBy(manufacturedBy);
        return this;
    }

    public List<Integer> getYears() {
        return this.years;
    }

    public VinNumberData setYears(final List<Integer> years) {
        this.years = years;
        return this;
    }

    public VinNumber getVinNumber() {
        return vinNumber;
    }

    public VinNumberData setVinNumber(final VinNumber vinNumber) {
        this.vinNumber = vinNumber;
        return this;
    }

    public VinManufacturingData getManufacturingData() {
        return manufacturingData;
    }

    public VinNumberData setManufacturingData(final VinManufacturingData manufacturingData) {
        this.manufacturingData = manufacturingData;
        return this;
    }

    public String getType() {
        return type;
    }

    public VinNumberData setType(final String type) {
        this.type = type;
        return this;
    }

    public String getEngineSeries() {
        return engineSeries;
    }

    public VinNumberData setEngineSeries(final String engineSeries) {
        this.engineSeries = engineSeries;
        return this;
    }

    public String getEngineType() {
        return engineType;
    }

    public VinNumberData setEngineType(final String engineType) {
        this.engineType = engineType;
        return this;
    }

    public String getFuelType() {
        return fuelType;
    }

    public VinNumberData setFuelType(final String fuelType) {
        this.fuelType = fuelType;
        return this;
    }
}
