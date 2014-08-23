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

package org.agatom.springatom.web.wizards.data.context;

/**
 * {@code DataScope} describes a scope of a data returned in result from a {@link org.agatom.springatom.web.wizards.WizardProcessor}
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-22</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
enum DataScope {
    /**
     * Scope dedicated to store data directly in wizard controller in client
     */
    WIZARD,
    /**
     * Scope dedicated to store data in form scope in client
     */
    FORM,
    /**
     * Scope dedicated to store data in formData in client
     */
    FORM_DATA,
    /**
     * Scope dedicated to store data in step in client
     */
    STEP
}
