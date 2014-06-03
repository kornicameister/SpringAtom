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
import org.agatom.springatom.server.model.types.report.ReportResource;
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
@Embeddable
class SReportResource
		implements ReportResource {
	private static final long serialVersionUID = -4655265596984988537L;

	@NotNull
	@Length(min = 5, max = 300)
	@Column(name = "report_jasper_path", nullable = false, unique = false, updatable = true, insertable = true, length = 300)
	protected String jasperPath;
	@NotNull
	@Length(min = 5, max = 300)
	@Column(name = "report_cfg_path", nullable = false, unique = false, updatable = true, insertable = true, length = 300)
	protected String configurationPath;

	/** {@inheritDoc} */
	@Override
	public String getJasperPath() {
		return this.jasperPath;
	}

	/**
	 * <p>Setter for the field <code>jasperPath</code>.</p>
	 *
	 * @param reportPath a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReportResource} object.
	 */
	public SReportResource setJasperPath(final String reportPath) {
		this.jasperPath = reportPath;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getJasperFilename() {
		return StringUtils.getFilename(this.jasperPath);
	}

	/** {@inheritDoc} */
	@Override
	public String getJasperExtension() {
		return StringUtils.getFilenameExtension(this.jasperPath);
	}

	/** {@inheritDoc} */
	@Override
	public String getConfigurationPath() {
		return this.configurationPath;
	}

	/**
	 * <p>Setter for the field <code>configurationPath</code>.</p>
	 *
	 * @param configurationPath a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReportResource} object.
	 */
	public SReportResource setConfigurationPath(final String configurationPath) {
		this.configurationPath = configurationPath;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getConfigurationFilename() {
		return StringUtils.getFilename(this.configurationPath);
	}

	/** {@inheritDoc} */
	@Override
	public String getConfigurationExtension() {
		return StringUtils.getFilenameExtension(this.configurationPath);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(jasperPath, configurationPath);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SReportResource that = (SReportResource) o;

		return Objects.equal(this.jasperPath, that.jasperPath) &&
				Objects.equal(this.configurationPath, that.configurationPath);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(jasperPath)
				.addValue(configurationPath)
				.toString();
	}
}
