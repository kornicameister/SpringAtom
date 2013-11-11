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
@Table(name = SAclClass.TABLE_NAME)
@Entity(name = SAclClass.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0))
public class SAclClass
        extends PersistentObject<Long> {
    public static final  String TABLE_NAME       = "acl_class";
    public static final  String ENTITY_NAME      = "SAclClass";
    private static final long   serialVersionUID = 2345236487666417725L;
    @Basic
    @Column(name = "class", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    private String                         clazz;
    @OneToMany(mappedBy = "aclClass")
    private Collection<SAclObjectIdentity> aclObjectIdentities;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(final String clazz) {
        this.clazz = clazz;
    }

    public Collection<SAclObjectIdentity> getAclObjectIdentities() {
        return aclObjectIdentities;
    }

    public void setAclObjectIdentities(final Collection<SAclObjectIdentity> aclObjectIdentitiesById) {
        this.aclObjectIdentities = aclObjectIdentitiesById;
    }
}
