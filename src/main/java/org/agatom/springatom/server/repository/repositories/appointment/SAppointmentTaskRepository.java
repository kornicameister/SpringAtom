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
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * <p>SAppointmentTaskRepository interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Qualifier(SAppointmentTaskRepository.REPO_NAME)
@RepositoryRestResource(itemResourceRel = SAppointmentTaskRepository.REST_REPO_REL, path = SAppointmentTaskRepository.REST_REPO_PATH)
public interface SAppointmentTaskRepository
		extends SBasicRepository<SAppointmentTask, Long> {
	/** Constant <code>REPO_NAME="AppointmentTaskRepo"</code> */
	String REPO_NAME      = "AppointmentTaskRepo";
	/** Constant <code>REST_REPO_REL="rest.appointment.task"</code> */
	String REST_REPO_REL  = "rest.appointment.task";
	/** Constant <code>REST_REPO_PATH="appointment_tasks"</code> */
	String REST_REPO_PATH = "appointment_tasks";

	/**
	 * <p>findByAppointment.</p>
	 *
	 * @param appointment a {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} object.
	 * @param pageable    a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SAppointmentTask> findByAppointment(@Param(value = "appointment") SAppointment appointment, Pageable pageable);

	/**
	 * <p>findByTaskContaining.</p>
	 *
	 * @param taskLike a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SAppointmentTask> findByTaskContaining(@Param(value = "task") String taskLike, Pageable pageable);

	/**
	 * <p>findByType.</p>
	 *
	 * @param type     a {@link org.agatom.springatom.server.model.types.appointment.AppointmentTaskType} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SAppointmentTask> findByType(@Param(value = "type") AppointmentTaskType type, Pageable pageable);
}
