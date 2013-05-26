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

package org.agatom.springatom.model.user;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SUserToRole")
@Table(name = "SUserToRole")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "user")),
        @AssociationOverride(name = "pk.role", joinColumns = @JoinColumn(name = "role"))
})
public class SUserToRole implements Serializable {

    @EmbeddedId
    private SUserToRolePK pk;

    public SUserToRole() {
        this.pk = new SUserToRolePK();
    }

    public SUserToRole(final SUser user, final SRole role) {
        this.pk = new SUserToRolePK(user, role);
    }

    public SUserToRolePK getPk() {
        return pk;
    }

    public void setPk(final SUserToRolePK pk) {
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
    public SRole getRole() {
        return pk.getRole();
    }

    @Transient
    public void setRole(final SRole role) {
        pk.setRole(role);
    }

    @Override
    public int hashCode() {
        return pk.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SUserToRole)) return false;

        SUserToRole that = (SUserToRole) o;

        return pk.equals(that.pk);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("pk", pk)
                .toString();
    }
}
