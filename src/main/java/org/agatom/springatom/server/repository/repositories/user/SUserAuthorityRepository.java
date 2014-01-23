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

import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.beans.user.authority.SUserAuthority;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * {@code SUserAuthorityRepository} is the {@link org.springframework.data.repository.Repository} designated to
 * retrieve information about all objects of type={@link org.agatom.springatom.server.model.beans.user.authority.SAuthority}
 * assigned to any object of type={@link org.agatom.springatom.server.model.beans.user.SUser}
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@Qualifier(SUserAuthorityRepository.REPO_NAME)
@RestResource(rel = SUserAuthorityRepository.REST_REPO_REL, path = SUserAuthorityRepository.REST_REPO_PATH)
public interface SUserAuthorityRepository
        extends SBasicRepository<SUserAuthority, Long> {
    String REPO_NAME      = "UserAuthorityRepo";
    String REST_REPO_REL  = "rest.user.authority";
    String REST_REPO_PATH = "user_authority";

    Page<SUserAuthority> findByPkUser(@Param("user") final SUser user, final Pageable pageable);

}
