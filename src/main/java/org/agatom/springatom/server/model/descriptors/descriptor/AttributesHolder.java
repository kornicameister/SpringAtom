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

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.descriptors.properties.BasicPropertyDescriptor;
import org.agatom.springatom.server.model.descriptors.properties.ManyToOnePropertyDescriptor;
import org.agatom.springatom.server.model.descriptors.properties.OneToManyPropertyDescriptor;
import org.agatom.springatom.server.model.descriptors.properties.SystemPropertyDescriptor;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

class AttributesHolder {

    private Set<BasicPropertyDescriptor>     basic     = Sets.newHashSet();
    private Set<OneToManyPropertyDescriptor> oneToMany = Sets.newHashSet();
    private Set<ManyToOnePropertyDescriptor> manyToOne = Sets.newHashSet();
    private Set<SystemPropertyDescriptor>    system    = Sets.newHashSet();
    private Map<Class<?>, Set<?>>            internal  = Maps.newHashMap();

    AttributesHolder() {
        super();
        this.internal.put(BasicPropertyDescriptor.class, this.basic);
        this.internal.put(OneToManyPropertyDescriptor.class, this.oneToMany);
        this.internal.put(ManyToOnePropertyDescriptor.class, this.manyToOne);
        this.internal.put(SystemPropertyDescriptor.class, this.system);
    }

    boolean hasProperties(final Class<?> clazz) {
        return this.internal.containsKey(clazz) && this.internal.get(clazz).size() > 0;
    }

    Set<BasicPropertyDescriptor> getBasicProperties() {
        return Collections.unmodifiableSet(this.basic);
    }

    Set<SystemPropertyDescriptor> getSystemProperties() {
        return Collections.unmodifiableSet(this.system);
    }

    Set<ManyToOnePropertyDescriptor> getManyToOneProperties() {
        return Collections.unmodifiableSet(this.manyToOne);
    }

    Set<OneToManyPropertyDescriptor> getOneToManyProperties() {
        return Collections.unmodifiableSet(this.oneToMany);
    }

    public void addAllOneToManyProperties(final Set<OneToManyPropertyDescriptor> pds) {
        this.oneToMany.addAll(pds);
    }

    public void addBasicProperties(final Set<BasicPropertyDescriptor> pds) {
        this.basic.addAll(pds);
    }

    public void addSystemProperties(final Set<SystemPropertyDescriptor> pds) {
        this.system.addAll(pds);
    }

    public void addAllManyToOneProperties(final Set<ManyToOnePropertyDescriptor> pds) {
        this.manyToOne.addAll(pds);
    }
}
