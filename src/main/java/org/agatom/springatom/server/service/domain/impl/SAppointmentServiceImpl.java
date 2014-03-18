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

package org.agatom.springatom.server.service.domain.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Longs;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.EnumPath;
import org.agatom.springatom.server.model.beans.appointment.*;
import org.agatom.springatom.server.model.beans.car.QSCar;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.user.QSUser;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.beans.user.authority.SAuthority;
import org.agatom.springatom.server.model.types.user.SRole;
import org.agatom.springatom.server.repository.repositories.appointment.SAppointmentTaskRepository;
import org.agatom.springatom.server.repository.repositories.car.SCarRepository;
import org.agatom.springatom.server.repository.repositories.user.SUserRepository;
import org.agatom.springatom.server.service.domain.SAppointmentService;
import org.agatom.springatom.server.service.support.exceptions.EntityDoesNotExistsServiceException;
import org.agatom.springatom.server.service.support.exceptions.SecurityViolationServiceException;
import org.agatom.springatom.server.service.support.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(value = "SAppointmentService")
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
public class SAppointmentServiceImpl
		extends SBasicServiceImpl<SAppointment, Long>
		implements SAppointmentService {
	private static final String                     BAD_ARG_MSG                = "No %s provided, could not execute call";
	private static final String                     ERROR_MESSAGE_SD_GT_ED_MSG = "startDate must be greater than endDate";
	private static final Logger                     LOGGER                     = Logger.getLogger(SAppointmentServiceImpl.class);
	@Value(value = "#{sAppointmentProperties['sappointment.minDiffBetweenDatesMs']}")
	private              long                       minDiffBetweenDates        = 0;
	@Autowired
	private              SAppointmentTaskRepository taskRepository             = null;
	@Autowired
	private              SCarRepository             carRepository              = null;
	@Autowired
	private              SUserRepository            userRepository             = null;

	@Override
	public SAppointment withFullLoad(final SAppointment obj) {
		final long idAppointment = obj.getId();

		final QSAppointment qsAppointment = QSAppointment.sAppointment;
		final QSCar qsCar = QSCar.sCar;

		final SCar car = this.carRepository
				.createCustomQuery()
				.from(qsCar, qsAppointment)
				.where(qsCar.id.eq(qsAppointment.car.id).and(qsAppointment.id.eq(idAppointment)))
				.fetchAll()
				.singleResult(qsCar);
		final Collection<SAppointmentTask> tasks = (Collection<SAppointmentTask>) this.taskRepository
				.findAll(QSAppointmentTask.sAppointmentTask.appointment.id.eq(idAppointment));

		obj.setTasks(tasks);
		obj.setCar(car);

		return obj;
	}

	@Override
	public SAppointment newAppointment(final ReadableInterval interval,
	                                   final long carId,
	                                   final long assigneeId,
	                                   final long reportedId,
	                                   final SAppointmentTask... tasks) throws ServiceException {
		checkArgument(carId > 0, String.format(BAD_ARG_MSG, "carId <= 0"));
		checkArgument(assigneeId > 0, String.format(BAD_ARG_MSG, "assigneeId <= "));
		checkArgument(tasks.length > 0, String.format(BAD_ARG_MSG, "tasks.length <= 0"));

		final SCar car = this.getCar(carId);
		final SUser assignee = this.getMechanic(assigneeId);
		final SUser reporter = this.getMechanic(reportedId);

		final SAppointment newAppointment = (SAppointment) new SAppointment()
				.setCar(car)
				.setTasks(Sets.newHashSet(tasks))
				.setInterval(interval)
				.setAssignee(assignee)
				.setReporter(reporter)
				.setAssigned(DateTime.now());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String
					.format("%s saved with %d task and assigned to %s", SAppointment.class
							.getSimpleName(), tasks.length, assignee));
		}

		return this.repository.save(newAppointment);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = {EntityDoesNotExistsServiceException.class, IllegalArgumentException.class}, isolation = Isolation.READ_COMMITTED)
	public SAppointment addTask(final long idAppointment, final SAppointmentTask... tasks) throws
			EntityDoesNotExistsServiceException {
		checkArgument(idAppointment > 0, String.format(BAD_ARG_MSG, "SAppointment#getId()"));
		checkArgument(tasks.length != 0, String.format(BAD_ARG_MSG, "SAppointmentTask#getId()"));

		final List<SAppointmentTask> savable = Lists.newArrayListWithExpectedSize(tasks.length);
		final SAppointment appointment = this.findAppointment(idAppointment);

		for (final SAppointmentTask task : tasks) {
			if (task.isNew()) {
				savable.add(task.setAppointment(appointment));
			}
		}

		this.taskRepository.save(Lists.newArrayList(tasks));
		return this.repository.findOne(idAppointment).setTasks((Collection<SAppointmentTask>) this.taskRepository
				.findAll(QSAppointmentTask.sAppointmentTask.appointment.id.eq(idAppointment)));
	}

	@Override
	public List<SAppointmentTask> findTasks(final long idAppointment) {
		final QSAppointmentTask task = QSAppointmentTask.sAppointmentTask;
		final BooleanExpression eq = task.appointment.id.eq(idAppointment);
		return (List<SAppointmentTask>) this.taskRepository.findAll(eq);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = {EntityDoesNotExistsServiceException.class, IllegalArgumentException.class}, isolation = Isolation.READ_COMMITTED)
	public SAppointment removeTask(final long idAppointment, final long... tasksId) throws EntityDoesNotExistsServiceException {
		checkArgument(idAppointment > 0, String.format(BAD_ARG_MSG, "SAppointment#getId()"));
		this.taskRepository.deleteInBatch(this.taskRepository
				.findAll(
						QSAppointmentTask
								.sAppointmentTask
								.id.
								in(Longs.asList(tasksId))
				)
		);
		return this.repository.findOne(idAppointment);
	}

	@Override
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public SAppointment findByTask(final long... tasks) {
		checkArgument(tasks.length != 0, String.format(BAD_ARG_MSG, "SAppointmentTask#getId()"));
		return this.repository.findOne(QSAppointment.sAppointment.tasks.any().id.in(Longs.asList(tasks)));
	}

	@Override
	public List<SAppointment> findByCar(final long carId) {
		final QSAppointment sAppointment = QSAppointment.sAppointment;
		return (List<SAppointment>) this.repository.findAll(sAppointment.car.id.eq(carId));
	}

	@Override
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public List<SAppointment> findBetween(final DateTime startDate, final DateTime endDate) {
		try {
			return this.findBetween(startDate, endDate, false);
		} catch (SecurityViolationServiceException ignore) {
		}
		return Lists.newArrayList();
	}

	@Override
	public List<SAppointment> findBetween(final DateTime startDate, final DateTime endDate, final boolean currentUserOnly) throws
			SecurityViolationServiceException {
		checkArgument(endDate.isAfter(startDate), ERROR_MESSAGE_SD_GT_ED_MSG);
		LOGGER.debug("/findBetween");
		final DateTimePath<DateTime> begin = QSAppointment.sAppointment.begin;
		final DateTimePath<DateTime> end = QSAppointment.sAppointment.end;
		final BooleanExpression expression = begin.goe(startDate).and(end.loe(endDate));

		if (currentUserOnly) {
			final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof SUser) {
				expression.and(QSAppointment.sAppointment.reporter.eq((SUser) principal));
			} else {
				throw new SecurityViolationServiceException(
						SAppointment.class,
						"Requested for list of appointment for current user but no such user was found in the context"
				);
			}
		}

		return (List<SAppointment>) this.repository.findAll(expression);
	}

	@Override
	public List<SAppointment> findLater(final DateTime dateTime) {
		final DateTimePath<DateTime> begin = QSAppointment.sAppointment.begin;
		final DateTimePath<DateTime> end = QSAppointment.sAppointment.end;
		return (List<SAppointment>) this.repository.findAll(end.goe(dateTime).and(end.gt(begin)));
	}

	@Override
	public List<SAppointment> findEarlier(final DateTime dateTime) {
		final DateTimePath<DateTime> begin = QSAppointment.sAppointment.begin;
		final DateTimePath<DateTime> end = QSAppointment.sAppointment.end;
		return (List<SAppointment>) this.repository.findAll(end.loe(dateTime).and(end.gt(begin)));
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = ServiceException.class)
	public SAppointment postponeToFuture(final long idAppointment, final ReadableDuration duration)
			throws ServiceException {
		try {
			return this.postpone(idAppointment, duration, true);
		} catch (AppointmentNotPostponedServiceException e) {
			LOGGER.error("Appointment not postponed to future", e);
			throw new ServiceException(SAppointment.class, e);
		} catch (EntityDoesNotExistsServiceException e2) {
			LOGGER.error("Appointment set pk=%d does not exists", e2);
			throw new ServiceException(SAppointment.class, e2);
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = ServiceException.class)
	public SAppointment postponeToPast(final long idAppointment, final ReadableDuration duration) throws
			ServiceException {
		try {
			return this.postpone(idAppointment, duration, false);
		} catch (AppointmentNotPostponedServiceException e) {
			LOGGER.error("Appointment not postponed to past", e);
			throw new ServiceException(SAppointment.class, e);
		} catch (EntityDoesNotExistsServiceException e2) {
			LOGGER.error("Appointment set pk=%d does not exists", e2);
			throw new ServiceException(SAppointment.class, e2);
		}
	}

	@Override
	public List<SFreeSlot> findSlots(final SFreeSlot.Slot slot, final long... idAppointment) {
		final List<SFreeSlot> freeSlots = this.findSlots(idAppointment);
		return FluentIterable
				.from(freeSlots)
				.filter(new Predicate<SFreeSlot>() {
					@Override
					public boolean apply(
							@Nullable
							final
							SFreeSlot input) {
						assert input != null;
						return input.getSlot().equals(slot);
					}
				})
				.toList();
	}

	@Override
	public List<SFreeSlot> findSlots(final long... idAppointment) {

		final List<SAppointment> appointments = (List<SAppointment>) this.repository
				.findAll(QSAppointment.sAppointment.id.in(this.toLong(idAppointment)));
		final List<SAppointment> allAppointments = (List<SAppointment>) this.repository
				.findAll(QSAppointment.sAppointment.id.notIn(this.toLong(idAppointment)));
		final List<SFreeSlot> freeSlots = new ArrayList<>();

		for (SAppointment appointment : appointments) {
			for (SAppointment nextAppointment : allAppointments) {

				final boolean isAfterNextAppointment = appointment.getEnd().isBefore(nextAppointment.getBegin());
				final boolean isBeforeNextAppointment = appointment.getBegin().isAfter(nextAppointment.getEnd());

				if (isAfterNextAppointment) {
					freeSlots.add(
							new SFreeSlot(appointment.getId(), nextAppointment.getId(), new Duration(nextAppointment
									.getBegin(), appointment.getEnd()), SFreeSlot.Slot.AFTER)
					);
				} else if (isBeforeNextAppointment) {
					freeSlots.add(
							new SFreeSlot(appointment.getId(), nextAppointment.getId(), new Duration(appointment
									.getBegin(), nextAppointment.getEnd()), SFreeSlot.Slot.AFTER)
					);
				} else {
					freeSlots.add(
							new SFreeSlot(appointment.getId(), nextAppointment
									.getId(), new Duration(0), SFreeSlot.Slot.INTERSECT)
					);
				}
			}
		}
		Collections.sort(freeSlots);
		return ImmutableList.copyOf(freeSlots);
	}

	@Override
	public Collection<SUser> findReporters() {
		// TODO tmp solution before introducing ACL
		final QSUser user = QSUser.sUser;
		final EnumPath<SRole> role = user.roles.any().pk.authority.role;
		BooleanExpression predicate = role.in(
				SRole.ROLE_APPOINTMENT_CREATE,
				SRole.ROLE_MECHANIC
		);
		predicate = predicate.and(user.accountNonLocked.eq(true)).and(user.accountNonExpired.eq(true));
		return Collections.unmodifiableList(Lists.newArrayList(this.userRepository.findAll(predicate)));
	}

	@Override
	public Collection<SUser> findReporters(final Pageable pageable) {
		// TODO tmp solution before introducing ACL
		final QSUser user = QSUser.sUser;
		final EnumPath<SRole> role = user.roles.any().pk.authority.role;
		BooleanExpression predicate = role.in(
				SRole.ROLE_APPOINTMENT_CREATE,
				SRole.ROLE_MECHANIC
		);
		predicate = predicate.and(user.accountNonLocked.eq(true)).and(user.accountNonExpired.eq(true));
		return Collections.unmodifiableList(Lists.newArrayList(this.userRepository.findAll(predicate, pageable)));
	}

	@Override
	public Collection<SUser> findAssignees() {
		// TODO tmp solution before introducing ACL
		final QSUser user = QSUser.sUser;
		final EnumPath<SRole> role = user.roles.any().pk.authority.role;
		BooleanExpression predicate = role.in(
				SRole.ROLE_MECHANIC,
				SRole.ROLE_APPOINTMENT_EXECUTE
		);
		predicate = predicate.and(user.accountNonLocked.eq(true)).and(user.accountNonExpired.eq(true));
		return Collections.unmodifiableList(Lists.newArrayList(this.userRepository.findAll(predicate)));
	}

	@Override
	public Collection<SUser> findAssignees(final Pageable pageable) {
		// TODO tmp solution before introducing ACL
		final QSUser user = QSUser.sUser;
		final EnumPath<SRole> role = user.roles.any().pk.authority.role;
		BooleanExpression predicate = role.in(
				SRole.ROLE_MECHANIC,
				SRole.ROLE_APPOINTMENT_EXECUTE
		);
		predicate = predicate.and(user.accountNonLocked.eq(true)).and(user.accountNonExpired.eq(true));
		return Collections.unmodifiableList(Lists.newArrayList(this.userRepository.findAll(predicate, pageable)));
	}

	@Override
	public void isValid(final SAppointment appointment, final Errors errors) {
		if (appointment == null) {
			errors.reject("1", "Appointment is null");
		} else {
			final DateTime begin = appointment.getBegin();
			final DateTime end = appointment.getEnd();
			final int beginHourOfDay = begin.getHourOfDay();
			final int endHourOfDay = end.getHourOfDay();
			if (beginHourOfDay < 8) {
				errors.rejectValue("begin", "Begin hour must not be lower than 8AM");
			}
			if (endHourOfDay > 20) {
				errors.rejectValue("end", "End hour must not be higher than 8PM");
			}
			if (begin.isAfter(end)) {
				errors.rejectValue("begin", "Begin must be lower than end");
				errors.rejectValue("end", "End must be higher than begin");
			} else if (new Duration(end.minus(begin.getMillis()).getMillis()).isShorterThan(new Duration(this.minDiffBetweenDates))) {
				errors.rejectValue("begin", "Minimum time of appointment is shorter than 10 minutes");
			}
			if (begin.getDayOfMonth() != end.getDayOfMonth()) {
				errors.rejectValue("begin", "Appointments must be at the same day");
			} else if (begin.getMonthOfYear() != end.getMonthOfYear()) {
				errors.rejectValue("begin", "Appointments must be at the same month");
			} else if (begin.getYear() != end.getYear()) {
				errors.rejectValue("begin", "Appointments must be at the same year");
			}
		}
	}

	private SUser getMechanic(final long mechanicId) throws EntityDoesNotExistsServiceException {
		final Revision<Integer, SUser> changeRevision = this.userRepository.findLastChangeRevision(mechanicId);
		final SUser mechanic = changeRevision.getEntity();
		if (mechanic == null) {
			throw new EntityDoesNotExistsServiceException(SUser.class, mechanicId);
		}
		if (!mechanic.hasAuthority(SAuthority.fromRole(SRole.ROLE_MECHANIC))) {
			throw new AccessDeniedException(String.format("%s is not in role %s", mechanic.getUsername(), SRole.ROLE_MECHANIC));
		}
		return mechanic;
	}

	private SCar getCar(final long carId) throws EntityDoesNotExistsServiceException {
		final SCar car = this.carRepository.findOne(carId);
		if (car == null) {
			throw new EntityDoesNotExistsServiceException(SCar.class, carId);
		}
		return car;
	}

	private SAppointment postpone(final long idAppointment, final ReadableDuration duration, final boolean toFuture) throws
			EntityDoesNotExistsServiceException,
			AppointmentNotPostponedServiceException {
		final SAppointment appointment = this.findAppointment(idAppointment);
		if (appointment.postpone(duration, toFuture)) {
			return this.repository.saveAndFlush(appointment);
		} else {
			throw new AppointmentNotPostponedServiceException(idAppointment, duration, toFuture);
		}
	}

	private SAppointment findAppointment(final long idAppointment) throws EntityDoesNotExistsServiceException {
		final SAppointment appointment = this.repository.findOne(idAppointment);
		if (appointment == null) {
			try {
				throw new EntityDoesNotExistsServiceException(SAppointment.class, idAppointment);
			} catch (EntityDoesNotExistsServiceException e) {
				LOGGER.error("Appointment set pk=%d does not exists", e);
				throw e;
			}
		}
		return appointment;
	}

	protected class AppointmentNotPostponedServiceException
			extends ServiceException {
		private static final long serialVersionUID = 3655490110996565446L;

		public AppointmentNotPostponedServiceException(final long idAppointment, final ReadableDuration duration, final boolean toFuture) {
			super(SAppointment.class, String
					.format("%s=[id==%d] was not postponed[duration=%s, toFuture=%s]", SAppointment.class
							.getSimpleName(), idAppointment, duration, toFuture));
		}
	}
}
