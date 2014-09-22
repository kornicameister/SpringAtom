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
import org.agatom.springatom.webmvc.converters.du.component.DateTimeGuiComponent;
import org.agatom.springatom.webmvc.converters.du.component.TextGuiComponent;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import java.util.Date;

/**
 * {@code DateTimeWebConverter} creates {@link org.agatom.springatom.webmvc.converters.du.component.TextGuiComponent}
 * from following classes that supports:
 * <ol>
 * <li>{@link org.joda.time.DateTime}</li>
 * <li>{@link org.joda.time.LocalDateTime}</li>
 * <li>{@link java.util.Date}</li>
 * </ol>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 31.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@WebConverter(types = {DateTime.class, LocalDateTime.class, Date.class})
class DateTimeWebConverter
        extends AbstractWebConverter {

    /** {@inheritDoc} */
    @Override
    protected TextGuiComponent doConvert(final String key, final Object value, final Persistable persistable, final ComponentDataRequest webRequest) {
        String data = null;

        final String format = this.messageSource.getMessage("data.format.value", LocaleContextHolder.getLocale());

        if (ClassUtils.isAssignableValue(DateTime.class, value) || ClassUtils.isAssignableValue(LocalDateTime.class, value)) {
            if (ClassUtils.isAssignableValue(DateTime.class, value)) {
                data = ((DateTime) value).toString(format);
            } else {
                data = ((LocalDateTime) value).toString(format);
            }
        } else if (ClassUtils.isAssignableValue(Date.class, value)) {
            final Date date = (Date) value;
            data = DateTimeFormat.forPattern(format).print(date.getTime());
        }

        final DateTimeGuiComponent component = new DateTimeGuiComponent();

        component.setValue(data);
        component.setFormat(format);
        component.setLocale(LocaleContextHolder.getLocale());

        return component;
    }
}
