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

package org.agatom.springatom.web.infopages.provider.structure;

import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * {@code InfoPageDefaults} contains set of the properties that propagated further
 * to every element of the {@link org.agatom.springatom.web.infopages.provider.structure.InfoPage}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageDefaults
		extends HashMap<String, Object> {
	public static final  String COLLAPSIBLE      = "collapsible";
	public static final  String LAYOUT           = "layout";
	private static final long   serialVersionUID = -4311432020224431919L;

	public boolean isCollapsible() {
		final Object value = this.get(COLLAPSIBLE);
		if (value == null) {
			return false;
		}
		if (ClassUtils.isAssignableValue(String.class, value)) {
			if (StringUtils.hasText((String) value)) {
				return Boolean.valueOf((String) value);
			}
		} else if (ClassUtils.isAssignableValue(Boolean.class, value)) {
			return (Boolean) value;
		}
		return false;
	}

	public InfoPageLayout getLayout() {
		final String value = (String) this.get(LAYOUT);
		if (StringUtils.hasText(value)) {
			return InfoPageLayout.valueOf(value);
		}
		return null;
	}

}
