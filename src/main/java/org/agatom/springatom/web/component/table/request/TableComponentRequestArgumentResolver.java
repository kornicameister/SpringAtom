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

package org.agatom.springatom.web.component.table.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.component.core.request.ComponentRequestAttribute;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * {@code TableComponentRequestArgumentResolver} resolves {@link org.agatom.springatom.web.component.table.request.TableComponentRequest}
 * param for {@link org.agatom.springatom.webmvc.controllers.components.SVComponentsDataController#onTableDataRequest(String, TableComponentRequest, org.springframework.web.context.request.WebRequest)}
 *
 * <div>
 * <h2>Changelog</h2>
 * <ol>
 * <li>0.0.2 - added code to resolve arguments sent from Angular based tables</li>
 * <li>0.0.2 - added logging for performance</li>
 * </ol>
 * </div>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 15.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class TableComponentRequestArgumentResolver
		implements HandlerMethodArgumentResolver {
	private static final Logger       LOGGER            = Logger.getLogger(TableComponentRequestArgumentResolver.class);
	private static final String       DEFAULT_GRID_MODE = "angular";
	@Autowired
	private              ObjectMapper objectMapper      = null;

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		final Class<?> parameterType = parameter.getParameterType();
		return ClassUtils.isAssignable(TableComponentRequest.class, parameterType);
	}

	@Override
	public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
		final TableComponentRequest componentRequest = new TableComponentRequest();
		final BeanWrapper beanWrapper = new BeanWrapperImpl(componentRequest);

		final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		final Map<String, String[]> parameterMap = request.getParameterMap();
		final String saGridMode = getGridMode(request);

		LOGGER.debug(String.format("Resolving for gridMode=%s", saGridMode));

		final long startTime = System.nanoTime();

		try {
			for (PropertyDescriptor propertyDescriptor : beanWrapper.getPropertyDescriptors()) {
				final String propertyDescriptorName = propertyDescriptor.getName();
				final Object requestParameter = parameterMap.get(propertyDescriptorName);
				if (requestParameter != null) {
					if (propertyDescriptorName.equals("attributes")) {
						final String[] attributes = (String[]) requestParameter;
						final Set<ComponentRequestAttribute> set = Sets.newHashSetWithExpectedSize(attributes.length);
						for (final String jsonAttribute : attributes) {
							set.add(this.objectMapper.readValue(jsonAttribute, ComponentRequestAttribute.class));
						}
						componentRequest.setAttributes(set);
					} else {
						beanWrapper.setPropertyValue(propertyDescriptorName, requestParameter);
					}
				}
			}
		} catch (Exception exp) {
			LOGGER.error(String.format("Failed to resolve %s, error occurred", ClassUtils.getShortName(TableComponentRequest.class)), exp);
			throw exp;
		}

		LOGGER.trace(String.format("Resolved table request [mode=%s] in %d ms", saGridMode, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)));

		return componentRequest;
	}

	private String getGridMode(final HttpServletRequest request) {
		final String header = request.getHeader("SA-GRID-MODE");
		return StringUtils.hasText(header) ? header : DEFAULT_GRID_MODE;
	}

}
