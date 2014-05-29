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

package org.agatom.springatom.web.component.core.request;

import com.google.common.base.Objects;
import org.agatom.springatom.web.component.core.Component;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;
import java.util.Locale;
import java.util.Map;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
final public class ComponentWrappedRequest
		extends ComponentDataRequest {
	private AbstractComponentRequest componentRequest = null;
	private WebRequest               webRequest       = null;
	/**
	 * Component for this request, {@link org.agatom.springatom.web.component.core.Component}
	 */
	private Component                component        = null;

	public ComponentWrappedRequest(final ModelMap modelMap, final WebRequest webRequest) {
		super(modelMap, webRequest);
	}

	public AbstractComponentRequest getComponentRequest() {
		return componentRequest;
	}

	public ComponentWrappedRequest setComponentRequest(final AbstractComponentRequest componentRequest) {
		this.componentRequest = componentRequest;
		return this;
	}

	public Principal getUser() {
		return this.webRequest.getUserPrincipal();
	}

	public Locale getLocale() {
		return this.webRequest.getLocale();
	}

	public Map<String, String[]> getParameterMap() {
		return webRequest.getParameterMap();
	}

	public Component getComponent() {
		return this.component;
	}

	public ComponentWrappedRequest setComponent(final Component component) {
		this.component = component;
		return this;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("componentRequest", componentRequest)
				.add("webRequest", webRequest)
				.toString();
	}
}
