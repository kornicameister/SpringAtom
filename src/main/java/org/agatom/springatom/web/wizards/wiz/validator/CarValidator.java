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

package org.agatom.springatom.web.wizards.wiz.validator;

import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.service.vinNumber.validator.VinNumberValidator;
import org.agatom.springatom.web.wizards.validation.annotation.WizardValidator;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-31</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@WizardValidator
public class CarValidator {

    private final VinNumberValidator vinNumberValidator = new VinNumberValidator();

    public void validateVin(final SCar car, final ValidationContext validationContext) {
        final boolean validate = this.vinNumberValidator.validate(car.getVinNumber());
        if (!validate) {
            final MessageContext messageContext = validationContext.getMessageContext();
            final MessageBuilder messageBuilder = new MessageBuilder();
            messageContext.addMessage(
                    messageBuilder
                            .source("vinNumber")
                            .code("invalid.car.vinNumber")
                            .error()
                            .arg(car.getVinNumber())
                            .build()
            );
        }
    }

}
