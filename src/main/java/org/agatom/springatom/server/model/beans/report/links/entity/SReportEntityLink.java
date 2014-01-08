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

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.beans.report.column.SReportColumn;
import org.agatom.springatom.server.model.beans.report.entity.SReportEntity;
import org.agatom.springatom.server.model.beans.report.links.column.SReportColumnLink;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.model.types.report.column.ReportColumn;
import org.agatom.springatom.server.model.types.report.column.ReportColumnLink;
import org.agatom.springatom.server.model.types.report.entity.ReportEntity;
import org.agatom.springatom.server.model.types.report.entity.ReportEntityLink;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;

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

@Entity(name = SReportEntityLink.ENTITY_NAME)
@Table(name = SReportEntityLink.TABLE_NAME)
@AssociationOverrides({
        @AssociationOverride(name = "pk.report", joinColumns = @JoinColumn(name = "pk_reports_rel_r")),
        @AssociationOverride(name = "pk.reportEntity", joinColumns = @JoinColumn(name = "pk_reports_rel_re"))
})
public class SReportEntityLink
        implements ReportEntityLink {

    public static final  String ENTITY_NAME      = "SReportEntityLink";
    public static final  String TABLE_NAME       = "reports_rel";
    private static final long   serialVersionUID = -6449957735739375561L;

    @EmbeddedId
    private SReportEntityLinkPK     pk;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SReportColumnLink> columns;

    /**
     * Constructs default {@code SReportEntityLink}.
     * However use {@link org.agatom.springatom.server.model.beans.report.links.entity.SReportEntityLink#newSReportEntityLink(org.agatom.springatom.server.model.types.report.Report,
     * org.agatom.springatom.server.model.types.report.entity.ReportEntity)}
     * in order to properly initialize new {@code SReportEntityLink}
     */
    public SReportEntityLink() {
        this.pk = new SReportEntityLinkPK();
    }

    /**
     * Instantiates a properly initialize instance of {@code SReportEntityLink}
     *
     * @param report
     *         {@link org.agatom.springatom.server.model.types.report.Report} to create a new {@code SReportEntityLink}
     * @param reportEntity
     *         {@link org.agatom.springatom.server.model.types.report.entity.ReportEntity} to create a new {@code
     *         SReportEntityLink}
     *
     * @return properly initialized instance of {@code SReportEntityLink
     *
     * @see org.agatom.springatom.server.model.types.report.Report
     * @see org.agatom.springatom.server.model.types.report.entity.ReportEntity
     */
    public static SReportEntityLink newSReportEntityLink(final Report report, final ReportEntity reportEntity) {
        final SReportEntityLink link = new SReportEntityLink();
        link.pk.setReport(report);
        link.pk.setReportEntity(reportEntity);
        return link;
    }

    @Override
    public Report getReport() {
        return this.pk.getReport();
    }

    @Override
    public ReportEntity getReportEntity() {
        return this.pk.getReportEntity();
    }

    @Override
    public List<ReportColumn> getColumns() {
        this.requireColumns();
        return FluentIterable.from(this.columns)
                             .transform(new Function<SReportColumnLink, ReportColumn>() {
                                 @Nullable
                                 @Override
                                 public ReportColumn apply(@Nullable final SReportColumnLink input) {
                                     assert input != null;
                                     return input.getReportColumn();
                                 }
                             }).toList();
    }

    @Override
    public boolean hasColumn(final String columnName) {
        this.requireColumns();
        return FluentIterable
                .from(this.columns)
                .filter(new Predicate<ReportColumnLink>() {
                    @Override
                    public boolean apply(@Nullable final ReportColumnLink input) {
                        return input != null
                                && input.getReportColumn() != null
                                && StringUtils.hasText(input.getReportColumn().getColumnName())
                                && input.getReportColumn().getColumnName().equals(columnName);
                    }
                })
                .first()
                .isPresent();
    }

    @Override
    public boolean hasColumn(final Class<?> columnClazz) {
        this.requireColumns();
        return FluentIterable
                .from(this.columns)
                .filter(new Predicate<ReportColumnLink>() {
                    @Override
                    public boolean apply(@Nullable final ReportColumnLink input) {
                        return input != null
                                && input.getReportColumn() != null
                                && input.getReportColumn().getColumnClass().equals(columnClazz);
                    }
                })
                .first()
                .isPresent();
    }

    @Override
    public boolean hasColumns() {
        return this.columns != null && !this.columns.isEmpty();
    }

    public SReportEntityLink setReport(final SReport report) {
        this.pk.setReport(report);
        return this;
    }

    public SReportEntityLink setReportEntity(final SReportEntity reportEntity) {
        this.pk.setReportEntity(reportEntity);
        return this;
    }

    public SReportEntityLink setColumns(final List<SReportColumn> columns) {
        Preconditions.checkNotNull(columns);
        for (final SReportColumn reportColumn : columns) {
            this.addColumn(reportColumn);
        }
        return this;
    }

    public SReportColumnLink addColumn(final SReportColumn column) {
        this.requireColumns();
        Assert.notNull(this.pk.getReportEntity(), String
                .format("%s [from %s] can not be null", ClassUtils.getShortName(ReportEntity.class), "this.pk.getReportEntity()"));
        final SReportColumnLink columnLink = new SReportColumnLink(this.pk.getReportEntity(), column);
        if (this.columns.add(columnLink)) {
            return columnLink;
        }
        throw new IllegalStateException(String.format("Failed to add new %s to %s", ClassUtils.getShortName(SReportColumn.class), this));
    }

    private void requireColumns() {
        if (this.columns == null) {
            this.columns = Lists.newLinkedList();
        }
    }

    public void clearColumns() {
        this.columns.clear();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(pk)
                      .toString();
    }
}
