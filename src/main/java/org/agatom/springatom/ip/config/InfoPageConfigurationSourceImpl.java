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

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.ip.DomainInfoPageResource;
import org.agatom.springatom.ip.InfoPage;
import org.agatom.springatom.ip.InfoPageResource;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import javax.annotation.Nullable;
import java.beans.Introspector;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class InfoPageConfigurationSourceImpl
        implements InfoPageConfigurationSource {
    public static final  String DOMAIN_KEY = "domainClass";
    public static final  String PATH_KEY   = "path";
    private static final Logger LOGGER     = Logger.getLogger(InfoPageConfigurationSource.class);
    private InfoPageComponentProvider provider;
    private String                    basePackage;
    private ApplicationContext        applicationContext;

    public InfoPageConfigurationSourceImpl setProvider(final InfoPageComponentProvider provider) {
        LOGGER.trace(String.format("/setProvider => %s", provider));
        this.provider = provider;
        return this;
    }

    public InfoPageConfigurationSourceImpl setBasePackage(final String basePackage) {
        LOGGER.trace(String.format("/setBasePackage => %s", basePackage));
        this.basePackage = basePackage;
        return this;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public InfoPage getFromDomain(final Class<?> domainClass) {
        LOGGER.debug(String.format("/getFromDomain => %s", domainClass));
        final Set<BeanDefinition> components = this.getBeanDefinitionsFailFast();
        final String annotationType = DomainInfoPageResource.class.getName();
        final Optional<BeanDefinition> beanDefinitionOptional = FluentIterable
                .from(components)
                .firstMatch(new Predicate<BeanDefinition>() {
                    @Override
                    public boolean apply(@Nullable final BeanDefinition input) {
                        if (input != null && input instanceof ScannedGenericBeanDefinition) {
                            final ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) input;
                            final AnnotationMetadata metadata = beanDefinition.getMetadata();

                            if (!metadata.getAnnotationTypes().contains(annotationType)) {
                                throw new RuntimeException(String
                                        .format("No %s annotation found over bean %s", annotationType, input.getBeanClassName()));
                            }

                            final Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(annotationType);
                            final Class<?> domainClassName = (Class<?>) annotationAttributes.get(DOMAIN_KEY);

                            return domainClassName != null && domainClassName.equals(domainClass);
                        }
                        return false;
                    }
                });

        LOGGER.trace(String.format("Resolved possible definition under => %s", beanDefinitionOptional));
        if (beanDefinitionOptional.isPresent()) {
            final BeanDefinition definition = beanDefinitionOptional.get();
            if (definition != null) {
                LOGGER.trace(String.format("Found definition for %s", domainClass));
                return this.resolveInfoPage(definition);
            }
        }

        return null;
    }

    @Override
    public Set<Class<?>> getAllInfoPageClasses() {
        final Set<BeanDefinition> components = this.getBeanDefinitionsFailFast();
        final Set<Class<?>> classes = Sets.newHashSetWithExpectedSize(components.size());
        for (final BeanDefinition definition : components) {
            try {
                classes.add(Class.forName(definition.getBeanClassName()));
            } catch (ClassNotFoundException cnfe) {
                LOGGER.error(String.format("Failed to find class for name %s", definition.getBeanClassName()), cnfe);
            }
        }
        return classes;
    }

    @Override
    public InfoPage getFromPath(final String path) {
        LOGGER.debug(String.format("/getFromPath => %s", path));
        final Set<BeanDefinition> components = this.getBeanDefinitionsFailFast();
        final String annotationType = InfoPageResource.class.getName();
        final Optional<BeanDefinition> beanDefinitionOptional = FluentIterable
                .from(components)
                .firstMatch(new Predicate<BeanDefinition>() {
                    @Override
                    public boolean apply(@Nullable final BeanDefinition input) {
                        if (input != null && input instanceof ScannedGenericBeanDefinition) {
                            final ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) input;
                            final AnnotationMetadata metadata = beanDefinition.getMetadata();

                            if (!metadata.getAnnotationTypes().contains(annotationType)) {
                                throw new RuntimeException(String
                                        .format("No %s annotation found over bean %s", annotationType, input.getBeanClassName()));
                            }

                            final Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(annotationType);
                            final String pathValue = (String) annotationAttributes.get(PATH_KEY);

                            return pathValue != null && pathValue.equals(path);
                        }
                        return false;
                    }
                });

        LOGGER.trace(String.format("Resolved possible definition under => %s", beanDefinitionOptional));
        if (beanDefinitionOptional.isPresent()) {
            final BeanDefinition definition = beanDefinitionOptional.get();
            if (definition != null) {
                LOGGER.trace(String.format("Found definition for %s", path));
                return this.resolveInfoPage(definition);
            }
        }

        return null;
    }

    private Set<BeanDefinition> getBeanDefinitionsFailFast() {
        final Set<BeanDefinition> components = this.provider.findCandidateComponents(this.basePackage);
        if (components.size() == 0) {
            throw new RuntimeException(String.format("No beans found under %s", this.basePackage));
        }
        return components;
    }

    private InfoPage resolveInfoPage(final BeanDefinition definition) {
        final String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return (InfoPage) this.applicationContext.getBean(Introspector.decapitalize(shortClassName));
    }
}
