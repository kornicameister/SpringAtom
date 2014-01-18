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

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.descriptors.EntityAssociation;
import org.agatom.springatom.server.model.descriptors.SlimEntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.bean.ReportableBean;
import org.agatom.springatom.web.rbuilder.bean.ReportableEntity;
import org.agatom.springatom.web.rbuilder.bean.ReportableEntityAssociation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Scope(WebApplicationContext.SCOPE_SESSION)
@Component(value = "pickEntityFormAction")
public class PickEntityFormAction
        extends ReportWizardFormAction {
    private final static Logger LOGGER                  = Logger.getLogger(PickEntityFormAction.class);
    private static final String ENTITIES                = "entities";
    private static final String ASSOCIATION_INFORMATION = "associationInformation";

    @Autowired
    private EntityDescriptors entityDescriptors;

    public PickEntityFormAction() {
        super();
        this.setValidator(new AreEntitiesSelectedForReportValidator());
    }

    @Override
    public Event setupForm(final RequestContext context) throws Exception {
        final MutableAttributeMap<Object> scope = this.getFormObjectScope().getScope(context);
        scope.put(ENTITIES, this.getUnselectedEntities());
        scope.put(ASSOCIATION_INFORMATION, this.getAssociateInformation());
        return super.setupForm(context);
    }

    @Override
    protected WebDataBinder doInitBinder(final WebDataBinder binder, final FormattingConversionService conversionService) {
        binder.setRequiredFields("entities");
        conversionService.addConverter(new ClazzFieldListToReportEntityConverterList());
        conversionService.addConverter(new ClazzFieldToReportEntityConverter());
        return binder;
    }

    /**
     * Returns all unselected entities, that were selected in previous displaying of this step
     *
     * @return {@link java.util.Set} of unselected entities (@link ReportableEntity}
     */
    private Set<ReportableEntity> getUnselectedEntities() {
        return FluentIterable.from(this.reportWizard.getEntities())
                             .filter(new UnselectedEntityPredicate())
                             .toSet();
    }

    /**
     * Calculates set of {@link org.agatom.springatom.web.rbuilder.bean.ReportableEntityAssociation} from values retrieved from
     * {@link org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors#getAssociation(Class)} .
     *
     * @return {@link java.util.Set} of {@link org.agatom.springatom.web.rbuilder.bean.ReportableEntityAssociation}
     */
    private Set<ReportableEntityAssociation> getAssociateInformation() {
        final Set<ReportableEntityAssociation> information = Sets.newHashSet();
        for (ReportableEntity entity : this.reportWizard.getEntities()) {
            final EntityAssociation association = this.entityDescriptors.getAssociation(entity.getJavaClass());
            if (association == null) {
                continue;
            }
            final Set<Long> children = FluentIterable
                    .from(association.getAssociations())
                    .transform(new Function<SlimEntityDescriptor<?>, Long>() {
                        @Nullable
                        @Override
                        public Long apply(@Nullable final SlimEntityDescriptor<?> input) {
                            if (input == null) {
                                return null;
                            }
                            final ReportableEntity reportableEntity = reportWizard.getEntity(input.getJavaClass());
                            if (reportableEntity == null) {
                                return null;
                            }
                            return Long.valueOf(reportableEntity.getId());
                        }
                    })
                    .toSet();
            information.add(new ReportableEntityAssociation()
                    .setMaster(Long.valueOf(entity.getId()))
                    .setChildren(children)
            );
        }
        return information;
    }

    private class UnselectedEntityPredicate
            implements Predicate<ReportableEntity> {
        @Override
        public boolean apply(@Nullable final ReportableEntity input) {
            assert input != null;
            return !reportWizard.getReportConfiguration().hasEntity(input.getJavaClass());
        }
    }

    private class AreEntitiesSelectedForReportValidator
            implements Validator {
        @Override
        public boolean supports(final Class<?> clazz) {
            return ClassUtils.isAssignable(ReportConfiguration.class, clazz);
        }

        @Override
        public void validate(final Object target, final Errors errors) {
            Preconditions.checkNotNull(target, "Target must not be null");
            Preconditions.checkArgument(ClassUtils.isAssignable(ReportConfiguration.class, target.getClass()));

            final String shortName = ClassUtils.getShortName(AreEntitiesSelectedForReportValidator.class);
            final ReportConfiguration targetReport = (ReportConfiguration) target;

            if (!targetReport.hasEntities()) {
                errors.rejectValue("entities", "wizard.NewReportWizard.error.noEntitiesSelected");
            }
            LOGGER.trace(String.format("%s validated target=%s...valid=%s",
                    shortName,
                    target,
                    errors.hasErrors() ? "true" : "false"
            ));
        }
    }

    private abstract class BaseConverter
            extends MatcherConverter {
        protected Set<ReportableEntity> doConvert(final Set<String> list) {
            LOGGER.trace(String.format("converting with selected clazz=%s", list));
            Preconditions.checkNotNull(list);
            Preconditions.checkArgument(!list.isEmpty());
            final Set<ReportableEntity> reportedEntities = Sets.newHashSet();
            for (final String javaClassName : list) {
                final ReportableBean bean = reportWizard.getReportableBean(Integer.valueOf(javaClassName));
                if (ClassUtils.isAssignable(ReportableEntity.class, bean.getClass())) {
                    reportedEntities.add((ReportableEntity) bean);
                }
            }
            return reportedEntities;
        }
    }

    private class ClazzFieldListToReportEntityConverterList
            extends BaseConverter
            implements Converter<String[], Set<ReportableEntity>> {

        @Override
        public Set<ReportableEntity> convert(final String[] attributes) {
            return this.doConvert(Sets.newHashSet(attributes));
        }

        @Override
        public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
            final Class<?> sourceTypeClass = sourceType.getType();
            return ClassUtils.isAssignable(String[].class, sourceTypeClass)
                    && ClassUtils.isAssignable(Set.class, targetType.getType());
        }
    }

    private class ClazzFieldToReportEntityConverter
            extends BaseConverter
            implements Converter<String, Set<ReportableEntity>> {

        @Override
        public Set<ReportableEntity> convert(final String clazz) {
            return this.doConvert(Sets.newHashSet(clazz));
        }

        @Override
        public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
            final Class<?> sourceTypeClass = sourceType.getType();
            return ClassUtils.isAssignable(String.class, sourceTypeClass)
                    && ClassUtils.isAssignable(Set.class, targetType.getType());
        }
    }

}
