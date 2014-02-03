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

package org.agatom.springatom.web.rbuilder.data.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.sf.jasperreports.engine.JRDataSource;
import org.agatom.springatom.core.invoke.InvokeUtils;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.agatom.springatom.server.service.domain.SReportService;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.ReportRepresentation;
import org.agatom.springatom.web.rbuilder.ReportViewDescriptor;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.exception.ReportBuilderServiceException;
import org.agatom.springatom.web.rbuilder.data.operation.create.RBuilderCreateOperation;
import org.agatom.springatom.web.rbuilder.data.service.JasperBuilderService;
import org.agatom.springatom.web.rbuilder.data.service.ReportBuilderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.repository.support.Repositories;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

/**
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@Service(value = ReportBuilderServiceImpl.SERVICE_NAME)
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
public class ReportBuilderServiceImpl
        implements ReportBuilderService {
    public static final  String                        SERVICE_NAME                    = "reportBuilderService";
    private static final Logger                        LOGGER                          = Logger.getLogger(ReportBuilderServiceImpl.class);
    private static final String                        CACHE_NAME                      = "reports";
    private static final OrderColumnTransformFunction  ORDER_COLUMN_TRANSFORM_FUNCTION = new OrderColumnTransformFunction();
    private              Repositories                  repositories                    = null;
    @Autowired
    private              ApplicationContext            applicationContext              = null;
    @Autowired
    private              FormattingConversionService   conversionService               = null;
    @Autowired
    private              ObjectMapper                  jackson                         = null;
    @Autowired
    @Qualifier(value = "rbuilderProperties")
    private              Properties                    propertiesHolder                = null;
    @Autowired
    private              List<RBuilderCreateOperation> createOperations                = null;
    @Autowired
    private              SReportService                domainService                   = null;
    @Autowired
    private              JasperBuilderService          builderService                  = null;

    @PostConstruct
    protected void init() {
        this.repositories = new Repositories(this.applicationContext);
    }

    @Override
    public Map<String, ReportRepresentation> getAvailableRepresentations() {
        return ReportRepresentation.getRepresentation();
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#reportConfiguration.title")
    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = ReportBuilderServiceException.class)
    public Report newReportInstance(final ReportConfiguration reportConfiguration) throws ReportBuilderServiceException {
        LOGGER.debug(String.format("Saving new report from %s=%s", ClassUtils.getShortName(ReportConfiguration.class), reportConfiguration));
        try {
            final RBuilderCreateOperation createOperation = this.getReportCreateOperation(reportConfiguration);
            final Report report = createOperation.createReport(reportConfiguration);

            Assert.notNull(report);
            Assert.isAssignable(Persistable.class, report.getClass());
            Assert.isTrue(((Persistable) report).isNew());
            return this.domainService.save((SReport) report);
        } catch (Exception exception) {
            LOGGER.error(String.format("Failed to save %s to file", reportConfiguration), exception);
            throw new ReportBuilderServiceException("Failed to persist new report", exception);
        }
    }

    @Override
    public void populateReportViewDescriptor(
            final Long reportId,
            final String format,
            final ReportViewDescriptor descriptor
    ) throws ReportBuilderServiceException {
        LOGGER.debug(String
                .format("Retrieving %s for pair=[reportId=%d,format=%s]", ClassUtils.getShortName(ReportViewDescriptor.class), reportId, format));
        try {
            final SReport report = this.domainService.findOne(reportId);
            final File file = ResourceUtils.getFile(report.getResource().getConfigurationPath());

            final ReportConfiguration configuration = this.orderColumns(this.jackson.readValue(
                    file,
                    ReportConfiguration.class
            ));


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

    private ReportConfiguration orderColumns(final ReportConfiguration reportConfiguration) {
        final List<RBuilderEntity> entities = reportConfiguration.getEntities();
        for (RBuilderEntity entity : entities) {
            final List<RBuilderColumn> columns = Lists.newArrayList(entity.getColumns());
            reportConfiguration.popColumns(entity, columns);
            Collections.sort(columns, new Comparator<RBuilderColumn>() {
                @Override
                public int compare(final RBuilderColumn o1, final RBuilderColumn o2) {
                    return o1.getOrder().compareTo(o2.getOrder());
                }
            });
            reportConfiguration.putColumns(entity, columns);
        }
        return reportConfiguration;
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
            modelMap.put(this.propertiesHolder.getProperty("reports.reportKey"), this.domainService.detach(report));

            return modelMap;
        } catch (Exception exception) {
            LOGGER.error(String.format("Error in retrieving reportParameters for %s", configuration), exception);
            throw new ReportBuilderServiceException(String.format("Error in retrieving reportParameters for %s", configuration), exception);
        }
    }

    private Map<String, Object> getConvertedDataSources(final ReportConfiguration configuration, final SReport report) {

        final String property = this.propertiesHolder.getProperty("reports.reportDataKey");
        final Map<String, Object> map = Maps.newLinkedHashMap();
        final Map<RBuilderEntity, SBasicRepository<?, ?>> repositories = this.getRepositories(configuration);
        final Map<RBuilderEntity, SReport> dynamicEntitiesMap = this.getReportsDistribution(configuration, report);

        for (final RBuilderEntity entity : repositories.keySet()) {
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

    private Map<RBuilderEntity, SBasicRepository<?, ?>> getRepositories(final ReportConfiguration report) {
        final Map<RBuilderEntity, SBasicRepository<?, ?>> repositoryMap = Maps.newHashMap();
        for (final RBuilderEntity entity : report.getEntities()) {
            final SBasicRepository<?, ?> repo = (SBasicRepository<?, ?>) this.repositories.getRepositoryFor(entity.getJavaClass());
            Assert.notNull(repo);
            repositoryMap.put(entity, repo);
            LOGGER.trace(String.format("For entity=%s retrieved repository=%s", entity.getJavaClassName(), ClassUtils.getShortName(repo.getClass())));
        }
        return repositoryMap;
    }

    private JRDataSource generateDataSource(final SBasicRepository<?, ?> repositoryFor, final Set<RBuilderColumn> columns) {
        Assert.notNull(repositoryFor);
        Assert.notEmpty(columns);

        final Set<RBuilderColumn> groupByColumns = builderService.getGroupByColumns(columns);
        List<?> all;

        if (!CollectionUtils.isEmpty(groupByColumns)) {
            final List<Order> orders = FluentIterable
                    .from(groupByColumns)
                    .transform(ORDER_COLUMN_TRANSFORM_FUNCTION).toList();
            all = repositoryFor.findAll(new Sort(orders));
        } else {
            all = repositoryFor.findAll();
        }

        final Function<Object, Object> transformationFunction = new Function<Object, Object>() {

            @Nullable
            @Override
            public Object apply(@Nullable final Object input) {
                assert input != null;
                final Map<String, Object> map = Maps.newHashMap();
                for (final RBuilderColumn column : columns) {
                    final String columnName = column.getColumnName();
                    final Object object = InvokeUtils.invokeGetter(input, columnName);
                    final Class<?> renderClass = column.getRenderClass();
                    this.convertColumn(map, column, columnName, object, renderClass);
                }
                return map;
            }

            private void convertColumn(final Map<String, Object> map, final RBuilderColumn column, final String columnName, final Object object, final Class<?> renderClass) {
                if (column.isMultiValued() && ClassUtils.isAssignable(List.class, renderClass)) {
                    this.convertAsMultiValued(map, columnName, (List<?>) object);
                } else {
                    this.convertAsSingleValued(map, columnName, object, renderClass);
                }
            }

            private void convertAsMultiValued(final Map<String, Object> map, final String columnName, final List<?> list) {
                final List<Object> objects = Lists.newArrayListWithCapacity(list.size());
                for (final Object obj : list) {
                    objects.add(conversionService.convert(obj, String.class));
                }
                map.put(columnName, objects);
            }

            private Object convertAsSingleValued(final Map<String, Object> map, final String columnName, final Object object, final Class<?> renderClass) {
                return map.put(columnName, conversionService.convert(object, renderClass));
            }

        };
        return JasperReportsUtils.convertReportData(FluentIterable.from(all).transform(transformationFunction).toSet());
    }

    private Map<RBuilderEntity, SReport> getReportsDistribution(final ReportConfiguration configuration, final SReport report) {
        Assert.isTrue(!report.isDynamic());

        final Map<RBuilderEntity, SReport> map = Maps.newHashMap();
        final List<RBuilderEntity> entities = configuration.getEntities();
        final ReportableEntityPredicate entityPredicate = new ReportableEntityPredicate().setReport(report);
        Optional<RBuilderEntity> entityOptional = FluentIterable.from(entities).filter(entityPredicate).first();

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
                final SReport tmp = this.domainService.detach((SReport) subReport);
                Assert.notNull(tmp);
                Assert.isTrue(!tmp.isNew());
                subReportsDetached.add(tmp);
            }
            return subReportsDetached;
        }
        return Sets.newHashSet();
    }

    /**
     * Picks up possible {@link org.agatom.springatom.web.rbuilder.data.operation.create.RBuilderCreateOperation}
     * which can create {@link org.agatom.springatom.server.model.types.report.Report} instance out of {@link ReportConfiguration}
     *
     * @param reportConfiguration
     *         reportConfiguration to be used
     *
     * @return instance of valid {@link org.agatom.springatom.web.rbuilder.data.operation.create.RBuilderCreateOperation}
     *
     * @throws ReportBuilderServiceException
     *         if any
     */
    private RBuilderCreateOperation getReportCreateOperation(final ReportConfiguration reportConfiguration) throws ReportBuilderServiceException {
        final int size = reportConfiguration.getSize();
        if (size <= 0) {
            throw new ReportBuilderServiceException(String.format("Impossible to retrieve %s for %s because it defines no entities",
                    ClassUtils.getShortName(RBuilderCreateOperation.class),
                    ClassUtils.getShortName(ReportConfiguration.class)
            ));
        }
        Assert.notEmpty(this.createOperations);
        for (final RBuilderCreateOperation operation : this.createOperations) {
            if (operation.canCreate(reportConfiguration)) {
                return operation;
            }
        }
        throw new ReportBuilderServiceException(String.format("No matching %s was found to handle %s",
                ClassUtils.getShortName(RBuilderCreateOperation.class),
                reportConfiguration
        ));
    }

    private static class OrderColumnTransformFunction
            implements Function<RBuilderColumn, Order> {
        @Nullable
        @Override
        public Order apply(@Nullable final RBuilderColumn input) {
            assert input != null;
            return new Order(Direction.ASC, input.getColumnName());
        }
    }

    private static class ReportableEntityPredicate
            implements Predicate<RBuilderEntity> {

        private Report report;

        public ReportableEntityPredicate setReport(final Report report) {
            this.report = report;
            return this;
        }

        @Override
        public boolean apply(@Nullable final RBuilderEntity input) {
            return input != null && input.getJavaClass().equals(this.report.getReportedClass());
        }
    }
}
