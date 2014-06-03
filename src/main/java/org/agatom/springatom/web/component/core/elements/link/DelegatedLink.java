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

package org.agatom.springatom.web.component.core.elements.link;

import org.agatom.springatom.web.component.core.ComponentValue;
import org.springframework.hateoas.Link;

/**
 * <p>DelegatedLink class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class DelegatedLink
		implements ComponentValue {
	private static final long serialVersionUID = 4641519789859558892L;
	private final Link   link;
	private       String label;

	/**
	 * <p>Constructor for DelegatedLink.</p>
	 *
	 * @param link a {@link org.springframework.hateoas.Link} object.
	 */
	public DelegatedLink(final Link link) {
		this.link = link;
	}

	/**
	 * <p>Getter for the field <code>label</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <p>withLabel.</p>
	 *
	 * @param label a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.core.elements.link.DelegatedLink} object.
	 */
	public DelegatedLink withLabel(final String label) {
		this.label = label;
		return this;
	}

	/**
	 * <p>getHref.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getHref() {
		return link.getHref();
	}

	/**
	 * <p>getRel.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getRel() {
		return link.getRel();
	}

	/**
	 * <p>withRel.</p>
	 *
	 * @param rel a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.core.elements.link.DelegatedLink} object.
	 */
	public DelegatedLink withRel(final String rel) {
		link.withRel(rel);
		return this;
	}

}
