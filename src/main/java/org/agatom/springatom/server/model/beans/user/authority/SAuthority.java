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

import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.types.user.SSecurityAuthorityEnum;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SAuthority")
@Table(name = "SAuthority")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSAuthority",
                updatable = false,
                nullable = false)
)
public class SAuthority
        extends PersistentObject<Long>
        implements GrantedAuthority {
    @Type(type = "org.hibernate.type.EnumType")
    @Column(name = "authority", updatable = false, unique = true, length = 50, nullable = false)
    @NaturalId(mutable = false)
    @Enumerated(value = EnumType.STRING)
    private SSecurityAuthorityEnum role;

    public SSecurityAuthorityEnum getAuthorityAsEnum() {
        return role;
    }

    public void setAuthority(final SSecurityAuthorityEnum role) {
        this.role = role;
    }

    public int getRoleId() {
        return this.role.getRoleId();
    }

    @Override
    public String getAuthority() {
        return this.role.toString();
    }

    public void setAuthority(final String role) {
        this.role = SSecurityAuthorityEnum.valueOf(role);
    }
}
