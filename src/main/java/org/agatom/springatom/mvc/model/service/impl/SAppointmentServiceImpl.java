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

package org.agatom.springatom.mvc.model.service.impl;

import com.mysema.query.types.path.DateTimePath;
import org.agatom.springatom.jpa.repositories.*;
import org.agatom.springatom.model.beans.appointment.QSAppointment;
import org.agatom.springatom.model.beans.appointment.QSAppointmentTask;
import org.agatom.springatom.model.beans.appointment.SAppointment;
import org.agatom.springatom.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.model.beans.car.SCar;
import org.agatom.springatom.model.beans.links.SAppointmentWorkerLink;
import org.agatom.springatom.model.beans.meta.QSMetaData;
import org.agatom.springatom.model.beans.meta.SAppointmentTaskType;
import org.agatom.springatom.model.beans.person.mechanic.SMechanic;
import org.agatom.springatom.model.beans.util.SIssueReporter;
import org.agatom.springatom.model.dto.SAppointmentTaskDTO;
import org.agatom.springatom.mvc.model.exceptions.SEntityDoesNotExists;
import org.agatom.springatom.mvc.model.exceptions.SException;
import org.agatom.springatom.mvc.model.service.SAppointmentService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
@SuppressWarnings("SpringJavaAutowiringInspection")
public class SAppointmentServiceImpl
        extends SBasicServiceImpl<SAppointment, Long, SAppointmentRepository>
        implements SAppointmentService {
    private static final String BAD_ARG_MSG                = "No %s provided, could not execute call";
    private static final String ERROR_MESSAGE_SD_GT_ED_MSG = "startDate must be greater than endDate";
    private static final Logger LOGGER                     = Logger.getLogger(SAppointmentServiceImpl.class);
    protected SAppointmentRepository repository;
    @Autowired
    SAppointmentTaskRepository       taskRepository;
    @Autowired
    SCarRepository                   carRepository;
    @Autowired
    SMechanicRepository              mechanicRepository;
    @Autowired
    SAppointmentWorkerLinkRepository workerLinkRepository;
    @Autowired
    SMetaDataRepository metaDataRepository;

    @Override
    @Autowired
    public void autoWireRepository(final SAppointmentRepository repo) {
        super.autoWireRepository(repo);
        this.repository = repo;
    }

    @Override
    public SAppointment newAppointment(final ReadableInterval interval,
                                       final Long carId,
                                       final Long assigneeId,
                                       final Long reportedId,
                                       final SAppointmentTaskDTO... tasks) throws SException {
        checkArgument(carId != null, String.format(BAD_ARG_MSG, "SCar#getId()"));
        checkArgument(assigneeId != null, String.format(BAD_ARG_MSG, "SMechanic#getId()"));

        final SCar car = this.carRepository.findOne(carId);
        final SMechanic assignee = this.mechanicRepository.findOne(assigneeId);
        final SMechanic reporter = this.mechanicRepository.findOne(reportedId);
        if (car == null) {
            throw new SEntityDoesNotExists(SCar.class, carId);
        }
        if (assignee == null) {
            throw new SEntityDoesNotExists(SMechanic.class, assigneeId);
        }
        if (reporter == null) {
            throw new SEntityDoesNotExists(SMechanic.class, reportedId);
        }

        final SAppointment appointment = this.repository.save(
                new SAppointment()
                        .setCar(car)
                        .setInterval(interval)
                        .addTask(this.convertTasks(tasks))
        );
        final SAppointmentWorkerLink workerLink = this.workerLinkRepository.saveAndFlush(
                new SAppointmentWorkerLink()
                        .setAppointment(appointment)
                        .setAssignee(assignee)
                        .setReporter(
                                new SIssueReporter()
                                        .setAssigned(DateTime.now())
                                        .setMechanic(reporter)
                        )
        );


        return workerLink.getAppointment();
    }

    private SAppointmentTask[] convertTasks(final SAppointmentTaskDTO[] tasks) {
        final SAppointmentTask[] appointmentTasks = new SAppointmentTask[tasks.length];
        for (int i = 0, tasksLength = tasks.length ; i < tasksLength ; i++) {
            final SAppointmentTaskDTO taskDTO = tasks[i];
            appointmentTasks[i] = (SAppointmentTask) new SAppointmentTask()
                    .setTask(taskDTO.getTask())
                    .setMetaInformation((SAppointmentTaskType) this.metaDataRepository
                            .findOne(QSMetaData.sMetaData.type.eq(taskDTO.getType())));
        }
        return appointmentTasks;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {SEntityDoesNotExists.class, IllegalArgumentException.class})
    public SAppointment addTask(final Long idAppointment, final SAppointmentTask... tasks) throws SEntityDoesNotExists {
        checkArgument(idAppointment != null, String.format(BAD_ARG_MSG, "SAppointment#getId()"));
        checkArgument(tasks.length != 0, String.format(BAD_ARG_MSG, "SAppointmentTask#getId()"));
        final SAppointment appointment = this.getAppointment(idAppointment);
        appointment.addTask(tasks);
        return this.repository.saveAndFlush(appointment);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {SEntityDoesNotExists.class, IllegalArgumentException.class})
    public SAppointment removeTask(final Long idAppointment, final Long... tasksId) throws SEntityDoesNotExists {
        checkArgument(idAppointment != null, String.format(BAD_ARG_MSG, "SAppointment#getId()"));
        final SAppointment appointment = this.getAppointment(idAppointment);
        final List<SAppointmentTask> tasks = (List<SAppointmentTask>) this.taskRepository
                .findAll(QSAppointmentTask.sAppointmentTask.id.in(tasksId));
        appointment.removeTask(tasks.toArray(new SAppointmentTask[tasks.size()]));
        return this.repository.saveAndFlush(appointment);
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public SAppointment findByTask(final Long... tasks) {
        checkArgument(tasks.length != 0, String.format(BAD_ARG_MSG, "SAppointmentTask#getId()"));
        return this.repository.findOne(QSAppointment.sAppointment.tasks.any().id.in(tasks));
    }

    @Override
    public List<SAppointment> findByCar(final Long carId) {
        final QSAppointment sAppointment = QSAppointment.sAppointment;
        return (List<SAppointment>) this.repository.findAll(sAppointment.car.id.eq(carId));
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public List<SAppointment> findOne(final DateTime startDate, final DateTime endDate) {
        checkArgument(startDate.isBefore(endDate), ERROR_MESSAGE_SD_GT_ED_MSG);
        final DateTimePath<DateTime> begin = QSAppointment.sAppointment.begin;
        final DateTimePath<DateTime> end = QSAppointment.sAppointment.end;
        return (List<SAppointment>) this.repository.findAll(begin.eq(startDate).and(end.eq(endDate)));
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
    @Transactional(readOnly = false, rollbackFor = SException.class)
    public SAppointment postponeToFuture(final Long idAppointment, final ReadableDuration duration)
            throws SException {
        try {
            return this.postpone(idAppointment, duration, true);
        } catch (SAppointmentNotPostponedException e) {
            LOGGER.error("Appointment not postponed to future", e);
            throw new SException(SAppointment.class, e);
        } catch (SEntityDoesNotExists e2) {
            LOGGER.error("Appointment set pk=%d does not exists", e2);
            throw new SException(SAppointment.class, e2);
        }
    }

    public SAppointment postpone(final Long idAppointment, final ReadableDuration duration, final boolean toFuture) throws
            SEntityDoesNotExists,
            SAppointmentNotPostponedException {
        final SAppointment appointment = this.getAppointment(idAppointment);
        if (appointment.postpone(duration, toFuture)) {
            return this.repository.saveAndFlush(appointment);
        } else {
            throw new SAppointmentNotPostponedException(idAppointment, duration, toFuture);
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = SException.class)
    public SAppointment postponeToPast(final Long idAppointment, final ReadableDuration duration) throws
            SException {
        try {
            return this.postpone(idAppointment, duration, false);
        } catch (SAppointmentNotPostponedException e) {
            LOGGER.error("Appointment not postponed to past", e);
            throw new SException(SAppointment.class, e);
        } catch (SEntityDoesNotExists e2) {
            LOGGER.error("Appointment set pk=%d does not exists", e2);
            throw new SException(SAppointment.class, e2);
        }
    }

    private SAppointment getAppointment(final Long idAppointment) throws SEntityDoesNotExists {
        final SAppointment appointment = this.repository.findOne(idAppointment);
        if (appointment == null) {
            try {
                throw new SEntityDoesNotExists(SAppointment.class, idAppointment);
            } catch (SEntityDoesNotExists e) {
                LOGGER.error("Appointment set pk=%d does not exists", e);
                throw e;
            }
        }
        return appointment;
    }

    public class SAppointmentNotPostponedException extends SException {
        public SAppointmentNotPostponedException(final Long idAppointment, final ReadableDuration duration, final boolean toFuture) {
            super(SAppointment.class, String
                    .format("%s=[id==%d] was not postponed[duration=%s, toFuture=%s]", SAppointment.class
                            .getSimpleName(), idAppointment, duration, toFuture));
        }
    }
}
