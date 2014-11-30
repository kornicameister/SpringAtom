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

import com.google.common.collect.Multimap;
import org.agatom.springatom.cmp.component.core.builders.Builder;
import org.agatom.springatom.cmp.component.core.builders.ComponentDataBuilder;
import org.agatom.springatom.cmp.component.core.builders.ComponentDefinitionBuilder;
import org.agatom.springatom.cmp.component.core.builders.exception.ComponentException;
import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.cmp.component.core.repository.ComponentMetaData;
import org.agatom.springatom.core.web.DataResource;
import org.agatom.springatom.webmvc.controllers.components.data.CmpResource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Description;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-09</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/cmp/components", produces = {MediaType.APPLICATION_JSON_VALUE})
@Description(value = "Controller for components")
public class SVComponentController
        extends AbstractComponentController {
    private static final Logger LOGGER = Logger.getLogger(SVComponentController.class);

    @ResponseBody
    @RequestMapping(value = "/builders", method = RequestMethod.GET) /* /cmp/components/get/builders/map */
    public DataResource<?> onAllBuilders() throws ComponentException {
        final long startTime = System.nanoTime();
        DataResource<?> resource;

        try {
            final Multimap<String, ComponentMetaData> allBuilders = this.builderRepository.getAllBuilders();

            resource = new CmpResource(allBuilders);

            resource.add(linkTo(methodOn(SVComponentController.class).onAllBuilders()).withSelfRel());
            for (String builderId : allBuilders.keySet()) {
                resource.add(linkTo(methodOn(SVComponentController.class).onComponentRequest(builderId, null, null)).withRel(builderId));
            }

            resource.setSize(allBuilders.size()).setSuccess(true);
        } catch (Exception exp) {
            resource = new CmpResource(null).setError(exp).setMessage(exp.getLocalizedMessage()).setSuccess(false);
        }

        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        return resource.setTime(endTime);
    }

    @ResponseBody
    @RequestMapping(value = "/get/component/{builderId}", method = RequestMethod.GET)
    public DataResource<?> onComponentRequest(
            @PathVariable("builderId") final String builderId,
            final Locale locale,
            final NativeWebRequest webRequest) throws ComponentException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("onComponentRequest(builderId=%s,locale=%s)", builderId, locale));
        }

        final long startTime = System.nanoTime();
        DataResource<?> resource;

        try {
            final Builder builder = this.builderRepository.getBuilder(builderId);
            final ComponentDataRequest componentDataRequest = this.getComponentDataRequest(webRequest);

            Object definition = null;
            if (ClassUtils.isAssignableValue(ComponentDefinitionBuilder.class, builder)) {
                final ComponentDefinitionBuilder definitionBuilder = (ComponentDefinitionBuilder) builder;
                definition = definitionBuilder.getDefinition(componentDataRequest);
            }

            if (definition != null) {
                componentDataRequest.setComponent(definition);
            }

            Object data = null;
            if (ClassUtils.isAssignableValue(ComponentDataBuilder.class, builder)) {
                final ComponentDataBuilder dataBuilder = (ComponentDataBuilder) builder;
                data = dataBuilder.getData(componentDataRequest);
                if (data != null) {
                    data = this.converter.convert(data, componentDataRequest);
                }
            }

            // we have definition and the data
            resource = new CmpResource(data, definition).setSize(2).setSuccess(true);

        } catch (Exception exp) {
            resource = new CmpResource(null).setError(exp).setMessage(exp.getLocalizedMessage()).setSuccess(false);
        }

        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        resource.setTime(endTime);
        resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SVComponentController.class).onComponentRequest(builderId, locale, webRequest)).withSelfRel());

        return resource;
    }
}
