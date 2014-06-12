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

import org.agatom.springatom.server.model.types.car.FuelType;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.component.core.TextComponent;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * {@code FuelTypeWebConverter} is designed to return localized value of {@link org.agatom.springatom.server.model.types.car.FuelType}
 * attribute of the {@link org.agatom.springatom.server.model.beans.car.SCar} persistable object.
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 03.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WebConverter(key = "fuelType", types = FuelType.class)
public class FuelTypeWebConverter
		extends PrimitivesWebConverter {

	/** {@inheritDoc} */
	@Override
	protected Serializable doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
		final TextComponent tc = (TextComponent) super.doConvert(key, value, persistable, webRequest);
		final FuelType fuelType = (FuelType) value;
		tc.setData(this.messageSource.getMessage(fuelType.name(), LocaleContextHolder.getLocale()));
		return tc;
	}
}
