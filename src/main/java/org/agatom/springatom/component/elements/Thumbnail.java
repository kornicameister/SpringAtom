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

package org.agatom.springatom.component.elements;

import com.google.common.base.Objects;
import org.agatom.springatom.component.DefaultComponent;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class Thumbnail
        extends DefaultComponent {
    private String href    = null;
    private String alt     = null;
    private String tooltip = null;

    public String getHref() {
        return href;
    }

    public Thumbnail setHref(final String href) {
        this.href = href;
        return this;
    }

    public String getAlt() {
        return alt;
    }

    public Thumbnail setAlt(final String alt) {
        this.alt = alt;
        return this;
    }

    public String getTooltip() {
        return tooltip;
    }

    public Thumbnail setTooltip(final String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(href, alt, tooltip, title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Thumbnail that = (Thumbnail) o;

        return Objects.equal(this.href, that.href) &&
                Objects.equal(this.alt, that.alt) &&
                Objects.equal(this.tooltip, that.tooltip) &&
                Objects.equal(this.title, that.title);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(href)
                      .addValue(alt)
                      .addValue(tooltip)
                      .addValue(title)
                      .toString();
    }
}
