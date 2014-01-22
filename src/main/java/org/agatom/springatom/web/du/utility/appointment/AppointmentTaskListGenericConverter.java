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

package org.agatom.springatom.web.du.utility.appointment;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.web.du.annotation.DataUtility;
import org.agatom.springatom.web.du.utility.DataUtilityConverter;
import org.apache.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@DataUtility(value = "du_appointmentTask")
public class AppointmentTaskListGenericConverter
        implements DataUtilityConverter,
                   ConditionalConverter {
    private static final Logger         LOGGER        = Logger.getLogger(AppointmentTaskListGenericConverter.class);
    private final static Class<?>       sourceClass   = ClassUtils.getUserClass(Collection.class);
    private final static List<Class<?>> targetClasses = Lists.newArrayList();

    static {
        targetClasses.add(ClassUtils.getUserClass(List.class));
        targetClasses.add(ClassUtils.getUserClass(Report.class));
    }

    private ConversionService conversionService;

    @Override
    public DataUtilityConverter setConversionService(final ConversionService conversionService) {
        this.conversionService = conversionService;
        return this;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return FluentIterable
                .from(targetClasses)
                .transform(new Function<Class<?>, ConvertiblePair>() {
                    @Nullable
                    @Override
                    public ConvertiblePair apply(@Nullable final Class<?> input) {
                        return new ConvertiblePair(sourceClass, input);
                    }
                }).toSet();
    }

    @Override
    public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
        final Class<?> sourceTypeType = sourceType.getType();
        TypeDescriptor typeDescriptor = null;
        if (ClassUtils.isAssignable(Collection.class, sourceTypeType)) {
            typeDescriptor = sourceType.getElementTypeDescriptor();
        } else if (ClassUtils.isAssignable(Map.class, sourceTypeType)) {
            typeDescriptor = sourceType.getMapValueTypeDescriptor();
        }
        return typeDescriptor != null && ClassUtils.isAssignable(SAppointmentTask.class, typeDescriptor.getType());
    }

    @Override
    public Object convert(final Object source, final TypeDescriptor sourceType, final TypeDescriptor targetType) {
        this.assertNotNull(source, sourceType, targetType);
        LOGGER.trace(String.format("/convert -> source=%s, sourceType=%s, targetType=%s", source, sourceType, targetType));
        return null;
    }

    /**
     * Runs quick assertion againts nullability of passed arguments
     *
     * @param source
     *         source
     * @param sourceType
     *         sourceType
     * @param targetType
     *         targetType
     */
    private void assertNotNull(final Object source, final TypeDescriptor sourceType, final TypeDescriptor targetType) {
        Assert.notNull(source);
        Assert.notNull(sourceType);
        Assert.notNull(targetType);
    }
}