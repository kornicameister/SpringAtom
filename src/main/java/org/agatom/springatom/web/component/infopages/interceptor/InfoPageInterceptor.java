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

package org.agatom.springatom.web.component.infopages.interceptor;

import org.agatom.springatom.core.exception.SException;
import org.agatom.springatom.web.component.infopages.InfoPageConstants;
import org.agatom.springatom.web.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.web.component.infopages.link.InfoPageLinkHelper;
import org.agatom.springatom.web.component.infopages.link.InfoPageRequest;
import org.agatom.springatom.web.component.infopages.mapping.InfoPageMappingService;
import org.agatom.springatom.web.component.infopages.provider.InfoPageProviderService;
import org.agatom.springatom.web.component.infopages.provider.builder.InfoPageComponentBuilderService;
import org.agatom.springatom.web.component.infopages.provider.structure.InfoPage;
import org.agatom.springatom.webmvc.controllers.components.SVComponentsDataController;
import org.agatom.springatom.webmvc.exceptions.ControllerTierException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * {@code InfoPageInterceptor} is specialized interceptor ({@link org.springframework.web.servlet.HandlerInterceptor}) designed
 * to put an information required in {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage} page rendering process.
 * Data being set is as follow:
 * <ol>
 * <li>{@link org.agatom.springatom.web.component.infopages.link.InfoPageRequest}</li>
 * <li>{@link org.springframework.hateoas.Link} to {@link org.agatom.springatom.web.component.infopages.InfoPageConstants#INFOPAGE_DS}</li>
 * </ol>
 *
 * @author kornicameister
 * @version 0.0.4
 * @since 0.0.1
 */
public class InfoPageInterceptor
		extends HandlerInterceptorAdapter {
	private static final Logger                          LOGGER             = Logger.getLogger(InfoPageInterceptor.class);
	@Autowired
	private              InfoPageLinkHelper              infoPageLinkHelper = null;
	@Autowired
	private              InfoPageProviderService         providerService    = null;
	@Autowired
	private              InfoPageMappingService          mappingService     = null;
	@Autowired
	private              InfoPageComponentBuilderService builderService     = null;

	/** {@inheritDoc} */
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
		LOGGER.debug(String.format("preHandle(request=%s,response=%s,handler=%s)", request, response, handler));

		final InfoPageRequest infoPageRequest = this.infoPageLinkHelper.toInfoPageRequest(request);
		boolean goOn = infoPageRequest != null && infoPageRequest.isValid();
		if (!goOn) {
			LOGGER.warn(String.format("URI:: %s does not correspond to any infoPage", request.getRequestURI()));
			request.setAttribute(InfoPageConstants.INFOPAGE_INVALID_REQUEST, String.format("InfoPage for path %s can not be requested, due to invalid request", request.getRequestURI()));
			return false;
		}

		goOn = this.mappingService.hasInfoPage(infoPageRequest.getObjectClass());
		if (!goOn) {
			LOGGER.warn(String.format("ObjectClass:: %s does not correspond to any infoPage", infoPageRequest.getObjectClass()));
			request.setAttribute(InfoPageConstants.INFOPAGE_NOT_AVAILABLE, String.format("InfoPage for domain %s can not be requested because it is not defined", ClassUtils.getShortName(infoPageRequest.getObjectClass())));
		}

		return goOn;
	}

	/** {@inheritDoc} */
	@Override
	public void postHandle(final HttpServletRequest request,
	                       final HttpServletResponse response,
	                       final Object handler,
	                       final ModelAndView modelAndView) throws Exception {
		LOGGER.debug(String.format("postHandle(request=%s,response=%s,handler=%s,modelAndView=%s)", request, response, handler, modelAndView));

		final ModelMap modelMap = modelAndView.getModelMap();
		final InfoPageRequest infoPageRequest = this.infoPageLinkHelper.toInfoPageRequest(request);

		final boolean valid = infoPageRequest.isValid();
		modelMap.put(InfoPageConstants.INFOPAGE_AVAILABLE, true);

		if (valid) {
			modelMap.put(InfoPageConstants.INFOPAGE_REQUEST, infoPageRequest);
			modelMap.put(InfoPageConstants.INFOPAGE_PAGE, this.getInfoPage(infoPageRequest));
			modelMap.put(InfoPageConstants.INFOPAGE_DS, this.getDataSourceLink());
		}

		LOGGER.trace(String.format("IP=%s(%s)", modelAndView.getViewName(), modelMap));

	}

	private InfoPageComponent getInfoPage(final InfoPageRequest infoPageRequest) throws SException {
		final InfoPage page = this.providerService.getInfoPage(infoPageRequest.getObjectClass());
		return this.builderService.buildInfoPage(page);
	}

	private Link getDataSourceLink() throws ControllerTierException {
		return linkTo(methodOn(SVComponentsDataController.class).onInfoPageDataRequest(null, null)).withRel(InfoPageConstants.INFOPAGE_DS);
	}

}
