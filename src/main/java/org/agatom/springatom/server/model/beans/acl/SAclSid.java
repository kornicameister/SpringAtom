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
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SAclSid.TABLE_NAME)
@Entity(name = SAclSid.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SAclSid
        extends PersistentObject<Long> {
    public static final  String TABLE_NAME       = "acl_sid";
    public static final  String ENTITY_NAME      = "SAclSid";
    private static final long   serialVersionUID = 5934704955111593386L;
    @Basic
    @Column(name = "principal", nullable = false, insertable = true, updatable = true, length = 0, precision = 0)
    private boolean                        principal;
    @Basic
    @Column(name = "sid", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    private String                         sid;
    @OneToMany(mappedBy = "aclSidBySid")
    private Collection<SAclEntry>          aclEntries;
    @OneToMany(mappedBy = "aclSid")
    private Collection<SAclObjectIdentity> aclObjectIdentities;

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(final boolean principal) {
        this.principal = principal;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(final String sid) {
        this.sid = sid;
    }

    public Collection<SAclEntry> getAclEntries() {
        return aclEntries;
    }

    public void setAclEntries(final Collection<SAclEntry> aclEntriesById) {
        this.aclEntries = aclEntriesById;
    }

    public Collection<SAclObjectIdentity> getAclObjectIdentities() {
        return aclObjectIdentities;
    }

    public void setAclObjectIdentities(final Collection<SAclObjectIdentity> aclObjectIdentitiesById) {
        this.aclObjectIdentities = aclObjectIdentitiesById;
    }
}
