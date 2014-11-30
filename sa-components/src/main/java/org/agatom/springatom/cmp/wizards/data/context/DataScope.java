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

package org.agatom.springatom.cmp.wizards.data.context;

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
     * Corresponds to the scope of the main wizard controller in the client.
     * Data set here wont be propagated to the client on submission however can
     * be used to provide some initial data that main controller will understand.
     * This is basically used when wizard is initialized.
     *
     * However for submission of a step this scope can be used to update
     * main scope of the controller with arbitrary data.
     */
    WIZARD,
    /**
     * Corresponds to the scope.formData in the client. Any data set here
     * will be available as data that will be submitted back to client
     * on step / wizard submission
     */
    FORM,
    /**
     * Scope dedicated to store data in step in client. Such data is propagated
     * from the main wizard controller on each submission or is populated on the server
     * when new step is being initialized to supply initial data like for instance
     * options for select components
     */
    STEP
}
