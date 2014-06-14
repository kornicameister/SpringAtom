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

package org.agatom.springatom.web.component.core.helper;

import org.agatom.springatom.web.component.table.elements.TableComponent;
import org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableColumnComponent;
import org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableComponent;
import org.springframework.hateoas.Link;

/**
 * <p>TableComponentHelper interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface TableComponentHelper
		extends ComponentHelper {
	/**
	 * <p>getInfoPageLink.</p>
	 *
	 * @param path a {@link java.lang.String} object.
	 * @param id   a {@link java.lang.Long} object.
	 *
	 * @return a {@link org.springframework.hateoas.Link} object.
	 */
	Link getInfoPageLink(String path, Long id);

	/**
	 * <p>newDandelionTable.</p>
	 *
	 * @param tableId   a {@link java.lang.String} object.
	 * @param builderId a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableComponent} object.
	 */
	DandelionTableComponent newDandelionTable(final String tableId, final String builderId);

	/**
	 * <p>getTableLink.</p>
	 *
	 * @param tableId   a {@link java.lang.String} object.
	 * @param builderId a {@link java.lang.String} object.
	 *
	 * @return a {@link org.springframework.hateoas.Link} object.
	 */
	Link getTableLink(final String tableId, final String builderId);

	/**
	 * <p>newTableColumn.</p>
	 *
	 * @param cmp   a {@link org.agatom.springatom.web.component.table.elements.TableComponent} object.
	 * @param path  a {@link java.lang.String} object.
	 * @param rbKey a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableColumnComponent} object.
	 */
	DandelionTableColumnComponent newTableColumn(final TableComponent cmp, final String path, final String rbKey);
}
