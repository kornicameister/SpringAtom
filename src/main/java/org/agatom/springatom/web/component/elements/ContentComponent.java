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

package org.agatom.springatom.web.component.elements;

import com.google.common.collect.Sets;
import org.agatom.springatom.web.component.EmbeddableComponent;

import java.util.Iterator;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class ContentComponent<T extends EmbeddableComponent>
		extends DefaultComponent
		implements Iterable<T> {
	private static final long   serialVersionUID = -8072389645215572550L;
	protected            Set<T> content          = null;

	public boolean removeContent(final Object o) {
		return this.getContent().remove(o);
	}

	public Set<T> getContent() {
		if (this.content == null) {
			this.content = Sets.newTreeSet();
		}
		return this.content;
	}

	@Override
	public Iterator<T> iterator() {
		return this.getContent().iterator();
	}

	public void setContent(final Set<T> content) {
		this.getContent().clear();
		for (final T t : content) {
			this.addContent(t);
		}
	}

	public boolean addContent(final T t) {
		t.setPosition(this.getContent().size());
		return this.getContent().add(t);
	}


}
