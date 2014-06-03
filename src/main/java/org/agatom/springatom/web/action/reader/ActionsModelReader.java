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

package org.agatom.springatom.web.action.reader;

import javassist.NotFoundException;
import org.agatom.springatom.web.action.model.ActionModel;
import org.springframework.cache.annotation.Cacheable;

import java.util.Set;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ActionsModelReader {
	/**
	 * <p>getActionModels.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	Set<ActionModel> getActionModels();

	/**
	 * <p>getActionModel.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.action.model.ActionModel} object.
	 *
	 * @throws javassist.NotFoundException if any.
	 */
	@Cacheable(value = "actionsModels", key = "#name")
	ActionModel getActionModel(final String name) throws NotFoundException;
}
