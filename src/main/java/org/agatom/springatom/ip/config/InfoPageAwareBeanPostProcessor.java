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

package org.agatom.springatom.ip.config;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

class InfoPageAwareBeanPostProcessor
        extends InstantiationAwareBeanPostProcessorAdapter
        implements BeanFactoryAware {
    private static final Logger LOGGER = Logger.getLogger(InfoPageAwareBeanPostProcessor.class);
    private ConfigurableListableBeanFactory context;
    private String                          basePackage;

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.context = (ConfigurableListableBeanFactory) beanFactory;
        }
    }

    public void setBasePackage(final String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, final PropertyDescriptor[] pds, final Object bean, final String beanName) throws
            BeansException {
        if (this.isInPackage(bean.getClass().getName())) {
            LOGGER.trace(String.format("/postProcessPropertyValues for bean => %s", beanName));
            pvs = this.postProcessOverAnnotation(pvs, pds, beanName);
        }
        return pvs;
    }

    private PropertyValues postProcessOverAnnotation(final PropertyValues pvs, final PropertyDescriptor[] pds, final String beanName) {
        final MutablePropertyValues values = new MutablePropertyValues(pvs);
        for (final PropertyDescriptor pd : pds) {
            final String pdName = pd.getName();
            final Object propertyValue = this.resolveValueFromAnnotation(pdName, beanName);
            if (propertyValue != null) {
                values.addPropertyValue(pdName, propertyValue);
            }
        }
        return values;
    }

    private Object resolveValueFromAnnotation(final String pdName, final String beanName) {
        LOGGER.trace(String.format("/resolveValueFromAnnotation \n\tproperty=>%s\n\tbean=>%s", pdName, beanName));

        final BeanDefinition beanDefinition = this.context.getBeanDefinition(beanName);
        AnnotationMetadata metadata = null;
        Object value = null;

        if (beanDefinition instanceof ScannedGenericBeanDefinition) {
            final ScannedGenericBeanDefinition definition = (ScannedGenericBeanDefinition) beanDefinition;
            metadata = definition.getMetadata();
        }
        if (metadata != null) {
            final Set<String> annotationTypes = metadata.getAnnotationTypes();
            for (final String annotationType : annotationTypes) {
                final Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(annotationType);
                value = annotationAttributes.get(pdName);
                if (value != null) {
                    break;
                }
            }
        }
        return value;
    }

    private boolean isInPackage(final String name) {
        return name != null && name.contains(this.basePackage);
    }
}
