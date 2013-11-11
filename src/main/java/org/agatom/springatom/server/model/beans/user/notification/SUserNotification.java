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

package org.agatom.springatom.server.model.beans.user.notification;

import org.agatom.springatom.server.model.beans.notification.SAbstractNotification;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = SUserNotification.ENTITY_NAME)
@DiscriminatorValue(value = "sun")
public class SUserNotification
        extends SAbstractNotification {
    public static final  String ENTITY_NAME      = "UserNotificiation";
    private static final long   serialVersionUID = 5952975044391002047L;
    @ManyToOne(cascade = {CascadeType.REMOVE}, optional = false, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sur_user", referencedColumnName = "idSUser", updatable = false, nullable = false)
    private SUser user;

    public SUserNotification setUser(final SUser user) {
        this.user = user;
        return this;
    }

    public SUser getUser() {
        return user;
    }
}
