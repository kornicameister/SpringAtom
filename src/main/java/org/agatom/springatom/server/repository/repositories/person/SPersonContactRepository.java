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

package org.agatom.springatom.server.repository.repositories.person;

import org.agatom.springatom.server.model.beans.person.SPersonContact;
import org.agatom.springatom.server.model.types.contact.ContactType;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * <p>SPersonContactRepository interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Qualifier(SPersonContactRepository.REPO_NAME)
@RepositoryRestResource(itemResourceRel = SPersonContactRepository.REST_REPO_REL, path = SPersonContactRepository.REST_REPO_PATH)
public interface SPersonContactRepository
		extends SBasicRepository<SPersonContact, Long> {

	/** Constant <code>REST_REPO_REL="rest.person.contact"</code> */
	String REST_REPO_REL  = "rest.person.contact";
	/** Constant <code>REST_REPO_PATH="person_contact"</code> */
	String REST_REPO_PATH = "person_contact";
	/** Constant <code>REPO_NAME="SPersonContactRepository"</code> */
	String REPO_NAME      = "SPersonContactRepository";

	/**
	 * <p>findByAssignedLastNameContaining.</p>
	 *
	 * @param lastName a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byAssignedLastName", path = "assigned_lastName")
	Page<SPersonContact> findByAssignedLastNameContaining(@Param("lastName") final String lastName, Pageable pageable);

	/**
	 * <p>findByContactContaining.</p>
	 *
	 * @param contact  a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SPersonContact> findByContactContaining(@Param("contact") final String contact, Pageable pageable);

	/**
	 * <p>findByType.</p>
	 *
	 * @param type     a {@link org.agatom.springatom.server.model.types.contact.ContactType} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SPersonContact> findByType(@Param("type") final ContactType type, Pageable pageable);

}
