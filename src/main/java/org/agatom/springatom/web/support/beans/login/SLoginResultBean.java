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

package org.agatom.springatom.web.support.beans.login;

import org.agatom.springatom.web.support.beans.WebBean;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SLoginResultBean
        implements WebBean {
    private static final String BEAN_ID = "loginResult";
    private boolean success;
    private String  message;
    private String  username;

    public boolean isSuccess() {
        return success;
    }

    public SLoginResultBean setSuccess(final boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SLoginResultBean setMessage(final String message) {
        this.message = message;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SLoginResultBean setUsername(final String username) {
        this.username = username;
        return this;
    }

    public SLoginResultBean success() {
        return this.setSuccess(true);
    }

    public SLoginResultBean failure() {
        return this.setSuccess(false);
    }

    @Override
    public String getBeanId() {
        return BEAN_ID;
    }

}
