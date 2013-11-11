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
@Table(name = SAclObjectIdentity.TABLE_NAME)
@Entity(name = SAclObjectIdentity.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SAclObjectIdentity
        extends PersistentObject<Long> {
    public static final  String TABLE_NAME       = "acl_object_identity";
    public static final  String ENTITY_NAME      = "SAclObjectIdentity";
    private static final long   serialVersionUID = -2283824509477652149L;
    @Basic
    @Column(name = "object_id_identity", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    private long                           objectIdIdentity;
    @Basic
    @Column(name = "entries_inheriting", nullable = false, insertable = true, updatable = true, length = 0, precision = 0)
    private boolean                        entriesInheriting;
    @OneToMany(mappedBy = "aclObjectIdentity")
    private Collection<SAclEntry>          aclEntriesById;
    @ManyToOne
    @JoinColumn(name = "parent_object", referencedColumnName = "id")
    private SAclObjectIdentity             parent;
    @OneToMany(mappedBy = "parent")
    private Collection<SAclObjectIdentity> children;
    @ManyToOne
    @JoinColumn(name = "object_id_class", referencedColumnName = "id", nullable = false)
    private SAclClass                      aclClass;
    @ManyToOne
    @JoinColumn(name = "owner_sid", referencedColumnName = "id")
    private SAclSid                        aclSid;

    public long getObjectIdIdentity() {
        return objectIdIdentity;
    }

    public void setObjectIdIdentity(final long objectIdIdentity) {
        this.objectIdIdentity = objectIdIdentity;
    }

    public boolean isEntriesInheriting() {
        return entriesInheriting;
    }

    public void setEntriesInheriting(final boolean entriesInheriting) {
        this.entriesInheriting = entriesInheriting;
    }

    public Collection<SAclEntry> getAclEntriesById() {
        return aclEntriesById;
    }

    public void setAclEntriesById(final Collection<SAclEntry> aclEntriesById) {
        this.aclEntriesById = aclEntriesById;
    }

    public SAclObjectIdentity getParent() {
        return parent;
    }

    public void setParent(final SAclObjectIdentity aclObjectIdentityByParentObject) {
        this.parent = aclObjectIdentityByParentObject;
    }

    public Collection<SAclObjectIdentity> getChildren() {
        return children;
    }

    public void setChildren(final Collection<SAclObjectIdentity> aclObjectIdentitiesById) {
        this.children = aclObjectIdentitiesById;
    }

    public SAclClass getAclClass() {
        return aclClass;
    }

    public void setAclClass(final SAclClass aclClassByObjectIdClass) {
        this.aclClass = aclClassByObjectIdClass;
    }

    public SAclSid getAclSid() {
        return aclSid;
    }

    public void setAclSid(final SAclSid aclSidByOwnerSid) {
        this.aclSid = aclSidByOwnerSid;
    }
}
