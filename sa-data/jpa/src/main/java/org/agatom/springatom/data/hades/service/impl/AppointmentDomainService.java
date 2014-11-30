package org.agatom.springatom.data.hades.service.impl;

import org.agatom.springatom.data.hades.model.appointment.NAppointment;
import org.agatom.springatom.data.hades.model.appointment.NAppointmentTask;
import org.agatom.springatom.data.hades.model.enumeration.NEnumeration;
import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.service.NAppointmentService;
import org.agatom.springatom.data.hades.service.NAppointmentTaskService;
import org.agatom.springatom.data.hades.service.NEnumerationService;
import org.joda.time.DateTime;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
class AppointmentDomainService
        extends AbstractDomainService<NAppointment>
        implements NAppointmentService {
    private static final String                  APPOINTMENT_TASK_TYPES = "APPOINTMENT_TASK_TYPES";
    @Autowired
    private              NEnumerationService     enumerationService     = null;
    @Autowired
    private              NAppointmentTaskService appointmentTaskService = null;

    @Override
    public NAppointment newAppointment(final ReadableInterval interval, final long carId, final long assigneeId, final long reporterId, final NAppointmentTask... tasks) throws Exception {
        return null;
    }

    @Override
    public NAppointment addTask(final long idAppointment, final NAppointmentTask... tasks) throws Exception {
        return null;
    }

    @Override
    public NAppointment addTask(final NAppointment appointment, final Iterable<NAppointmentTask> tasks) throws Exception {
        return null;
    }

    @Override
    public NAppointmentTask createTask(final String task, final String type) throws Exception {
        return this.appointmentTaskService.newTask(task, type);
    }

    @Override
    public List<NAppointmentTask> findTasks(final long idAppointment) {
        return null;
    }

    @Override
    public NAppointment removeTask(final long idAppointment, final long... tasksId) throws Exception {
        return null;
    }

    @Override
    public NAppointment findByTask(final long... tasks) {
        return null;
    }

    @Override
    public List<NAppointment> findByCar(final long carId) {
        return null;
    }

    @Override
    public List<NAppointment> findBetween(final DateTime startDate, final DateTime endDate) {
        return null;
    }

    @Override
    public List<NAppointment> findBetween(final DateTime startDate, final DateTime endDate, final boolean currentUserOnly) throws Exception {
        return null;
    }

    @Override
    public List<NAppointment> findLater(final DateTime dateTime) {
        return null;
    }

    @Override
    public List<NAppointment> findEarlier(final DateTime dateTime) {
        return null;
    }

    @Override
    public NAppointment postponeToFuture(final long idAppointment, final ReadableDuration duration) throws Exception {
        return null;
    }

    @Override
    public NAppointment postponeToPast(final long idAppointment, final ReadableDuration duration) throws Exception {
        return null;
    }

    @Override
    public Collection<NUser> findReporters() {
        return null;
    }

    @Override
    public Collection<NUser> findAssignees() {
        return null;
    }

    @Override
    public NEnumeration getAppointmentTypes() throws Exception {
        return this.enumerationService.getEnumeration(APPOINTMENT_TASK_TYPES);
    }
}
