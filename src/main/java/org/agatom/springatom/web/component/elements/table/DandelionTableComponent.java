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

package org.agatom.springatom.web.component.elements.table;

import com.google.common.base.Objects;
import org.springframework.hateoas.Link;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class DandelionTableComponent
        extends TableComponent {
    private static final long    serialVersionUID    = 1822934065790049138L;
    protected            Link    url                 = null;
    protected            boolean deferRender         = true;
    protected            boolean infoVisible         = true;
    protected            boolean processingIndicator = true;
    protected            boolean scrollCollapse      = false;
    protected            boolean scroll              = false;
    protected            boolean sortable            = true;
    protected            boolean stateful            = true;

    public Link getUrl() {
        return url;
    }

    public DandelionTableComponent setUrl(final Link url) {
        this.url = url;
        return this;
    }

    public boolean isDeferRender() {
        return deferRender;
    }

    public DandelionTableComponent setDeferRender(final boolean deferRender) {
        this.deferRender = deferRender;
        return this;
    }

    public boolean isInfoVisible() {
        return infoVisible;
    }

    public DandelionTableComponent setInfoVisible(final boolean infoVisible) {
        this.infoVisible = infoVisible;
        return this;
    }

    public boolean isProcessingIndicator() {
        return processingIndicator;
    }

    public DandelionTableComponent setProcessingIndicator(final boolean processingIndicator) {
        this.processingIndicator = processingIndicator;
        return this;
    }

    public boolean isScrollCollapse() {
        return scrollCollapse;
    }

    public DandelionTableComponent setScrollCollapse(final boolean scrollCollapse) {
        this.scrollCollapse = scrollCollapse;
        return this;
    }

    public boolean isScroll() {
        return scroll;
    }

    public DandelionTableComponent setScroll(final boolean scroll) {
        this.scroll = scroll;
        return this;
    }

    public boolean isSortable() {
        return sortable;
    }

    public DandelionTableComponent setSortable(final boolean sortable) {
        this.sortable = sortable;
        return this;
    }

    public boolean isStateful() {
        return stateful;
    }

    public DandelionTableComponent setStateful(final boolean stateful) {
        this.stateful = stateful;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url, tableId);
    }

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
                      .addValue(title)
                      .toString();
    }
}
