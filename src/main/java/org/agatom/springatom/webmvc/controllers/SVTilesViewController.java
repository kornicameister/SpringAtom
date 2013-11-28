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

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.AbstractUrlViewController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Properties;


public class SVTilesViewController
        extends AbstractUrlViewController {
    private static final Logger              LOGGER = Logger.getLogger(SVTilesViewController.class);
    private final        Map<String, String> urlMap = Maps.newHashMap();

    @Override
    protected String getViewNameForRequest(final HttpServletRequest request) {
        final String path = this.getUrlPathHelper().getLookupPathForRequest(request);
        if (this.urlMap.containsKey(path)) {
            final String viewName = this.urlMap.get(path);
            LOGGER.info(String.format("For path %s resolved view %s", path, viewName));
            return viewName;
        }
        return null;
    }

    public void setMappings(Properties mappings) {
        CollectionUtils.mergePropertiesIntoMap(mappings, this.urlMap);
    }
}