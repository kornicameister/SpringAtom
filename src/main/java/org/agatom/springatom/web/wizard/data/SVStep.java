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

package org.agatom.springatom.web.wizard.data;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SVStep
        implements Serializable {
    private static final long serialVersionUID = -61364689221832361L;
    private String         name;
    private List<SVBInput> data;

    public List<SVBInput> getData() {
        return data;
    }

    public SVStep setData(final List<SVBInput> data) {
        this.data = data;
        return this;
    }

    public String getName() {
        return name;
    }

    public SVStep setName(final String name) {
        this.name = name;
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

        SVStep that = (SVStep) o;

        return Objects.equal(this.name, that.name) &&
                Objects.equal(this.data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, data);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(name)
                      .addValue(data)
                      .toString();
    }
}
