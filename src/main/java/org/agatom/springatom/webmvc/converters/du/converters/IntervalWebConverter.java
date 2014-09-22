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

package org.agatom.springatom.webmvc.converters.du.converters;

import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.component.TextGuiComponent;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import java.util.Locale;

/**
 * {@code IntervalWebConverter} supports creating {@link org.agatom.springatom.webmvc.converters.du.component.TextGuiComponent}
 * out of {@link org.joda.time.Interval} or {@link org.joda.time.Duration}
 * <small>Class is a part of <b>SpringAtom</b> and was created at 01.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WebConverter(types = {Interval.class, Duration.class})
class IntervalWebConverter
        extends AbstractWebConverter {

    /** {@inheritDoc} */
    @Override
    protected TextGuiComponent doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
        if (value != null) {
            final long intervalValue = this.getIntervalValue(ClassUtils.isAssignableValue(Interval.class, value) ? ((Interval) value).toDuration() : (Duration) value);
            final String intervalFormat = this.getIntervalLocalizedValue(intervalValue, LocaleContextHolder.getLocale());
            final TextGuiComponent component = new TextGuiComponent();
            component.setValue(intervalFormat);
            return component;
        }
        return null;
    }

    /**
     * Extracts the value to be used to create localized duration value. By Default {@link org.joda.time.Duration#getStandardMinutes()} is used
     *
     * @param duration duration to get interval value from
     *
     * @return interval value
     */
    protected long getIntervalValue(final Duration duration) {
        return duration.getStandardMinutes();
    }

    /**
     * Creates localized {@link java.lang.String} value out of {@code intervalValue}. By default <b>minute</b> scoped
     * message is created
     *
     * @param intervalValue extracted duration from {@link #getIntervalValue(org.joda.time.Duration)}
     * @param locale        current locale
     *
     * @return localized value
     */
    protected String getIntervalLocalizedValue(final long intervalValue, final Locale locale) {
        return this.messageSource.getMessage("date.interval.minutes.format", new Object[]{intervalValue}, locale);
    }

}
