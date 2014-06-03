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

package org.agatom.springatom.web.component.core.elements.link;

import com.google.common.base.Objects;
import org.agatom.springatom.web.component.core.ComponentValue;
import org.springframework.hateoas.Link;

import java.io.Serializable;

/**
 * <p>BuilderLink class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class BuilderLink
		implements ComponentValue {
	private static final long serialVersionUID = -5825562954435267927L;
	private final Serializable contextKey;
	private final Class<?>     contextClass;
	private final String       builderId;
	private final Link         link;

	/**
	 * <p>Constructor for BuilderLink.</p>
	 *
	 * @param builderId    a {@link java.lang.String} object.
	 * @param contextClass a {@link java.lang.Class} object.
	 * @param contextKey   a {@link java.io.Serializable} object.
	 * @param link         a {@link org.springframework.hateoas.Link} object.
	 */
	public BuilderLink(final String builderId,
	                   final Class<?> contextClass,
	                   final Serializable contextKey,
	                   final Link link) {
		this.builderId = builderId;
		this.contextClass = contextClass;
		this.contextKey = contextKey;
		this.link = link;
	}

	/**
	 * <p>Getter for the field <code>contextKey</code>.</p>
	 *
	 * @return a {@link java.io.Serializable} object.
	 */
	public Serializable getContextKey() {
		return contextKey;
	}

	/**
	 * <p>Getter for the field <code>contextClass</code>.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getContextClass() {
		return contextClass;
	}

	/**
	 * <p>getContextClassName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getContextClassName() {
		return contextClass.getName();
	}

	/**
	 * <p>Getter for the field <code>builderId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getBuilderId() {
		return builderId;
	}

	/**
	 * <p>Getter for the field <code>link</code>.</p>
	 *
	 * @return a {@link org.springframework.hateoas.Link} object.
	 */
	public Link getLink() {
		return this.link;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(builderId, contextClass, contextKey);
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

		BuilderLink that = (BuilderLink) o;

		return Objects.equal(this.builderId, that.builderId) &&
				Objects.equal(this.contextClass, that.contextClass) &&
				Objects.equal(this.contextKey, that.contextKey);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(builderId)
				.addValue(contextClass)
				.addValue(contextKey)
				.toString();
	}
}
