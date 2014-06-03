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

package org.agatom.springatom.webmvc.interceptors;

import org.agatom.springatom.web.beans.SWebBeanHelper;
import org.agatom.springatom.web.beans.search.SSearchCommandBean;
import org.agatom.springatom.web.breadcrumbs.SBreadcrumbResolver;
import org.agatom.springatom.web.view.SViewTitleResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code SCDRViewInterceptor} is the shorthand for <i>SCommonDataResolverViewInterceptor</i>.
 * This class intercepts request for the views before processing them and injects commonly reused beans
 * into them.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SCDRViewInterceptor
		extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = Logger.getLogger(SCDRViewInterceptor.class);
	@Value(value = "#{webProperties['sa.locale.requestParam']}")
	private String              localeParamKey;
	private SViewTitleResolver  titleResolver;
	private SBreadcrumbResolver breadcrumbResolver;

	/** {@inheritDoc} */
	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws
			Exception {
		if (modelAndView != null && modelAndView.getModelMap() != null) {
			final ModelMap modelMap = modelAndView.getModelMap();

			modelMap.put(this.localeParamKey, LocaleContextHolder.getLocale());
			SWebBeanHelper.addToModelMap(modelMap, new SSearchCommandBean());
			SWebBeanHelper.addToModelMap(modelMap, this.titleResolver.getViewTitle(modelAndView.getViewName()));
			SWebBeanHelper.addToModelMap(modelMap, this.breadcrumbResolver.getBreadcrumbPath(modelAndView.getView()));

			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace(String.format("SCDR=%s(%s)", modelAndView.getViewName(), modelMap));
			}
		}
	}

	/**
	 * <p>Setter for the field <code>titleResolver</code>.</p>
	 *
	 * @param titleResolver a {@link org.agatom.springatom.web.view.SViewTitleResolver} object.
	 */
	@Required
	public void setTitleResolver(final SViewTitleResolver titleResolver) {
		this.titleResolver = titleResolver;
	}

	/**
	 * <p>Setter for the field <code>breadcrumbResolver</code>.</p>
	 *
	 * @param breadcrumbResolver a {@link org.agatom.springatom.web.breadcrumbs.SBreadcrumbResolver} object.
	 */
	@Required
	public void setBreadcrumbResolver(SBreadcrumbResolver breadcrumbResolver) {
		this.breadcrumbResolver = breadcrumbResolver;
	}
}
