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

package org.agatom.springatom.web.component.core.elements;

import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.core.Component;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * <p>Abstract DefaultComponent class.</p>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
abstract public class DefaultComponent
		implements Component {
	private static final long                serialVersionUID  = -2772317139576112812L;
	protected            String              title             = null;
	protected            Map<String, Object> dynamicProperties = null;

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return this.title;
	}

	/** {@inheritDoc} */
	@Override
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * <p>getDynamicProperty.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getDynamicProperty(@Nonnull final String key) {
		return this.getDynamicProperties().get(key);
	}

	/**
	 * <p>Getter for the field <code>dynamicProperties</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<String, Object> getDynamicProperties() {
		if (this.dynamicProperties == null) {
			this.dynamicProperties = Maps.newHashMap();
		}
		return this.dynamicProperties;
	}

	/**
	 * <p>addDynamicProperty.</p>
	 *
	 * @param key   a {@link java.lang.String} object.
	 * @param value a {@link java.lang.Object} object.
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object addDynamicProperty(final String key, final Object value) {
		return this.getDynamicProperties().put(key, value);
	}
}
