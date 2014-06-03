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

package org.agatom.springatom.web.rbuilder.conversion.type;

import com.google.common.base.Objects;
import org.agatom.springatom.web.rbuilder.conversion.ColumnTypeConversionBranch;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;

import java.io.Serializable;

/**
 * <p>RBuilderConvertiblePair class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class RBuilderConvertiblePair
		implements Serializable {
	private static final long serialVersionUID = 4975825247135266596L;
	private ConvertiblePair            convertiblePair;
	private ColumnTypeConversionBranch branch;

	/**
	 * <p>Getter for the field <code>convertiblePair</code>.</p>
	 *
	 * @return a {@link org.springframework.core.convert.converter.GenericConverter.ConvertiblePair} object.
	 */
	public ConvertiblePair getConvertiblePair() {
		return convertiblePair;
	}

	/**
	 * <p>Setter for the field <code>convertiblePair</code>.</p>
	 *
	 * @param convertiblePair a {@link org.springframework.core.convert.converter.GenericConverter.ConvertiblePair} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.conversion.type.RBuilderConvertiblePair} object.
	 */
	public RBuilderConvertiblePair setConvertiblePair(final ConvertiblePair convertiblePair) {
		this.convertiblePair = convertiblePair;
		return this;
	}

	/**
	 * <p>Getter for the field <code>branch</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.conversion.ColumnTypeConversionBranch} object.
	 */
	public ColumnTypeConversionBranch getBranch() {
		return branch;
	}

	/**
	 * <p>Setter for the field <code>branch</code>.</p>
	 *
	 * @param branch a {@link org.agatom.springatom.web.rbuilder.conversion.ColumnTypeConversionBranch} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.conversion.type.RBuilderConvertiblePair} object.
	 */
	public RBuilderConvertiblePair setBranch(final ColumnTypeConversionBranch branch) {
		this.branch = branch;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(convertiblePair, branch);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		RBuilderConvertiblePair that = (RBuilderConvertiblePair) o;

		return Objects.equal(this.convertiblePair, that.convertiblePair) &&
				Objects.equal(this.branch, that.branch);
	}
}
