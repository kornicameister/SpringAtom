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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.agatom.springatom.ip.DomainInfoPage;
import org.agatom.springatom.ip.DomainInfoPageResource;
import org.agatom.springatom.ip.InfoPage;
import org.agatom.springatom.ip.InfoPageResource;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.repository.util.ClassUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class InfoPageComponentProvider
        extends ClassPathScanningCandidateComponentProvider {
    private Map<String, Set<BeanDefinition>> cache = Maps.newHashMap();

    public InfoPageComponentProvider() {
        super(false);
        super.addIncludeFilter(new InterfaceTypeFilter(InfoPage.class));
        super.addIncludeFilter(new InterfaceTypeFilter(DomainInfoPage.class));
        super.addIncludeFilter(new AnnotationTypeFilter(InfoPageResource.class, true, true));
        super.addIncludeFilter(new AnnotationTypeFilter(DomainInfoPageResource.class, true, true));
    }

    @Override
    public void addIncludeFilter(final TypeFilter includeFilter) {
        super.addIncludeFilter(new AllTypeFilter(Lists.newArrayList(
                includeFilter,
                new InterfaceTypeFilter(InfoPage.class),
                new InterfaceTypeFilter(DomainInfoPage.class)
        )));
        super.addIncludeFilter(new AllTypeFilter(Lists.newArrayList(
                includeFilter,
                new AnnotationTypeFilter(InfoPageResource.class),
                new AnnotationTypeFilter(DomainInfoPageResource.class)
        )));
    }

    @Override
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        if (this.cache.containsKey(basePackage)) {
            return this.cache.get(basePackage);
        }
        final Set<BeanDefinition> candidates = super.findCandidateComponents(basePackage);
        for (final BeanDefinition candidate : candidates) {
            if (candidate instanceof AnnotatedBeanDefinition) {
                AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
            }
        }
        if (candidates.size() > 0) {
            this.cache.put(basePackage, candidates);
        }
        return candidates;
    }

    @Override
    protected boolean isCandidateComponent(final AnnotatedBeanDefinition beanDefinition) {
        final boolean isNonRepositoryInterface = !ClassUtils.isGenericRepositoryInterface(beanDefinition.getBeanClassName());
        final boolean isTopLevelType = !beanDefinition.getMetadata().hasEnclosingClass();
        return isNonRepositoryInterface && isTopLevelType;
    }

    private static class InterfaceTypeFilter
            extends AssignableTypeFilter {
        public InterfaceTypeFilter(final Class<?> targetType) {
            super(targetType);
        }

        @Override
        public boolean match(
                final MetadataReader metadataReader,
                final MetadataReaderFactory metadataReaderFactory) throws IOException {
            return metadataReader.getClassMetadata().isInterface()
                    && super.match(metadataReader, metadataReaderFactory);
        }
    }

    private static class AllTypeFilter
            implements TypeFilter {

        private final List<TypeFilter> delegates;

        public AllTypeFilter(final List<TypeFilter> delegates) {
            Assert.notNull(delegates);
            this.delegates = delegates;
        }

        public boolean match(
                final MetadataReader metadataReader,
                final MetadataReaderFactory metadataReaderFactory) throws IOException {
            for (final TypeFilter filter : delegates) {
                if (!filter.match(metadataReader, metadataReaderFactory)) {
                    return false;
                }
            }
            return true;
        }
    }
}
