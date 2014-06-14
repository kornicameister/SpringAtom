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

package org.agatom.springatom.web.component.table.elements.extjs.store;

import java.io.Serializable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 14.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ExtJSStoreField
		implements Serializable {
	private static final long   serialVersionUID = -4040880531246748955L;
	private              String name             = null;
	private              String type             = "string";

	public String getName() {
		return name;
	}

	public ExtJSStoreField setName(final String name) {
		this.name = name;
		return this;
	}

	public String getType() {
		return type;
	}

	public ExtJSStoreField setType(final String type) {
		this.type = type;
		return this;
	}
}
