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

package org.agatom.springatom.server.model.descriptors;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import java.io.Serializable;
import java.lang.reflect.Member;

/**
 * <p>EntityPropertyDescriptor interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface EntityPropertyDescriptor
		extends Serializable {
	/**
	 * <p>getName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * <p>getPersistentAttributeType.</p>
	 *
	 * @return a {@link javax.persistence.metamodel.Attribute.PersistentAttributeType} object.
	 */
	Attribute.PersistentAttributeType getPersistentAttributeType();

	/**
	 * <p>getJavaType.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	Class<?> getJavaType();

	/**
	 * <p>getJavaMember.</p>
	 *
	 * @return a {@link java.lang.reflect.Member} object.
	 */
	Member getJavaMember();

	/**
	 * <p>getDeclaringType.</p>
	 *
	 * @return a {@link javax.persistence.metamodel.ManagedType} object.
	 */
	ManagedType<?> getDeclaringType();

	/**
	 * <p>isAssociation.</p>
	 *
	 * @return a boolean.
	 */
	boolean isAssociation();

	/**
	 * <p>isCollection.</p>
	 *
	 * @return a boolean.
	 */
	boolean isCollection();

	/**
	 * <p>getAttribute.</p>
	 *
	 * @return a {@link javax.persistence.metamodel.Attribute} object.
	 */
	Attribute<?, ?> getAttribute();
}
