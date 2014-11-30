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

package org.agatom.springatom.data.hades.repo.repositories.user;

import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.repo.NRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * <p>NUserRepository interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@RepositoryRestResource(itemResourceRel = NUserRepository.REST_REPO_REL, path = NUserRepository.REST_REPO_PATH)
public interface NUserRepository
        extends NRepository<NUser> {
    /** Constant <code>REST_REPO_PATH="user"</code> */
    String REST_REPO_PATH = "user";
    /** Constant <code>REST_REPO_REL="rest.user"</code> */
    String REST_REPO_REL  = "rest.user";

    @RestResource(rel = "byPersonMail", path = "mail")
    NUser findByEmail(@Param("mail") final String mail);

    @RestResource(rel = "byLogin", path = "login")
    NUser findByCredentialsUsername(@Param("login") final String login);

    @RestResource(rel = "byLoginContaining", path = "login_contains")
    Page<NUser> findByCredentialsUsernameContaining(
            @Param("login") final String login,
            final Pageable pageable
    );
}
