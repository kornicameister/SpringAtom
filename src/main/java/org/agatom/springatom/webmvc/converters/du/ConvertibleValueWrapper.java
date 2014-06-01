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

package org.agatom.springatom.webmvc.converters.du;

import com.google.common.base.Objects;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.springframework.data.domain.Persistable;

/**
 * {@code WebData} represent a container that is required to be passed as <b>convert</b>
 * of the conversion from it to the {@link java.lang.String}, which is considered as <b>target</b>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 30.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ConvertibleValueWrapper {
	private Object               value   = null;
	private ComponentDataRequest request = null;
	private String         key;
	private Persistable<?> source;

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public ComponentDataRequest getRequest() {
		return request;
	}

	public void setRequest(final ComponentDataRequest request) {
		this.request = request;
	}

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public Persistable<?> getSource() {
		return source;
	}

	public void setSource(final Persistable<?> source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("key", key)
				.add("value", value)
				.add("convert", source)
				.add("request", request)
				.toString();
	}
}
