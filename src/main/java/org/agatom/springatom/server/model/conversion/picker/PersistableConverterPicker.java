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

package org.agatom.springatom.server.model.conversion.picker;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.core.annotations.LazyComponent;
import org.agatom.springatom.server.model.conversion.annotation.PersistableConverterUtility;
import org.agatom.springatom.server.model.conversion.converter.PersistableConverter;
import org.agatom.springatom.server.model.conversion.converter.PersistableConverterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Persistable;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@LazyComponent
@Role(BeanDefinition.ROLE_SUPPORT)
public class PersistableConverterPicker {
	private static final DefaultPickedConverter<Persistable> DEFAULT_PICKED_CONVERTER = new DefaultPickedConverter<>();
	@Autowired
	private              Set<PersistableConverter<?>>        converters               = null;

	@SuppressWarnings("unchecked")
	public <T extends Persistable> Converter<T, String> getConverterForSelector(final String key) {
		final Predicate<PersistableConverter<?>> selector = new Predicate<PersistableConverter<?>>() {
			@Override
			public boolean apply(@Nullable final PersistableConverter<?> input) {
				assert input != null;
				final PersistableConverterUtility annotation = input
						.getClass()
						.getAnnotation(PersistableConverterUtility.class);
				return annotation != null
						&& AnnotationUtils.getValue(annotation, "selector").equals(key);
			}
		};
		final Optional<PersistableConverter<?>> match = FluentIterable.from(this.converters).firstMatch(selector);
		if (match.isPresent()) {
			return (Converter<T, String>) match.get();
		}
		return new DefaultPickedConverter<>();
	}

	@SuppressWarnings("unchecked")
	public <T extends Persistable> Converter<T, String> getDefaultConverter(final TypeDescriptor sourceType) {
		final Predicate<PersistableConverter<?>> filter = new Predicate<PersistableConverter<?>>() {
			@Override
			public boolean apply(@Nullable final PersistableConverter<?> input) {
				assert input != null;
				return input.matches(sourceType, TypeDescriptor.valueOf(String.class));
			}
		};
		final Predicate<PersistableConverter<?>> selector = new Predicate<PersistableConverter<?>>() {
			@Override
			public boolean apply(@Nullable final PersistableConverter<?> input) {
				assert input != null;
				final PersistableConverterUtility annotation = input
						.getClass()
						.getAnnotation(PersistableConverterUtility.class);
				return annotation != null
						&& String.valueOf(AnnotationUtils.getValue(annotation, "selector")).isEmpty();
			}
		};

		final Optional<PersistableConverter<?>> match = FluentIterable
				.from(this.converters)
				.filter(filter)
				.firstMatch(selector);

		if (match.isPresent()) {
			return (Converter<T, String>) match.get();
		}

		return (Converter<T, String>) DEFAULT_PICKED_CONVERTER;
	}

	private static class DefaultPickedConverter<T extends Persistable>
			extends PersistableConverterImpl<T> {

		@Override
		public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
			return true;
		}

		@Override
		public String convert(final Persistable source) {
			if (source == null) {
				return "";
			}
			return source.toString();
		}
	}
}
