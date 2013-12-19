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

package org.agatom.springatom.component.config;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Maps;
import org.agatom.springatom.component.builders.ComponentBuilder;
import org.agatom.springatom.component.builders.ComponentBuilders;
import org.agatom.springatom.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.component.data.ComponentDataRequest;
import org.apache.log4j.Logger;
import org.joor.Reflect;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class ComponentBuilderRepository
        implements ComponentBuilders,
                   ApplicationContextAware,
                   BeanFactoryAware {
    private static final Logger LOGGER = Logger.getLogger(ComponentBuilderRepository.class);
    private ApplicationContext              context;
    private ConfigurableListableBeanFactory beanFactory;
    private Map<String, DefinitionHolder>   holderMap;

    @PostConstruct
    private void readBuilders() {
        final String[] beanNamesForType = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(this.context, ComponentBuilder.class, true, false);
        if (beanNamesForType.length > 0) {

            this.holderMap = Maps.newHashMap();

            for (final String beanName : beanNamesForType) {
                final ScannedGenericBeanDefinition definition = (ScannedGenericBeanDefinition) this.beanFactory.getBeanDefinition(beanName);
                if (!definition.isAbstract()) {
                    final String beanClassName = definition.getBeanClassName();
                    final Class<?> beanClazz = Reflect.on(beanClassName).get();

                    final Class<?> builds = (Class<?>) AnnotationUtils.getValue(beanClazz.getAnnotation(ComponentBuilds.class), CRITERIA.TARGET.key);
                    final ComponentBuilds.Produces producesType = (ComponentBuilds.Produces) AnnotationUtils
                            .getValue(beanClazz.getAnnotation(ComponentBuilds.class), CRITERIA.PRODUCES.key);

                    this.holderMap.put(beanName, new DefinitionHolder(builds, producesType));

                }
            }
        }
    }

    @Override
    public ComponentBuilder<?> getBuilder(final String componentId, final ModelMap modelMap, final WebRequest request) {
        final ComponentBuilder<?> builder = (ComponentBuilder<?>) this.context.getBean(componentId);
        if (builder != null) {
            LOGGER.trace(String.format("Found builder for target %s", componentId));
            builder.init(new ComponentDataRequest(modelMap, request));
            return builder;
        }
        return null;
    }

    @Override
    public ComponentBuilder<?> getBuilder(final Class<?> target, final ModelMap modelMap, final WebRequest request) {
        return this.getBuilder(target, ComponentBuilds.Produces.PAGE_COMPONENT, modelMap, request);
    }

    @Override
    public ComponentBuilder<?> getBuilder(final Class<?> target, final ComponentBuilds.Produces produces, final ModelMap modelMap, final WebRequest request) {
        return this.getBuilder(this.getBuilderId(target, produces), modelMap, request);
    }

    @Override
    public boolean hasBuilder(final Class<?> target) {
        return this.hasBuilder(target, ComponentBuilds.Produces.PAGE_COMPONENT);
    }

    @Override
    public boolean hasBuilder(final Class<?> target, final ComponentBuilds.Produces produces) {
        return this.getBuilderId(target, produces) != null;
    }

    @Override
    public String getBuilderId(final Class<?> target) {
        return this.getBuilderId(target, ComponentBuilds.Produces.PAGE_COMPONENT);
    }

    @Override
    public String getBuilderId(final Class<?> target, final ComponentBuilds.Produces produces) {
        for (final String componentId : this.holderMap.keySet()) {
            if (this.holderMap.get(componentId).equals(target, produces)) {
                return componentId;
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    private static enum CRITERIA {
        TARGET("builds"),
        PRODUCES("produces"),
        ID("id");
        private final String key;

        CRITERIA(final String key) {
            this.key = key;
        }

    }

    private static class DefinitionHolder
            implements Comparable<DefinitionHolder> {

        private final Class<?>                 builds;
        private final ComponentBuilds.Produces produces;

        public DefinitionHolder(final Class<?> builds, final ComponentBuilds.Produces produces) {
            this.builds = builds;
            this.produces = produces;
        }

        public boolean equals(final Class<?> target, final ComponentBuilds.Produces produces) {
            return this.builds.equals(target) && this.produces.equals(produces);
        }

        @Override
        public int compareTo(@Nonnull final DefinitionHolder o) {
            return ComparisonChain.start().compare(this.builds.getName(), o.builds.getName()).compare(this.produces, o.produces).result();
        }
    }
}
