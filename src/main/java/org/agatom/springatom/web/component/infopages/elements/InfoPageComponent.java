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

package org.agatom.springatom.web.component.infopages.elements;

import org.agatom.springatom.web.component.core.elements.ContentComponent;
import org.springframework.hateoas.Identifiable;

/**
 * <p>InfoPageComponent class.</p>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class InfoPageComponent
		extends ContentComponent<InfoPagePanelComponent>
		implements Identifiable<String> {
	private static final long   serialVersionUID = -1693645505025410828L;
	private              String id               = null;
	private Class<?> domain;

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
	 * @return a {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent} object.
	 */
	public InfoPageComponent setId(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * <p>getSize.</p>
	 *
	 * @return a int.
	 */
	public int getSize() {
		return this.content.size();
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
	 * @return a {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent} object.
	 */
	public InfoPageComponent setDomain(final Class<?> domain) {
		this.domain = domain;
		return this;
	}
}
