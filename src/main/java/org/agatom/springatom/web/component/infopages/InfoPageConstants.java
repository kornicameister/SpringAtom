/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
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

package org.agatom.springatom.web.component.infopages;

/**
 * {@code InfoPageConstants} contains set of keys to describe important data used in <b>InfoPage</b> processing
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public final class InfoPageConstants {

	/**
	 * {@code INFOPAGE_DS} is key for property containing {@link org.springframework.hateoas.Link} to be called to get data
	 */
	public static final String INFOPAGE_DS              = "ipDataView";
	/**
	 * {@code INFOPAGE_PAGE} is key for property containing {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
	 */
	public static final String INFOPAGE_PAGE            = "ipInfoPage";
	/**
	 * {@code INFOPAGE_REQUEST} is key for property containing {@link org.agatom.springatom.web.component.infopages.link.InfoPageRequest}
	 */
	public static final String INFOPAGE_REQUEST         = "ipRequest";
	/**
	 * {@code INFOPAGE_INVALID_REQUEST} meaning {@link org.agatom.springatom.web.component.infopages.link.InfoPageRequest} was invalid and hence could not have been processed
	 *
	 * @see org.agatom.springatom.web.component.infopages.link.InfoPageRequest#isValid()
	 */
	public static final String INFOPAGE_INVALID_REQUEST = "ipInvalidRequest";
	/**
	 * {@code INFOPAGE_AVAILABLE}'s presence means that page is available for requested URI
	 */
	public static final String INFOPAGE_AVAILABLE       = "ipAvailable";
	/**
	 * {@code INFOPAGE_NOT_AVAILABLE}'s presence means that page is not available for requested URI
	 */
	public static final String INFOPAGE_NOT_AVAILABLE   = "ipNotAvailable";

	private InfoPageConstants() {
	}
}
