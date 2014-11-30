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

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.neovisionaries.i18n.CountryCode;

import java.io.Serializable;

public class VinManufacturingData
        implements Serializable {
    private static final long        serialVersionUID = -4128505949300532237L;
    private              String      brand            = null;
    private              String      model            = null;
    private              CountryCode manufacturedIn   = null;
    private              String      manufacturedBy   = null;

    public String getBrand() {
        return this.brand;
    }

    public VinManufacturingData setBrand(final String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return this.model;
    }

    public VinManufacturingData setModel(final String model) {
        this.model = model;
        return this;
    }

    public CountryCode getManufacturedIn() {
        return this.manufacturedIn;
    }

    public VinManufacturingData setManufacturedIn(final CountryCode manufacturedIn) {
        this.manufacturedIn = manufacturedIn;
        return this;
    }

    public String getManufacturedBy() {
        return this.manufacturedBy;
    }

    public VinManufacturingData setManufacturedBy(final String manufacturedBy) {
        this.manufacturedBy = manufacturedBy;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(brand, model, manufacturedIn, manufacturedBy);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VinManufacturingData that = (VinManufacturingData) o;

        return Objects.equal(this.brand, that.brand) &&
                Objects.equal(this.model, that.model) &&
                Objects.equal(this.manufacturedIn, that.manufacturedIn) &&
                Objects.equal(this.manufacturedBy, that.manufacturedBy);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(brand)
                .addValue(model)
                .addValue(manufacturedIn)
                .addValue(manufacturedBy)
                .toString();
    }
}
