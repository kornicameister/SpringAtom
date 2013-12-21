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

package org.agatom.springatom.component.elements.table;

import com.google.common.base.Objects;
import org.agatom.springatom.component.EmbeddableComponent;
import org.agatom.springatom.component.elements.DefaultComponent;
import org.agatom.springatom.core.util.Localized;
import org.springframework.data.domain.Sort;

import javax.annotation.Nonnull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class TableColumnComponent
        extends DefaultComponent
        implements EmbeddableComponent,
                   Localized {
    protected String         title              = null;
    protected String         property           = null;
    protected Sort.Direction sortDirection      = Sort.Direction.ASC;
    protected boolean        sortable           = true;
    protected boolean        filterable         = false;
    protected boolean        visible            = true;
    private   int            position           = -1;
    private   String         titleKey           = null;
    private   String         renderFunctionName = "renderValue";

    public String getRenderFunctionName() {
        return renderFunctionName;
    }

    public TableColumnComponent setRenderFunctionName(final String renderFunctionName) {
        this.renderFunctionName = renderFunctionName;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public TableColumnComponent setProperty(final String property) {
        this.property = property;
        return this;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public TableColumnComponent setSortDirection(final Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
        return this;
    }

    public boolean isSortable() {
        return sortable;
    }

    public TableColumnComponent setSortable(final boolean sortable) {
        this.sortable = sortable;
        return this;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public TableColumnComponent setFilterable(final boolean filterable) {
        this.filterable = filterable;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public TableColumnComponent setVisible(final boolean visible) {
        this.visible = visible;
        return this;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public TableColumnComponent setTitleKey(final String titleKey) {
        this.titleKey = titleKey;
        return this;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(final int position) {
        this.position = position;
    }

    @Override
    public int compareTo(@Nonnull final EmbeddableComponent ec) {
        return Integer.compare(this.position, ec.getPosition());
    }

    @Override
    public String getMessageKey() {
        return this.getTitleKey();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, property, sortDirection, sortable, filterable, visible,
                position, titleKey, title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TableColumnComponent that = (TableColumnComponent) o;

        return Objects.equal(this.title, that.title) &&
                Objects.equal(this.property, that.property) &&
                Objects.equal(this.sortDirection, that.sortDirection) &&
                Objects.equal(this.sortable, that.sortable) &&
                Objects.equal(this.filterable, that.filterable) &&
                Objects.equal(this.visible, that.visible) &&
                Objects.equal(this.position, that.position) &&
                Objects.equal(this.titleKey, that.titleKey) &&
                Objects.equal(this.title, that.title);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(title)
                      .addValue(property)
                      .addValue(sortDirection)
                      .addValue(sortable)
                      .addValue(filterable)
                      .addValue(visible)
                      .addValue(position)
                      .addValue(titleKey)
                      .addValue(title)
                      .toString();
    }
}
