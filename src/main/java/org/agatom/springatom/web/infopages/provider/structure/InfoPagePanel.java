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

package org.agatom.springatom.web.infopages.provider.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import org.springframework.hateoas.Identifiable;

import java.io.Serializable;
import java.util.Set;

/**
 * {@code InfoPagePanel} describes the structure of a single panel visible on the {@link org.agatom.springatom.web.infopages.provider.structure.InfoPage}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoPagePanel
		implements Identifiable<String>, Serializable {
	private static final long                   serialVersionUID = -4966053815380618681L;
	private              String                 id               = null;
	private              InfoPageLayout         layout           = null;
	private              Set<InfoPageAttribute> attributes       = null;
	private              boolean                collapsible      = false;
	private              boolean                frozen           = false;

	public boolean isFrozen() {
		return this.frozen;
	}

	public InfoPagePanel setFrozen(final boolean frozen) {
		this.frozen = frozen;
		return this;
	}

	public boolean isCollapsible() {
		return this.collapsible;
	}

	public InfoPagePanel setCollapsible(final boolean collapsible) {
		this.collapsible = collapsible;
		return this;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public InfoPagePanel setId(final String id) {
		this.id = id;
		return this;
	}

	public InfoPageLayout getLayout() {
		return this.layout;
	}

	public InfoPagePanel setLayout(final InfoPageLayout layout) {
		this.layout = layout;
		return this;
	}

	public Set<InfoPageAttribute> getAttributes() {
		return this.attributes;
	}

	public InfoPagePanel setAttributes(final Set<InfoPageAttribute> attributes) {
		this.attributes = attributes;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, layout, attributes);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InfoPagePanel that = (InfoPagePanel) o;

		return Objects.equal(this.id, that.id) &&
				Objects.equal(this.layout, that.layout) &&
				Objects.equal(this.attributes, that.attributes);
	}
}
