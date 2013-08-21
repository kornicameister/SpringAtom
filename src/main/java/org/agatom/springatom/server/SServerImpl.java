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

import org.agatom.springatom.server.properties.SPropertiesHolder;
import org.apache.log4j.Logger;
import org.joor.Reflect;
import org.joor.ReflectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Component(value = "springAtomServer")
public class SServerImpl
        implements SServer {
    private static final Logger LOGGER                 = Logger.getLogger(SServerImpl.class);
    private static final String SERVER_DELIMITER       = ",";
    private static final String SA_DELIMITER           = "sa.delimiter";
    private static final String SA_VALUE_DELIMITER     = "sa.value.delimiter";
    private static final String SERVER_VALUE_DELIMITER = "=";
    @Autowired
    private SPropertiesHolder propertiesHolder;

    @Override
    public String getProperty(final String key) {
        return this.propertiesHolder.getProperty(key);
    }

    @Override
    public <T> T getProperty(final String key, final Class<T> as) {
        T value = null;
        try {
            value = Reflect.on(as).create(this.propertiesHolder.getProperty(key)).get();
        } catch (ReflectException cce) {
            LOGGER.warn(String.format("Failed to retrieve property for key=%s as clazz=%s", key, as), cce);
        }
        return value;
    }

    @Override
    public Locale getServerLocale() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public void setServerLocale(final Locale locale) {
        LocaleContextHolder.setLocale(locale, true);
    }

    @Override
    public String getDelimiter() {
        final String property = this.getProperty(SA_DELIMITER);
        if (property == null) {
            return SERVER_DELIMITER;
        }
        return property;
    }

    @Override
    public String getValueDelimiter() {
        final String property = this.getProperty(SA_VALUE_DELIMITER);
        if (property == null) {
            return SERVER_VALUE_DELIMITER;
        }
        return property;
    }
}
