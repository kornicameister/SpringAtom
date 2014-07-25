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

package org.agatom.springatom.web.component.table.elements.ng;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.component.table.elements.TableComponent;
import org.agatom.springatom.web.component.table.elements.ng.feature.NgTableFeature;

import java.util.Set;

/**
 * {@code NgTable} is a {@link org.agatom.springatom.web.component.table.elements.TableComponent}
 * which holds information about {@link org.agatom.springatom.web.component.table.elements.ng.NgTableColumn}
 * <small>Class is a part of <b>SpringAtom</b> and was created at 03.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.3
 * @since 0.0.1
 */
public class NgTable
		extends TableComponent<NgTableColumn> {
	private static final long                serialVersionUID = 614019837253089296L;
	private              Set<NgTableFeature> features         = null;
	private              Set<String>         plugins          = null;
	private              String              store            = null;
	private              int                 minHeight        = -1;
	private              int                 maxHeight        = -1;
	private              boolean             collapsible      = false;
	private              boolean             sortableColumns  = false;
	private              boolean             border           = true;

	/**
	 * <p>Constructor for NgTable.</p>
	 */
	public NgTable() {
		this.initDefaults();
	}

	private void initDefaults() {
		super.addContent(new NgTableColumn().setXtype("rownumberer"));
	}

	public NgTable(final String tableId, final String builderId) {
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

	public NgTable setStore(final String store) {
		this.store = store;
		return this;
	}

	/**
	 * <p>addFeature.</p>
	 *
	 * @param feature a {@link org.agatom.springatom.web.component.table.elements.ng.feature.NgTableFeature} object.
	 *
	 * @return a {@link NgTable} object.
	 */
	public NgTable addFeature(final NgTableFeature feature) {
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
	public Set<NgTableFeature> getFeatures() {
		return features;
	}

	/**
	 * <p>Setter for the field <code>features</code>.</p>
	 *
	 * @param features a {@link java.util.Set} object.
	 *
	 * @return a {@link NgTable} object.
	 */
	public NgTable setFeatures(final Set<NgTableFeature> features) {
		this.features = features;
		return this;
	}

	public Set<String> getPlugins() {
		return plugins;
	}

	public NgTable setPlugins(final Set<String> plugins) {
		this.plugins = plugins;
		return this;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public NgTable setMinHeight(final int minHeight) {
		this.minHeight = minHeight;
		return this;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public NgTable setMaxHeight(final int maxHeight) {
		this.maxHeight = maxHeight;
		return this;
	}

	public boolean isCollapsible() {
		return collapsible;
	}

	public NgTable setCollapsible(final boolean collapsible) {
		this.collapsible = collapsible;
		return this;
	}

	public boolean isBorder() {
		return border;
	}

	public NgTable setBorder(final boolean border) {
		this.border = border;
		return this;
	}

	public boolean isSortableColumns() {
		return sortableColumns;
	}

	public NgTable setSortableColumns(final boolean sortableColumns) {
		this.sortableColumns = sortableColumns;
		return this;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("tableId", tableId)
				.add("builderId", builderId)
				.add("label", label)
				.toString();
	}
}
