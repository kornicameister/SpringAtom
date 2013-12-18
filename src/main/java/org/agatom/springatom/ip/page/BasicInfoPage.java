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

package org.agatom.springatom.ip.page;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.agatom.springatom.ip.SInfoPage;
import org.springframework.util.Assert;

/**
 * {@code BasicInfoPage} is a default implementation for {@link org.agatom.springatom.ip.SInfoPage} interface
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class BasicInfoPage
        implements SInfoPage {
    private String path;
    private String rel;

    @Override
    public String getPath() {
        return this.path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    @Override
    public String getRel() {
        return this.rel;
    }

    public void setRel(final String rel) {
        this.rel = rel;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public int compareTo(final SInfoPage infoPage) {
        Assert.notNull(infoPage);
        return ComparisonChain
                .start()
                .compare(this.path, infoPage.getPath())
                .compare(this.rel, infoPage.getRel())
                .result();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path, rel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasicInfoPage that = (BasicInfoPage) o;

        return Objects.equal(this.path, that.path) &&
                Objects.equal(this.rel, that.rel);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(path)
                      .addValue(rel)
                      .toString();
    }
}
