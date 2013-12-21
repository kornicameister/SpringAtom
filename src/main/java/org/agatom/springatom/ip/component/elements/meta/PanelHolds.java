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

package org.agatom.springatom.ip.component.elements.meta;

import org.agatom.springatom.core.util.Localized;

/**
 * {@code PanelHolds} provides information about what type of attributes
 * is held in the particular {@link org.agatom.springatom.ip.component.elements.InfoPagePanelComponent}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public enum PanelHolds
        implements Localized {
    BASIC_ATTRIBUTES {
        @Override
        public String getMessageKey() {
            return Constants.PREFIX + ".basic";
        }
    },
    ONE_TO_MANY_ATTRIBUTES {
        @Override
        public String getMessageKey() {
            return Constants.PREFIX + ".oneToMany";
        }
    },
    MANY_TO_ONE_ATTRIBUTES {
        @Override
        public String getMessageKey() {
            return Constants.PREFIX + ".manyToOne";
        }
    },
    SYSTEM_ATTRIBUTES {
        @Override
        public String getMessageKey() {
            return Constants.PREFIX + ".system";
        }
    };

    private static class Constants {
        private static final String PREFIX = "springatom.attributes";
    }
}
