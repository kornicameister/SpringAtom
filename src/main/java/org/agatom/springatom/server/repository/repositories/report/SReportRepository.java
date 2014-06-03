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
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

/**
 * <p>SReportRepository interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@RepositoryRestResource(itemResourceRel = SReportRepository.REST_REPO_REL, path = SReportRepository.REST_REPO_PATH)
public interface SReportRepository
		extends SRepository<SReport, Long, Integer> {
	/** Constant <code>REPO_NAME="reportsRepository"</code> */
	String REPO_NAME      = "reportsRepository";
	/** Constant <code>REST_REPO_REL="rest.reports"</code> */
	String REST_REPO_REL  = "rest.reports";
	/** Constant <code>REST_REPO_PATH="reports"</code> */
	String REST_REPO_PATH = "reports";

	/**
	 * <p>findByTitle.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 *
	 * @throws java.lang.Exception if any.
	 */
	@Lock(value = LockModeType.READ)
	@Transactional(rollbackFor = Exception.class)
	SReport findByTitle(@Param(value = "title") final String title) throws Exception;
}
