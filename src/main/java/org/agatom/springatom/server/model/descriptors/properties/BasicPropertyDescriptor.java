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

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

/**
 * <p>BasicPropertyDescriptor class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class BasicPropertyDescriptor
		extends AbstractPropertyDescriptor {

	private static final long serialVersionUID = -2082514178668619298L;
	private final SingularAttribute<?, ?> attribute;

	/**
	 * <p>Constructor for BasicPropertyDescriptor.</p>
	 *
	 * @param attribute a {@link javax.persistence.metamodel.SingularAttribute} object.
	 */
	public BasicPropertyDescriptor(final SingularAttribute<?, ?> attribute) {
		super(attribute);
		this.attribute = attribute;
	}

	/**
	 * <p>isId.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isId() {
		return this.attribute.isId();
	}

	/**
	 * <p>isVersion.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isVersion() {
		return this.attribute.isVersion();
	}

	/**
	 * <p>isOptional.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isOptional() {
		return this.attribute.isOptional();
	}

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link javax.persistence.metamodel.Type} object.
	 */
	public Type<?> getType() {
		return this.attribute.getType();
	}
}
