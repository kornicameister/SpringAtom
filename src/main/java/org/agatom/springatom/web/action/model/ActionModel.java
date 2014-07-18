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
public class ActionModel
		extends AbstractAction {
	private static final long        serialVersionUID = -4880614509556359333L;
	private              String      description      = null;
	private              Set<Action> content          = null;

	/**
	 * <p>Getter for the field <code>description</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <p>Setter for the field <code>description</code>.</p>
	 *
	 * @param description a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.action.model.ActionModel} object.
	 */
	public ActionModel setDescription(final String description) {
		this.description = description;
		return this;
	}

	/**
	 * <p>Getter for the field <code>content</code>.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<Action> getContent() {
		return content;
	}

	/**
	 * <p>Setter for the field <code>content</code>.</p>
	 *
	 * @param content a {@link java.util.Set} object.
	 *
	 * @return a {@link org.agatom.springatom.web.action.model.ActionModel} object.
	 */
	public ActionModel setContent(final Set<Action> content) {
		this.content = content;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(this.getId())
				.addValue(description)
				.addValue(content)
				.toString();
	}

	/** {@inheritDoc} */
	@Override
	public String getId() {
		return this.getName();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(this.getId(), description, content);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ActionModel that = (ActionModel) o;

		return Objects.equal(this.getId(), that.getId()) &&
				Objects.equal(this.description, that.description) &&
				Objects.equal(this.content, that.content);
	}


}
