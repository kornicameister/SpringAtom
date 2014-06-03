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

package org.agatom.springatom.server.model.types.contact;

import org.hibernate.validator.constraints.Email;

/**
 * {@code SContactable} marks entity as contactable using embedded
 * <b>email value</b>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SContactable {
	/**
	 * <p>getPrimaryMail.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getPrimaryMail();

	/**
	 * <p>setPrimaryMail.</p>
	 *
	 * @param mail a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.contact.SContactable} object.
	 */
	SContactable setPrimaryMail(final @Email String mail);
}
