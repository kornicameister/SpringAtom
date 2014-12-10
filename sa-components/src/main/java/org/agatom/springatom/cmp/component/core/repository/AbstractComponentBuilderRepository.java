package org.agatom.springatom.cmp.component.core.repository;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.*;
import org.agatom.springatom.cmp.component.core.builders.Builder;
import org.agatom.springatom.cmp.component.core.builders.ComponentDefinitionBuilder;
import org.agatom.springatom.cmp.component.core.builders.ComponentProduces;
import org.agatom.springatom.cmp.component.core.builders.annotation.EntityBased;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-10</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractComponentBuilderRepository
        implements ComponentBuilderRepository, BeanFactoryAware {
    protected ConfigurableListableBeanFactory beanFactory              = null;
    protected Map<DefinitionHolder, String>   definitionToBuilderIdMap = null;
    protected Map<DefinitionHolder, Builder>  definitionToBuilderMap   = null;

    /**
     * Returns {@link org.agatom.springatom.cmp.component.core.builders.ComponentProduces}
     * is {@code convert} is {@link org.agatom.springatom.cmp.component.core.builders.ComponentDefinitionBuilder}
     *
     * @param source {@link org.agatom.springatom.cmp.component.core.builders.Builder} convert
     *
     * @return {@link org.agatom.springatom.cmp.component.core.builders.ComponentProduces}
     */
    protected ComponentProduces getComponentProduces(final Builder source) {
        if (ClassUtils.isAssignableValue(ComponentDefinitionBuilder.class, source)) {
            return ((ComponentDefinitionBuilder<?>) source).getProduces();
        }
        return null;
    }

    /**
     * Returns the {@link Class} that {@link org.agatom.springatom.cmp.component.core.builders.ComponentDefinitionBuilder}
     * object is built by the class
     *
     * @param source {@link org.agatom.springatom.cmp.component.core.builders.Builder} convert
     *
     * @return the built class
     */
    protected Class<?> getComponentBuilds(final Builder source) {
        if (ClassUtils.isAssignableValue(ComponentDefinitionBuilder.class, source)) {
            return ((ComponentDefinitionBuilder<?>) source).getBuilds();
        }
        return null;
    }

    /**
     * Returns {@link org.agatom.springatom.cmp.component.core.builders.annotation.EntityBased#entity()} value, if
     * {@code builder} is designated to build data/definition for domain specific class
     *
     * @param builder to get based for entity
     *
     * @return the class or null, if builder has no {@link org.agatom.springatom.cmp.component.core.builders.annotation.EntityBased} annotation
     */
    protected Collection<Class<?>> getBasedForEntity(final Builder builder) {
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
        if (this.getBuilderMap().containsKey(componentId)) {
            return this.getBuilderMap().get(componentId);
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

    protected abstract Map<String, Builder> getBuilderMap();

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

    protected static class DefinitionHolder
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
