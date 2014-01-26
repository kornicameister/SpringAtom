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

package org.agatom.springatom.server.model.descriptors.descriptor;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.descriptors.*;
import org.agatom.springatom.server.model.descriptors.association.SimpleEntityAssociation;
import org.agatom.springatom.server.model.descriptors.properties.ManyToOnePropertyDescriptor;
import org.agatom.springatom.server.model.descriptors.properties.OneToManyPropertyDescriptor;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class EntityDescriptors {
    private EntityDescriptorReader             reader           = null;
    private Cache<Class<?>, EntityAssociation> associationCache = null;

    @PostConstruct
    private void initialize() {
        this.associationCache = CacheBuilder.<Class<?>, EntityAssociation>newBuilder()
                                            .maximumSize(666)
                                            .expireAfterAccess(60, TimeUnit.MINUTES)
                                            .expireAfterWrite(60, TimeUnit.MINUTES)
                                            .build();
        Assert.notNull(this.associationCache);
    }

    public void setReader(final EntityDescriptorReader reader) {
        this.reader = reader;
    }

    public EntityDescriptor<?> getDescriptor(final String className) {
        final String cleanedClassName = this.cleanClassName(className);
        if (!ClassUtils.isPresent(className, this.getClass().getClassLoader())) {
            final Optional<EntityDescriptor<?>> optional = FluentIterable
                    .from(this.reader.getDefinitions())
                    .firstMatch(new Predicate<EntityDescriptor<?>>() {
                        @Override
                        public boolean apply(@Nullable final EntityDescriptor<?> input) {
                            assert input != null;
                            return input.getJavaClass().getName().equals(cleanedClassName);
                        }
                    });
            if (optional.isPresent()) {
                return optional.get();
            } else {
                return null;
            }
        }
        return this.getDescriptor(ClassUtils.resolveClassName(className, this.getClass().getClassLoader()));
    }

    public <X> EntityDescriptor<X> getDescriptor(final Class<X> xClass) {
        return this.reader.getDefinition(xClass, false);
    }

    public Set<EntityDescriptor<?>> getDescriptors() {
        return this.reader.getDefinitions();
    }

    public Set<SlimEntityDescriptor<?>> getSlimDescriptors() {
        final Set<EntityDescriptor<?>> descriptors = this.getDescriptors();
        return FluentIterable
                .from(descriptors)
                .transform(new Function<EntityDescriptor<?>, SlimEntityDescriptor<?>>() {
                    @Nullable
                    @Override
                    public SlimEntityDescriptor<?> apply(@Nullable final EntityDescriptor<?> input) {
                        assert input != null;
                        return new SlimEntityTypeDescriptor<>(input.getName(), input.getJavaClass());
                    }
                })
                .toSet();
    }

    public SlimEntityDescriptor<?> getSlimDescriptor(final String className) {

        final String cleanedClassName = this.cleanClassName(className);
        final ClassLoader classLoader = this.getClass().getClassLoader();

        if (ClassUtils.isPresent(cleanedClassName, classLoader)) {
            final Class<?> resolvedClass = ClassUtils.resolveClassName(cleanedClassName, classLoader);
            return this.getSlimDescriptor(resolvedClass);
        } else {
            return null;
        }
    }

    public <X> SlimEntityDescriptor<X> getSlimDescriptor(final Class<X> javaClass) {
        final EntityDescriptor<X> entityDescriptor = this.getDescriptor(javaClass);
        return new SlimEntityTypeDescriptor<>(entityDescriptor.getName(), entityDescriptor.getJavaClass());
    }

    public String getEntityName(final Class<?> xClass) {
        return this.reader.getDefinition(xClass, false).getEntityType().getName();
    }

    public <X> Set<EntityDescriptorColumn<X>> getColumns(final Class<X> javaClass) {
        final EntityDescriptor<X> descriptor = this.getDescriptor(javaClass);
        final Set<Attribute<? super X, ?>> attributes = descriptor.getEntityType().getAttributes();
        final Set<EntityDescriptorColumn<X>> columns = Sets.newLinkedHashSet();
        for (final Attribute<? super X, ?> attribute : attributes) {
            if (ClassUtils.isAssignable(PluralAttribute.class, attribute.getClass())) {
                final PluralAttribute pluralAttribute = (PluralAttribute) attribute;
                columns.add(new EntityTypeDescriptorCollectionColumn<X>().setElementClass(pluralAttribute.getElementType().getJavaType())
                                                                         .setName(attribute.getName())
                                                                         .setColumnClass(attribute.getJavaType())
                                                                         .setEntityDescriptor(descriptor));
            } else {
                columns.add(new EntityTypeDescriptorColumn<X>().setName(attribute.getName())
                                                               .setColumnClass(attribute.getJavaType())
                                                               .setEntityDescriptor(descriptor));
            }
        }
        return columns;
    }

    public Map<Class<?>, EntityAssociation> getAssociations() {
        for (SlimEntityDescriptor<?> descriptor : this.getSlimDescriptors()) {
            final Class<?> javaClass = descriptor.getJavaClass();
            this.getAssociation(javaClass);
        }
        return this.associationCache.asMap();
    }

    public EntityAssociation getAssociation(final Class<?> clazz) {
        final EntityAssociation entityAssociation = this.associationCache.getIfPresent(clazz);
        if (entityAssociation == null) {
            this.associationCache.invalidate(clazz);
            return this.doGetAssociation(clazz);
        }
        return entityAssociation;
    }

    private EntityAssociation doGetAssociation(final Class<?> clazz) {
        final EntityDescriptor<?> descriptor = this.getDescriptor(clazz);
        final SimpleEntityAssociation association = new SimpleEntityAssociation();

        association.setMaster(descriptor);
        for (final ManyToOnePropertyDescriptor propertyDescriptor : descriptor.getManyToOneProperties()) {
            association.addAssociation(this.getDescriptor(propertyDescriptor.getJavaType()));
        }
        for (final OneToManyPropertyDescriptor propertyDescriptor : descriptor.getOneToManyProperties()) {
            association.addAssociation(this.getDescriptor(propertyDescriptor.getJavaType()));
        }

        this.associationCache.put(clazz, association);

        return association;
    }

    private String cleanClassName(String className) {
        if (className.startsWith("class")) {
            final String[] classes = className.split("class");
            className = classes[1].trim();
        }
        return StringUtils.trimAllWhitespace(className);
    }
}
