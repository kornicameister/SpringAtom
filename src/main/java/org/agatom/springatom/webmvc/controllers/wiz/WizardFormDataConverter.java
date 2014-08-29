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

package org.agatom.springatom.webmvc.controllers.wiz;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-30</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy(false)
@Component
class WizardFormDataConverter
        implements Converter<Object, WizardFormData>, ConditionalConverter {
    private static final Logger LOGGER = Logger.getLogger(WizardFormDataConverter.class);
    @Autowired
    @Qualifier("springAtomConversionService")
    private FormattingConversionService conversionService;

    @PostConstruct
    private void register() {
        this.conversionService.addConverter(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public WizardFormData convert(final Object source) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("convert(source=%s)", source));
        }

        final long startTime = System.nanoTime();
        final Map<String, Object> data = (Map<String, Object>) source;
        final WizardFormData formData = this.doConvert(data);

        if (LOGGER.isDebugEnabled()) {
            final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            LOGGER.debug(String.format("convert(source=%s) returns formData=%s, completed in %d ms", source, formData, endTime));
        }

        return formData;
    }

    @SuppressWarnings("unchecked")
    private WizardFormData doConvert(final Map<String, Object> data) {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("doConvert(data=%s)", data));
        }

        final WizardFormData formData = new WizardFormData();
        final ModelMap map = new ModelMap();
        final Set<String> keys = data.keySet();

        for (final String key : keys) {

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format("doConvert(key=%s)", key));
            }

            final Object object = data.get(key);
            if (ClassUtils.isPrimitiveOrWrapper(object.getClass()) || ClassUtils.isAssignableValue(String.class, object) || ClassUtils.isAssignableValue(Number.class, object) || ClassUtils.isAssignableValue(Boolean.class, object)) {
                map.put(key, object);
            } else if (ClassUtils.isAssignableValue(Map.class, object)) {
                map.putAll(this.doConvertFromMap(key, (Map<String, ?>) object));
            }

        }

        return formData.setData(map);
    }

    private Map<String, Object> doConvertFromMap(final String parentKey, final Map<String, ?> data) {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("doConvertFromMap(data=%s)", data));
        }

        final Set<String> keys = data.keySet();
        final Map<String, Object> localData = Maps.newHashMap();

        for (final String key : keys) {
            localData.put(parentKey + "." + key, data.get(key));
        }

        return localData;
    }

    @Override
    public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
        final Class<?> sourceTypeType = sourceType.getType();
        final Class<?> targetTypeType = targetType.getType();
        return ClassUtils.isAssignableValue(Map.class, sourceTypeType) && ClassUtils.isAssignable(WizardFormData.class, targetTypeType);
    }
}
