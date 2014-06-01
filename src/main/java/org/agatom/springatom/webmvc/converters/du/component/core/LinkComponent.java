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

import com.google.common.base.Objects;

/**
 * {@code LinkComponent} is customized {@link org.agatom.springatom.webmvc.converters.du.component.core.TextComponent}.
 * For this component consider:
 * <ol>
 * <li>{@link TextComponent#getValue()} as the link href</li>
 * </ol>
 * Has additional {@link #getLinkLabel()} that should return the label to be put as the content of {@code <a></a>}
 * HTML node.
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 01.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class LinkComponent
		extends TextComponent {
	private static final long   serialVersionUID = -1536553355416884697L;
	private              String linkLabel        = null;

	public String getLinkLabel() {
		return linkLabel;
	}

	public LinkComponent setLinkLabel(final String linkLabel) {
		this.linkLabel = linkLabel;
		return this;
	}

	@Override
	public String getUiType() {
		return "link";
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(linkLabel, super.hashCode());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LinkComponent that = (LinkComponent) o;

		return Objects.equal(this.linkLabel, that.linkLabel) && super.equals(o);
	}
}
