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

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.AbstractUrlViewController;
import org.springframework.web.util.UriTemplate;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * {@code SVTilesViewController} is generic controller mapping requests to views.
 * Views are loaded from {@code urlMappingProperties}
 *
 * @author kornicameister
 * @version $Id: $Id
 */
public class SVTilesViewController
		extends AbstractUrlViewController {
	private static final Logger                   LOGGER        = Logger.getLogger(SVTilesViewController.class);
	private static final String                   PROP_LOCATION = "urlMappingsProperties";
	private final        Map<String, String>      urlMap        = Maps.newHashMap();
	private final        Map<String, UriTemplate> templateUrls  = Maps.newHashMap();
	@Autowired
	@Qualifier(value = PROP_LOCATION)
	private              Properties               mappings      = null;

	@PostConstruct
	private void initializeMapping() {
		CollectionUtils.mergePropertiesIntoMap(mappings, this.urlMap);
		this.findTemplateUrls();
	}

	private void findTemplateUrls() {
		final Set<String> templateUrls = FluentIterable
				.from(this.urlMap.keySet())
				.filter(new Predicate<String>() {
					@Override
					public boolean apply(@Nullable final String input) {
						return input != null && input.contains("*");
					}
				})
				.toSet();
		LOGGER.info(String.format("Found %d template urls", templateUrls.size()));
		for (final String templateUrl : templateUrls) {
			final String substring = templateUrl.substring(1);
			this.templateUrls.put(substring, new UriTemplate(substring));
		}
	}

	/** {@inheritDoc} */
	@Override
	protected String getViewNameForRequest(final HttpServletRequest request) {
		final String path = this.getUrlPathHelper().getLookupPathForRequest(request);
		final String shortName = ClassUtils.getShortName(this.getClass());

		String viewName = null;
		if (this.urlMap.containsKey(path)) {
			viewName = this.urlMap.get(path);
			LOGGER.info(String.format("For path %s resolved view %s", path, viewName));
		} else {
			final UriTemplate template = this.getTemplate(path);
			if (template != null) {
				viewName = this.urlMap.get(String.format("*%s", template.toString()));
				LOGGER.info(String.format("For path %s resolved view %s", path, viewName));
				request.setAttribute(String.format("%sByTemplate", shortName), true);
				return viewName;
			}
		}
		if (viewName != null) {
			request.setAttribute(String.format("%sViewName", shortName), viewName);
		}
		return viewName;
	}

	private UriTemplate getTemplate(final String path) {
		for (final String key : this.templateUrls.keySet()) {
			final UriTemplate template = this.templateUrls.get(key);
			if (template.matches(path)) {
				return template;
			}
		}
		return null;
	}

}
