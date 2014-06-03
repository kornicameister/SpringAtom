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

package org.agatom.springatom.server.model.descriptors.descriptor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.EntityDescriptorReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * {@code DynamicDescriptorReader} is reader designed to read {@link org.agatom.springatom.server.model.descriptors.EntityDescriptor} dynamically
 * using {@link javax.persistence.metamodel.Metamodel} from JPA standard.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Qualifier(value = "dynamicDescriptorReader")
public class DynamicEntityDescriptorReader
		implements EntityDescriptorReader {
	private static final Logger                               LOGGER    = Logger.getLogger(DynamicEntityDescriptorReader.class);
	private              Cache<Class<?>, EntityDescriptor<?>> cache     = null;
	private              Metamodel                            metamodel = null;

	@PostConstruct
	private void initialize() {
		this.cache = CacheBuilder.<Class<?>, EntityDescriptor<?>>newBuilder()
				.maximumSize(200)
				.expireAfterAccess(60, TimeUnit.MINUTES)
				.expireAfterWrite(60, TimeUnit.MINUTES)
				.build();
		Assert.notNull(this.cache);
	}

	/**
	 * <p>Setter for the field <code>metamodel</code>.</p>
	 *
	 * @param metamodel a {@link javax.persistence.metamodel.Metamodel} object.
	 */
	public void setMetamodel(final Metamodel metamodel) {
		this.metamodel = metamodel;
	}

	/** {@inheritDoc} */
	@Override
	public Set<EntityDescriptor<?>> getDefinitions() {
		final Set<EntityDescriptor<?>> descriptors = Sets.newHashSet();
		final Set<EntityType<?>> entities = this.metamodel.getEntities();
		for (final EntityType<?> entityType : entities) {
			final Class<?> javaType = entityType.getJavaType();
			if (javaType != null) {
				descriptors.add(this.getDefinition(javaType, false));
			} else {
				LOGGER.warn(String.format("%s return null javaType...skipping", entityType.getName()));
			}
		}
		return descriptors;
	}

	/** {@inheritDoc} */
	@Override
	public <X> EntityDescriptor<X> getDefinition(final Class<X> xClass) {
		return this.getDefinition(xClass, true);
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public <X> EntityDescriptor<X> getDefinition(final Class<X> xClass, final boolean initialize) {
		LOGGER.trace(String.format("/getDefinition => xClass -> %s\tinitialize -> %s", ClassUtils.getShortName(xClass), initialize));
		if (this.containsKey(xClass)) {
			return (EntityDescriptor<X>) this.cache.getIfPresent(xClass);
		}
		final EntityType<?> entityType = this.getEntityType(xClass);
		if (entityType != null) {
			final EntityTypeDescriptor<?> descriptor = new EntityTypeDescriptor<>(entityType);
			if (initialize) {
				descriptor.initialize();
			}
			this.cache.put(xClass, descriptor);
			return (EntityDescriptor<X>) descriptor;
		}
		LOGGER.trace(String.format("Could not retrieve %s for %s", ClassUtils.getShortName(EntityDescriptor.class), ClassUtils.getShortName(xClass)));
		return null;
	}

	private <X> boolean containsKey(final Class<X> xClass) {
		final boolean isPresent = this.cache.getIfPresent(xClass) != null;
		if (!isPresent) {
			this.cache.invalidate(xClass);
		}
		return isPresent;
	}

	private EntityType<?> getEntityType(final Class<?> xClass) {
		try {
			return this.metamodel.entity(xClass);
		} catch (Exception e) {
			LOGGER.warn(String.format("Could not retrieve %s for %s, error is=\n%s", ClassUtils.getShortName(EntityType.class), ClassUtils
					.getShortName(xClass), e.getMessage()));
		}
		return null;
	}
}
