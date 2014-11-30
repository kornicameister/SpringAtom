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

package org.agatom.springatom.data.vin.validator;

import org.agatom.springatom.data.vin.model.VinNumber;
import org.apache.log4j.Logger;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 08.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class VinNumberValidator
        implements Validator {
    private static final Logger LOGGER     = Logger.getLogger(VinNumberValidator.class);
    private static final int    VIN_LENGTH = 17;
    private static final int[]  WEIGHTS    = {8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2};

    /** {@inheritDoc} */
    @Override
    public boolean supports(final Class<?> clazz) {
        return ClassUtils.isAssignable(VinNumber.class, clazz) || ClassUtils.isAssignable(String.class, clazz);
    }

    /** {@inheritDoc} */
    @Override
    public void validate(final Object target, final Errors errors) {
        String vinNumber;
        if (ClassUtils.isAssignableValue(VinNumber.class, target)) {
            vinNumber = ((VinNumber) target).getVinNumber();
        } else {
            vinNumber = (String) target;
        }
        if (!this.validate(vinNumber)) {
            errors.reject("vinNumber");
        }
    }

    /**
     * <p>validate.</p>
     *
     * @param vinNumber a {@link String} object.
     *
     * @return a boolean.
     */
    public boolean validate(final String vinNumber) {
        int crc = 0;
        for (int i = 0; i < VIN_LENGTH; i++) {
            crc += WEIGHTS[i] * this.resolveCharacterValue(vinNumber.charAt(i));
        }
        final char checksum = (crc %= 11) == 10 ? 'X' : Character.forDigit(crc, 10);
        final boolean valid = this.isChecksumValid(vinNumber, checksum);
        if (valid && LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("VinNumber=%s is valid [checksum=%s]", vinNumber, checksum));
        } else if (!valid) {
            LOGGER.warn(String.format("VinNumber=%s is invalid [checksum=%s]", vinNumber, checksum));
        }
        return valid;
    }

    private int resolveCharacterValue(final int c) {
        return c <= '9' ? c - '0' : ((c >= 'S' ? c + 1 : c) - 'A') % 9 + 1;
    }

    private boolean isChecksumValid(final String number, final char checksum) {
        return number.charAt(8) == checksum;
    }
}
