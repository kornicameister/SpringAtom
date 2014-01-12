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

package org.agatom.springatom.web.beans.search;

import com.google.common.base.Objects;
import org.agatom.springatom.web.beans.WebBean;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SSearchCommandBean
        implements WebBean {

    private static final String BEAN_ID          = "searchCommandBean";
    private static final long   serialVersionUID = 3398125574712945412L;
    private String phrase;

    public String getPhrase() {
        return phrase;
    }

    public SSearchCommandBean setPhrase(final String phrase) {
        this.phrase = phrase;
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

        SSearchCommandBean that = (SSearchCommandBean) o;

        return Objects.equal(this.phrase, that.phrase);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(phrase);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(phrase)
                      .toString();
    }

    @Override
    public String getBeanId() {
        return BEAN_ID;
    }
}
