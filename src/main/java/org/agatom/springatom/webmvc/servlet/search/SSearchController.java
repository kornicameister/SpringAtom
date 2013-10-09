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

package org.agatom.springatom.webmvc.servlet.search;

import org.agatom.springatom.web.beans.search.SSearchCommandBean;
import org.agatom.springatom.webmvc.servlet.controllers.SDefaultController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * {@code SSearchController is the controller which resolves all search-based request coming from the UI}.
 * It does recognize the type of the search, push the request to services and than return the logic back
 * to the UI to show results.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(value = SSearchController.CONTROLLER_NAME)
@RequestMapping(value = "/search")
public class SSearchController
        extends SDefaultController {
    protected static final String CONTROLLER_NAME = "SearchController";
    private static final   Logger LOGGER          = Logger.getLogger(SSearchController.class);

    public SSearchController() {
        super(CONTROLLER_NAME);
    }

    @RequestMapping(value = "/global", method = RequestMethod.POST)
    public String doSearch(@ModelAttribute final SSearchCommandBean bean) {
        LOGGER.debug(String.format("Searching for = %s", bean));
        return "redirect:/app";
    }
}