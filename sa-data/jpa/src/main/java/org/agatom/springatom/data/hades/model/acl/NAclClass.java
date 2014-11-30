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
 * <p>SAclClass class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table
@Entity
public class NAclClass
        extends NAbstractPersistable {
    private static final long                           serialVersionUID    = 2345236487666417725L;
    @Basic
    @Column(name = "class", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    private              String                         clazz               = null;
    @OneToMany(mappedBy = "aclClass")
    private              Collection<NAclObjectIdentity> aclObjectIdentities = null;

    /**
     * <p>Getter for the field <code>clazz</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * <p>Setter for the field <code>clazz</code>.</p>
     *
     * @param clazz a {@link String} object.
     */
    public void setClazz(final String clazz) {
        this.clazz = clazz;
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
