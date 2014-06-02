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
	protected final ModelMap         values;
	protected final ComponentRequest request;
	private         Component        component;

	public ComponentDataRequest(final ModelMap modelMap, final ComponentRequest request) {
		this.values = modelMap;
		this.request = request;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(final Component component) {
		this.component = component;
	}

	public ModelMap getValues() {
		return this.values;
	}

	public ComponentRequest getComponentRequest() {
		return request;
	}
}
