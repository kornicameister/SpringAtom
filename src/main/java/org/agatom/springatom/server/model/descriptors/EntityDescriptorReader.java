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

package org.agatom.springatom.server.model.descriptors;

import java.util.Set;

/**
 * <p>EntityDescriptorReader interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface EntityDescriptorReader {
	/**
	 * <p>getDefinitions.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	Set<EntityDescriptor<?>> getDefinitions();

	/**
	 * <p>getDefinition.</p>
	 *
	 * @param xClass a {@link java.lang.Class} object.
	 * @param <X>    a X object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.descriptors.EntityDescriptor} object.
	 */
	<X> EntityDescriptor<X> getDefinition(final Class<X> xClass);

	/**
	 * <p>getDefinition.</p>
	 *
	 * @param xClass     a {@link java.lang.Class} object.
	 * @param initialize a boolean.
	 * @param <X>        a X object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.descriptors.EntityDescriptor} object.
	 */
	<X> EntityDescriptor<X> getDefinition(final Class<X> xClass, boolean initialize);
}
