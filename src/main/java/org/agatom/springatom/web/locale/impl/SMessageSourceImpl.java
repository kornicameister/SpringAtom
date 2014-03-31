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

package org.agatom.springatom.web.locale.impl;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.core.util.LocalizationAware;
import org.agatom.springatom.core.util.Localized;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.locale.beans.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.persistence.metamodel.Attribute;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SMessageSourceImpl
		extends ReloadableResourceBundleMessageSource
		implements SMessageSource {
	private static final Logger            LOGGER            = Logger.getLogger(SMessageSourceImpl.class);
	@Autowired
	private              EntityDescriptors entityDescriptors = null;

	@Override
	@SuppressWarnings("ConstantConditions")
	public <T> LocalizedClassModel<T> getMessage(final Class<T> clazz, final Locale locale) {
		LOGGER.trace(String.format("getMessage(clazz=%s,locale=%s", clazz, locale));
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

		final List<String> attributesToLocalize = Lists.newLinkedList();
		final EntityDescriptor<T> descriptor = this.entityDescriptors.getDescriptor(clazz);
		if (descriptor != null) {
			LOGGER.trace(String.format("%s has corresponding %s to read attributes from", ClassUtils.getShortName(clazz), ClassUtils.getShortName(EntityDescriptor.class)));
			final Set<Attribute<? super T, ?>> localAttributes = descriptor.getEntityType().getAttributes();
			for (final Attribute<? super T, ?> attribute : localAttributes) {
				attributesToLocalize.add(attribute.getName());
			}
		} else {
			LOGGER.trace(String.format("%s has not corresponding %s to read attributes from", ClassUtils.getShortName(clazz), ClassUtils.getShortName(EntityDescriptor.class)));
			final Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				attributesToLocalize.add(field.getName());
			}
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

	@Override
	public <LA extends LocalizationAware> LA localize(final LA localizationAware, final Locale locale) {
		final String messageKey = localizationAware.getMessageKey();
		final String msg = this.getMessage(messageKey, locale);
		if (StringUtils.hasText(msg)) {
			localizationAware.setValueForMessageKey(msg);
		}
		return localizationAware;
	}

	@Override
	public String getMessage(final Localized localized, final Locale locale) {
		return this.getMessage(localized.getMessageKey(), locale);
	}

	@Override
	public String getMessage(final String key, final Locale locale) {
		return this.getMessage(key, null, locale);
	}

	@Override
	public SLocalizedMessages getLocalizedMessages(final Locale locale) {
		final SLocalizedMessages preferences = new SLocalizedMessages();
		final Set<String> keys = this.getKeys(locale);

		for (final String key : keys) {
			preferences.put(key, this.getMessage(key, locale), locale);
		}

		return preferences;
	}

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

	@Override
	public SLocalizedMessage getLocalizedMessage(final String key, final Locale locale) {
		return new SLocalizedMessage()
				.setKey(key)
				.setMessage(this.getMessage(key, locale))
				.setLocale(SLocale.fromLocale(locale));
	}

	/**
	 * Returns all the keys from all given resource bundles ({@link ReloadableResourceBundleMessageSource#setBasenames(String...)}
	 *
	 * @param locale locale
	 *
	 * @return set of keys
	 */
	private Set<String> getKeys(final Locale locale) {
		return this.getMergedProperties(locale).getProperties().stringPropertyNames();
	}

	private static class InternalLocalizedClassAttribute implements LocalizedClassAttribute {
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
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			InternalLocalizedClassAttribute that = (InternalLocalizedClassAttribute) o;

			return Objects.equal(this.name, that.name) &&
					Objects.equal(this.label, that.label);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(name, label);
		}

		@Override
		public String toString() {
			return Objects.toStringHelper(this)
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
		public boolean hasAttributes() {
			return !CollectionUtils.isEmpty(this.attributes);
		}

		@Override
		public Map<String, LocalizedClassAttribute> asMap() {
			final Map<String, LocalizedClassAttribute> map = Maps.newHashMapWithExpectedSize(this.attributes.size());
			for (final LocalizedClassAttribute key : this.attributes) {
				map.put(key.getName(), key);
			}
			return map;
		}
	}
}
