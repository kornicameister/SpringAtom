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

package org.agatom.springatom.cmp.component.core.repository;

import com.google.common.collect.Maps;
import org.agatom.springatom.cmp.component.core.builders.Builder;
import org.agatom.springatom.cmp.component.core.builders.ComponentProduces;
import org.agatom.springatom.cmp.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.cmp.component.core.builders.multi.MultiComponentBuilder;
import org.agatom.springatom.cmp.component.core.builders.multi.MultiComponentDescriptor;
import org.agatom.springatom.core.annotations.LazyComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

/**
 * {@code DefaultComponentBuilderRepository} is an implementation of {@link org.agatom.springatom.cmp.component.core.repository.ComponentBuilderRepository}.
 * Reads all existing builder definition to support direct access for the application according to the {@link org.agatom.springatom.cmp.component.core.repository.ComponentBuilderRepository}
 * contract
 *
 * @author kornicameister
 * @version 0.0.3
 * @since 0.0.1
 */
@LazyComponent
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
class DefaultComponentBuilderRepository
        extends AbstractComponentBuilderRepository {
    @Autowired
    private Map<String, Builder> builderMap = null;

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


    @Override
    protected Map<String, Builder> getBuilderMap() {
        return this.builderMap;
    }
}
