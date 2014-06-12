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

package org.agatom.springatom.webmvc.converters.du.component;

import org.agatom.springatom.web.component.core.Component;
import org.agatom.springatom.web.component.core.EmbeddableComponent;
import org.springframework.hateoas.Identifiable;

/**
 * {@code WebDataComponent} interface marks implementing classes as <b>web components</b>
 * {@code WebDataComponent} is therefore an object returned to the client containing information like:
 * <ol>
 * <li>label ({@link org.agatom.springatom.web.component.core.Component#getLabel()}</li>
 * <li>representable/comparable {@link java.lang.Object} value</li>
 * <li>raw value type</li>
 * </ol>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 31.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface WebDataComponent<T>
		extends Component, EmbeddableComponent, WebDataUITyped, Identifiable<String> {
	/**
	 * Returns {@link java.io.Serializable} and {@link java.lang.Comparable} value to be rendered in the client
	 *
	 * @return the value
	 */
	T getData();

	/**
	 * Raw value type used to create this {@link org.agatom.springatom.webmvc.converters.du.component.WebDataComponent}
	 *
	 * @return raw value {@link java.lang.Class}
	 */
	Class<?> getDataType();
}
