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

package org.agatom.springatom.server.repository.repositories.user;

import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.user.SRole;
import org.agatom.springatom.server.repository.SRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;

/**
 * <p>SUserRepository interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Qualifier(value = SUserRepository.REPO_NAME)
@RepositoryRestResource(itemResourceRel = SUserRepository.REST_REPO_REL, path = SUserRepository.REST_REPO_PATH)
public interface SUserRepository
		extends SRepository<SUser, Long, Integer> {
	/** Constant <code>REPO_NAME="UserRepo"</code> */
	String REPO_NAME      = "UserRepo";
	/** Constant <code>REST_REPO_PATH="user"</code> */
	String REST_REPO_PATH = "user";
	/** Constant <code>REST_REPO_REL="rest.user"</code> */
	String REST_REPO_REL  = "rest.user";

	/**
	 * <p>findByPersonLastNameContainingOrPersonFirstNameContaining.</p>
	 *
	 * @param lastName  a {@link java.lang.String} object.
	 * @param firstName a {@link java.lang.String} object.
	 * @param pageable  a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byPersonIdentityContaining", path = "identity_contains")
	Page<SUser> findByPersonLastNameContainingOrPersonFirstNameContaining(
			@Param("lastName") final String lastName,
			@Param("firstName") final String firstName,
			Pageable pageable);

	/**
	 * <p>findByPersonPrimaryMail.</p>
	 *
	 * @param mail a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 */
	@RestResource(rel = "byPersonMail", path = "mail")
	SUser findByPersonPrimaryMail(
			@Param("mail") final String mail
	);

	/**
	 * <p>findByPerson.</p>
	 *
	 * @param person a {@link org.agatom.springatom.server.model.beans.person.SPerson} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 */
	@RestResource(rel = "byPerson", path = "person")
	SUser findByPerson(
			@Param("person") final SPerson person
	);

	/**
	 * <p>findByCredentialsUsername.</p>
	 *
	 * @param login a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 */
	@RestResource(rel = "byLogin", path = "login")
	SUser findByCredentialsUsername(
			@Param("login") final String login
	);

	/**
	 * <p>findByCredentialsUsernameContaining.</p>
	 *
	 * @param login    a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byLoginContaining", path = "login_contains")
	Page<SUser> findByCredentialsUsernameContaining(
			@Param("login") final String login,
			final Pageable pageable
	);

	/**
	 * <p>findByRolesIn.</p>
	 *
	 * @param role a {@link java.util.Collection} object.
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<SUser> findByRolesIn(final Collection<SRole> role);

	/**
	 * <p>findByRolesIn.</p>
	 *
	 * @param role     a {@link java.util.Collection} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SUser> findByRolesIn(final Collection<SRole> role, final Pageable pageable);
}
