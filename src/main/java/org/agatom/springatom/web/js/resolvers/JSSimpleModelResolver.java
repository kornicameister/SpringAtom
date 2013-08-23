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

package org.agatom.springatom.web.js.resolvers;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.agatom.springatom.web.js.meta.JSMetaModel;
import org.agatom.springatom.web.js.meta.JSMetaModelAssociation;
import org.apache.log4j.Logger;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class JSSimpleModelResolver
        implements JSModelResolver {
    private static final Logger LOGGER = Logger.getLogger(JSSimpleModelResolver.class);

    @Override
    public JSMetaModel resolveModel(final Class<?> aClass) {
        LOGGER.trace(String.format("Resolving from clazz=%s", aClass));

        final Field[] fields = this.collectFields(aClass, new Field[0]);
        final String clazzName = this.collectModelName(aClass);
        final String superClazzName = this.collectSuperModelName(aClass.getSuperclass());
        final Set<JSMetaModelAssociation> associations = this.collectAssociations(fields);
        final Set<String> jsFields = this.transformFields(fields, associations);

        return new JSMetaModel().setModelName(clazzName)
                                .setSuperModelName(superClazzName)
                                .setBean(aClass)
                                .setFields(jsFields)
                                .setAssociations(associations);
    }

    protected Set<String> transformFields(final Field[] fields, final Set<JSMetaModelAssociation> associations) {
        return null;
    }

    protected Set<JSMetaModelAssociation> collectAssociations(final Field[] fields) {
        for (final Field field : fields) {
            if (this.isOneToMany(field)) {
                final OneToMany oneToMany = field.getAnnotation(OneToMany.class);
            } else if (this.isManyToOne(field)) {
                final ManyToOne oneToMany = field.getAnnotation(ManyToOne.class);
            }
        }
        return null;
    }

    private boolean isManyToOne(final Field field) {
        return field.getAnnotation(ManyToOne.class) != null;
    }

    protected boolean isOneToMany(final Field field) {
        return field.getAnnotation(OneToMany.class) != null;
    }

    private String collectSuperModelName(final Class<?> aClass) {
        final Entity annotation = aClass.getAnnotation(Entity.class);
        return annotation != null ? annotation.name() : aClass.getSimpleName();
    }

    private String collectModelName(final Class<?> aClass) {
        final Entity annotation = aClass.getAnnotation(Entity.class);
        return annotation != null ? annotation.name() : aClass.getSimpleName();
    }

    private Field[] collectFields(Class<?> aClass, Field[] fields) {
        if (this.isObjectClazz(aClass)) {
            return fields;
        } else {
            final List<Field> fieldList = Lists.newArrayList(fields);
            final List<Field> newFieldList = Lists.newArrayList(aClass.getDeclaredFields());
            fieldList.addAll(Lists.newArrayList(
                    FluentIterable
                            .from(newFieldList)
                            .filter(new Predicate<Field>() {
                                @Override
                                public boolean apply(@Nullable final Field input) {
                                    assert input != null;
                                    final int modifiers = input.getModifiers();
                                    if (Modifier.isStatic(modifiers)) {
                                        return false;
                                    } else if (Modifier.isTransient(modifiers) || input
                                            .getAnnotation(Transient.class) != null || input
                                            .getAnnotation(java.beans.Transient.class) != null) {
                                        return false;
                                    }
                                    return true;
                                }
                            })
                            .toList()
            ));
            return this.collectFields(aClass.getSuperclass(), fieldList.toArray(new Field[fieldList.size()]));
        }
    }

    private boolean isObjectClazz(final Class<?> aClass) {
        return aClass.equals(Object.class);
    }
}
