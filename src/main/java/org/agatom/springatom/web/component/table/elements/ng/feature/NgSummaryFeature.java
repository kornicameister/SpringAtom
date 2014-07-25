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
 * {@code NgSummaryFeature} corresponds to the <a href="http://docs.sencha.com/ng/4.2.2/#!/api/Ext.grid.feature.Summary">Ext.grid.feature.Summary</a>
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 03.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class NgSummaryFeature
		extends AbstractNgTableFeature {
	private static final long    serialVersionUID = 4363888471601789083L;
	protected            String  remoteRoot       = "";
	protected            boolean showSummaryRow   = true;

	/**
	 * <p>Constructor for NgSummaryFeature.</p>
	 */
	public NgSummaryFeature() {
		super(Feature.SUMMARY);
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
	 * @return a {@link NgSummaryFeature} object.
	 */
	public NgSummaryFeature setRemoteRoot(final String remoteRoot) {
		this.remoteRoot = remoteRoot;
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
	 * @return a {@link NgSummaryFeature} object.
	 */
	public NgSummaryFeature setShowSummaryRow(final boolean showSummaryRow) {
		this.showSummaryRow = showSummaryRow;
		return this;
	}

}
