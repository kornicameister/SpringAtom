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

package org.agatom.springatom.webmvc.controllers.components;

import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.data.ComponentDataResponse;
import org.agatom.springatom.web.component.infopages.builder.InfoPageResponseWrapper;
import org.agatom.springatom.web.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.webmvc.converters.du.ConvertibleValueWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code CDRReturnValueConverter} is a customized {@link org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor}
 * that gets single {@link org.agatom.springatom.web.component.core.data.ComponentDataResponse} and process received values
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
@Role(BeanDefinition.ROLE_APPLICATION)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CDRReturnValueConverter {
	private static final Logger                      LOGGER            = Logger.getLogger(CDRReturnValueConverter.class);
	private static final String                      BUILT_BY          = "builtBy";
	private static final String                      SUCCESS           = "success";
	private static final String                      TIME              = "time";
	private static final String                      ERROR             = "error";
	private static final String                      SIZE              = "size";
	private static final String                      DATA              = "data";
	@Autowired
	@Qualifier("springAtomConversionService")
	private              FormattingConversionService conversionService = null;

	public HashMap<String, Object> convert(final ComponentDataResponse response, final ComponentDataRequest request) {
		LOGGER.trace(String.format("convert(returnValue=%s)", response));

		final HashMap<String, Object> value = Maps.newHashMap();
		final Object data = response.getData();

		value.put(BUILT_BY, response.getBuiltBy());
		value.put(SUCCESS, response.isSuccess());
		value.put(TIME, response.getTime());

		if (!response.isSuccess() && data == null) {
			value.put(ERROR, response.getError());
			value.put(SIZE, 0);
		} else {
			value.put(DATA, this.getConvertedValue(data, request));
			value.put(SIZE, this.getSizeOfData(data, request.getComponent()));
		}

		return value;
	}

	private Object getConvertedValue(final Object value, final ComponentDataRequest request) {
		if (ClassUtils.isAssignableValue(InfoPageResponseWrapper.class, value)) {
			final InfoPageResponseWrapper wrapper = (InfoPageResponseWrapper) value;
			final Map<String, Object> data = wrapper.getData();
			final Map<String, Object> retData = Maps.newHashMap();

			for (final String key : data.keySet()) {
				final ConvertibleValueWrapper webData = new ConvertibleValueWrapper();

				webData.setValue(data.get(key));
				webData.setKey(key);
				webData.setSource(wrapper.getSource());
				webData.setRequest(request);

				retData.put(key, this.conversionService.convert(webData, Object.class));
			}

			return retData;
		}
		return null;
	}

	private Object getSizeOfData(final Object data, final org.agatom.springatom.web.component.core.Component component) {
		if (ClassUtils.isAssignableValue(InfoPageComponent.class, component)) {
			return 1;
		}
		return ((Map<?, ?>) data).size();
	}
}
