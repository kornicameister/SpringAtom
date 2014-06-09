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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * {@code InfoPageAttribute} class describes the single attribute available in {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPagePanel}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoPageAttribute
		implements Serializable, Comparable<InfoPageAttribute> {
	private static final long          serialVersionUID = -8718015686294718467L;
	private              InfoPageTitle title            = null;
	private              String        path             = null;
	private              String        type             = "value";
	private              String        builderId        = null;
	private              int           position         = 0;

	public String getBuilderId() {
		return this.builderId;
	}

	public InfoPageAttribute setBuilderId(final String builderId) {
		this.builderId = builderId;
		return this;
	}

	public String getType() {
		return this.type;
	}

	public InfoPageAttribute setType(final String type) {
		this.type = type;
		return this;
	}

	public InfoPageTitle getTitle() {
		return this.title;
	}

	public InfoPageAttribute setTitle(final InfoPageTitle title) {
		this.title = title;
		return this;
	}

	/**
	 * <p>Getter for the field <code>path</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * <p>Setter for the field <code>path</code>.</p>
	 *
	 * @param path a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPageAttribute} object.
	 */
	public InfoPageAttribute setPath(final String path) {
		this.path = path;
		return this;
	}

	public int getPosition() {
		return position;
	}

	public InfoPageAttribute setPosition(final int position) {
		this.position = position;
		return this;
	}

	public boolean isGridAttribute() {
		return StringUtils.hasText(this.type) && this.type.equalsIgnoreCase("grid") && StringUtils.hasText(this.builderId);
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(@Nonnull final InfoPageAttribute o) {
		return ComparisonChain.start()
				.compare(this.position, o.position)
				.compare(this.path, o.path)
				.result();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(path, position);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InfoPageAttribute that = (InfoPageAttribute) o;

		return Objects.equal(this.path, that.path) &&
				Objects.equal(this.position, that.position);
	}
}
