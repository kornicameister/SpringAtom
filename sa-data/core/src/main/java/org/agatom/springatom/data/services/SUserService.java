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

package org.agatom.springatom.data.services;

import org.agatom.springatom.data.constraints.Password;
import org.agatom.springatom.data.constraints.UserName;
import org.agatom.springatom.data.constraints.ValidUser;
import org.agatom.springatom.data.types.person.Person;
import org.agatom.springatom.data.types.user.User;
import org.agatom.springatom.data.types.user.authority.Role;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Collection;

/**
 * <p>SUserService interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SUserService<T extends User & Persistable<Long>, R extends Role>
        extends SDomainService<T>,
        UserDetailsService {

    /**
     * {@code org.agatom.springatom.server.service.domain.SUserService#registerNewUser(String, String, long)} registers new user
     * in the system.
     *
     * @param userName username (length=[5,20]
     * @param password password (can not be empty,null, its length must be between 6,20 and must be in special format)
     * @param personId personId
     *
     * @return an instance of the {@link org.agatom.springatom.data.types.user.User}
     *
     * @throws java.lang.Exception if any
     */
    @NotNull
    T registerNewUser(@UserName final String userName,
                      @Password final String password,
                      final long personId)
            throws Exception;

    /**
     * Performs direct registration from instance of {@link org.agatom.springatom.data.types.user.User}.
     * {@code user} will be validated against {@link org.agatom.springatom.data.constraints.ValidUser}
     *
     * @param user user to be registered
     *
     * @return registered user
     *
     * @throws java.lang.Exception if any
     */
    @NotNull
    T registerNewUser(@ValidUser final T user) throws Exception;

    /**
     * @param user   user to register
     * @param person person to associate user with
     * @param <P>    generic marker of the person
     *
     * @return registered user
     *
     * @throws Exception if any
     * @see #registerNewUser(org.agatom.springatom.data.types.user.User)
     */
    @NotNull
    <P extends Person> T registerNewUser(@ValidUser final T user, final P person) throws Exception;

    /**
     * @param user        user to register
     * @param person      person to associate user with
     * @param authorities roles
     * @param <P>         generic marker of the person
     *
     * @return registered user
     *
     * @throws Exception iff any
     */
    @NotNull
    <P extends Person> T registerNewUser(@ValidUser final T user, final P person, final Collection<? extends GrantedAuthority> authorities) throws Exception;

    /**
     * @return authenticated user
     */
    @NotNull
    T getAuthenticatedUser();

    @NotNull
    Principal getAuthenticatedPrincipal();

    <P extends Person> P getPerson(final T user);

    /**
     * Method returns administrator of the system
     *
     * @return the administrator
     */
    @NotNull
    T getAdministrator();

    /**
     * Returns all possible {@link org.agatom.springatom.data.types.user.authority.Role} that one user may have
     *
     * @return all roles
     */
    @NotNull
    Iterable<R> getAllRoles();

    /**
     * Looks up persistable instance of {@link org.agatom.springatom.data.types.user.authority.Role} by given
     * {@code authority}
     *
     * @param authority authority
     *
     * @return the role
     */
    R getRole(@NotNull String authority);

    T loadUserByEmail(@NotNull String email);
}
