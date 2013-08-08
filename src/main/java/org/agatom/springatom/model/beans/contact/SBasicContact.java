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

package org.agatom.springatom.model.beans.contact;

import org.agatom.springatom.model.beans.PersistentContactable;
import org.agatom.springatom.model.beans.meta.SContactType;
import org.agatom.springatom.model.beans.meta.holder.SBasicMetaDataHolder;
import org.agatom.springatom.model.types.contact.SContact;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SContact")
@Table(name = "SContact")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idContact",
                updatable = false,
                nullable = false)
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = SBasicContact.CONTACT_IS,
        discriminatorType = DiscriminatorType.STRING
)
@Audited
abstract public class SBasicContact<SC_H extends PersistentContactable>
        extends SBasicMetaDataHolder<SContactType, Long>
        implements SContact<SC_H, SContactType, Long> {

    @Transient
    protected static final String CONTACT_IS = "contactIs";
    @Length(min = 5, max = 60)
    @Column(name = "contact", length = 60)
    protected              String contact    = "";

    public static Class<SContactType> holds() {
        return SContactType.class;
    }

    @Override
    public final String getContact() {
        return contact;
    }

    @Override
    public final SContact setContact(final
                                     @NotNull
                                     @NotEmpty
                                     String contact) {
        this.contact = contact;
        return this;
    }
}
