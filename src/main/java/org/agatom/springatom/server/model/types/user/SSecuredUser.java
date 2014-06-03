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

package org.agatom.springatom.server.model.types.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * {@code SSecuredUser} is extended version of <i>OOTB</i>'s SpringSecurity
 * User's security-related classes with additional methods that allow to create
 * (<b>register</b>) new users in the application.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SSecuredUser
		extends UserDetails {

	/**
	 * <p>setPassword.</p>
	 *
	 * @param password a {@link java.lang.String} object.
	 */
	void setPassword(final String password);

	/**
	 * <p>setUsername.</p>
	 *
	 * @param userName a {@link java.lang.String} object.
	 */
	void setUsername(final String userName);

	/**
	 * <p>setEnabled.</p>
	 *
	 * @param disabled a boolean.
	 */
	void setEnabled(final boolean disabled);

	/**
	 * <p>addAuthority.</p>
	 *
	 * @param authority a T object.
	 * @param <T>       a T object.
	 *
	 * @return a boolean.
	 */
	<T extends GrantedAuthority> boolean addAuthority(final T authority);

	/**
	 * <p>removeAuthority.</p>
	 *
	 * @param authority a T object.
	 * @param <T>       a T object.
	 *
	 * @return a boolean.
	 */
	<T extends GrantedAuthority> boolean removeAuthority(final T authority);

	/**
	 * <p>hasAuthority.</p>
	 *
	 * @param authority a T object.
	 * @param <T>       a T object.
	 *
	 * @return a boolean.
	 */
	<T extends GrantedAuthority> boolean hasAuthority(final T authority);

	/**
	 * <p>hasAuthorities.</p>
	 *
	 * @param authorities a {@link java.util.Collection} object.
	 *
	 * @return a boolean.
	 */
	boolean hasAuthorities(final Collection<? extends GrantedAuthority> authorities);

}
