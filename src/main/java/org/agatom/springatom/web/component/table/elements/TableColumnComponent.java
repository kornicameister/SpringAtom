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

package org.agatom.springatom.web.component.table.elements;

import com.google.common.base.Objects;
import org.agatom.springatom.web.component.core.EmbeddableComponent;
import org.agatom.springatom.web.component.core.elements.DefaultComponent;
import org.agatom.springatom.web.component.infopages.elements.meta.AttributeDisplayAs;

import javax.annotation.Nonnull;

/**
 * {@code TableColumnComponent} in an abstract representation of the single <b>column</b>.
 * Each <b>column</b> is part of the {@link TableComponent} through {@link org.agatom.springatom.web.component.core.elements.ContentComponent}
 * generic constraint.
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 03.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
abstract public class TableColumnComponent
		extends DefaultComponent
		implements EmbeddableComponent {
	private static final long               serialVersionUID = 7798481976595660140L;
	private              int                position         = -1;
	private              boolean            sortable         = false;
	private              AttributeDisplayAs displayAs        = null;

	/** {@inheritDoc} */
	@Override
	public int getPosition() {
		return this.position;
	}

	/** {@inheritDoc} */
	@Override
	public void setPosition(final int position) {
		this.position = position;
	}

	public boolean isSortable() {
		return sortable;
	}

	public TableColumnComponent setSortable(final boolean sortable) {
		this.sortable = sortable;
		return this;
	}

	public AttributeDisplayAs getDisplayAs() {
		return displayAs;
	}

	public TableColumnComponent setDisplayAs(final AttributeDisplayAs displayAs) {
		this.displayAs = displayAs;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(@Nonnull final EmbeddableComponent ec) {
		return Integer.compare(this.position, ec.getPosition());
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(position);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TableColumnComponent that = (TableColumnComponent) o;

		return Objects.equal(this.position, that.position);
	}

}
