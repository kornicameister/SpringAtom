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
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.converters.ToInfoPageLinkWebConverter;
import org.agatom.springatom.webmvc.converters.du.converters.ToTableRequestWebConverter;
import org.agatom.springatom.webmvc.converters.du.exception.WebConverterException;
import org.apache.log4j.Logger;
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
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * {@code WebDataGenericConverter} is a facade hiding all {@link org.agatom.springatom.webmvc.converters.du.WebDataConverter} and exposing them
 * after unpacking {@link org.agatom.springatom.webmvc.converters.du.ConvertibleValueWrapper}, being a convert of conversion process.
 * Therefore this class will return {@code true} from {@link #matches(org.springframework.core.convert.TypeDescriptor, org.springframework.core.convert.TypeDescriptor)}
 * only for following configuration:
 * <ol>
 * <li>convert: {@link org.agatom.springatom.webmvc.converters.du.ConvertibleValueWrapper} or subclass</li>
 * <li>target: {@link java.lang.String}, because {@link org.agatom.springatom.webmvc.converters.du.WebDataConverter} returned value will be translated to {@link java.lang.String}</li>
 * </ol>
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 30.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@Lazy(false)
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("WebDataGenericConverter is a generic converter exposing WebDataConverter")
class WebDataGenericConverter
        implements GenericConverter,
        ConditionalConverter {
    private static final Logger                                        LOGGER            = Logger.getLogger(WebDataGenericConverter.class);
    @Autowired
    @Qualifier("springAtomConversionService")
    private              FormattingConversionService                   conversionService = null;
    @Autowired
    private              Map<String, WebDataConverter<?>>              converters        = null;
    @Autowired
    private              EntityDescriptors                             entityDescriptors = null;
    private              Map<WebDataConverterKey, WebDataConverter<?>> converterMap      = null;

    /** {@inheritDoc} */
    @Override
    public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
        final Class<?> sType = sourceType.getObjectType();
        final Class<?> tType = targetType.getObjectType();
        return ClassUtils.isAssignable(ConvertibleValueWrapper.class, sType) && ClassUtils.isAssignable(Object.class, tType);
    }

    /** {@inheritDoc} */
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Sets.newHashSet(new ConvertiblePair(ConvertibleValueWrapper.class, Object.class));
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public Object convert(final Object source, final TypeDescriptor sourceType, final TypeDescriptor targetType) {
        Assert.isInstanceOf(ConvertibleValueWrapper.class, source, "Source is not WebData");
        final ConvertibleValueWrapper webData = (ConvertibleValueWrapper) source;

        LOGGER.debug(String.format("Converting webData %s", webData.getKey()));

        final Object value = webData.getValue();
        final Persistable<?> persistable = webData.getSource();
        final String key = webData.getKey();
        final Class<?> valueType = ClassUtils.getUserClass(value != null ? value.getClass() : Void.class);

        final Map<WebDataConverterKey, WebDataConverter<?>> capable = this.getWebConverterMap(key, persistable, valueType);

        if (capable.size() > 1) {
            final String message = String.format("Unambiguous web convert choice, for key=%s, type=%s found %d converters", key, value, capable.size());
            LOGGER.warn(message);
            return null;
        } else if (capable.size() == 0) {
            final String message = String.format("No web convert choice, for key=%s, type=%s found %d converters", key, value, capable.size());
            LOGGER.warn(message);
            return null;
        }

        final WebDataConverter<?> next = capable.values().iterator().next();
        try {
            final Object data = next.convert(key, value, persistable, webData.getRequest());
            LOGGER.debug(String.format("Converted for key=%s to data=%s", key, data));
            return data;
        } catch (Exception exp) {
            LOGGER.fatal(String.format("Failure in conversion for key >> %s", key));
            throw new WebConverterException(String.format("Failure in conversion for key >> %s", key), exp).setConversionKey(key).setConversionValue(value);
        }

    }

    private Map<WebDataConverterKey, WebDataConverter<?>> getWebConverterMap(String key, final Persistable<?> persistable, final Class<?> valueType) {
        final String localKey = this.getConverterKey(key, persistable, valueType);
        FluentIterable<WebDataConverterKey> map = FluentIterable.from(this.converterMap.keySet())
                .filter(new Predicate<WebDataConverterKey>() {
                    @Override
                    public boolean apply(@Nullable final WebDataConverterKey input) {
                        return input != null && input.getKey().equals(localKey);
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

    private String getConverterKey(final String key, final Persistable<?> persistable, final Class<?> valueType) {
        final EntityDescriptor<? extends Persistable> entityDescriptor = this.entityDescriptors.getDescriptor(persistable.getClass());
        final EntityType<? extends Persistable> entityType = entityDescriptor.getEntityType();

        // 1. Check if it is an multi association from persistable
        Attribute<?, ?> attribute = null;
        try {
            attribute = entityType.getAttribute(key);
        } catch (Exception ignore) {
        }
        if (attribute == null) {
            return key;
        }
        final Attribute.PersistentAttributeType attributeType = attribute.getPersistentAttributeType();
        if (attribute.isCollection() || Attribute.PersistentAttributeType.ONE_TO_MANY.equals(attributeType)) {
            return ToTableRequestWebConverter.SELECTOR;
        }
        // 2. If not, perhaps it is one-to-many from persistable
        if (attributeType.equals(Attribute.PersistentAttributeType.MANY_TO_ONE) || attributeType.equals(Attribute.PersistentAttributeType.ONE_TO_ONE)) {
            return ToInfoPageLinkWebConverter.SELECTOR;
        }

        return key;
    }

    /**
     * Registers this {@link org.agatom.springatom.webmvc.converters.du.WebDataGenericConverter} as {@link org.springframework.core.convert.converter.GenericConverter}
     * in {@link org.springframework.format.support.FormattingConversionService}
     */
    @PostConstruct
    private void register() {
        LOGGER.trace(String.format("Registering in %d converters", converters.size()));

        final long startTime = System.nanoTime();

        this.conversionService.addConverter(this);
        this.converterMap = Maps.newHashMapWithExpectedSize(this.converters.size());
        for (final String converterId : converters.keySet()) {
            final WebDataConverter<?> converter = this.converters.get(converterId);
            final WebConverter annotation = converter.getClass().getAnnotation(WebConverter.class);
            final WebDataConverterKey key = new WebDataConverterKey(converterId, annotation.key(), annotation.types());

            LOGGER.trace(String.format("Registering WebDataConverter with id=%s, key=%s", converterId, key));

            this.converterMap.put(key, converter);
        }

        LOGGER.debug(String.format("Registration completed in %dms", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)));
    }
}
