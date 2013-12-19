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

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.component.builders.ComponentBuilders;
import org.agatom.springatom.component.builders.SComponentBuilder;
import org.agatom.springatom.component.builders.annotation.ComponentBuilder;
import org.agatom.springatom.component.data.ComponentDataRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class ComponentBuilderRepository
        implements ComponentBuilders,
                   ApplicationContextAware {
    private static final Logger LOGGER = Logger.getLogger(ComponentBuilderRepository.class);
    private ApplicationContext context;

    @Override
    public SComponentBuilder<?> getBuilder(final Class<?> target, final ModelMap modelMap, final WebRequest request) {
        final Map<String, Object> beansWithAnnotation = this.context.getBeansWithAnnotation(ComponentBuilder.class);
        final boolean classValuesAsString = true;

        final Optional<Object> beanDefinitionOptional = FluentIterable
                .from(beansWithAnnotation.values())
                .firstMatch(new Predicate<Object>() {
                    @Override
                    public boolean apply(@Nullable final Object input) {
                        if (input != null) {
                            if (input.getClass().isAnnotationPresent(ComponentBuilder.class)) {
                                final Annotation ann = input.getClass().getAnnotation(ComponentBuilder.class);
                                final Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(
                                        ann,
                                        classValuesAsString,
                                        false
                                );
                                final String pathValue = (String) annotationAttributes.get(CRITERIA.TARGET.key);

                                return pathValue != null && pathValue.equals(target.getName());
                            }
                        }
                        return false;
                    }
                });

        LOGGER.trace(String.format("Resolved possible definition under => %s", beanDefinitionOptional));

        if (beanDefinitionOptional.isPresent()) {
            final Object builder = beanDefinitionOptional.get();
            if (builder != null) {
                LOGGER.trace(String.format("Found builder for target %s", target));
                final SComponentBuilder<?> componentBuilder = (SComponentBuilder<?>) builder;
                componentBuilder.init(new ComponentDataRequest(modelMap, request));
                return componentBuilder;
            }
        }

        return null;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private static enum CRITERIA {
        TARGET("target"),
        ID("id");
        private final String key;

        CRITERIA(final String key) {
            this.key = key;
        }

    }
}
