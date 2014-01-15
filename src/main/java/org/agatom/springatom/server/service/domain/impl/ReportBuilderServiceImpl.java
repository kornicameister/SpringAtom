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
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.*;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;
import org.agatom.springatom.SpringAtom;
import org.agatom.springatom.core.invoke.InvokeUtils;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.model.types.report.ReportResource;
import org.agatom.springatom.server.properties.SPropertiesHolder;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.agatom.springatom.server.repository.repositories.report.SReportRepository;
import org.agatom.springatom.server.service.domain.ReportBuilderService;
import org.agatom.springatom.server.service.domain.SUserService;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.ReportRepresentation;
import org.agatom.springatom.web.rbuilder.ReportViewDescriptor;
import org.agatom.springatom.web.rbuilder.bean.ReportableColumn;
import org.agatom.springatom.web.rbuilder.bean.ReportableEntity;
import org.agatom.springatom.web.rbuilder.exception.ReportBuilderServiceException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
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
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.util.*;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    public static final  String SERVICE_NAME = "reportBuilderService";
    private static final Logger LOGGER = Logger.getLogger(ReportBuilderServiceImpl.class);
    private Repositories          repositories;
    private SReportRepository           repository;
    private Cache<Object, Object> cache;
    @Autowired
    private ApplicationContext          applicationContext;
    @Autowired(required = false)
    private FormattingConversionService conversionService;
    @Autowired(required = false)
    private ObjectMapper                jackson;
    @Autowired(required = false)
    private EntityDescriptors     entityDescriptors;
    @Autowired(required = false)
    private SUserService          userService;
    @Autowired(required = false)
    private SMessageSource        messageSource;
    @Autowired(required = false)
    private SPropertiesHolder     propertiesHolder;

    @Override
    @Autowired
    public void autoWireRepository(@Qualifier(value = SReportRepository.REPO_NAME) final SReportRepository repo) {
        super.autoWireRepository(repo);
        this.repository = repo;
    }

    @PostConstruct
    protected void init() {
        this.repositories = new Repositories(this.applicationContext);
        this.cache = CacheBuilder.<Thread, SUser>newBuilder()
                                 .maximumSize(100)
                                 .expireAfterAccess(5, TimeUnit.MINUTES)
                                 .expireAfterWrite(5, TimeUnit.MINUTES)
                                 .build();
        Assert.notNull(this.cache);
        this.cache.put(Thread.currentThread(), this.userService.getAuthenticatedUser());
    }

    @Override
    public SReport newReport(@NotNull final String title, @NotNull final String subtitle, @NotNull final String description) {
        return this.newReport(title, subtitle, description, Maps.<String, Serializable>newHashMap());
    }

    @Override
    public SReport newReport(final String title, final String subtitle, final String description, final Map<String, Serializable> settings) {

        final SReport report = new SReport();
        final SUser authenticatedUser = this.getAuthenticatedUser();

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
    @Cacheable(value = "reports", key = "#reportConfiguration.title")
    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = ReportBuilderServiceException.class)
    public Map<Long, Report> save(final ReportConfiguration reportConfiguration) throws ReportBuilderServiceException {
        LOGGER.debug(String.format("Saving new report from %s=%s", ClassUtils.getShortName(ReportConfiguration.class), reportConfiguration));
        try {

            long firstId = -1;
            final SUser user = this.getAuthenticatedUser();
            final Locale locale = LocaleContextHolder.getLocale();
            final Map<Long, Report> reports = Maps.newLinkedHashMap();

            SReport report = null;

            for (final ReportableEntity entity : reportConfiguration.getEntities()) {
                LOGGER.trace(String.format("Generating report instance for entity=%s", entity.getName()));

                if (firstId == -1) {
                    report = (SReport) this.newReport(reportConfiguration);
                } else {
                    Assert.notNull(report);
                    report = this.newReport(
                            this.messageSource.getMessage("wizard.NewReportWizard.dynamicReport.title",
                                    new Object[]{String.format("%s_%d", entity.getName(), ((SReport) reports.get(firstId)).getId())},
                                    locale
                            ),
                            this.messageSource.getMessage("wizard.NewReportWizard.dynamicReport.subtitle",
                                    new Object[]{this.entityDescriptors.getDescriptor(entity.getJavaClass()).getEntityType().getName()},
                                    locale
                            ),
                            this.messageSource.getMessage("wizard.NewReportWizard.dynamicReport.description",
                                    new Object[]{DateTime.now(), user.getUsername()},
                                    locale
                            ),
                            reportConfiguration.getSettings()
                    );
                    report.setReportMaster((SReport) reports.get(firstId));
                    report.setDynamic(true);
                }

                report.setReportedClass(entity.getJavaClass());

                final JasperReport jasperReport = this.generateReport(report, entity);
                report = this.save(this.saveCompiledReportToFile(
                        report,
                        jasperReport,
                        this.getReportResource(report),
                        reportConfiguration,
                        this.getReportConfigurationResource(report)
                ));

                Assert.isTrue(!report.isNew());
                if (firstId == -1) {
                    firstId = report.getId();
                }
                reports.put(report.getId(), report);
            }

            final SReport reportMaster = (SReport) reports.get(firstId);
            final long finalFirstId = firstId;
            final FluentIterable<Long> longs = FluentIterable.from(reports.keySet()).filter(new Predicate<Long>() {
                @Override
                public boolean apply(@Nullable final Long input) {
                    return input != null && input != finalFirstId;
                }
            });
            for (final Long reportId : longs) {
                reportMaster.putSubReport((SReport) reports.get(reportId));
            }
            if (!longs.isEmpty()) {
                this.save(reportMaster);
            }

            return reports;
        } catch (Exception exception) {
            LOGGER.error(String.format("Failed to save %s to file", reportConfiguration), exception);
            throw new ReportBuilderServiceException("Failed to persist new report", exception);
        }
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
    public void getReportWrapper(final Long reportId, final String format, final ReportViewDescriptor descriptor) throws
            ReportBuilderServiceException {
        LOGGER.debug(String
                .format("Retrieving %s for pair=[reportId=%d,format=%s]", ClassUtils.getShortName(ReportViewDescriptor.class), reportId, format));
        try {
            final SReport report = this.getReport(reportId);
            final File file = ResourceUtils.getFile(report.getResource().getConfigurationPath());

            final ReportConfiguration configuration = this.jackson.readValue(
                    file,
                    ReportConfiguration.class
            );

            final ModelMap reportParameters = this.getReportParameters(format, configuration, report);

            descriptor.setParameters(reportParameters)
                      .setViewName(report.getResource().getJasperFilename())
                      .setFormat(format);
        } catch (Exception e) {
            LOGGER.error(String.format("Failed to create %s", ClassUtils.getShortName(ReportViewDescriptor.class)), e);
            if (e instanceof ReportBuilderServiceException) {
                throw (ReportBuilderServiceException) e;
            } else {
                throw new ReportBuilderServiceException(e);
            }
        }
    }

    private ModelMap getReportParameters(final String format, final ReportConfiguration configuration, final SReport report) throws
            ReportBuilderServiceException {
        if (!this.getAvailableRepresentations().containsKey(format)) {
            throw new ReportBuilderServiceException(SReport.class, String.format("%s is not available format to be used", format));
        }
        try {
            final ModelMap modelMap = new ModelMap();

            modelMap.putAll(this.getConvertedDataSources(configuration, report));
            modelMap.put(this.propertiesHolder.getProperty("reports.reportFormatKey"), format);
            modelMap.put(this.propertiesHolder.getProperty("reports.reportSubReportsKey"), this.extractSubReports(report));
            modelMap.put(this.propertiesHolder.getProperty("reports.reportKey"), this.repository.detach(report));

            return modelMap;
        } catch (Exception exception) {
            LOGGER.error(String.format("Error in retrieving reportParameters for %s", configuration), exception);
            throw new ReportBuilderServiceException(String.format("Error in retrieving reportParameters for %s", configuration), exception);
        }
    }

    private Map<String, Object> getConvertedDataSources(final ReportConfiguration configuration, final SReport report) {

        final String property = this.propertiesHolder.getProperty("reports.reportDataKey");
        final Map<String, Object> map = Maps.newLinkedHashMap();
        final Map<ReportableEntity, SBasicRepository<?, ?>> repositories = this.getRepositories(configuration);
        final Map<ReportableEntity, SReport> dynamicEntitiesMap = this.getReportsDistribution(configuration, report);

        for (final ReportableEntity entity : repositories.keySet()) {
            final JRDataSource value = this.generateDataSource(repositories.get(entity), entity.getColumns());
            String dataKey;
            if (!dynamicEntitiesMap.get(entity).isDynamic()) {
                dataKey = property;
            } else {
                dataKey = String.format("%s_%d", property, dynamicEntitiesMap.get(entity).getId());
            }
            map.put(dataKey, value);
        }
        return map;
    }

    private Map<ReportableEntity, SBasicRepository<?, ?>> getRepositories(final ReportConfiguration report) {
        final Map<ReportableEntity, SBasicRepository<?, ?>> repositoryMap = Maps.newHashMap();
        for (final ReportableEntity entity : report.getEntities()) {
            final SBasicRepository<?, ?> repo = (SBasicRepository<?, ?>) this.repositories.getRepositoryFor(entity.getJavaClass());
            Assert.notNull(repo);
            repositoryMap.put(entity, repo);
            LOGGER.trace(String.format("For entity=%s retrieved repository=%s", entity.getJavaClassName(), ClassUtils.getShortName(repo.getClass())));
        }
        return repositoryMap;
    }

    private JRDataSource generateDataSource(final SBasicRepository<?, ?> repositoryFor, final Set<ReportableColumn> columns) {
        Assert.notNull(repositoryFor);
        Assert.notEmpty(columns);

        final List<?> all = repositoryFor.findAll();
        final Function<Object, Object> transformationFunction = new Function<Object, Object>() {
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
        };
        return JasperReportsUtils.convertReportData(FluentIterable.from(all).transform(transformationFunction).toSet());
    }

    private Map<ReportableEntity, SReport> getReportsDistribution(final ReportConfiguration configuration, final SReport report) {
        Assert.isTrue(!report.isDynamic());

        final Map<ReportableEntity, SReport> map = Maps.newHashMap();
        final List<ReportableEntity> entities = configuration.getEntities();
        final ReportableEntityPredicate entityPredicate = new ReportableEntityPredicate().setReport(report);
        Optional<ReportableEntity> entityOptional = FluentIterable.from(entities).filter(entityPredicate).first();

        Assert.isTrue(entityOptional.isPresent());
        map.put(entityOptional.get(), report);

        for (final Report dynamicReport : report.getSubReports()) {
            final SReport sDynamicReport = (SReport) dynamicReport;
            Assert.isTrue(sDynamicReport.isDynamic());

            entityOptional = FluentIterable.from(entities).filter(entityPredicate.setReport(dynamicReport)).first();
            Assert.isTrue(entityOptional.isPresent());
            map.put(entityOptional.get(), sDynamicReport);
        }
        return map;
    }

    private Set<Report> extractSubReports(final SReport report) {
        final Set<Report> subReports = report.getSubReports();
        if (!CollectionUtils.isEmpty(subReports)) {
            final Set<Report> subReportsDetached = Sets.newLinkedHashSetWithExpectedSize(subReports.size());
            for (final Report subReport : subReports) {
                final SReport tmp = this.repository.detach((SReport) subReport);
                Assert.notNull(tmp);
                Assert.isTrue(!tmp.isNew());
                subReportsDetached.add(tmp);
            }
            return subReportsDetached;
        }
        return Sets.newHashSet();
    }

    private Report newReport(final ReportConfiguration reportConfiguration) {
        return this.newReport(
                reportConfiguration.getTitle(),
                reportConfiguration.getSubtitle(),
                reportConfiguration.getDescription(),
                reportConfiguration.getSettings()
        );
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

    private JasperReport generateReport(final Report report, final ReportableEntity entity) throws
            ClassNotFoundException, JRException, ColumnBuilderException, DJBuilderException {
        LOGGER.trace(String.format("Generating report[%s] from pair=[\n\treport->%s\n\tentity->%s",
                ClassUtils.getShortName(DynamicReport.class),
                report,
                entity)
        );
        final DynamicReport dynamicReport;
        try {
            dynamicReport = this.buildReport(report, entity);
            return DynamicJasperHelper.generateJasperReport(dynamicReport, new ClassicLayoutManager(), Maps.newHashMap());
        } catch (DJBuilderException | ClassNotFoundException | ColumnBuilderException | JRException exception) {
            LOGGER.error(String.format("Failed to generate report from %s", report), exception);
            throw exception;
        }
    }

    private DynamicReport buildReport(final Report report, final ReportableEntity entity) throws ClassNotFoundException,
            ColumnBuilderException, DJBuilderException {
        LOGGER.trace(String.format("Building report instance of %s from pair=[\n\treport->%s\n\tentities->%s",
                ClassUtils.getShortName(FastReportBuilder.class),
                report,
                entity)
        );
        final Integer margin = 20;
        final FastReportBuilder builder = new FastReportBuilder();
        builder.setIgnorePagination(true)
               .setMargins(0, 0, 0, 0)
               .setWhenNoDataAllSectionNoDetail()
               .setUseFullPageWidth(true)
               .setReportName(String.format("%s - %s", report.getTitle(), this.getAuthenticatedUser().getUsername()))
               .setReportLocale(LocaleContextHolder.getLocale())
               .setTitle(report.getTitle())
               .setSubtitle(report.getSubtitle())
               .setPrintColumnNames(true)
               .setIgnorePagination(false)
               .setMargins(0, 0, 0, 0)
               .setWhenNoDataAllSectionNoDetail()
               .addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_CENTER)
               .setDetailHeight(new Integer(15)).setLeftMargin(margin)
               .setRightMargin(margin).setTopMargin(margin).setBottomMargin(margin)
               .setUseFullPageWidth(true);

        for (final ReportableColumn column : entity.getColumns()) {
            final AbstractColumn ac = ColumnBuilder.getNew()
                                                   .setColumnProperty(column.getColumnName(), column.getRenderClass())
                                                   .setTitle(column.getLabel())
                                                   .setWidth(50)
                                                   .build();
            builder.addColumn(ac);
            if (this.isGroupingColumn(entity, column)) {
                final GroupBuilder groupBuilder = new GroupBuilder();

                groupBuilder.setCriteriaColumn((PropertyColumn) ac)
                            .setGroupLayout(GroupLayout.DEFAULT_WITH_HEADER);

                builder.addGroup(groupBuilder.build());
            }
        }

        return builder.build();
    }

    private boolean isGroupingColumn(final ReportableEntity entity, final ReportableColumn column) {
        final EntityDescriptor<?> descriptor = this.entityDescriptors.getDescriptor(entity.getJavaClass());
        if (descriptor != null) {
            final EntityType<?> entityType = descriptor.getEntityType();
            final Attribute<?, ?> attribute = entityType.getAttribute(column.getColumnName());
            return attribute != null && ClassUtils.isAssignable(Enum.class, attribute.getJavaType());
        }
        return false;
    }

    private Resource getReportResource(final SReport report) throws ReportBuilderServiceException {
        final String corePackageName = String.format("%s/", ClassUtils.getPackageName(SpringAtom.class).replaceAll("\\.", "/"));
        File file;
        try {
            file = ResourceUtils.getFile(String.format("classpath:%sjasper", corePackageName));
        } catch (FileNotFoundException fnfe) {
            file = this.createJasperHolderDirectory(corePackageName);
        }
        final FileSystemResource fileSystemResource = new FileSystemResource(file);
        return this.getFileSystemResource(fileSystemResource, report, ResourceType.JASPER);
    }

    private Resource getReportConfigurationResource(final SReport report) throws ReportBuilderServiceException {
        final String corePackageName = String.format("%s/", ClassUtils.getPackageName(SpringAtom.class).replaceAll("\\.", "/"));
        File file;
        try {
            file = ResourceUtils.getFile(String.format("classpath:%sjasper", corePackageName));
        } catch (FileNotFoundException fnfe) {
            file = this.createJasperHolderDirectory(corePackageName);
        }
        final FileSystemResource fileSystemResource = new FileSystemResource(file);
        return this.getFileSystemResource(fileSystemResource, report, ResourceType.JSON);
    }

    private FileSystemResource getFileSystemResource(final FileSystemResource fileSystemResource, final SReport report, final ResourceType resourceType) {
        final String pathname = StringUtils.cleanPath(
                String.format(LocaleContextHolder.getLocale(), "%s/%s.%s",
                        fileSystemResource.getPath(),
                        resourceType.getResourceName(this.calculateFileNameHashCode(report)),
                        resourceType.toString().toLowerCase()
                )
        );
        return new FileSystemResource(new File(pathname));
    }

    private String calculateFileNameHashCode(final SReport report) {
        return StringUtils.trimAllWhitespace(report.getTitle());
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

    private static enum ResourceType
            implements ResourceTypeInterface {
        JASPER {
            public String getResourceName(final String fileName) {
                return String.format("report_%s", fileName);
            }
        },
        JSON {
            public String getResourceName(final String fileName) {
                return String.format("report_cfg_%s", fileName);
            }
        }
    }

    private SUser getAuthenticatedUser() {
        final Thread key = Thread.currentThread();
        Object ifPresent = this.cache.getIfPresent(key);

        if (ifPresent == null) {
            this.cache.invalidate(key);
            ifPresent = this.userService.getAuthenticatedUser();
            this.cache.put(key, ifPresent);
        }

        return (SUser) ifPresent;
    }

    private static interface ResourceTypeInterface {
        String getResourceName(final String fileName);
    }

    private static class ReportableEntityPredicate
            implements Predicate<ReportableEntity> {

        private Report report;

        public ReportableEntityPredicate setReport(final Report report) {
            this.report = report;
            return this;
        }

        @Override
        public boolean apply(@Nullable final ReportableEntity input) {
            return input != null && input.getJavaClass().equals(this.report.getReportedClass());
        }
    }
}
