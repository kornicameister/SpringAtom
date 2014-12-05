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

package org.agatom.springatom.data.vin.decoder;

import org.agatom.springatom.data.vin.decoder.resolver.VISYearResolver;
import org.agatom.springatom.data.vin.decoder.resolver.WMIManufacturedInResolver;
import org.agatom.springatom.data.vin.exception.VinDecodingException;
import org.agatom.springatom.data.vin.model.VinNumber;
import org.agatom.springatom.data.vin.model.VinNumberData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 08.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Service("vinNumberDecoder")
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("vinNumberDecoder resolves all possible to retrieve information from passed vin number")
class VinNumberDecoder
        implements VinDecoder {
    private static final Logger                    LOGGER                    = LoggerFactory.getLogger(VinNumberDecoder.class);
    @Autowired
    private              VISYearResolver           yearDecoder               = null;
    @Autowired
    private              WMIManufacturedInResolver wmiManufacturedInResolver = null;

    /** {@inheritDoc} */
    @Override
    public VinNumberData decode(final String vinNumber) throws VinDecodingException {
        try {
            return this.decode(VinNumber.newVinNumber(vinNumber));
        } catch (Exception exp) {
            LOGGER.error("Decode failed due to exception", exp);
            if (ClassUtils.isAssignableValue(VinDecodingException.class, exp)) {
                //noinspection ConstantConditions
                throw (VinDecodingException) exp;
            }
            throw new VinDecodingException(exp);
        }
    }

    /** {@inheritDoc} */
    @Override
    public VinNumberData decode(final VinNumber vinNumber) throws VinDecodingException {
        final VinNumberData vinNumberData = new VinNumberData();
        try {
            this.decodeWmi(vinNumber.getWmi(), vinNumberData);
            this.decodeVis(vinNumber.getVis(), vinNumberData);
            this.decodeVds(vinNumber.getVds(), vinNumberData);
        } catch (VinDecodingException exp) {
            LOGGER.error("decode(vinNumber=%s) failed", exp);
            throw exp;
        }
        vinNumberData.setVinNumber(vinNumber);
        return vinNumberData;
    }

    private void decodeWmi(final String wmi, final VinNumberData vinNumberData) throws VinDecodingException {
        try {
            vinNumberData.setManufacturedIn(this.wmiManufacturedInResolver.getCountryCode(wmi));
        } catch (Exception exp) {
            throw new VinDecodingException("decodeVis(vis=%s) failed", exp);
        }
    }

    private void decodeVis(final String vis, final VinNumberData vinNumberData) throws VinDecodingException {
        try {
            vinNumberData.setYears(this.yearDecoder.getYear(vis));
        } catch (Exception exp) {
            throw new VinDecodingException("decodeVis(vis=%s) failed", exp);
        }
    }

    private void decodeVds(final String vds, final VinNumberData vinNumberData) {

    }

}
