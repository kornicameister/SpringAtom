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

package org.agatom.springatom.web.infopages.interceptor;

import org.agatom.springatom.web.infopages.InfoPageConstants;
import org.agatom.springatom.web.infopages.link.InfoPageLinkHelper;
import org.agatom.springatom.web.infopages.link.InfoPageRequest;
import org.agatom.springatom.webmvc.controllers.SVInfoPageController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * {@code SCDRViewInterceptor} is the shorthand for <i>SCommonDataResolverViewInterceptor</i>.
 * This class intercepts request for the views before processing them and injects commonly reused beans
 * into them.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageInterceptor
		extends HandlerInterceptorAdapter {
	private static final Logger             LOGGER             = Logger.getLogger(InfoPageInterceptor.class);
	@Autowired
	private              InfoPageLinkHelper infoPageLinkHelper = null;

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws
			Exception {
		if (modelAndView != null && modelAndView.getModelMap() != null) {
			final ModelMap modelMap = modelAndView.getModelMap();
			final InfoPageRequest infoPageRequest = this.infoPageLinkHelper.toInfoPageRequest(request.getRequestURI());

			modelMap.put(InfoPageConstants.INFOPAGE_AVAILABLE, infoPageRequest.isValid());
			if (infoPageRequest.isValid()) {
				modelMap.put(InfoPageConstants.INFOPAGE_PAGE, infoPageRequest);
				modelMap.put(InfoPageConstants.INFOPAGE_VIEW_DATA_TEMPLATE_LINK, linkTo(methodOn(SVInfoPageController.class).getInfoPageViewData(null, null)).withRel("dummy").getHref());
			}

			LOGGER.trace(String.format("IP=%s(%s)", modelAndView.getViewName(), modelMap));
		}
	}
}
