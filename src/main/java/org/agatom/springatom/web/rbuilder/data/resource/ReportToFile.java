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

package org.agatom.springatom.web.rbuilder.data.resource;

import net.sf.jasperreports.engine.JasperReport;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;

/**
 * {@code ReportToFile} supplies holder carrying information regarding:
 * <ul>
 * <li>{@link org.agatom.springatom.server.model.types.report.Report}</li>
 * <li>{@link net.sf.jasperreports.engine.JasperReport}</li>
 * <li>{@link org.agatom.springatom.web.rbuilder.ReportConfiguration}</li>
 * </ul>
 * <p/>
 * These information are required to persist complete object of a {@link org.agatom.springatom.server.model.types.report.Report}
 * along with compiled {@link net.sf.jasperreports.engine.JasperReport} and serialized {@link org.agatom.springatom.web.rbuilder.ReportConfiguration}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public final class ReportToFile {
    private Report              report;
    private JasperReport        jasperReport;
    private ReportConfiguration reportConfiguration;

    public static ReportToFile newReportToFile(final Report report, final JasperReport jasperReport, final ReportConfiguration reportConfiguration) {
        final ReportToFile reportToFile = new ReportToFile();
        reportToFile.report = report;
        reportToFile.jasperReport = jasperReport;
        reportToFile.reportConfiguration = reportConfiguration;
        return reportToFile;
    }

    public Report getReport() {
        return report;
    }

    public JasperReport getJasperReport() {
        return jasperReport;
    }

    public ReportConfiguration getReportConfiguration() {
        return reportConfiguration;
    }
}
