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

package org.agatom.springatom.web.rbuilder.data.service;

import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.ReportRepresentation;
import org.agatom.springatom.web.rbuilder.ReportViewDescriptor;
import org.agatom.springatom.web.rbuilder.data.exception.ReportBuilderServiceException;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * <p>ReportBuilderService interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ReportBuilderService {

	/**
	 * <p>getAvailableRepresentations.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	@NotNull
	Map<String, ReportRepresentation> getAvailableRepresentations();

	/**
	 * <p>newReportInstance.</p>
	 *
	 * @param reportConfiguration a {@link org.agatom.springatom.web.rbuilder.ReportConfiguration} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.report.Report} object.
	 *
	 * @throws org.agatom.springatom.web.rbuilder.data.exception.ReportBuilderServiceException if any.
	 */
	@NotNull
	Report newReportInstance(@NotNull final ReportConfiguration reportConfiguration) throws ReportBuilderServiceException;

	/**
	 * <p>populateReportViewDescriptor.</p>
	 *
	 * @param reportId   a {@link java.lang.Long} object.
	 * @param format     a {@link java.lang.String} object.
	 * @param descriptor a {@link org.agatom.springatom.web.rbuilder.ReportViewDescriptor} object.
	 *
	 * @throws org.agatom.springatom.web.rbuilder.data.exception.ReportBuilderServiceException if any.
	 */
	void populateReportViewDescriptor(
			@Min(value = 1) final Long reportId, @Length(min = 1) final String format, @NotNull final ReportViewDescriptor descriptor) throws
			ReportBuilderServiceException;
}
