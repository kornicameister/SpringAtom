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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Multimap;
import org.agatom.springatom.cmp.component.core.repository.ComponentMetaData;
import org.agatom.springatom.core.web.DataResource;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 20.07.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class CmpResource
        extends DataResource<Object> {
    private static final long serialVersionUID = -2046530736751451451L;

    public CmpResource(final Object data, final Object definition) {
        super(new CmpDataConfiguration().setDefinition(definition).setData(data));
    }

    public CmpResource(final Multimap<String, ComponentMetaData> allBuilders) {
        super(allBuilders);
    }

}
