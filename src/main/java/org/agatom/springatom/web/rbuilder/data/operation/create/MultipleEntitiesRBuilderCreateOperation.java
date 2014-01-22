package org.agatom.springatom.web.rbuilder.data.operation.create;

import net.sf.jasperreports.engine.JasperReport;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.data.exception.InSaveReportCreateOperationException;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Properties;

/**
 * {@link SingleEntityRBuilderCreateOperation} is an implementation
 * of {ReportCreateOperation} focused on handling the situation where {@link org.agatom.springatom.web.rbuilder.ReportConfiguration}
 * defines more than one {@link javax.persistence.Entity}.
 * Only direct associations ({@code ManyToOne} and {@code OneToMany}) are supported due to limitations in treversing or and building paths
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
@Description("Handles creating the report instance where more than one entity is selected")
public class MultipleEntitiesRBuilderCreateOperation
        extends AbstractRBuilderCreateOperation {

    @Override
    protected boolean preValidate(final ReportConfiguration reportConfiguration) {
        return reportConfiguration.getSize() > 1;
    }

    @Override
    protected SReport createReport(final ReportConfiguration reportConfiguration, final SUser user, final Locale locale, final Properties propertiesHolder) throws
            InSaveReportCreateOperationException {
        return null;
    }

    @Override
    protected JasperReport generateJasperReport(final Report report, final ReportConfiguration reportConfiguration) throws
            InSaveReportCreateOperationException {
        return null;
    }

}
