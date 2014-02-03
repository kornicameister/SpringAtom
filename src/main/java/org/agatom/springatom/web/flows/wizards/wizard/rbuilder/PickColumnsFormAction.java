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

package org.agatom.springatom.web.flows.wizards.wizard.rbuilder;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.flows.wizards.actions.WizardAction;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.bean.RBuilderBean;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.conversion.ConversionHelper;
import org.agatom.springatom.web.rbuilder.conversion.type.RBuilderConvertiblePair;
import org.agatom.springatom.web.rbuilder.data.model.ReportableBeanResolver;
import org.agatom.springatom.web.rbuilder.data.model.ReportableColumnResolver;
import org.agatom.springatom.web.rbuilder.support.RColumnRenderClass;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.*;

/**
 * {@code PickColumnsFormAction} is an {@link org.agatom.springatom.web.flows.wizards.wizard.WizardFormAction}
 * designed to support step where columns are being selected by creator of the report
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */

@WizardAction(value = "pickColumnsFormAction")
public class PickColumnsFormAction
        extends ReportWizardFormAction<ReportConfiguration> {
    private static final Logger LOGGER           = Logger.getLogger(PickColumnsFormAction.class);
    private static final String ENTITY_TO_COLUMN = "entityToColumn";
    private static final String RENDER_PROP      = "colToRenderProp";

    @Autowired(required = false)
    private SMessageSource                   messageSource    = null;
    @Value("#{webProperties['sa.delimiter']}")
    private String                           valueDelimiter   = null;
    @Autowired
    private ConversionHelper<RBuilderColumn> conversionHelper = null;
    @Autowired
    private ReportableColumnResolver         columnResolver   = null;
    @Autowired
    private ReportableBeanResolver           beanResolver     = null;

    public PickColumnsFormAction() {
        super();
        this.setValidator(new AreColumnsSelectedForAllEntitiesValidator());
    }

    @Override
    public Event setupForm(final RequestContext context) throws Exception {
        final Map<RBuilderEntity, Set<RBuilderColumn>> distribution = this.getColumnDistribution(context);
        context.getViewScope().put(ENTITY_TO_COLUMN, distribution);
        context.getViewScope().put(RENDER_PROP, this.getColumnsRenderProperties(distribution.values()));
        return super.setupForm(context);
    }

    @Override
    protected WebDataBinder doInitBinder(final WebDataBinder binder, final FormattingConversionService conversionService) {
        binder.setIgnoreUnknownFields(true);
        conversionService.addConverter(new StringArrayToReportColumnListConverter());
        conversionService.addConverter(new StringToReportColumnListConverter());
        return binder;
    }

    @Override
    public Event resetForm(final RequestContext context) throws Exception {
        final Event event = super.resetForm(context);
        this.getCommandBean(context).clearColumns();
        return event;
    }

    private Map<RBuilderEntity, Set<RBuilderColumn>> getColumnDistribution(final RequestContext context) throws Exception {
        final Map<RBuilderEntity, Set<RBuilderColumn>> picked = Maps.newTreeMap();
        final ReportConfiguration reportConfiguration = this.getCommandBean(context);
        for (final RBuilderEntity entity : reportConfiguration.getEntities()) {
            picked.put(entity, this.columnResolver.getReportableColumns(entity));
            reportConfiguration.putColumns(entity, this.columnResolver.getReportableColumns(entity));
        }
        return picked;
    }

    // TODO add support for rendering given column as the concatenated report
    private Object getColumnsRenderProperties(final Collection<Set<RBuilderColumn>> columnsSet) {
        final Map<Integer, List<RColumnRenderClass>> map = Maps.newHashMap();
        final Locale locale = LocaleContextHolder.getLocale();

        Assert.notEmpty(columnsSet);

        for (Set<RBuilderColumn> colSet : columnsSet) {
            Assert.notEmpty(colSet);
            for (final RBuilderColumn column : colSet) {
                final Set<RBuilderConvertiblePair> convertiblePairs = this.conversionHelper.getConvertiblePairs(column);
                if (CollectionUtils.isEmpty(convertiblePairs)) {
                    continue;
                }

                final List<RColumnRenderClass> properties = Lists.newArrayList();

                for (RBuilderConvertiblePair convertiblePair : convertiblePairs) {
                    final ConvertiblePair pair = convertiblePair.getConvertiblePair();
                    properties.add(new RColumnRenderClass(pair, this.messageSource.getMessage(pair.getTargetType(), locale).getName()));
                }

                map.put(column.getId(), properties);
            }
        }


        return map;
    }

    private abstract class BaseConverter
            extends MatcherConverter {
        protected Set<RBuilderColumn> doConvert(final Set<String> list) {
            LOGGER.trace(String.format("converting with selected clazz=%s", list));
            Preconditions.checkNotNull(list);
            Preconditions.checkArgument(!list.isEmpty());
            final Set<RBuilderColumn> reportedColumns = Sets.newLinkedHashSet();
            for (final String javaClassName : list) {
                final RBuilderBean bean = beanResolver.getReportableBean(Integer.valueOf(javaClassName));
                if (ClassUtils.isAssignable(RBuilderColumn.class, bean.getClass())) {
                    reportedColumns.add((RBuilderColumn) bean);
                }
            }
            return reportedColumns;
        }
    }

    private class StringArrayToReportColumnListConverter
            extends BaseConverter
            implements Converter<String[], Set<RBuilderColumn>> {

        @Override
        public Set<RBuilderColumn> convert(final String[] attributes) {
            return this.doConvert(Sets.newHashSet(attributes));
        }

        @Override
        public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
            final Class<?> sourceTypeClass = sourceType.getType();
            return ClassUtils.isAssignable(String[].class, sourceTypeClass)
                    && ClassUtils.isAssignable(Set.class, targetType.getType());
        }
    }

    private class StringToReportColumnListConverter
            extends BaseConverter
            implements Converter<String, Set<RBuilderColumn>> {

        @Override
        public Set<RBuilderColumn> convert(final String clazz) {
            return this.doConvert(Sets.newHashSet(clazz));
        }

        @Override
        public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
            final Class<?> sourceTypeClass = sourceType.getType();
            return ClassUtils.isAssignable(String.class, sourceTypeClass)
                    && ClassUtils.isAssignable(Set.class, targetType.getType());
        }
    }

    private class AreColumnsSelectedForAllEntitiesValidator
            implements Validator {
        @Override
        public boolean supports(final Class<?> clazz) {
            return ClassUtils.isAssignable(ReportConfiguration.class, clazz);
        }

        @Override
        public void validate(final Object target, final Errors errors) {
            Preconditions.checkNotNull(target, "Target must not be null");
            Preconditions.checkArgument(ClassUtils.isAssignable(ReportConfiguration.class, target.getClass()));

            final String shortName = ClassUtils.getShortName(AreColumnsSelectedForAllEntitiesValidator.class);
            final ReportConfiguration targetReport = (ReportConfiguration) target;
            boolean hasNoErrors = true;

            if (!targetReport.hasEntities()) {
                errors.rejectValue("entities", "wizard.NewReportWizard.error.noEntitiesSelected");
                hasNoErrors = false;
            }
            if (hasNoErrors) {
                for (final RBuilderEntity entity : targetReport.getEntities()) {
                    if (!targetReport.hasColumn(entity)) {
                        errors.rejectValue("entities", "wizard.NewReportWizard.error.noColumnsSelectedForEntity", new Object[]{entity
                                .getName()}, null);
                    }
                }
            }
            LOGGER.trace(String.format("%s validated target=%s...valid=%s",
                    shortName,
                    target,
                    errors.hasErrors() ? "true" : "false"
            ));
        }
    }
}
