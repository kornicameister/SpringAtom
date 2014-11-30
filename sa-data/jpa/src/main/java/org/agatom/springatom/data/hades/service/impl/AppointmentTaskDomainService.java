package org.agatom.springatom.data.hades.service.impl;

import org.agatom.springatom.data.hades.model.appointment.NAppointmentTask;
import org.agatom.springatom.data.hades.service.NAppointmentTaskService;
import org.agatom.springatom.data.hades.service.NEnumerationService;
import org.agatom.springatom.data.services.enumeration.EnumerationServiceException;
import org.agatom.springatom.data.services.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-09</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
class AppointmentTaskDomainService
        extends AbstractDomainService<NAppointmentTask>
        implements NAppointmentTaskService {
    private static final Logger              LOGGER                 = LoggerFactory.getLogger(AppointmentTaskDomainService.class);
    private static final String              APPOINTMENT_TASK_TYPES = "APPOINTMENT_TASK_TYPES";
    @Autowired
    private              NEnumerationService enumerationService     = null;

    @Override
    public NAppointmentTask newTask(final String task, final String type) throws ServiceException {
        final NAppointmentTask appointmentTask = new NAppointmentTask();
        appointmentTask.setTask(task);
        try {
            appointmentTask.setType(this.getTaskType(type));
        } catch (EnumerationServiceException exp) {
            LOGGER.error(String.format("Failed to set task type for type=%s", type), exp);
        }
        return appointmentTask;
    }

    private String getTaskType(final String type) throws EnumerationServiceException {
        final String entry = this.enumerationService.getEnumeratedValue(APPOINTMENT_TASK_TYPES, type);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("For(type=%s) retrieved %s", type, entry));
        }
        return entry;
    }
}
