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

package org.agatom.springatom.data.services;

import org.agatom.springatom.data.types.appointment.Appointment;
import org.agatom.springatom.data.types.appointment.AppointmentTask;
import org.agatom.springatom.data.types.car.Car;
import org.agatom.springatom.data.types.enumeration.Enumeration;
import org.agatom.springatom.data.types.user.User;
import org.joda.time.DateTime;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInterval;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Persistable;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * <p>SAppointmentService interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SAppointmentService<T extends Appointment<U, C> & Persistable<Long>, C extends Car, AT extends AppointmentTask, U extends User>
        extends SDomainService<T> {

    @NotNull
    T newAppointment(final ReadableInterval interval,
                     final long carId,
                     final long assigneeId,
                     final long reporterId,
                     final AT... tasks) throws Exception;

    @NotNull
    T addTask(final long idAppointment,
              final AT... tasks) throws Exception;

    @NotNull
    T addTask(final T appointment, final Iterable<AT> tasks) throws Exception;

    @NotNull
    AT createTask(final String task, final String type) throws Exception;

    @NotNull
    List<AT> findTasks(final long idAppointment);

    @NotNull
    T removeTask(final long idAppointment, final long... tasksId) throws Exception;

    T findByTask(final long... tasks);

    @NotNull
    List<T> findByCar(final long carId);

    @NotNull
    List<T> findBetween(final DateTime startDate, final DateTime endDate);

    @NotNull
    List<T> findBetween(final DateTime startDate, final DateTime endDate, boolean currentUserOnly) throws
            Exception;

    @NotNull
    List<T> findLater(final DateTime dateTime);

    @NotNull
    List<T> findEarlier(final DateTime dateTime);

    @NotNull
    T postponeToFuture(final long idAppointment, final ReadableDuration duration) throws
            Exception;

    @NotNull
    T postponeToPast(final long idAppointment, final ReadableDuration duration) throws
            Exception;

    @Cacheable(value = "appointment_principals_reporters")
    Collection<U> findReporters();

    @Cacheable(value = "appointment_principals_assignees")
    Collection<U> findAssignees();

    <E extends Enumeration> E getAppointmentTypes() throws Exception;
}
