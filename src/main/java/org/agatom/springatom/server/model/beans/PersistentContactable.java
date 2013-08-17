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

package org.agatom.springatom.server.model.beans;

import org.agatom.springatom.server.model.types.contact.SContactable;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * {@code PersistentContactable} is superclass for all entities that can be accesses using
 * {@link SContactable} information.
 * {@code PersistentContactable} extends from {@link PersistentVersionedObject} because of the nature of
 * the data known further as contact information.
 * In this particular class it is rather obvious that {@link PersistentContactable#primaryMail} is an immutable
 * value that can be changed in the future.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@MappedSuperclass
abstract public class PersistentContactable
        extends PersistentVersionedObject
        implements SContactable<Long> {

    @Email
    @NotBlank
    @NaturalId
    @Column(name = "primaryMail", length = 45, nullable = false, unique = true)
    private String primaryMail;

    @Override
    public String getPrimaryMail() {
        return this.primaryMail;
    }

    @Override
    public SContactable setPrimaryMail(final String email) {
        this.primaryMail = email;
        return this;
    }
}
