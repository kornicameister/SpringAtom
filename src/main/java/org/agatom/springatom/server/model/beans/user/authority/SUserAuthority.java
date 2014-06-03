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

import org.agatom.springatom.server.model.beans.user.SUser;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>SUserAuthority class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = SUserAuthority.ENTITY_NAME)
@Table(name = SUserAuthority.TABLE_NAME)
@AssociationOverrides({
		@AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "user")),
		@AssociationOverride(name = "pk.authority", joinColumns = @JoinColumn(name = "authority"))
})
public class SUserAuthority
		implements Serializable {

	/** Constant <code>TABLE_NAME="suserauthority"</code> */
	protected static final String           TABLE_NAME       = "suserauthority";
	/** Constant <code>ENTITY_NAME="SUserAuthority"</code> */
	protected static final String           ENTITY_NAME      = "SUserAuthority";
	private static final   long             serialVersionUID = -61697944597116291L;
	@EmbeddedId
	private                SUserAuthorityPK pk               = null;

	/**
	 * <p>Constructor for SUserAuthority.</p>
	 */
	public SUserAuthority() {
		this.pk = new SUserAuthorityPK();
	}

	/**
	 * <p>Constructor for SUserAuthority.</p>
	 *
	 * @param user a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 * @param role a {@link org.agatom.springatom.server.model.beans.user.authority.SAuthority} object.
	 */
	public SUserAuthority(final SUser user, final SAuthority role) {
		this.pk = new SUserAuthorityPK(user, role);
	}

	/**
	 * <p>Getter for the field <code>pk</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.authority.SUserAuthorityPK} object.
	 */
	public SUserAuthorityPK getPk() {
		return pk;
	}

	/**
	 * <p>Setter for the field <code>pk</code>.</p>
	 *
	 * @param pk a {@link org.agatom.springatom.server.model.beans.user.authority.SUserAuthorityPK} object.
	 */
	public void setPk(final SUserAuthorityPK pk) {
		this.pk = pk;
	}

	/**
	 * <p>getUser.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 */
	@Transient
	public SUser getUser() {
		return pk.getUser();
	}

	/**
	 * <p>setUser.</p>
	 *
	 * @param user a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 */
	@Transient
	public void setUser(final SUser user) {
		pk.setUser(user);
	}

	/**
	 * <p>getAuthority.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.authority.SAuthority} object.
	 */
	@Transient
	public SAuthority getAuthority() {
		return this.pk.getAuthority();
	}

	/**
	 * <p>setAuthority.</p>
	 *
	 * @param role a {@link org.agatom.springatom.server.model.beans.user.authority.SAuthority} object.
	 */
	@Transient
	public void setAuthority(final SAuthority role) {
		pk.setAuthority(role);
	}
}
