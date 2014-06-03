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

/**
 * <p>SystemPropertyDescriptor class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SystemPropertyDescriptor
		extends BasicPropertyDescriptor {
	private static final long serialVersionUID = 3775651163181736999L;

	/**
	 * <p>Constructor for SystemPropertyDescriptor.</p>
	 *
	 * @param attribute a {@link javax.persistence.metamodel.SingularAttribute} object.
	 */
	public SystemPropertyDescriptor(final SingularAttribute<?, ?> attribute) {
		super(attribute);
	}
}
