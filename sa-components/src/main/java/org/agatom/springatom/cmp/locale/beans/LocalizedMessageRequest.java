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

package org.agatom.springatom.cmp.locale.beans;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * <p>LocalizedMessageRequest class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class LocalizedMessageRequest
        implements Serializable {
    private static final long serialVersionUID = 5395003418793746321L;
    private String[] keys;
    private boolean  pattern;

    /**
     * <p>Getter for the field <code>keys</code>.</p>
     *
     * @return an array of {@link String} objects.
     */
    public String[] getKeys() {
        return keys;
    }

    /**
     * <p>Setter for the field <code>keys</code>.</p>
     *
     * @param keys an array of {@link String} objects.
     *
     * @return a {@link org.agatom.springatom.web.locale.beans.LocalizedMessageRequest} object.
     */
    public LocalizedMessageRequest setKeys(final String[] keys) {
        this.keys = keys;
        return this;
    }

    /**
     * <p>isPattern.</p>
     *
     * @return a boolean.
     */
    public boolean isPattern() {
        return pattern;
    }

    /**
     * <p>Setter for the field <code>pattern</code>.</p>
     *
     * @param pattern a boolean.
     *
     * @return a {@link org.agatom.springatom.web.locale.beans.LocalizedMessageRequest} object.
     */
    public LocalizedMessageRequest setPattern(final boolean pattern) {
        this.pattern = pattern;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(keys)
                .addValue(pattern)
                .toString();
    }
}
