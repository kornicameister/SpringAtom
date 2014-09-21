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

package org.agatom.springatom.web.component.infopages.provider.builder;

import org.agatom.springatom.core.exception.SException;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.web.component.infopages.elements.InfoPageAttributeComponent;
import org.agatom.springatom.web.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.web.component.infopages.elements.InfoPagePanelComponent;
import org.agatom.springatom.web.component.infopages.elements.meta.AttributeDisplayAs;
import org.agatom.springatom.web.component.infopages.mapping.InfoPageMappingService;
import org.agatom.springatom.web.component.infopages.provider.InfoPageProviderService;
import org.agatom.springatom.web.component.infopages.provider.structure.InfoPage;
import org.agatom.springatom.web.component.infopages.provider.structure.InfoPageAttribute;
import org.agatom.springatom.web.component.infopages.provider.structure.InfoPagePanel;
import org.agatom.springatom.web.component.infopages.provider.structure.InfoPageTitle;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.locale.beans.LocalizedClassModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 25.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("Creates InfoPageComponent out of InfoPage structure")
class InfoPageComponentBuilderServiceImpl
		implements InfoPageComponentBuilderService {
	private static final Logger                  LOGGER            = Logger.getLogger(InfoPageComponentBuilderServiceImpl.class);
	@Autowired
	private              InfoPageProviderService provider          = null;
	@Autowired
	private              InfoPageMappingService  mapping           = null;
	@Autowired
	private              EntityDescriptors       entityDescriptors = null;
	@Autowired
	private              SMessageSource          messageSource     = null;

	/** {@inheritDoc} */
	@Override
	public InfoPageComponent buildInfoPage(@Nonnull final InfoPage page) {

		LOGGER.debug(String.format("buildInfoPage(page=%s)", page));

		final long startTime = System.nanoTime();
		final InfoPageComponent component = this.buildComponent(new InfoPageComponent(), page);
		final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

		LOGGER.trace(String.format("Built InfoPageComponent in %dms", endTime));

		component.addDynamicProperty("compilationTime", endTime);

		return component;
	}

	/** {@inheritDoc} */
	@Override
	public InfoPageComponent buildInfoPage(@Nonnull final String path) throws SException {
		LOGGER.debug(String.format("buildInfoPage(path=%s)", path));
		return this.buildInfoPage(this.mapping.getMappedClass(path));
	}

	/** {@inheritDoc} */
	@Override
	public <T extends Persistable<?>> InfoPageComponent buildInfoPage(@Nonnull final Class<T> domainClass) throws SException {
		LOGGER.debug(String.format("buildInfoPage(domainClass=%s)", domainClass));
		return this.buildInfoPage(this.provider.getInfoPage(domainClass));
	}

	private InfoPageComponent buildComponent(final InfoPageComponent cmp, final InfoPage page) {
		LOGGER.debug(String.format("buildComponent(cmp=%s,page=%s)", cmp, page));

		final Class<?> pageDomain = page.getDomain();
		final Locale locale = LocaleContextHolder.getLocale();

		final EntityDescriptor<?> descriptor = this.entityDescriptors.getDescriptor(pageDomain);
		final LocalizedClassModel<?> rbModel = this.messageSource.getLocalizedClassModel(pageDomain, locale);
		final EntityType<?> entityType = descriptor.getEntityType();

		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace(String.format("Retrieved EntityDescriptor ==> %s", descriptor));
			LOGGER.trace(String.format("Retrieved EntityType ==> %s", entityType));
			LOGGER.trace(String.format("Retrieved LocalizedClassModel ==> %s", rbModel));
		}

		for (final InfoPagePanel panel : page) {
			LOGGER.trace(String.format("Processing InfoPagePanel=%s", panel.getId()));

			final InfoPagePanelComponent panelComponent = new InfoPagePanelComponent();
			panelComponent.setLabel(panel.getTitle().getTitle(locale));
			panelComponent.setPosition(panel.getPosition());
			panelComponent.setIconCfg(panel.getIcon());

			for (final InfoPageAttribute attribute : panel) {
				Attribute<?, ?> descriptorOfAttribute = null;
				try {
					descriptorOfAttribute = entityType.getAttribute(attribute.getPath());
				} catch (Exception exp) {
					LOGGER.warn(String.format("%s has no corresponding meta attribute", attribute.getPath()));
				}
				panelComponent.addContent(this.buildAttributeComponent(attribute, rbModel, descriptorOfAttribute, locale));
			}

			LOGGER.trace(String.format("Created InfoPagePanelComponent=%s", panelComponent));

			cmp.addContent(panelComponent);
		}

		// setting properties
		cmp.setId(page.getId());
		cmp.setLabel(rbModel.getName());
		cmp.setDomain(page.getDomain());

		cmp.addDynamicProperty("entityTypeName", entityType.getName());
		cmp.addDynamicProperty("idType", entityType.getIdType());
		cmp.addDynamicProperty("versioned", entityType.hasVersionAttribute());

		LOGGER.trace(String.format("Created InfoPageComponent=%s", cmp));

		return cmp;
	}

	private InfoPageAttributeComponent buildAttributeComponent(
			final InfoPageAttribute attribute,
			final LocalizedClassModel<?> rbModel,
			final Attribute<?, ?> descriptor,
			final Locale locale) {
		LOGGER.debug(String.format("buildAttributeComponent(path=%s)", attribute.getPath()));
		final InfoPageAttributeComponent cmp = new InfoPageAttributeComponent();

		cmp.setLabel(this.getAttributeTitle(attribute, locale, rbModel));
		cmp.setPath(attribute.getPath());
		cmp.setPosition(attribute.getPosition());

		cmp.addDynamicProperty("isDynamic", descriptor == null);

		if (descriptor != null) {
			cmp.setDisplayAs(this.getDisplayAs(descriptor));
			cmp.addDynamicProperty("attributeName", descriptor.getName());
			cmp.addDynamicProperty("isAssociation", descriptor.isAssociation());
			cmp.addDynamicProperty("attributeClass", descriptor.getJavaType().getName());

		} else if (attribute.isGridAttribute()) {
			cmp.setDisplayAs(AttributeDisplayAs.TABLE_ATTRIBUTE);
			cmp.addDynamicProperty("builderId", attribute.getBuilderId());
		} else {
			cmp.setDisplayAs(AttributeDisplayAs.VALUE_ATTRIBUTE);
		}

		return cmp;
	}

	private String getAttributeTitle(final InfoPageAttribute attribute, final Locale locale, final LocalizedClassModel<?> rbModel) {
		String localTitle = null;
		final InfoPageTitle title = attribute.getTitle();
		if (title != null) {
			localTitle = title.getTitle(locale);
		}
		if (localTitle == null) {
			final String attributePath = attribute.getPath();
			try {
				localTitle = rbModel.getLocalizedAttribute(attributePath);
			} catch (Exception exp) {
				LOGGER.warn(String.format("Failed to determing valid title of attribute %s", attributePath));
				localTitle = attributePath;
			}
		}
		return localTitle;
	}

	private AttributeDisplayAs getDisplayAs(final Attribute<?, ?> descriptor) {
		switch (descriptor.getPersistentAttributeType()) {
			case BASIC:
				return AttributeDisplayAs.VALUE_ATTRIBUTE;
			case MANY_TO_ONE:
			case ONE_TO_ONE:
				return AttributeDisplayAs.INFOPAGE_ATTRIBUTE;
			case ONE_TO_MANY:
				return AttributeDisplayAs.TABLE_ATTRIBUTE;
			case EMBEDDED:
				return AttributeDisplayAs.EMBEDDED_ATTRIBUTE;
			default:
				return AttributeDisplayAs.VALUE_ATTRIBUTE;
		}
	}

}
