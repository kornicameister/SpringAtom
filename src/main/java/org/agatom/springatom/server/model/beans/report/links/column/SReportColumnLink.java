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
import org.agatom.springatom.server.model.types.report.column.ReportColumn;
import org.agatom.springatom.server.model.types.report.column.ReportColumnLink;
import org.agatom.springatom.server.model.types.report.entity.ReportEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * {@code SReportEntityLink} provides actual information about association between:
 * <ul>
 * <li>
 * {@link org.agatom.springatom.server.model.beans.report.SReport} single instance
 * </li>
 * <li>
 * {@link org.agatom.springatom.server.model.beans.report.entity.SReportEntity} multiple instances used
 * in single {@link org.agatom.springatom.server.model.beans.report.SReport}
 * </li>
 * </ul>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Entity(name = SReportColumnLink.ENTITY_NAME)
@Table(name = SReportColumnLink.TABLE_NAME)
@AssociationOverrides({
        @AssociationOverride(name = "pk.reportEntity", joinColumns = @JoinColumn(name = "pk_reports_rcl_re")),
        @AssociationOverride(name = "pk.reportColumn", joinColumns = @JoinColumn(name = "pk_reports_rcl_c"))
})
public class SReportColumnLink
        implements ReportColumnLink {

    public static final  String ENTITY_NAME      = "SReportColumnLink";
    public static final  String TABLE_NAME       = "reports_rcl";
    private static final long   serialVersionUID = -4384782448821189339L;

    @EmbeddedId
    private SReportColumnLinkPK pk;

    @NotNull
    @Length(min = 2, max = 50)
    @Column(name = "reports_rcl_header", nullable = false, updatable = false, length = 50)
    private String   header = null;
    @NotNull
    @Range(min = 1, max = 50)
    @Column(name = "reports_rcl_width", nullable = false, updatable = true)
    private Long     width  = 50l;
    @NotNull
    @Length(min = 10, max = 300)
    @Column(name = "reports_rcl_type", nullable = false, insertable = true, updatable = true, length = 300)
    private Class<?> type   = String.class;

    public SReportColumnLink() {
        this.pk = new SReportColumnLinkPK();
    }

    public SReportColumnLink(final ReportEntity reportEntity, final ReportColumn reportColumn) {
        this.pk = new SReportColumnLinkPK(reportEntity, reportColumn);
    }

    @Override
    public ReportEntity getReportEntityLink() {
        return this.pk.getReportEntity();
    }

    @Override
    public ReportColumn getReportColumn() {
        return this.pk.getReportColumn();
    }

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public Long getWidth() {
        return width;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    public SReportColumnLink setReportColumn(final ReportColumn reportColumn) {
        this.pk.setReportColumn(reportColumn);
        return this;
    }

    public SReportColumnLink setReportEntityLink(final ReportEntity reportEntityLink) {
        this.pk.setReportEntity(reportEntityLink);
        return this;
    }

    public SReportColumnLink setHeader(final String header) {
        this.header = header;
        return this;
    }

    public SReportColumnLink setWidth(final Long width) {
        this.width = width;
        return this;
    }

    public SReportColumnLink setType(final Class<?> type) {
        this.type = type;
        return this;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(pk)
                      .toString();
    }
}
