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

package org.agatom.springatom.server.model.conversion.converter;

import org.agatom.springatom.server.model.conversion.picker.PersistableConverterPicker;
import org.agatom.springatom.web.locale.SMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Persistable;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.ClassUtils;

import static org.springframework.core.GenericTypeResolver.resolveTypeArgument;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

abstract public class PersistableConverterImpl<S extends Persistable>
        implements PersistableConverter<S> {

    protected static final Class<String> STRING_CLASS = String.class;
    private final          Class<?>      SOURCE_TYPE  = resolveTypeArgument(getClass(), PersistableConverterImpl.class);

    @Autowired(required = false)
    protected FormattingConversionService conversionService;
    @Autowired(required = false)
    protected SMessageSource              messageSource;
    @Autowired(required = false)
    protected PersistableConverterPicker  converterPicker;

    @Override
    public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
        final Class<?> type = sourceType.getObjectType();
        final Class<?> tType = targetType.getObjectType();
        return ClassUtils.isAssignable(SOURCE_TYPE, type) && ClassUtils.isAssignable(String.class, tType);
    }

}
