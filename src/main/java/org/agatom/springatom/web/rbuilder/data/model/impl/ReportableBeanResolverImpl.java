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

package org.agatom.springatom.web.rbuilder.data.model.impl;

import com.google.common.collect.Sets;
import com.rits.cloning.Cloner;
import org.agatom.springatom.server.model.descriptors.SlimEntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.agatom.springatom.web.rbuilder.bean.RBuilderAssociation;
import org.agatom.springatom.web.rbuilder.bean.RBuilderBean;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.model.ReportableAssociationResolver;
import org.agatom.springatom.web.rbuilder.data.model.ReportableBeanResolver;
import org.agatom.springatom.web.rbuilder.data.model.ReportableColumnResolver;
import org.agatom.springatom.web.rbuilder.data.model.ReportableEntityResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * <p>ReportableBeanResolverImpl class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(value = ReportableBeanResolverImpl.SERVICE_NAME)
public class ReportableBeanResolverImpl
		implements ReportableBeanResolver {

	/** Constant <code>SERVICE_NAME="reportableBeanResolver"</code> */
	protected static final String                        SERVICE_NAME        = "reportableBeanResolver";
	private static final   Logger                        LOGGER              = Logger.getLogger(ReportableBeanResolverImpl.class);
	@Autowired
	private                EntityDescriptors             entityDescriptors   = null;
	@Autowired
	private                ReportableEntityResolver      entityResolver      = null;
	@Autowired
	private                ReportableAssociationResolver associationResolver = null;
	@Autowired
	private                ReportableColumnResolver      columnResolver      = null;

	/** {@inheritDoc} */
	@Override
	public Set<RBuilderEntity> getReportableEntities() {
		final Set<RBuilderEntity> entities = Sets.newTreeSet();
		final Set<SlimEntityDescriptor<?>> descriptors = this.entityDescriptors.getSlimDescriptors();
		for (SlimEntityDescriptor<?> descriptor : descriptors) {
			try {
				final RBuilderEntity entity = this.entityResolver.getReportableEntity(descriptor.getJavaClass());
				if (entity != null) {
					entities.add(entity);
				} else {
					LOGGER.debug(String.format("%s is not %s",
							descriptor.getJavaClass(),
							ClassUtils.getShortName(ReportableEntity.class)
					));
				}
			} catch (Exception exception) {
				LOGGER.warn(String.format("Exception caught when resolving %s for %s",
								ClassUtils.getShortName(RBuilderEntity.class),
								descriptor.getJavaClass())
				);
			}
		}
		LOGGER.trace(String.format("Available %s at count %d", ClassUtils.getShortName(RBuilderEntity.class), entities.size()));
		return entities;
	}

	/** {@inheritDoc} */
	@Override
	public Set<RBuilderAssociation> getEntityAssociations(@NotNull final Set<RBuilderEntity> entities) {
		final Set<RBuilderAssociation> set = Sets.newHashSet();
		for (RBuilderEntity entity : entities) {
			final RBuilderAssociation entityAssociation = this.associationResolver.getEntityAssociation(entity);
			if (entityAssociation != null) {
				set.add(entityAssociation);
			}
		}
		return set;
	}

	/** {@inheritDoc} */
	@Override
	public Set<RBuilderColumn> getReportableColumns(@NotNull final RBuilderEntity entity) {
		return this.columnResolver.getReportableColumns(entity);
	}

	/** {@inheritDoc} */
	@Override
	public RBuilderBean getReportableBean(final Integer identifier) {
		final Set<RBuilderEntity> reportableEntities = this.getReportableEntities();
		final Cloner cloner = new Cloner();
		for (RBuilderEntity reportableEntity : reportableEntities) {
			if (reportableEntity.getId().equals(identifier)) {
				return cloner.shallowClone(reportableEntity);
			}
		}
		return null;
	}
}
