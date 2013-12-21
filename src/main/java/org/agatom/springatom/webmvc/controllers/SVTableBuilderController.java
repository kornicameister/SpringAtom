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

package org.agatom.springatom.webmvc.controllers;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;
import org.agatom.springatom.component.builders.ComponentBuilder;
import org.agatom.springatom.component.builders.ComponentBuilders;
import org.agatom.springatom.component.builders.table.TableComponentBuilder;
import org.agatom.springatom.component.data.ComponentDataResponse;
import org.agatom.springatom.component.elements.value.InContextBuilderLink;
import org.agatom.springatom.ip.InfoPageConstants;
import org.agatom.springatom.webmvc.data.DataBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(value = SVTableBuilderController.CONTROLLER_NAME)
@RequestMapping(value = "/tableBuilder")
@ExposesResourceFor(value = TableComponentBuilder.class)
public class SVTableBuilderController {
    protected static final String CONTROLLER_NAME = "tableBuilderController";
    private static final   Logger LOGGER          = Logger.getLogger(SVTableBuilderController.class);
    private static final   String VIEW_NAME       = "springatom.tiles.table.DandelionTableView";
    @Autowired
    private ComponentBuilders builders;

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.TEXT_HTML_VALUE}
    )
    public ModelAndView getTableBuilder(@PathVariable("id") final String builderId) {
        LOGGER.info(String.format("/getTable -> builder=%s", builderId));
        final ComponentBuilder<?> builder = this.builders.getBuilder(builderId);
        if (builder != null && builder instanceof TableComponentBuilder) {
            LOGGER.trace(String.format("Found builder %s:%s:%s", builderId, builder.getId(), builder.getTarget()));
        }
        return new ModelAndView(VIEW_NAME, new ModelMap("builder", builder));
    }

    @RequestMapping(
            value = "/inContext",
            method = RequestMethod.POST,
            produces = {MediaType.TEXT_HTML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ModelAndView getTableBuilderPost(@RequestBody final DataBean data) {
        final String builderId = data.get("builderId").getValue();
        LOGGER.info(String.format("/getTable -> builder=%s", builderId));
        final ComponentBuilder<?> builder = this.builders.getBuilder(builderId);
        if (builder != null && builder instanceof TableComponentBuilder) {
            LOGGER.trace(String.format("Found builder %s:%s:%s", builderId, builder.getId(), builder.getTarget()));
        }

        final ModelMap modelMap = new ModelMap(InfoPageConstants.TABLE_COMPONENT_BUILDER, builder);
        modelMap.addAttribute(InfoPageConstants.INFOPAGE_PARAMS, data.toModelMap());

        return new ModelAndView(VIEW_NAME, modelMap);
    }

    @ResponseBody
    @RequestMapping(value = "/data/{id}")
    public DatatablesResponse getData(
            @PathVariable("id") final String builderId,
            final @DatatablesParams DatatablesCriterias criterias,
            final InContextBuilderLink contextLink,
            final WebRequest request) {
        LOGGER.info(String.format("/getTable -> builder=%s, criterias=%s", builderId, criterias));

        final ModelMap map = new ModelMap("dandelionCriterias", criterias);
        map.put("contextLink", contextLink);

        final ComponentBuilder<?> builder = this.builders.getBuilder(builderId, map, request);

        if (builder != null && builder instanceof TableComponentBuilder) {
            LOGGER.trace(String.format("Found builder %s:%s:%s", builderId, builder.getId(), builder.getTarget()));
            final ComponentDataResponse<?> data = (ComponentDataResponse<?>) builder.getData();
            return DatatablesResponse.build((DataSet) data.getValue(), criterias);
        }
        return null;
    }

}
