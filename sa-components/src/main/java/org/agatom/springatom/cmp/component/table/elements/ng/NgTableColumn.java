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

package org.agatom.springatom.cmp.component.table.elements.ng;

import org.agatom.springatom.cmp.component.table.elements.TableColumnComponent;

/**
 * {@code NgTableColumn} is a {@link org.agatom.springatom.cmp.component.table.elements.TableColumnComponent}
 * and defines information required to properly display the column as a part of the grid
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 03.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class NgTableColumn
        extends TableColumnComponent {
    private static final long   serialVersionUID = 7324208543889807894L;
    private              String xtype            = "gridcolumn";
    private              String dataIndex        = null;
    private              String tooltip          = null;
    /**
     * Corresponds to the {@code type} property of <a href="http://docs.sencha.com/ng/4.2.2/#!/api/Ext.data.Field'>Ext.data.Field</a>.
     * It is required to set this attribute in order to get proper rendering method
     */
    private              String fieldType        = null;

    public String getText() {
        return this.getLabel();
    }

    public NgTableColumn setText(final String text) {
        return (NgTableColumn) this.setLabel(text);
    }

    public String getDataIndex() {
        return dataIndex;
    }

    public NgTableColumn setDataIndex(final String dataIndex) {
        this.dataIndex = dataIndex;
        return this;
    }

    public String getFieldType() {
        if (fieldType == null) {
            this.fieldType = this.getDisplayAs() != null ? this.getDisplayAs().name() : null;
        }
        return fieldType;
    }

    public NgTableColumn setFieldType(final String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    /**
     * <p>Getter for the field <code>xtype</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getXtype() {
        return xtype;
    }

    /**
     * <p>Setter for the field <code>xtype</code>.</p>
     *
     * @param xtype a {@link String} object.
     *
     * @return a {@link org.agatom.springatom.cmp.component.table.elements.ng.NgTableColumn} object.
     */
    public NgTableColumn setXtype(final String xtype) {
        this.xtype = xtype;
        return this;
    }

    public String getTooltip() {
        return tooltip;
    }

    public NgTableColumn setTooltip(final String tooltip) {
        this.tooltip = tooltip;
        return this;
    }
}
