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

import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.component.GuiComponent;
import org.agatom.springatom.webmvc.converters.du.component.TextGuiComponent;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.util.Locale;

/**
 * {@code PrimitivesWebConverter} supports retrieving the value for primitives values like for example {@link Number} or {@link Boolean}
 * <small>Class is a part of <b>SpringAtom</b> and was created at 31.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WebConverter(types = {
        String.class,
        Boolean.class,
        Number.class,
        Enum.class
})
class PrimitivesWebConverter
        extends AbstractWebConverter {

    /** {@inheritDoc} */
    @Override
    protected Serializable doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
        if (value == null) {
            return null;
        }

        GuiComponent component;

        if (ClassUtils.isAssignableValue(Boolean.class, value)) {
            component = this.doConvertFromBoolean(key, value, persistable, webRequest);
        } else if (ClassUtils.isAssignableValue(Enum.class, value)) {
            component = this.doConvertFromEnum(key, value, persistable, webRequest);
        } else if (ClassUtils.isAssignableValue(Number.class, value)) {
            component = this.doConvertFromNumber(key, value, persistable, webRequest);
        } else {
            component = this.doConvertFromString(key, value, persistable, webRequest);
        }

        return component;
    }

    protected GuiComponent doConvertFromBoolean(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
        final Locale locale = LocaleContextHolder.getLocale();
        final Boolean aBoolean = (Boolean) value;
        String retValue;
        if (aBoolean.equals(true)) {
            retValue = this.messageSource.getMessage("boolean.true", locale);
        } else {
            retValue = this.messageSource.getMessage("boolean.false", locale);
        }
        final TextGuiComponent cmp = new TextGuiComponent();
        cmp.setValue(retValue);
        return cmp;
    }

    protected GuiComponent doConvertFromEnum(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
        final Locale locale = LocaleContextHolder.getLocale();
        final String retValue = this.messageSource.getMessage(((Enum<?>) value).name(), locale);
        final TextGuiComponent cmp = new TextGuiComponent();
        cmp.setValue(retValue);
        return cmp;
    }

    protected GuiComponent doConvertFromNumber(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
        final TextGuiComponent cmp = new TextGuiComponent();
        cmp.setValue(String.valueOf(value));
        return cmp;
    }

    protected GuiComponent doConvertFromString(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
        final TextGuiComponent cmp = new TextGuiComponent();
        cmp.setValue(String.valueOf(value));
        return cmp;
    }

}
