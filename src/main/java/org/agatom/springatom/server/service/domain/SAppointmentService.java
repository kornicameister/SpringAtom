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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

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
public interface SAppointmentService
		extends SBasicService<SAppointment, Long> {
	/**
	 * <p>newAppointment.</p>
	 *
	 * @param interval   a {@link org.joda.time.ReadableInterval} object.
	 * @param carId      a long.
	 * @param assigneeId a long.
	 * @param reporterId a long.
	 * @param tasks      a {@link org.agatom.springatom.server.model.beans.appointment.SAppointmentTask} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 *
	 * @throws org.agatom.springatom.server.service.support.exceptions.ServiceException if any.
	 */
	@NotNull
	SAppointment newAppointment(final ReadableInterval interval,
	                            final long carId,
	                            final long assigneeId,
	                            final long reporterId,
	                            final SAppointmentTask... tasks) throws ServiceException;

	/**
	 * <p>addTask.</p>
	 *
	 * @param idAppointment a long.
	 * @param tasks         a {@link org.agatom.springatom.server.model.beans.appointment.SAppointmentTask} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 *
	 * @throws org.agatom.springatom.server.service.support.exceptions.EntityDoesNotExistsServiceException if any.
	 */
	@NotNull
	SAppointment addTask(final long idAppointment,
	                     final SAppointmentTask... tasks) throws EntityDoesNotExistsServiceException;

	/**
	 * <p>findTasks.</p>
	 *
	 * @param idAppointment a long.
	 *
	 * @return a {@link java.util.List} object.
	 */
	@NotNull
	List<SAppointmentTask> findTasks(final long idAppointment);

	/**
	 * <p>removeTask.</p>
	 *
	 * @param idAppointment a long.
	 * @param tasksId       a long.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 *
	 * @throws org.agatom.springatom.server.service.support.exceptions.EntityDoesNotExistsServiceException if any.
	 */
	@NotNull
	SAppointment removeTask(final long idAppointment, final long... tasksId) throws EntityDoesNotExistsServiceException;

	/**
	 * <p>findByTask.</p>
	 *
	 * @param tasks a long.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 */
	@NotNull
	SAppointment findByTask(final long... tasks);

	/**
	 * <p>findByCar.</p>
	 *
	 * @param carId a long.
	 *
	 * @return a {@link java.util.List} object.
	 */
	@NotNull
	List<SAppointment> findByCar(final long carId);

	/**
	 * <p>findBetween.</p>
	 *
	 * @param startDate a {@link org.joda.time.DateTime} object.
	 * @param endDate   a {@link org.joda.time.DateTime} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	@NotNull
	List<SAppointment> findBetween(final DateTime startDate, final DateTime endDate);

	/**
	 * <p>findBetween.</p>
	 *
	 * @param startDate       a {@link org.joda.time.DateTime} object.
	 * @param endDate         a {@link org.joda.time.DateTime} object.
	 * @param currentUserOnly a boolean.
	 *
	 * @return a {@link java.util.List} object.
	 *
	 * @throws org.agatom.springatom.server.service.support.exceptions.ServiceException if any.
	 */
	@NotNull
	List<SAppointment> findBetween(final DateTime startDate, final DateTime endDate, boolean currentUserOnly) throws
			ServiceException;

	/**
	 * <p>findLater.</p>
	 *
	 * @param dateTime a {@link org.joda.time.DateTime} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	@NotNull
	List<SAppointment> findLater(final DateTime dateTime);

	/**
	 * <p>findEarlier.</p>
	 *
	 * @param dateTime a {@link org.joda.time.DateTime} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	@NotNull
	List<SAppointment> findEarlier(final DateTime dateTime);

	/**
	 * <p>postponeToFuture.</p>
	 *
	 * @param idAppointment a long.
	 * @param duration      a {@link org.joda.time.ReadableDuration} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 *
	 * @throws org.agatom.springatom.server.service.support.exceptions.ServiceException if any.
	 */
	@NotNull
	SAppointment postponeToFuture(final long idAppointment, final ReadableDuration duration) throws
			ServiceException;

	/**
	 * <p>postponeToPast.</p>
	 *
	 * @param idAppointment a long.
	 * @param duration      a {@link org.joda.time.ReadableDuration} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 *
	 * @throws org.agatom.springatom.server.service.support.exceptions.ServiceException if any.
	 */
	@NotNull
	SAppointment postponeToPast(final long idAppointment, final ReadableDuration duration) throws
			ServiceException;

	/**
	 * <p>findSlots.</p>
	 *
	 * @param slot          a {@link org.agatom.springatom.server.model.beans.appointment.SFreeSlot.Slot} object.
	 * @param idAppointment a long.
	 *
	 * @return a {@link java.util.List} object.
	 */
	@NotNull
	List<SFreeSlot> findSlots(final SFreeSlot.Slot slot, final long... idAppointment);

	/**
	 * <p>findSlots.</p>
	 *
	 * @param idAppointment a long.
	 *
	 * @return a {@link java.util.List} object.
	 */
	@NotNull
	List<SFreeSlot> findSlots(final long... idAppointment);

	/**
	 * <p>findReporters.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	@Cacheable(value = "appointment_principals", key = "'suser_reporters'")
	Collection<SUser> findReporters();

	/**
	 * <p>findReporters.</p>
	 *
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	@Cacheable(value = "appointment_principals", key = "'suser_reporters' + #pageble.pageNumber")
	Collection<SUser> findReporters(final Pageable pageable);

	/**
	 * <p>findAssignees.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	@Cacheable(value = "appointment_principals", key = "suser_assignees")
	Collection<SUser> findAssignees();

	/**
	 * <p>findAssignees.</p>
	 *
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	@Cacheable(value = "appointment_principals", key = "'suser_assignees' + #pageble.pageNumber")
	Collection<SUser> findAssignees(final Pageable pageable);
}
