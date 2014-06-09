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
import com.google.common.collect.Maps;
import org.springframework.binding.collection.MapAdaptable;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * {@code InfoPageLayout} is a simple class describing the order in which
 * attributes of the panel are being layed out
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoPageLayout
		implements Serializable, MapAdaptable<String, String> {
	private static final long   serialVersionUID = -775925469234496099L;
	private              String type             = null;
	private              String align            = null;
	private              String pack             = null;

	public String getType() {
		return type;
	}

	public InfoPageLayout setType(final String type) {
		this.type = type;
		return this;
	}

	public String getAlign() {
		return align;
	}

	public InfoPageLayout setAlign(final String align) {
		this.align = align;
		return this;
	}

	public String getPack() {
		return pack;
	}

	public InfoPageLayout setPack(final String pack) {
		this.pack = pack;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(type, align, pack);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InfoPageLayout that = (InfoPageLayout) o;

		return Objects.equal(this.type, that.type) &&
				Objects.equal(this.align, that.align) &&
				Objects.equal(this.pack, that.pack);
	}

	@Override
	public Map<String, String> asMap() {
		final Map<String, String> map = Maps.newHashMap();
		map.put("type", this.type);
		if (StringUtils.hasText(this.align)) {
			map.put("align", this.align);
		}
		if (StringUtils.hasText(this.pack)) {
			map.put("pack", this.pack);
		}
		return map;
	}
}
