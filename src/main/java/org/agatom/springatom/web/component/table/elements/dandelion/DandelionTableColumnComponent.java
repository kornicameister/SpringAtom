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

package org.agatom.springatom.web.component.table.elements.dandelion;

import com.google.common.base.Objects;
import org.agatom.springatom.core.util.Localized;
import org.agatom.springatom.web.component.core.EmbeddableComponent;
import org.agatom.springatom.web.component.table.elements.TableColumnComponent;
import org.springframework.data.domain.Sort;

/**
 * <p>DandelionTableColumnComponent class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class DandelionTableColumnComponent
		extends TableColumnComponent
		implements EmbeddableComponent,
		Localized {
	private static final long           serialVersionUID   = 1814716311674625618L;
	protected            String         title              = null;
	protected            String         property           = null;
	protected            Sort.Direction sortDirection      = Sort.Direction.ASC;
	protected            boolean        sortable           = true;
	protected            boolean        filterable         = false;
	protected            boolean        visible            = true;
	private              String         titleKey           = null;
	private              String         renderFunctionName = "renderValue";

	/**
	 * <p>Getter for the field <code>renderFunctionName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getRenderFunctionName() {
		return renderFunctionName;
	}

	/**
	 * <p>Setter for the field <code>renderFunctionName</code>.</p>
	 *
	 * @param renderFunctionName a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableColumnComponent} object.
	 */
	public DandelionTableColumnComponent setRenderFunctionName(final String renderFunctionName) {
		this.renderFunctionName = renderFunctionName;
		return this;
	}

	/**
	 * <p>Getter for the field <code>property</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * <p>Setter for the field <code>property</code>.</p>
	 *
	 * @param property a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableColumnComponent} object.
	 */
	public DandelionTableColumnComponent setProperty(final String property) {
		this.property = property;
		return this;
	}

	/**
	 * <p>Getter for the field <code>sortDirection</code>.</p>
	 *
	 * @return a {@link org.springframework.data.domain.Sort.Direction} object.
	 */
	public Sort.Direction getSortDirection() {
		return sortDirection;
	}

	/**
	 * <p>Setter for the field <code>sortDirection</code>.</p>
	 *
	 * @param sortDirection a {@link org.springframework.data.domain.Sort.Direction} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableColumnComponent} object.
	 */
	public DandelionTableColumnComponent setSortDirection(final Sort.Direction sortDirection) {
		this.sortDirection = sortDirection;
		return this;
	}

	/**
	 * <p>isSortable.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isSortable() {
		return sortable;
	}

	/**
	 * <p>Setter for the field <code>sortable</code>.</p>
	 *
	 * @param sortable a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableColumnComponent} object.
	 */
	public DandelionTableColumnComponent setSortable(final boolean sortable) {
		this.sortable = sortable;
		return this;
	}

	/**
	 * <p>isFilterable.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isFilterable() {
		return filterable;
	}

	/**
	 * <p>Setter for the field <code>filterable</code>.</p>
	 *
	 * @param filterable a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableColumnComponent} object.
	 */
	public DandelionTableColumnComponent setFilterable(final boolean filterable) {
		this.filterable = filterable;
		return this;
	}

	/**
	 * <p>isVisible.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * <p>Setter for the field <code>visible</code>.</p>
	 *
	 * @param visible a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableColumnComponent} object.
	 */
	public DandelionTableColumnComponent setVisible(final boolean visible) {
		this.visible = visible;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getMessageKey() {
		return this.getTitleKey();
	}

	/**
	 * <p>Getter for the field <code>titleKey</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTitleKey() {
		return titleKey;
	}

	/**
	 * <p>Setter for the field <code>titleKey</code>.</p>
	 *
	 * @param titleKey a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableColumnComponent} object.
	 */
	public DandelionTableColumnComponent setTitleKey(final String titleKey) {
		this.titleKey = titleKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(title, property, sortDirection, sortable, filterable, visible, titleKey, title, super.hashCode());
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

		DandelionTableColumnComponent that = (DandelionTableColumnComponent) o;

		return Objects.equal(this.title, that.title) &&
				Objects.equal(this.property, that.property) &&
				Objects.equal(this.sortDirection, that.sortDirection) &&
				Objects.equal(this.sortable, that.sortable) &&
				Objects.equal(this.filterable, that.filterable) &&
				Objects.equal(this.visible, that.visible) &&
				Objects.equal(this.titleKey, that.titleKey) &&
				Objects.equal(this.title, that.title) &&
				super.equals(o);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(title)
				.addValue(property)
				.addValue(sortDirection)
				.addValue(sortable)
				.addValue(filterable)
				.addValue(visible)
				.addValue(titleKey)
				.addValue(title)
				.toString();
	}
}
