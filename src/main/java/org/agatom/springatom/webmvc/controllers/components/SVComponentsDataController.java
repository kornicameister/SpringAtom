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

import com.google.common.base.Preconditions;
import org.agatom.springatom.web.component.core.builders.Builder;
import org.agatom.springatom.web.component.core.builders.ComponentDataBuilder;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.data.ComponentDataResponse;
import org.agatom.springatom.web.component.core.data.RequestedBy;
import org.agatom.springatom.web.component.infopages.builder.InfoPageComponentBuilder;
import org.agatom.springatom.web.component.infopages.builder.InfoPageComponentBuilderDispatcher;
import org.agatom.springatom.web.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.web.component.infopages.provider.builder.InfoPageComponentBuilderService;
import org.agatom.springatom.web.component.infopages.request.InfoPageComponentRequest;
import org.agatom.springatom.web.component.table.request.TableComponentRequest;
import org.agatom.springatom.webmvc.exceptions.ControllerTierException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Persistable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code SVComponentsDataController} receives calls for data for either {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
 * and {@link org.agatom.springatom.web.component.table.elements.TableComponent}. Request is routed to appropriate {@link org.agatom.springatom.web.component.core.builders.Builder}
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 18.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
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

	/**
	 * <p>onInfoPageDataRequest.</p>
	 * Mapped to address <b>/cmp/data/ip</b> resolved the data for the {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent}.
	 * Data is returned, as raw value, from {@link org.agatom.springatom.web.component.infopages.builder.InfoPageComponentBuilder#getData(org.agatom.springatom.web.component.core.data.ComponentDataRequest)}
	 * and then adjusted to proper representation with {@link org.agatom.springatom.webmvc.controllers.components.CDRReturnValueConverter}
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
		request.setRequestedBy(RequestedBy.INFOPAGE);
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

		return this.getConvertedData(request, data);
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

	private Map<String, Object> getConvertedData(final ComponentDataRequest request, final ComponentDataResponse data) throws ControllerTierException {
		try {
			return this.converter.convert(data, request);
		} catch (Exception exp) {
			throw new ControllerTierException("Failed to convert data using CDRReturnValueConverter", exp);
		}
	}

	@ResponseBody
	@RequestMapping(
			value = "/table/{builderId}",
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public Object onTableDataRequest(@PathVariable("builderId") final String builderId, final TableComponentRequest cmpRequest, final WebRequest webRequest) throws ControllerTierException {
		LOGGER.trace(String.format("onInfoPageDataRequest(cmpRequest=%s,webRequest=%s)", cmpRequest, webRequest));
		final ComponentDataRequest request = this.combineRequest(cmpRequest, webRequest);
		request.setRequestedBy(RequestedBy.TABLE);
		LOGGER.trace(String.format("%s is %s", ClassUtils.getShortName(request.getClass()), request));
		ComponentDataResponse data;

		try {
			final long startTime = System.nanoTime();
			{
				final Builder dataBuilder = this.builderRepository.getBuilder(builderId);
				Preconditions.checkNotNull(dataBuilder, String.format("Builder(id=%s) not found...", dataBuilder));
				Preconditions.checkArgument(
						ClassUtils.isAssignableValue(ComponentDataBuilder.class, dataBuilder),
						String.format("Builder(id=%s) found, but it is not %s", builderId, ClassUtils.getShortName(ComponentDataBuilder.class))
				);

				data = ((ComponentDataBuilder) dataBuilder).getData(request);
			}
			final long endTime = System.nanoTime();

			LOGGER.info(String.format("For %s returning data %s in %dms", cmpRequest, data, TimeUnit.NANOSECONDS.toMillis(endTime - startTime)));
		} catch (Exception exp) {
			throw new ControllerTierException(String.format("onTableDataRequest(cmpRequest=%s,webRequest=%s) failed...", cmpRequest, webRequest), exp);
		}

		return this.getConvertedData(request, data);
	}

}
