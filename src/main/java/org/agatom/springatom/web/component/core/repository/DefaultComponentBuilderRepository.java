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
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.core.builders.Builder;
import org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder;
import org.agatom.springatom.web.component.core.builders.ComponentProduces;
import org.agatom.springatom.web.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.web.component.core.builders.annotation.EntityBased;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.List;
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
		implements ComponentBuilderRepository, ApplicationContextAware, BeanFactoryAware {
	private static final Logger                          LOGGER                   = Logger.getLogger(ComponentBuilderRepository.class);
	@Autowired
	private              List<Builder>                   builderList              = null;
	private              ApplicationContext              context                  = null;
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
				final Object beanClassName = definition.getBeanClassName();

				final Builder builder = FluentIterable.from(this.builderList).firstMatch(new Predicate<Builder>() {
					@Override
					public boolean apply(@Nullable final Builder input) {
						return input != null && input.getClass().getName().equals(beanClassName);
					}
				}).get();

				if (!definition.isAbstract()) {

					final ComponentProduces producesType = this.getComponentProduces(builder);
					final Class<?> builds = this.getComponentBuilds(builder);
					final Class<?> basedForEntity = this.getBasedForEntity(builder);

					final DefinitionHolder holder = new DefinitionHolder(builds, producesType, basedForEntity);

					this.definitionToBuilderIdMap.put(holder, beanName);
					this.definitionToBuilderMap.put(holder, builder);

					LOGGER.trace(String.format("/readBuilders -> %s", holder));
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
	private Class<?> getBasedForEntity(final Builder builder) {
		final EntityBased annotation = AnnotationUtils.findAnnotation(builder.getClass(), EntityBased.class);
		if (annotation != null) {
			return (Class<?>) AnnotationUtils.getValue(annotation, "entity");
		}
		return null;
	}

	@Override
	public Builder getBuilder(final String componentId) {
		return (Builder) this.context.getBean(componentId);
	}

	@Override
	public boolean hasBuilder(final Class<?> target) {
		return this.hasBuilder(target, ComponentProduces.TABLE_COMPONENT);
	}

	@Override
	public boolean hasBuilder(final Class<?> target, final ComponentProduces produces) {
		return this.getBuilderId(target, produces) != null;
	}

	@Override
	public String getBuilderId(final Class<?> target) {
		return this.getBuilderId(target, ComponentProduces.TABLE_COMPONENT);
	}

	@Override
	public String getBuilderId(final Class<?> target, final ComponentProduces produces) {
		final Optional<DefinitionHolder> match = FluentIterable.from(this.definitionToBuilderMap.keySet()).firstMatch(new Predicate<DefinitionHolder>() {
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
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	private static class DefinitionHolder
			implements Comparable<DefinitionHolder> {

		private final Class<?>          builds;
		private final ComponentProduces produces;
		private final Class<?>          basedForEntity;

		public DefinitionHolder(final Class<?> builds, final ComponentProduces produces, final Class<?> basedForEntity) {
			this.builds = builds;
			this.produces = produces;
			this.basedForEntity = basedForEntity;
		}

		@Override
		public int compareTo(@Nonnull final DefinitionHolder o) {
			return ComparisonChain.start().compare(this.builds.getName(), o.builds.getName()).compare(this.produces, o.produces).result();
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(builds, produces, basedForEntity);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			DefinitionHolder that = (DefinitionHolder) o;

			return Objects.equal(this.builds, that.builds) &&
					Objects.equal(this.produces, that.produces) &&
					Objects.equal(this.basedForEntity, that.basedForEntity);
		}

		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.addValue(builds)
					.addValue(produces)
					.addValue(basedForEntity)
					.toString();
		}

		public boolean canBuiltForEntity(final Class<?> target, final ComponentProduces produces) {
			return this.produces.equals(produces) && (this.basedForEntity != null && ClassUtils.isAssignable(this.basedForEntity, target));
		}
	}
}
