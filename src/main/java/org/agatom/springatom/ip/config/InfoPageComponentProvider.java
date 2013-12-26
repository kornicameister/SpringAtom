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
import org.agatom.springatom.core.filters.AllTypeFilter;
import org.agatom.springatom.core.filters.InterfaceTypeFilter;
import org.agatom.springatom.ip.SEntityInfoPage;
import org.agatom.springatom.ip.SInfoPage;
import org.agatom.springatom.ip.annotation.DomainInfoPage;
import org.agatom.springatom.ip.annotation.InfoPage;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.repository.util.ClassUtils;

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
        super.addIncludeFilter(new InterfaceTypeFilter(SInfoPage.class));
        super.addIncludeFilter(new InterfaceTypeFilter(SEntityInfoPage.class));
        super.addIncludeFilter(new AnnotationTypeFilter(InfoPage.class, true, true));
        super.addIncludeFilter(new AnnotationTypeFilter(DomainInfoPage.class, true, true));
    }

    @Override
    public void addIncludeFilter(final TypeFilter includeFilter) {
        super.addIncludeFilter(new AllTypeFilter(Lists.newArrayList(
                includeFilter,
                new InterfaceTypeFilter(SInfoPage.class),
                new InterfaceTypeFilter(SEntityInfoPage.class)
        )));
        super.addIncludeFilter(new AllTypeFilter(Lists.newArrayList(
                includeFilter,
                new AnnotationTypeFilter(InfoPage.class),
                new AnnotationTypeFilter(DomainInfoPage.class)
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

}
