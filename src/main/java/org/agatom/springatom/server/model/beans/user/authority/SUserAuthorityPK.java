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

package org.agatom.springatom.server.model.beans.user.authority;

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
public class SUserAuthorityPK
        implements Serializable {
    private static final long serialVersionUID = -605204180160028113L;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "idSUser")
    private SUser      user;
    @ManyToOne
    @JoinColumn(name = "authority", referencedColumnName = "idSAuthority")
    private SAuthority authority;

    public SUserAuthorityPK() {
        super();
    }

    public SUserAuthorityPK(final SUser user, final SAuthority authority) {
        this.user = user;
        this.authority = authority;
    }

    public SUser getUser() {
        return user;
    }

    public void setUser(final SUser user) {
        this.user = user;
    }

    public SAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(final SAuthority role) {
        this.authority = role;
    }
}
