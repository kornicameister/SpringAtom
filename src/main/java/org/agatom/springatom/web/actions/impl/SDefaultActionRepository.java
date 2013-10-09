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

package org.agatom.springatom.web.actions.impl;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.agatom.springatom.web.actions.SActionRepository;
import org.agatom.springatom.web.actions.beans.SActions;
import org.agatom.springatom.web.actions.exception.SActionRepositoryException;
import org.agatom.springatom.web.actions.exception.SNoSuchActionDefinition;
import org.agatom.springatom.web.actions.xml.SActionsXMLResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SDefaultActionRepository
        implements ResourceLoaderAware,
                   InitializingBean,
                   SActionRepository {
    private final static Logger LOGGER = Logger.getLogger(SDefaultActionRepository.class);
    private Map<String, String>   actions;
    private Map<String, SActions> cache;
    private ResourceLoader        resourceLoader;
    private SActionsXMLResolver   xmlResolver;
    private Integer               capacity;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.actions == null || this.actions.isEmpty()) {
            throw new SActionRepositoryException("Actions map can be null or empty");
        }
        if (this.resourceLoader == null) {
            this.resourceLoader = new DefaultResourceLoader();
        }
        this.cache = Maps.newConcurrentMap();
        // test purpose
    }

    private SActions resolveActionsDefinitions(final String componentKey) throws SNoSuchActionDefinition {
        if (this.actions.containsKey(componentKey)) {
            final Resource resource = this.resourceLoader.getResource(this.actions.get(componentKey));
            if (resource != null) {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(String.format("Reading definitions from resource=%s", resource.getFilename()));

                    try {
                        final SActions actions = this.xmlResolver
                                .getDefinitions(resource.getFile().getAbsolutePath());
                        if (LOGGER.isTraceEnabled()) {
                            LOGGER.trace(String
                                    .format("Read definition from\n->resource=%s\n->definition=%s", resource
                                            .getFilename(), actions));
                        }
                        if (this.cache.size() + 1 == capacity) {
                            this.cache.clear();
                        }
                        this.cache.put(componentKey, actions);
                        return actions;
                    } catch (IOException exception) {
                        LOGGER.fatal(String
                                .format("Reading failed from resource=%s", resource.getFilename()), exception);
                    }

                }
            }
        }
        throw new SNoSuchActionDefinition(componentKey);
    }

    @Override
    public SActions getActionsForComponent(final String component) throws SNoSuchActionDefinition {
        final SActions sActions = this.cache.get(component);
        if (sActions == null) {
            LOGGER.trace(String.format("Actions definition not found in cache, resolving for component=%s", component));
            return this.resolveActionsDefinitions(component);
        }
        return sActions;
    }

    @Required
    public void setXmlResolver(SActionsXMLResolver xmlResolver) {
        this.xmlResolver = xmlResolver;
    }

    @Required
    public void setActions(final Map<String, String> actionsDefinitionsPath) {
        this.actions = actionsDefinitionsPath;
    }

    @Required
    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public void setResourceLoader(final ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(cache)
                      .addValue(capacity)
                      .toString();
    }
}
