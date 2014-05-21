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

package org.agatom.springatom.web.infopages.link;

import org.agatom.springatom.web.infopages.InfoPageNotFoundException;
import org.agatom.springatom.web.infopages.SInfoPage;
import org.springframework.data.domain.Persistable;
import org.springframework.hateoas.Link;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 20.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface InfoPageLinkHelper {

	<T extends Serializable> Link getInfoPageLink(final SInfoPage page, final Persistable<T> persistable);

	<T extends Serializable> Link getInfoPageLink(final String path, final T id);

	/**
	 * Determines if given path points to infoPage
	 *
	 * @param path to check
	 *
	 * @return true if path is InfoPage path
	 */
	boolean isInfoPageLink(final String path);

	/**
	 * Retrieves map of parameters from {@link javax.servlet.http.HttpServletRequest} request
	 *
	 * @param request to extract params
	 *
	 * @return map of params
	 */
	InfoPageRequest toInfoPageRequest(final HttpServletRequest request) throws InfoPageNotFoundException;
}
