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
import com.google.common.base.Predicate;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.core.builders.Builder;
import org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder;
import org.agatom.springatom.web.component.core.builders.ComponentProduces;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.annotation.Scope;
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
 * @version 0.0.2
 * @since 0.0.1
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
class DefaultComponentBuilderRepository
		implements ComponentBuilderRepository, ApplicationContextAware, BeanFactoryAware {
	private static final Logger                          LOGGER      = Logger.getLogger(ComponentBuilderRepository.class);
	@Autowired
	private              List<Builder>                   builderList = null;
	private              ApplicationContext              context     = null;
	private              ConfigurableListableBeanFactory beanFactory = null;
	private              Map<String, DefinitionHolder>   holderMap   = null;

	@PostConstruct
	private void readBuilders() {
		final String[] beanNamesForType = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(this.context, Builder.class, true, false);
		if (beanNamesForType.length > 0) {

			this.holderMap = Maps.newHashMap();

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

					final DefinitionHolder holder = new DefinitionHolder(builds, producesType);

					this.holderMap.put(beanName, holder);

					LOGGER.trace(String.format("/readBuilders -> %s", holder));
				}
			}
		}
	}

	/**
	 * Returns {@link org.agatom.springatom.web.component.core.builders.ComponentProduces}
	 * is {@code source} is {@link org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder}
	 *
	 * @param source {@link org.agatom.springatom.web.component.core.builders.Builder} source
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
	 * @param source {@link org.agatom.springatom.web.component.core.builders.Builder} source
	 *
	 * @return the built class
	 */
	private Class<?> getComponentBuilds(final Builder source) {
		if (ClassUtils.isAssignableValue(ComponentDefinitionBuilder.class, source)) {
			return ((ComponentDefinitionBuilder<?>) source).getBuilds();
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
		for (final String componentId : this.holderMap.keySet()) {
			if (this.holderMap.get(componentId).equals(target, produces)) {
				return componentId;
			}
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

	private static enum CRITERIA {
		TARGET("builds"),
		PRODUCES("produces"),
		ID("id");
		private final String key;

		CRITERIA(final String key) {
			this.key = key;
		}

	}

	private static class DefinitionHolder
			implements Comparable<DefinitionHolder> {

		private final Class<?>          builds;
		private final ComponentProduces produces;

		public DefinitionHolder(final Class<?> builds, final ComponentProduces produces) {
			this.builds = builds;
			this.produces = produces;
		}

		public boolean equals(final Class<?> target, final ComponentProduces produces) {
			return this.builds.equals(target) && this.produces.equals(produces);
		}

		@Override
		public int compareTo(@Nonnull final DefinitionHolder o) {
			return ComparisonChain.start().compare(this.builds.getName(), o.builds.getName()).compare(this.produces, o.produces).result();
		}

		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.addValue(builds)
					.addValue(produces)
					.toString();
		}
	}
}
