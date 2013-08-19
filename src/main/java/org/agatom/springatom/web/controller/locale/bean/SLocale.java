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

package org.agatom.springatom.web.controller.locale.bean;

import com.google.common.base.Objects;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SLocale {
    private String tag;
    private String language;
    private String country;

    public String getTag() {
        return tag;
    }

    public SLocale setTag(final String tag) {
        this.tag = tag;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public SLocale setLanguage(final String language) {
        this.language = language;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public SLocale setCountry(final String country) {
        this.country = country;
        return this;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("tag", tag)
                      .add("language", language)
                      .add("country", country)
                      .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SLocale that = (SLocale) o;

        return Objects.equal(this.tag, that.tag) &&
                Objects.equal(this.language, that.language) &&
                Objects.equal(this.country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tag, language, country);
    }
}
