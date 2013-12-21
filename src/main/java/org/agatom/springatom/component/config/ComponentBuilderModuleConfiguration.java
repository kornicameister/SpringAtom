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

import org.agatom.springatom.component.builders.ComponentBuilders;
import org.agatom.springatom.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.component.helper.impl.DefaultComponentHelper;
import org.agatom.springatom.component.helper.impl.DefaultTableComponentHelper;
import org.agatom.springatom.core.module.AbstractModuleConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration(value = ComponentBuilderModuleConfiguration.MODULE_NAME)
@ComponentScan(
        nameGenerator = ComponentBuilderModuleConfiguration.NameGen.class,
        basePackages = {
                ComponentBuilderModuleConfiguration.PACKAGE
        },
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(value = ComponentBuilds.class, type = FilterType.ANNOTATION)
)
public class ComponentBuilderModuleConfiguration
        extends AbstractModuleConfiguration {
    protected static final String MODULE_NAME = "ComponentBuilders";
    protected static final String PACKAGE     = "org.agatom.springatom";
    private static final   Logger LOGGER      = Logger.getLogger(ComponentBuilderModuleConfiguration.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BeanFactory        beanFactory;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ComponentBuilderPostProcessor getComponentBuilderPostProcessor() {
        this.logRegistering(ComponentBuilderPostProcessor.class, LOGGER);
        return new ComponentBuilderPostProcessor();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ComponentBuilders getComponentBuilders() {
        this.logRegistering(ComponentBuilders.class, LOGGER);
        final ComponentBuilderRepository builders = new ComponentBuilderRepository();
        builders.setApplicationContext(this.applicationContext);
        builders.setBeanFactory(this.beanFactory);
        return builders;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ComponentBuilderProvider getComponentProviders() {
        this.logRegistering(ComponentBuilderProvider.class, LOGGER);
        return new ComponentBuilderProvider();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultComponentHelper getDefaultComponentHelper() {
        this.logRegistering(DefaultComponentHelper.class, LOGGER);
        return new DefaultComponentHelper();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultTableComponentHelper getDefaultTableComponentHelper() {
        this.logRegistering(DefaultTableComponentHelper.class, LOGGER);
        return new DefaultTableComponentHelper();
    }

    public static class NameGen
            extends AnnotationBeanNameGenerator {

        private static final String  ANNOTATION_TYPE        = ComponentBuilds.class.getName();
        private static final boolean CLASS_VALUES_AS_STRING = false;

        @Override
        protected String determineBeanNameFromAnnotation(final AnnotatedBeanDefinition annotatedDef) {
            final AnnotationMetadata metadata = annotatedDef.getMetadata();
            if (metadata.hasAnnotation(ANNOTATION_TYPE)) {
                final Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ANNOTATION_TYPE, CLASS_VALUES_AS_STRING);
                return (String) annotationAttributes.get("id");
            }
            return super.determineBeanNameFromAnnotation(annotatedDef);
        }
    }

}
