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

package org.agatom.springatom.server.service.domain.impl;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.util.JRSaver;
import org.agatom.springatom.SpringAtom;
import org.agatom.springatom.core.invoke.InvokeUtils;
import org.agatom.springatom.server.model.beans.PersistentVersionedObject;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.model.types.report.ReportResource;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.agatom.springatom.server.repository.repositories.report.SReportRepository;
import org.agatom.springatom.server.service.domain.ReportBuilderService;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.ReportRepresentation;
import org.agatom.springatom.web.rbuilder.ReportViewDescriptor;
import org.agatom.springatom.web.rbuilder.bean.ReportableColumn;
import org.agatom.springatom.web.rbuilder.bean.ReportableEntity;
import org.agatom.springatom.web.rbuilder.exception.ReportBuilderServiceException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.support.Repositories;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(value = ReportBuilderServiceImpl.SERVICE_NAME)
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
public class ReportBuilderServiceImpl
        extends SServiceImpl<SReport, Long, Integer, SReportRepository>
        implements ReportBuilderService {
    private static final Logger LOGGER       = Logger.getLogger(ReportBuilderServiceImpl.class);
    public static final  String SERVICE_NAME = "reportBuilderService";

    private SReportRepository           repository;
    @Autowired
    private ApplicationContext          applicationContext;
    private Repositories                repositories;
    @Autowired(required = false)
    private FormattingConversionService conversionService;
    @Autowired(required = false)
    private ObjectMapper                jackson;

    @Override
    @Autowired
    public void autoWireRepository(@Qualifier(value = SReportRepository.REPO_NAME) final SReportRepository repo) {
        super.autoWireRepository(repo);
        this.repository = repo;
    }

    @PostConstruct
    protected void init() {
        this.repositories = new Repositories(this.applicationContext);
    }

    @Override
    public Map<String, ReportRepresentation> getAvailableRepresentations() {
        return ReportRepresentation.getRepresentation();
    }

    @Override
    @CacheEvict(value = "reports", key = "#pk")
    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW, rollbackFor = ReportBuilderServiceException.class)
    public Report deleteReport(final Long pk) throws ReportBuilderServiceException {
        try {
            final SReport one = this.findOne(pk);

            Assert.notNull(one);
            Assert.isTrue(!one.isNew());

            final ReportResource resource = one.getResource();
            final File jasperFile = ResourceUtils.getFile(resource.getJasperPath());
            final File cfgFile = ResourceUtils.getFile(resource.getConfigurationPath());

            Assert.isTrue(jasperFile.exists());
            Assert.isTrue(jasperFile.delete());

            Assert.isTrue(cfgFile.exists());
            Assert.isTrue(cfgFile.delete());

            this.deleteOne(pk);

            return one;
        } catch (Exception e) {
            LOGGER.error(String.format("Failed to delete %s", ClassUtils.getShortName(SReport.class)), e);
            throw new ReportBuilderServiceException(e);
        }
    }

    @Override
    @Cacheable(value = "reports")
    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = ReportBuilderServiceException.class)
    public Report save(final ReportConfiguration reportConfiguration, SReport report) throws ReportBuilderServiceException {
        LOGGER.debug(String.format("Saving new report from %s=%s", ClassUtils.getShortName(ReportConfiguration.class), reportConfiguration));
        try {
            Assert.notNull(report, String.format("%s has not set valid instance of %s",
                    ClassUtils.getShortName(ReportConfiguration.class),
                    ClassUtils.getShortName(SReport.class))
            );
            final JasperReport jasperReport = this.generateReport(report, reportConfiguration.getEntities());
            report = this.save(this.saveCompiledReportToFile(
                    report,
                    jasperReport,
                    this.getReportResource(report.getTitle()),
                    reportConfiguration,
                    this.getReportConfigurationResource(report.getTitle())
            ));
            Assert.isTrue(!report.isNew());
            return report;
        } catch (Exception e) {
            LOGGER.error(String.format("Failed to save %s to file", report), e);
            throw new ReportBuilderServiceException("Failed to persist new report", e);
        }
    }

    private SReport saveCompiledReportToFile(final SReport report, final JasperReport jasperReport,
                                             final Resource jasperResource,
                                             final ReportConfiguration reportConfiguration,
                                             final Resource reportResource) throws
            Exception {
        Assert.notNull(jasperResource);
        Assert.isTrue(!jasperResource.getFile().exists());
        JRSaver.saveObject(jasperReport, jasperResource.getFile());
        Assert.isTrue(jasperResource.getFile().setReadOnly());

        Assert.notNull(reportResource);
        Assert.isTrue(!reportResource.getFile().exists());
        this.jackson.writeValue(reportResource.getFile(), reportConfiguration);
        Assert.isTrue(reportResource.getFile().setReadOnly());

        return report.setResource(jasperResource.getURL().getPath(), reportResource.getURL().getPath());
    }

    @Override
    @Cacheable(value = "reports", key = "#reportId", condition = "#reportId > 0")
    public SReport getReport(final Long reportId) throws ReportBuilderServiceException {
        try {
            Assert.notNull(reportId, "Report#ID can not be null");
            return this.findOne(reportId);
        } catch (Exception e) {
            throw new ReportBuilderServiceException(String.format("Failed to retrieve report for ID=%d", reportId), e);
        }
    }

    @Override
    @Cacheable(value = "reports")
    public SReport getReport(final String title) throws ReportBuilderServiceException {
        try {
            Assert.notNull(title, "Report#title can not be null");
            return this.repository.findByTitle(title);
        } catch (Exception e) {
            throw new ReportBuilderServiceException(String.format("Failed to retrieve report for title=%s", title), e);
        }
    }

    @Override
    public ReportViewDescriptor getReportWrapper(final Long reportId, final String format) throws ReportBuilderServiceException {
        LOGGER.debug(String
                .format("Retrieving %s for pair=[reportId=%d,format=%s]", ClassUtils.getShortName(ReportViewDescriptor.class), reportId, format));
        try {
            final SReport report = this.getReport(reportId);
            final File file = ResourceUtils.getFile(report.getResource().getConfigurationPath());

            final ReportConfiguration configuration = this.jackson.readValue(
                    file,
                    ReportConfiguration.class
            );

            final ModelMap reportParameters = this.getReportParameters(format, configuration);

            return new ReportViewDescriptor(
                    report.getResource().getJasperFilename(),
                    format,
                    reportParameters
            );
        } catch (Exception e) {
            LOGGER.error(String.format("Failed to create %s", ClassUtils.getShortName(ReportViewDescriptor.class)), e);
            if (e instanceof ReportBuilderServiceException) {
                throw (ReportBuilderServiceException) e;
            } else {
                throw new ReportBuilderServiceException(e);
            }
        }
    }

    private ModelMap getReportParameters(final String format, final ReportConfiguration configuration) throws ReportBuilderServiceException {
        if (!this.getAvailableRepresentations().containsKey(format)) {
            throw new ReportBuilderServiceException(SReport.class, String.format("%s is not available format to be used", format));
        }
        try {
            final ModelMap modelMap = new ModelMap();

            modelMap.put("format", format);
            modelMap.put("dataSource", this.getConvertedDataSource(configuration));

            return modelMap;
        } catch (Exception exception) {
            LOGGER.error(String.format("Error in retrieving reportParameters for %s", configuration), exception);
            throw new ReportBuilderServiceException(String.format("Error in retrieving reportParameters for %s", configuration), exception);
        }
    }

    private JasperReport generateReport(@Nonnull final Report report, @Size(min = 1) final List<ReportableEntity> entities) {
        LOGGER.trace(String.format("Generating report[%s] from pair=[\n\treport->%s\n\tentities->%s",
                ClassUtils.getShortName(DynamicReport.class),
                report,
                entities)
        );
        final DynamicReport dynamicReport;
        try {
            dynamicReport = this.buildReport(report, entities);
            return DynamicJasperHelper.generateJasperReport(dynamicReport, new ClassicLayoutManager(), Maps.newHashMap());
        } catch (ClassNotFoundException | ColumnBuilderException | JRException e) {
            LOGGER.error(String.format("Failed to generate report from %s", report), e);
        }
        return null;
    }

    private DynamicReport buildReport(final Report report, final List<ReportableEntity> entities) throws ClassNotFoundException,
            ColumnBuilderException {
        LOGGER.trace(String.format("Building report instance of %s from pair=[\n\treport->%s\n\tentities->%s",
                ClassUtils.getShortName(FastReportBuilder.class),
                report,
                entities)
        );
        final FastReportBuilder builder = new FastReportBuilder();
        builder.setIgnorePagination(true)
               .setMargins(0, 0, 0, 0)
               .setWhenNoDataAllSectionNoDetail()
               .setUseFullPageWidth(true)
               .setReportName(String.format("%s - %s", report.getTitle(), ((PersistentVersionedObject) report).getCreatedBy().getUsername()))
               .setReportLocale(LocaleContextHolder.getLocale())
               .setTitle(report.getTitle())
               .setSubtitle(report.getSubtitle())
               .setPrintColumnNames(true)
               .setIgnorePagination(true)
               .setMargins(0, 0, 0, 0)
               .setUseFullPageWidth(true);
        for (final ReportableEntity entity : entities) {
            for (final ReportableColumn column : entity.getColumns()) {
                final String columnTitle = column.getLabel();
                final String propertyName = column.getColumnName();
                final Class<?> renderingAsClass = column.getRenderClass();
                final int width = 50;
                final boolean fixedWidth = false;
                builder.addColumn(columnTitle, propertyName, renderingAsClass, width, fixedWidth);
            }
        }
        return builder.build();
    }

    private File createJasperHolderDirectory(final String corePackageName) throws ReportBuilderServiceException {
        try {
            final FileSystemResource fileSystemResource = new FileSystemResource(ResourceUtils
                    .getFile(String.format("classpath:%s", corePackageName)));
            final Resource jasper = new FileSystemResource(new File(String.format("%s/jasper", fileSystemResource.getPath())));
            final boolean mkdir = jasper.getFile().mkdir();
            if (mkdir) {
                LOGGER.trace(String.format("Created JasperHolderDirectory at file=%s", jasper.getFile().getPath()));
                return jasper.getFile();
            } else {
                throw new NestedIOException("Failed to created JasperHolderDirectory");
            }
        } catch (Exception e) {
            throw new ReportBuilderServiceException("Failed to create JasperHolderDirectory", e);
        }
    }

    private List<?> getConvertedDataSource(final ReportConfiguration report) {
        final Map<ReportableEntity, SBasicRepository<?, ?>> repositoryMap = Maps.newHashMap();
        for (final ReportableEntity entity : report.getEntities()) {
            final SBasicRepository<?, ?> repo = (SBasicRepository<?, ?>) this.repositories.getRepositoryFor(entity.getJavaClass());
            Assert.notNull(repo);
            repositoryMap.put(entity, repo);
            LOGGER.trace(String.format("For entity=%s retrieved repository=%s", entity.getJavaClassName(), ClassUtils.getShortName(repo.getClass())));
        }
        final Map<ReportableEntity, List<?>> allAndConvert = this.findAllAndConvert(repositoryMap);
        // TODO how to handle multiple selected entities
        return FluentIterable.from(allAndConvert.values()).first().get();
    }

    private Map<ReportableEntity, List<?>> findAllAndConvert(final Map<ReportableEntity, SBasicRepository<?, ?>> repositoryMap) {
        Assert.notEmpty(repositoryMap.keySet());
        final Map<ReportableEntity, List<?>> map = Maps.newHashMap();
        for (final ReportableEntity entity : repositoryMap.keySet()) {
            final List<?> converted = this.findAllAndConvert(repositoryMap.get(entity), entity.getColumns());
            Assert.notNull(converted);
            Assert.notEmpty(converted);
            map.put(entity, converted);
            LOGGER.trace(String.format("For entity=%s retrieved values=%d", entity.getJavaClassName(), converted.size()));
        }
        return map;
    }

    private List<?> findAllAndConvert(final SBasicRepository<?, ?> repositoryFor, final Set<ReportableColumn> columns) {
        Assert.notNull(repositoryFor);
        Assert.notEmpty(columns);

        final List<?> all = repositoryFor.findAll();
        return FluentIterable
                .from(all)
                .transform(new Function<Object, Object>() {
                    @Nullable
                    @Override
                    public Object apply(@Nullable final Object input) {
                        assert input != null;
                        final Map<String, Object> map = Maps.newHashMap();
                        for (final ReportableColumn column : columns) {
                            final Object object = InvokeUtils.invokeGetter(input, column.getColumnName());
                            map.put(column.getColumnName(), conversionService.convert(object, column.getRenderClass()));
                        }
                        return map;
                    }
                })
                .toList();
    }

    private Map<JRExporterParameter, Object> getParams() throws JRException {
        final Map<JRExporterParameter, Object> params = Maps.newHashMap();
        params.put(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        params.put(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        params.put(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        return params;
    }

    private Resource getReportResource(final String title) throws ReportBuilderServiceException {
        final String corePackageName = String.format("%s/", ClassUtils.getPackageName(SpringAtom.class).replaceAll("\\.", "/"));
        File file;
        try {
            file = ResourceUtils.getFile(String.format("classpath:%sjasper", corePackageName));
        } catch (FileNotFoundException fnfe) {
            file = this.createJasperHolderDirectory(corePackageName);
        }
        final FileSystemResource fileSystemResource = new FileSystemResource(file);
        return new FileSystemResource(
                new File(String.format(LocaleContextHolder.getLocale(), "%s/%s.jasper",
                        fileSystemResource.getPath(),
                        this.getReportName(title)
                ))
        );
    }

    private Resource getReportConfigurationResource(final String title) throws ReportBuilderServiceException {
        final String corePackageName = String.format("%s/", ClassUtils.getPackageName(SpringAtom.class).replaceAll("\\.", "/"));
        File file;
        try {
            file = ResourceUtils.getFile(String.format("classpath:%sjasper", corePackageName));
        } catch (FileNotFoundException fnfe) {
            file = this.createJasperHolderDirectory(corePackageName);
        }
        final FileSystemResource fileSystemResource = new FileSystemResource(file);
        return new FileSystemResource(
                new File(String.format(LocaleContextHolder.getLocale(), "%s/%s.json",
                        fileSystemResource.getPath(),
                        this.getReportConfigurationName(title)
                ))
        );
    }

    private String getReportName(final String reportName) throws ReportBuilderServiceException {
        return String.format("report_%s", reportName);
    }

    private String getReportConfigurationName(final String reportName) {
        return String.format("report_cfg_%s", reportName);
    }
}
