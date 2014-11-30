package org.agatom.springatom.data.hades.service;

import org.agatom.springatom.data.hades.model.appointment.NAppointmentTask;
import org.agatom.springatom.data.services.SAppointmentTaskService;
import org.agatom.springatom.data.services.exception.ServiceException;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-09</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface NAppointmentTaskService
        extends SAppointmentTaskService<NAppointmentTask> {

    NAppointmentTask newTask(final String task, final String type) throws ServiceException;

}
