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

package org.agatom.springatom.web.rbuilder.conversion.type;

import com.google.common.base.Objects;
import org.agatom.springatom.web.rbuilder.conversion.ColumnTypeConversionBranch;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class RBuilderConvertiblePair
        implements Serializable {
    private static final long serialVersionUID = 4975825247135266596L;
    private ConvertiblePair            convertiblePair;
    private ColumnTypeConversionBranch branch;

    public RBuilderConvertiblePair setConvertiblePair(final ConvertiblePair convertiblePair) {
        this.convertiblePair = convertiblePair;
        return this;
    }

    public RBuilderConvertiblePair setBranch(final ColumnTypeConversionBranch branch) {
        this.branch = branch;
        return this;
    }

    public ConvertiblePair getConvertiblePair() {
        return convertiblePair;
    }

    public ColumnTypeConversionBranch getBranch() {
        return branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RBuilderConvertiblePair that = (RBuilderConvertiblePair) o;

        return Objects.equal(this.convertiblePair, that.convertiblePair) &&
                Objects.equal(this.branch, that.branch);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(convertiblePair, branch);
    }
}
