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

package org.agatom.springatom.web.component.infopages.provider.structure;

import java.util.HashMap;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 09.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageIcon
		extends HashMap<String, String> {
	public static final  String ICON_CLS         = "iconCls";
	public static final  String ICON_PATH        = "iconPath";
	public static final  String ICON_GLYPH       = "glyph";
	private static final long   serialVersionUID = 1816235449787709837L;

	public String getIconCls() {
		return this.get(ICON_CLS);
	}

	public String getIconPath() {
		return this.get(ICON_PATH);
	}

	public String getIconGlyph() {
		return this.get(ICON_GLYPH);
	}

}
