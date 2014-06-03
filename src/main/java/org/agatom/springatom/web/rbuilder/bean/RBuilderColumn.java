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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import org.springframework.util.ClassUtils;

import java.util.Map;

/**
 * <p>RBuilderColumn class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RBuilderColumn
		extends RBuilderBean {
	/** Constant <code>DEFAULT_ORDER=-1</code> */
	public static final  int                   DEFAULT_ORDER    = -1;
	protected            Integer               order            = DEFAULT_ORDER;
	private static final long                  serialVersionUID = 2600080347152145806L;
	private static final Class<String>         DEFAULT_RENDER   = String.class;
	protected            Class<?>              renderClass      = DEFAULT_RENDER;
	protected            String                prefix           = null;
	protected            String                columnName       = null;
	protected            Class<?>              columnClass      = Void.class;
	protected            RBuilderColumnOptions options          = null;
	protected            Class<?>              elementClass     = null;

	/**
	 * <p>Getter for the field <code>order</code>.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * <p>Setter for the field <code>order</code>.</p>
	 *
	 * @param order a {@link java.lang.Integer} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn} object.
	 */
	public RBuilderColumn setOrder(final Integer order) {
		this.order = order;
		return this;
	}

	/**
	 * <p>Getter for the field <code>options</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumnOptions} object.
	 */
	public RBuilderColumnOptions getOptions() {
		if (this.options == null) {
			this.options = new RBuilderColumnOptions();
		}
		return options;
	}

	/**
	 * <p>Setter for the field <code>options</code>.</p>
	 *
	 * @param options a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumnOptions} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn} object.
	 */
	public RBuilderColumn setOptions(final RBuilderColumnOptions options) {
		this.options = options;
		return this;
	}

	/**
	 * <p>Getter for the field <code>prefix</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * <p>Setter for the field <code>prefix</code>.</p>
	 *
	 * @param prefix a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn} object.
	 */
	public RBuilderColumn setPrefix(final String prefix) {
		this.prefix = prefix;
		return this;
	}

	/**
	 * <p>Getter for the field <code>columnClass</code>.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getColumnClass() {
		return columnClass;
	}

	/**
	 * <p>Setter for the field <code>columnClass</code>.</p>
	 *
	 * @param columnClass a {@link java.lang.Class} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn} object.
	 */
	public RBuilderColumn setColumnClass(final Class<?> columnClass) {
		this.columnClass = columnClass;
		return this;
	}

	/**
	 * <p>Getter for the field <code>columnName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * <p>Setter for the field <code>columnName</code>.</p>
	 *
	 * @param columnName a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn} object.
	 */
	public RBuilderColumn setColumnName(final String columnName) {
		this.columnName = columnName;
		return this;
	}

	/**
	 * <p>Getter for the field <code>renderClass</code>.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getRenderClass() {
		if (this.renderClass == null) {
			this.renderClass = String.class;
		}
		return this.renderClass;
	}

	/**
	 * <p>Setter for the field <code>renderClass</code>.</p>
	 *
	 * @param renderClass a {@link java.lang.Class} object.
	 */
	public void setRenderClass(final Class<?> renderClass) {
		this.renderClass = renderClass;
	}

	/**
	 * <p>Getter for the field <code>elementClass</code>.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getElementClass() {
		return elementClass;
	}

	/**
	 * <p>Setter for the field <code>elementClass</code>.</p>
	 *
	 * @param elementClass a {@link java.lang.Class} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn} object.
	 */
	public RBuilderColumn setElementClass(final Class<?> elementClass) {
		this.elementClass = elementClass;
		return this;
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
	 * Method checks if that's so by verifying if {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn#columnClass}
	 * is assignable either from {@link java.lang.Iterable} or {@link java.util.Map}
	 *
	 * @return true if multi valued
	 */
	@JsonIgnore
	public boolean isMultiValued() {
		return ClassUtils.isAssignable(Iterable.class, this.columnClass)
				|| ClassUtils.isAssignable(Map.class, this.columnClass);
	}

	/** {@inheritDoc} */
	@Override
	@JsonIgnore
	public String getMessageKey() {
		return String.format("%s.%s", this.prefix, this.columnName);
	}


	/** {@inheritDoc} */
	@Override
	protected Integer calculateId() {
		return this.hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(order, prefix, columnName, columnClass, renderClass, options,
				elementClass, label, id);
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

		RBuilderColumn that = (RBuilderColumn) o;

		return Objects.equal(this.order, that.order) &&
				Objects.equal(this.prefix, that.prefix) &&
				Objects.equal(this.columnName, that.columnName) &&
				Objects.equal(this.columnClass, that.columnClass) &&
				Objects.equal(this.renderClass, that.renderClass) &&
				Objects.equal(this.options, that.options) &&
				Objects.equal(this.elementClass, that.elementClass) &&
				Objects.equal(this.label, that.label) &&
				Objects.equal(this.id, that.id);
	}
}
