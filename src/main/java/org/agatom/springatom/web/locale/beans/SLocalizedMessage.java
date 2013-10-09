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
import org.agatom.springatom.web.beans.WebBean;

/**
 * {@code SLocalizedMessage} is a {@link org.agatom.springatom.web.beans.WebBean} wrapping
 * the information about the message for the given key.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SLocalizedMessage
        implements WebBean {
    private static final String BEAN_ID = "localizedMessage";
    private String  key;
    private String  message;
    private SLocale locale;

    public SLocalizedMessage() {
        this.setKey("").setMessage("");
    }

    public SLocalizedMessage(final String key, final String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public SLocalizedMessage setKey(final String key) {
        this.key = key;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SLocalizedMessage setMessage(final String message) {
        this.message = message;
        return this;
    }

    public SLocale getLocale() {
        return locale;
    }

    public SLocalizedMessage setLocale(final SLocale locale) {
        this.locale = locale;
        return this;
    }

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

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    @Override public String toString() {
        return Objects.toStringHelper(this)
                      .add("key", key)
                      .add("message", message)
                      .add("locale", locale)
                      .toString();
    }

    @Override
    public String getBeanId() {
        return BEAN_ID;
    }
}
