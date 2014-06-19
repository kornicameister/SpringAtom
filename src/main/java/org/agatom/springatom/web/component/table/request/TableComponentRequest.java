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

package org.agatom.springatom.web.component.table.request;

import com.google.common.collect.Lists;
import org.agatom.springatom.web.component.core.request.AbstractComponentRequest;

import java.util.List;

/**
 * <p>TableComponentRequest class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class TableComponentRequest
		extends AbstractComponentRequest {
	private static final long                        serialVersionUID  = 8662629050407736443L;
	private              String                      builderId         = null;
	private              int                         page              = 0;
	private              int                         start             = 0;
	private              int                         limit             = 0;
	private              List<TableRequestColumnDef> sortingColumnDefs = Lists.newArrayList();

	public String getBuilderId() {
		return builderId;
	}

	public TableComponentRequest setBuilderId(final String builderId) {
		this.builderId = builderId;
		return this;
	}

	public int getPage() {
		return page;
	}

	public TableComponentRequest setPage(final int page) {
		this.page = page;
		return this;
	}

	public int getStart() {
		return start;
	}

	public TableComponentRequest setStart(final int start) {
		this.start = start;
		return this;
	}

	public int getLimit() {
		return limit;
	}

	public TableComponentRequest setLimit(final int limit) {
		this.limit = limit;
		return this;
	}

	public List<TableRequestColumnDef> getSortingColumnDefs() {
		return sortingColumnDefs;
	}
}
