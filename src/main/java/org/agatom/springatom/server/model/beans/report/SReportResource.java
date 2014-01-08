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

package org.agatom.springatom.server.model.beans.report;

import com.google.common.base.Objects;
import org.agatom.springatom.server.model.types.report.resource.ReportResource;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable class SReportResource
        implements ReportResource {
    private static final long serialVersionUID = -4655265596984988537L;

    @NotNull
    @Length(min = 5, max = 200)
    @Column(name = "report_path", nullable = false, unique = false, updatable = true, insertable = true, length = 200)
    protected String reportPath;

    public SReportResource setReportPath(final String reportPath) {
        this.reportPath = reportPath;
        return this;
    }

    @Override
    public String getFilename() {
        return StringUtils.getFilename(reportPath);
    }

    @Override
    public String getExtension() {
        return StringUtils.getFilenameExtension(reportPath);
    }

    @Override
    public String getPath() {
        return this.reportPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SReportResource that = (SReportResource) o;

        return Objects.equal(this.reportPath, that.reportPath);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reportPath);
    }
}
