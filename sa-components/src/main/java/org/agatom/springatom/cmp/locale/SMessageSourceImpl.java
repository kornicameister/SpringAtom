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

package org.agatom.springatom.cmp.locale;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.cmp.locale.beans.*;
import org.agatom.springatom.core.util.LocalizationAware;
import org.agatom.springatom.core.util.Localized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.persistence.metamodel.Attribute;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <p>SMessageSourceImpl class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class SMessageSourceImpl
        extends ReloadableResourceBundleMessageSource
        implements SMessageSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(SMessageSourceImpl.class);

    private static class InternalLocalizedClassAttribute
            implements LocalizedClassAttribute {
        private static final long serialVersionUID = -9210749041074012095L;
        String  messageKey = null;
        boolean found      = false;
        String  name       = null;
        String  label      = null;

        InternalLocalizedClassAttribute(final String name, final String label, final String messageKey, final boolean found) {
            this.name = name;
            this.label = label;
            this.messageKey = messageKey;
            this.found = found;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getLabel() {
            return this.label;
        }

        @Override
        public boolean isFound() {
            return this.found;
        }

        @Override
        public String getMessageKey() {
            return this.messageKey;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name, label);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InternalLocalizedClassAttribute that = (InternalLocalizedClassAttribute) o;

            return Objects.equal(this.name, that.name) &&
                    Objects.equal(this.label, that.label);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("name", name)
                    .add("found", found)
                    .toString();
        }
    }

    private static class InternalLocalizedClassModel<T>
            implements LocalizedClassModel<T> {
        private static final long serialVersionUID = -9041229694358443850L;
        boolean                      found      = false;
        Class<T>                     source     = null;
        String                       name       = null;
        Set<LocalizedClassAttribute> attributes = null;
        transient Map<String, LocalizedClassAttribute> asMap = null;

        public InternalLocalizedClassModel(final Class<T> clazz, final String name, final boolean found) {
            this.source = clazz;
            this.name = name;
            this.found = found;
        }

        @Override
        public Class<T> getSource() {
            return source;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isFound() {
            return found;
        }

        @Override
        public Set<LocalizedClassAttribute> getAttributes() {
            return this.attributes;
        }

        @Override
        public String getLocalizedAttribute(final String attributeName) {
            final LocalizedClassAttribute attribute = this.asMap().get(attributeName);
            return attribute != null ? attribute.getLabel() : attributeName;
        }

        @Override
        public boolean hasAttributes() {
            return !CollectionUtils.isEmpty(this.attributes);
        }

        @Override
        public Map<String, LocalizedClassAttribute> asMap() {
            if (this.asMap == null) {
                final Map<String, LocalizedClassAttribute> map = Maps.newHashMapWithExpectedSize(this.attributes.size());
                for (final LocalizedClassAttribute key : this.attributes) {
                    map.put(key.getName(), key);
                }
                this.asMap = map;
            }
            return this.asMap;
        }
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("ConstantConditions")
    public <T> LocalizedClassModel<T> getLocalizedClassModel(final Class<T> clazz, final Locale locale) {
        LOGGER.trace(String.format("getLocalizedClassModel(clazz=%s,locale=%s", clazz, locale));
        final boolean isPersistable = ClassUtils.isAssignable(Persistable.class, clazz);

        String messageKey;
        Class<?> localClass = clazz;
        String localizedClassName;

        if (!isPersistable) {
            messageKey = clazz.getName();
            localizedClassName = this.getMessage(messageKey, locale);
            if (messageKey.equals(localizedClassName)) {
                LOGGER.warn(String.format("%s is not persistable hence no attribute lookup, but localized name was not found", clazz));
            }
            return new InternalLocalizedClassModel<>(clazz, localizedClassName, true);
        } else {
            do {
                messageKey = ClassUtils.getShortName(localClass).toLowerCase();
                localizedClassName = this.getMessage(messageKey, locale);
                if (localizedClassName.equals(messageKey)) {
                    LOGGER.trace(String.format("Traversing down from %s -> %s", ClassUtils.getShortName(localClass), ClassUtils.getShortName(localClass.getSuperclass())));
                    localClass = localClass.getSuperclass();
                }
                if (localClass.equals(Object.class)) {
                    LOGGER.warn(String.format("Failed to found localized class name for %s", clazz));
                    return new InternalLocalizedClassModel<>(clazz, localizedClassName, false);
                }
            } while (localizedClassName.equals(messageKey));
        }

        final Set<LocalizedClassAttribute> attributes = Sets.newHashSet();

        final Set<String> attributesToLocalize = Sets.newHashSet();
        final Object descriptor = null/*this.entityDescriptors.getDescriptor(clazz)*/;
        if (descriptor != null) {
            LOGGER.trace(String.format("%s has corresponding %s to read attributes from", ClassUtils.getShortName(clazz), ClassUtils.getShortName(Object.class)));
            final Set<Attribute<? super T, ?>> localAttributes = null;
            for (final Attribute<? super T, ?> attribute : localAttributes) {
                attributesToLocalize.add(attribute.getName().toLowerCase());
            }
        } else {
            LOGGER.trace(String.format("%s has not corresponding %s to read attributes from", ClassUtils.getShortName(clazz), ClassUtils.getShortName(Object.class)));
            final Field[] fields = clazz.getFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                attributesToLocalize.add(field.getName());
            }
        }

        // analyze getters
        final Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (!(name.startsWith("get") || name.startsWith("is"))) {
                continue;
            }
            if (name.startsWith("get")) {
                name = name.substring(3, name.length());
            } else {
                name = name.substring(2, name.length());
            }
            name = name.toLowerCase();
            attributesToLocalize.add(name);
        }


        if (!CollectionUtils.isEmpty(attributesToLocalize)) {
            LOGGER.trace(String.format("%s has %d attributes to localize", ClassUtils.getShortName(clazz), attributesToLocalize.size()));
            boolean found = false;

            for (final String attributeName : attributesToLocalize) {
                do {

                    final String localKey = String.format("%s.%s", ClassUtils.getShortName(localClass).toLowerCase(), attributeName.toLowerCase());
                    final String localMessage = this.getMessage(String.format(localKey), locale);

                    if (StringUtils.hasText(localMessage) && localMessage.equals(localKey)) {
                        localClass = localClass.getSuperclass();
                    } else {
                        attributes.add(new InternalLocalizedClassAttribute(attributeName, localMessage, localKey, true));
                        found = true;
                        break;
                    }

                    if (localClass.equals(Object.class)) {
                        LOGGER.warn(String.format("Failed to resolve localized value of attribute %s", attributeName));
                        attributes.add(new InternalLocalizedClassAttribute(attributeName, localMessage, null, false));
                        found = true;
                    }

                } while (!found);
                found = false;
                localClass = clazz;
            }

        } else {
            LOGGER.warn(String.format("Failed to resolve attributes to localize for clazz=%s", clazz));
        }

        final InternalLocalizedClassModel<T> localizedClass = new InternalLocalizedClassModel<>(clazz, localizedClassName, true);
        localizedClass.attributes = attributes;
        return localizedClass;
    }


    /** {@inheritDoc} */
    @Override
    public <T> LocalizedClassAttribute getLocalizedClassAttribute(final Class<T> clazz, final String attributeName, final Locale locale) {
        final LocalizedClassModel<T> message = this.getLocalizedClassModel(clazz, locale);
        if (message.hasAttributes()) {
            return message.asMap().get(attributeName);
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public <LA extends LocalizationAware> LA localize(final LA localizationAware, final Locale locale) {
        final String messageKey = localizationAware.getMessageKey();
        final String msg = this.getMessage(messageKey, locale);
        if (StringUtils.hasText(msg)) {
            localizationAware.setValueForMessageKey(msg);
        }
        return localizationAware;
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage(final Localized localized, final Locale locale) {
        return this.getMessage(localized.getMessageKey(), locale);
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage(final String key, final Locale locale) {
        return this.getMessage(key, (Object[]) null, locale);
    }

    @Override
    public String getMessage(final String key, final String defaultMsg, final Locale locale) {
        final String msg = this.getMessage(key, locale);
        if (!StringUtils.hasText(msg) || msg.equalsIgnoreCase(key)) {
            return defaultMsg;
        }
        return msg;
    }

    /** {@inheritDoc} */
    @Override
    public SLocalizedMessages getLocalizedMessages(final Locale locale) {
        final SLocalizedMessages preferences = new SLocalizedMessages();
        final Set<String> keys = this.getKeys(locale);

        for (final String key : keys) {
            preferences.put(key, this.getMessage(key, locale), locale);
        }

        return preferences;
    }

    /** {@inheritDoc} */
    @Override
    public SLocalizedMessages getLocalizedMessages(final String[] keys, final Locale locale, final boolean usePattern) {
        if (!usePattern) {
            return this.getLocalizedMessages(locale);
        }
        final Set<String> allKeys = this.getKeys(locale);
        final SLocalizedMessages messages = new SLocalizedMessages();

        for (final String key : keys) {
            final Pattern pattern = Pattern.compile(key, Pattern.CASE_INSENSITIVE);
            {
                final Set<String> filteredMsgKeys = FluentIterable
                        .from(allKeys)
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean apply(@Nullable final String input) {
                                assert input != null;
                                return pattern.matcher(input).matches();
                            }
                        })
                        .toSet();
                for (final String msgKey : filteredMsgKeys) {
                    messages.put(this.getLocalizedMessage(msgKey, locale));
                }
            }
        }

        return messages;
    }

    /** {@inheritDoc} */
    @Override
    public SLocalizedMessage getLocalizedMessage(final String key, final Locale locale) {
        return new SLocalizedMessage()
                .setKey(key)
                .setMessage(this.getMessage(key, locale))
                .setLocale(SLocale.fromLocale(locale));
    }

    /**
     * Returns all the keys from all given resource bundles ({@link org.springframework.context.support.ReloadableResourceBundleMessageSource#setBasenames(String...)}
     *
     * @param locale locale
     *
     * @return set of keys
     */
    private Set<String> getKeys(final Locale locale) {
        return this.getMergedProperties(locale).getProperties().stringPropertyNames();
    }


}
