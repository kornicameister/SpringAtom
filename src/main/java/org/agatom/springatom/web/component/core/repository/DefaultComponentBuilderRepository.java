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

package org.agatom.springatom.web.component.core.repository;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.*;
import org.agatom.springatom.web.component.core.builders.Builder;
import org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder;
import org.agatom.springatom.web.component.core.builders.ComponentProduces;
import org.agatom.springatom.web.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.web.component.core.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.core.builders.multi.MultiComponentBuilder;
import org.agatom.springatom.web.component.core.builders.multi.MultiComponentDescriptor;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * {@code DefaultComponentBuilderRepository} is an implementation of {@link org.agatom.springatom.web.component.core.repository.ComponentBuilderRepository}.
 * Reads all existing builder definition to support direct access for the application according to the {@link org.agatom.springatom.web.component.core.repository.ComponentBuilderRepository}
 * contract
 *
 * @author kornicameister
 * @version 0.0.3
 * @since 0.0.1
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
class DefaultComponentBuilderRepository
        implements ComponentBuilderRepository, BeanFactoryAware {
    private static final Logger                          LOGGER                   = Logger.getLogger(ComponentBuilderRepository.class);
    @Autowired
    private              Map<String, Builder>            builderMap               = null;
    private              ConfigurableListableBeanFactory beanFactory              = null;
    private              Map<DefinitionHolder, String>   definitionToBuilderIdMap = null;
    private              Map<DefinitionHolder, Builder>  definitionToBuilderMap   = null;

    @PostConstruct
    private void readBuilders() {
        final String[] beanNamesForType = this.beanFactory.getBeanNamesForAnnotation(ComponentBuilder.class);
        if (beanNamesForType.length > 0) {

            this.definitionToBuilderIdMap = Maps.newHashMap();
            this.definitionToBuilderMap = Maps.newHashMap();

            for (final String beanName : beanNamesForType) {
                final ScannedGenericBeanDefinition definition = (ScannedGenericBeanDefinition) this.beanFactory.getBeanDefinition(beanName);

                final Builder builder = this.builderMap.get(beanName);

                if (!definition.isAbstract()) {

                    final ComponentProduces producesType = this.getComponentProduces(builder);
                    final Class<?> builds = this.getComponentBuilds(builder);

                    if (ClassUtils.isAssignableValue(MultiComponentBuilder.class, builder)) {

                        final MultiComponentBuilder multiComponentBuilder = (MultiComponentBuilder) builder;
                        final Collection<MultiComponentDescriptor> descriptors = multiComponentBuilder.getDescriptors();

                        for (MultiComponentDescriptor descriptor : descriptors) {
                            final String alias = descriptor.getAlias();
                            final Collection<Class<?>> supportedClasses = descriptor.getSupportedClasses();

                            DefinitionHolder holder = new DefinitionHolder(builds, producesType, supportedClasses, alias);

                            this.definitionToBuilderIdMap.put(holder, alias);
                            this.definitionToBuilderMap.put(holder, builder);
                        }

                    } else {
                        final Collection<Class<?>> basedForEntity = this.getBasedForEntity(builder);
                        DefinitionHolder holder = new DefinitionHolder(builds, producesType, basedForEntity);
                        this.definitionToBuilderIdMap.put(holder, beanName);
                        this.definitionToBuilderMap.put(holder, builder);
                    }
                }
            }
        }
    }

    /**
     * Returns {@link org.agatom.springatom.web.component.core.builders.ComponentProduces}
     * is {@code convert} is {@link org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder}
     *
     * @param source {@link org.agatom.springatom.web.component.core.builders.Builder} convert
     *
     * @return {@link org.agatom.springatom.web.component.core.builders.ComponentProduces}
     */
    private ComponentProduces getComponentProduces(final Builder source) {
        if (ClassUtils.isAssignableValue(ComponentDefinitionBuilder.class, source)) {
            return ((ComponentDefinitionBuilder<?>) source).getProduces();
        }
        return null;
    }

    /**
     * Returns the {@link java.lang.Class} that {@link org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder}
     * object is built by the class
     *
     * @param source {@link org.agatom.springatom.web.component.core.builders.Builder} convert
     *
     * @return the built class
     */
    private Class<?> getComponentBuilds(final Builder source) {
        if (ClassUtils.isAssignableValue(ComponentDefinitionBuilder.class, source)) {
            return ((ComponentDefinitionBuilder<?>) source).getBuilds();
        }
        return null;
    }

    /**
     * Returns {@link org.agatom.springatom.web.component.core.builders.annotation.EntityBased#entity()} value, if
     * {@code builder} is designated to build data/definition for domain specific class
     *
     * @param builder to get based for entity
     *
     * @return the class or null, if builder has no {@link org.agatom.springatom.web.component.core.builders.annotation.EntityBased} annotation
     */
    private Collection<Class<?>> getBasedForEntity(final Builder builder) {
        final EntityBased annotation = AnnotationUtils.findAnnotation(builder.getClass(), EntityBased.class);
        final Collection<Class<?>> entitiesList = Sets.newHashSet();
        if (annotation != null) {
            final Class<?> entity = (Class<?>) AnnotationUtils.getValue(annotation, "entity");

            if (entity != null && !entity.equals(Void.class)) {
                entitiesList.add(entity);
            }

            final Class<?>[] entities = (Class<?>[]) AnnotationUtils.getValue(annotation, "entities");
            if (entities != null && entities.length > 0) {
                entitiesList.addAll(Arrays.asList(entities));
            }
        }
        return entitiesList;
    }

    /** {@inheritDoc} */
    @Override
    public Builder getBuilder(final String componentId) {
        if (this.builderMap.containsKey(componentId)) {
            return this.builderMap.get(componentId);
        }
        // not found, might be alias request
        final Optional<DefinitionHolder> match = FluentIterable.from(this.definitionToBuilderIdMap.keySet())
                .filter(new Predicate<DefinitionHolder>() {
                    @Override
                    public boolean apply(@Nullable final DefinitionHolder input) {
                        return input != null && StringUtils.hasText(input.alias);
                    }
                })
                .firstMatch(new Predicate<DefinitionHolder>() {
                    @Override
                    public boolean apply(@Nullable final DefinitionHolder input) {
                        return definitionToBuilderIdMap.get(input).equals(componentId);
                    }
                });
        if (match.isPresent()) {
            final DefinitionHolder holder = match.get();
            return this.definitionToBuilderMap.get(holder);
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasBuilder(final Class<?> target) {
        return this.hasBuilder(target, ComponentProduces.TABLE_COMPONENT);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasBuilder(final Class<?> target, final ComponentProduces produces) {
        return this.getBuilderId(target, produces) != null;
    }

    /** {@inheritDoc} */
    @Override
    public String getBuilderId(final Class<?> target) {
        return this.getBuilderId(target, ComponentProduces.TABLE_COMPONENT);
    }

    /** {@inheritDoc} */
    @Override
    public String getBuilderId(final Class<?> target, final ComponentProduces produces) {
        final Optional<DefinitionHolder> match = FluentIterable
                .from(this.definitionToBuilderMap.keySet()).firstMatch(new Predicate<DefinitionHolder>() {
                    @Override
                    public boolean apply(@Nullable final DefinitionHolder input) {
                        return input != null && input.canBuiltForEntity(target, produces);
                    }
                });
        if (match.isPresent()) {
            final DefinitionHolder definitionHolder = match.get();
            return this.definitionToBuilderIdMap.get(definitionHolder);
        }
        return null;
    }

    @Override
    public Multimap<String, ComponentMetaData> getAllBuilders() {
        final Map<DefinitionHolder, String> normal = this.definitionToBuilderIdMap;
        final Map<String, Collection<ComponentMetaData>> map = Maps.newHashMap();
        final Multimap<String, ComponentMetaData> reversed = Multimaps.newMultimap(map, new Supplier<Collection<ComponentMetaData>>() {
            @Override
            public Collection<ComponentMetaData> get() {
                return Sets.newHashSet();
            }
        });
        for (final DefinitionHolder holder : normal.keySet()) {
            reversed.put(normal.get(holder), holder);
        }
        return reversed;
    }

    /** {@inheritDoc} */
    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    private static class DefinitionHolder
            implements Comparable<DefinitionHolder>, ComponentMetaData {

        protected final Class<?>             builds;
        protected final ComponentProduces    produces;
        protected final Collection<Class<?>> basedForEntity;
        protected final String               alias;

        public DefinitionHolder(final Class<?> builds, final ComponentProduces produces, final Collection<Class<?>> basedForEntity) {
            this(builds, produces, basedForEntity, null);
        }

        public DefinitionHolder(final Class<?> builds, final ComponentProduces produces, final Collection<Class<?>> basedForEntity, final String alias) {
            this.alias = alias;
            this.builds = builds;
            this.produces = produces;
            this.basedForEntity = basedForEntity;
        }

        @Override
        public String getAlias() {
            return alias;
        }

        @Override
        public Class<?> getBuilds() {
            return builds;
        }

        @Override
        public ComponentProduces getProduces() {
            return produces;
        }

        @Override
        public Collection<Class<?>> getBasedForEntity() {
            return basedForEntity;
        }

        public boolean canBuiltForEntity(Class<?> target, final ComponentProduces produces) {
            final Class<?> localTarget = ClassUtils.getUserClass(target);
            return this.produces.equals(produces)
                    && (this.basedForEntity != null
                    && !FluentIterable
                    .from(this.basedForEntity)
                    .filter(new Predicate<Class<?>>() {
                        @Override
                        public boolean apply(@Nullable Class<?> input) {
                            if (input != null) {
                                input = ClassUtils.getUserClass(input);
                            }
                            return input != null && ClassUtils.isAssignable(input, localTarget);
                        }
                    })
                    .isEmpty()
            );
        }

        @Override
        public int compareTo(@Nonnull final DefinitionHolder o) {
            return ComparisonChain
                    .start()
                    .compare(this.builds.getName(), o.builds.getName())
                    .compare(this.produces, o.produces)
                    .compare(this.alias, o.alias)
                    .result();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(builds, produces, basedForEntity, alias);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DefinitionHolder that = (DefinitionHolder) o;

            return Objects.equal(this.builds, that.builds) &&
                    Objects.equal(this.produces, that.produces) &&
                    Objects.equal(this.basedForEntity, that.basedForEntity) &&
                    Objects.equal(this.alias, that.alias);
        }
    }
}
