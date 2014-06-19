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

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class IconGuiComponent
		implements GuiComponent {
	private static final long   serialVersionUID = 3802580675439239804L;
	private              String cls              = null;
	private              String path             = null;
	private              String autoEl           = null;

	public String getCls() {
		return cls;
	}

	public IconGuiComponent setCls(final String cls) {
		this.cls = cls;
		return this;
	}

	public String getPath() {
		return path;
	}

	public IconGuiComponent setPath(final String path) {
		this.path = path;
		return this;
	}

	public String getAutoEl() {
		return this.cls != null ? "i" : "img";
	}

	@Override
	public Object getRawValue() {
		return this.cls != null ? this.cls : this.path;
	}
}
