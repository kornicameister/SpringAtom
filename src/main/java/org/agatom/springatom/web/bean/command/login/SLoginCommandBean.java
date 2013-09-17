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

package org.agatom.springatom.web.bean.command.login;

import com.google.common.base.Objects;
import org.agatom.springatom.web.bean.command.CommandBean;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
//TODO add validation
public class SLoginCommandBean
        extends CommandBean {
    private String username;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public SLoginCommandBean setUsername(final String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public SLoginCommandBean setPassword(final String password) {
        this.password = password;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SLoginCommandBean that = (SLoginCommandBean) o;

        return Objects.equal(this.username, that.username) &&
                Objects.equal(this.password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username, password);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(username)
                      .addValue(password)
                      .toString();
    }
}
