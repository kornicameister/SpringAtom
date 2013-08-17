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

package org.agatom.springatom.server.service;

import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.server.model.beans.appointment.SFreeSlot;
import org.agatom.springatom.server.model.dto.SAppointmentTaskDTO;
import org.agatom.springatom.server.repository.repositories.SAppointmentRepository;
import org.agatom.springatom.server.service.base.SBasicService;
import org.agatom.springatom.server.service.exceptions.SEntityDoesNotExists;
import org.agatom.springatom.server.service.exceptions.SException;
import org.joda.time.DateTime;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInterval;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SAppointmentService
        extends SBasicService<SAppointment, Long, SAppointmentRepository> {
    @NotNull SAppointment newAppointment(final ReadableInterval interval,
                                         final long carId,
                                         final long assigneeId,
                                         final long reporterId,
                                         final SAppointmentTaskDTO... tasks) throws SException;

    @NotNull SAppointment addTask(final long idAppointment,
                                  final SAppointmentTaskDTO... tasks) throws SEntityDoesNotExists;

    @NotNull List<SAppointmentTask> findTasks(final long idAppointment);

    @NotNull SAppointment removeTask(final long idAppointment, final long... tasksId) throws SEntityDoesNotExists;

    @NotNull SAppointment findByTask(final long... tasks);

    @NotNull List<SAppointment> findByCar(final long carId);

    @NotNull List<SAppointment> findBetween(final DateTime startDate, final DateTime endDate);

    @NotNull List<SAppointment> findLater(final DateTime dateTime);

    @NotNull List<SAppointment> findEarlier(final DateTime dateTime);

    @NotNull SAppointment postponeToFuture(final long idAppointment, final ReadableDuration duration) throws
            SException;

    @NotNull SAppointment postponeToPast(final long idAppointment, final ReadableDuration duration) throws
            SException;

    @NotNull List<SFreeSlot> findSlots(final SFreeSlot.Slot slot, final long... idAppointment);

    @NotNull List<SFreeSlot> findSlots(final long... idAppointment);
}
