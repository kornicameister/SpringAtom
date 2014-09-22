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
import org.agatom.springatom.server.service.vinNumber.exception.VinDecodingException;
import org.agatom.springatom.server.service.vinNumber.model.VinNumber;
import org.agatom.springatom.server.service.vinNumber.model.VinNumberData;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.component.GuiComponent;
import org.agatom.springatom.webmvc.converters.du.component.TextGuiComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

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
class VinNumberWebConverter
        extends AbstractWebConverter {
    @Autowired
    private VinDecoder vinDecoder = null;

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    protected Serializable doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) throws Exception {
        VinNumberData vinNumberData;
        String vinNumber = null;
        try {
            if (ClassUtils.isAssignableValue(String.class, value)) {
                vinNumber = (String) value;
                vinNumberData = this.vinDecoder.decode(VinNumber.newVinNumber(vinNumber));
            } else if (ClassUtils.isAssignableValue(VinNumber.class, value)) {
                final VinNumber number = (VinNumber) value;
                vinNumberData = this.vinDecoder.decode(number);
                vinNumber = number.getVinNumber();
            } else {
                vinNumberData = (VinNumberData) value;
            }
        } catch (VinDecodingException exp) {
            vinNumberData = null;
        }

        final Locale locale = LocaleContextHolder.getLocale();

        final StringBuilder builder = new StringBuilder();
        if (StringUtils.hasText(vinNumber)) {
            builder.append(this.messageSource.getMessage("scar.vinnumber", locale)).append("=").append(vinNumber);
        }
        if (vinNumberData != null) {
            builder.append(GuiComponent.NEW_LINE);
            if (StringUtils.hasText(vinNumberData.getBrand())) {
                builder.append(this.messageSource.getMessage("scarmaster.manufacturingdata.brand", locale)).append("=").append(vinNumberData.getBrand());
            }
            builder.append(GuiComponent.NEW_LINE);
            if (StringUtils.hasText(vinNumberData.getModel())) {
                builder.append(this.messageSource.getMessage("scarmaster.manufacturingdata.model", locale)).append("=").append(vinNumberData.getModel());
            }
            builder.append(GuiComponent.NEW_LINE);
            if (StringUtils.hasText(vinNumberData.getManufacturedBy())) {
                builder.append(this.messageSource.getMessage("scarmaster.manufacturedBy", locale)).append("=").append(vinNumberData.getManufacturedBy());
            }
            builder.append(GuiComponent.NEW_LINE);
            if (vinNumberData.getManufacturedIn() != null) {
                builder.append(this.messageSource.getMessage("scarmaster.manufacturedIn", locale)).append("=").append(vinNumberData.getManufacturedIn().getAlpha3());
            }
        }

        final TextGuiComponent component = new TextGuiComponent();
        component.setValue(builder.toString());
        return component;
    }
}
