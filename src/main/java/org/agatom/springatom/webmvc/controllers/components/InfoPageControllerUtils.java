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

package org.agatom.springatom.webmvc.controllers.components;

import org.agatom.springatom.core.exception.SException;
import org.agatom.springatom.web.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.web.component.infopages.link.InfoPageRequest;
import org.agatom.springatom.web.component.infopages.request.InfoPageComponentRequest;
import org.springframework.data.domain.Persistable;

import javax.servlet.http.HttpServletRequest;

/**
 * {@code InfoPageControllerUtils} combines method to utilize operations such as:
 * <ol>
 * <li>getting InfoPage links</li>
 * <li>getting InfoPage requests</li>
 * <li>creating component request</li>
 * </ol>
 * for {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent}
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 20.07.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface InfoPageControllerUtils {

	/**
	 * Retrieves {@link org.agatom.springatom.web.component.infopages.link.InfoPageRequest} out of {@link javax.servlet.http.HttpServletRequest}
	 *
	 * @param request active request
	 *
	 * @return {@link org.agatom.springatom.web.component.infopages.link.InfoPageRequest} or throws exception
	 *
	 * @throws Exception if any
	 */
	InfoPageRequest getInfoPageRequest(final HttpServletRequest request) throws Exception;

	/**
	 * Wraps operation of building {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent} from
	 * {@link org.springframework.data.domain.Persistable} class
	 *
	 * @param objectClass the persistable class
	 *
	 * @return page component
	 */
	InfoPageComponent getInfoPageComponent(final Class<Persistable<?>> objectClass) throws SException;

	/**
	 * Wraps operation of building {@link org.agatom.springatom.web.component.infopages.elements.InfoPageComponent} from
	 * {@link org.agatom.springatom.web.component.infopages.link.InfoPageRequest}
	 *
	 * @param infoPageRequest infopage request
	 *
	 * @return page component
	 */
	InfoPageComponent getInfoPageComponent(final InfoPageRequest infoPageRequest) throws SException;

	InfoPageComponentRequest getInfoPageComponentRequest(final InfoPageComponent infoPageComponent, final InfoPageRequest pageRequest);
}
