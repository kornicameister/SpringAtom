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

package org.agatom.springatom.model.types.notification;

import org.agatom.springatom.model.types.PersistentBean;
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
    String getMessage();

    SNotification<PK> setMessage(final String notification);

    DateTime getSent();

    SNotification<PK> setSent(final DateTime sent);

    Boolean isRead();

    SNotification<PK> readNotification();
}
