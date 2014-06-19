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

package org.agatom.springatom.webmvc.converters.du.converters;

import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.server.service.domain.SUserService;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.locale.beans.LocalizedClassAttribute;
import org.agatom.springatom.webmvc.converters.du.WebDataConverter;
import org.agatom.springatom.webmvc.converters.du.component.TextGuiComponent;
import org.agatom.springatom.webmvc.converters.du.exception.WebConverterException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * {@code AbstractWebConverter} is an abstract implementation of {@link org.agatom.springatom.webmvc.converters.du.WebDataConverter}.
 * Defines the algorithm or retrieving converted value
 * in {@link #convert(String, Object, org.springframework.data.domain.Persistable, org.agatom.springatom.web.component.core.data.ComponentDataRequest)}
 * and defines some reusable methods.
 * <small>Class is a part of <b>SpringAtom</b> and was created at 31.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
abstract public class AbstractWebConverter
		implements WebDataConverter<Serializable> {
	private static final Logger            LOGGER            = Logger.getLogger(AbstractWebConverter.class);
	@Autowired
	protected            SMessageSource    messageSource     = null;
	@Autowired
	protected            EntityDescriptors entityDescriptors = null;
	@Autowired
	protected            SUserService      userService       = null;

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public final Serializable convert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest request) throws WebConverterException {
		LOGGER.debug(String.format("getValue(key=%s,value=%s,persistable=%s,request=%s)", key, value, persistable, request));
		try {
			final long startTime = System.nanoTime();
			Serializable convertedValue = this.doConvert(key, value, persistable, request);
			if (convertedValue == null) {
				final TextGuiComponent component = new TextGuiComponent();
				component.setName(key);
				component.setRawValue(value);
				component.setValue("???");
				convertedValue = component;
			}
			final long conversionTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
			LOGGER.trace(String.format("Converted value for key=%s is %s, conversion completed in %dms", key, convertedValue, conversionTime));
			return convertedValue;
		} catch (Exception exp) {
			final String message = String.format("getValue(key=%s,value=%s,persistable=%s,request=%s) failed...", key, value, persistable, request);
			LOGGER.fatal(message, exp);
			throw new WebConverterException(message, exp);
		}
	}

	/**
	 * <p>doConvert.</p>
	 *
	 * @param key         a {@link java.lang.String} object.
	 * @param value       a {@link java.lang.Object} object.
	 * @param persistable a {@link org.springframework.data.domain.Persistable} object.
	 * @param webRequest  a {@link org.agatom.springatom.web.component.core.data.ComponentDataRequest} object.
	 *
	 * @return a {@link java.io.Serializable} object.
	 *
	 * @throws java.lang.Exception if any.
	 */
	protected abstract Serializable doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) throws Exception;

	/**
	 * <p>getLabel.</p>
	 *
	 * @param key         a {@link java.lang.String} object.
	 * @param persistable a {@link org.springframework.data.domain.Persistable} object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	protected String getLabel(final String key, final Persistable<?> persistable) {
		final LocalizedClassAttribute localizedClassAttribute = this.messageSource.getLocalizedClassAttribute(persistable.getClass(), key, LocaleContextHolder.getLocale());
		if (localizedClassAttribute != null && localizedClassAttribute.isFound()) {
			return localizedClassAttribute.getLabel();
		}
		return this.getLabel(key);
	}

	/**
	 * <p>getLabel.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	protected String getLabel(final String key) {
		return this.messageSource.getMessage(key, LocaleContextHolder.getLocale());
	}

}
