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
import org.agatom.springatom.webmvc.converters.du.component.core.IconComponent;
import org.agatom.springatom.webmvc.converters.du.component.core.TextComponent;
import org.agatom.springatom.webmvc.converters.du.component.core.WebDataComponentsArray;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import java.io.Serializable;

/**
 * {@code PrimaryKeyWebConverter} creates {@link org.agatom.springatom.webmvc.converters.du.component.core.WebDataComponentsArray}
 * containing two other components:
 * <ol>
 * <li>{@link org.agatom.springatom.webmvc.converters.du.component.core.TextComponent} with the {@link org.springframework.data.domain.Persistable#getId()}</li>
 * <li>{@link org.agatom.springatom.webmvc.converters.du.component.core.IconComponent} with the icon</li>
 * </ol>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 31.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WebConverter(key = "id")
public class PrimaryKeyWebConverter
		extends AbstractWebConverter {

	@Override
	@SuppressWarnings("unchecked")
	protected Serializable doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
		final WebDataComponentsArray array = new WebDataComponentsArray();

		array.setTitle(this.getLabel(key, persistable));
		array.addContent(new TextComponent().setKey(key).setValue(String.valueOf(value)).setRawValueType(ClassUtils.getUserClass(value)));
		array.addContent(new IconComponent().setIconClass("fa fa-key"));

		return array;
	}

}
