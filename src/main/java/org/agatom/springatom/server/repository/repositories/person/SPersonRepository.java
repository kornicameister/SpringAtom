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

import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.repository.SRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * <p>SPersonRepository interface.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@Qualifier(value = SPersonRepository.REPO_NAME)
@RepositoryRestResource(itemResourceRel = SPersonRepository.REST_REPO_REL, path = SPersonRepository.REST_REPO_PATH)
public interface SPersonRepository
		extends SRepository<SPerson, Long, Integer> {
	/** Constant <code>REST_REPO_REL="rest.person"</code> */
	String REST_REPO_REL  = "rest.person";
	/** Constant <code>REST_REPO_PATH="person"</code> */
	String REST_REPO_PATH = "person";
	/** Constant <code>REPO_NAME="PersonRepository"</code> */
	String REPO_NAME      = "PersonRepository";

	/**
	 * <p>findByFirstNameContaining.</p>
	 *
	 * @param name     a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byFirstNameContaining", path = "firstName_contains")
	Page<SPerson> findByFirstNameContaining(@Param("name") final String name, final Pageable pageable);

	/**
	 * <p>findByLastNameContaining.</p>
	 *
	 * @param name     a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byLastNameContaining", path = "lastName_contains")
	Page<SPerson> findByLastNameContaining(@Param("name") final String name, final Pageable pageable);

	/**
	 * <p>findByPrimaryMail.</p>
	 *
	 * @param mail a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.person.SPerson} object.
	 */
	@RestResource(rel = "byMail", path = "mail")
	SPerson findByPrimaryMail(@Param("mail") final String mail);

	/**
	 * <p>findPerson.</p>
	 *
	 * @param arg a {@link java.lang.String} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	@RestResource(rel = "byIdentity", path = "identity")
	@Query(name = "byIdentityQuery", value = "select p from SPerson as p where p.firstName like %:arg% or p.lastName like %:arg%")
	List<SPerson> findPerson(@Param("arg") final String arg);
}
