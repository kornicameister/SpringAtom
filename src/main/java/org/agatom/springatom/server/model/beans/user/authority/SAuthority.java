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

package org.agatom.springatom.server.model.beans.user.authority;

import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.types.user.SRole;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * <p>SAuthority class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = SAuthority.ENTITY_NAME)
@Table(name = SAuthority.TABLE_NAME)
@AttributeOverride(name = "id", column = @Column(name = "idSAuthority", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SAuthority
		extends PersistentObject<Long>
		implements GrantedAuthority {
	/** Constant <code>ENTITY_NAME="SAuthority"</code> */
	protected static final String ENTITY_NAME      = "SAuthority";
	/** Constant <code>TABLE_NAME="sauthority"</code> */
	protected static final String TABLE_NAME       = "sauthority";
	private static final   long   serialVersionUID = 2893594861541235345L;
	@Type(type = "org.hibernate.type.EnumType")
	@Column(name = "authority", updatable = false, unique = true, length = 50, nullable = false)
	@Enumerated(value = EnumType.STRING)
	private                SRole  role             = null;

	/**
	 * <p>Constructor for SAuthority.</p>
	 */
	public SAuthority() {
	}

	/**
	 * <p>Constructor for SAuthority.</p>
	 *
	 * @param role a {@link java.lang.String} object.
	 */
	public SAuthority(final String role) {
		this.role = SRole.valueOf(role);
	}

	/**
	 * <p>fromRole.</p>
	 *
	 * @param roleMechanic a {@link org.agatom.springatom.server.model.types.user.SRole} object.
	 *
	 * @return a {@link org.springframework.security.core.GrantedAuthority} object.
	 */
	public static GrantedAuthority fromRole(final SRole roleMechanic) {
		return new SAuthority(roleMechanic.toString());
	}

	/**
	 * <p>Getter for the field <code>role</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.user.SRole} object.
	 */
	public SRole getRole() {
		return role;
	}

	/**
	 * <p>Setter for the field <code>role</code>.</p>
	 *
	 * @param role a {@link org.agatom.springatom.server.model.types.user.SRole} object.
	 */
	public void setRole(final SRole role) {
		this.role = role;
	}

	/**
	 * <p>getRoleId.</p>
	 *
	 * @return a int.
	 */
	public int getRoleId() {
		return this.role.getRoleId();
	}

	/** {@inheritDoc} */
	@Override
	public String getAuthority() {
		return this.role.toString();
	}

	/**
	 * <p>setAuthority.</p>
	 *
	 * @param role a {@link java.lang.String} object.
	 */
	public void setAuthority(final String role) {
		this.role = SRole.valueOf(role);
	}

	/** {@inheritDoc} */
	@Override
	public String getIdentity() {
		return this.role.toString();
	}
}
