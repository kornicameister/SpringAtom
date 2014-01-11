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
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.repository.repositories.report.SReportRepository;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.ReportRepresentation;
import org.agatom.springatom.web.rbuilder.ReportWrapper;
import org.agatom.springatom.web.rbuilder.exception.ReportBuilderServiceException;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public interface ReportBuilderService
        extends SService<SReport, Long, Integer, SReportRepository> {

    @NotNull
    Map<String, ReportRepresentation> getAvailableRepresentations();

    Report deleteReport(@NotNull @Min(value = 1) final Long pk) throws ReportBuilderServiceException;

    @NotNull
    Report save(@NotNull final ReportConfiguration reportConfiguration, @NotNull final SReport report) throws ReportBuilderServiceException;

    @NotNull
    SReport getReport(@Min(value = 1) final Long reportId) throws ReportBuilderServiceException;

    @NotNull
    SReport getReport(@NotNull @Length(min = 1) final String title) throws ReportBuilderServiceException;

    @NotNull
    ReportWrapper getReportWrapper(@Min(value = 1) final Long reportId, @NotNull final String format) throws ReportBuilderServiceException;
}
