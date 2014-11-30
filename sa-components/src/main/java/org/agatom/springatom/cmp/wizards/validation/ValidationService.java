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

package org.agatom.springatom.cmp.wizards.validation;

import org.agatom.springatom.cmp.wizards.data.result.WizardResult;
import org.agatom.springatom.cmp.wizards.validation.model.ValidationBean;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-27</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ValidationService {

    /**
     * Invoke single validator. Method invokes global validator for entire wizard.
     * This is done {@link org.agatom.springatom.cmp.wizards.WizardProcessor#onWizardSubmit(java.util.Map, java.util.Locale)}}.
     *
     * @param validationBean validation information
     */
    void validate(final ValidationBean validationBean);

    boolean canValidate(final ValidationBean validationBean);

    /**
     * Routes validation via {@code localValidator} in order to be able to append some result to the {@link org.agatom.springatom.cmp.wizards.data.result.WizardResult}
     *
     * @param localValidator local validator to call
     * @param errors         current errors
     * @param result         result to update with {@link org.agatom.springatom.cmp.wizards.data.result.WizardDebugDataKeys}
     */
    void validate(Validator localValidator, Errors errors, final WizardResult result);
}
