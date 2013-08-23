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

package org.agatom.springatom.web.js;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;
import org.agatom.springatom.web.js.meta.JSMetaModel;
import org.agatom.springatom.web.js.resolvers.JSModelResolver;
import org.agatom.springatom.web.js.resolvers.JSSimpleModelResolver;
import org.agatom.springatom.web.js.storage.JSModelStorage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class JSModelLoader
        implements InitializingBean {
    private static final Logger LOGGER = Logger.getLogger(JSModelLoader.class);
    private String                        basePackage;
    private Frontend                      frontend;
    private JSModelResolver               resolver;
    private Map<ExclusionPattern, String> exclusions;

    @Override
    @SuppressWarnings("ConstantConditions")
    public void afterPropertiesSet() {

        if (this.resolver == null) {
            LOGGER.trace(String
                    .format("Resolver not set, fallback to default=%s", JSSimpleModelResolver.class.getSimpleName()));
            this.resolver = new JSSimpleModelResolver();
        }

        if (this.exclusions == null) {
            this.exclusions = Maps.newHashMap();
        }

        final Set<ClassPath.ClassInfo> scannedClasses = this.doScanPackage();
        Assert.isTrue(scannedClasses != null);

        final Set<Class<?>> loadedClasses = this.loadClasses(scannedClasses);
        Assert.isTrue(loadedClasses.size() == scannedClasses.size());

        this.resolveModels(loadedClasses);
    }

    protected void resolveModels(final Set<? extends Class<?>> loadedClasses) {
        for (final Class<?> aClass : loadedClasses) {
            this.resolveModel(aClass);
        }
    }

    public JSMetaModel resolveModel(final Class<?> aClass) {
        if (JSModelStorage.hasModelForClass(aClass)) {
            return null;
        }
        final JSMetaModel metaModel = this.resolver.resolveModel(aClass);
        if (metaModel != null) {
            LOGGER.trace(String
                    .format("Resolved from clazz=%s to model=%s", aClass.getSimpleName(), metaModel
                            .getModelName()));
            return JSModelStorage.storeModel(metaModel);
        }
        return metaModel;
    }

    protected Set<Class<?>> loadClasses(final Set<ClassPath.ClassInfo> scannedClasses) {
        final Set<Class<?>> classes = Sets.newHashSet();
        for (final ClassPath.ClassInfo classInfo : scannedClasses) {
            classes.add(classInfo.load());
            LOGGER.trace(String
                    .format("Loaded clazz=%s from package=%s", classInfo.getName(), classInfo.getPackageName()));
        }
        return classes;
    }

    protected Set<ClassPath.ClassInfo> doScanPackage() {
        try {
            final ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
            final ImmutableSet<ClassPath.ClassInfo> infos = classPath.getTopLevelClassesRecursive(this.basePackage);
            if (this.exclusions == null) {
                return infos;
            } else {
                return this.buildFluent(FluentIterable.from(infos)).toSet();
            }
        } catch (IOException e) {
            LOGGER.error(String.format("Failed to load from package=%s", this.basePackage), e);
        }
        return null;
    }

    protected FluentIterable<ClassPath.ClassInfo> buildFluent(FluentIterable<ClassPath.ClassInfo> fluent) {
        for (final Map.Entry<ExclusionPattern, String> entry : this.exclusions.entrySet()) {
            switch (entry.getKey()) {
                case MODIFIER:
                    fluent = fluent.filter(new Predicate<ClassPath.ClassInfo>() {
                        @Override
                        public boolean apply(@Nullable final ClassPath.ClassInfo input) {
                            assert input != null;
                            switch (entry.getValue()) {
                                case "final":
                                    return !Modifier.isFinal(input.getClass().getModifiers());
                            }
                            return true;
                        }
                    });
                    break;
                case ANNOTATION:
                    fluent = fluent.filter(new Predicate<ClassPath.ClassInfo>() {
                        @Override
                        public boolean apply(@Nullable final ClassPath.ClassInfo input) {
                            assert input != null;
                            for (final Annotation annotation : input.getClass().getAnnotations()) {
                                if (annotation.getClass().getName().equals(entry.getValue())) {
                                    return false;
                                }
                            }
                            return true;
                        }
                    });
                    break;
                case PACKAGE:
                    fluent = fluent.filter(new Predicate<ClassPath.ClassInfo>() {
                        @Override
                        public boolean apply(@Nullable final ClassPath.ClassInfo input) {
                            assert input != null;
                            final String packageName = input.getPackageName();
                            return !entry.getValue().equals(packageName);
                        }
                    });
                    break;
                case CLASS:
                    fluent = fluent.filter(new Predicate<ClassPath.ClassInfo>() {
                        @Override
                        public boolean apply(@Nullable final ClassPath.ClassInfo input) {
                            assert input != null;
                            final String clazzName = input.getSimpleName();
                            return !entry.getValue().equals(clazzName);
                        }
                    });
                    break;
                case PATTERN:
                    fluent = fluent.filter(new Predicate<ClassPath.ClassInfo>() {
                        @Override
                        public boolean apply(@Nullable final ClassPath.ClassInfo input) {
                            assert input != null;
                            final String clazzName = input.getSimpleName();
                            return !clazzName.matches(entry.getValue());
                        }
                    });
                    break;
            }
        }
        return fluent.filter(new Predicate<ClassPath.ClassInfo>() {
            @Override
            public boolean apply(@Nullable final ClassPath.ClassInfo input) {
                // default exclusions
                assert input != null;
                final Class<? extends ClassPath.ClassInfo> aClass = input.getClass();
                final int modifiers = aClass.getModifiers();
                if (aClass.isSynthetic()) {
                    return false;
                } else if (aClass.isAnnotation()) {
                    return false;
                } else if (aClass.isPrimitive()) {
                    return false;
                } else if (aClass.isLocalClass()) {
                    return false;
                } else if (Modifier.isInterface(modifiers)) {
                    return false;
                } else if (Modifier.isAbstract(modifiers)) {
                    return false;
                }
                return true;
            }
        });
    }

    public void setResolver(final JSModelResolver resolver) {
        this.resolver = resolver == null ? new JSSimpleModelResolver() : resolver;
    }

    @Required
    public void setFrontend(final Frontend frontend) {
        this.frontend = frontend;
    }

    @Required
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setExclusions(final Map<ExclusionPattern, String> exclusions) {
        this.exclusions = exclusions;
    }

    public static enum ExclusionPattern {
        CLASS,
        PATTERN,
        PACKAGE,
        ANNOTATION,
        MODIFIER
    }
}
