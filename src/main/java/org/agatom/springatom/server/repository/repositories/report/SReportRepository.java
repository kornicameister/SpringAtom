/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.server.repository.repositories.report;

import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.repository.SRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Qualifier(value = SReportRepository.REPO_NAME)
@RestResource(rel = SReportRepository.REST_REPO_REL, path = SReportRepository.REST_REPO_PATH)
public interface SReportRepository
        extends SRepository<SReport, Long, Integer> {
    String REPO_NAME      = "reportsRepository";
    String REST_REPO_REL  = "rest.reports";
    String REST_REPO_PATH = "reports";

    @Lock(value = LockModeType.READ)
    @Transactional(rollbackFor = Exception.class)
    SReport findByTitle(@Param(value = "title") final String title) throws Exception;
}
