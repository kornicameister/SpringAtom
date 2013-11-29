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

package org.agatom.springatom.webmvc.controllers.ip;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(value = InfoPageController.CONTROLLER_NAME)
@RequestMapping(value = "/ip")
public class InfoPageController {
    public static final  String CONTROLLER_NAME = "infoPageController";
    private static final String VIEW_NAME       = "springatom.tiles.ip.InfoPage";
    private static final Logger LOGGER          = Logger.getLogger(InfoPageController.class);

    @RequestMapping(value = "/{domainClass}/{id}", method = RequestMethod.GET)
    public ModelAndView getInfoPage(
            @PathVariable("domainClass") String domainClass,
            @PathVariable("id") String id
    ) {
        LOGGER.debug(String.format("/getInfoPage/domain=%s/id=%s", domainClass, id));
        return new ModelAndView(VIEW_NAME);
    }
}