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
import org.agatom.springatom.webmvc.converters.du.component.core.TextComponent;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.util.Locale;

/**
 * {@code PrimitivesWebConverter} supports retrieving the value for primitives values like for example {@link java.lang.Number} or {@link java.lang.Boolean}
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
public class PrimitivesWebConverter
		extends AbstractWebConverter {

	/** {@inheritDoc} */
	@Override
	protected Serializable doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
		final TextComponent component = new TextComponent();
		final Locale locale = LocaleContextHolder.getLocale();

		if (ClassUtils.isAssignableValue(Boolean.class, value)) {
			final Boolean aBoolean = (Boolean) value;
			if (aBoolean.equals(true)) {
				component.setData(this.messageSource.getMessage("boolean.true", locale));
			} else {
				component.setData(this.messageSource.getMessage("boolean.false", locale));
			}
		} else if (ClassUtils.isAssignableValue(Enum.class, value)) {
			component.setData(this.messageSource.getMessage(((Enum<?>) value).name(), locale));
			component.addDynamicProperty("rawEnumValue", value);
		} else {
			component.setData(String.valueOf(value));
		}

		component.setDataType(value.getClass());
		component.setId(key);
		component.setLabel(this.getLabel(key, persistable));

		return component;
	}

}
