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

package org.agatom.springatom.server.model.descriptors.descriptor;

import com.google.common.base.Objects;
import org.agatom.springatom.server.model.descriptors.EntityDescriptorCollectionColumn;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class EntityTypeDescriptorCollectionColumn<X>
        extends EntityTypeDescriptorColumn<X>
        implements EntityDescriptorCollectionColumn<X> {
    private static final long serialVersionUID = 5084669095081694602L;
    private Class<?> elementClass;

    public EntityTypeDescriptorCollectionColumn<X> setElementClass(final Class<?> elementClass) {
        this.elementClass = elementClass;
        return this;
    }

    @Override
    public Class<?> getElementClass() {
        return elementClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntityTypeDescriptorCollectionColumn that = (EntityTypeDescriptorCollectionColumn) o;

        return Objects.equal(this.elementClass, that.elementClass) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.columnClass, that.columnClass) &&
                Objects.equal(this.entityDescriptor, that.entityDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(elementClass, name, columnClass, entityDescriptor);
    }
}
