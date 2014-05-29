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

import org.agatom.springatom.web.component.core.EmbeddableComponent;

import javax.annotation.Nonnull;

/**
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class PanelComponent<T extends EmbeddableComponent>
		extends ContentComponent<T>
		implements EmbeddableComponent {
	private static final long   serialVersionUID = 8998087479297251535L;
	protected            int    position         = -1;
	protected            String layout           = null;

	public String getLayout() {
		return this.layout;
	}

	public PanelComponent<T> setLayout(final String layout) {
		this.layout = layout;
		return this;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public void setPosition(final int position) {
		this.position = position;
	}

	@Override
	public int compareTo(final @Nonnull EmbeddableComponent panel) {
		return Integer.compare(this.position, panel.getPosition());
	}
}
