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

package org.agatom.springatom.web.component.table.elements.extjs.feature;

import com.google.common.base.Objects;

/**
 * {@code AbstractNgTableFeature} is a foundation for features of {@link org.agatom.springatom.web.component.table.elements.extjs.NgTable}
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 03.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract class AbstractNgTableFeature
		implements NgTableFeature {
	private static final long   serialVersionUID = 8485389573075863861L;
	/**
	 * {@code ftype} is a property of the <b>feature</b>, uniquely identifying it to the ExtJS
	 */
	private              String ftype            = null;

	/**
	 * <p>Constructor for AbstractNgTableFeature.</p>
	 *
	 * @param feature a {@link AbstractNgTableFeature.Feature} object.
	 */
	protected AbstractNgTableFeature(final Feature feature) {
		this.ftype = feature.name().toLowerCase().replaceAll("_", "");
	}

	/** {@inheritDoc} */
	@Override
	public final String getFtype() {
		return ftype;
	}

	/** {@inheritDoc} */
	@Override
	public final int hashCode() {
		return Objects.hashCode(ftype);
	}

	/** {@inheritDoc} */
	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AbstractNgTableFeature that = (AbstractNgTableFeature) o;

		return Objects.equal(this.ftype, that.ftype);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("ftype", ftype)
				.toString();
	}

	protected static enum Feature {
		GROUPING,
		SUMMARY
	}
}
