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

package org.agatom.springatom.web.component.infopages.provider.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.agatom.springatom.core.util.Localized;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * {@code InfoPageAttribute} class describes the single attribute available in {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPagePanel}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoPageAttribute
		implements Serializable, Comparable<InfoPageAttribute>, Localized {
	private static final long   serialVersionUID = -8718015686294718467L;
	private              String path             = null;
	private              String converter        = null;
	/**
	 * Optional: By default localization for attribute comes from domain object localized model
	 */
	private              String messageKey       = null;

	/**
	 * <p>Getter for the field <code>path</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * <p>Setter for the field <code>path</code>.</p>
	 *
	 * @param path a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPageAttribute} object.
	 */
	public InfoPageAttribute setPath(final String path) {
		this.path = path;
		return this;
	}

	/**
	 * <p>Getter for the field <code>converter</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getConverter() {
		return this.converter;
	}

	/**
	 * <p>Setter for the field <code>converter</code>.</p>
	 *
	 * @param converter a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPageAttribute} object.
	 */
	public InfoPageAttribute setConverter(final String converter) {
		this.converter = converter;
		return this;
	}

	/**
	 * <p>getFixedConverterClass.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getFixedConverterClass() {
		if (this.hasFixedConverter()) {
			return ClassUtils.resolveClassName(this.converter, this.getClass().getClassLoader());
		}
		return null;
	}

	/**
	 * <p>hasFixedConverter.</p>
	 *
	 * @return a boolean.
	 */
	public boolean hasFixedConverter() {
		if (StringUtils.hasText(this.converter)) {
			try {
				ClassUtils.resolveClassName(this.converter, this.getClass().getClassLoader());
				return true;
			} catch (IllegalArgumentException exp) {
				return false;
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String getMessageKey() {
		return this.messageKey;
	}

	/**
	 * <p>Setter for the field <code>messageKey</code>.</p>
	 *
	 * @param messageKey a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPageAttribute} object.
	 */
	public InfoPageAttribute setMessageKey(final String messageKey) {
		this.messageKey = messageKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(@Nonnull final InfoPageAttribute o) {
		return ComparisonChain.start()
				.compare(this.path, o.path)
				.result();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(path, converter);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InfoPageAttribute that = (InfoPageAttribute) o;

		return Objects.equal(this.path, that.path) &&
				Objects.equal(this.converter, that.converter);
	}
}
