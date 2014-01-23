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

package org.agatom.springatom.server.service.domain.impl;

import org.agatom.springatom.server.model.beans.report.QSReport;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.service.domain.SReportService;
import org.agatom.springatom.web.rbuilder.exception.ReportBuilderServiceException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(value = SReportServiceImpl.SERVICE_NAME)
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
public class SReportServiceImpl
        extends SServiceImpl<SReport, Long, Integer>
        implements SReportService {
    public static final String SERVICE_NAME = "reportService";
    private static final String CACHE_NAME   = "reports";

    @Override
    @Cacheable(value = CACHE_NAME, key = "#reportId", condition = "#reportId > 0")
    public SReport getReport(final Long reportId) throws ReportBuilderServiceException {
        try {
            Assert.notNull(reportId, "Report#ID can not be null");
            return this.findOne(reportId);
        } catch (Exception e) {
            throw new ReportBuilderServiceException(String.format("Failed to retrieve report for ID=%d", reportId), e);
        }
    }

    @Override
    @Cacheable(value = CACHE_NAME)
    public SReport getReport(final String title) throws ReportBuilderServiceException {
        try {
            Assert.notNull(title, "Report#title can not be null");
            return this.repository.findOne(QSReport.sReport.title.eq(title));
        } catch (Exception e) {
            throw new ReportBuilderServiceException(String.format("Failed to retrieve report for title=%s", title), e);
        }
    }

}
