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
    private PropertiesConstants() {
    }

    public static final String REPORTS_DOCUMENT_META_MARGIN             = "reports.document.meta.margin";
    public static final String REPORTS_DOCUMENT_META_DETAIL_HEIGHT      = "reports.document.meta.detailHeight";
    public static final String REPORTS_DOCUMENT_META_IGNORE_PAGINATION  = "reports.document.meta.ignorePagination";
    public static final String REPORTS_DOCUMENT_META_FULL_PAGE_WIDTH    = "reports.document.meta.useFullPageWidth";
    public static final String REPORTS_DOCUMENT_META_SHOW_COL_NAMES     = "reports.document.meta.printColumnNames";
    public static final String REPORTS_DOCUMENT_META_ALLOW_DETAIL_SPLIT = "reports.document.meta.allowDetailSplit";
    public static final String REPORTS_DOCUMENT_META_PRINT_ODD_ROWS     = "reports.document.meta.printOddRows";
    public static final String REPORTS_COLUMN_GROUP_START_IN_NEW_PAGE   = "reports.column.group.startInNewPage";
    public static final String REPORTS_COLUMN_GROUP_REPRINT_IN_NEW_PAGE = "reports.column.group.reprintInNewPage";
}
