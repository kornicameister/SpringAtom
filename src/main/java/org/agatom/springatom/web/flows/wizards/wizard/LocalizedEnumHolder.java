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

package org.agatom.springatom.web.flows.wizards.wizard;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 13.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class LocalizedEnumHolder<T extends Enum>
		implements Serializable {
	private static final long   serialVersionUID = -7606927619896461320L;
	private              String label            = null;
	private              T      value            = null;

	/**
	 * <p>Getter for the field <code>label</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * <p>Setter for the field <code>label</code>.</p>
	 *
	 * @param label a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.flows.wizards.wizard.LocalizedEnumHolder} object.
	 */
	public LocalizedEnumHolder setLabel(final String label) {
		this.label = label;
		return this;
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a T object.
	 */
	public T getValue() {
		return this.value;
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a T object.
	 *
	 * @return a {@link org.agatom.springatom.web.flows.wizards.wizard.LocalizedEnumHolder} object.
	 */
	public LocalizedEnumHolder setValue(final T value) {
		this.value = value;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(label, value);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LocalizedEnumHolder that = (LocalizedEnumHolder) o;

		return Objects.equal(this.label, that.label) &&
				Objects.equal(this.value, that.value);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(label)
				.addValue(value)
				.toString();
	}
}
