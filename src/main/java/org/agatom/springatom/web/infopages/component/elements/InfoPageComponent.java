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

package org.agatom.springatom.web.infopages.component.elements;

import org.agatom.springatom.web.component.elements.ContentComponent;
import org.springframework.hateoas.Identifiable;

/**
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

	@Override
	public String getId() {
		return this.id;
	}

	public InfoPageComponent setId(final String id) {
		this.id = id;
		return this;
	}

	public int getSize() {
		return this.content.size();
	}

	public Class<?> getDomain() {
		return this.domain;
	}

	public InfoPageComponent setDomain(final Class<?> domain) {
		this.domain = domain;
		return this;
	}
}
