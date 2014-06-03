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

/**
 * <p>SAclEntry class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SAclEntry.TABLE_NAME)
@Entity(name = SAclEntry.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SAclEntry
		extends PersistentObject<Long> {
	/** Constant <code>TABLE_NAME="acl_entry"</code> */
	public static final  String TABLE_NAME       = "acl_entry";
	/** Constant <code>ENTITY_NAME="SAclEntry"</code> */
	public static final  String ENTITY_NAME      = "SAclEntry";
	private static final long   serialVersionUID = -624646177688568551L;
	@Basic
	@Column(name = "ace_order", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
	private int                aceOrder;
	@Basic
	@Column(name = "mask", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
	private int                mask;
	@Basic
	@Column(name = "granting", nullable = false, insertable = true, updatable = true, length = 0, precision = 0)
	private boolean            granting;
	@Basic
	@Column(name = "audit_success", nullable = false, insertable = true, updatable = true, length = 0, precision = 0)
	private boolean            auditSuccess;
	@Basic
	@Column(name = "audit_failure", nullable = false, insertable = true, updatable = true, length = 0, precision = 0)
	private boolean            auditFailure;
	@ManyToOne
	@JoinColumn(name = "acl_object_identity", referencedColumnName = "id", nullable = false)
	private SAclObjectIdentity aclObjectIdentity;
	@ManyToOne
	@JoinColumn(name = "sid", referencedColumnName = "id", nullable = false)
	private SAclSid            aclSidBySid;

	/**
	 * <p>Getter for the field <code>aceOrder</code>.</p>
	 *
	 * @return a int.
	 */
	public int getAceOrder() {
		return aceOrder;
	}

	/**
	 * <p>Setter for the field <code>aceOrder</code>.</p>
	 *
	 * @param aceOrder a int.
	 */
	public void setAceOrder(final int aceOrder) {
		this.aceOrder = aceOrder;
	}

	/**
	 * <p>Getter for the field <code>mask</code>.</p>
	 *
	 * @return a int.
	 */
	public int getMask() {
		return mask;
	}

	/**
	 * <p>Setter for the field <code>mask</code>.</p>
	 *
	 * @param mask a int.
	 */
	public void setMask(final int mask) {
		this.mask = mask;
	}

	/**
	 * <p>isGranting.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isGranting() {
		return granting;
	}

	/**
	 * <p>Setter for the field <code>granting</code>.</p>
	 *
	 * @param granting a boolean.
	 */
	public void setGranting(final boolean granting) {
		this.granting = granting;
	}

	/**
	 * <p>isAuditSuccess.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isAuditSuccess() {
		return auditSuccess;
	}

	/**
	 * <p>Setter for the field <code>auditSuccess</code>.</p>
	 *
	 * @param auditSuccess a boolean.
	 */
	public void setAuditSuccess(final boolean auditSuccess) {
		this.auditSuccess = auditSuccess;
	}

	/**
	 * <p>isAuditFailure.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isAuditFailure() {
		return auditFailure;
	}

	/**
	 * <p>Setter for the field <code>auditFailure</code>.</p>
	 *
	 * @param auditFailure a boolean.
	 */
	public void setAuditFailure(final boolean auditFailure) {
		this.auditFailure = auditFailure;
	}

	/**
	 * <p>Getter for the field <code>aclObjectIdentity</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.acl.SAclObjectIdentity} object.
	 */
	public SAclObjectIdentity getAclObjectIdentity() {
		return aclObjectIdentity;
	}

	/**
	 * <p>Setter for the field <code>aclObjectIdentity</code>.</p>
	 *
	 * @param aclObjectIdentityByAclObjectIdentity a {@link org.agatom.springatom.server.model.beans.acl.SAclObjectIdentity} object.
	 */
	public void setAclObjectIdentity(final SAclObjectIdentity aclObjectIdentityByAclObjectIdentity) {
		this.aclObjectIdentity = aclObjectIdentityByAclObjectIdentity;
	}

	/**
	 * <p>Getter for the field <code>aclSidBySid</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.acl.SAclSid} object.
	 */
	public SAclSid getAclSidBySid() {
		return aclSidBySid;
	}

	/**
	 * <p>Setter for the field <code>aclSidBySid</code>.</p>
	 *
	 * @param aclSidBySid a {@link org.agatom.springatom.server.model.beans.acl.SAclSid} object.
	 */
	public void setAclSidBySid(final SAclSid aclSidBySid) {
		this.aclSidBySid = aclSidBySid;
	}
}
