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

package org.agatom.springatom.server.model.descriptors.descriptor;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.SlimEntityDescriptor;
import org.agatom.springatom.server.model.descriptors.reader.EntityDescriptorReader;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class EntityDescriptors {
    private EntityDescriptorReader reader;

    public void setReader(final EntityDescriptorReader reader) {
        this.reader = reader;
    }

    public EntityDescriptor<?> getEntityDescriptor(String className) {
        if (className.startsWith("class")) {
            final String[] classes = className.split("class");
            className = classes[1].trim();
        }
        final String cleanedClassName = StringUtils.trimAllWhitespace(className);
        if (!ClassUtils.isPresent(className, this.getClass().getClassLoader())) {
            final Optional<EntityDescriptor<?>> optional = FluentIterable
                    .from(this.getDescriptors(true))
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
                // TODO add exception
                return null;
            }
        }
        return this.getEntityDescriptor(ClassUtils.resolveClassName(className, this.getClass().getClassLoader()));
    }

    public SlimEntityDescriptor<?> getSlimEntityDescriptor(final String className) {
        final EntityDescriptor<?> entityDescriptor = this.getEntityDescriptor(className);
        return new SlimEntityTypeDescriptor<>(entityDescriptor.getName(), entityDescriptor.getJavaClass());
    }

    public <X> EntityDescriptor<X> getEntityDescriptor(final Class<X> xClass) {
        return this.reader.getDefinition(xClass, false);
    }

    public String getEntityName(final Class<?> xClass) {
        return this.reader.getDefinition(xClass, false).getEntityType().getName();
    }

    public Set<EntityDescriptor<?>> getDescriptors(final boolean includeSuperclasses) {
        final Set<EntityDescriptor<?>> definitions = this.reader.getDefinitions();
        for (final EntityDescriptor<?> descriptor : definitions) {
            if (descriptor.isAbstract() && !includeSuperclasses) {
                definitions.remove(descriptor);
            }
        }
        return definitions;
    }

    public Set<SlimEntityDescriptor<?>> getSlimDescriptors(final boolean includeSuperclasses) {
        final Set<EntityDescriptor<?>> descriptors = this.getDescriptors(includeSuperclasses);
        return FluentIterable.from(descriptors)
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
}
