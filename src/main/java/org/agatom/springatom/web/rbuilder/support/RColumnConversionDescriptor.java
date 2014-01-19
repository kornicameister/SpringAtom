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

package org.agatom.springatom.web.rbuilder.support;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.agatom.springatom.core.conversion.ConvertiblePair;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * {@code RColumnConversionDescriptor} describes the association between original {@link org.agatom.springatom.web.rbuilder.bean.ReportableColumn#getColumnClass()}
 * and user chosen target class identified by {@link RColumnConversionDescriptor#getTargetClass()}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
// TODO investigate reusing of code from Converters of Spring
public class RColumnConversionDescriptor
        implements ConvertiblePair,
                   Serializable,
                   Comparable<RColumnConversionDescriptor> {
    private static final long serialVersionUID = -3950594352760043269L;
    private final Class<?> sourceClass;
    private final Class<?> targetClass;
    private final String   label;

    public RColumnConversionDescriptor(final Class<?> sourceType, final Class<?> targetType, final String name) {
        this.sourceClass = sourceType;
        this.targetClass = targetType;
        this.label = name;
    }

    @Override
    public Class<?> getSourceClass() {
        return this.sourceClass;
    }

    @Override
    public Class<?> getTargetClass() {
        return this.targetClass;
    }

    public String getSourceClassName() {
        return this.sourceClass.getName();
    }

    public String getTargetClassName() {
        return this.targetClass.getName();
    }

    public String getLabel() {
        return label;
    }

    @Override
    public int compareTo(@Nonnull final RColumnConversionDescriptor prop) {
        return ComparisonChain.start().compare(this.label, prop.label).result() - 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RColumnConversionDescriptor that = (RColumnConversionDescriptor) o;

        return Objects.equal(this.sourceClass, that.sourceClass) &&
                Objects.equal(this.targetClass, that.targetClass);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sourceClass, targetClass);
    }
}
