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

import java.io.Serializable;
import java.util.Set;

/**
 * {@code ActionSecurityCheck} contains information required to validate action accessibility
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 15.07.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ActionSecurityCheck
		implements Serializable {
	private static final long        serialVersionUID = -3698508519155193203L;
	private              boolean     enabled          = false;
	private              String      pattern          = null;
	private              Set<String> roles            = null;

	public Set<String> getRoles() {
		return roles;
	}

	public ActionSecurityCheck setRoles(final Set<String> roles) {
		this.roles = roles;
		return this;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public ActionSecurityCheck setEnabled(final boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public String getPattern() {
		return pattern;
	}

	public ActionSecurityCheck setPattern(final String pattern) {
		this.pattern = pattern;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(enabled, pattern, roles);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ActionSecurityCheck that = (ActionSecurityCheck) o;

		return Objects.equal(this.enabled, that.enabled) &&
				Objects.equal(this.pattern, that.pattern) &&
				Objects.equal(this.roles, that.roles);
	}
}
