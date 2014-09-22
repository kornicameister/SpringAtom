/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.web.component.infopages.mapping;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.domain.Persistable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-10</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageMapping {
    private String                          rel  = null;
    private Class<? extends Persistable<?>> type = null;

    public String getRel() {
        return rel;
    }

    public InfoPageMapping setRel(final String rel) {
        this.rel = rel;
        return this;
    }

    public Class<? extends Persistable<?>> getType() {
        return type;
    }

    public InfoPageMapping setType(final Class<? extends Persistable<?>> type) {
        this.type = type;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rel, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoPageMapping that = (InfoPageMapping) o;

        return Objects.equal(this.rel, that.rel) &&
                Objects.equal(this.type, that.type);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("rel", rel)
                .add("type", type)
                .toString();
    }
}
