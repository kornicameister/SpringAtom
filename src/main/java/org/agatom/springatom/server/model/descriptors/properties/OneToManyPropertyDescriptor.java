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
 * <p>OneToManyPropertyDescriptor class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class OneToManyPropertyDescriptor {

	private final PluralAttribute<?, ?, ?> attribute;

	/**
	 * <p>Constructor for OneToManyPropertyDescriptor.</p>
	 *
	 * @param attribute a {@link javax.persistence.metamodel.PluralAttribute} object.
	 */
	public OneToManyPropertyDescriptor(final PluralAttribute<?, ?, ?> attribute) {
		this.attribute = attribute;
	}

	/**
	 * <p>getCollectionType.</p>
	 *
	 * @return a {@link javax.persistence.metamodel.PluralAttribute.CollectionType} object.
	 */
	public PluralAttribute.CollectionType getCollectionType() {
		return attribute.getCollectionType();
	}

	/**
	 * <p>isAssociation.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isAssociation() {
		return attribute.isAssociation();
	}

	/**
	 * <p>getJavaMember.</p>
	 *
	 * @return a {@link java.lang.reflect.Member} object.
	 */
	public Member getJavaMember() {
		return attribute.getJavaMember();
	}

	/**
	 * <p>getBindableType.</p>
	 *
	 * @return a {@link javax.persistence.metamodel.Bindable.BindableType} object.
	 */
	public Bindable.BindableType getBindableType() {
		return attribute.getBindableType();
	}

	/**
	 * <p>getName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return attribute.getName();
	}

	/**
	 * <p>getBindableJavaType.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getBindableJavaType() {
		return attribute.getBindableJavaType();
	}

	/**
	 * <p>getPersistentAttributeType.</p>
	 *
	 * @return a {@link javax.persistence.metamodel.Attribute.PersistentAttributeType} object.
	 */
	public Attribute.PersistentAttributeType getPersistentAttributeType() {
		return attribute.getPersistentAttributeType();
	}

	/**
	 * <p>getElementType.</p>
	 *
	 * @return a {@link javax.persistence.metamodel.Type} object.
	 */
	public Type<?> getElementType() {
		return attribute.getElementType();
	}

	/**
	 * <p>isCollection.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isCollection() {
		return attribute.isCollection();
	}

	/**
	 * <p>getDeclaringType.</p>
	 *
	 * @return a {@link javax.persistence.metamodel.ManagedType} object.
	 */
	public ManagedType<?> getDeclaringType() {
		return attribute.getDeclaringType();
	}

	/**
	 * <p>getJavaType.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getJavaType() {
		return attribute.getJavaType();
	}
}
