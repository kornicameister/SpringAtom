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

package org.agatom.springatom.server.model.beans.contact;

import org.agatom.springatom.server.model.beans.PersistentContactable;
import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.types.contact.ContactType;
import org.agatom.springatom.server.model.types.contact.SContact;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * <p>Abstract SAbstractContact class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@Audited
@Table(name = SAbstractContact.TABLE_NAME)
@Entity(name = SAbstractContact.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "idContact", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = SAbstractContact.CONTACT_FOR, discriminatorType = DiscriminatorType.STRING)
abstract public class SAbstractContact<SC_H extends PersistentContactable>
		extends PersistentObject<Long>
		implements SContact<SC_H> {
	/** Constant <code>TABLE_NAME="contacts"</code> */
	public static final    String TABLE_NAME       = "contacts";
	/** Constant <code>ENTITY_NAME="SAbstractContact"</code> */
	public static final    String ENTITY_NAME      = "SAbstractContact";
	/** Constant <code>CONTACT_FOR="sac_for"</code> */
	protected static final String CONTACT_FOR      = "sac_for";
	private static final   long   serialVersionUID = 8494361809629647372L;
	@Length(min = 5, max = 60)
	@Column(name = "contact", length = 60)
	protected String      contact;
	@Type(type = "org.hibernate.type.EnumType")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "type", length = 60)
	protected ContactType type;

	/** {@inheritDoc} */
	@Override
	public final String getContact() {
		return contact;
	}

	/** {@inheritDoc} */
	@Override
	public final void setContact(final String contact) {
		this.contact = contact;
	}

	/** {@inheritDoc} */
	@Override
	public ContactType getType() {
		return this.type;
	}

	/** {@inheritDoc} */
	@Override
	public void setType(final ContactType type) {
		this.type = type;
	}

	/** {@inheritDoc} */
	@Override
	public String getIdentity() {
		return this.contact;
	}
}
