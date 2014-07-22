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

package org.agatom.springatom.web.component.infopages.link;

import com.google.common.base.Function;
import org.agatom.springatom.web.component.infopages.InfoPageNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Persistable;
import org.springframework.hateoas.Link;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 20.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.3
 * @since 0.0.1
 */
public interface InfoPageLinkHelper {
	/**
	 * {@code CACHE_KEY} is key of the cache of this {@link org.agatom.springatom.web.component.infopages.link.InfoPageLinkHelper} service
	 */
	String CACHE_KEY = "infoPageLinkHelper";

	/**
	 * Retrieves {@link org.springframework.hateoas.Link} to {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
	 * from passed {@link org.springframework.data.domain.Persistable}
	 *
	 * @param persistable persistable object
	 *
	 * @return a link to open {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
	 *
	 * @throws org.agatom.springatom.web.component.infopages.InfoPageNotFoundException if page does not exists for associated {@link java.lang.Class}
	 */
	<T extends Serializable> Link getInfoPageLink(final Persistable<T> persistable) throws InfoPageNotFoundException;

	/**
	 * Retrieves {@link org.springframework.hateoas.Link} to {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
	 * by using passed {@code path} and {@code id}
	 *
	 * @param path <b>rel</b> under which {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage} is accessible
	 * @param id   <b>{@link org.springframework.data.domain.Persistable#getId()} value
	 *
	 * @return a link to open {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
	 */
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
	 *
	 * @throws org.agatom.springatom.web.component.infopages.InfoPageNotFoundException if any.
	 */
	@Cacheable(value = CACHE_KEY, key = "#request.getRequestURI()", condition = "#request != null")
	InfoPageRequest toInfoPageRequest(final HttpServletRequest request) throws InfoPageNotFoundException;

	/**
	 * Retrieves map of parameters from {@link javax.servlet.http.HttpServletRequest} request.
	 * 2nd param can be used to adjust the path is for instance request for infopage was called from dynamic view.
	 *
	 * @param request to extract params
	 * @param getPath function to extract adjusted path
	 *
	 * @return map of params
	 *
	 * @throws org.agatom.springatom.web.component.infopages.InfoPageNotFoundException if any.
	 */
	@Cacheable(value = CACHE_KEY, key = "#request.getRequestURI()", condition = "#request != null")
	InfoPageRequest toInfoPageRequest(final HttpServletRequest request, final Function<HttpServletRequest, String> getPath) throws InfoPageNotFoundException;
}
