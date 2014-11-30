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

package org.agatom.springatom.cmp.component.table.elements.ng.feature;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-07-25</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public enum NgFeatures
        implements NgFeaturePropertyBased {
    GROUPING {
        @Override
        public boolean isPropertyBased() {
            return true;
        }
    },
    PAGING {
        @Override
        public boolean isPropertyBased() {
            return true;
        }
    },
    SUMMARY {
        @Override
        public boolean isPropertyBased() {
            return true;
        }
    },
    EDITABLE {
        @Override
        public boolean isPropertyBased() {
            return false;
        }
    },
    PINNING {
        @Override
        public boolean isPropertyBased() {
            return false;
        }
    },
    ROW_REORDER {
        @Override
        public boolean isPropertyBased() {
            return false;
        }
    },
    FOOTER {
        @Override
        public boolean isPropertyBased() {
            return false;
        }
    },
    FILTER {
        @Override
        public boolean isPropertyBased() {
            return false;
        }
    },
    COLUMN_MENU {
        @Override
        public boolean isPropertyBased() {
            return false;
        }
    },
    SORTABLE {
        @Override
        public boolean isPropertyBased() {
            return false;
        }
    },
    ROW_NUMBERER {
        @Override
        public boolean isPropertyBased() {
            return false;
        }
    }
}
