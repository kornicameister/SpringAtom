/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
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

package org.agatom.springatom.web.component.infopages.builder;

import com.google.common.collect.Maps;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.agatom.springatom.server.repository.SRepository;
import org.agatom.springatom.web.component.core.builders.AbstractComponentDataBuilder;
import org.agatom.springatom.web.component.core.builders.exception.ComponentException;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.repository.ComponentBuilderRepository;
import org.agatom.springatom.web.component.core.request.ComponentRequestAttribute;
import org.agatom.springatom.web.component.infopages.link.InfoPageLinkHelper;
import org.agatom.springatom.web.locale.SMessageSource;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Persistable;
import org.springframework.data.history.Revision;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Set;

/**
 * {@code InfoPageComponentBuilder} is abstract builder designed to to be proxied
 * with specific class as an generic type. Scoped as {@link org.springframework.beans.factory.config.BeanDefinition#SCOPE_PROTOTYPE}
 * takes advantage from dependency injection process.
 *
 * @param <Y> marker for {@link org.springframework.data.domain.Persistable} that builder builds data for
 *
 * @author kornicameister
 * @version 0.0.4
 * @since 0.0.1
 */
public abstract class InfoPageComponentBuilder<Y extends Persistable<?>>
		extends AbstractComponentDataBuilder {
	protected static final String                     BUILDER_ID                 = "genericInfoPageBuilder";
	protected              SBasicRepository<Y, Long>  repository                 = null;
	protected              EntityDescriptors          descriptors                = null;
	protected              ComponentBuilderRepository componentBuilderRepository = null;
	protected              SMessageSource             messageSource              = null;
	protected              Class<Y>                   entity                     = null;
	protected              InfoPageLinkHelper         linkHelper                 = null;

	public InfoPageComponentBuilder(final Class<Y> domainClass) {
		super();
		this.entity = domainClass;
	}

	/**
	 * Returns fixed <b>ID</b> of {@link org.agatom.springatom.web.component.infopages.builder.InfoPageComponentBuilder}
	 *
	 * @return {@link #BUILDER_ID}
	 */
	@Override
	public String getId() {
		return BUILDER_ID;
	}

	@Override
	protected Object buildData(final ComponentDataRequest request) throws ComponentException {
		this.logger.trace(String.format("buildData(dataRequest=%s)", request));

		final Long objectId = request.getId();
		final Long objectVersion = request.getVersion();
		final Set<ComponentRequestAttribute> attributes = request.getAttributes();

		final Y object = this.getOne(objectId, objectVersion);
		final BeanWrapper bw = new BeanWrapperImpl(object);

		this.logger.trace(String.format("processing object %s=%s", object, object.getId()));

		final Map<String, Object> data = Maps.newHashMap();

		for (final ComponentRequestAttribute attribute : attributes) {
			this.logger.trace(String.format("Processing attribute for path=%s from %s", attribute.getPath(), attribute));

			final String path = attribute.getPath();
			final Object propertyValue = bw.getPropertyValue(path);

			this.logger.trace(String.format("Processed attribute for path=%s to %s", path, propertyValue));

			data.put(path, propertyValue);
		}

		return new InfoPageResponseWrapper().setData(data).setSource(object);
	}

	/**
	 * Returns single {@code Y} {@link org.springframework.data.domain.Persistable} object.
	 * If version is different than <b>-1</b>, method attempts to cast from {@link org.agatom.springatom.server.repository.SBasicRepository}
	 * to {@link org.agatom.springatom.server.repository.SRepository} to retrieve object in given revision.
	 *
	 * @param id      the id
	 * @param version the version
	 *
	 * @return {@link org.springframework.data.domain.Persistable} instance
	 *
	 * @throws ComponentException if {@link #repository} is not {@link org.agatom.springatom.server.repository.SRepository}
	 */
	private Y getOne(final Long id, final Long version) throws ComponentException {
		if (version != null && !version.equals(-1l)) {
			if (ClassUtils.isAssignableValue(SRepository.class, this.repository)) {
				final Revision<Long, Y> inRevision = ((SRepository<Y, Long, Long>) this.repository).findInRevision(id, version);
				this.logger.trace(String.format("getOne(id=%s,version=%s) found revision=%s", id, version, inRevision));
				return inRevision.getEntity();
			} else {
				throw new ComponentException(String.format("Version was specified in the request, but repository %s does not support", ClassUtils.getShortName(this.repository.getClass())));
			}
		}
		return this.repository.findOne(id);
	}

	@Override
	protected EntityDescriptor<Y> getEntityDescriptor() {
		return this.descriptors.getDescriptor(this.entity);
	}

	@Override
	protected EntityDescriptor getEntityDescriptor(final Class<?> forClass) {
		return this.descriptors.getDescriptor(forClass);
	}

	protected String getEntityName() {
		return this.getEntityDescriptor().getEntityType().getName();
	}
}
