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
import org.agatom.springatom.core.util.Localized;
import org.springframework.hateoas.Identifiable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

/**
 * {@code InfoPagePanel} describes the structure of a single panel visible on the {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoPagePanel
		implements Identifiable<String>, Serializable, Localized, Iterable<InfoPageAttribute> {
	private static final long                   serialVersionUID = -4966053815380618681L;
	private              String                 id               = null;
	private              InfoPageLayout         layout           = null;
	private              String                 messageKey       = null;
	private              Set<InfoPageAttribute> attributes       = null;
	private              boolean                collapsible      = false;
	private              boolean                frozen           = false;

	/**
	 * <p>isFrozen.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isFrozen() {
		return this.frozen;
	}

	/**
	 * <p>Setter for the field <code>frozen</code>.</p>
	 *
	 * @param frozen a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPagePanel} object.
	 */
	public InfoPagePanel setFrozen(final boolean frozen) {
		this.frozen = frozen;
		return this;
	}

	/**
	 * <p>isCollapsible.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isCollapsible() {
		return this.collapsible;
	}

	/**
	 * <p>Setter for the field <code>collapsible</code>.</p>
	 *
	 * @param collapsible a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPagePanel} object.
	 */
	public InfoPagePanel setCollapsible(final boolean collapsible) {
		this.collapsible = collapsible;
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
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPagePanel} object.
	 */
	public InfoPagePanel setId(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * <p>Getter for the field <code>layout</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPageLayout} object.
	 */
	public InfoPageLayout getLayout() {
		return this.layout;
	}

	/**
	 * <p>Setter for the field <code>layout</code>.</p>
	 *
	 * @param layout a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPageLayout} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPagePanel} object.
	 */
	public InfoPagePanel setLayout(final InfoPageLayout layout) {
		this.layout = layout;
		return this;
	}

	/**
	 * <p>Getter for the field <code>attributes</code>.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<InfoPageAttribute> getAttributes() {
		return this.attributes;
	}

	/**
	 * <p>Setter for the field <code>attributes</code>.</p>
	 *
	 * @param attributes a {@link java.util.Set} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPagePanel} object.
	 */
	public InfoPagePanel setAttributes(final Set<InfoPageAttribute> attributes) {
		this.attributes = attributes;
		return this;
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
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPagePanel} object.
	 */
	public InfoPagePanel setMessageKey(final String messageKey) {
		this.messageKey = messageKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(id, layout, messageKey, attributes, collapsible, frozen);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InfoPagePanel that = (InfoPagePanel) o;

		return Objects.equal(this.id, that.id) &&
				Objects.equal(this.layout, that.layout) &&
				Objects.equal(this.messageKey, that.messageKey) &&
				Objects.equal(this.attributes, that.attributes) &&
				Objects.equal(this.collapsible, that.collapsible) &&
				Objects.equal(this.frozen, that.frozen);
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<InfoPageAttribute> iterator() {
		return this.attributes.iterator();
	}
}
