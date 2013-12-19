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

package org.agatom.springatom.component.builders;

import org.agatom.springatom.component.data.ComponentDataRequest;
import org.agatom.springatom.component.data.ComponentDataResponse;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class DefaultComponentBuilder<COMP extends Serializable>
        implements SComponentBuilder<COMP> {

    private Class<?>             target;
    private String               id;
    private ComponentDataRequest dataRequest;

    @Override
    public ComponentDataResponse getData() {
        return this.getData(this.dataRequest);
    }

    protected abstract ComponentDataResponse getData(final ComponentDataRequest dataRequest);

    @Override
    public final String getId() {
        return this.id;
    }

    public final void setId(final String id) {
        this.id = id;
    }

    @Override
    public final Class<?> getTarget() {
        return this.target;
    }

    public final void setTarget(final Class<?> clazz) {
        this.target = clazz;
    }

    @Override
    public void init(final ComponentDataRequest dataRequest) {
        this.dataRequest = dataRequest;
    }

    public ComponentDataRequest getDataRequest() {
        return dataRequest;
    }
}
