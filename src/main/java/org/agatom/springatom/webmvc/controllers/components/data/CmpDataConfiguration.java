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

package org.agatom.springatom.webmvc.controllers.components.data;

import com.google.common.base.MoreObjects;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-16</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class CmpDataConfiguration {
    private Object data       = null;
    private Object definition = null;

    public Object getData() {
        return data;
    }

    public CmpDataConfiguration setData(final Object data) {
        this.data = data;
        return this;
    }

    public Object getDefinition() {
        return definition;
    }

    public CmpDataConfiguration setDefinition(final Object configuration) {
        this.definition = configuration;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("data", data)
                .add("definition", definition)
                .toString();
    }
}
