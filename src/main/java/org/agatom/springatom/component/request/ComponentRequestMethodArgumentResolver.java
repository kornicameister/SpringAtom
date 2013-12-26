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

package org.agatom.springatom.component.request;

import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.agatom.springatom.component.ComponentConstants;
import org.agatom.springatom.component.request.beans.ComponentRequest;
import org.agatom.springatom.component.request.beans.ComponentTableRequest;
import org.joor.Reflect;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ComponentRequestMethodArgumentResolver
        implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        final Class<?> parameterType = parameter.getParameterType();
        return parameterType.isAssignableFrom(ComponentRequest.class)
                || parameterType.isAssignableFrom(ComponentTableRequest.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) throws
            Exception {
        final Class<?> parameterType = parameter.getParameterType();
        if (parameterType.isAssignableFrom(ComponentRequest.class)) {
            return this.populate(mavContainer, (HttpServletRequest) webRequest.getNativeRequest(), binderFactory);
        } else if (parameterType.isAssignableFrom(ComponentTableRequest.class)) {
            return this.populateTable(mavContainer, (HttpServletRequest) webRequest.getNativeRequest(), binderFactory);
        }
        return null;
    }

    private ComponentRequest populate(final ModelAndViewContainer mavContainer,
                                      final HttpServletRequest webRequest,
                                      final WebDataBinderFactory binderFactory) {
        final ComponentRequest request = new ComponentRequest();
        request.setBuilderId((String) webRequest.getAttribute(ComponentConstants.BUILDER_ID));
        request.setContextClass((Class<?>) Reflect.on(webRequest.getParameter(ComponentConstants.CONTEXT_CLASS)).get());
        request.setContextKey((String) webRequest.getAttribute(ComponentConstants.CONTEXT_KEY));
        return request;
    }

    private ComponentTableRequest populateTable(final ModelAndViewContainer mavContainer,
                                                final HttpServletRequest webRequest,
                                                final WebDataBinderFactory binderFactory) {
        final ComponentTableRequest request = new ComponentTableRequest();
        request.setCriterias(DatatablesCriterias.getFromRequest(webRequest));
        request.setBuilderId(webRequest.getParameter(ComponentConstants.BUILDER_ID));
        request.setContextClass((Class<?>) Reflect.on(webRequest.getParameter(ComponentConstants.CONTEXT_CLASS)).get());
        request.setContextKey(webRequest.getParameter(ComponentConstants.CONTEXT_KEY));
        return request;
    }
}
