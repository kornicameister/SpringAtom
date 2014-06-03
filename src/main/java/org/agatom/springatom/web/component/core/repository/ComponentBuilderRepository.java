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

package org.agatom.springatom.web.component.core.repository;

import org.agatom.springatom.web.component.core.builders.Builder;
import org.agatom.springatom.web.component.core.builders.ComponentProduces;

/**
 * {@code ComponentBuilderRepository} provides access to the {@link org.agatom.springatom.web.component.core.builders.Builder} instances
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public interface ComponentBuilderRepository {

	/**
	 * Retrieves builder for given {@code componentId}, the same as defined in {@link org.agatom.springatom.web.component.core.builders.annotation.ComponentBuilder#value()}
	 *
	 * @param componentId the id of the builder
	 *
	 * @return the {@link org.agatom.springatom.web.component.core.builders.Builder}
	 */
	Builder getBuilder(final String componentId);

	/**
	 * Verifies is there is {@link org.agatom.springatom.web.component.core.builders.Builder} defined
	 * to build {@code target} class. By default this method evaluates {@link org.agatom.springatom.web.component.core.builders.ComponentProduces#TABLE_COMPONENT} existence
	 *
	 * @param target class
	 *
	 * @return true/false
	 *
	 * @see #hasBuilder(Class, org.agatom.springatom.web.component.core.builders.ComponentProduces)
	 */
	boolean hasBuilder(Class<?> target);

	/**
	 * Verifies is there is {@link org.agatom.springatom.web.component.core.builders.Builder} defined
	 * to build {@code target} and {@link org.agatom.springatom.web.component.core.builders.ComponentProduces}
	 *
	 * @param target   class
	 * @param produces what builder produces
	 *
	 * @return true/false
	 *
	 * @see #hasBuilder(Class)
	 */
	boolean hasBuilder(Class<?> target, ComponentProduces produces);

	/**
	 * Retrieves {@link org.agatom.springatom.web.component.core.builders.Builder} for
	 * {@code target} class. By default this method evaluates for {@link org.agatom.springatom.web.component.core.builders.ComponentProduces#TABLE_COMPONENT}
	 *
	 * @param target class
	 *
	 * @return builder id
	 *
	 * @see #getBuilderId(Class, org.agatom.springatom.web.component.core.builders.ComponentProduces)
	 * @see #getBuilder(String)
	 * @see #getBuilderId(Class, org.agatom.springatom.web.component.core.builders.ComponentProduces)
	 * @see #getBuilder(String)
	 */
	String getBuilderId(Class<?> target);

	/**
	 * Retrieves {@link org.agatom.springatom.web.component.core.builders.Builder} for
	 * {@code target} class and particular {@link org.agatom.springatom.web.component.core.builders.ComponentProduces}
	 *
	 * @param target   class
	 * @param produces what builder produces
	 *
	 * @return builder id
	 */
	String getBuilderId(Class<?> target, ComponentProduces produces);

}
