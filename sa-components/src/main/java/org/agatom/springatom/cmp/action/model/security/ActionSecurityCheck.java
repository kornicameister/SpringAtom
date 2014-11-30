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

package org.agatom.springatom.cmp.action.model.security;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;

import java.io.Serializable;

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
    private static final long       serialVersionUID = -3698508519155193203L;
    private              boolean    authenticated    = false;
    private              ActionRole roles            = null;
    private              boolean    validateOnClick  = false;

    public boolean isValidateOnClick() {
        return validateOnClick;
    }

    public ActionSecurityCheck setValidateOnClick(final boolean validateOnClick) {
        this.validateOnClick = validateOnClick;
        return this;
    }

    @JsonUnwrapped(enabled = true)
    public ActionRole getRoles() {
        return this.roles;
    }

    public ActionSecurityCheck setRoles(final ActionRole roles) {
        this.roles = roles;
        return this;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public ActionSecurityCheck setAuthenticated(final boolean authenticated) {
        this.authenticated = authenticated;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(authenticated, roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionSecurityCheck that = (ActionSecurityCheck) o;

        return Objects.equal(this.authenticated, that.authenticated) &&
                Objects.equal(this.roles, that.roles);
    }
}
