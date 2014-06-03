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

package org.agatom.springatom.server.service.domain;

import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.web.rbuilder.data.exception.ReportBuilderServiceException;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>SReportService interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SReportService
		extends SService<SReport, Long, Integer> {

	/**
	 * <p>getReport.</p>
	 *
	 * @param reportId a {@link java.lang.Long} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 *
	 * @throws org.agatom.springatom.web.rbuilder.data.exception.ReportBuilderServiceException if any.
	 */
	@NotNull
	SReport getReport(@Min(value = 1) final Long reportId) throws ReportBuilderServiceException;

	/**
	 * <p>findByTitle.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 *
	 * @throws org.agatom.springatom.web.rbuilder.data.exception.ReportBuilderServiceException if any.
	 */
	@NotNull
	SReport findByTitle(@NotNull @Length(min = 1) final String title) throws ReportBuilderServiceException;
}
