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

package org.agatom.springatom.web.rbuilder.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RBuilderColumn
        extends RBuilderBean
        implements Comparable<RBuilderColumn> {
    private static final long                  serialVersionUID = 2600080347152145806L;
    private static final Class<String>         DEFAULT_RENDER   = String.class;
    protected            String                prefix           = null;
    protected            String                columnName       = null;
    protected            Class<?>              columnClass      = Void.class;
    protected            Class<?>              renderClass      = DEFAULT_RENDER;
    protected            RBuilderColumnOptions options          = null;
    protected            Class<?>              elementClass     = null;

    public RBuilderColumn setOptions(final RBuilderColumnOptions options) {
        this.options = options;
        return this;
    }

    public RBuilderColumnOptions getOptions() {
        if (this.options == null) {
            this.options = new RBuilderColumnOptions();
        }
        return options;
    }

    public RBuilderColumn setPrefix(final String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public RBuilderColumn setColumnClass(final Class<?> columnClass) {
        this.columnClass = columnClass;
        return this;
    }

    public Class<?> getColumnClass() {
        return columnClass;
    }

    public String getColumnName() {
        return columnName;
    }

    public RBuilderColumn setColumnName(final String columnName) {
        this.columnName = columnName;
        return this;
    }

    public Class<?> getRenderClass() {
        if (this.renderClass == null) {
            this.renderClass = String.class;
        }
        return this.renderClass;
    }

    public void setRenderClass(final Class<?> renderClass) {
        this.renderClass = renderClass;
    }

    public RBuilderColumn setElementClass(final Class<?> elementClass) {
        this.elementClass = elementClass;
        return this;
    }

    public Class<?> getElementClass() {
        return elementClass;
    }

    /**
     * Evaluates if this column most likely represents single-valued column.
     *
     * @return true if so, false otherwise
     *
     * @see RBuilderColumn#isMultiValued()
     */

    @JsonIgnore
    public boolean isSingleValued() {
        return !this.isMultiValued();
    }

    /**
     * Evaluates is this columns most likely represents multi valued column.
     * Method checks if that's so by verifying if {@link RBuilderColumn#columnClass}
     * is assignable either from {@link java.lang.Iterable} or {@link java.util.Map}
     *
     * @return true if multi valued
     */

    @JsonIgnore
    public boolean isMultiValued() {
        return ClassUtils.isAssignable(Iterable.class, this.columnClass)
                || ClassUtils.isAssignable(Map.class, this.columnClass);
    }

    @Override
    public int compareTo(@Nonnull final RBuilderColumn column) {
        return ComparisonChain
                .start()
                .compare(this.columnName, column.columnName)
                .result();
    }

    @Override
    @JsonIgnore
    public String getMessageKey() {
        return String.format("%s.%s", this.prefix, this.columnName);
    }


    @Override
    protected Integer calculateId() {
        return this.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RBuilderColumn that = (RBuilderColumn) o;

        return Objects.equal(this.prefix, that.prefix) &&
                Objects.equal(this.columnName, that.columnName) &&
                Objects.equal(this.columnClass, that.columnClass) &&
                Objects.equal(this.renderClass, that.renderClass) &&
                Objects.equal(this.options, that.options) &&
                Objects.equal(this.label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(prefix, columnName, columnClass, renderClass,
                options, label);
    }
}
