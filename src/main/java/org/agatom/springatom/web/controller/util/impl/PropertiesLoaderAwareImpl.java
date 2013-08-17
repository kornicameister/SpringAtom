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

package org.agatom.springatom.web.controller.util.impl;

import org.agatom.springatom.web.controller.util.PropertiesLoader;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PropertiesLoaderAwareImpl
        extends ResourceLoaderAwareImpl
        implements PropertiesLoader {

    @Override public Properties getProperties(final String path) throws FileNotFoundException {
        final Resource propertyResource = this.getResource(path);
        if (!propertyResource.exists()) {
            throw new FileNotFoundException(String.format("Properties at path=%s not found", path));
        }
        final Properties properties = new Properties();
        try {
            properties.load(propertyResource.getInputStream());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
