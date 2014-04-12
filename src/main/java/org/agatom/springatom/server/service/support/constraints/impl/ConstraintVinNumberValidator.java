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

package org.agatom.springatom.server.service.support.constraints.impl;

import org.agatom.springatom.server.service.support.constraints.VinNumber;
import org.agatom.springatom.server.service.vinNumber.validator.VinNumberValidator;
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
public class ConstraintVinNumberValidator
		implements ConstraintValidator<VinNumber, String> {
	private static final Logger LOGGER = Logger.getLogger(ConstraintVinNumberValidator.class);

	@Override
	public void initialize(final VinNumber constraintAnnotation) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace(String.format("%s initialized",
					ConstraintVinNumberValidator.class.getSimpleName()
			));
		}
	}

	@Override
	public boolean isValid(final String vinNumber, final ConstraintValidatorContext context) {
		return new VinNumberValidator().validate(vinNumber);
	}
}
