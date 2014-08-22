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

    /**
     * <p>getBrand.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getBrand() {
        return this.manufacturingData.getBrand();
    }

    /**
     * <p>getModel.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getModel() {
        return this.manufacturingData.getModel();
    }

    /**
     * <p>getManufacturedIn.</p>
     *
     * @return a {@link com.neovisionaries.i18n.CountryCode} object.
     */
    public CountryCode getManufacturedIn() {
        return this.manufacturingData.getManufacturedIn();
    }

    /**
     * <p>getManufacturedBy.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getManufacturedBy() {
        return this.manufacturingData.getManufacturedBy();
    }

    /**
     * <p>setBrand.</p>
     *
     * @param brand a {@link java.lang.String} object.
     *
     * @return a {@link org.agatom.springatom.server.model.types.car.ManufacturingData} object.
     */
    public ManufacturingData setBrand(final String brand) {
        return this.manufacturingData.setBrand(brand);
    }

    /**
     * <p>setModel.</p>
     *
     * @param model a {@link java.lang.String} object.
     *
     * @return a {@link org.agatom.springatom.server.model.types.car.ManufacturingData} object.
     */
    public ManufacturingData setModel(final String model) {
        return this.manufacturingData.setModel(model);
    }

    /**
     * <p>setManufacturedBy.</p>
     *
     * @param manufacturedBy a {@link java.lang.String} object.
     *
     * @return a {@link org.agatom.springatom.server.service.vinNumber.model.VinManufacturingData} object.
     */
    public VinManufacturingData setManufacturedBy(final String manufacturedBy) {
        return this.manufacturingData.setManufacturedBy(manufacturedBy);
    }

    /**
     * <p>setManufacturedIn.</p>
     *
     * @param manufacturedIn a {@link com.neovisionaries.i18n.CountryCode} object.
     *
     * @return a {@link org.agatom.springatom.server.service.vinNumber.model.VinManufacturingData} object.
     */
    public VinManufacturingData setManufacturedIn(final CountryCode manufacturedIn) {
        return this.manufacturingData.setManufacturedIn(manufacturedIn);
    }

    /**
     * <p>Getter for the field <code>years</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Integer> getYears() {
        return this.years;
    }

    /**
     * <p>Setter for the field <code>years</code>.</p>
     *
     * @param years a {@link java.util.List} object.
     *
     * @return a {@link org.agatom.springatom.server.service.vinNumber.model.VinNumberData} object.
     */
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

    public FuelType getFuelType() {
        return fuelType;
    }

    public VinNumberData setFuelType(final FuelType fuelType) {
        this.fuelType = fuelType;
        return this;
    }
}
