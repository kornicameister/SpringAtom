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

package org.agatom.springatom.web.calendar;

import org.agatom.springatom.web.component.core.Component;

import java.util.HashMap;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-01</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class Calendar
        extends HashMap<String, Object>
        implements Component {
    private static final long serialVersionUID = 6652812453541549468L;

    @Override
    public String getLabel() {
        return (String) this.get("label");
    }

    @Override
    public Component setLabel(final String title) {
        this.put("label", title);
        return this;
    }
}
