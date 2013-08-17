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

package org.agatom.springatom.web.controller.meta.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SMetaDataUI
        implements Serializable {
    private final String              prefix;
    private       Map<String, String> settings;

    public SMetaDataUI(final String prefix) {
        this.prefix = prefix;
        this.settings = new TreeMap<>();
    }

    public String getPrefix() {
        return prefix;
    }

    public String put(final String key, final String value) {
        return this.settings.put(key, value);
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSettings(final Map<String, String> settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "SMetaDataUI{" +
                "prefix='" + prefix + '\'' +
                ", settings=" + settings +
                "} " + super.toString();
    }
}
