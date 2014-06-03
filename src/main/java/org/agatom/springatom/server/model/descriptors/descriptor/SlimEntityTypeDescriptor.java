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

package org.agatom.springatom.server.model.descriptors.descriptor;

import com.google.common.base.Objects;
import org.agatom.springatom.server.model.descriptors.SlimEntityDescriptor;
import org.springframework.util.ClassUtils;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class SlimEntityTypeDescriptor<X>
		implements SlimEntityDescriptor<X> {
	private static final long serialVersionUID = 5735047201811977565L;
	private final Class<X> javaClass;
	private final String   name;

	/**
	 * <p>Constructor for SlimEntityTypeDescriptor.</p>
	 *
	 * @param name      a {@link java.lang.String} object.
	 * @param javaClass a {@link java.lang.Class} object.
	 */
	public SlimEntityTypeDescriptor(final String name, final Class<X> javaClass) {
		this.javaClass = javaClass;
		this.name = name;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public Class<X> getJavaClass() {
		return this.javaClass;
	}

	/** {@inheritDoc} */
	@Override
	public String getJavaClassName() {
		return this.javaClass.getName();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(javaClass, name);
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

		SlimEntityTypeDescriptor<?> that = (SlimEntityTypeDescriptor<?>) o;

		return Objects.equal(this.javaClass, that.javaClass) &&
				Objects.equal(this.name, that.name);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(name)
				.addValue(ClassUtils.getShortName(javaClass))
				.toString();
	}
}
