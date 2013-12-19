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

package org.agatom.springatom.component.data;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ComponentDataResponse
        implements Serializable {

    private Class<?> clazz;
    private Object   value;
    private DataType type;

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getValue() {
        return value;
    }

    public ComponentDataResponse setValue(final Object value) {
        this.value = value;
        this.clazz = value.getClass();
        return this;
    }

    public DataType getType() {
        return type;
    }

    public ComponentDataResponse setType(final DataType type) {
        this.type = type;
        return this;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(clazz, value, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComponentDataResponse that = (ComponentDataResponse) o;

        return Objects.equal(this.clazz, that.clazz) &&
                Objects.equal(this.value, that.value) &&
                Objects.equal(this.type, that.type);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(clazz)
                      .addValue(value)
                      .addValue(type)
                      .toString();
    }

    public static enum DataType {
        VALUE,
        COLLECTION,
        LINK
    }
}
