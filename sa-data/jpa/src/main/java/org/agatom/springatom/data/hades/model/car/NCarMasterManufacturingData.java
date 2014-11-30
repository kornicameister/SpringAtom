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

package org.agatom.springatom.data.hades.model.car;

import com.google.common.base.Objects;
import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.data.types.car.ManufacturingData;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

import static org.agatom.springatom.core.RegexpPatterns.BIG_FIRST_LETTER_PATTERN;

@Embeddable
public class NCarMasterManufacturingData
        implements ManufacturingData, Serializable {
    private static final long   serialVersionUID = 545689870492641597L;
    @NotBlank
    @Pattern(regexp = BIG_FIRST_LETTER_PATTERN)
    @Column(nullable = false, length = 45, updatable = true, insertable = true)
    private              String brand            = null;
    @NotBlank
    @Pattern(regexp = BIG_FIRST_LETTER_PATTERN)
    @Column(nullable = false, length = 45, updatable = true, insertable = true)
    private              String model            = null;
    @Column(nullable = false, length = 100, updatable = true, insertable = true)
    private              String manufacturedIn   = null;
    @Column(nullable = false, length = 100, updatable = true, insertable = true)
    private              String manufacturedBy   = null;

    public NCarMasterManufacturingData() {
        super();
    }

    public NCarMasterManufacturingData(final String brand, final String model) {
        this.brand = brand;
        this.model = model;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    public NCarMasterManufacturingData setBrand(final String brand) {
        this.brand = brand;
        return this;
    }

    @Override
    public String getModel() {
        return model;
    }

    public NCarMasterManufacturingData setModel(final String model) {
        this.model = model;
        return this;
    }

    @Override
    public CountryCode getManufacturedIn() {
        if (StringUtils.hasText(this.manufacturedIn)) {
            return CountryCode.getByCode(this.manufacturedIn);
        }
        return null;
    }

    public NCarMasterManufacturingData setManufacturedIn(final CountryCode manufacturedIn) {
        this.manufacturedIn = manufacturedIn.getAlpha3();
        return this;
    }

    @Override
    public String getManufacturedBy() {
        return manufacturedBy;
    }

    public NCarMasterManufacturingData setManufacturedBy(final String manufacturedBy) {
        this.manufacturedBy = manufacturedBy;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(brand, model, manufacturedIn, manufacturedBy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NCarMasterManufacturingData that = (NCarMasterManufacturingData) o;

        return Objects.equal(this.brand, that.brand) &&
                Objects.equal(this.model, that.model) &&
                Objects.equal(this.manufacturedIn, that.manufacturedIn) &&
                Objects.equal(this.manufacturedBy, that.manufacturedBy);
    }
}
