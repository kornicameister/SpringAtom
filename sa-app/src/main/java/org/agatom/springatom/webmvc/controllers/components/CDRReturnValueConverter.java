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

package org.agatom.springatom.webmvc.controllers.components;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.data.oid.SOid;
import org.agatom.springatom.data.oid.creators.PersistableOid;
import org.agatom.springatom.webmvc.converters.du.ConvertibleValueWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Persistable;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.Collection;
import java.util.Map;

/**
 * {@code CDRReturnValueConverter} eases with transforming data from non-atomic to atomic form
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.3
 * @since 0.0.1
 */
@Component
@Role(BeanDefinition.ROLE_APPLICATION)
@Scope(BeanDefinition.SCOPE_SINGLETON)
class CDRReturnValueConverter {
    private static final Logger                      LOGGER            = LoggerFactory.getLogger(CDRReturnValueConverter.class);
    @Autowired
    private              FormattingConversionService conversionService = null;

    /**
     * <p>convert.</p>
     *
     * @param request a {@link ComponentDataRequest} object.
     *
     * @return a {@link java.util.Map} object.
     */
    public Object convert(Object data, final ComponentDataRequest request) {
        LOGGER.trace(String.format("convert(data=%s)", data));
        return this.getConvertedValue(data, request);
    }

    private Object getConvertedValue(final Object value, final ComponentDataRequest request) {
        if (value == null) {
            return null;
        }

        if (ClassUtils.isAssignableValue(Map.class, value)) {
            return this.convertFromMap((Map<?, ?>) value, request);
        } else if (ClassUtils.isAssignableValue(Collection.class, value)) {
            return this.convertFromCollection((Collection<?>) value, request);
        } else if (ClassUtils.isAssignableValue(org.agatom.springatom.cmp.component.core.Component.class, value)) {
            return value;
        }

        throw new IllegalArgumentException(String.format("%s not supported", ClassUtils.getShortName(value.getClass())));
    }

    private Object convertFromMap(final Map<?, ?> data, final ComponentDataRequest request) {
        final Map<String, Object> retData = Maps.newHashMap();

        for (final Object key : data.keySet()) {
            final ConvertibleValueWrapper webData = new ConvertibleValueWrapper();

            webData.setValue(data.get(key));
            webData.setKey(String.valueOf(key));
            webData.setSource(this.getSource(request));
            webData.setRequest(request);

            retData.put(String.valueOf(key), this.conversionService.convert(webData, Object.class));
        }

        return retData;
    }

    private Object convertFromCollection(final Collection<?> value, final ComponentDataRequest request) {
        final Collection<Object> returnCollection = Lists.newLinkedList();
        for (final Object data : value) {
            if (ClassUtils.isAssignableValue(Map.class, data)) {
                returnCollection.add(this.convertFromMap((Map<?, ?>) data, request));
            } else {
                returnCollection.add(this.convertFromObject(data, request));
            }
        }
        return returnCollection;
    }

    private Persistable<?> getSource(final ComponentDataRequest request) {
        final SOid oid = request.getOid();
        Assert.isInstanceOf(PersistableOid.class, oid);
        return ((PersistableOid) oid).getReference().get();
    }

    private Object convertFromObject(final Object data, final ComponentDataRequest request) {
        final ConvertibleValueWrapper webData = new ConvertibleValueWrapper();

        webData.setValue(data);
        webData.setSource(this.getSource(request));
        webData.setRequest(request);

        return this.conversionService.convert(webData, Object.class);
    }
}
