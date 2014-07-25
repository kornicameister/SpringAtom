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
import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.table.elements.TableComponent;
import org.agatom.springatom.web.component.table.elements.ng.feature.NgFeatures;
import org.agatom.springatom.web.component.table.elements.ng.feature.NgTableFeature;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Map;
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
	private static final long                    serialVersionUID = 614019837253089296L;
	private              Map<NgFeatures, Object> features         = null;
	private              Set<String>             plugins          = null;
	private              String                  store            = null;
	private              int                     minHeight        = -1;
	private              int                     maxHeight        = -1;
	private              boolean                 collapsible      = false;
	private              boolean                 border           = true;

	public NgTable(final String tableId, final String builderId) {
		super(tableId, builderId);
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
	 * @return this {@link org.agatom.springatom.web.component.table.elements.ng.NgTable} object
	 */
	public final NgTable addFeature(final NgTableFeature feature) {
		if (this.features == null) {
			this.features = Maps.newHashMap();
		}
		this.features.put(feature.getId(), feature);
		return this;
	}

	/**
	 * <p>Getter for the field <code>features</code>.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Map<NgFeatures, Object> getFeatures() {
		return features;
	}

	/**
	 * <p>Setter for the field <code>features</code>.</p>
	 *
	 * @param features a {@link java.util.Set} object.
	 *
	 * @return a {@link NgTable} object.
	 */
	public NgTable setFeatures(final Map<NgFeatures, Object> features) {
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

	public boolean isSortable() {
		return this.features.containsKey(NgFeatures.SORTABLE);
	}

	public NgTable setSortable(final boolean sortable) {
		if (sortable && !this.features.containsKey(NgFeatures.SORTABLE)) {
			this.addFeature(NgFeatures.SORTABLE);
		} else if (!sortable) {
			this.removeFeature(NgFeatures.SORTABLE);
		}
		return this;
	}

	/**
	 * <p>addFeature.</p>
	 * Adds feature that is not {@link org.agatom.springatom.web.component.table.elements.ng.feature.NgFeaturePropertyBased}
	 * This method can throw an exception if it is called with a property based feature
	 *
	 * @param feature a feature
	 *
	 * @return this {@link org.agatom.springatom.web.component.table.elements.ng.NgTable} object
	 *
	 * @see org.agatom.springatom.web.component.table.elements.ng.feature.NgFeaturePropertyBased#isPropertyBased()
	 */
	public NgTable addFeature(final NgFeatures feature) {
		Assert.isTrue(!feature.isPropertyBased(), "Cannot add NgFeatures that is propertyBased, use #addFeature(NgTableFeature)");
		if (this.features == null) {
			this.features = Maps.newHashMap();
		}
		this.features.put(feature, true);
		return this;
	}

	/**
	 * <p>removeFeature.</p>
	 * Removes the feature
	 *
	 * @param features a feature
	 *
	 * @return this {@link org.agatom.springatom.web.component.table.elements.ng.NgTable} object
	 */
	public NgTable removeFeature(final NgFeatures features) {
		if (CollectionUtils.isEmpty(this.features)) {
			return this;
		}
		this.features.remove(features);
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
