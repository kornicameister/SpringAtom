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
import org.agatom.springatom.web.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.web.component.infopages.provider.structure.InfoPage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Persistable;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nonnull;

/**
 * {@code InfoPageComponentBuilderService} takes advantage from read {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
 * to create full configuration of a page in {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 25.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Validated
public interface InfoPageComponentBuilderService {

	/**
	 * Builds {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent}
	 * out of {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
	 *
	 * @param page source page
	 *
	 * @return complete {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent}
	 */
	@Cacheable(value = "InfoPageComponentBuilderService.cache.page")
	InfoPageComponent buildInfoPage(@Nonnull final InfoPage page);

	/**
	 * Builds {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent}
	 * out of {@code path} corresponding to the {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
	 *
	 * @param path source path
	 *
	 * @return complete {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent}
	 *
	 * @throws SException in case of a failure
	 * @see #buildInfoPage(org.agatom.springatom.web.component.infopages.provider.structure.InfoPage)
	 * @see org.agatom.springatom.web.component.infopages.mapping.InfoPageMappingService#getMappedClass(String)
	 */
	@Cacheable(value = "InfoPageComponentBuilderService.cache.path")
	InfoPageComponent buildInfoPage(@Nonnull final String path) throws SException;

	/**
	 * Builds {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent}
	 * out of {@code domainClass} corresponding to the {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent}
	 *
	 * @param domainClass class associated to the {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent}
	 * @param <T>         persistable indicator
	 *
	 * @return complete {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent}
	 *
	 * @throws SException in case of a failure
	 * @see org.agatom.springatom.web.component.infopages.provider.InfoPageProviderService#getInfoPage(Class)
	 */
	@Cacheable(value = "InfoPageComponentBuilderService.cache.domainClass")
	<T extends Persistable<?>> InfoPageComponent buildInfoPage(@Nonnull final Class<T> domainClass) throws SException;
}
