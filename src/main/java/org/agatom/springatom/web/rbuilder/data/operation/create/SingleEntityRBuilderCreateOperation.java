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

package org.agatom.springatom.web.rbuilder.data.operation.create;

import net.sf.jasperreports.engine.JasperReport;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.exception.InSaveReportCreateOperationException;
import org.agatom.springatom.web.rbuilder.data.exception.ReportGenerationException;
import org.agatom.springatom.web.rbuilder.data.service.JasperBuilderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Properties;

/**
 * {@link org.agatom.springatom.web.rbuilder.data.operation.create.SingleEntityRBuilderCreateOperation} is an implementation
 * of {ReportCreateOperation} focused on handling the situation where only one {@link javax.persistence.Entity}
 * has been selected to generate report from.
 * <p/>
 * This implementation is not capable of handling the situation when {@link org.agatom.springatom.web.rbuilder.ReportConfiguration}
 * populated elsewhere.
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */

@Component
@Description("Handles logic of creating new report instance if only one Entity has been selected.")
public class SingleEntityRBuilderCreateOperation
		extends AbstractRBuilderCreateOperation {

	private final static Logger LOGGER = Logger.getLogger(SingleEntityRBuilderCreateOperation.class);

	@Autowired
	@Qualifier("concatenatedReportsBuilder")
	private JasperBuilderService dynamicBuilder;

	/** {@inheritDoc} */
	@Override
	protected boolean preValidate(final ReportConfiguration reportConfiguration) {
		return reportConfiguration.getSize() == 1;
	}

	/** {@inheritDoc} */
	@Override
	protected Report createReport(
			final ReportConfiguration reportConfiguration,
			final SUser user,
			final Locale locale,
			final Properties propertiesHolder
	) throws InSaveReportCreateOperationException {
		try {
			LOGGER.info(String.format("Saving using %s", reportConfiguration));

			final SReport report = this.newReport(reportConfiguration);
			final RBuilderEntity entity = this.getEntity(reportConfiguration);

			report.setReportedClass(entity.getJavaClass());
			report.setDynamic(false);

			return report;

		} catch (Exception exception) {
			LOGGER.error(String.format("Failed to save %s to file", reportConfiguration), exception);
			throw new InSaveReportCreateOperationException("Failed to persist new report", exception);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected JasperReport generateJasperReport(final Report report, final ReportConfiguration reportConfiguration) throws
			InSaveReportCreateOperationException {
		try {
			return this.dynamicBuilder.generateReport(report, this.getEntity(reportConfiguration));
		} catch (ReportGenerationException exception) {
			final String message = String.format("Failed to generate JasperReport of %s", report.getTitle());
			LOGGER.fatal(message, exception);
			throw new InSaveReportCreateOperationException(message, exception);
		}
	}

	private RBuilderEntity getEntity(final ReportConfiguration reportConfiguration) {
		return reportConfiguration.getEntities().get(0);
	}
}

