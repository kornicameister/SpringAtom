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

import com.google.common.collect.Sets;
import org.springframework.hateoas.Identifiable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

/**
 * {@code InfoPageJsonBean} holds properties read from the file.
 * Used in conversion from JSON file to {@code InfoPageJsonBean} object
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPage
		implements Identifiable<String>, Serializable, Iterable<InfoPagePanel> {
	private static final long               serialVersionUID = -4350752021301908713L;
	private              String             id               = null;
	private              Class<?>           domain           = null;
	private              InfoPageDefaults   defaults         = null;
	private              Set<InfoPagePanel> content          = null;

	/**
	 * Retrieves the panels count of this {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}. Will return <pre>-1</pre> if content is not set
	 *
	 * @return the size of the page
	 */
	public final int getSize() {
		return this.content != null ? this.content.size() : -1;
	}

	/**
	 * <p>Getter for the field <code>content</code>.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<InfoPagePanel> getContent() {
		return this.content;
	}

	/**
	 * <p>Setter for the field <code>content</code>.</p>
	 *
	 * @param content a {@link java.util.Set} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage} object.
	 */
	public InfoPage setContent(final Set<InfoPagePanel> content) {
		this.content = content;
		return this;
	}

	/**
	 * Retrieves domain class for this {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
	 *
	 * @return the domain class
	 */
	public Class<?> getDomain() {
		return this.domain;
	}

	/**
	 * <p>Setter for the field <code>domain</code>.</p>
	 *
	 * @param domain a {@link java.lang.Class} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage} object.
	 */
	public InfoPage setDomain(final Class<?> domain) {
		this.domain = domain;
		return this;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Retrieves unique identifier of this page.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * <p>Setter for the field <code>id</code>.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage} object.
	 */
	public InfoPage setId(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * Retrieves a map of defaults properties applicable for every element being
	 * a part of this {@code InfoPage}
	 *
	 * @return map of default properties
	 */
	public InfoPageDefaults getDefaults() {
		return this.defaults;
	}

	/**
	 * <p>Setter for the field <code>defaults</code>.</p>
	 *
	 * @param defaults a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPageDefaults} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage} object.
	 */
	public InfoPage setDefaults(final InfoPageDefaults defaults) {
		this.defaults = defaults;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<InfoPagePanel> iterator() {
		if (this.content == null) {
			this.content = Sets.newHashSet();
		}
		return this.content.iterator();
	}
}
