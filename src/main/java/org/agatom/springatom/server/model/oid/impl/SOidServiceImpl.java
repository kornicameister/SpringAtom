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

package org.agatom.springatom.server.model.oid.impl;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import org.agatom.springatom.server.model.oid.SOid;
import org.agatom.springatom.server.model.oid.SOidCreator;
import org.agatom.springatom.server.model.oid.SOidService;
import org.agatom.springatom.server.model.oid.annotation.OidProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.springframework.core.GenericTypeResolver.resolveTypeArgument;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-17</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service("oidService")
@Role(BeanDefinition.ROLE_SUPPORT)
@Scope(BeanDefinition.SCOPE_SINGLETON)
class SOidServiceImpl
        implements SOidService {

    @Autowired
    private Collection<SOidCreator<?>> oidCreators = null;
    private Map<OidCreatorKey, SOidCreator<?>> oidCreatorsMap;

    @PostConstruct
    @SuppressWarnings("ConstantConditions")
    private void readOidCreators() {
        Assert.notEmpty(oidCreators);
        final Map<OidCreatorKey, SOidCreator<?>> localMap = Maps.newHashMapWithExpectedSize(this.oidCreators.size());
        for (final SOidCreator<?> oidCreator : oidCreators) {

            final Class<?> supportedType = resolveTypeArgument(oidCreator.getClass(), SOidCreator.class);
            final String prefix = findAnnotation(oidCreator.getClass(), OidProvider.class).prefix();

            localMap.put(new OidCreatorKey().setPrefix(prefix).setSupportedClass(supportedType), oidCreator);
        }
        this.oidCreatorsMap = Collections.unmodifiableMap(localMap);
    }

    @Override
    public SOid getOid(@Nonnull final String oid) throws Exception {
        final SOidCreator creator = this.getCreator(oid.split(":")[0]);
        return creator != null ? creator.fromString(oid) : null;
    }

    @Override
    public SOid getOid(@Nonnull final Object obj) throws Exception {
        final SOidCreator creator = this.getCreator(ClassUtils.getUserClass(obj));
        return creator != null ? creator.fromObject(obj) : null;
    }

    private SOidCreator<?> getCreator(final Class<?> clazz) {
        final Optional<OidCreatorKey> match = FluentIterable
                .from(this.oidCreatorsMap.keySet())
                .firstMatch(new Predicate<OidCreatorKey>() {
                    @Override
                    public boolean apply(@Nullable final OidCreatorKey input) {
                        assert input != null;
                        final Class<?> supportedType = input.supportedClass;
                        return ClassUtils.isAssignable(supportedType, clazz) || ClassUtils.determineCommonAncestor(clazz, supportedType) != null;
                    }
                });
        if (match.isPresent()) {
            final OidCreatorKey key = match.get();
            return this.oidCreatorsMap.get(key);
        }
        return null;
    }

    private SOidCreator<?> getCreator(final String prefix) {
        final Optional<OidCreatorKey> match = FluentIterable
                .from(this.oidCreatorsMap.keySet())
                .firstMatch(new Predicate<OidCreatorKey>() {
                    @Override
                    public boolean apply(@Nullable final OidCreatorKey input) {
                        assert input != null;
                        return input.prefix.equalsIgnoreCase(prefix);
                    }
                });
        if (match.isPresent()) {
            final OidCreatorKey key = match.get();
            return this.oidCreatorsMap.get(key);
        }
        return null;
    }

    private static final class OidCreatorKey {
        private String   prefix         = null;
        private Class<?> supportedClass = null;

        public OidCreatorKey setPrefix(final String prefix) {
            this.prefix = prefix;
            return this;
        }

        public OidCreatorKey setSupportedClass(final Class<?> supportedClass) {
            this.supportedClass = ClassUtils.getUserClass(supportedClass);
            return this;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(prefix, supportedClass);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OidCreatorKey that = (OidCreatorKey) o;

            return Objects.equal(this.prefix, that.prefix) &&
                    Objects.equal(this.supportedClass, that.supportedClass);
        }
    }
}
