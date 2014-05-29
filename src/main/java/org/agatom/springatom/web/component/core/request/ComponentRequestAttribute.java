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

package org.agatom.springatom.web.component.core.request;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ComponentRequestAttribute
		implements Serializable {
	private static final long   serialVersionUID = -4898954767253086160L;
	private              String path             = null;
	private              String type             = null;

	public String getType() {
		return this.type;
	}

	public ComponentRequestAttribute setType(final String displayAs) {
		this.type = displayAs;
		return this;
	}

	public String getPath() {
		return this.path;
	}

	public ComponentRequestAttribute setPath(final String path) {
		this.path = path;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(path, type);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ComponentRequestAttribute that = (ComponentRequestAttribute) o;

		return Objects.equal(this.path, that.path) &&
				Objects.equal(this.type, that.type);
	}
}
