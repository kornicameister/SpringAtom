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

import com.rits.cloning.Cloner;
import org.agatom.springatom.server.model.descriptors.SlimEntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.model.ReportableEntityResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * <p>ReportableEntityResolverImpl class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(value = ReportableEntityResolverImpl.SERVICE_NAME)
public class ReportableEntityResolverImpl
		implements ReportableEntityResolver {

	/** Constant <code>SERVICE_NAME="reportableEntityResolver"</code> */
	protected static final String            SERVICE_NAME      = "reportableEntityResolver";
	@Autowired
	private                SMessageSource    messageSource     = null;
	@Autowired
	private                EntityDescriptors entityDescriptors = null;

	/** {@inheritDoc} */
	@Override
	public RBuilderEntity getReportableEntity(final Class<?> clazz) {
		if (!this.isReportableEntity(clazz)) {
			return null;
		}
		final SlimEntityDescriptor<?> descriptor = this.entityDescriptors.getSlimDescriptor(clazz);
		final Locale locale = LocaleContextHolder.getLocale();
		final RBuilderEntity entity = new RBuilderEntity().setJavaClass(descriptor.getJavaClass()).setName(descriptor.getName());
		final Cloner cloner = new Cloner();
		return cloner.shallowClone(this.messageSource.localize(entity, locale));
	}

	/** {@inheritDoc} */
	@Override
	public Boolean isReportableEntity(final Class<?> clazz) {
		return clazz.isAnnotationPresent(ReportableEntity.class);
	}
}
