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

package org.agatom.springatom.web.component.infopages.elements;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.core.elements.PanelComponent;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * <p>InfoPagePanelComponent class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPagePanelComponent
		extends PanelComponent<InfoPageAttributeComponent> {
	private static final long                serialVersionUID = 4239054882163081910L;
	private              Map<String, String> layoutCfg        = null;
	private              Map<String, String> iconCfg          = null;

	/**
	 * <p>containsAttributeForPath.</p>
	 *
	 * @param path a {@link java.lang.String} object.
	 *
	 * @return a boolean.
	 */
	public boolean containsAttributeForPath(final String path) {
		return this.getAttributeForPath(path) != null;
	}

	/**
	 * <p>getAttributeForPath.</p>
	 *
	 * @param path a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.elements.InfoPageAttributeComponent} object.
	 */
	public InfoPageAttributeComponent getAttributeForPath(final String path) {
		final Optional<InfoPageAttributeComponent> match = FluentIterable.from(this.content).firstMatch(new Predicate<InfoPageAttributeComponent>() {
			@Override
			public boolean apply(@Nullable final InfoPageAttributeComponent input) {
				return input != null && input.getPath().equals(path);
			}
		});
		return match.isPresent() ? match.get() : null;
	}

	public Map<String, String> getIconCfg() {
		return this.iconCfg == null ? Maps.<String, String>newHashMap() : this.iconCfg;
	}

	public InfoPagePanelComponent setIconCfg(final Map<String, String> iconCfg) {
		this.iconCfg = iconCfg;
		return this;
	}

	public Map<String, String> getLayoutCfg() {
		return this.layoutCfg == null ? Maps.<String, String>newHashMap() : this.layoutCfg;
	}

	public InfoPagePanelComponent setLayoutCfg(final Map<String, String> layoutCfg) {
		this.layoutCfg = layoutCfg;
		if (this.layoutCfg.containsKey("type")) {
			this.setLayout(this.layoutCfg.get("type"));
		}
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(position, layout, content, title, dynamicProperties);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InfoPagePanelComponent that = (InfoPagePanelComponent) o;

		return Objects.equal(this.position, that.position) &&
				Objects.equal(this.layout, that.layout) &&
				Objects.equal(this.content, that.content) &&
				Objects.equal(this.title, that.title) &&
				Objects.equal(this.dynamicProperties, that.dynamicProperties);
	}
}
