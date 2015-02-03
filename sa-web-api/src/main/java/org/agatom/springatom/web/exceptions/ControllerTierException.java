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

package org.agatom.springatom.web.exceptions;

/**
 * <p>ControllerTierException class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
//TODO remove it
public class ControllerTierException
        extends Exception {

    private static final String MSG_PATTERN_1    = "ControllerTierException=\n\t=>%s\n";
    private static final long   serialVersionUID = -61989976957896637L;

    /**
     * <p>Constructor for ControllerTierException.</p>
     */
    public ControllerTierException() {
    }

    /**
     * <p>Constructor for ControllerTierException.</p>
     *
     * @param message a {@link String} object.
     */
    public ControllerTierException(final String message) {
        super(String.format(MSG_PATTERN_1, message));
    }

    /**
     * <p>Constructor for ControllerTierException.</p>
     *
     * @param cause a {@link Throwable} object.
     */
    public ControllerTierException(final Throwable cause) {
        super(cause);
    }

    /**
     * <p>Constructor for ControllerTierException.</p>
     *
     * @param message a {@link String} object.
     * @param cause   a {@link Throwable} object.
     */
    public ControllerTierException(final String message, final Throwable cause) {
        super(String.format("ControllerTierException=\n\t=>%s\n", message), cause);
    }
}
