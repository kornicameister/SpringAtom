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

import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.data.ComponentDataResponse;
import org.agatom.springatom.web.component.infopages.builder.InfoPageComponentBuilder;
import org.agatom.springatom.web.component.infopages.builder.InfoPageComponentBuilderDispatcher;
import org.agatom.springatom.web.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.web.component.infopages.provider.builder.InfoPageComponentBuilderService;
import org.agatom.springatom.web.component.infopages.request.InfoPageComponentRequest;
import org.agatom.springatom.webmvc.exceptions.ControllerTierException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Persistable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * {@code SVComponentsDataController} receives calls for data for either {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
 * and {@link org.agatom.springatom.web.component.table.elements.TableComponent}. Request is routed to appropriate {@link org.agatom.springatom.web.component.core.builders.Builder}
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 18.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/cmp/data")
@Description(value = "Data controller for components")
public class SVComponentsDataController
		extends AbstractComponentController {
	private static final Logger                             LOGGER                             = Logger.getLogger(SVComponentsDataController.class);
	@Autowired
	private              InfoPageComponentBuilderService    infoPageBuilderService             = null;
	@Autowired
	private              InfoPageComponentBuilderDispatcher infoPageComponentBuilderDispatcher = null;
	@Autowired
	private              CDRReturnValueConverter            returnValueConverter               = null;

	/**
	 * <p>onInfoPageDataRequest.</p>
	 *
	 * @param cmpRequest a {@link org.agatom.springatom.web.component.infopages.request.InfoPageComponentRequest} object.
	 * @param webRequest a {@link org.springframework.web.context.request.WebRequest} object.
	 *
	 * @return a {@link java.lang.Object} object.
	 *
	 * @throws org.agatom.springatom.webmvc.exceptions.ControllerTierException if any.
	 */
	@ResponseBody
	@RequestMapping(
			value = "/ip",
			method = RequestMethod.POST,
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public Object onInfoPageDataRequest(@RequestBody final InfoPageComponentRequest cmpRequest, final WebRequest webRequest) throws ControllerTierException {

		LOGGER.trace(String.format("onInfoPageDataRequest(cmpRequest=%s,webRequest=%s)", cmpRequest, webRequest));
		final ComponentDataRequest request = this.combineRequest(cmpRequest, webRequest);
		LOGGER.trace(String.format("%s is %s", ClassUtils.getShortName(request.getClass()), request));

		ComponentDataResponse data;

		try {
			final long startTime = System.nanoTime();
			{
				final Class<? extends Persistable<?>> domain = getDomain(cmpRequest.getDomain());
				LOGGER.trace(String.format("Requesting for %s for domain %s", ClassUtils.getShortName(InfoPageComponent.class), domain));

				this.setInfoPageComponentInDataRequest(request, domain);

				final InfoPageComponentBuilder<? extends Persistable<?>> ipBuilder =
						this.infoPageComponentBuilderDispatcher.getInfoPageComponentBuilder(domain);

				data = ipBuilder.getData(request);
			}
			final long endTime = System.nanoTime();

			LOGGER.info(String.format("For %s returning data %s in %dms", cmpRequest, data, TimeUnit.NANOSECONDS.toMillis(endTime - startTime)));
		} catch (Exception exp) {
			throw new ControllerTierException(String.format("onInfoPageDataRequest(cmpRequest=%s,webRequest=%s) failed...", cmpRequest, webRequest), exp);
		}

		return this.returnValueConverter.convert(data, request);
	}

	/**
	 * Retrieves casted domain {@link java.lang.Class}
	 *
	 * @param domain raw domain
	 *
	 * @return the domain class
	 */
	@SuppressWarnings("unchecked")
	private Class<? extends Persistable<?>> getDomain(final Class<?> domain) {
		return (Class<? extends Persistable<?>>) domain;
	}

	private void setInfoPageComponentInDataRequest(final ComponentDataRequest request, final Class<? extends Persistable<?>> domain) throws org.agatom.springatom.core.exception.SException, ControllerTierException {
		if (ClassUtils.isAssignable(Persistable.class, domain)) {
			final InfoPageComponent infoPage = this.infoPageBuilderService.buildInfoPage(domain);
			request.setComponent(infoPage);
		} else {
			throw new ControllerTierException(String.format("%s domain is not Persistable class", domain));
		}
	}

	@ResponseBody
	@RequestMapping(
			value = "/table",
			method = RequestMethod.POST,
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public Object onTableDataRequest(final Object o, final Object o1) {
		return null;
	}

}
