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

package org.agatom.springatom.web.component.table.elements.extjs;

import org.agatom.springatom.web.component.table.elements.TableColumnComponent;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 03.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ExtJSTableColumn
		extends TableColumnComponent {
	private static final long   serialVersionUID = 7324208543889807894L;
	private              String xtype            = "gridcolumn";
	private              String dataIndex        = null;
	private              String tooltip          = null;
	/**
	 * Corresponds to the {@code type} property of <a href="http://docs.sencha.com/extjs/4.2.2/#!/api/Ext.data.Field'>Ext.data.Field</a>.
	 * It is required to set this attribute in order to get proper rendering method
	 */
	private              String fieldType        = null;

	public String getText() {
		return this.getLabel();
	}

	public ExtJSTableColumn setText(final String text) {
		return (ExtJSTableColumn) this.setLabel(text);
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public ExtJSTableColumn setDataIndex(final String dataIndex) {
		this.dataIndex = dataIndex;
		return this;
	}

	public String getFieldType() {
		if (fieldType == null) {
			this.fieldType = this.getDisplayAs() != null ? this.getDisplayAs().name() : null;
		}
		return fieldType;
	}

	public ExtJSTableColumn setFieldType(final String fieldType) {
		this.fieldType = fieldType;
		return this;
	}

	/**
	 * <p>Getter for the field <code>xtype</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getXtype() {
		return xtype;
	}

	/**
	 * <p>Setter for the field <code>xtype</code>.</p>
	 *
	 * @param xtype a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.extjs.ExtJSTableColumn} object.
	 */
	public ExtJSTableColumn setXtype(final String xtype) {
		this.xtype = xtype;
		return this;
	}

	public String getTooltip() {
		return tooltip;
	}

	public ExtJSTableColumn setTooltip(final String tooltip) {
		this.tooltip = tooltip;
		return this;
	}
}
