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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mysema.query.annotations.QueryInit;
import org.agatom.springatom.model.beans.PersistentContactable;
import org.agatom.springatom.model.beans.person.contact.SPersonContact;
import org.agatom.springatom.model.beans.person.embeddable.SPersonalInformation;
import org.agatom.springatom.model.types.contact.SContact;
import org.agatom.springatom.model.types.contact.SMultiContactable;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@Entity(name = "SPerson")
@Table(name = "SPerson",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "primaryMail")
        }
)
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSPerson",
                updatable = false,
                nullable = false
        )
)
@Inheritance(strategy = InheritanceType.JOINED)
@Audited(auditParents = PersistentContactable.class)
abstract public class SPerson
        extends PersistentContactable
        implements SMultiContactable<Long> {

    @Embedded
    @QueryInit(value = "information")
    private SPersonalInformation information;
    @BatchSize(size = 10)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "assigned", targetEntity = SPersonContact.class)
    private Set<SContact>        contacts;

    public SPersonalInformation getInformation() {
        return information;
    }

    public SPerson setInformation(final SPersonalInformation information) {
        this.information = information;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SContact> getContacts() {
        return ImmutableList.copyOf(this.contacts);
    }

    @Override
    public <SC extends SContact<?, ?, ?>> SMultiContactable addContact(final Collection<SC> contacts) {
        if (contacts.size() > 0) {
            if (this.contacts == null) {
                this.contacts = Sets.newIdentityHashSet();
            }
            this.contacts.addAll(contacts);
        }
        return this;
    }

    @Override
    public <SC extends SContact<?, ?, ?>> SMultiContactable removeContact(final Collection<SC> contacts) {
        if (contacts.size() > 0 && this.contacts != null) {
            final HashSet<SC> contactSet = Sets.newHashSet(contacts);
            final ImmutableSet<SContact> difference = Sets.union(this.contacts, contactSet).immutableCopy();
            this.contacts.removeAll(difference);
        }
        return this;
    }
}
