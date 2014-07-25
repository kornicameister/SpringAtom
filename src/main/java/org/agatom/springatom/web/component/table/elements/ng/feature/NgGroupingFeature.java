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

package org.agatom.springatom.web.component.table.elements.ng.feature;

/**
 * {@code NgGroupingFeature} corresponds to the <a href="http://docs.sencha.com/ng/4.2.2/#!/api/Ext.grid.feature.Grouping">Ext.grid.feature.Grouping</a>
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 03.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class NgGroupingFeature
		extends AbstractNgTableFeature {
	private static final long    serialVersionUID   = -616786001642472095L;
	protected            boolean collapsible        = true;
	protected            short   depthToIndent      = 17;
	protected            boolean enableGroupingMenu = true;
	protected            boolean enableNoGroups     = true;
	protected            boolean startCollapsed     = false;
	protected            boolean showSummaryRow     = false;
	protected            String  remoteRoot         = null;
	protected            String  showsGroupsText    = null;
	protected            String  groupByText        = null;

	/**
	 * <p>Constructor for NgGroupingFeature.</p>
	 */
	public NgGroupingFeature() {
		super(Feature.GROUPING);
	}

	/**
	 * <p>isCollapsible.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isCollapsible() {
		return collapsible;
	}

	/**
	 * <p>Setter for the field <code>collapsible</code>.</p>
	 *
	 * @param collapsible a boolean.
	 *
	 * @return a {@link NgGroupingFeature} object.
	 */
	public NgGroupingFeature setCollapsible(final boolean collapsible) {
		this.collapsible = collapsible;
		return this;
	}

	/**
	 * <p>Getter for the field <code>depthToIndent</code>.</p>
	 *
	 * @return a short.
	 */
	public short getDepthToIndent() {
		return depthToIndent;
	}

	/**
	 * <p>Setter for the field <code>depthToIndent</code>.</p>
	 *
	 * @param depthToIndent a short.
	 *
	 * @return a {@link NgGroupingFeature} object.
	 */
	public NgGroupingFeature setDepthToIndent(final short depthToIndent) {
		this.depthToIndent = depthToIndent;
		return this;
	}

	/**
	 * <p>isEnableGroupingMenu.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isEnableGroupingMenu() {
		return enableGroupingMenu;
	}

	/**
	 * <p>Setter for the field <code>enableGroupingMenu</code>.</p>
	 *
	 * @param enableGroupingMenu a boolean.
	 *
	 * @return a {@link NgGroupingFeature} object.
	 */
	public NgGroupingFeature setEnableGroupingMenu(final boolean enableGroupingMenu) {
		this.enableGroupingMenu = enableGroupingMenu;
		return this;
	}

	/**
	 * <p>isEnableNoGroups.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isEnableNoGroups() {
		return enableNoGroups;
	}

	/**
	 * <p>Setter for the field <code>enableNoGroups</code>.</p>
	 *
	 * @param enableNoGroups a boolean.
	 *
	 * @return a {@link NgGroupingFeature} object.
	 */
	public NgGroupingFeature setEnableNoGroups(final boolean enableNoGroups) {
		this.enableNoGroups = enableNoGroups;
		return this;
	}

	/**
	 * <p>isStartCollapsed.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isStartCollapsed() {
		return startCollapsed;
	}

	/**
	 * <p>Setter for the field <code>startCollapsed</code>.</p>
	 *
	 * @param startCollapsed a boolean.
	 *
	 * @return a {@link NgGroupingFeature} object.
	 */
	public NgGroupingFeature setStartCollapsed(final boolean startCollapsed) {
		this.startCollapsed = startCollapsed;
		return this;
	}

	/**
	 * <p>isShowSummaryRow.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isShowSummaryRow() {
		return showSummaryRow;
	}

	/**
	 * <p>Setter for the field <code>showSummaryRow</code>.</p>
	 *
	 * @param showSummaryRow a boolean.
	 *
	 * @return a {@link NgGroupingFeature} object.
	 */
	public NgGroupingFeature setShowSummaryRow(final boolean showSummaryRow) {
		this.showSummaryRow = showSummaryRow;
		return this;
	}

	/**
	 * <p>Getter for the field <code>remoteRoot</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getRemoteRoot() {
		return remoteRoot;
	}

	/**
	 * <p>Setter for the field <code>remoteRoot</code>.</p>
	 *
	 * @param remoteRoot a {@link java.lang.String} object.
	 *
	 * @return a {@link NgGroupingFeature} object.
	 */
	public NgGroupingFeature setRemoteRoot(final String remoteRoot) {
		this.remoteRoot = remoteRoot;
		return this;
	}

	/**
	 * <p>Getter for the field <code>showsGroupsText</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getShowsGroupsText() {
		return showsGroupsText;
	}

	/**
	 * <p>Setter for the field <code>showsGroupsText</code>.</p>
	 *
	 * @param showsGroupsText a {@link java.lang.String} object.
	 *
	 * @return a {@link NgGroupingFeature} object.
	 */
	public NgGroupingFeature setShowsGroupsText(final String showsGroupsText) {
		this.showsGroupsText = showsGroupsText;
		return this;
	}

	/**
	 * <p>Getter for the field <code>groupByText</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getGroupByText() {
		return groupByText;
	}

	/**
	 * <p>Setter for the field <code>groupByText</code>.</p>
	 *
	 * @param groupByText a {@link java.lang.String} object.
	 *
	 * @return a {@link NgGroupingFeature} object.
	 */
	public NgGroupingFeature setGroupByText(final String groupByText) {
		this.groupByText = groupByText;
		return this;
	}

}
