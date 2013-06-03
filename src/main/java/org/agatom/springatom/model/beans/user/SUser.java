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

package org.agatom.springatom.model.beans.user;

import com.google.common.base.Objects;
import org.agatom.springatom.model.beans.PersistentVersionedObject;
import org.agatom.springatom.model.beans.util.SPerson;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@Entity(name = "SUser")
@Table(name = "SUser")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSUser",
                updatable = false,
                nullable = false)
)
public class SUser extends PersistentVersionedObject {
    @NaturalId
    @Column(name = "login", length = 45, unique = true, nullable = false)
    private String login;

    @Audited
    @Column(name = "secPass", length = 66, nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "person", referencedColumnName = "idSPerson")
    private SPerson person;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user")
    private Set<SUserToRole> roles;

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public SPerson getPerson() {
        return person;
    }

    public void setPerson(final SPerson person) {
        this.person = person;
    }

    public Set<SRole> getRoles() {
        Set<SRole> roles = new HashSet<>();
        for (SUserToRole userToRole : this.roles) {
            roles.add(userToRole.getRole());
        }
        return roles;
    }

    public void setRoles(final Set<SRole> roles) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        for (SRole role : roles) {
            this.roles.add(new SUserToRole(this, role));
        }
    }

    public boolean addRole(final SRole role) {
        return roles.add(new SUserToRole(this, role));
    }

    public boolean removeRole(final SRole role) {
        SUserToRole toDelete = null;
        for (SUserToRole userToRole : this.roles) {
            if (userToRole.getRole().equals(role)) {
                toDelete = userToRole;
                break;
            }
        }
        return toDelete != null && this.roles.remove(toDelete);
    }

    public boolean containsRole(final SRole role) {
        Set<SRole> rolesOut = new HashSet<>();
        rolesOut.add(role);
        return this.containsRoles(rolesOut);
    }

    public boolean containsRoles(final Collection<SRole> roles) {
        Set<SRole> rolesIn = new HashSet<>();
        for (SUserToRole userToRole : this.roles) {
            rolesIn.add(userToRole.getRole());
        }
        return rolesIn.containsAll(roles);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + login.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SUser)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        SUser sUser = (SUser) o;

        return login.equals(sUser.login);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("login", login)
                .add("password", password)
                .toString();
    }
}
