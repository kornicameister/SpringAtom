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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * {@code RBuilderEntity} carries information about all sorts entities that are used in
 * {@link org.agatom.springatom.web.flows.wizards.wizard.rbuilder.ReportWizard}
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RBuilderEntity
		extends RBuilderBean
		implements Comparable<RBuilderEntity> {
	private static final long serialVersionUID = -5864111277348030161L;
	@Size(min = 1)
	private Set<RBuilderColumn> columns;
	@NotNull
	private Class<?>            javaClass;
	@NotNull
	@Length(min = 5, max = 50)
	private String              name;

	/**
	 * <p>Getter for the field <code>javaClass</code>.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getJavaClass() {
		return javaClass;
	}

	/**
	 * <p>Setter for the field <code>javaClass</code>.</p>
	 *
	 * @param javaClass a {@link java.lang.Class} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} object.
	 */
	public RBuilderEntity setJavaClass(final Class<?> javaClass) {
		this.javaClass = javaClass;
		return this;
	}

	/**
	 * <p>getJavaClassName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getJavaClassName() {
		return this.javaClass.getName();
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} object.
	 */
	public RBuilderEntity setName(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * <p>addColumn.</p>
	 *
	 * @param reportableColumn a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} object.
	 */
	public RBuilderEntity addColumn(final RBuilderColumn reportableColumn) {
		this.getColumns();
		this.columns.add(reportableColumn);
		return this;
	}

	/**
	 * <p>Getter for the field <code>columns</code>.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<RBuilderColumn> getColumns() {
		if (this.columns == null) {
			this.columns = Sets.newLinkedHashSet();
		}
		return columns;
	}

	/**
	 * <p>Setter for the field <code>columns</code>.</p>
	 *
	 * @param columns a {@link java.util.Set} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} object.
	 */
	public RBuilderEntity setColumns(final Set<RBuilderColumn> columns) {
		this.columns = Sets.newLinkedHashSet(columns);
		return this;
	}

	/**
	 * Removes single {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn} from the set of columns for this {@code entity}
	 *
	 * @param reportableColumn column to be removed
	 *
	 * @return this {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity}
	 */
	public RBuilderEntity removeColumn(final RBuilderColumn reportableColumn) {
		this.getColumns();
		this.columns.remove(reportableColumn);
		return this;
	}

	/**
	 * <p>hasColumn.</p>
	 *
	 * @param reportableColumn a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn} object.
	 *
	 * @return a boolean.
	 */
	public boolean hasColumn(final RBuilderColumn reportableColumn) {
		this.getColumns();
		return this.columns.contains(reportableColumn);
	}

	/**
	 * <p>hasColumns.</p>
	 *
	 * @return a boolean.
	 */
	public boolean hasColumns() {
		this.getColumns();
		return !this.columns.isEmpty();
	}

	/**
	 * <p>clearColumns.</p>
	 */
	public void clearColumns() {
		this.columns.clear();
	}

	/** {@inheritDoc} */
	@Override
	public String getMessageKey() {
		return ClassUtils.getShortName(this.javaClass).toLowerCase();
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(@Nonnull final RBuilderEntity entity) {
		return ComparisonChain
				.start()
				.compare(this.label, entity.label)
				.result() * -1;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(javaClass, name, label, id);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		RBuilderEntity that = (RBuilderEntity) o;

		return Objects.equal(this.javaClass, that.javaClass) &&
				Objects.equal(this.name, that.name) &&
				Objects.equal(this.label, that.label) &&
				Objects.equal(this.id, that.id);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(javaClass)
				.addValue(name)
				.addValue(label)
				.addValue(id)
				.toString();
	}

	/** {@inheritDoc} */
	@Override
	protected Integer calculateId() {
		return this.javaClass.hashCode();
	}
}
