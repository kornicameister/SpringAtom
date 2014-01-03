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

package org.agatom.springatom.web.component.builders;

import org.agatom.springatom.web.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.web.component.builders.exception.ComponentException;
import org.agatom.springatom.web.component.data.ComponentDataRequest;
import org.agatom.springatom.web.component.data.ComponentDataResponse;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ComponentBuilder<COMP extends Serializable> {

    String getId();

    Class<?> getBuilds();

    ComponentBuilds.Produces getProduces();

    ComponentDataResponse getData() throws ComponentException;

    COMP getDefinition() throws ComponentException;

    void init(ComponentDataRequest componentDataRequest);
}
