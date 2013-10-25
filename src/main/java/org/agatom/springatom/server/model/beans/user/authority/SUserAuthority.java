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

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SUserAuthority")
@Table(name = "SUserAuthority")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "user")),
        @AssociationOverride(name = "pk.authority", joinColumns = @JoinColumn(name = "authority"))
})
public class SUserAuthority
        implements Serializable {

    private static final long serialVersionUID = -61697944597116291L;
    @EmbeddedId
    private SUserAuthorityPK pk;

    public SUserAuthority() {
        this.pk = new SUserAuthorityPK();
    }

    public SUserAuthority(final SUser user, final SAuthority role) {
        this.pk = new SUserAuthorityPK(user, role);
    }

    public SUserAuthorityPK getPk() {
        return pk;
    }

    public void setPk(final SUserAuthorityPK pk) {
        this.pk = pk;
    }

    @Transient
    public SUser getUser() {
        return pk.getUser();
    }

    @Transient
    public void setUser(final SUser user) {
        pk.setUser(user);
    }

    @Transient
    public SAuthority getAuthority() {
        return this.pk.getAuthority();
    }

    @Transient
    public void setAuthority(final SAuthority role) {
        pk.setAuthority(role);
    }
}
