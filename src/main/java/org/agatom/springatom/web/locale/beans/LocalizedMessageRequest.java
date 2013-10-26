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

package org.agatom.springatom.web.locale.beans;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class LocalizedMessageRequest
        implements Serializable {
    private static final long serialVersionUID = 5395003418793746321L;
    private String[] keys;
    private boolean  pattern;

    public String[] getKeys() {
        return keys;
    }

    public LocalizedMessageRequest setKeys(final String[] keys) {
        this.keys = keys;
        return this;
    }

    public boolean isPattern() {
        return pattern;
    }

    public LocalizedMessageRequest setPattern(final boolean pattern) {
        this.pattern = pattern;
        return this;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(keys)
                      .addValue(pattern)
                      .toString();
    }
}
