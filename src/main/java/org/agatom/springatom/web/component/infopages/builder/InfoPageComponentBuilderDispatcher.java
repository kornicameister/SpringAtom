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

package org.agatom.springatom.web.component.infopages.builder;

import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.web.component.core.repository.ComponentBuilderRepository;
import org.agatom.springatom.web.component.infopages.link.InfoPageLinkHelper;
import org.agatom.springatom.web.locale.SMessageSource;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InfoPageComponentBuilderDispatcher {
	@Autowired
	protected EntityDescriptors          descriptors                = null;
	@Autowired
	protected InfoPageLinkHelper         linkHelper                 = null;
	@Autowired
	private   ComponentBuilderRepository componentBuilderRepository = null;
	@Autowired
	private   SMessageSource             messageSource              = null;
	@Autowired
	private   ListableBeanFactory        beanFactory                = null;
	private   Repositories               repos                      = null;

	@PostConstruct
	private void initRepositories() {
		this.repos = new Repositories(this.beanFactory);
	}

	@SuppressWarnings("unchecked")
	public <T extends Persistable<?>> InfoPageComponentBuilder<T> getInfoPageComponentBuilder(final Class<T> domainClass) {
		final InfoPageComponentBuilder<T> builder = new InfoPageComponentBuilder<T>(domainClass) {
			@Override
			public String toString() {
				return domainClass.toString();
			}
		};

		builder.descriptors = descriptors;
		builder.messageSource = messageSource;
		builder.componentBuilderRepository = componentBuilderRepository;
		builder.linkHelper = linkHelper;
		builder.repository = (org.agatom.springatom.server.repository.SBasicRepository<T, Long>) this.repos.getRepositoryFor(domainClass);

		return builder;
	}

}
