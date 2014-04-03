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

import com.google.common.base.Function;
import com.google.common.collect.*;
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
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.persistence.Table;
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
		if (this.contacts == null) {
			this.contacts = Sets.newHashSet();
		}
		return Lists.newArrayList(this.contacts);
	}

	@Override
	public boolean addContact(final Collection<SContact> contacts) {
		if (!CollectionUtils.isEmpty(contacts)) {
			if (this.contacts == null) {
				this.contacts = Sets.newIdentityHashSet();
			}
			for (SContact contact : contacts) {
				if (ClassUtils.isAssignableValue(SPersonContact.class, contact)) {
					((SPersonContact) contact).setAssigned(this);
				}
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

	public SPerson setContacts(final Collection<SContact> contacts) {
		this.addContact(contacts);
		return this;
	}

	public void clearContacts() {
		this.contacts = null;
	}

	public Collection<SPersonContact> getPersonContacts() {
		final ImmutableList<SPersonContact> list = FluentIterable.from(this.getContacts()).transform(new Function<SContact, SPersonContact>() {
			@Nullable
			@Override
			public SPersonContact apply(@Nullable final SContact input) {
				if (input != null && ClassUtils.isAssignableValue(SPersonContact.class, input)) {
					return (SPersonContact) input;
				}
				return null;
			}
		}).toList();
		return Lists.newArrayList(list);
	}

	public boolean setPersonContacts(final Collection<SPersonContact> contacts) {
		return this.addContact(FluentIterable.from(contacts).transform(new Function<SPersonContact, SContact>() {
			@Nullable
			@Override
			public SContact apply(@Nullable final SPersonContact input) {
				return input;
			}
		}).toList());
	}
}
