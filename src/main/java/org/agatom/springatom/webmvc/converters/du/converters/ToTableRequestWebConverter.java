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

package org.agatom.springatom.webmvc.converters.du.converters;

import com.google.common.collect.Maps;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.types.PersistentVersionedBean;
import org.agatom.springatom.web.component.core.builders.ComponentProduces;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.repository.ComponentBuilderRepository;
import org.agatom.springatom.webmvc.controllers.components.SVComponentsDataController;
import org.agatom.springatom.webmvc.controllers.components.SVComponentsDefinitionController;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.component.core.DefaultWebDataComponent;
import org.agatom.springatom.webmvc.converters.du.exception.WebConverterException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import java.io.Serializable;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * {@link org.agatom.springatom.webmvc.converters.du.converters.ToTableRequestWebConverter}
 * crates instance of {@link org.agatom.springatom.webmvc.converters.du.converters.ToTableRequestWebConverter.TableRequest} component.
 * Provides information required to make a request to retrieve table configuration and data.
 * <small>Class is a part of <b>SpringAtom</b> and was created at 01.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WebConverter(key = ToTableRequestWebConverter.SELECTOR)
public class ToTableRequestWebConverter
		extends AbstractWebConverter {
	public static final  String                     SELECTOR          = "tableRequestInitMaker";
	private static final Logger                     LOGGER            = Logger.getLogger(ToTableRequestWebConverter.class);
	@Autowired
	private              ComponentBuilderRepository builderRepository = null;

	@Override
	protected Serializable doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) throws Exception {
		LOGGER.trace(String.format("doConverter(key=%s,value=%s)", key, value));
		final EntityDescriptor<? extends Persistable> descriptor = this.entityDescriptors.getDescriptor(persistable.getClass());

		final Attribute<?, ?> attribute = descriptor.getEntityType().getAttribute(key);
		if (!attribute.isAssociation() || !attribute.isCollection()) {
			throw new WebConverterException(String.format("Requested to convert %s as table request, but %s was not recognized as association for %s", key, key, ClassUtils.getUserClass(persistable.getClass())));
		}

		final PluralAttribute<?, ?, ?> pluralAttribute = (PluralAttribute<?, ?, ?>) attribute;
		final Class<?> associatedType = ClassUtils.getUserClass(pluralAttribute.getElementType().getJavaType());

		LOGGER.trace(String.format("%s corresponds to %s", key, ClassUtils.getUserClass(associatedType)));

		final boolean hasBuilder = this.builderRepository.hasBuilder(associatedType, ComponentProduces.TABLE_COMPONENT);

		LOGGER.trace(String.format("%s %s to existing %s builder", associatedType, (hasBuilder ? "corresponds" : "does not corresponds"), ComponentProduces.TABLE_COMPONENT));

		if (hasBuilder) {
			final String builderId = this.builderRepository.getBuilderId(associatedType, ComponentProduces.TABLE_COMPONENT);
			final TableRequest request = new TableRequest();

			request.setTitle(this.getLabel(key, persistable));
			request.addDynamicProperty("builderId", builderId);
			request.addDynamicProperty("configurationUrl", linkTo(methodOn(SVComponentsDefinitionController.class).onTableConfigRequest(null, null)).withSelfRel().getHref());
			request.addDynamicProperty("dataUrl", linkTo(methodOn(SVComponentsDataController.class).onTableDataRequest(null, null)).withSelfRel().getHref());
			request.addDynamicProperty("domain", associatedType.getName());

			final Map<String, Object> context = Maps.newHashMap();
			context.put("domain", ClassUtils.getUserClass(persistable));
			context.put("id", persistable.getId());
			context.put("version", ClassUtils.isAssignableValue(PersistentVersionedBean.class, persistable) ? ((PersistentVersionedBean) persistable).getVersion() : -1);
			request.setValue(context);

			return request;
		}

		return null;
	}

	protected static class TableRequest
			extends DefaultWebDataComponent<Map<String, Object>> {
		private static final long serialVersionUID = 4160071560623816868L;

		@Override
		public String getUiType() {
			return "tableRequest";
		}
	}

}
