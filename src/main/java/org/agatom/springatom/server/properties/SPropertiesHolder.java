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

package org.agatom.springatom.server.properties;

import org.apache.log4j.Logger;
import org.joor.Reflect;
import org.joor.ReflectException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class SPropertiesHolder
        extends PropertyPlaceholderConfigurer {
    private static final Logger LOGGER = Logger.getLogger(SPropertiesHolder.class);
    private Map<String, String> propertiesMap;

    @Override
    protected void processProperties(final ConfigurableListableBeanFactory beanFactory,
                                     final Properties props) throws BeansException {
        super.processProperties(beanFactory, props);
        this.propertiesMap = new HashMap<>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            propertiesMap.put(keyStr, props.getProperty(keyStr));
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Cached %d keys", props.keySet().size()));
        }
    }

    public String getProperty(final String key) {
        String property = this.getProperty(key, String.class);
        if (property == null) {
            property = this.resolveSystemProperty(key);
        }
        return property;
    }

    public <T> T getProperty(final String key, final Class<T> as) {
        T value = null;
        try {
            value = Reflect.on(as).create(this.propertiesMap.get(key)).get();
        } catch (ReflectException cce) {
            LOGGER.warn(String.format("Failed to retrieve property for key=%s as clazz=%s", key, as), cce);
        }
        return value;
    }


}
