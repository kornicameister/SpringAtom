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

import org.agatom.springatom.server.service.vinNumber.decoder.VinDecoder;
import org.agatom.springatom.server.service.vinNumber.model.VinNumber;
import org.agatom.springatom.server.service.vinNumber.model.VinNumberData;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.component.core.TextComponent;
import org.agatom.springatom.webmvc.converters.du.component.core.WebDataComponentsArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.util.Locale;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 03.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WebConverter(key = "vinNumber", types = {VinNumber.class, VinNumberData.class})
public class VinNumberWebConverter
		extends AbstractWebConverter {
	@Autowired
	private VinDecoder vinDecoder = null;

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	protected Serializable doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) throws Exception {
		final VinNumberData vinNumberData;
		if (ClassUtils.isAssignableValue(String.class, value)) {
			vinNumberData = this.vinDecoder.decode(VinNumber.newVinNumber((String) value));
		} else if (ClassUtils.isAssignableValue(VinNumber.class, value)) {
			vinNumberData = this.vinDecoder.decode((VinNumber) value);
		} else {
			vinNumberData = (VinNumberData) value;
		}

		final Locale locale = LocaleContextHolder.getLocale();
		final WebDataComponentsArray array = new WebDataComponentsArray();

		array.addWDC(new TextComponent()
				.setData(vinNumberData.getBrand())
				.setDataType(String.class)
				.setId("brand")
				.setLabel(this.messageSource.getMessage("scarmaster.manufacturingdata.brand", locale)));
		array.addWDC(new TextComponent()
				.setData(vinNumberData.getModel())
				.setDataType(String.class)
				.setId("model")
				.setLabel(this.messageSource.getMessage("scarmaster.manufacturingdata.model", locale)));
		array.addWDC(new TextComponent()
				.setData(vinNumberData.getManufacturedBy())
				.setDataType(String.class)
				.setId("manufacturedBy")
				.setLabel(this.messageSource.getMessage("scarmaster.manufacturedBy", locale)));
		array.addWDC(new TextComponent()
				.setData(vinNumberData.getManufacturedIn().getAlpha3())
				.setDataType(String.class)
				.setId("manufacturedIn")
				.setLabel(this.messageSource.getMessage("scarmaster.manufacturedIn", locale)));

		return array;
	}

}
