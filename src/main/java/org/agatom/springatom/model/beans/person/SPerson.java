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

package org.agatom.springatom.model.beans.person;

import org.agatom.springatom.model.beans.PersistentVersionedObject;
import org.agatom.springatom.model.beans.person.embeddable.SPersonalInformation;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@Entity(name = "SPerson")
@Table(name = "SPerson", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSPerson",
                updatable = false,
                nullable = false
        )
)
@Inheritance(strategy = InheritanceType.JOINED)
abstract public class SPerson
        extends PersistentVersionedObject {

    @Embedded
    private SPersonalInformation information;
    @Email
    @Audited
    @NaturalId
    @Column(name = "email", length = 45, nullable = false, unique = true)
    private String               email;
    @Audited
    @Type(type = "boolean")
    @Column(name = "enabled")
    private Boolean enabled = Boolean.TRUE;

    public SPersonalInformation getInformation() {
        return information;
    }

    public void setInformation(final SPersonalInformation information) {
        this.information = information;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean disabled) {
        this.enabled = disabled;
    }
}
