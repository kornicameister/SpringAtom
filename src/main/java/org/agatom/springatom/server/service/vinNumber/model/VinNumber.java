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
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Maps;
import org.agatom.springatom.server.service.vinNumber.exception.VinNumberServiceException;
import org.springframework.hateoas.Identifiable;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Map;

/**
 * {@code VinNumber} holds reference to the {@code vinNumber}. Provides
 * basis splitting the number into meaningful parts of {@link org.agatom.springatom.server.service.vinNumber.model.VinNumberElement}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 08.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class VinNumber
		implements Serializable, Comparable<VinNumber>, Identifiable<String> {
	private static final long                          serialVersionUID = -2070896970742015138L;
	private final        Map<VinNumberElement, String> elementMap       = Maps.newHashMapWithExpectedSize(3);
	private              String                        vinNumber        = null;

	private VinNumber(final String vinNumber) throws VinNumberServiceException {
		try {
			Assert.hasText(vinNumber, "VinNumber must not be empty or null");
			Assert.isTrue(vinNumber.length() == 17, "VinNumber must have correct length");
		} catch (Exception exp) {
			throw new VinNumberServiceException("VinNumber is either null or has insufficient length 17", exp);
		}
		this.vinNumber = vinNumber;
		this.splitVinNumber();
	}

	private void splitVinNumber() throws VinNumberServiceException {
		try {
			this.elementMap.put(VinNumberElement.WMI, this.vinNumber.substring(0, 3));
			this.elementMap.put(VinNumberElement.VDS, this.vinNumber.substring(3, 9));
			this.elementMap.put(VinNumberElement.VIS, this.vinNumber.substring(9, this.vinNumber.length()));
		} catch (Exception exp) {
			throw new VinNumberServiceException(String.format("VinNumber %s has invalid form", this.vinNumber), exp);
		}
	}

	/**
	 * Constructs new {@code VinNumber}
	 *
	 * @param vinNumber vin number to be used
	 *
	 * @return {@code VinNumber}
	 *
	 * @throws VinNumberServiceException in case of any error
	 */
	public static VinNumber newVinNumber(final String vinNumber) throws VinNumberServiceException {
		return new VinNumber(vinNumber);
	}

	/**
	 * Returns {@link org.agatom.springatom.server.service.vinNumber.model.VinNumberElement#WMI}
	 *
	 * @return WMI number
	 */
	public String getWmi() {
		return this.elementMap.get(VinNumberElement.WMI);
	}

	/**
	 * Returns {@link org.agatom.springatom.server.service.vinNumber.model.VinNumberElement#VDS}
	 *
	 * @return VDS number
	 */
	public String getVds() {
		return this.elementMap.get(VinNumberElement.VDS);
	}

	/**
	 * Returns {@link org.agatom.springatom.server.service.vinNumber.model.VinNumberElement#VIS}
	 *
	 * @return VIS number
	 */
	public String getVis() {
		return this.elementMap.get(VinNumberElement.VIS);
	}

	public String getElement(final VinNumberElement element) {
		return this.elementMap.get(element);
	}

	@Override
	public int compareTo(@Nonnull final VinNumber vinNumber) {
		return ComparisonChain.start()
				.compare(this.vinNumber, vinNumber.vinNumber)
				.result();
	}

	@Override
	public String getId() {
		return this.getVinNumber();
	}

	public String getVinNumber() {
		return vinNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(elementMap, vinNumber);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		VinNumber that = (VinNumber) o;

		return Objects.equal(this.elementMap, that.elementMap) &&
				Objects.equal(this.vinNumber, that.vinNumber);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(elementMap)
				.toString();
	}
}
