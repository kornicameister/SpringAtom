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

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.server.model.descriptors.EntityAssociation;
import org.agatom.springatom.server.model.descriptors.SlimEntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.web.rbuilder.bean.RBuilderAssociation;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.model.ReportableAssociationResolver;
import org.agatom.springatom.web.rbuilder.data.model.ReportableEntityResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * <p>ReportableAssociationResolverImpl class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(value = ReportableAssociationResolverImpl.SERVICE_NAME)
public class ReportableAssociationResolverImpl
		implements ReportableAssociationResolver {

	/** Constant <code>SERVICE_NAME="reportableAssociationResolver"</code> */
	protected static final String                   SERVICE_NAME      = "reportableAssociationResolver";
	private static final   Logger                   LOGGER            = Logger.getLogger(ReportableAssociationResolverImpl.class);
	@Autowired
	private                EntityDescriptors        entityDescriptors = null;
	@Autowired
	private                ReportableEntityResolver entityResolver    = null;


	/** {@inheritDoc} */
	@Override
	@Nullable
	public RBuilderAssociation getEntityAssociation(@NotNull final RBuilderEntity entity) {
		LOGGER.debug(String.format("Resolving association for %s", entity));
		final EntityAssociation association = this.entityDescriptors.getAssociation(entity.getJavaClass());
		if (association == null) {
			return null;
		}
		final Function<SlimEntityDescriptor<?>, Integer> function = new Function<SlimEntityDescriptor<?>, Integer>() {
			@Nullable
			@Override
			public Integer apply(@Nullable final SlimEntityDescriptor<?> input) {
				if (input == null) {
					return null;
				}
				final RBuilderEntity reportableEntity = entityResolver.getReportableEntity(input.getJavaClass());
				return reportableEntity.getId();
			}
		};
		final Set<Integer> children = FluentIterable
				.from(association.getAssociations())
				.transform(function)
				.toSet();
		return new RBuilderAssociation().setMaster(entity.getId())
				.setChildren(children);
	}

}
