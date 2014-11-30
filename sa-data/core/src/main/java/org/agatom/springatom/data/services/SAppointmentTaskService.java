package org.agatom.springatom.data.services;

import org.agatom.springatom.data.services.exception.ServiceException;
import org.agatom.springatom.data.types.appointment.AppointmentTask;
import org.springframework.data.domain.Persistable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-09</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Validated
public interface SAppointmentTaskService<T extends AppointmentTask & Persistable<Long>>
        extends SDomainService<T> {

    T newTask(@NotNull String task, @NotNull String type) throws ServiceException;

}
