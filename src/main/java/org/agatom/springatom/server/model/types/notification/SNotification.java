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

package org.agatom.springatom.server.model.types.notification;

import org.agatom.springatom.server.model.types.PersistentBean;
import org.joda.time.DateTime;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * {@code SNotification} describes the functionality of simple notification
 * which is sent to the given {@code principal}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SNotification<PK extends Serializable>
		extends PersistentBean,
		Persistable<PK> {
	/**
	 * <p>getMessage.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getMessage();

	/**
	 * <p>setMessage.</p>
	 *
	 * @param notification a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.notification.SNotification} object.
	 */
	SNotification<PK> setMessage(final String notification);

	/**
	 * <p>getSent.</p>
	 *
	 * @return a {@link org.joda.time.DateTime} object.
	 */
	DateTime getSent();

	/**
	 * <p>setSent.</p>
	 *
	 * @param sent a {@link org.joda.time.DateTime} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.notification.SNotification} object.
	 */
	SNotification<PK> setSent(final DateTime sent);

	/**
	 * <p>isRead.</p>
	 *
	 * @return a {@link java.lang.Boolean} object.
	 */
	Boolean isRead();

	/**
	 * <p>readNotification.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.notification.SNotification} object.
	 */
	SNotification<PK> readNotification();
}
