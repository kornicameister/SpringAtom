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

package org.agatom.springatom.cmp.wizards.data.result;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-30</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class WizardDebugDataKeys {
    public static final String DEBUG_TIME      = "debugTime";
    public static final String VALIDATION_TIME = "validationTime";
    public static final String COMPILE_TIME    = "compilationTime";
    public static final String TOTAL_TIME      = "totalTime";
    public static final String SUBMISSION_TIME = "submissionTime";
    public static final String PROCESSOR       = "processor";
    public static final String INIT_TIME       = "initTime";
    public static final String LOCALE          = "locale";
    public static final String BINDING_TIME    = "bindingTime";
    public static final String IS_STEP_BINDING = "stepBinding";
    public static final String DATA_SIZE       = "dataSize";
    public static final String VALIDATOR       = "validator";

    private WizardDebugDataKeys() {

    }
}
