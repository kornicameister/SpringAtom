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

package org.agatom.springatom.mvc.model.dao.impl;

import org.agatom.springatom.model.beans.appointment.SAppointment;
import org.agatom.springatom.model.beans.mechanic.SMechanic;
import org.agatom.springatom.mvc.model.dao.SMechanicDAO;
import org.agatom.springatom.mvc.model.dao.abstracts.SPersonDefaultDAO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Repository(value = "SMechanicDAO")
public class SMechanicDAOImpl
        extends SPersonDefaultDAO<SMechanic, Long>
        implements SMechanicDAO {

    private static final String CACHE_NAME = "appointment";

    @Override
    @Cacheable(value = CACHE_NAME, key = "#appointment.id")
    public SMechanic findByAppointment(@NotNull final SAppointment appointment) {
        return null;
    }

    @Override
    public SMechanic findByAppointments(@NotNull final Iterable<SAppointment> appointments) {
        return null;
    }
}
