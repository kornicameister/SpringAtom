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

package org.agatom.springatom.data.hades.repo.repositories.appointment;

import org.agatom.springatom.data.hades.model.appointment.NAppointment;
import org.agatom.springatom.data.hades.model.appointment.NAppointmentTask;
import org.agatom.springatom.data.hades.repo.NRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * <p>SAppointmentTaskRepository interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@RepositoryRestResource(itemResourceRel = NAppointmentTaskRepository.REST_REPO_REL, path = NAppointmentTaskRepository.REST_REPO_PATH)
public interface NAppointmentTaskRepository
        extends NRepository<NAppointmentTask> {
    /** Constant <code>REST_REPO_REL="rest.appointment.task"</code> */
    String REST_REPO_REL  = "rest.appointment.task";
    /** Constant <code>REST_REPO_PATH="appointment_tasks"</code> */
    String REST_REPO_PATH = "appointment_tasks";

    Page<NAppointmentTask> findByAppointment(@Param(value = "appointment") NAppointment appointment, Pageable pageable);

    Page<NAppointmentTask> findByTaskContaining(@Param(value = "task") String taskLike, Pageable pageable);

}
