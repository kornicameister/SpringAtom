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

import org.agatom.springatom.web.component.builders.ComponentBuilders;
import org.agatom.springatom.web.infopages.InfoPageConstants;
import org.agatom.springatom.web.infopages.SEntityInfoPage;
import org.agatom.springatom.web.infopages.SInfoPage;
import org.agatom.springatom.web.infopages.component.builder.InfoPageComponentBuilder;
import org.agatom.springatom.web.infopages.mapping.InfoPageMappings;
import org.agatom.springatom.webmvc.data.DataBean;
import org.agatom.springatom.webmvc.exceptions.ControllerTierException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(value = SVInfoPageController.CONTROLLER_NAME)
@RequestMapping(value = "/ip")
public class SVInfoPageController {
    public static final  String CONTROLLER_NAME  = "IPController";
    private static final String VIEW_NAME        = "springatom.tiles.ip.InfoPage";
    private static final String DOMAIN_VIEW_NAME = "springatom.tiles.ip.DomainInfoPage";
    private static final String DATA_VIEW_NAME   = "springatom.tiles.ip.DataRenderer";
    private static final Logger LOGGER           = Logger.getLogger(SVInfoPageController.class);
    @Autowired
    private InfoPageMappings  infoPageMappings;
    @Autowired
    private ComponentBuilders builders;

    @RequestMapping(value = "/{path}/{id}", method = RequestMethod.GET)
    public ModelAndView getInfoPageView(@PathVariable("path") final String path,
                                        @PathVariable("id") final Long id) throws InfoPageNotFoundException {
        LOGGER.debug(String.format("/getInfoPageView/path=%s/id=%s", path, id));
        final SInfoPage page = this.getInfoPageForPath(path);
        if (page != null) {
            LOGGER.trace(String.format("Resolved infoPage => %s for path => %s", page, path));
            final ModelMap modelMap = new ModelMap();

            modelMap.put(InfoPageConstants.INFOPAGE_PAGE, page);
            modelMap.put(InfoPageConstants.INFOPAGE_VIEW_DATA_TEMPLATE_LINK, this.getViewDataTemplateLink());

            return new ModelAndView(
                    this.getViewForPage(page),
                    modelMap
            );
        }
        throw new InfoPageNotFoundException(path);
    }

    @RequestMapping(
            value = "/template/render",
            method = RequestMethod.POST,
            produces = {MediaType.TEXT_HTML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ModelAndView getInfoPageViewData(@RequestBody final DataBean data, final WebRequest request) throws InfoPageNotFoundException {
        LOGGER.trace(String.format("/getInfoPageViewData -> data=%s, request=%s", data, request));
        final String rel = data.get("infopage").getValue();
        final SInfoPage page = this.infoPageMappings.getInfoPageForRel(rel);

        if (page != null) {
            final InfoPageComponentBuilder builder = (InfoPageComponentBuilder) this.builders
                    .getBuilder(
                            page.getClass(),
                            data.toModelMap(),
                            request
                    );
            return new ModelAndView(DATA_VIEW_NAME, new ModelMap(InfoPageConstants.INFOPAGE_BUILDER, builder));
        }

        throw new InfoPageNotFoundException(rel);
    }

    private String getViewDataTemplateLink() throws InfoPageNotFoundException {
        return linkTo(methodOn(SVInfoPageController.class).getInfoPageViewData(null, null)).withRel("dummy").getHref();
    }

    private SInfoPage getInfoPageForPath(final String domain) {
        LOGGER.debug(String.format("/getInfoPageForPath/domain=%s", domain));
        return infoPageMappings.getInfoPageForPath(domain);
    }

    private String getViewForPage(final SInfoPage page) {
        return page instanceof SEntityInfoPage ? DOMAIN_VIEW_NAME : VIEW_NAME;
    }

    @ResponseBody
    @ExceptionHandler({InfoPageNotFoundException.class})
    public ResponseEntity<?> handleInfoPageNotFound(InfoPageNotFoundException ipnfe) {
        LOGGER.error(ipnfe.getMessage());
        return new ResponseEntity<>(ipnfe, HttpStatus.NOT_FOUND);
    }

    public static class InfoPageNotFoundException
            extends ControllerTierException {
        public InfoPageNotFoundException(final String domain) {
            super(String.format("InfoPage not found for domain => %s", domain));
        }
    }
}
