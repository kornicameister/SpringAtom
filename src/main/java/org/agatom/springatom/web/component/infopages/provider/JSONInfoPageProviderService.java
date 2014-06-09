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

package org.agatom.springatom.web.component.infopages.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agatom.springatom.core.exception.SException;
import org.agatom.springatom.core.exception.SInvalidArgumentException;
import org.agatom.springatom.web.component.infopages.provider.structure.InfoPage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Description("InfoPageProviderService implementation designed to load InfoPages from json files")
class JSONInfoPageProviderService
		implements InfoPageProviderService {
	private static final Logger       LOGGER       = Logger.getLogger(JSONInfoPageProviderService.class);
	private static final String       EXTENSION    = "json";
	private static final String       CLASSPATH    = "classpath";
	@Autowired
	@Qualifier("jackson2ObjectFactoryBean")
	private              ObjectMapper objectMapper = null;

	@PostConstruct
	private void postConstruct() {
		Assert.notNull(this.objectMapper, "ObjectMapper not initialized");
	}

	/** {@inheritDoc} */
	@Override
	public <T extends Persistable<?>> InfoPage getInfoPage(final Class<T> persistableClass) throws SException {
		LOGGER.debug(String.format("getInfoPage(persistableClass=%s)", persistableClass));
		if (persistableClass == null) {
			throw new SInvalidArgumentException("persistableClass", new NullPointerException());
		}
		final String filePath = this.getFilePath(persistableClass);
		final File file = this.getFile(filePath);

		try {
			return this.objectMapper.readValue(file, InfoPage.class);
		} catch (IOException e) {
			LOGGER.fatal(String.format("getInfoPage(persistableClass=%s) failed...", persistableClass), e);
			throw new SException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public <T extends Persistable<?>> String getFilePath(final Class<T> persistableClass) {
		final String name = ClassUtils.getShortName(persistableClass).toLowerCase();
		final String packageName = ClassUtils.getPackageName(persistableClass).replaceAll("\\.", "/");
		final String path = String.format("%s/%s.%s", packageName, name, EXTENSION);
		return StringUtils.cleanPath(path);
	}

	/** {@inheritDoc} */
	@Override
	public InfoPage getInfoPage(final Persistable<?> persistable) throws SException {
		LOGGER.debug(String.format("getInfoPage(persistable=%s)", persistable));
		if (persistable == null) {
			throw new SInvalidArgumentException("persistable", new NullPointerException());
		}
		return this.getInfoPage(persistable.getClass());
	}

	/**
	 * Returns a file, as classpath resource, for passed {@code filePath}
	 *
	 * @param filePath path to get a file from
	 *
	 * @return {@link java.io.File} with {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage} definition
	 *
	 * @throws SException If file not found or not readable
	 */
	private File getFile(final String filePath) throws SException {
		try {
			final File file = ResourceUtils.getFile(String.format("%s:%s", CLASSPATH, filePath));
			if (!file.canRead()) {
				throw new SException(String.format("%s can not be read", filePath));
			}
			return file;
		} catch (FileNotFoundException exp) {
			final String message = String.format("Could not locate the page definition under %s", filePath);
			LOGGER.fatal(message);
			throw new SException(message, exp);
		} catch (SException exp) {
			LOGGER.fatal(exp.getMessage());
			throw exp;
		}
	}

}
