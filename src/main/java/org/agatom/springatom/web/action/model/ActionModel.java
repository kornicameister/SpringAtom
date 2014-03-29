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

package org.agatom.springatom.web.action.model;

import com.google.common.base.Objects;

import java.util.Set;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ActionModel implements Action {
	private static final long        serialVersionUID = -4880614509556359333L;
	private              String      name             = null;
	private              String      description      = null;
	private              Set<Action> content          = null;
	private              String      label            = null;

	public ActionModel setLabel(final String label) {
		this.label = label;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public ActionModel setName(final String name) {
		this.name = name;
		return this;
	}

	public ActionModel setDescription(final String description) {
		this.description = description;
		return this;
	}

	public ActionModel setContent(final Set<Action> content) {
		this.content = content;
		return this;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Set<Action> getContent() {
		return content;
	}

	@Override
	public String getId() {
		return this.name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ActionModel that = (ActionModel) o;

		return Objects.equal(this.name, that.name) &&
				Objects.equal(this.description, that.description) &&
				Objects.equal(this.content, that.content);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, description, content);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(name)
				.addValue(description)
				.addValue(content)
				.toString();
	}
}
