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

package org.agatom.springatom.web.rbuilder.support;

/**
 * {@code PropertiesConstants} defines keys for properties {@code rbuilder.properties}.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public final class PropertiesConstants {
	/** Constant <code>REPORTS_DOCUMENT_META_MARGIN="reports.document.meta.margin"</code> */
	public static final String REPORTS_DOCUMENT_META_MARGIN             = "reports.document.meta.margin";
	/** Constant <code>REPORTS_DOCUMENT_META_DETAIL_HEIGHT="reports.document.meta.detailHeight"</code> */
	public static final String REPORTS_DOCUMENT_META_DETAIL_HEIGHT      = "reports.document.meta.detailHeight";
	/** Constant <code>REPORTS_DOCUMENT_META_IGNORE_PAGINATION="reports.document.meta.ignorePagination"</code> */
	public static final String REPORTS_DOCUMENT_META_IGNORE_PAGINATION  = "reports.document.meta.ignorePagination";
	/** Constant <code>REPORTS_DOCUMENT_META_FULL_PAGE_WIDTH="reports.document.meta.useFullPageWidth"</code> */
	public static final String REPORTS_DOCUMENT_META_FULL_PAGE_WIDTH    = "reports.document.meta.useFullPageWidth";
	/** Constant <code>REPORTS_DOCUMENT_META_SHOW_COL_NAMES="reports.document.meta.printColumnNames"</code> */
	public static final String REPORTS_DOCUMENT_META_SHOW_COL_NAMES     = "reports.document.meta.printColumnNames";
	/** Constant <code>REPORTS_DOCUMENT_META_ALLOW_DETAIL_SPLIT="reports.document.meta.allowDetailSplit"</code> */
	public static final String REPORTS_DOCUMENT_META_ALLOW_DETAIL_SPLIT = "reports.document.meta.allowDetailSplit";
	/** Constant <code>REPORTS_DOCUMENT_META_PRINT_ODD_ROWS="reports.document.meta.printOddRows"</code> */
	public static final String REPORTS_DOCUMENT_META_PRINT_ODD_ROWS     = "reports.document.meta.printOddRows";
	/** Constant <code>REPORTS_COLUMN_GROUP_START_IN_NEW_PAGE="reports.column.group.startInNewPage"</code> */
	public static final String REPORTS_COLUMN_GROUP_START_IN_NEW_PAGE   = "reports.column.group.startInNewPage";
	/** Constant <code>REPORTS_COLUMN_GROUP_REPRINT_IN_NEW_PAGE="reports.column.group.reprintInNewPage"</code> */
	public static final String REPORTS_COLUMN_GROUP_REPRINT_IN_NEW_PAGE = "reports.column.group.reprintInNewPage";

	private PropertiesConstants() {
	}
}
