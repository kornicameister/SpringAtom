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

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportableEntity
        extends ReportableBean
        implements Comparable<ReportableEntity> {
    private static final long serialVersionUID = -5864111277348030161L;
    @Size(min = 1)
    private Set<ReportableColumn> columns;
    @NotNull
    private Class<?>              javaClass;
    @NotNull
    @Length(min = 5, max = 50)
    private String                name;

    public Class<?> getJavaClass() {
        return javaClass;
    }

    public String getJavaClassName() {
        return this.javaClass.getName();
    }

    public ReportableEntity setJavaClass(final Class<?> javaClass) {
        this.javaClass = javaClass;
        return this;
    }

    public ReportableEntity setName(final String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public ReportableEntity setColumns(final Set<ReportableColumn> columns) {
        this.columns = columns;
        return this;
    }

    public Set<ReportableColumn> getColumns() {
        if (this.columns == null) {
            this.columns = Sets.newTreeSet();
        }
        return columns;
    }

    public ReportableEntity addColumn(final ReportableColumn reportableColumn) {
        this.getColumns();
        this.columns.add(reportableColumn);
        return this;
    }

    public boolean hasColumn(final ReportableColumn reportableColumn) {
        this.getColumns();
        return this.columns.contains(reportableColumn);
    }

    public boolean hasColumns() {
        this.getColumns();
        return !this.columns.isEmpty();
    }

    public void clearColumns() {
        this.columns.clear();
    }

    @Override
    public String getMessageKey() {
        return ClassUtils.getShortName(this.javaClass).toLowerCase();
    }

    @Override
    public int compareTo(@Nonnull final ReportableEntity entity) {
        return ComparisonChain
                .start()
                .compare(this.label, entity.label)
                .result() * -1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReportableEntity that = (ReportableEntity) o;

        return Objects.equal(this.javaClass, that.javaClass) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.label, that.label) &&
                Objects.equal(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(javaClass, name, label, id);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(javaClass)
                      .addValue(name)
                      .addValue(label)
                      .addValue(id)
                      .toString();
    }
}
