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

package org.agatom.springatom.server.model.beans.car.embeddable;

import com.google.common.base.Objects;
import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.server.model.types.PersistentIdentity;
import org.agatom.springatom.server.model.types.car.ManufacturingData;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import static org.agatom.springatom.core.RegexpPatterns.BIG_FIRST_LETTER_PATTERN;

/**
 * {@code SCarMasterManufacturingData} is {@link javax.persistence.Embeddable} model
 * embedded into {@link org.agatom.springatom.server.model.beans.car.SCarMaster}
 * instance and hence providing information about single {@link org.agatom.springatom.server.model.beans.car.SCar}
 * instance
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable
public class SCarMasterManufacturingData
		implements ManufacturingData, PersistentIdentity {
	private static final long   serialVersionUID = 545689870492641597L;
	@NotBlank
	@Pattern(regexp = BIG_FIRST_LETTER_PATTERN)
	@Column(nullable = false, length = 45, updatable = true, insertable = true, name = "brand")
	private              String brand            = null;
	@NotBlank
	@Pattern(regexp = BIG_FIRST_LETTER_PATTERN)
	@Column(nullable = false, length = 45, updatable = true, insertable = true, name = "service")
	private              String model            = null;
	@Column(nullable = false, length = 100, updatable = true, insertable = true, name = "scm_md_mi")
	private              String manufacturedIn   = null;
	@Column(nullable = false, length = 100, updatable = true, insertable = true, name = "scd_md_mb")
	private              String manufacturedBy   = null;

	public SCarMasterManufacturingData() {
		super();
	}

	public SCarMasterManufacturingData(final String brand, final String model) {
		this.brand = brand;
		this.model = model;
	}

	@Override
	public String getBrand() {
		return brand;
	}

	public SCarMasterManufacturingData setBrand(final String brand) {
		this.brand = brand;
		return this;
	}

	@Override
	public String getModel() {
		return model;
	}

	public SCarMasterManufacturingData setModel(final String model) {
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

	public SCarMasterManufacturingData setManufacturedIn(final CountryCode manufacturedIn) {
		this.manufacturedIn = manufacturedIn.getAlpha3();
		return this;
	}

	@Override
	public String getManufacturedBy() {
		return manufacturedBy;
	}

	public SCarMasterManufacturingData setManufacturedBy(final String manufacturedBy) {
		this.manufacturedBy = manufacturedBy;
		return this;
	}

	@Override
	public String getIdentity() {
		return String.format("%s %s", this.brand, this.model);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(brand, model, manufacturedIn, manufacturedBy);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SCarMasterManufacturingData that = (SCarMasterManufacturingData) o;

		return Objects.equal(this.brand, that.brand) &&
				Objects.equal(this.model, that.model) &&
				Objects.equal(this.manufacturedIn, that.manufacturedIn) &&
				Objects.equal(this.manufacturedBy, that.manufacturedBy);
	}
}
