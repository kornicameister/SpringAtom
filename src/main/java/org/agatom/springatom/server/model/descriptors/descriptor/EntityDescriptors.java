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

package org.agatom.springatom.server.model.descriptors.descriptor;

import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.reader.EntityDescriptorReader;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class EntityDescriptors {
    private EntityDescriptorReader reader;

    public void setReader(final EntityDescriptorReader reader) {
        this.reader = reader;
    }

    public <X> EntityDescriptor<X> getDomainDescriptor(final Class<X> xClass) {
        return this.reader.getDefinition(xClass, false);
    }

    public String getDomainEntityName(final Class<?> xClass) {
        return this.reader.getDefinition(xClass, false).getEntityType().getName();
    }
}
