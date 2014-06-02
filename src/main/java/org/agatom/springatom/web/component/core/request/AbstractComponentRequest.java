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

	public Set<ComponentRequestAttribute> getAttributes() {
		return this.attributes;
	}

	public AbstractComponentRequest setAttributes(final Set<ComponentRequestAttribute> attributes) {
		this.attributes = attributes;
		return this;
	}

	public Class<?> getDomain() {
		return this.domain;
	}

	public AbstractComponentRequest setDomain(final Class<?> domain) {
		this.domain = domain;
		return this;
	}

	public Long getId() {
		return this.id;
	}

	public AbstractComponentRequest setId(final Long id) {
		this.id = id;
		return this;
	}

	public Long getVersion() {
		return this.version;
	}

	public AbstractComponentRequest setVersion(final Long version) {
		this.version = version;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(attributes, domain, id, version);
	}

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
