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

package org.agatom.springatom.web.component.core.request;

import com.google.common.base.Objects;

import java.util.Set;

/**
 * {@code AbstractComponentRequest} is a bean describing single request specified by either <b>table</b> or <b>infopage</b>
 *
 * @author kornicameister
 * @version 0.0.3
 * @since 0.0.1
 */
abstract public class AbstractComponentRequest
		implements ComponentRequest {
	private static final long                           serialVersionUID = 8264945607694867245L;
	protected            Set<ComponentRequestAttribute> attributes       = null;
	protected            Class<?>                       domain           = null;
	protected            Long                           id               = null;
	protected            Long                           version          = null;

	/**
	 * <p>Getter for the field <code>attributes</code>.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<ComponentRequestAttribute> getAttributes() {
		return this.attributes;
	}

	/**
	 * <p>Setter for the field <code>attributes</code>.</p>
	 *
	 * @param attributes a {@link java.util.Set} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.core.request.AbstractComponentRequest} object.
	 */
	public AbstractComponentRequest setAttributes(final Set<ComponentRequestAttribute> attributes) {
		this.attributes = attributes;
		return this;
	}

	/**
	 * <p>Getter for the field <code>domain</code>.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getDomain() {
		return this.domain;
	}

	/**
	 * <p>Setter for the field <code>domain</code>.</p>
	 *
	 * @param domain a {@link java.lang.Class} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.core.request.AbstractComponentRequest} object.
	 */
	public AbstractComponentRequest setDomain(final Class<?> domain) {
		this.domain = domain;
		return this;
	}

	/**
	 * <p>Getter for the field <code>id</code>.</p>
	 *
	 * @return a {@link java.lang.Long} object.
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * <p>Setter for the field <code>id</code>.</p>
	 *
	 * @param id a {@link java.lang.Long} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.core.request.AbstractComponentRequest} object.
	 */
	public AbstractComponentRequest setId(final Long id) {
		this.id = id;
		return this;
	}

	/**
	 * <p>Getter for the field <code>version</code>.</p>
	 *
	 * @return a {@link java.lang.Long} object.
	 */
	public Long getVersion() {
		return this.version;
	}

	/**
	 * <p>Setter for the field <code>version</code>.</p>
	 *
	 * @param version a {@link java.lang.Long} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.core.request.AbstractComponentRequest} object.
	 */
	public AbstractComponentRequest setVersion(final Long version) {
		this.version = version;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(attributes, domain, id, version);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AbstractComponentRequest that = (AbstractComponentRequest) o;

		return Objects.equal(this.attributes, that.attributes) &&
				Objects.equal(this.domain, that.domain) &&
				Objects.equal(this.id, that.id) &&
				Objects.equal(this.version, that.version);
	}

}
