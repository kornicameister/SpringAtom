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
import org.agatom.springatom.server.model.types.issue.IssueType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * {@code SAppointmentRepository} supports CRUD operations, backend with {@link org.springframework.data.jpa.repository.support.Querydsl}
 * support.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Qualifier(value = SIssueRepository.REPO_NAME)
@RestResource(rel = SIssueRepository.REST_REPO_REL, path = SIssueRepository.REST_REPO_PATH)
public interface SIssueRepository
        extends SAbstractIssueRepository<SIssue> {
    String REPO_NAME      = "IssuesRepository";
    String REST_REPO_REL  = "rest.issue";
    String REST_REPO_PATH = "issues";

    @RestResource(rel = "byTypeAsList", path = "byType_list")
    @Query(name = "byTypeAsListQuery", value = "select si from SIssue as si where si.type=:type")
    List<SIssue> findByTypeAsList(@Param(value = "type") IssueType issueType);
}
