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

package org.agatom.springatom.web.action.model.actions;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>PopupAction class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PopupAction
		extends LinkAction {
	private static final long          serialVersionUID = -2015463566430907903L;
	private              RequestMethod type             = null;

	/**
	 * <p>Getter for the field <code>type</code>.</p>
	 *
	 * @return a {@link org.springframework.web.bind.annotation.RequestMethod} object.
	 */
	public RequestMethod getType() {
		return type;
	}

	/**
	 * <p>Setter for the field <code>type</code>.</p>
	 *
	 * @param type a {@link org.springframework.web.bind.annotation.RequestMethod} object.
	 *
	 * @return a {@link org.agatom.springatom.web.action.model.actions.PopupAction} object.
	 */
	public PopupAction setType(final RequestMethod type) {
		this.type = type;
		return this;
	}
}
