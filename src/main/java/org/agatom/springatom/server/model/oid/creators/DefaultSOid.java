/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.server.model.oid.creators;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;
import org.agatom.springatom.server.model.oid.SOid;

import javax.annotation.Nonnull;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-23</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class DefaultSOid<T, ID extends Comparable>
        implements SOid, Comparable<DefaultSOid> {
    private static final long     serialVersionUID = 4999512443240841991L;
    private              String   objectPrefix     = null;
    private              ID       objectId         = null;
    private              Class<T> objectClass      = null;

    @JsonIgnore
    public ID getObjectId() {
        return this.objectId;
    }

    public DefaultSOid<T, ID> setObjectId(final ID objectId) {
        this.objectId = objectId;
        return this;
    }

    @JsonIgnore
    public Class<T> getObjectClass() {
        return this.objectClass;
    }

    public DefaultSOid<T, ID> setObjectClass(final Class<T> objectClass) {
        this.objectClass = objectClass;
        return this;
    }

    @JsonIgnore
    public String getObjectPrefix() {
        return objectPrefix;
    }

    public DefaultSOid<T, ID> setObjectPrefix(final String objectPrefix) {
        this.objectPrefix = objectPrefix;
        return this;
    }

    @Override
    public int compareTo(@Nonnull final DefaultSOid o) {
        return ComparisonChain
                .start()
                .compare(this.objectId, o.objectId)
                .compare(this.objectClass.getName(), o.objectClass.getName())
                .result();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("oid", this.getOid())
                .add("objectPrefix", this.objectPrefix)
                .add("objectClass", this.objectClass)
                .add("objectId", this.objectId)
                .toString();
    }

    @Override
    public String getOid() {
        return String.format("%s:%s:%s", this.objectPrefix, this.objectClass.getName(), this.objectId);
    }
}
