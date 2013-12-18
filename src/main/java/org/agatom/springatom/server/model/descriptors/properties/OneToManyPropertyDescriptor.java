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

package org.agatom.springatom.server.model.descriptors.properties;

import javax.persistence.metamodel.*;
import java.lang.reflect.Member;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class OneToManyPropertyDescriptor {

    private final PluralAttribute<?, ?, ?> attribute;

    public OneToManyPropertyDescriptor(final PluralAttribute<?, ?, ?> attribute) {
        this.attribute = attribute;
    }

    public PluralAttribute.CollectionType getCollectionType() {
        return attribute.getCollectionType();
    }

    public boolean isAssociation() {
        return attribute.isAssociation();
    }

    public Member getJavaMember() {
        return attribute.getJavaMember();
    }

    public Bindable.BindableType getBindableType() {
        return attribute.getBindableType();
    }

    public String getName() {
        return attribute.getName();
    }

    public Class<?> getBindableJavaType() {
        return attribute.getBindableJavaType();
    }

    public Attribute.PersistentAttributeType getPersistentAttributeType() {
        return attribute.getPersistentAttributeType();
    }

    public Type<?> getElementType() {
        return attribute.getElementType();
    }

    public boolean isCollection() {
        return attribute.isCollection();
    }

    public ManagedType<?> getDeclaringType() {
        return attribute.getDeclaringType();
    }

    public Class<?> getJavaType() {
        return attribute.getJavaType();
    }
}
