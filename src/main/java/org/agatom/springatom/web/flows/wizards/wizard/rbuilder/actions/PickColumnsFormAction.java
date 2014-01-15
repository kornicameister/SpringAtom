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

package org.agatom.springatom.web.flows.wizards.wizard.rbuilder.actions;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.bean.ReportableBean;
import org.agatom.springatom.web.rbuilder.bean.ReportableColumn;
import org.agatom.springatom.web.rbuilder.bean.ReportableEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Scope(WebApplicationContext.SCOPE_SESSION)
@Component(value = "pickColumnsFormAction")
public class PickColumnsFormAction
        extends ReportWizardFormAction {
    private static final Logger LOGGER = Logger.getLogger(PickColumnsFormAction.class);

    public PickColumnsFormAction() {
        super();
        this.setValidator(new AreColumnsSelectedForAllEntitiesValidator());
    }

    @Override
    public Event setupForm(final RequestContext context) throws Exception {
        context.getViewScope().put("entityToColumn", this.reportWizard.getEntityToColumnForReport());
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
        final ReportConfiguration report = this.reportWizard.getReportConfiguration();
        Assert.notNull(report);
        report.clearColumns();
        return success();
    }

    private abstract class BaseConverter
            extends MatcherConverter {
        protected Set<ReportableColumn> doConvert(final Set<String> list) {
            LOGGER.trace(String.format("converting with selected clazz=%s", list));
            Preconditions.checkNotNull(list);
            Preconditions.checkArgument(!list.isEmpty());
            final Set<ReportableColumn> reportedColumns = Sets.newTreeSet();
            for (final String javaClassName : list) {
                final ReportableBean bean = reportWizard.getReportableBean(Integer.valueOf(javaClassName));
                if (ClassUtils.isAssignable(ReportableColumn.class, bean.getClass())) {
                    reportedColumns.add((ReportableColumn) bean);
                }
            }
            return reportedColumns;
        }


    }

    private class StringArrayToReportColumnListConverter
            extends BaseConverter
            implements Converter<String[], Set<ReportableColumn>> {

        @Override
        public Set<ReportableColumn> convert(final String[] attributes) {
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
            implements Converter<String, Set<ReportableColumn>> {

        @Override
        public Set<ReportableColumn> convert(final String clazz) {
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
                for (final ReportableEntity entity : targetReport.getEntities()) {
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
