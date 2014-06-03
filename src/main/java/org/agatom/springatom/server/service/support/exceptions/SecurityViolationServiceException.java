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

package org.agatom.springatom.server.service.support.exceptions;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * <p>SecurityViolationServiceException class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SecurityViolationServiceException
		extends ServiceException {
	private static final long serialVersionUID = 622035483718925698L;

	/**
	 * <p>Constructor for SecurityViolationServiceException.</p>
	 *
	 * @param target  a {@link java.lang.Class} object.
	 * @param message a {@link java.lang.String} object.
	 * @param <T>     a T object.
	 * @param <ID>    a ID object.
	 */
	public <T extends Persistable<ID>, ID extends Serializable> SecurityViolationServiceException(final Class<T> target, final String message) {
		super(target, message);
	}
}
