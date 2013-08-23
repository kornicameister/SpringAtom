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

package org.agatom.springatom.web.js.meta;

import com.google.common.base.Objects;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class JSMetaModelAssociation {
    private String name;
    private String key;
    private String foreignKey;

    public String getName() {
        return name;
    }

    public JSMetaModelAssociation setName(final String name) {
        this.name = name;
        return this;
    }

    public String getKey() {
        return key;
    }

    public JSMetaModelAssociation setKey(final String key) {
        this.key = key;
        return this;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public JSMetaModelAssociation setForeignKey(final String foreignKey) {
        this.foreignKey = foreignKey;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JSMetaModelAssociation that = (JSMetaModelAssociation) o;

        return Objects.equal(this.name, that.name) &&
                Objects.equal(this.key, that.key) &&
                Objects.equal(this.foreignKey, that.foreignKey);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, key, foreignKey);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(name)
                      .addValue(key)
                      .addValue(foreignKey)
                      .toString();
    }
}
