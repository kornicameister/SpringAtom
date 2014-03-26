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

package org.agatom.springatom.server.service.domain;

import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.server.model.beans.appointment.SFreeSlot;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.service.support.exceptions.EntityDoesNotExistsServiceException;
import org.agatom.springatom.server.service.support.exceptions.ServiceException;
import org.joda.time.DateTime;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInterval;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SAppointmentService
		extends SBasicService<SAppointment, Long> {
	@NotNull
	SAppointment newAppointment(final ReadableInterval interval,
	                            final long carId,
	                            final long assigneeId,
	                            final long reporterId,
	                            final SAppointmentTask... tasks) throws ServiceException;

	@NotNull
	SAppointment addTask(final long idAppointment,
	                     final SAppointmentTask... tasks) throws EntityDoesNotExistsServiceException;

	@NotNull
	List<SAppointmentTask> findTasks(final long idAppointment);

	@NotNull
	SAppointment removeTask(final long idAppointment, final long... tasksId) throws EntityDoesNotExistsServiceException;

	@NotNull
	SAppointment findByTask(final long... tasks);

	@NotNull
	List<SAppointment> findByCar(final long carId);

	@NotNull
	List<SAppointment> findBetween(final DateTime startDate, final DateTime endDate);

	@NotNull
	List<SAppointment> findBetween(final DateTime startDate, final DateTime endDate, boolean currentUserOnly) throws
			ServiceException;

	@NotNull
	List<SAppointment> findLater(final DateTime dateTime);

	@NotNull
	List<SAppointment> findEarlier(final DateTime dateTime);

	@NotNull
	SAppointment postponeToFuture(final long idAppointment, final ReadableDuration duration) throws
			ServiceException;

	@NotNull
	SAppointment postponeToPast(final long idAppointment, final ReadableDuration duration) throws
			ServiceException;

	@NotNull
	List<SFreeSlot> findSlots(final SFreeSlot.Slot slot, final long... idAppointment);

	@NotNull
	List<SFreeSlot> findSlots(final long... idAppointment);

	@Cacheable(value = "appointment_principals", key = "'suser_reporters'")
	Collection<SUser> findReporters();

	@Cacheable(value = "appointment_principals", key = "'suser_reporters' + #pageble.pageNumber")
	Collection<SUser> findReporters(final Pageable pageable);

	@Cacheable(value = "appointment_principals", key = "suser_assignees")
	Collection<SUser> findAssignees();

	@Cacheable(value = "appointment_principals", key = "'suser_assignees' + #pageble.pageNumber")
	Collection<SUser> findAssignees(final Pageable pageable);
}
