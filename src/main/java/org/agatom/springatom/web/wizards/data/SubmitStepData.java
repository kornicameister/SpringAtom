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

package org.agatom.springatom.web.wizards.data;

import java.util.Set;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class SubmitStepData
        extends StepData {
    private static final long        serialVersionUID = 6457923902445152404L;
    protected            boolean     valid            = false;
    protected            Set<String> messages         = null;

    public static SubmitStepData valid() {
        return new SubmitStepData().setValid(true);
    }

    public static SubmitStepData invalid() {
        return new SubmitStepData().setValid(false);
    }

    public boolean isValid() {
        return valid;
    }

    public SubmitStepData setValid(final boolean valid) {
        this.valid = valid;
        return this;
    }
}
