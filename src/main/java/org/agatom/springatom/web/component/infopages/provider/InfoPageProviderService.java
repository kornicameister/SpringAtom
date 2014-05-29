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

package org.agatom.springatom.web.component.infopages.provider;

import org.agatom.springatom.core.exception.SException;
import org.agatom.springatom.web.component.infopages.provider.structure.InfoPage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Persistable;

/**
 * {@code InfoPageProviderService} loads {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage} from
 * the implemented source.
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface InfoPageProviderService {

	/**
	 * Returns {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage} for selected {@link org.springframework.data.domain.Persistable}
	 * domain class
	 *
	 * @param persistableClass domain class to execute search
	 *
	 * @return {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
	 *
	 * @throws org.agatom.springatom.core.exception.SException in case of error
	 */
	@Cacheable(value = "InfoPageProviderService.cache.class")
	<T extends Persistable<?>> InfoPage getInfoPage(final Class<T> persistableClass) throws SException;

	/**
	 * Retrieves path to the file containing {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage} declaration
	 *
	 * @param persistableClass domain class to get the path from
	 * @param <T>              {@link org.springframework.data.domain.Persistable} subclasses
	 *
	 * @return the path
	 */
	@Cacheable(value = "InfoPageProviderService.cache.class.path")
	<T extends Persistable<?>> String getFilePath(Class<T> persistableClass);

	/**
	 * Returns {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage} for selected {@link org.springframework.data.domain.Persistable}
	 * domain object
	 *
	 * @param persistable domain object to execute search
	 *
	 * @return {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
	 *
	 * @throws org.agatom.springatom.core.exception.SException in case of error
	 */
	@Cacheable(value = "InfoPageProviderService.cache.object")
	InfoPage getInfoPage(final Persistable<?> persistable) throws SException;

}
