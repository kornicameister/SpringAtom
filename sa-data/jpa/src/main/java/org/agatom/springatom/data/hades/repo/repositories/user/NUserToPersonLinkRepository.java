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

import org.agatom.springatom.data.hades.model.person.NPerson;
import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.model.user.NUserToPersonLink;
import org.agatom.springatom.data.hades.repo.NRepository;
import org.springframework.data.jpa.repository.Query;
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

@RepositoryRestResource
public interface NUserToPersonLinkRepository
        extends NRepository<NUserToPersonLink> {

    @RestResource(rel = "byUser", path = "user")
    @Query(name = "systemMemberByUser", value = "select SM from NUserToPersonLink as SM where SM.pk.roleA = :user")
    NUserToPersonLink findByUser(@Param("user") final NUser user);

    @RestResource(rel = "byPerson", path = "person")
    @Query(name = "systemMemberByUser", value = "select SM from NUserToPersonLink as SM where SM.pk.roleB = :person")
    NUserToPersonLink findByPerson(@Param("person") final NPerson person);

}
