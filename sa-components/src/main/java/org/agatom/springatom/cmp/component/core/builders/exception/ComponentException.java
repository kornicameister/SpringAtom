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

package org.agatom.springatom.cmp.component.core.builders.exception;

/**
 * <p>ComponentException class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ComponentException
        extends Exception {
    private static final long serialVersionUID = -6181041823249235155L;

    /**
     * <p>Constructor for ComponentException.</p>
     *
     * @param message a {@link String} object.
     */
    public ComponentException(final String message) {
        super(message);
    }

    /**
     * <p>Constructor for ComponentException.</p>
     *
     * @param message a {@link String} object.
     * @param cause   a {@link Throwable} object.
     */
    public ComponentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Constructor for ComponentException.</p>
     *
     * @param cause a {@link Throwable} object.
     */
    public ComponentException(final Throwable cause) {
        super(cause);
    }
}
