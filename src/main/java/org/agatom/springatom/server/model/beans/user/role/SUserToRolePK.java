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

package org.agatom.springatom.server.model.beans.user.role;

import org.agatom.springatom.server.model.beans.user.SUser;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable
public class SUserToRolePK
        implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "idSUser")
    private SUser user;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "idSRole")
    private SRole role;

    public SUserToRolePK() {
        super();
    }

    public SUserToRolePK(final SUser user, final SRole role) {
        this.user = user;
        this.role = role;
    }

    public SUser getUser() {
        return user;
    }

    public void setUser(final SUser user) {
        this.user = user;
    }

    public SRole getRole() {
        return role;
    }

    public void setRole(final SRole role) {
        this.role = role;
    }
}
