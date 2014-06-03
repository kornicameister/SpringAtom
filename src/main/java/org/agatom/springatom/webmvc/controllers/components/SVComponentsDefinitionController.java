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

import org.agatom.springatom.web.component.core.builders.Builder;
import org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder;
import org.agatom.springatom.web.component.core.repository.ComponentBuilderRepository;
import org.agatom.springatom.web.component.table.request.TableDefinitionRequest;
import org.agatom.springatom.webmvc.exceptions.ControllerTierException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * {@code SVComponentsDefinitionController} receives calls for data for either {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
 * and {@link org.agatom.springatom.web.component.core.elements.table.TableComponent}. Request is routed to appropriate {@link org.agatom.springatom.web.component.core.builders.Builder}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 18.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/cmp/config")
@Description(value = "Configuration controller for components")
public class SVComponentsDefinitionController
		extends AbstractComponentController {
	private static final Logger                     LOGGER            = Logger.getLogger(SVComponentsDefinitionController.class);
	@Autowired
	private              ComponentBuilderRepository builderRepository = null;

	/**
	 * Returns {@link org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder#getDefinition(org.agatom.springatom.web.component.core.data.ComponentDataRequest)}.
	 * Request must contain information about {@code builderId} to be used to retrieve the definition.
	 *
	 * @param request    {@link org.agatom.springatom.web.component.table.request.TableDefinitionRequest} instance
	 * @param webRequest {@link org.springframework.web.context.request.WebRequest} instance
	 *
	 * @return the definition
	 *
	 * @throws org.agatom.springatom.webmvc.exceptions.ControllerTierException if failed
	 */
	@ResponseBody
	@RequestMapping(
			value = "/table",
			method = RequestMethod.POST,
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public Object onTableConfigRequest(@RequestBody final TableDefinitionRequest request, final WebRequest webRequest) throws ControllerTierException {
		LOGGER.trace(String.format("onTableConfigRequest(request=%s,webRequest=%s)", request, webRequest));
		final String builderId = request.getBuilderId();
		try {
			final Builder builder = this.builderRepository.getBuilder(builderId);

			Assert.notNull(builder, String.format("Builder for builderId=%s not found", builderId));
			Assert.isInstanceOf(ComponentDefinitionBuilder.class, builder, String.format("Builder for builderId=%s not capable of producing definition", builderId));

			final ComponentDefinitionBuilder<?> definitionBuilder = (ComponentDefinitionBuilder<?>) builder;

			LOGGER.trace(String.format("For builderId=%s using object=%s", builderId, definitionBuilder));

			return definitionBuilder.getDefinition(this.combineRequest(request, webRequest));

		} catch (Exception exp) {
			LOGGER.error(String.format("onTableConfigRequest(builderId=%s,webRequest=%s) failed...", builderId, webRequest), exp);
			throw new ControllerTierException(String.format("Failed to get table config for builderId=%s", builderId), exp);
		}
	}


}
