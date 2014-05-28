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

package org.agatom.springatom.web.component.builders;

import com.google.common.base.Objects;
import org.apache.log4j.Logger;

/**
 * {@code AbstractBuilder} is a root for all <b>component builder</b>. Defines root fields and functionality.
 * It is not an actual {@link org.agatom.springatom.web.component.builders.ComponentDataBuilder}
 * or {@link org.agatom.springatom.web.component.builders.ComponentDefinitionBuilder}, yet all concrete implementations
 * starts from it
 * <small>Class is a part of <b>SpringAtom</b> and was created at 27.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract class AbstractBuilder
		implements Builder {
	protected final Logger logger = Logger.getLogger(getClass());
	private         String id     = null;

	@Override
	public final String getId() {
		return this.id;
	}

	public final void setId(final String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("id", id)
				.toString();
	}
}
