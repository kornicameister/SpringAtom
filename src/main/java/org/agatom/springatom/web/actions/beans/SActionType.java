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

package org.agatom.springatom.web.actions.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@XmlEnum
public enum SActionType
        implements Serializable {
    NAVIGATION;

    public static class SActionTypeAdapter
            extends XmlAdapter<String, SActionType> {

        @Override
        public SActionType unmarshal(final String value) throws Exception {
            final SActionType sActionType = SActionType.valueOf(value.toUpperCase());
            if (sActionType == null) {
                throw new SActionTypeAdapterException(String.format("No SActionType matches %s", value));
            }
            return sActionType;
        }

        @Override
        public String marshal(final SActionType value) throws Exception {
            return value.name().toLowerCase();
        }

        private class SActionTypeAdapterException
                extends Exception {
            public SActionTypeAdapterException(final String msg) {
                super(msg);
            }
        }
    }
}
