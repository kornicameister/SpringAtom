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

package org.agatom.springatom.web.component.core.builders;

import org.agatom.springatom.web.component.core.builders.exception.ComponentException;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.data.ComponentDataResponse;

/**
 * {@code ComponentDataBuilder} is an interface for <b>component builders</b>
 * designed to provide the data for given component without paying attention on its definition
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 27.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ComponentDataBuilder
		extends Builder {
	/**
	 * Returns data for implementing builder. Data must be {@link org.agatom.springatom.web.component.core.data.ComponentDataResponse} or subclass
	 *
	 * @param dataRequest request to work with
	 *
	 * @return the data
	 *
	 * @throws ComponentException if any
	 */
	ComponentDataResponse getData(final ComponentDataRequest dataRequest) throws ComponentException;
}
