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
import org.agatom.springatom.web.component.table.elements.TableComponent;
import org.springframework.hateoas.Link;

/**
 * {@code DandelionTableColumnComponent} describes single table structure in context of <a href="http://dandelion.github.io/datatables/">Dandelion Tables</a>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class DandelionTableComponent
		extends TableComponent<DandelionTableColumnComponent> {
	private static final long    serialVersionUID    = 1822934065790049138L;
	protected            Link    url                 = null;
	protected            boolean deferRender         = true;
	protected            boolean infoVisible         = true;
	protected            boolean processingIndicator = true;
	protected            boolean scrollCollapse      = false;
	protected            boolean scroll              = false;
	protected            boolean sortable            = true;
	protected            boolean stateful            = true;

	/**
	 * <p>Getter for the field <code>url</code>.</p>
	 *
	 * @return a {@link org.springframework.hateoas.Link} object.
	 */
	public Link getUrl() {
		return url;
	}

	/**
	 * <p>Setter for the field <code>url</code>.</p>
	 *
	 * @param url a {@link org.springframework.hateoas.Link} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableComponent} object.
	 */
	public DandelionTableComponent setUrl(final Link url) {
		this.url = url;
		return this;
	}

	/**
	 * <p>isDeferRender.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isDeferRender() {
		return deferRender;
	}

	/**
	 * <p>Setter for the field <code>deferRender</code>.</p>
	 *
	 * @param deferRender a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableComponent} object.
	 */
	public DandelionTableComponent setDeferRender(final boolean deferRender) {
		this.deferRender = deferRender;
		return this;
	}

	/**
	 * <p>isInfoVisible.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isInfoVisible() {
		return infoVisible;
	}

	/**
	 * <p>Setter for the field <code>infoVisible</code>.</p>
	 *
	 * @param infoVisible a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableComponent} object.
	 */
	public DandelionTableComponent setInfoVisible(final boolean infoVisible) {
		this.infoVisible = infoVisible;
		return this;
	}

	/**
	 * <p>isProcessingIndicator.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isProcessingIndicator() {
		return processingIndicator;
	}

	/**
	 * <p>Setter for the field <code>processingIndicator</code>.</p>
	 *
	 * @param processingIndicator a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableComponent} object.
	 */
	public DandelionTableComponent setProcessingIndicator(final boolean processingIndicator) {
		this.processingIndicator = processingIndicator;
		return this;
	}

	/**
	 * <p>isScrollCollapse.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isScrollCollapse() {
		return scrollCollapse;
	}

	/**
	 * <p>Setter for the field <code>scrollCollapse</code>.</p>
	 *
	 * @param scrollCollapse a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableComponent} object.
	 */
	public DandelionTableComponent setScrollCollapse(final boolean scrollCollapse) {
		this.scrollCollapse = scrollCollapse;
		return this;
	}

	/**
	 * <p>isScroll.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isScroll() {
		return scroll;
	}

	/**
	 * <p>Setter for the field <code>scroll</code>.</p>
	 *
	 * @param scroll a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableComponent} object.
	 */
	public DandelionTableComponent setScroll(final boolean scroll) {
		this.scroll = scroll;
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
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableComponent} object.
	 */
	public DandelionTableComponent setSortable(final boolean sortable) {
		this.sortable = sortable;
		return this;
	}

	/**
	 * <p>isStateful.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isStateful() {
		return stateful;
	}

	/**
	 * <p>Setter for the field <code>stateful</code>.</p>
	 *
	 * @param stateful a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableComponent} object.
	 */
	public DandelionTableComponent setStateful(final boolean stateful) {
		this.stateful = stateful;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(url, tableId);
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

		DandelionTableComponent that = (DandelionTableComponent) o;

		return Objects.equal(this.url, that.url) &&
				Objects.equal(this.tableId, that.tableId);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(url)
				.addValue(deferRender)
				.addValue(infoVisible)
				.addValue(processingIndicator)
				.addValue(scrollCollapse)
				.addValue(scroll)
				.addValue(sortable)
				.addValue(stateful)
				.addValue(tableId)
				.addValue(rowsOnPage)
				.addValue(filterable)
				.addValue(content)
				.addValue(label)
				.toString();
	}
}
