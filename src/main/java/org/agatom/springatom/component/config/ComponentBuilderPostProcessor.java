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

import org.agatom.springatom.component.builders.SComponentBuilder;
import org.agatom.springatom.component.builders.annotation.ComponentBuilder;
import org.agatom.springatom.core.processors.AbstractAnnotationBeanPostProcessorAdapter;
import org.agatom.springatom.ip.SDomainInfoPage;
import org.agatom.springatom.ip.component.builder.DomainInfoPageComponentBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class ComponentBuilderPostProcessor
        extends AbstractAnnotationBeanPostProcessorAdapter {

    private static final Logger LOGGER = Logger.getLogger(ComponentBuilderPostProcessor.class);

    @Override
    protected boolean isProcessable(final Object bean) {
        return bean instanceof SComponentBuilder;
    }

    @Override
    public PropertyValues postProcessOverAnnotation(final PropertyValues pvs, final PropertyDescriptor[] pds, final String beanName) {
        try {
            final MutablePropertyValues values = new MutablePropertyValues(pvs);
            final ScannedGenericBeanDefinition definition = (ScannedGenericBeanDefinition) this.contextFactory.getBeanDefinition(beanName);

            final Class<?> beanClass = Class.forName(definition.getBeanClassName());
            if (ClassUtils.isAssignable(DomainInfoPageComponentBuilder.class, beanClass)) {
                final AnnotationMetadata metadata = definition.getMetadata();
                if (metadata != null) {
                    final Map<String, Object> attributes = metadata.getAnnotationAttributes(ComponentBuilder.class.getName());
                    final Class<?> target = (Class<?>) attributes.get("target");
                    final Object targetBean = this.contextFactory.getBean(target);
                    if (targetBean instanceof SDomainInfoPage) {
                        values.add("domainClass", ((SDomainInfoPage) targetBean).getDomain());
                    }
                }
            }

            return super.postProcessOverAnnotation(values, pds, beanName);
        } catch (Exception e) {
            LOGGER.trace(e);
        }
        return this.postProcessOverAnnotation(pvs, pds, beanName);
    }
}
