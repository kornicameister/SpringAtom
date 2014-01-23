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

package org.agatom.springatom.server.repository.repositories.appointment;

import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.server.model.types.appointment.AppointmentTaskType;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Qualifier(SAppointmentTaskRepository.REPO_NAME)
@RestResource(rel = SAppointmentTaskRepository.REST_REPO_REL, path = SAppointmentTaskRepository.REST_REPO_PATH)
public interface SAppointmentTaskRepository
        extends SBasicRepository<SAppointmentTask, Long> {
    String REPO_NAME      = "AppointmentTaskRepo";
    String REST_REPO_REL  = "rest.appointment.task";
    String REST_REPO_PATH = "appointment_tasks";

    Page<SAppointmentTask> findByAppointment(@Param(value = "appointment") SAppointment appointment, Pageable pageable);

    Page<SAppointmentTask> findByTaskContaining(@Param(value = "task") String taskLike, Pageable pageable);

    Page<SAppointmentTask> findByType(@Param(value = "type") AppointmentTaskType type, Pageable pageable);
}
