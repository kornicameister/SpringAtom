/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.server.service.exceptions;

import com.rits.cloning.Cloner;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.service.support.exceptions.ServiceException;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * {@code UserRegistrationException} is a subclass of {@link org.agatom.springatom.server.service.support.exceptions.ServiceException}
 * which effectively carries information about {@link org.agatom.springatom.server.model.beans.user.SUser}
 * that was just failed to be registered due to numerous of reasons.
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 06.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class UserRegistrationException
		extends ServiceException {
	private static final long   serialVersionUID = 734212521196222534L;
	private              SUser  user             = null;
	private              String userName         = null;

	/**
	 * <p>Constructor for UserRegistrationException.</p>
	 *
	 * @param user    a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 * @param message a {@link java.lang.String} object.
	 * @param cause   a {@link java.lang.Throwable} object.
	 * @param <T>     a T object.
	 * @param <ID>    a ID object.
	 */
	public <T extends Persistable<ID>, ID extends Serializable> UserRegistrationException(
			final SUser user,
			final String message,
			final Throwable cause) {
		super(message, cause);
		this.user = new Cloner().deepClone(user);
	}

	/**
	 * <p>Constructor for UserRegistrationException.</p>
	 *
	 * @param userName a {@link java.lang.String} object.
	 * @param message  a {@link java.lang.String} object.
	 * @param exp      a {@link java.lang.Exception} object.
	 */
	public UserRegistrationException(final String userName, final String message, final Exception exp) {
		super(message, exp);
		this.userName = userName;
	}

	/**
	 * <p>Getter for the field <code>user</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 */
	public SUser getUser() {
		return this.user;
	}

	/**
	 * <p>Getter for the field <code>userName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUserName() {
		return this.userName == null ? (this.user != null ? this.user.getUsername() : "") : this.userName;
	}
}
