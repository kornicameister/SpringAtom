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

package org.agatom.springatom.webmvc.converters.du.component.core;

import com.google.common.collect.ComparisonChain;
import org.agatom.springatom.web.component.core.EmbeddableComponent;

import javax.annotation.Nonnull;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 31.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class IconComponent
		implements EmbeddableComponent {
	private static final long   serialVersionUID = -1035402964649329298L;
	private              int    position         = -1;
	private              String iconClass        = null;
	private              String iconPath         = null;

	public String getIconClass() {
		return this.iconClass;
	}

	public IconComponent setIconClass(final String iconClass) {
		this.iconClass = iconClass;
		return this;
	}

	public String getIconPath() {
		return this.iconPath;
	}

	public IconComponent setIconPath(final String iconPath) {
		this.iconPath = iconPath;
		return this;
	}

	@Override
	public int getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(final int position) {
		this.position = position;
	}

	@Override
	public int compareTo(@Nonnull final EmbeddableComponent o) {
		return ComparisonChain.start()
				.compare(this.position, o.getPosition())
				.result();
	}
}
