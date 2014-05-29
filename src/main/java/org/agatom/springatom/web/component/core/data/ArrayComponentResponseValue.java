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
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Set;

/**
 * {@code ArrayComponentResponseValue} is designed to carry multiple {@link org.agatom.springatom.web.component.core.data.ComponentResponseValue} values
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ArrayComponentResponseValue
		implements ComponentResponseValue, Iterable<ComponentResponseValue> {
	private static final long                        serialVersionUID = -4216098968674546827L;
	private              Set<ComponentResponseValue> data             = Sets.newHashSet();

	public boolean addValue(final ComponentResponseValue value) {
		return this.data.add(value);
	}

	@Override
	public Iterator<ComponentResponseValue> iterator() {
		return this.getData().iterator();
	}

	public Set<ComponentResponseValue> getData() {
		return this.data;
	}

	public ArrayComponentResponseValue setData(final Set<ComponentResponseValue> data) {
		this.data = data;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(data);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ArrayComponentResponseValue that = (ArrayComponentResponseValue) o;

		return Objects.equal(this.data, that.data);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("size", this.size())
				.toString();
	}

	public int size() {
		return this.data.size();
	}
}
