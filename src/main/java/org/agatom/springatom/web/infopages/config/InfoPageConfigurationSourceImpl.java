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

package org.agatom.springatom.web.infopages.config;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.infopages.SInfoPage;
import org.agatom.springatom.web.infopages.annotation.DomainInfoPage;
import org.agatom.springatom.web.infopages.annotation.InfoPage;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import javax.annotation.Nullable;
import java.beans.Introspector;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class InfoPageConfigurationSourceImpl
        implements InfoPageConfigurationSource {
    private static final Logger            LOGGER                      = Logger.getLogger(InfoPageConfigurationSource.class);
    private static final List<String>      ANNOTATION_TYPES            = Lists.newArrayList(
            InfoPage.class.getName(),
            DomainInfoPage.class.getName()
    );
    private static final Predicate<String> ANNOTATION_LOOKUP_PREDICATE = new Predicate<String>() {
        @Override
        public boolean apply(@Nullable final String input) {
            return ANNOTATION_TYPES.contains(input);
        }
    };
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
    public SInfoPage getFromDomain(final Class<?> domainClass) {
        LOGGER.debug(String.format("/getFromDomain => %s", domainClass));
        return this.getInternal(domainClass.getName(), CRITERIA.DOMAIN);
    }

    @Override
    public SInfoPage getFromPath(final String path) {
        LOGGER.debug(String.format("/getFromPath => %s", path));
        return this.getInternal(path, CRITERIA.PATH);
    }

    @Override
    public SInfoPage getFromRel(final String rel) {
        LOGGER.debug(String.format("/getFromRel => %s", rel));
        return this.getInternal(rel, CRITERIA.REL);
    }

    private SInfoPage getInternal(final String query, final CRITERIA criteria) {
        final Set<BeanDefinition> components = this.getBeanDefinitionsFailFast();
        final Optional<BeanDefinition> beanDefinitionOptional = FluentIterable
                .from(components)
                .firstMatch(new Predicate<BeanDefinition>() {
                    @Override
                    public boolean apply(@Nullable final BeanDefinition input) {
                        if (input != null && input instanceof ScannedGenericBeanDefinition) {
                            final ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) input;
                            final AnnotationMetadata metadata = beanDefinition.getMetadata();

                            final Optional<String> annotationPresent = FluentIterable.from(metadata.getAnnotationTypes())
                                                                                     .firstMatch(ANNOTATION_LOOKUP_PREDICATE);
                            if (!annotationPresent.isPresent()) {
                                throw new RuntimeException(String
                                        .format("No %s annotation found over bean %s", ANNOTATION_TYPES, input.getBeanClassName()));
                            }

                            final Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(annotationPresent.get(), true);
                            final String pathValue = (String) annotationAttributes.get(criteria.key);

                            return pathValue != null && pathValue.equals(query);
                        }
                        return false;
                    }
                });

        LOGGER.trace(String.format("Resolved possible definition under => %s", beanDefinitionOptional));
        if (beanDefinitionOptional.isPresent()) {
            final BeanDefinition definition = beanDefinitionOptional.get();
            if (definition != null) {
                LOGGER.trace(String.format("Found definition for %s", query));
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

    private SInfoPage resolveInfoPage(final BeanDefinition definition) {
        final String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return (SInfoPage) this.applicationContext.getBean(Introspector.decapitalize(shortClassName));
    }

    private static enum CRITERIA {
        DOMAIN("domain"),
        REL("rel"),
        PATH("path");
        private final String key;

        CRITERIA(final String key) {
            this.key = key;
        }
    }
}
