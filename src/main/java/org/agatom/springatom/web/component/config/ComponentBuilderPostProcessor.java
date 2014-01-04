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

package org.agatom.springatom.web.component.config;

import org.agatom.springatom.core.processors.AbstractAnnotationBeanPostProcessorAdapter;
import org.agatom.springatom.web.component.builders.ComponentBuilder;
import org.agatom.springatom.web.component.builders.EntityAware;
import org.agatom.springatom.web.component.builders.annotation.EntityBased;
import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.support.Repositories;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class ComponentBuilderPostProcessor
        extends AbstractAnnotationBeanPostProcessorAdapter {

    private static final Logger       LOGGER          = Logger.getLogger(ComponentBuilderPostProcessor.class);
    private static final String       ENTITY_KEY      = "entity";
    private static final String       ENTITY_REPO_KEY = "repository";
    private              Repositories repositories    = null;

    @PostConstruct
    private void initRepositories() {
        this.repositories = new Repositories(this.contextFactory);
        LOGGER.trace(String.format("initRepositories -> %s", this.repositories));
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected boolean isProcessable(final Object bean) {
        return bean instanceof ComponentBuilder;
    }

    @Override
    public PropertyValues postProcessOverAnnotation(final PropertyValues pvs, final PropertyDescriptor[] pds, final String beanName) {
        try {
            final MutablePropertyValues values = new MutablePropertyValues(pvs);
            final ScannedGenericBeanDefinition definition = (ScannedGenericBeanDefinition) this.contextFactory.getBeanDefinition(beanName);

            final Class<?> beanClass = Class.forName(definition.getBeanClassName());
            final Set<Class> interfacesForClassAsSet = ClassUtils.getAllInterfacesForClassAsSet(beanClass);

            if (interfacesForClassAsSet.contains(EntityAware.class) && beanClass.isAnnotationPresent(EntityBased.class)) {
                final Map<String, Object> attributes = AnnotationUtils.getAnnotationAttributes(beanClass.getAnnotation(EntityBased.class));

                if (attributes.containsKey(ENTITY_KEY)) {
                    final Class<?> entityClass = (Class<?>) attributes.get(ENTITY_KEY);
                    final Object repositoryFor = this.repositories.getRepositoryFor(entityClass);

                    if (repositoryFor != null) {
                        values.add(ENTITY_KEY, entityClass);
                        values.add(ENTITY_REPO_KEY, repositoryFor);

                        LOGGER.trace(String.format("%s is %s, hence added properties [%s]",
                                beanName,
                                ClassUtils.getShortName(EntityBased.class),
                                Arrays.toString(new String[]{ENTITY_KEY, ENTITY_REPO_KEY}))
                        );

                    } else {
                        throw new BeanInitializationException(
                                String.format("%s is %s but there is no matching %s for %s",
                                        beanName,
                                        ClassUtils.getShortName(EntityBased.class),
                                        ClassUtils.getShortName(RepositoryDefinition.class),
                                        ClassUtils.getShortName(entityClass)
                                )
                        );
                    }

                }

            } else {
                LOGGER.warn(String
                        .format("%s in not applicable for processing in context of %s", beanName, ClassUtils.getShortName(EntityBased.class)));
            }

            return super.postProcessOverAnnotation(values, pds, beanName);
        } catch (Exception e) {
            LOGGER.trace(e);
        }
        return super.postProcessOverAnnotation(pvs, pds, beanName);
    }
}
