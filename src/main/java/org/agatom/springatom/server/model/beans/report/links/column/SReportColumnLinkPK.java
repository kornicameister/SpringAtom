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

package org.agatom.springatom.server.model.beans.report.links.column;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.agatom.springatom.server.model.beans.report.column.SReportColumn;
import org.agatom.springatom.server.model.beans.report.entity.SReportEntity;
import org.agatom.springatom.server.model.types.report.column.ReportColumn;
import org.agatom.springatom.server.model.types.report.entity.ReportEntity;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * {@code SReportEntityLinkPK} is designated to uniquely identify
 * the association made between single {@link org.agatom.springatom.server.model.beans.report.SReport} and
 * multiple {@link org.agatom.springatom.server.model.beans.report.entity.SReportEntity}.
 * <p/>
 * It is used as {@link javax.persistence.EmbeddedId} in {@link SReportColumnLink}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Embeddable class SReportColumnLinkPK
        implements Serializable,
                   Comparable<SReportColumnLinkPK> {

    private static final long serialVersionUID = 7415537324216369079L;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pk_reports_rcl_re", referencedColumnName = "idSReportEntity")
    private SReportEntity reportEntity;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pk_reports_rcl_c", referencedColumnName = "idSReportColumn")
    private SReportColumn reportColumn;

    public SReportColumnLinkPK() {
        super();
    }

    public SReportColumnLinkPK(final ReportEntity reportEntity, final ReportColumn reportColumn) {
        this.setReportColumn(reportColumn);
        this.setReportEntity(reportEntity);
    }

    public SReportColumnLinkPK setReportEntity(final ReportEntity reportEntity) {
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

    public ReportEntity getReportEntity() {
        return reportEntity;
    }

    public SReportColumnLinkPK setReportColumn(final ReportColumn reportColumn) {
        Assert.isInstanceOf(
                SReportColumn.class, reportColumn,
                String.format("Given %s is not valid instance of %s",
                        ClassUtils.getShortName(reportColumn.getClass()),
                        ClassUtils.getShortName(SReportColumn.class)
                )
        );
        this.reportColumn = (SReportColumn) reportColumn;
        return this;
    }

    public ReportColumn getReportColumn() {
        return reportColumn;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(reportEntity)
                      .addValue(reportColumn)
                      .toString();
    }


    @Override
    public int compareTo(@Nonnull final SReportColumnLinkPK o) {
        return ComparisonChain.start()
                              .compare(Boolean.valueOf(this.reportColumn.isNew()), Boolean.valueOf(o.reportColumn.isNew()))
                              .compare(Boolean.valueOf(this.reportEntity.isNew()), Boolean.valueOf(o.reportEntity.isNew()))
                              .compare(this.reportColumn.getId(), o.reportColumn.getId())
                              .compare(this.reportEntity.getId(), o.reportEntity.getId())
                              .result();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SReportColumnLinkPK that = (SReportColumnLinkPK) o;

        return Objects.equal(this.reportEntity.getId(), that.reportEntity.getId()) &&
                Objects.equal(this.reportColumn.getId(), that.reportColumn.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reportEntity.getId(), reportColumn.getId());
    }
}
