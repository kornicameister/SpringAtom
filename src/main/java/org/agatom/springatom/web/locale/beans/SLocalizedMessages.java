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
import com.google.common.collect.Sets;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.Locale;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SLocalizedMessages
        implements Serializable {
    private static final long serialVersionUID = 4409512355804928387L;
    private Set<SLocalizedMessage> preferences;

    public Set<SLocalizedMessage> getPreferences() {
        return this.preferences;
    }

    public SLocalizedMessages setPreferences(final Set<SLocalizedMessage> preferences) {
        this.preferences = preferences;
        return this;
    }

    public SLocalizedMessages put(final SLocalizedMessage localizedMessage) {
        return this.put(Sets.newHashSet(localizedMessage));
    }

    public SLocalizedMessages put(final Set<SLocalizedMessage> preferences) {
        if (this.preferences == null) {
            this.preferences = Sets.newHashSet();
        }
        this.preferences.addAll(preferences);
        return this;
    }

    public SLocalizedMessages put(final String key, final String pref, Locale locale) {
        if (key != null && pref != null) {

            if (locale == null) {
                locale = LocaleContextHolder.getLocale();
            }

            if (this.preferences == null) {
                this.preferences = Sets.newHashSet();
            }
            this.preferences.add(new SLocalizedMessage().setKey(key).setMessage(pref).setLocale(
                    SLocale.fromLocale(locale)
            ));
        }
        return this;
    }

    public int size() {
        return this.preferences.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SLocalizedMessages that = (SLocalizedMessages) o;

        return Objects.equal(this.preferences, that.preferences);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(preferences);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(preferences)
                      .toString();
    }
}
