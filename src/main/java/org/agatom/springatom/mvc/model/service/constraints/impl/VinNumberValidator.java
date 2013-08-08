/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
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

package org.agatom.springatom.mvc.model.service.constraints.impl;

import org.agatom.springatom.mvc.model.service.constraints.VinNumber;
import org.apache.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * {@code VinNumberValidator} validates given <b>vin number</b>.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class VinNumberValidator
        implements ConstraintValidator<VinNumber, String> {
    private static final Logger LOGGER     = Logger.getLogger(VinNumberValidator.class);
    private static final int    VIN_LENGTH = 17;
    private static final int[]  WEIGHTS    = {8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2};

    @Override
    public void initialize(final VinNumber constraintAnnotation) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s initialized",
                    VinNumberValidator.class.getSimpleName()
            ));
        }
    }

    @Override
    public boolean isValid(final String vinNumber, final ConstraintValidatorContext context) {
        int crc = 0;
        for (int i = 0 ; i < VIN_LENGTH ; i++) {
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

    public boolean isChecksumValid(final String number, final char checksum) {
        return number.charAt(8) == checksum;
    }
}
