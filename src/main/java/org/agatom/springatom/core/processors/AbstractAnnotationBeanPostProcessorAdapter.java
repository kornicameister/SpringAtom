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

package org.agatom.springatom.core.processors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
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
// TODO implement AnnotationBeanUtils !!!!
public abstract class AbstractAnnotationBeanPostProcessorAdapter
        extends InstantiationAwareBeanPostProcessorAdapter
        implements AnnotationBeanPostProcessor,
                   InitializingBean {

    protected Logger                          logger;
    protected ConfigurableListableBeanFactory contextFactory;

    @Override
    public final void afterPropertiesSet() throws Exception {
        this.logger = this.getLogger();
        this.initProcessor();
    }

    protected void initProcessor() {
    }

    protected abstract Logger getLogger();

    protected abstract boolean isProcessable(Object bean);

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.contextFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public final PropertyValues postProcessPropertyValues(PropertyValues pvs, final PropertyDescriptor[] pds, final Object bean, final String beanName) throws
            BeansException {
        if (this.isProcessable(bean)) {
            logger.debug(String.format("/postProcessPropertyValues for bean => %s", beanName));
            pvs = this.postProcessOverAnnotation(pvs, pds, beanName);
            logger.debug(String.format("/postProcessPropertyValues for bean finished => %s", beanName));
        }
        return pvs;
    }

    @Override
    public PropertyValues postProcessOverAnnotation(final PropertyValues pvs, final PropertyDescriptor[] pds, final String beanName) {
        MutablePropertyValues values = new MutablePropertyValues(pvs);
        for (final PropertyDescriptor pd : pds) {
            final String pdName = pd.getName();
            final Object propertyValue = this.resolveValueFromAnnotation(pdName, beanName);
            if (propertyValue != null) {
                values = values.add(pdName, propertyValue);
            }
        }
        return values;
    }

    @Override
    public Object resolveValueFromAnnotation(final String pdName, final String beanName) {
        logger.trace(String.format("/resolveValueFromAnnotation \n\tproperty=>%s\n\tbean=>%s", pdName, beanName));

        final BeanDefinition beanDefinition = this.contextFactory.getBeanDefinition(beanName);
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
}
