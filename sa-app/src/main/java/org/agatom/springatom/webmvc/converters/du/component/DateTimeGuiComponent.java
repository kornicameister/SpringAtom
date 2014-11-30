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

package org.agatom.springatom.webmvc.converters.du.component;

import java.util.Locale;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 18.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class DateTimeGuiComponent
        extends TextGuiComponent {
    private static final long   serialVersionUID = 3950106797686043041L;
    private              String format           = null;
    private              Locale locale           = null;

    public String getFormat() {
        return format;
    }

    public DateTimeGuiComponent setFormat(final String format) {
        this.format = format;
        return this;
    }

    public Locale getLocale() {
        return locale;
    }

    public DateTimeGuiComponent setLocale(final Locale locale) {
        this.locale = locale;
        return this;
    }
}
