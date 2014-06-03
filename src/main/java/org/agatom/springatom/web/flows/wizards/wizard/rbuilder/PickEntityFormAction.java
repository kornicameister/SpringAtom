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
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.flows.wizards.actions.WizardAction;
import org.agatom.springatom.web.flows.wizards.wizard.WizardFormAction;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.bean.RBuilderAssociation;
import org.agatom.springatom.web.rbuilder.bean.RBuilderBean;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.model.ReportableBeanResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
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

@WizardAction(value = "pickEntityFormAction")
public class PickEntityFormAction
		extends WizardFormAction<ReportConfiguration> {
	private final static Logger LOGGER                  = Logger.getLogger(PickEntityFormAction.class);
	private static final String ENTITIES                = "entities";
	private static final String ASSOCIATION_INFORMATION = "associationInformation";

	@Autowired
	private ReportableBeanResolver reportableBeanResolver;

	public PickEntityFormAction() {
		super();
		this.setValidator(new AreEntitiesSelectedForReportValidator());
	}

	@Override
	public Event setupForm(final RequestContext context) throws Exception {
		final MutableAttributeMap<Object> scope = this.getFormObjectScope().getScope(context);
		final Set<RBuilderEntity> entities = this.getReportableEntities(context);
		scope.put(ENTITIES, entities);
		scope.put(ASSOCIATION_INFORMATION, this.getReportableAssociations(entities));
		return super.setupForm(context);
	}

	@Override
	public Event resetForm(final RequestContext context) throws Exception {
		final Event event = super.resetForm(context);
		this.getCommandBean(context).clearEntities();
		return event;
	}

	/**
	 * Returns all unselected entities, that were selected in previous displaying of this step
	 *
	 * @param context {@link org.springframework.webflow.execution.RequestContext} of flow execution
	 *
	 * @return {@link java.util.Set} of unselected entities (@link ReportableEntity}
	 *
	 * @throws java.lang.Exception if any
	 */
	private Set<RBuilderEntity> getReportableEntities(final RequestContext context) throws Exception {
		final ReportConfiguration commandBean = this.getCommandBean(context);
		return FluentIterable.from(this.reportableBeanResolver.getReportableEntities())
				.filter(new Predicate<RBuilderEntity>() {
					@Override
					public boolean apply(@Nullable final RBuilderEntity input) {
						return input == null || !commandBean.hasEntity(input);
					}
				})
				.toSet();
	}

	/**
	 * Calculates set of {@link org.agatom.springatom.web.rbuilder.bean.RBuilderAssociation} from values retrieved from
	 * {@link org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors#getAssociation(Class)} .
	 *
	 * @param context {@link org.springframework.webflow.execution.RequestContext} of flow execution
	 *
	 * @return {@link java.util.Set} of {@link org.agatom.springatom.web.rbuilder.bean.RBuilderAssociation}
	 *
	 * @throws java.lang.Exception if any
	 */
	private Set<RBuilderAssociation> getReportableAssociations(final Set<RBuilderEntity> context) throws Exception {
		return this.reportableBeanResolver.getEntityAssociations(ImmutableSet.copyOf(context));
	}

	@Override
	protected WebDataBinder doInitBinder(final WebDataBinder binder, final FormattingConversionService conversionService) {
		binder.setRequiredFields("entities");
		conversionService.addConverter(new ClazzFieldListToReportEntityConverterList());
		conversionService.addConverter(new ClazzFieldToReportEntityConverter());
		return binder;
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
		protected Set<RBuilderEntity> doConvert(final Set<String> list) {
			LOGGER.trace(String.format("converting with selected clazz=%s", list));
			Preconditions.checkNotNull(list);
			Preconditions.checkArgument(!list.isEmpty());
			final Set<RBuilderEntity> reportedEntities = Sets.newHashSet();
			for (final String beanIdentifierString : list) {
				final Integer identifier = Integer.valueOf(beanIdentifierString);
				final RBuilderBean bean = reportableBeanResolver.getReportableBean(identifier);
				if (ClassUtils.isAssignable(RBuilderEntity.class, bean.getClass())) {
					reportedEntities.add((RBuilderEntity) bean);
				}
			}
			return reportedEntities;
		}
	}

	private class ClazzFieldListToReportEntityConverterList
			extends BaseConverter
			implements Converter<String[], Set<RBuilderEntity>> {

		@Override
		public Set<RBuilderEntity> convert(final String[] attributes) {
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
			implements Converter<String, Set<RBuilderEntity>> {

		@Override
		public Set<RBuilderEntity> convert(final String clazz) {
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
