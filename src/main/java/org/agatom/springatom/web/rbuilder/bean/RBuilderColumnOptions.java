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

package org.agatom.springatom.web.rbuilder.bean;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * <p>RBuilderColumnOptions class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class RBuilderColumnOptions
		implements Serializable {
	private static final long    serialVersionUID = -9114684272818588861L;
	private static final Boolean DEFAULT_EXCLUDED = Boolean.FALSE;
	private              boolean excluded         = DEFAULT_EXCLUDED;
	private static final Boolean DEFAULT_GROUP_BY = Boolean.FALSE;
	private              boolean groupBy          = DEFAULT_GROUP_BY;

	/**
	 * <p>isExcluded.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isExcluded() {
		return excluded;
	}

	/**
	 * <p>Setter for the field <code>excluded</code>.</p>
	 *
	 * @param excluded a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumnOptions} object.
	 */
	public RBuilderColumnOptions setExcluded(final boolean excluded) {
		this.excluded = excluded;
		return this;
	}

	/**
	 * <p>isGroupBy.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isGroupBy() {
		return groupBy;
	}

	/**
	 * <p>Setter for the field <code>groupBy</code>.</p>
	 *
	 * @param groupBy a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumnOptions} object.
	 */
	public RBuilderColumnOptions setGroupBy(final boolean groupBy) {
		this.groupBy = groupBy;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(excluded, groupBy);
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

		RBuilderColumnOptions that = (RBuilderColumnOptions) o;

		return Objects.equal(this.excluded, that.excluded) &&
				Objects.equal(this.groupBy, that.groupBy);
	}
}
