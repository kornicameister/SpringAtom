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

package org.agatom.springatom.server.model.beans.person;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.beans.PersistentContactable;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.agatom.springatom.server.model.types.contact.SContact;
import org.agatom.springatom.server.model.types.contact.SMultiContactable;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

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

@Entity(name = SPerson.ENTITY_NAME)
@Table(name = SPerson.TABLE_NAME, uniqueConstraints = {@UniqueConstraint(columnNames = "primaryMail")})
@ReportableEntity
@AttributeOverride(name = "id", column = @Column(name = "idSPerson", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
@Inheritance(strategy = InheritanceType.JOINED)
@Audited(auditParents = PersistentContactable.class)
public class SPerson
		extends PersistentContactable
		implements SMultiContactable {
	private static final   long   serialVersionUID = -8306142304138446067L;
	protected static final String ENTITY_NAME      = "SPerson";
	protected static final String TABLE_NAME       = "sperson";
	@NotEmpty
	@Length(min = 3, max = 45)
	@Column(name = "fName", length = 45, nullable = false)
	private String        firstName;
	@NotEmpty
	@Length(min = 3, max = 45)
	@Column(name = "lName", length = 45, nullable = false)
	private String        lastName;
	@BatchSize(size = 10)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "assigned", targetEntity = SPersonContact.class)
	private Set<SContact> contacts;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getIdentity() {
		return String.format("%s %s", this.firstName, this.lastName);
	}

	@Override
	public List<SContact> getContacts() {
		return ImmutableList.copyOf(this.contacts);
	}

	@Override
	public boolean addContact(final Collection<SContact> contacts) {
		if (contacts.size() > 0) {
			if (this.contacts == null) {
				this.contacts = Sets.newIdentityHashSet();
			}
			return this.contacts.addAll(contacts);
		}
		return false;
	}

	@Override
	public boolean removeContact(final Collection<SContact> contacts) {
		if (contacts.size() > 0 && this.contacts != null) {
			final HashSet<SContact> contactSet = Sets.newHashSet(contacts);
			final ImmutableSet<SContact> difference = Sets.union(this.contacts, contactSet).immutableCopy();
			return this.contacts.removeAll(difference);
		}
		return false;
	}
}
