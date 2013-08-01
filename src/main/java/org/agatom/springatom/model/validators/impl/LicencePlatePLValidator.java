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

package org.agatom.springatom.model.validators.impl;

import org.agatom.springatom.model.validators.LicencePlatePL;
import org.apache.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class LicencePlatePLValidator
        implements ConstraintValidator<LicencePlatePL, String> {
    private static final Logger LOGGER = Logger.getLogger(LicencePlatePLValidator.class);

    @Override
    public void initialize(final LicencePlatePL constraintAnnotation) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s initialized",
                    LicencePlatePLValidator.class.getSimpleName()
            ));
        }
    }

    @Override
    public boolean isValid(final String licencePlate, final ConstraintValidatorContext context) {
        //TODO add validating licence plates
        return true;
    }
}
