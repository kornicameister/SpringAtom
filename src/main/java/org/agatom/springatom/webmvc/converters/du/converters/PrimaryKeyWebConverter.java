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

package org.agatom.springatom.webmvc.converters.du.converters;

import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.component.IconGuiComponent;
import org.agatom.springatom.webmvc.converters.du.component.TextGuiComponent;
import org.springframework.data.domain.Persistable;

/**
 * {@code PrimaryKeyWebConverter}
 * <small>Class is a part of <b>SpringAtom</b> and was created at 31.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WebConverter(key = "id")
public class PrimaryKeyWebConverter
		extends AbstractWebConverter {

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	protected TextGuiComponent doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
		final TextGuiComponent component = new TextGuiComponent();
		component.setValue(this.getPrimaryKeyValue(value, persistable));
		component.setRawValue(value);
		component.setIcon(new IconGuiComponent().setCls("fa fa-key fa-fw"));
		return component;
	}

	/**
	 * Returns primary key value. If {@code value} is null, method tries to extract the value from {@link org.springframework.data.domain.Persistable#getId()}
	 *
	 * @param value       current "id" value
	 * @param persistable persistable having the value
	 *
	 * @return primary key value
	 */
	protected String getPrimaryKeyValue(final Object value, final Persistable<?> persistable) {
		return String.valueOf(value != null ? value : persistable.getId());
	}

}
