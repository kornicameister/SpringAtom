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

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import net.sf.jasperreports.engine.JasperReport;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.service.domain.SReportService;
import org.agatom.springatom.server.service.domain.SUserService;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.data.exception.InSaveReportCreateOperationException;
import org.agatom.springatom.web.rbuilder.data.exception.ReportCreateOperationException;
import org.agatom.springatom.web.rbuilder.data.resource.ReportResourceHelper;
import org.agatom.springatom.web.rbuilder.data.resource.ReportResources;
import org.agatom.springatom.web.rbuilder.data.resource.ReportToFile;
import org.agatom.springatom.web.rbuilder.data.service.ReportBuilderService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
abstract class AbstractRBuilderCreateOperation
        implements RBuilderCreateOperation {

    private static final Logger LOGGER = Logger.getLogger(RBuilderCreateOperation.class);

    @Autowired(required = false)
    protected ReportBuilderService builderService   = null;
    @Autowired
    protected SReportService       domainService    = null;
    @Autowired(required = false)
    private   SUserService         userService      = null;
    @Autowired(required = false)
    @Qualifier(value = "rbuilderProperties")
    private   Properties           propertiesHolder = null;
    @Autowired(required = false)
    private   ReportResourceHelper resourceHelper   = null;

    @Override
    public Report createReport(final ReportConfiguration reportConfiguration) throws ReportCreateOperationException {
        try {
            this.preValidateInternal(reportConfiguration);

            final SUser user = this.getAuthenticatedUser();
            SReport report = (SReport) this.createReport(
                    reportConfiguration,
                    user,
                    LocaleContextHolder.getLocale(),
                    this.propertiesHolder
            );
            Assert.notNull(report);

            final JasperReport jasperReport = this.generateJasperReport(report, reportConfiguration);
            Assert.notNull(jasperReport);

            final ReportToFile reportToFile = ReportToFile.newReportToFile(report, jasperReport, reportConfiguration);

            final ReportResources reportResources = this.resourceHelper.toFile(reportToFile);
            Assert.notNull(reportResources);

            return report.setResource(
                    reportResources.getJasperResource().getURL().getPath(),
                    reportResources.getConfigurationResource().getURI().getPath()
            );

        } catch (Exception exception) {
            LOGGER.fatal(String.format("Report not saved was %s", reportConfiguration.getTitle()));
            LOGGER.fatal("Could not save report instance due to an error", Throwables.getRootCause(exception));
            throw this.getReportException(exception);
        }
    }

    @Override
    public boolean canCreate(final ReportConfiguration reportConfiguration) {
        return this.preValidate(reportConfiguration);
    }

    protected SReport newReport(final String title, final String subtitle, final String description) {
        return this.newReport(title, subtitle, description, Maps.<String, Serializable>newHashMap());
    }

    protected SReport newReport(final String title, final String subtitle, final String description, final Map<String, Serializable> settings) {

        final SReport report = new SReport();
        final SUser authenticatedUser = this.userService.getAuthenticatedUser();

        report.setCreatedBy(authenticatedUser);
        report.setCreatedDate(DateTime.now());
        report.setLastModifiedBy(authenticatedUser);
        report.setLastModifiedDate(DateTime.now());

        report.setTitle(title);
        report.setDescription(description);
        report.setSubtitle(subtitle);
        for (final String settingKey : settings.keySet()) {
            final Serializable value = settings.get(settingKey);
            if (value == null) {
                continue;
            }
            report.putSetting(settingKey, value);
        }

        return report;
    }

    /**
     * Runs several assertions to ensure valid state of {@link org.agatom.springatom.web.rbuilder.ReportConfiguration}
     * as source for new {@link org.agatom.springatom.server.model.types.report.Report}
     *
     * @param reportConfiguration
     *         configuration to be used
     *
     * @throws ReportCreateOperationException
     *         if any assertion fails
     * @see AbstractRBuilderCreateOperation#preValidate(org.agatom.springatom.web.rbuilder.ReportConfiguration)
     */
    private void preValidateInternal(final ReportConfiguration reportConfiguration) throws ReportCreateOperationException {
        Assert.isTrue(!reportConfiguration.getTitle().isEmpty());
        Assert.isTrue(this.preValidate(reportConfiguration));
        Assert.isTrue(!this.isPersisted(reportConfiguration));
    }

    private ReportCreateOperationException getReportException(final Exception exception) throws ReportCreateOperationException {
        if (ClassUtils.isAssignable(ReportCreateOperationException.class, exception.getClass())) {
            return (ReportCreateOperationException) exception;
        }
        return new ReportCreateOperationException(exception);
    }

    private boolean isPersisted(final ReportConfiguration reportConfiguration) {
        final String title = reportConfiguration.getTitle();
        Assert.hasText(title);
        try {
            this.domainService.findByTitle(title);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * Returns currently authenticated
     *
     * @return current user
     *
     * @see org.agatom.springatom.server.service.domain.SUserService#getAuthenticatedUser()
     */
    private SUser getAuthenticatedUser() {
        return this.userService.getAuthenticatedUser();
    }

    /**
     * Instantiates {@link org.agatom.springatom.server.model.types.report.Report} instance from {@link
     * org.agatom.springatom.web.rbuilder.ReportConfiguration}
     *
     * @param reportConfiguration
     *         configuration to be used
     *
     * @return report from {@link org.agatom.springatom.web.rbuilder.ReportConfiguration}
     */
    protected SReport newReport(final ReportConfiguration reportConfiguration) {
        return this.newReport(
                reportConfiguration.getTitle(),
                reportConfiguration.getSubtitle(),
                reportConfiguration.getDescription(),
                reportConfiguration.getSettings()
        );
    }

    /**
     * Validates report configuration according to the requirements represented by specific {@link RBuilderCreateOperation}
     *
     * @param reportConfiguration
     *         {@link org.agatom.springatom.web.rbuilder.ReportConfiguration} to be persisted
     *
     * @return true if valid
     */
    protected abstract boolean preValidate(final ReportConfiguration reportConfiguration);

    /**
     * Dispatches and perform actual saving of new {@link org.agatom.springatom.server.model.types.report.Report} instance
     *
     * @param reportConfiguration
     *         configuration to be used
     * @param user
     *         current user in {@link org.springframework.security.core.context.SecurityContext#getAuthentication()}
     * @param locale
     *         current locale retrieved from {@link org.springframework.context.i18n.LocaleContextHolder}
     * @param propertiesHolder
     *         {@code rbuilderProperties} as {@link java.util.Properties}
     *
     * @return new report instance
     *
     * @throws org.agatom.springatom.web.rbuilder.data.exception.InSaveReportCreateOperationException
     */
    protected abstract Report createReport(
            final ReportConfiguration reportConfiguration,
            final SUser user,
            final Locale locale,
            final Properties propertiesHolder
    ) throws InSaveReportCreateOperationException;

    protected abstract JasperReport generateJasperReport(final Report report, final ReportConfiguration reportConfiguration) throws
            InSaveReportCreateOperationException;
}
