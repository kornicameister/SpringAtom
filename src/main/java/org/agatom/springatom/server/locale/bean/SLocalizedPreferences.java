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

package org.agatom.springatom.server.locale.bean;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SLocalizedPreferences
        implements Serializable {
    private String                     paramName;
    private SLocale                    locale;
    private List<SLocalizedPreference> preferences;

    public String getParamName() {
        return paramName;
    }

    public SLocalizedPreferences setParamName(final String paramName) {
        this.paramName = paramName;
        return this;
    }

    public SLocale getLocale() {
        return this.locale;
    }

    public SLocalizedPreferences setLocale(final SLocale locale) {
        this.locale = locale;
        return this;
    }

    public List<SLocalizedPreference> getPreferences() {
        return this.preferences;
    }

    public SLocalizedPreferences setPreferences(final List<SLocalizedPreference> preferences) {
        this.preferences = preferences;
        return this;
    }

    public SLocalizedPreferences put(final List<SLocalizedPreference> preferences) {
        if (this.preferences == null) {
            this.preferences = Lists.newArrayList();
        }
        this.preferences.addAll(preferences);
        return this;
    }

    public SLocalizedPreferences put(final String key, final String pref) {
        if (this.preferences == null) {
            this.preferences = Lists.newArrayList();
        }
        this.preferences.add(new SLocalizedPreference().setKey(key).setValue(pref));
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

        SLocalizedPreferences that = (SLocalizedPreferences) o;

        return Objects.equal(this.locale, that.locale) &&
                Objects.equal(this.preferences, that.preferences);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(locale, preferences);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(paramName)
                      .addValue(locale)
                      .addValue(preferences)
                      .toString();
    }
}
