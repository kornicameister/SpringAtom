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

package org.agatom.springatom.web.component.core.data;

import com.google.common.base.Objects;

/**
 * {@code SingleComponentResponseValue} describes single value sent in a response to the client.
 * Carries information about the:
 * <ol>
 * <li>{@link #path}</li>
 * <li>{@link #value}</li>
 * <li>{@link #renderType}</li>
 * <li>{@link #valueType}</li>
 * </ol>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class SingleComponentResponseValue
		implements ComponentResponseValue {
	private static final long   serialVersionUID = 6036509201003171980L;
	protected String path       = null;
	protected Object value      = null;
	protected String renderType = null;
	protected String valueType  = null;

	public String getPath() {
		return path;
	}

	public SingleComponentResponseValue setPath(final String path) {
		this.path = path;
		return this;
	}

	public Object getValue() {
		return value;
	}

	public SingleComponentResponseValue setValue(final Object value) {
		this.value = value;
		return this;
	}

	public String getRenderType() {
		return renderType;
	}

	public SingleComponentResponseValue setRenderType(final String renderType) {
		this.renderType = renderType;
		return this;
	}

	public String getValueType() {
		return valueType;
	}

	public SingleComponentResponseValue setValueType(final String valueType) {
		this.valueType = valueType;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(path, value, renderType, valueType);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SingleComponentResponseValue that = (SingleComponentResponseValue) o;

		return Objects.equal(this.path, that.path) &&
				Objects.equal(this.value, that.value) &&
				Objects.equal(this.renderType, that.renderType) &&
				Objects.equal(this.valueType, that.valueType);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("path", path)
				.add("value", value)
				.add("renderType", renderType)
				.add("valueType", valueType)
				.toString();
	}
}
