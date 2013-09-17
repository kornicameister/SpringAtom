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

package org.agatom.springatom.server.service;

import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.repository.repositories.SUserRepository;
import org.agatom.springatom.server.service.base.SService;
import org.agatom.springatom.server.service.constraints.Password;
import org.agatom.springatom.server.service.constraints.UserName;
import org.agatom.springatom.server.service.exceptions.SEntityDoesNotExists;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SUserService
        extends SService<SUser, Long, Integer, SUserRepository>,
                UserDetailsService {

    /**
     * {@code org.agatom.springatom.server.service.SUserService#registerNewUser(String, String, long)} registers new
     * user
     * in the system.
     * <p>
     * <b>
     * It is crucial that an object of {@link org.agatom.springatom.server.model.beans.person.SPerson} already exists
     * because
     * each instance of the {@link org.agatom.springatom.server.model.beans.user.SUser} must be associated with either
     * {@link org.agatom.springatom.server.model.beans.person.client.SClient}
     * or {@link org.agatom.springatom.server.model.beans.person.mechanic.SMechanic}
     * </b>
     * </p>
     *
     * @param userName
     *         username (length=[5,20]
     * @param password
     *         password (can not be empty,null, its length must be between 6,20 and must be in special format)
     * @param personId
     *         personId
     *
     * @return an instance of the {@link org.agatom.springatom.server.model.beans.user.SUser}
     *
     * @throws SEntityDoesNotExists
     *         if there is no {@link org.agatom.springatom.server.model.beans.person.SPerson} to be associated with the
     *         new {@link org.agatom.springatom.server.model.beans.user.SUser}
     */
    @NotNull SUser registerNewUser(
            @UserName
            final String userName,
            @Password
            final String password,
            @Min(value = 2, message = "Minimal SPerson#id is 2, 1 is reserved for internal application usage")
            final long personId) throws SEntityDoesNotExists;

}
