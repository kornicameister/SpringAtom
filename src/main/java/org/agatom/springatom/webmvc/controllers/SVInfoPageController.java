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

import org.agatom.springatom.ip.DomainInfoPage;
import org.agatom.springatom.ip.InfoPage;
import org.agatom.springatom.ip.InfoPageConstants;
import org.agatom.springatom.ip.mapping.InfoPageMappings;
import org.agatom.springatom.ip.resource.InfoPageResource;
import org.agatom.springatom.webmvc.exceptions.ControllerTierException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(value = SVInfoPageController.CONTROLLER_NAME)
@RequestMapping(value = "/ip")
public class SVInfoPageController {
    public static final  String CONTROLLER_NAME = "IPController";
    private static final String VIEW_NAME       = "springatom.tiles.ip.InfoPage";
    private static final Logger LOGGER          = Logger.getLogger(SVInfoPageController.class);
    @Autowired
    private InfoPageMappings infoPageMappings;

    @RequestMapping(value = "/{domain}/{id}", method = RequestMethod.GET)
    public ModelAndView getInfoPage(@PathVariable("domain") String domain, @PathVariable("id") Long id) throws InfoPageNotFoundException {
        LOGGER.debug(String.format("/getFromDomain/domainClass=%s/id=%s", domain, id));

        final ModelMap modelMap = new ModelMap();
        final InfoPage infoPageForPath = infoPageMappings.getInfoPageForPath(domain);

        if (infoPageForPath != null) {
            LOGGER.trace(String.format("Resolved infoPage => %s for path => %s", infoPageForPath, domain));
            final InfoPageResource infoPage = this.createResource(infoPageForPath, id);
            modelMap.put(InfoPageConstants.INFOPAGE_RESOURCE_NAME, infoPage);
            return new ModelAndView(VIEW_NAME, modelMap);
        }

        throw new InfoPageNotFoundException(domain);
    }

    private InfoPageResource createResource(final InfoPage ip, final Long id) {
        final InfoPageResource infoPageResource = new InfoPageResource(ip);
        if (ip instanceof DomainInfoPage) {
            final DomainInfoPage domainInfoPage = (DomainInfoPage) ip;
            final Class<?> repositoryClass = domainInfoPage.getRepositoryClass();
            if (repositoryClass.isAnnotationPresent(RestResource.class)) {
                final RestResource annotation = repositoryClass.getAnnotation(RestResource.class);
                final String path = annotation.path();
                infoPageResource.add(new Link(String.format("/rest/%s/%d", path, id), InfoPageConstants.INFOPAGE_REST_CONTENT_LINK));
            }
        }
        return infoPageResource;
    }

    @ResponseBody
    @ExceptionHandler({InfoPageNotFoundException.class})
    public ResponseEntity<?> handleNPE(InfoPageNotFoundException ipnfe) {
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
