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

package org.agatom.springatom.server.service.domain;

import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.person.SPersonContact;
import org.agatom.springatom.server.model.types.contact.SContact;
import org.agatom.springatom.server.service.support.exceptions.EntityDoesNotExistsServiceException;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>SPersonService interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SPersonService
		extends SService<SPerson, Long, Integer> {

	/**
	 * <p>newContactData.</p>
	 *
	 * @param contact         a {@link java.lang.String} object.
	 * @param assignTo        a long.
	 * @param assignToContact a {@link org.agatom.springatom.server.model.types.contact.SContact} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.contact.SContact} object.
	 *
	 * @throws org.agatom.springatom.server.service.support.exceptions.EntityDoesNotExistsServiceException if any.
	 */
	SContact<SPerson> newContactData(
			@NotNull
			final String contact,
			final long assignTo,
			@NotNull
			final SContact assignToContact) throws EntityDoesNotExistsServiceException;

	/**
	 * <p>findAllContacts.</p>
	 *
	 * @param idClient a {@link java.lang.Long} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<SPersonContact> findAllContacts(final Long idClient);

	/**
	 * <p>findByFirstName.</p>
	 *
	 * @param firstName a {@link java.lang.String} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<SPerson> findByFirstName(
			@NotNull
			final String firstName);

	/**
	 * <p>findByLastName.</p>
	 *
	 * @param lastName a {@link java.lang.String} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<SPerson> findByLastName(
			@NotNull
			final String lastName);

	/**
	 * <p>findByEmail.</p>
	 *
	 * @param email a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.person.SPerson} object.
	 */
	SPerson findByEmail(
			@NotNull
			final String email);
}
