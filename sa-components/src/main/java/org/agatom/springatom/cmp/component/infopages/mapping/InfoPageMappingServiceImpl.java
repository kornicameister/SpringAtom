/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.cmp.component.infopages.mapping;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import org.agatom.springatom.cmp.component.infopages.InfoPageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * {@code InfoPageMappingServiceImpl} implements {@link org.agatom.springatom.cmp.component.infopages.mapping.InfoPageMappingService}
 * in order to support retrieving information about registered {@link org.agatom.springatom.cmp.component.infopages.provider.structure.InfoPage}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 18.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@Component
@Role(BeanDefinition.ROLE_SUPPORT)
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Description("InfoPageMappingService gives access to verify if an InfoPage exists for certain information (rel,class,object)")
class InfoPageMappingServiceImpl
        implements InfoPageMappingService {
    private static final Logger                                       LOGGER             = LoggerFactory.getLogger(InfoPageMappingServiceImpl.class);
    private static final String                                       MAPPING_PROPERTIES = "infoPageProperties";
    private static final String                                       REGISTER_PREFIX    = "springatom.infoPages.register.";
    private              Map<String, Class<? extends Persistable<?>>> relToClassMap      = Maps.newHashMapWithExpectedSize(5);
    @Autowired
    @Qualifier(MAPPING_PROPERTIES)
    private              Properties                                   properties         = null;

    /**
     * Loads all registered {@link org.agatom.springatom.cmp.component.infopages.provider.structure.InfoPage} from {@code infoPageProperties}
     */
    @PostConstruct
    @SuppressWarnings("unchecked")
    private void postConstruct() {
        LOGGER.debug(String.format("Analyzing %s", MAPPING_PROPERTIES));
        final long startTime = System.nanoTime();
        {

            final Set<String> propertyNames = properties.stringPropertyNames();
            final ClassLoader classLoader = this.getClass().getClassLoader();

            for (final String prop : propertyNames) {
                if (prop.startsWith(REGISTER_PREFIX)) {

                    final String rel = prop.replaceFirst(REGISTER_PREFIX, "").trim();
                    final Class<?> clazz = ClassUtils.resolveClassName(this.properties.getProperty(prop), classLoader);

                    this.relToClassMap.put(rel, (Class<? extends Persistable<?>>) ClassUtils.getUserClass(clazz));

                    LOGGER.trace(String.format("Hit in register, key = %s // clazz = %s", prop, ClassUtils.getShortName(clazz)));
                }
            }

        }
        final long analyzeTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        LOGGER.debug(String.format("Analyzed %s in %d ms", MAPPING_PROPERTIES, analyzeTime));
    }

    @Override
    public Collection<InfoPageMapping> getInfoPageMappings() {
        return FluentIterable.from(this.relToClassMap.keySet())
                .transform(new Function<String, InfoPageMapping>() {
                    @Nullable
                    @Override
                    public InfoPageMapping apply(@Nullable final String rel) {
                        return new InfoPageMapping().setRel(rel).setType(relToClassMap.get(rel));
                    }
                })
                .toSet();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasInfoPage(final String rel) {
        return this.relToClassMap.containsKey(rel);
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings({"UnusedDeclaration", "unchecked"})
    public <T extends Persistable<?>> boolean hasInfoPage(final Class<T> persistableClass) {
        final Class<T> cleanedClass = (Class<T>) ClassUtils.getUserClass(persistableClass);
        return FluentIterable.from(this.relToClassMap.values()).firstMatch(new Predicate<Class<?>>() {
            @Override
            public boolean apply(@Nullable final Class<?> input) {
                return ClassUtils.isAssignable(cleanedClass, input);
            }
        }).isPresent();
    }

    /** {@inheritDoc} */
    @Override
    public <T extends Persistable<?>> boolean hasInfoPage(final T persistable) {
        return this.hasInfoPage(persistable.getClass());
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Persistable<?>> Class<T> getMappedClass(final String rel) throws InfoPageNotFoundException {
        if (!this.hasInfoPage(rel)) {
            throw new InfoPageNotFoundException(rel);
        }
        return (Class<T>) this.relToClassMap.get(rel);
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Persistable<?>> String getMappedRel(final Class<T> clazz) throws InfoPageNotFoundException {
        final Class<T> cleanedClazz = (Class<T>) ClassUtils.getUserClass(clazz);
        if (!this.hasInfoPage(cleanedClazz)) {
            throw new InfoPageNotFoundException(clazz);
        }
        return FluentIterable.from(this.relToClassMap.keySet()).firstMatch(new Predicate<String>() {
            @Override
            public boolean apply(@Nullable final String input) {
                return input != null && relToClassMap.get(input).equals(cleanedClazz);
            }
        }).get();
    }


}
