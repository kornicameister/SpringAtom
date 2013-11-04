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

package org.agatom.springatom.web.wizard.util;

/**
 * {@code SVWizardModelVariables} contains public constraints for variables further being available in the JSP pages.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class SVWizardModelVariables {
    /**
     * {@code SVWizardModelVariables#WIZARD_ID} determines the key under which <b>wizard id</b> is available in JSP.
     * It can be accessed using:
     * <pre>
     *     request.getAttribute(SVWizardModelVariables.WIZARD_ID);
     * </pre>
     */
    public static final String WIZARD_ID          = "wiz.id";
    /**
     * {@code SVWizardModelVariables#WIZARD_NAME} determines the key under which <b>wizard name</b> is available in
     * JSP.
     * It can be accessed using:
     * <pre>
     *     request.getAttribute(SVWizardModelVariables.WIZARD_NAME);
     * </pre>
     */
    public static final String WIZARD_NAME        = "wiz.name";
    /**
     * {@code SVWizardModelVariables#WIZARD_ID} determines the key under which <b>wizard submit url</b> is available in
     * JSP.
     * It can be accessed using:
     * <pre>
     *     request.getAttribute(SVWizardModelVariables.WIZARD_SUBMIT_URL);
     * </pre>
     */
    public static final String WIZARD_SUBMIT_URL  = "wiz.submit.url";
    /**
     * {@code SVWizardModelVariables#WIZARD_STEP_PREFIX} determines the key under which <b>wizard step prefix</b> is
     * available in JSP.
     * It can be accessed using:
     * <pre>
     *     request.getAttribute(WIZARD_STEP_PREFIX);
     * </pre>
     */
    public static final String WIZARD_STEP_PREFIX = "wiz.step-";
}
