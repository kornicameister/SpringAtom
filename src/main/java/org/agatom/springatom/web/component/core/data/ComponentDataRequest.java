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

package org.agatom.springatom.web.component.core.data;

import org.agatom.springatom.web.component.core.Component;
import org.agatom.springatom.web.component.core.request.ComponentRequest;
import org.springframework.ui.ModelMap;

/**
 * {@code ComponentDataRequest} wraps {@link org.agatom.springatom.web.component.core.request.ComponentRequest} (marshalled
 * from Ajax], {@link #component} and {@link #values} of the request
 *
 * @author kornicameister
 * @version 0.0.5
 * @since 0.0.1
 */
public class ComponentDataRequest {
	private final ModelMap         values;
	private final ComponentRequest request;
	private       Component        component;
	private RequestedBy requestedBy = null;

	/**
	 * <p>Constructor for ComponentDataRequest.</p>
	 *
	 * @param modelMap a {@link org.springframework.ui.ModelMap} object.
	 * @param request  a {@link org.agatom.springatom.web.component.core.request.ComponentRequest} object.
	 */
	public ComponentDataRequest(final ModelMap modelMap, final ComponentRequest request) {
		this.values = modelMap;
		this.request = request;
	}

	/**
	 * <p>Getter for the field <code>component</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.web.component.core.Component} object.
	 */
	public Component getComponent() {
		return component;
	}

	/**
	 * <p>Setter for the field <code>component</code>.</p>
	 *
	 * @param component a {@link org.agatom.springatom.web.component.core.Component} object.
	 */
	public void setComponent(final Component component) {
		this.component = component;
	}

	/**
	 * <p>Getter for the field <code>values</code>.</p>
	 *
	 * @return a {@link org.springframework.ui.ModelMap} object.
	 */
	public ModelMap getValues() {
		return this.values;
	}

	/**
	 * <p>getComponentRequest.</p>
	 *
	 * @return a {@link org.agatom.springatom.web.component.core.request.ComponentRequest} object.
	 */
	public ComponentRequest getComponentRequest() {
		return request;
	}

	public RequestedBy getRequestedBy() {
		return requestedBy;
	}

	public ComponentDataRequest setRequestedBy(final RequestedBy requestedBy) {
		this.requestedBy = requestedBy;
		return this;
	}
}
