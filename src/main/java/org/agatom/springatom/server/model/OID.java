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

package org.agatom.springatom.server.model;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.agatom.springatom.core.util.StringAdaptable;

import java.io.Serializable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-23</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class OID
        implements Serializable, Comparable<OID>, StringAdaptable {
    private static final long   serialVersionUID = 4999512443240841991L;
    private              long   id               = -1;
    private              String objectClass      = null;
    private              String prefix           = null;

    public long getId() {
        return id;
    }

    public OID setId(final long id) {
        this.id = id;
        return this;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public OID setObjectClass(final String objectClass) {
        this.objectClass = objectClass;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public OID setPrefix(final String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public int compareTo(final OID o) {
        return ComparisonChain
                .start()
                .compare(this.id, o.id)
                .compare(this.objectClass, o.objectClass)
                .compare(this.prefix, o.prefix)
                .result();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("serialVersionUID", serialVersionUID)
                .add("id", id)
                .add("objectClass", objectClass)
                .add("prefix", prefix)
                .toString();
    }

    @Override
    public String asString() {
        return String.format("%s:%s:%s", this.prefix, this.objectClass, this.id);
    }
}
