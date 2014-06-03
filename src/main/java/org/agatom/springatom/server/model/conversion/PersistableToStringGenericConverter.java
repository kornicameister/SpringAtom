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

package org.agatom.springatom.server.model.conversion;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.server.model.conversion.picker.PersistableConverterPicker;
import org.agatom.springatom.server.model.descriptors.SlimEntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.domain.Persistable;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.ClassUtils;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * <p>PersistableToStringGenericConverter class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PersistableToStringGenericConverter
		implements GenericConverter,
		ConditionalConverter,
		InitializingBean {
	private static final Function<SlimEntityDescriptor<?>, ConvertiblePair> CONVERTIBLE_PAIR_FUNCTION = new Function<SlimEntityDescriptor<?>, ConvertiblePair>() {
		@Nullable
		@Override
		public ConvertiblePair apply(@Nullable final SlimEntityDescriptor<?> input) {
			assert input != null;
			return new ConvertiblePair(input.getJavaClass(), String.class);
		}
	};
	@Autowired
	private EntityDescriptors           descriptors;
	@Autowired
	private PersistableConverterPicker  keyAwarePicker;
	@Autowired
	private FormattingConversionService conversionService;

	/** {@inheritDoc} */
	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return FluentIterable.from(this.descriptors.getSlimDescriptors()).transform(CONVERTIBLE_PAIR_FUNCTION).toSet();
	}

	/** {@inheritDoc} */
	@Override
	public Object convert(final Object source, final TypeDescriptor sourceType, final TypeDescriptor targetType) {
		final Converter<Persistable, String> defaultConverter = this.keyAwarePicker.getDefaultConverter(sourceType);
		return defaultConverter.convert((Persistable) source);
	}

	/** {@inheritDoc} */
	@Override
	public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
		final Class<?> type = sourceType.getObjectType();
		final Class<?> tType = targetType.getObjectType();
		return ClassUtils.isAssignable(Persistable.class, type) && ClassUtils.isAssignable(String.class, tType);
	}

	/** {@inheritDoc} */
	@Override
	public void afterPropertiesSet() throws Exception {
		this.conversionService.addConverter(this);
	}
}
