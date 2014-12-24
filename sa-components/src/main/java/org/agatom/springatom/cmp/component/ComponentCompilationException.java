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

package org.agatom.springatom.cmp.component;

/**
 * {@code ComponentCompilationException} is a custom exception thrown during building
 *
 * <p><small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-25</small></p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class ComponentCompilationException
        extends Exception {
    private static final long serialVersionUID = -1452148899616678089L;

    public ComponentCompilationException(final String message) {
        super(message);
    }

    public ComponentCompilationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ComponentCompilationException(final Throwable cause) {
        super(cause);
    }

}
