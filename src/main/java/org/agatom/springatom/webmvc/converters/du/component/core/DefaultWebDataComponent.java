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

package org.agatom.springatom.webmvc.converters.du.component.core;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.agatom.springatom.web.component.core.EmbeddableComponent;
import org.agatom.springatom.web.component.core.elements.DefaultComponent;
import org.agatom.springatom.webmvc.converters.du.component.WebDataComponent;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 31.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class DefaultWebDataComponent<T>
		extends DefaultComponent
		implements WebDataComponent<T> {
	private static final long     serialVersionUID = -9193939519358944467L;
	private String   id       = null;
	private T        data     = null;
	private Class<?> dataType = null;
	private              int      position         = -1;

	/** {@inheritDoc} */
	@Override
	public T getData() {
		return this.data;
	}

	/**
	 * <p>Setter for the field <code>data</code>.</p>
	 *
	 * @param data a T object.
	 *
	 * @return a {@link org.agatom.springatom.webmvc.converters.du.component.core.DefaultWebDataComponent} object.
	 */
	public DefaultWebDataComponent setData(final T data) {
		this.data = data;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getDataType() {
		return this.dataType;
	}

	/**
	 * <p>Setter for the field <code>dataType</code>.</p>
	 *
	 * @param dataType a {@link java.lang.Class} object.
	 *
	 * @return a {@link org.agatom.springatom.webmvc.converters.du.component.core.DefaultWebDataComponent} object.
	 */
	public DefaultWebDataComponent setDataType(final Class<?> dataType) {
		this.dataType = dataType;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * <p>Setter for the field <code>id</code>.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.webmvc.converters.du.component.core.DefaultWebDataComponent} object.
	 */
	public DefaultWebDataComponent setId(final String id) {
		this.id = id;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int getPosition() {
		return this.position;
	}

	/** {@inheritDoc} */
	@Override
	public void setPosition(final int position) {
		this.position = position;
	}

	@Override
	public final String getUiType() {
		return StringUtils.uncapitalize(ClassUtils.getShortName(this.getClass()));
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(@Nonnull final EmbeddableComponent o) {
		return ComparisonChain.start()
				.compare(this.position, o.getPosition())
				.result();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(id, position);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DefaultWebDataComponent that = (DefaultWebDataComponent) o;

		return Objects.equal(this.id, that.id) &&
				Objects.equal(this.position, that.position);
	}
}
