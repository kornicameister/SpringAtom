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

package org.agatom.springatom.data.hades.model;

import org.agatom.springatom.data.support.EmailBean;
import org.agatom.springatom.data.types.contact.Contactable;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * {@code NPersistentContactable} is superclass for all entities that can be accesses using
 * {@link org.agatom.springatom.data.types.contact.Contactable} information.
 * {@code PersistentContactable} extends from {@link org.agatom.springatom.data.hades.model.NAbstractVersionedPersistable} because of the nature of
 * the data known further as contact information.
 * In this particular class it is rather obvious that {@link NPersistentContactable#email} is an immutable
 * value that can be changed in the future.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@MappedSuperclass
abstract public class NPersistentContactable
        extends NAbstractVersionedPersistable
        implements Contactable {
    private static final long      serialVersionUID = 8693440343386488007L;
    @Audited
    @Email
    @NotBlank
    @NaturalId
    @Column(length = 45, nullable = false, unique = true)
    private              String    email            = null;
    private transient    EmailBean emailBean        = null;

    /** {@inheritDoc} */
    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public EmailBean getEmailBean() {
        return this.emailBean != null ? this.emailBean : (this.emailBean = new EmailBean(this.email));
    }

    public NPersistentContactable setEmail(final String email) {
        this.email = email;
        return this;
    }
}
