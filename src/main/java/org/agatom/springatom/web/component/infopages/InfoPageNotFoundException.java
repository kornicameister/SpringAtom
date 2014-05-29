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

package org.agatom.springatom.web.component.infopages;

import org.agatom.springatom.core.exception.SException;
import org.springframework.data.domain.Persistable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 18.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageNotFoundException
		extends SException {
	private static final long serialVersionUID = -9214937287759107097L;

	public InfoPageNotFoundException(final String rel) {
		super(getMsg(rel));
	}

	private static String getMsg(final String rel) {
		return String.format("InfoPage not found for REL=%s", rel);
	}

	public <T extends Persistable<?>> InfoPageNotFoundException(final Class<T> clazz) {
		super(getMsg(clazz));
	}

	private static String getMsg(final Class<?> clazz) {
		return String.format("InfoPage not found for CLAZZ=%s", clazz);
	}

}
