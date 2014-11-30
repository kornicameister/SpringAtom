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

package org.agatom.springatom.cmp.component.infopages.provider;

import org.agatom.springatom.cmp.component.core.builders.exception.ComponentException;
import org.agatom.springatom.cmp.component.infopages.provider.structure.InfoPage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Description("InfoPageProviderService implementation designed to load InfoPages from json files")
class JSONInfoPageProviderService
        extends AbstractInfoPageProvider
        implements InfoPageProviderService {
    private static final Logger LOGGER    = Logger.getLogger(JSONInfoPageProviderService.class);
    private static final String EXTENSION = "json";
    private static final String CLASSPATH = "classpath";

    /** {@inheritDoc} */
    @Override
    public <T extends Persistable<?>> InfoPage getInfoPage(final Class<T> persistableClass) throws Exception {
        LOGGER.debug(String.format("getInfoPage(persistableClass=%s)", persistableClass));
        if (persistableClass == null) {
            throw new IllegalArgumentException("persistableClass", new NullPointerException());
        }
        final String filePath = this.getFilePath(persistableClass);
        final InputStream stream = this.getStream(filePath);

        try {
            return this.objectMapper.readValue(stream, InfoPage.class);
        } catch (IOException e) {
            LOGGER.fatal(String.format("getInfoPage(persistableClass=%s) failed...", persistableClass), e);
            throw new ComponentException(e);
        }
    }

    private <T extends Persistable<?>> String getFilePath(final Class<T> persistableClass) {
        final String name = ClassUtils.getShortName(persistableClass).toLowerCase();
        final String packageName = ClassUtils.getPackageName(persistableClass).replaceAll("\\.", "/");
        final String path = String.format("%s/%s.%s", packageName, name, EXTENSION);
        return StringUtils.cleanPath(path);
    }

    /**
     * Returns a file, as classpath resource, for passed {@code filePath}
     *
     * @param filePath path to get a file from
     *
     * @return {@link java.io.File} with {@link org.agatom.springatom.cmp.component.infopages.provider.structure.InfoPage} definition
     *
     * @throws Exception If file not found or not readable
     */
    private InputStream getStream(final String filePath) throws Exception {
        try {
            final String path = String.format("%s:%s", CLASSPATH, filePath);
            final Resource file = new FileSystemResourceLoader().getResource(path);
            final InputStream stream = file.getInputStream();
            if (stream.available() < 0) {
                throw new Exception(String.format("Stream not ready for path=%s", path));
            }
            return stream;
        } catch (Exception exp) {
            LOGGER.fatal(exp.getMessage());
            throw exp;
        }
    }

}
