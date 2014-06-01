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

package org.agatom.springatom.webmvc.converters.du;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.request.ComponentRequestAttribute;
import org.agatom.springatom.web.component.infopages.elements.meta.AttributeDisplayAs;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.component.core.DefaultWebDataComponent;
import org.agatom.springatom.webmvc.converters.du.component.core.TextComponent;
import org.agatom.springatom.webmvc.converters.du.converters.ToInfoPageLinkWebConverter;
import org.agatom.springatom.webmvc.converters.du.converters.ToTableRequestWebConverter;
import org.agatom.springatom.webmvc.converters.du.exception.WebConverterException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.domain.Persistable;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * {@code WebDataGenericConverter} is a facade hiding all {@link WebDataConverter} and exposing them
 * after unpacking {@link ConvertibleValueWrapper}, being a convert of conversion process.
 * Therefore this class will return {@code true} from {@link #matches(org.springframework.core.convert.TypeDescriptor, org.springframework.core.convert.TypeDescriptor)}
 * only for following configuration:
 * <ol>
 * <li>convert: {@link ConvertibleValueWrapper} or subclass</li>
 * <li>target: {@link java.lang.String}, because {@link WebDataConverter} returned value will be translated to {@link java.lang.String}</li>
 * </ol>
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 30.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy(false)
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("WebDataGenericConverter is a generic converter exposing WebDataConverter")
public class WebDataGenericConverter
		implements GenericConverter,
		ConditionalConverter {
	private static final Logger                                        LOGGER            = Logger.getLogger(WebDataGenericConverter.class);
	@Autowired
	@Qualifier("springAtomConversionService")
	private              FormattingConversionService                   conversionService = null;
	@Autowired
	private              ListableBeanFactory                           beanFactory       = null;
	private              Map<WebDataConverterKey, WebDataConverter<?>> converterMap      = Maps.newHashMap();
	@Autowired
	private              SMessageSource                                messageSource     = null;

	@Override
	public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
		final Class<?> sType = sourceType.getObjectType();
		final Class<?> tType = targetType.getObjectType();
		return ClassUtils.isAssignable(ConvertibleValueWrapper.class, sType) && ClassUtils.isAssignable(Object.class, tType);
	}

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return Sets.newHashSet(new ConvertiblePair(ConvertibleValueWrapper.class, Object.class));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object convert(final Object source, final TypeDescriptor sourceType, final TypeDescriptor targetType) {
		Assert.isInstanceOf(ConvertibleValueWrapper.class, source, "Source is not WebData");
		final ConvertibleValueWrapper webData = (ConvertibleValueWrapper) source;

		LOGGER.debug(String.format("Converting webData %s", webData.getKey()));

		final ComponentDataRequest request = webData.getRequest();
		final Object value = webData.getValue();
		final Persistable<?> persistable = webData.getSource();
		final String key = webData.getKey();
		final Class<?> valueType = ClassUtils.getUserClass(value.getClass());
		final AttributeDisplayAs displayAs = this.getValueRenderType(webData.getRequest().getAttributes(), key);

		String localKey = null;
		switch (displayAs) {
			case LINK_ATTRIBUTE:
				localKey = ToInfoPageLinkWebConverter.SELECTOR;
				break;
			case TABLE_ATTRIBUTE:
				localKey = ToTableRequestWebConverter.SELECTOR;
				break;
			case VALUE_ATTRIBUTE:
				localKey = key;
				break;
		}

		LOGGER.trace(String.format("For %s using key %s to pick converter", displayAs, localKey));

		final Map<WebDataConverterKey, WebDataConverter<?>> capable = this.pickUpCapable(localKey, valueType);
		Serializable serializable = null;

		if (capable.size() > 1) {
			final String message = String.format("Unambiguous web convert choice, for key=%s, type=%s found %d converters", key, value, capable.size());
			LOGGER.warn(message);
			serializable = new TextComponent().setValue(message).setKey(localKey).setRawValueType(valueType);
		} else if (capable.size() == 0) {
			final String message = String.format("No web convert choice, for key=%s, type=%s found %d converters", key, value, capable.size());
			LOGGER.warn(message);
			serializable = new TextComponent().setValue(message).setKey(localKey).setRawValueType(valueType);
		}

		if (serializable != null) {
			DefaultWebDataComponent<?> dataComponent = (DefaultWebDataComponent<?>) serializable;

			dataComponent = dataComponent.setKey(key).setRawValueType(String.class);
			dataComponent.setTitle(this.messageSource.getLocalizedClassAttribute(persistable.getClass(), key, (java.util.Locale) request.getValues().get("locale")).getLabel());

			return dataComponent;
		}

		final WebDataConverter<?> next = capable.values().iterator().next();
		try {
			serializable = next.convert(key, value, persistable, request);
			LOGGER.debug(String.format("Converted for key=%s to value=%s", key, serializable));
			return serializable;
		} catch (Exception exp) {
			LOGGER.fatal(String.format("Failure in conversion for key >> %s", key));
			throw new WebConverterException(String.format("Failure in conversion for key >> %s", key), exp).setConversionKey(key).setConversionValue(value);
		}

	}

	private AttributeDisplayAs getValueRenderType(final Set<ComponentRequestAttribute> attributes, final String key) {
		final Optional<ComponentRequestAttribute> match = FluentIterable.from(attributes).firstMatch(new Predicate<ComponentRequestAttribute>() {
			@Override
			public boolean apply(@Nullable final ComponentRequestAttribute input) {
				return input != null && input.getPath().equals(key);
			}
		});
		final ComponentRequestAttribute attribute = match.get();
		return AttributeDisplayAs.valueOf(attribute.getType());
	}

	private Map<WebDataConverterKey, WebDataConverter<?>> pickUpCapable(final String key, final Class<?> valueType) {
		FluentIterable<WebDataConverterKey> map = FluentIterable.from(this.converterMap.keySet())
				.filter(new Predicate<WebDataConverterKey>() {
					@Override
					public boolean apply(@Nullable final WebDataConverterKey input) {
						return input != null && input.key.equals(key);
					}
				});
		if (map.isEmpty()) {
			map = FluentIterable.from(this.converterMap.keySet())
					.filter(new Predicate<WebDataConverterKey>() {
						@Override
						public boolean apply(@Nullable final WebDataConverterKey input) {
							return input != null && input.capableOfType(valueType);
						}
					});
		}
		return map.toMap(new Function<WebDataConverterKey, WebDataConverter<?>>() {
			@Nullable
			@Override
			public WebDataConverter<?> apply(@Nullable final WebDataConverterKey input) {
				return converterMap.get(input);
			}
		});
	}

	/**
	 * Registers this {@link org.agatom.springatom.webmvc.converters.du.WebDataGenericConverter} as {@link org.springframework.core.convert.converter.GenericConverter}
	 * in {@link org.springframework.format.support.FormattingConversionService}
	 */
	@PostConstruct
	private void register() {
		final Map<String, Object> converters = this.beanFactory.getBeansWithAnnotation(WebConverter.class);

		LOGGER.trace(String.format("Registering in %s and %d converters", this.conversionService, converters.size()));

		final long startTime = System.nanoTime();

		this.conversionService.addConverter(this);
		for (final String converterId : converters.keySet()) {
			final WebDataConverter<?> converter = (WebDataConverter<?>) this.beanFactory.getBean(converterId);
			final WebConverter annotation = converter.getClass().getAnnotation(WebConverter.class);
			final WebDataConverterKey key = new WebDataConverterKey(annotation.key(), annotation.types());

			LOGGER.trace(String.format("Registering WebDataConverter with id=%s, key=%s", converterId, key));

			this.converterMap.put(key, converter);
		}

		LOGGER.debug(String.format("Registration completed in %dms", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)));
	}

	/**
	 * Used in {@link java.util.Map} as the key for which converter will be picked up
	 */
	private class WebDataConverterKey {
		private final String        key;
		private final Set<Class<?>> types;

		private WebDataConverterKey(final String key, final Class<?>... types) {
			this.key = key;
			this.types = Sets.newHashSet(types);
		}

		public boolean capableOfType(final Class<?> valueType) {
			for (Class<?> type : this.types) {
				if (ClassUtils.isAssignable(type, valueType)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(key, types);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			WebDataConverterKey that = (WebDataConverterKey) o;

			return Objects.equal(this.key, that.key) &&
					Objects.equal(this.types, that.types);
		}

		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("key", key)
					.add("types", types)
					.toString();
		}
	}
}
