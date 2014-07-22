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

import org.agatom.springatom.core.exception.SException;
import org.agatom.springatom.web.component.core.builders.Builder;
import org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.data.ComponentDataResponse;
import org.agatom.springatom.web.component.core.data.RequestedBy;
import org.agatom.springatom.web.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.web.component.infopages.link.InfoPageRequest;
import org.agatom.springatom.web.component.infopages.request.InfoPageComponentRequest;
import org.agatom.springatom.web.component.table.request.TableDefinitionRequest;
import org.agatom.springatom.webmvc.controllers.components.data.CmpResource;
import org.agatom.springatom.webmvc.exceptions.ControllerTierException;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * {@code SVComponentsDefinitionController} receives calls for data for either {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
 * and {@link org.agatom.springatom.web.component.table.elements.TableComponent}. Request is routed to appropriate {@link org.agatom.springatom.web.component.core.builders.Builder}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 18.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.3
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/cmp/config")
@Description(value = "Configuration controller for components")
public class SVComponentsDefinitionController
		extends AbstractComponentController {
	private static final Logger LOGGER = Logger.getLogger(SVComponentsDefinitionController.class);

	@ResponseBody
	@RequestMapping(
			value = "/ip/{domain}/{id}",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public Object onInfoPageConfigRequest(@PathVariable("domain") final String domain, @PathVariable("id") final Long id, final WebRequest webRequest) throws ControllerTierException {
		LOGGER.debug(String.format("onInfoPageConfigRequest(domain=%s,id=%s)", domain, id));
		try {
			final long startTime = System.nanoTime();

			final ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
			final InfoPageRequest pageRequest = this.infoPageControllerUtils.getInfoPageRequest(servletWebRequest.getRequest());

			if (!pageRequest.isValid()) {
				LOGGER.trace(String.format("InfoPage not located for domain=%s", domain));
				throw new SException("Computed InfoPageRequest is not valid");
			}

			final InfoPageComponent ipCmp = this.infoPageControllerUtils.getInfoPageComponent(pageRequest.getObjectClass());
			final InfoPageComponentRequest ipCmpRequest = this.infoPageControllerUtils.getInfoPageComponentRequest(ipCmp, pageRequest);

			final ComponentDataRequest dataRequest = this.combineRequest(ipCmpRequest, webRequest);
			dataRequest.setRequestedBy(RequestedBy.INFOPAGE);
			final ComponentDataResponse dataResponse = ComponentDataResponse.success(ClassUtils.getShortName(this.getClass()), ipCmp, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));

			final CmpResource<?> convertedData = this.toComponentResource(dataRequest, dataResponse);
			convertedData.add(linkTo(methodOn(SVComponentsDefinitionController.class).onInfoPageConfigRequest(domain, id, null)).withSelfRel());

			LOGGER.info(String.format("For %s returning data %s in %dms", ipCmpRequest, convertedData, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)));

			return convertedData;
		} catch (Exception exp) {
			LOGGER.error(String.format("onInfoPageDataRequest(domain=%s) threw %s", domain, ClassUtils.getShortName(exp.getClass())), exp);
			throw new ControllerTierException(exp);
		}
	}

	/**
	 * Returns {@link org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder#getDefinition(org.agatom.springatom.web.component.core.data.ComponentDataRequest)}.
	 * Request must contain information about {@code builderId} to be used to retrieve the definition.
	 *
	 * @param builderId  builderId
	 * @param webRequest {@link org.springframework.web.context.request.WebRequest} instance
	 *
	 * @return the definition
	 *
	 * @throws org.agatom.springatom.webmvc.exceptions.ControllerTierException if failed
	 */
	@ResponseBody
	@RequestMapping(
			value = "/table/{builderId}",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public Object onTableConfigRequest(@PathVariable("builderId") final String builderId, final WebRequest webRequest) throws ControllerTierException {
		LOGGER.trace(String.format("onTableConfigRequest(builderId=%s,webRequest=%s)", builderId, webRequest));
		try {
			final Builder builder = this.builderRepository.getBuilder(builderId);

			Assert.notNull(builder, String.format("Builder for builderId=%s not found", builderId));
			Assert.isInstanceOf(ComponentDefinitionBuilder.class, builder, String.format("Builder for builderId=%s not capable of producing definition", builderId));

			final ComponentDefinitionBuilder<?> definitionBuilder = (ComponentDefinitionBuilder<?>) builder;

			LOGGER.trace(String.format("For builderId=%s using object=%s", builderId, definitionBuilder));

			final ComponentDataRequest request = this.combineRequest(new TableDefinitionRequest().setBuilderId(builderId), webRequest);
			request.setRequestedBy(RequestedBy.TABLE);

			final CmpResource<?> resource = this.toComponentResource(request, definitionBuilder.getDefinition(request));
			resource.add(linkTo(methodOn(SVComponentsDefinitionController.class).onTableConfigRequest(builderId, webRequest)).withSelfRel());
			return resource;

		} catch (Exception exp) {
			LOGGER.error(String.format("onTableConfigRequest(builderId=%s,webRequest=%s) failed...", builderId, webRequest), exp);
			throw new ControllerTierException(String.format("Failed to get table config for builderId=%s", builderId), exp);
		}
	}

}
