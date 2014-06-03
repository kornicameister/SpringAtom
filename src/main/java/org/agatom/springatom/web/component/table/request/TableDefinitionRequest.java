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

package org.agatom.springatom.web.component.table.request;

import com.google.common.base.Objects;
import org.agatom.springatom.web.component.core.request.ComponentRequest;

/**
 * {@link org.agatom.springatom.web.component.table.request.TableDefinitionRequest} carries information
 * about {@link #builderId} and {@link #gridDataType}. These information are required to call {@link org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder}
 * to return valid table definition
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 02.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class TableDefinitionRequest
		implements ComponentRequest {
	private static final long     serialVersionUID = -3111030945284786959L;
	private              String   builderId        = null;
	private              Class<?> gridDataType     = null;

	/**
	 * <p>Getter for the field <code>builderId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getBuilderId() {
		return builderId;
	}

	/**
	 * <p>Setter for the field <code>builderId</code>.</p>
	 *
	 * @param builderId a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.request.TableDefinitionRequest} object.
	 */
	public TableDefinitionRequest setBuilderId(final String builderId) {
		this.builderId = builderId;
		return this;
	}

	/**
	 * <p>Getter for the field <code>gridDataType</code>.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getGridDataType() {
		return gridDataType;
	}

	/**
	 * <p>Setter for the field <code>gridDataType</code>.</p>
	 *
	 * @param gridDataType a {@link java.lang.Class} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.request.TableDefinitionRequest} object.
	 */
	public TableDefinitionRequest setGridDataType(final Class<?> gridDataType) {
		this.gridDataType = gridDataType;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("builderId", builderId)
				.add("gridDataType", gridDataType)
				.toString();
	}
}
