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

import org.agatom.springatom.server.model.descriptors.properties.BasicPropertyDescriptor;
import org.agatom.springatom.server.model.descriptors.properties.ManyToOnePropertyDescriptor;
import org.agatom.springatom.server.model.descriptors.properties.OneToManyPropertyDescriptor;

import javax.persistence.metamodel.EntityType;
import java.util.Set;

/**
 * <p>EntityDescriptor interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface EntityDescriptor<X>
		extends SlimEntityDescriptor<X> {
	/**
	 * <p>getEntityType.</p>
	 *
	 * @return a {@link javax.persistence.metamodel.EntityType} object.
	 */
	EntityType<X> getEntityType();

	/**
	 * <p>getBasicProperties.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	Set<BasicPropertyDescriptor> getBasicProperties();

	/**
	 * <p>getOneToManyProperties.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	Set<OneToManyPropertyDescriptor> getOneToManyProperties();

	/**
	 * <p>getManyToOneProperties.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	Set<ManyToOnePropertyDescriptor> getManyToOneProperties();

	/**
	 * <p>isAbstract.</p>
	 *
	 * @return a boolean.
	 */
	boolean isAbstract();

	/**
	 * <p>isVersionable.</p>
	 *
	 * @return a boolean.
	 */
	boolean isVersionable();
}
