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

import com.google.common.collect.Sets;
import org.agatom.springatom.web.component.core.request.ComponentRequestAttribute;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.util.Set;

/**
 * {@code TableComponentRequestArgumentResolver} resolves {@link org.agatom.springatom.web.component.table.request.TableComponentRequest}
 * param for {@link org.agatom.springatom.webmvc.controllers.components.SVComponentsDataController#onTableDataRequest(String, TableComponentRequest, org.springframework.web.context.request.WebRequest)}
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 15.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class TableComponentRequestArgumentResolver
		implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		final Class<?> parameterType = parameter.getParameterType();
		return ClassUtils.isAssignable(TableComponentRequest.class, parameterType);
	}

	@Override
	public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
		final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		final TableComponentRequest componentRequest = new TableComponentRequest();
		final BeanWrapper beanWrapper = new BeanWrapperImpl(componentRequest);
		for (PropertyDescriptor propertyDescriptor : beanWrapper.getPropertyDescriptors()) {
			final String propertyDescriptorName = propertyDescriptor.getName();
			final Object requestParameter = request.getParameter(propertyDescriptorName);
			if (requestParameter != null) {
				if (propertyDescriptorName.equals("attributes")) {
					final String[] attributes = ((String) requestParameter).split("&");
					final Set<ComponentRequestAttribute> set = Sets.newHashSetWithExpectedSize(attributes.length / 2);
					for (int it = 0, length = attributes.length; it < length; it++) {
						final String[] chunk1 = StringUtils.split(attributes[it++], "=");
						final String[] chunk2 = StringUtils.split(attributes[it], "=");
						set.add(new ComponentRequestAttribute().setPath(chunk1[1]).setType(chunk2[1]));
					}
					componentRequest.setAttributes(set);
				} else {
					beanWrapper.setPropertyValue(propertyDescriptorName, requestParameter);
				}
			}
		}
		return componentRequest;
	}

}
