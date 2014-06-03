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

package org.agatom.springatom.server.model.beans.user.embeddable;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * {@code SUserCredentials} is {@link javax.persistence.Embeddable} object used as placeholder
 * for credentials available to <b>user</b> of the application.
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */

@Embeddable
public class SUserCredentials
		implements Serializable {
	private static final long serialVersionUID = -4152612128922561019L;
	@NotEmpty
	@NaturalId
	@Column(name = "username", length = 45, unique = true, nullable = false)
	private String username;
	@NotEmpty
	@Column(name = "password", length = 66, nullable = false)
	private String password;

	/**
	 * <p>getUserName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * <p>Setter for the field <code>username</code>.</p>
	 *
	 * @param login a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.embeddable.SUserCredentials} object.
	 */
	public SUserCredentials setUsername(final String login) {
		this.username = login;
		return this;
	}

	/**
	 * <p>Getter for the field <code>password</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <p>Setter for the field <code>password</code>.</p>
	 *
	 * @param password a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.embeddable.SUserCredentials} object.
	 */
	public SUserCredentials setPassword(final String password) {
		this.password = password;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		int result = username != null ? username.hashCode() : 0;
		result = 31 * result + (password != null ? password.hashCode() : 0);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SUserCredentials)) {
			return false;
		}

		final SUserCredentials that = (SUserCredentials) o;

		return !(username != null ? !username
				.equals(that.username) : that.username != null) && !(password != null ? !password
				.equals(that.password) : that.password != null);
	}
}
