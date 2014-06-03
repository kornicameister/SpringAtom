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

package org.agatom.springatom.server.model.beans.acl;

import org.agatom.springatom.server.model.beans.PersistentObject;

import javax.persistence.*;
import java.util.Collection;

/**
 * <p>SAclClass class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SAclClass.TABLE_NAME)
@Entity(name = SAclClass.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0))
public class SAclClass
		extends PersistentObject<Long> {
	/** Constant <code>TABLE_NAME="acl_class"</code> */
	public static final  String TABLE_NAME       = "acl_class";
	/** Constant <code>ENTITY_NAME="SAclClass"</code> */
	public static final  String ENTITY_NAME      = "SAclClass";
	private static final long   serialVersionUID = 2345236487666417725L;
	@Basic
	@Column(name = "class", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
	private String                         clazz;
	@OneToMany(mappedBy = "aclClass")
	private Collection<SAclObjectIdentity> aclObjectIdentities;

	/**
	 * <p>Getter for the field <code>clazz</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * <p>Setter for the field <code>clazz</code>.</p>
	 *
	 * @param clazz a {@link java.lang.String} object.
	 */
	public void setClazz(final String clazz) {
		this.clazz = clazz;
	}

	/**
	 * <p>Getter for the field <code>aclObjectIdentities</code>.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<SAclObjectIdentity> getAclObjectIdentities() {
		return aclObjectIdentities;
	}

	/**
	 * <p>Setter for the field <code>aclObjectIdentities</code>.</p>
	 *
	 * @param aclObjectIdentitiesById a {@link java.util.Collection} object.
	 */
	public void setAclObjectIdentities(final Collection<SAclObjectIdentity> aclObjectIdentitiesById) {
		this.aclObjectIdentities = aclObjectIdentitiesById;
	}
}
