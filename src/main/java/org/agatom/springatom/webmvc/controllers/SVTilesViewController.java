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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.mvc.AbstractUrlViewController;
import org.springframework.web.util.UriTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SVTilesViewController
		extends AbstractUrlViewController {
	private static final Logger                   LOGGER    = Logger.getLogger(SVTilesViewController.class);
	private final        Map<UriTemplate, String> templates = Maps.newHashMap();
	@Autowired
	@Qualifier(value = "urlMappingsProperties")
	private              Properties               mappings  = null;

	@PostConstruct
	private void initializeMapping() {
		final Set<String> templateUrls = this.mappings.stringPropertyNames();
		for (final String templateUrl : templateUrls) {
			this.templates.put(new UriTemplate(templateUrl), this.mappings.getProperty(templateUrl));
		}
	}

	@Override
	protected String getViewNameForRequest(final HttpServletRequest request) {
		final String path = this.getUrlPathHelper().getLookupPathForRequest(request);
		for (final UriTemplate template : this.templates.keySet()) {
			if (template.matches(path)) {
				final String viewName = this.templates.get(template);
				LOGGER.info(String.format("For path %s resolved view %s", path, viewName));
				return viewName;
			}
		}
		LOGGER.warn(String.format("Failed to retrieve view for path %s", path));
		return null;
	}

}