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

package org.agatom.springatom.webmvc.controllers.wiz;

import org.springframework.binding.collection.MapAdaptable;
import org.springframework.ui.ModelMap;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-30</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class WizardFormData
        implements Serializable, MapAdaptable<String, Object> {
    private static final long     serialVersionUID = 3992936954836469481L;
    private              ModelMap data             = null;

    public ModelMap getData() {
        return data;
    }

    public WizardFormData setData(final ModelMap data) {
        this.data = data;
        return this;
    }

    @Override
    public Map<String, Object> asMap() {
        return this.data;
    }
}
