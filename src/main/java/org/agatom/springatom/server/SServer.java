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

package org.agatom.springatom.server;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Locale;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public interface SServer {
    /**
     * Retrieves the property value. At first it tries to load applications properties, that are given
     * locations in xml configuration file.
     * If the property is not found, than it tries to resolve from system properties {@link System#getProperty(String)}
     *
     * @param key
     *         property key
     *
     * @return property
     */
    String getProperty(final String key);

    <T> T getProperty(final String key, final Class<T> as);

    Locale getServerLocale();

    void setServerLocale(Locale locale);

    String getDelimiter();

    String getValueDelimiter();
}
