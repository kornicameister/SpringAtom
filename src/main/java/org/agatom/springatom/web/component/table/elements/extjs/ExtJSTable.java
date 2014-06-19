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

import com.google.common.collect.Sets;
import org.agatom.springatom.web.component.table.elements.TableComponent;
import org.agatom.springatom.web.component.table.elements.extjs.feature.ExtJSTableFeature;
import org.agatom.springatom.web.component.table.elements.extjs.store.ExtJSStoreField;

import java.util.Set;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 03.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class ExtJSTable
		extends TableComponent<ExtJSTableColumn> {
	private static final long                   serialVersionUID = 614019837253089296L;
	private              Set<ExtJSTableFeature> features         = null;
	private              Set<String>            plugins          = null;
	private              Set<ExtJSStoreField>   fields           = null;
	private              String                 store            = null;
	private              int                    minHeight        = -1;
	private              int                    maxHeight        = -1;
	private              boolean                animCollapse     = false;
	private              boolean                collapsible      = false;
	private              boolean                sortableColumns  = false;
	private              boolean                rowLines         = true;
	private              boolean                border           = true;
	private              boolean                forceFit         = true;

	/**
	 * <p>Constructor for ExtJSTable.</p>
	 */
	public ExtJSTable() {
		this.initDefaults();
	}

	private void initDefaults() {
		super.addContent(new ExtJSTableColumn().setXtype("rownumberer"));
		this.fields = Sets.newHashSet();
	}

	public ExtJSTable(final String tableId, final String builderId) {
		super(tableId, builderId);
		this.initDefaults();
	}

	/**
	 * Returns {@code storeId}. By default it is a combination of {@link #tableId} and word <b>Store</b>
	 *
	 * @return store id
	 */
	public String getStore() {
		if (this.store == null) {
			this.store = String.format("%sStore", this.tableId);
		}
		return store;
	}

	public ExtJSTable setStore(final String store) {
		this.store = store;
		return this;
	}

	/**
	 * <p>addFeature.</p>
	 *
	 * @param feature a {@link org.agatom.springatom.web.component.table.elements.extjs.feature.ExtJSTableFeature} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.extjs.ExtJSTable} object.
	 */
	public ExtJSTable addFeature(final ExtJSTableFeature feature) {
		if (this.features == null) {
			this.features = Sets.newHashSet();
		}
		this.features.add(feature);
		return this;
	}

	/**
	 * <p>Getter for the field <code>features</code>.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<ExtJSTableFeature> getFeatures() {
		return features;
	}

	/**
	 * <p>Setter for the field <code>features</code>.</p>
	 *
	 * @param features a {@link java.util.Set} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.extjs.ExtJSTable} object.
	 */
	public ExtJSTable setFeatures(final Set<ExtJSTableFeature> features) {
		this.features = features;
		return this;
	}

	public Set<String> getPlugins() {
		return plugins;
	}

	public ExtJSTable setPlugins(final Set<String> plugins) {
		this.plugins = plugins;
		return this;
	}

	public boolean isAnimCollapse() {
		return animCollapse;
	}

	public ExtJSTable setAnimCollapse(final boolean animCollapse) {
		this.animCollapse = animCollapse;
		return this;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public ExtJSTable setMinHeight(final int minHeight) {
		this.minHeight = minHeight;
		return this;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public ExtJSTable setMaxHeight(final int maxHeight) {
		this.maxHeight = maxHeight;
		return this;
	}

	public boolean isRowLines() {
		return rowLines;
	}

	public ExtJSTable setRowLines(final boolean rowLines) {
		this.rowLines = rowLines;
		return this;
	}

	public boolean isCollapsible() {
		return collapsible;
	}

	public ExtJSTable setCollapsible(final boolean collapsible) {
		this.collapsible = collapsible;
		return this;
	}

	public boolean isBorder() {
		return border;
	}

	public ExtJSTable setBorder(final boolean border) {
		this.border = border;
		return this;
	}

	public boolean isForceFit() {
		return forceFit;
	}

	public ExtJSTable setForceFit(final boolean forceFit) {
		this.forceFit = forceFit;
		return this;
	}

	public boolean isSortableColumns() {
		return sortableColumns;
	}

	public ExtJSTable setSortableColumns(final boolean sortableColumns) {
		this.sortableColumns = sortableColumns;
		return this;
	}

	public Set<ExtJSStoreField> getFields() {
		return fields;
	}

	@Override
	public boolean addContent(final ExtJSTableColumn column) {

		column.setSortable(this.sortableColumns);
		this.fields.add(new ExtJSStoreField().setName(column.getDataIndex()).setType(column.getFieldType()));

		return super.addContent(column);
	}

	public boolean addColumn(final ExtJSTableColumn column, final ExtJSStoreField field) {
		column.setSortable(this.sortableColumns);
		this.fields.add(field.setName(column.getDataIndex()));
		return super.addContent(column);
	}

}
