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

import com.google.common.base.Objects;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 01.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class LinkGuiComponent
		extends TextGuiComponent {
	private static final long   serialVersionUID = -1536553355416884697L;
	private              String linkLabel        = null;
	private              String target           = "_self";
	private              String download         = null;

	/**
	 * <p>Getter for the field <code>linkLabel</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLinkLabel() {
		return linkLabel;
	}

	/**
	 * <p>Setter for the field <code>linkLabel</code>.</p>
	 *
	 * @param linkLabel a {@link java.lang.String} object.
	 *
	 * @return a {@link LinkGuiComponent} object.
	 */
	public LinkGuiComponent setLinkLabel(final String linkLabel) {
		this.linkLabel = linkLabel;
		return this;
	}

	public String getTarget() {
		return target;
	}

	public LinkGuiComponent setTarget(final String target) {
		this.target = target;
		return this;
	}

	public String getDownload() {
		return download;
	}

	public LinkGuiComponent setDownload(final String download) {
		this.download = download;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(linkLabel, super.hashCode());
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LinkGuiComponent that = (LinkGuiComponent) o;

		return Objects.equal(this.linkLabel, that.linkLabel) && super.equals(o);
	}
}
