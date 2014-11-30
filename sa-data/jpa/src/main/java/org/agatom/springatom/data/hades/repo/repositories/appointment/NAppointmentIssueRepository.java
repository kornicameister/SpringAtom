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
import org.agatom.springatom.data.hades.model.appointment.NAppointmentIssue;
import org.agatom.springatom.data.hades.repo.repositories.issue.NAbstractIssueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(itemResourceRel = NAppointmentIssueRepository.REST_REPO_REL, path = NAppointmentIssueRepository.REST_REPO_PATH)
public interface NAppointmentIssueRepository
        extends NAbstractIssueRepository<NAppointmentIssue> {
    /** Constant <code>REST_REPO_REL="rest.appointment.issue"</code> */
    String REST_REPO_REL  = "rest.appointment.issue";
    /** Constant <code>REST_REPO_PATH="appointment_issues"</code> */
    String REST_REPO_PATH = "appointment_issues";

    Page<NAppointmentIssue> findByAppointment(@Param(value = "appointment") NAppointment appointment, Pageable pageable);
}
