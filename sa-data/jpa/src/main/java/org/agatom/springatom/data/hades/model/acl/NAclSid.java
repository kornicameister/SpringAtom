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

package org.agatom.springatom.data.hades.model.acl;

import org.agatom.springatom.data.hades.model.NAbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

/**
 * <p>SAclSid class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table
@Entity
public class NAclSid
        extends NAbstractPersistable {
    private static final long serialVersionUID = 5934704955111593386L;
    @Basic
    @Column(name = "principal", nullable = false, insertable = true, updatable = true, length = 0, precision = 0)
    private boolean                        principal;
    @Basic
    @Column(name = "sid", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    private String                         sid;
    @OneToMany(mappedBy = "aclSidBySid")
    private Collection<NAclEntry>          aclEntries;
    @OneToMany(mappedBy = "aclSid")
    private Collection<NAclObjectIdentity> aclObjectIdentities;

    /**
     * <p>isPrincipal.</p>
     *
     * @return a boolean.
     */
    public boolean isPrincipal() {
        return principal;
    }

    /**
     * <p>Setter for the field <code>principal</code>.</p>
     *
     * @param principal a boolean.
     */
    public void setPrincipal(final boolean principal) {
        this.principal = principal;
    }

    /**
     * <p>Getter for the field <code>sid</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getSid() {
        return sid;
    }

    /**
     * <p>Setter for the field <code>sid</code>.</p>
     *
     * @param sid a {@link String} object.
     */
    public void setSid(final String sid) {
        this.sid = sid;
    }

    /**
     * <p>Getter for the field <code>aclEntries</code>.</p>
     *
     * @return a {@link java.util.Collection} object.
     */
    public Collection<NAclEntry> getAclEntries() {
        return aclEntries;
    }

    /**
     * <p>Setter for the field <code>aclEntries</code>.</p>
     *
     * @param aclEntriesById a {@link java.util.Collection} object.
     */
    public void setAclEntries(final Collection<NAclEntry> aclEntriesById) {
        this.aclEntries = aclEntriesById;
    }

    /**
     * <p>Getter for the field <code>aclObjectIdentities</code>.</p>
     *
     * @return a {@link java.util.Collection} object.
     */
    public Collection<NAclObjectIdentity> getAclObjectIdentities() {
        return aclObjectIdentities;
    }

    /**
     * <p>Setter for the field <code>aclObjectIdentities</code>.</p>
     *
     * @param aclObjectIdentitiesById a {@link java.util.Collection} object.
     */
    public void setAclObjectIdentities(final Collection<NAclObjectIdentity> aclObjectIdentitiesById) {
        this.aclObjectIdentities = aclObjectIdentitiesById;
    }
}
