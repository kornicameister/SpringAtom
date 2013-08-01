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

package org.agatom.springatom.model.types.user;

import org.agatom.springatom.populators.DatabaseEnumPopulable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public enum SSecurityRoleEnum
        implements DatabaseEnumPopulable {
    ADMIN(666),
    ANONYMOUS(-1),
    USER(0),
    CLIENT(1),
    MECHANIC(2),
    BOSS(3),
    ACCOUNT_ADMINISTRATOR(4),
    //per persistent business class
    CAR_UPDATE(5),
    CAR_READ(6),
    CAR_CREATE(7),
    CAR_DELETE(8),
    APPOINTMENT_CREATE(9),
    APPOINTMENT_READ(10),
    APPOINTMENT_UPDATE(11),
    APPOINTMENT_DELETE(12);
    //per persistent business class
    private static final String PREFIX = "ROLE_";
    private final int roleId;

    SSecurityRoleEnum(final int id) {
        this.roleId = id;
    }

    public int getRoleId() {
        return this.roleId;
    }

    @Override
    public String[] getColumns() {
        return new String[]{"role"};
    }

    @Override
    public String getTable() {
        return "srole";
    }

    @Override
    public String[] getData() {
        return new String[]{this.toString()};
    }

    @Override
    public String toString() {
        return String.format("%s%s", PREFIX, super.toString());
    }
}
