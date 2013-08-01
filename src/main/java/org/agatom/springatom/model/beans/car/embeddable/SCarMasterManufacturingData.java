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

package org.agatom.springatom.model.beans.car.embeddable;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

import static org.agatom.springatom.util.RegexpPatterns.BIG_FIRST_LETTER_PATTERN;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable
public class SCarMasterManufacturingData
        implements Serializable {
    @Index(name = "scmmd_brand")
    @NotBlank
    @Pattern(regexp = BIG_FIRST_LETTER_PATTERN)
    @Column(nullable = false,
            length = 45,
            updatable = true,
            insertable = true,
            name = "brand")
    private String brand;
    @Index(name = "scmmd_model")
    @NotBlank
    @Pattern(regexp = BIG_FIRST_LETTER_PATTERN)
    @Column(nullable = false,
            length = 45,
            updatable = true,
            insertable = true,
            name = "model")
    private String model;

    public SCarMasterManufacturingData() {
        super();
    }

    public SCarMasterManufacturingData(final String brand, final String model) {
        this.brand = brand;
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public SCarMasterManufacturingData setBrand(final String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public SCarMasterManufacturingData setModel(final String model) {
        this.model = model;
        return this;
    }

    @Override
    public int hashCode() {
        int result = brand != null ? brand.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SCarMasterManufacturingData)) {
            return false;
        }

        SCarMasterManufacturingData that = (SCarMasterManufacturingData) o;

        return !(brand != null ? !brand.equals(that.brand) : that.brand != null)
                && !(model != null ? !model.equals(that.model) : that.model != null);
    }
}
