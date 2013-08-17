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

package org.agatom.springatom.server.model.beans.person.embeddable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable
public class SPersonalInformation
        implements Serializable {
    @NotEmpty
    @Length(min = 3, max = 45)
    @Column(name = "fName", length = 45, nullable = false)
    private String firstName;
    @NotEmpty
    @Length(min = 3, max = 45)
    @Column(name = "lName", length = 45, nullable = false)
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public SPersonalInformation setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public SPersonalInformation setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }
}
