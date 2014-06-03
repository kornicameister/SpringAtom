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

/**
 * {@code SContact} is an interface marking the entity, which implements it,
 * as being
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SContact<SC_H extends SContactable> {
	/**
	 * <p>getContact.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getContact();

	/**
	 * <p>setContact.</p>
	 *
	 * @param contact a {@link java.lang.String} object.
	 */
	void setContact(final String contact);

	/**
	 * <p>getAssigned.</p>
	 *
	 * @return a SC_H object.
	 */
	SC_H getAssigned();

	/**
	 * <p>setAssigned.</p>
	 *
	 * @param assigned a SC_H object.
	 */
	void setAssigned(final SC_H assigned);

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.contact.ContactType} object.
	 */
	ContactType getType();

	/**
	 * <p>setType.</p>
	 *
	 * @param type a {@link org.agatom.springatom.server.model.types.contact.ContactType} object.
	 */
	void setType(ContactType type);

}
