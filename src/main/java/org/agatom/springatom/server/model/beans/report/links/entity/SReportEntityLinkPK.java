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

package org.agatom.springatom.server.model.beans.report.links.entity;

import com.google.common.base.Objects;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.beans.report.entity.SReportEntity;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.model.types.report.entity.ReportEntity;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * {@code SReportEntityLinkPK} is designated to uniquely identify
 * the association made between single {@link org.agatom.springatom.server.model.beans.report.SReport} and
 * multiple {@link org.agatom.springatom.server.model.beans.report.entity.SReportEntity}.
 * <p/>
 * It is used as {@link javax.persistence.EmbeddedId} in {@link org.agatom.springatom.server.model.beans.report.links.entity.SReportEntityLink}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Embeddable class SReportEntityLinkPK
        implements Serializable {
    private static final long serialVersionUID = 7327536301126954327L;
    @ManyToOne
    @JoinColumn(name = "pk_reports_rel_r", referencedColumnName = "idSReport")
    private SReport       report;
    @ManyToOne
    @JoinColumn(name = "pk_reports_rel_re", referencedColumnName = "idSReportEntity")
    private SReportEntity reportEntity;

    public SReportEntityLinkPK() {
        super();
    }

    public SReportEntityLinkPK(final Report report, final ReportEntity reportEntity) {
        this.setReport(report);
        this.setReportEntity(reportEntity);
    }

    public SReportEntityLinkPK setReport(final Report report) {
        Assert.isInstanceOf(
                SReport.class, report,
                String.format("Given %s is not valid instance of %s",
                        ClassUtils.getShortName(report.getClass()),
                        ClassUtils.getShortName(SReport.class)
                )
        );
        this.report = (SReport) report;
        return this;
    }

    public SReportEntityLinkPK setReportEntity(final ReportEntity reportEntity) {
        Assert.isInstanceOf(
                SReportEntity.class, reportEntity,
                String.format("Given %s is not valid instance of %s",
                        ClassUtils.getShortName(reportEntity.getClass()),
                        ClassUtils.getShortName(SReportEntity.class)
                )
        );
        this.reportEntity = (SReportEntity) reportEntity;
        return this;
    }

    public SReport getReport() {
        return report;
    }

    public SReportEntity getReportEntity() {
        return reportEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SReportEntityLinkPK that = (SReportEntityLinkPK) o;

        return Objects.equal(this.reportEntity.getId(), that.reportEntity.getId()) &&
                Objects.equal(this.report.getId(), that.report.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reportEntity.getId(), report.getId());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(report)
                      .addValue(reportEntity)
                      .toString();
    }
}
