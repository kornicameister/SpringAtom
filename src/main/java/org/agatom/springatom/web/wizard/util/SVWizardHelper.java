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

import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class SVWizardHelper {
    public synchronized static String generateWizardId(final String name) {
        final StringTokenizer stringTokenizer = new StringTokenizer(name, ".");
        final StringBuilder builder = new StringBuilder();
        final Random random = new Random(System.nanoTime());
        while (stringTokenizer.hasMoreElements()) {
            builder.append(stringTokenizer.nextToken()).append(random.nextInt(10));
        }
        return builder.toString();
    }

    public static String getSVBStepDataKeyForStep(final int step) {
        return String.format("%s%s", SVWizardModelVariables.WIZARD_STEP_PREFIX, step);
    }
}
