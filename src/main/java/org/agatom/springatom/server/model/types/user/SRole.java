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

package org.agatom.springatom.server.model.types.user;

import org.agatom.springatom.server.populators.DatabaseEnumPopulable;

/**
 * {@code SSecurityAuthorityEnum} is an enum that describes available roles in the application
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public enum SRole
        implements DatabaseEnumPopulable {
    ROLE_ADMIN(666),
    ROLE_ANONYMOUS(-1),
    ROLE_USER(0),
    ROLE_CLIENT(1),
    ROLE_MECHANIC(2),
    ROLE_BOSS(3),
    ROLE_ACCOUNT_ADMINISTRATOR(4),
    //per persistent business class
    ROLE_CAR_UPDATE(5),
    ROLE_CAR_READ(6),
    ROLE_CAR_CREATE(7),
    ROLE_CAR_DELETE(8),
    ROLE_APPOINTMENT_CREATE(9),
    ROLE_APPOINTMENT_READ(10),
    ROLE_APPOINTMENT_UPDATE(11),
    ROLE_APPOINTMENT_DELETE(12);
    //per persistent business class
    private final int roleId;

    SRole(final int id) {
        this.roleId = id;
    }

    public int getRoleId() {
        return this.roleId;
    }

    @Override
    public String[] getColumns() {
        return new String[]{"authority"};
    }

    @Override
    public String getTable() {
        return "sauthority";
    }

    @Override
    public String[] getData() {
        return new String[]{this.toString()};
    }

}
