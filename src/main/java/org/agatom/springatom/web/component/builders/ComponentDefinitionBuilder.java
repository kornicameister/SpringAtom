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

package org.agatom.springatom.web.component.builders;

import org.agatom.springatom.web.component.builders.exception.ComponentException;
import org.agatom.springatom.web.component.data.ComponentDataRequest;

import java.io.Serializable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 27.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ComponentDefinitionBuilder<COMP extends Serializable>
		extends ComponentDataBuilder {

	/**
	 * Returns value of {@link ComponentProduces}
	 *
	 * @return what builder produces
	 */
	ComponentProduces getProduces();

	/**
	 * Returns a component class built by this builder
	 *
	 * @return a component class
	 */
	Class<?> getBuilds();

	/**
	 * Returns valid definition appropriate for this builder. Definition must be {@link java.io.Serializable}
	 *
	 * @param dataRequest to access the request and conditionally build definition if necessary
	 *
	 * @return builder definition
	 *
	 * @throws ComponentException in case of an error
	 */
	COMP getDefinition(final ComponentDataRequest dataRequest) throws ComponentException;

}
