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

package org.agatom.springatom.web.component.core.builders;

import com.google.common.collect.Lists;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.web.component.core.builders.exception.ComponentException;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.data.ComponentDataResponse;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * {@code AbstractComponentDataBuilder} is a <b>component builder</b> that can be extended
 * for builder returning nothing but data.
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 27.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractComponentDataBuilder
		extends AbstractBuilder
		implements ComponentDataBuilder {

	@Override
	public final ComponentDataResponse getData(final ComponentDataRequest dataRequest) throws ComponentException {
		this.logger.debug(String.format("getData(dataRequest=%s)", dataRequest));
		final long startTime = System.nanoTime();
		try {
			Assert.notNull(dataRequest, "DataRequest can not be null");

			final Object data = this.buildData(dataRequest);

			if (data == null) {
				this.logger.warn(String.format("For request=%s, builder returned null data", dataRequest));
			} else {
				this.logger.trace(String.format("For request=%s, builder returned data=%s", dataRequest, data));
			}

			final long endTime = System.nanoTime() - startTime;
			this.logger.info(String.format("getData(dataRequest=%s) executed in %dms", dataRequest, TimeUnit.NANOSECONDS.toMillis(endTime)));

			return ComponentDataResponse.success(this.getId(), data, endTime);
		} catch (Exception exp) {
			this.logger.fatal(String.format("getData(dataRequest=%s) failed...", dataRequest), exp);
			return ComponentDataResponse.error(this.getId(), exp, System.nanoTime() - startTime);
		}
	}

	/**
	 * Implement this method to return actual data of this builder
	 *
	 * @param dataRequest request to work with
	 *
	 * @return response for this builder
	 *
	 * @throws ComponentException if any
	 */
	protected abstract Object buildData(final ComponentDataRequest dataRequest) throws ComponentException;

	/**
	 * Retrieves {@link javax.persistence.metamodel.Attribute} for given path out of {@link #getEntityDescriptor()}
	 *
	 * @param path lookup path
	 *
	 * @return persistence attribute
	 */
	protected Attribute<?, ?> getEntityAttribute(final String path) {
		EntityType<?> entityType = this.getEntityDescriptor().getEntityType();
		Attribute<?, ?> attribute = null;
		try {
			final List<String> paths = Lists.newArrayListWithExpectedSize(1);
			if (path.contains(".")) {
				paths.addAll(Lists.newArrayList(StringUtils.split(path, ".")));
			} else {
				paths.add(path);
			}
			if (paths.size() == 1) {
				attribute = entityType.getAttribute(path);
			} else {
				for (int i = 0; i < paths.size(); i++) {
					final String nestedPath = paths.get(i);
					attribute = entityType.getAttribute(nestedPath);
					if (i != paths.size() - 1) {
						entityType = this.getEntityDescriptor(attribute.getJavaType()).getEntityType();
					}
				}
			}
		} finally {
			if (attribute != null) {
				this.logger.trace(String.format("%s has no attribute %s", entityType.getName(), path));
			}
		}
		return attribute;
	}

	/**
	 * Returns either {@link org.springframework.beans.factory.annotation.Autowired} or set by other means
	 * value the object of {@link org.agatom.springatom.server.model.descriptors.EntityDescriptor}
	 *
	 * @return the entity descriptor
	 */
	protected abstract EntityDescriptor getEntityDescriptor();

	/**
	 * Returns {@link org.agatom.springatom.server.model.descriptors.EntityDescriptor} for particular {@code forClass}
	 *
	 * @param forClass java type to look descriptor for
	 *
	 * @return descriptor for {@code forClass}
	 */
	protected abstract EntityDescriptor getEntityDescriptor(final Class<?> forClass);
}
