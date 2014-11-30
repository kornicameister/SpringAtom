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

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * {@code SLocalizedMessage} wrapping
 * the information about the message for the given key.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SLocalizedMessage
        implements Serializable {
    private static final long serialVersionUID = -5800265126575659371L;
    private String  key;
    private String  message;
    private SLocale locale;

    /**
     * <p>Constructor for SLocalizedMessage.</p>
     */
    public SLocalizedMessage() {
        this.setKey("").setMessage("");
    }

    /**
     * <p>Constructor for SLocalizedMessage.</p>
     *
     * @param key     a {@link String} object.
     * @param message a {@link String} object.
     */
    public SLocalizedMessage(final String key, final String message) {
        this.key = key;
        this.message = message;
    }

    /**
     * <p>Getter for the field <code>key</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getKey() {
        return key;
    }

    /**
     * <p>Setter for the field <code>key</code>.</p>
     *
     * @param key a {@link String} object.
     *
     * @return a {@link org.agatom.springatom.cmp.locale.beans.SLocalizedMessage} object.
     */
    public SLocalizedMessage setKey(final String key) {
        this.key = key;
        return this;
    }

    /**
     * <p>Getter for the field <code>message</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getMessage() {
        return message;
    }

    /**
     * <p>Setter for the field <code>message</code>.</p>
     *
     * @param message a {@link String} object.
     *
     * @return a {@link org.agatom.springatom.cmp.locale.beans.SLocalizedMessage} object.
     */
    public SLocalizedMessage setMessage(final String message) {
        this.message = message;
        return this;
    }

    /**
     * <p>Getter for the field <code>locale</code>.</p>
     *
     * @return a {@link org.agatom.springatom.cmp.locale.beans.SLocale} object.
     */
    public SLocale getLocale() {
        return locale;
    }

    /**
     * <p>Setter for the field <code>locale</code>.</p>
     *
     * @param locale a {@link org.agatom.springatom.cmp.locale.beans.SLocale} object.
     *
     * @return a {@link org.agatom.springatom.cmp.locale.beans.SLocalizedMessage} object.
     */
    public SLocalizedMessage setLocale(final SLocale locale) {
        this.locale = locale;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SLocalizedMessage that = (SLocalizedMessage) o;

        return Objects.equal(this.key, that.key);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("key", key)
                .add("message", message)
                .add("locale", locale)
                .toString();
    }
}
