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
import org.agatom.springatom.server.repository.SRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Qualifier(value = SUserRepository.REPO_NAME)
@RestResource(rel = SUserRepository.REST_REPO_REL, path = SUserRepository.REST_REPO_PATH)
public interface SUserRepository
        extends SRepository<SUser, Long, Integer> {
    String REPO_NAME      = "UserRepo";
    String REST_REPO_PATH = "user";
    String REST_REPO_REL  = "rest.user";

    @RestResource(rel = "byPersonIdentityContaining", path = "identity_contains")
    Page<SUser> findByPersonLastNameContainingOrPersonFirstNameContaining(
            @Param("lastName") final String lastName,
            @Param("firstName") final String firstName,
            Pageable pageable);

    @RestResource(rel = "byPersonMail", path = "mail")
    SUser findByPersonPrimaryMail(
            @Param("mail") final String mail
    );

    @RestResource(rel = "byPerson", path = "person")
    SUser findByPerson(
            @Param("person") final SPerson person
    );

    @RestResource(rel = "byLogin", path = "login")
    SUser findByCredentialsUsername(
            @Param("login") final String login
    );

    @RestResource(rel = "byLoginContaining", path = "login_contains")
    Page<SUser> findByCredentialsUsernameContaining(
            @Param("login") final String login,
            final Pageable pageable
    );
}
