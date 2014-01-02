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

package org.agatom.springatom.webmvc.flows.wizard.rbuilder.form;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.beans.report.SReportEntity;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.model.types.report.ReportEntity;
import org.agatom.springatom.webmvc.flows.wizard.rbuilder.bean.ReportableBean;
import org.agatom.springatom.webmvc.flows.wizard.rbuilder.bean.ReportableEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component(value = "pickEntityFormAction")
public class PickEntityFormAction
        extends ReportWizardFormAction {
    private final static Logger LOGGER = Logger.getLogger(PickEntityFormAction.class);

    public PickEntityFormAction() {
        super();
        this.setValidator(new AreEntitiesSelectedForReportValidator());
    }

    @Override
    public Event setupForm(final RequestContext context) throws Exception {
        context.getViewScope().put("entities", this.getUnselectedEntities());
        return super.setupForm(context);
    }

    @Override
    protected WebDataBinder doInitBinder(final WebDataBinder binder, final FormattingConversionService conversionService) {
        binder.setRequiredFields("entities");
        conversionService.addConverter(new ClazzFieldListToReportEntityConverterList());
        conversionService.addConverter(new ClazzFieldToReportEntityConverter());
        return binder;
    }

    private Set<ReportableEntity> getUnselectedEntities() {
        return FluentIterable.from(this.reportWizard.getEntities())
                             .filter(new UnselectedEntityPredicate())
                             .toSet();
    }

    private class UnselectedEntityPredicate
            implements Predicate<ReportableEntity> {
        @Override
        public boolean apply(@Nullable final ReportableEntity input) {
            assert input != null;
            return !reportWizard.getReport().hasEntity(input.getJavaClass());
        }
    }

    private class AreEntitiesSelectedForReportValidator
            implements Validator {
        @Override
        public boolean supports(final Class<?> clazz) {
            return ClassUtils.isAssignable(SReport.class, clazz);
        }

        @Override
        public void validate(final Object target, final Errors errors) {
            Preconditions.checkNotNull(target, "Target must not be null");
            Preconditions.checkArgument(ClassUtils.isAssignable(Report.class, target.getClass()));

            final String shortName = ClassUtils.getShortName(AreEntitiesSelectedForReportValidator.class);
            final Report targetReport = (Report) target;
            boolean hasNoErrors = true;

            if (!targetReport.hasEntities()) {
                errors.rejectValue("entities", "wizard.NewReportWizard.error.noEntitiesSelected");
                hasNoErrors = false;
            }
            LOGGER.trace(String.format("%s validated target=%s...valid=%s",
                    shortName,
                    target,
                    hasNoErrors ? "true" : "false"
            ));
        }
    }

    private abstract class BaseConverter
            extends MatcherConverter {
        protected List<ReportEntity> doConvert(final List<String> list) {
            LOGGER.trace(String.format("converting with selected clazz=%s", list));
            Preconditions.checkNotNull(list);
            Preconditions.checkArgument(!list.isEmpty());
            final List<ReportEntity> reportedEntities = Lists.newArrayList();
            for (final String javaClassName : list) {
                final ReportableBean bean = reportWizard.getReportableBean(Integer.valueOf(javaClassName));
                if (ClassUtils.isAssignable(ReportableEntity.class, bean.getClass())) {
                    final Class<?> aClass = ((ReportableEntity) bean).getJavaClass();
                    final String label = reportWizard.getEntity(aClass).getLabel();
                    reportedEntities.add(new SReportEntity().setClazz(aClass).setName(label));
                }
            }
            return reportedEntities;
        }


    }

    private class ClazzFieldListToReportEntityConverterList
            extends BaseConverter
            implements Converter<String[], List<ReportEntity>> {

        @Override
        public List<ReportEntity> convert(final String[] attributes) {
            return this.doConvert(Lists.newArrayList(attributes));
        }

        @Override
        public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
            final Class<?> sourceTypeClass = sourceType.getType();
            return ClassUtils.isAssignable(String[].class, sourceTypeClass)
                    && ClassUtils.isAssignable(List.class, targetType.getType());
        }
    }

    private class ClazzFieldToReportEntityConverter
            extends BaseConverter
            implements Converter<String, List<ReportEntity>> {

        @Override
        public List<ReportEntity> convert(final String clazz) {
            return this.doConvert(Lists.newArrayList(clazz));
        }

        @Override
        public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
            final Class<?> sourceTypeClass = sourceType.getType();
            return ClassUtils.isAssignable(String.class, sourceTypeClass)
                    && ClassUtils.isAssignable(List.class, targetType.getType());
        }
    }

}
