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

package org.agatom.springatom.web.rbuilder.service.impl;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.agatom.springatom.core.invoke.InvokeUtils;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.types.report.ReportColumn;
import org.agatom.springatom.server.model.types.report.ReportEntity;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.agatom.springatom.server.repository.repositories.report.SReportRepository;
import org.agatom.springatom.server.service.support.exceptions.ServiceException;
import org.agatom.springatom.web.rbuilder.ReportRepresentation;
import org.agatom.springatom.web.rbuilder.service.ReportBuilderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.Repositories;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(value = ReportBuilderServiceImpl.SERVICE_NAME)
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
public class ReportBuilderServiceImpl
        implements ReportBuilderService {
    private static final Logger   LOGGER                   = Logger.getLogger(ReportBuilderServiceImpl.class);
    private static final Class<?> CONVERSION_SERVICE_CLASS = FormattingConversionService.class;
    public static final  String   SERVICE_NAME             = "reportBuilderService";

    @Autowired
    @Qualifier(value = SReportRepository.REPO_NAME)
    private SReportRepository repository;

    @Autowired
    private ApplicationContext          applicationContext;
    private Repositories                repositories;
    private FormattingConversionService conversionService;

    @PostConstruct
    protected void init() {
        this.repositories = new Repositories(this.applicationContext);
        this.conversionService = (FormattingConversionService) this.applicationContext.getBean(CONVERSION_SERVICE_CLASS);
    }

    @Override
    public List<SReport> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<SReport> findAll(@Nonnull final Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<SReport> findAll(@Nonnull final Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public List<ReportRepresentation> getAvailableRepresentations() {
        return null;
    }

    @Override
    public void generateReport(@Nonnull @Min(value = 1) final Long reportId, final HttpServletResponse response) throws ServiceException {
        final SReport report = this.repository.findOne(reportId);
        LOGGER.info(String.format("Generate report for %s", report));
        try {
            final DynamicReport dynamicReport = this.buildReport(report);

            final SBasicRepository repositoryFor = (SBasicRepository) this.repositories.getRepositoryFor(report.getEntities().get(0).getClazz());

            final Map<String, Object> params = Maps.newHashMap();
            params.put("dynamicReportDs", findAllAndConvert(repositoryFor, report.getEntities().get(0).getColumns()));
            final JasperReport jasperReport = DynamicJasperHelper.generateJasperReport(dynamicReport, new ClassicLayoutManager(), params);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, (JRDataSource) null);

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            this.exportToXLS(jasperPrint, baos);

            final String fileName = "SalesReport.xls";
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(baos.size());
            this.write(response, baos);
        } catch (Exception generateException) {
            throw new ServiceException(SReport.class, String.format("Failed to generate report for id=%d", reportId), generateException);
        }
    }

    private List<?> findAllAndConvert(final SBasicRepository<?, ?> repositoryFor, final List<ReportColumn> columns) {
        final List<?> all = repositoryFor.findAll();
        return FluentIterable
                .from(all)
                .transform(new Function<Object, Object>() {
                    @Nullable
                    @Override
                    public Object apply(@Nullable final Object input) {
                        assert input != null;
                        final Map<String, String> map = Maps.newHashMap();
                        for (final ReportColumn column : columns) {
                            final Object object = InvokeUtils.invokeGetter(input, column.getPropertyName());
                            map.put(column.getPropertyName(), conversionService.convert(object, String.class));
                        }
                        return map;
                    }
                })
                .toList();
    }

    private DynamicReport buildReport(final SReport report) throws Exception {
        final FastReportBuilder parentReportBuilder = new FastReportBuilder();
        parentReportBuilder.setIgnorePagination(true)
                           .setMargins(0, 0, 0, 0)
                           .setWhenNoDataAllSectionNoDetail()
                           .setUseFullPageWidth(true);
        parentReportBuilder.setReportName(report.getName());
        parentReportBuilder.setReportLocale(LocaleContextHolder.getLocale());
        parentReportBuilder.setSubtitle(report.getDescription());

        try {
            parentReportBuilder.addConcatenatedReport(this.buildLayout(report.getEntities()),
                    new ClassicLayoutManager(), "dynamicReportDs",
                    DJConstants.DATA_SOURCE_ORIGIN_PARAMETER,
                    DJConstants.DATA_SOURCE_TYPE_COLLECTION);
        } catch (Exception e) {
            LOGGER.error("Unable to concat child report");
            throw e;
        }
        return parentReportBuilder.build();
    }

    private void exportToXLS(JasperPrint jp, ByteArrayOutputStream baos) throws JRException {
        // Create a JRXlsExporter instance
        JRXlsExporter exporter = new JRXlsExporter();

        // Here we assign the parameters jp and baos to the exporter
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

        // Excel specific parameters
        // Check the Jasper (not DynamicJasper) docs for a description of these settings. Most are
        // self-documenting
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        // Retrieve the exported report in XLS format
        exporter.exportReport();
    }

    private DynamicReport buildLayout(final List<ReportEntity> entities) throws Exception {
        final FastReportBuilder builder = new FastReportBuilder();

        for (final ReportEntity entity : entities) {
            builder.addGroups(entities.indexOf(entity), GroupLayout.VALUE_IN_HEADER_AND_FOR_EACH);
            builder.setTitle(entity.getName());
            for (final ReportColumn column : entity.getColumns()) {
                builder.addColumn(column.getColumnName(), column.getPropertyName(), String.class, 50);
            }
        }

        builder.setPrintColumnNames(true)
               .setIgnorePagination(true)
               .setMargins(0, 0, 0, 0)
               .setUseFullPageWidth(true);

        return builder.build();
    }

    private void write(HttpServletResponse response, ByteArrayOutputStream baos) {
        LOGGER.debug("Writing report to the stream");
        try {
            // Retrieve the output stream
            ServletOutputStream outputStream = response.getOutputStream();
            // Write to the output stream
            baos.writeTo(outputStream);
            // Flush the stream
            outputStream.flush();

        } catch (Exception e) {
            LOGGER.error("Unable to write report to the output stream");
        }
    }
}
