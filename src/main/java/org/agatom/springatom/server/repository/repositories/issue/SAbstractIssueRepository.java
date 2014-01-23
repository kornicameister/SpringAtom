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

package org.agatom.springatom.server.repository.repositories.issue;

import org.agatom.springatom.server.model.beans.issue.SIssue;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.issue.IssueType;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * {@code SAppointmentRepository} supports CRUD operations, backend with {@link org.springframework.data.jpa.repository.support.Querydsl}
 * support.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public interface SAbstractIssueRepository<T extends SIssue>
        extends SBasicRepository<T, Long> {
    @RestResource(rel = "byType", path = "type")
    Page<T> findByType(@Param(value = "type") IssueType issueType, Pageable pageable);

    @RestResource(rel = "byAssignee", path = "assignee")
    Page<T> findByAssignee(@Param(value = "assignee") SUser assignee, Pageable pageable);

    @RestResource(rel = "byAssigneeLastName", path = "assignee_lastName")
    Page<T> findByAssigneePersonLastNameContaining(@Param(value = "assigneeLastName") String assigneeLastName, Pageable pageable);

    @RestResource(rel = "byReporter", path = "reporter")
    Page<T> findByReporter(@Param(value = "reporter") SUser assignee, Pageable pageable);

    @RestResource(rel = "byReporterLastName", path = "reporter_lastName")
    Page<T> findByReporterPersonLastNameContaining(@Param(value = "reporterLastName") String reporterLastName, Pageable pageable);
}
