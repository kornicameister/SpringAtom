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

package org.agatom.springatom.component.elements;

import com.google.common.collect.Sets;
import org.agatom.springatom.component.DefaultComponent;
import org.agatom.springatom.component.EmbeddableComponent;

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
    protected Set<T> content = Sets.newTreeSet();

    public Set<T> getContent() {
        return content;
    }

    public void setContent(final Set<T> content) {
        this.content = content;
    }

    public boolean addContent(final T t) {
        t.setPosition(this.content.size());
        return content.add(t);
    }

    public boolean removeContent(final Object o) {
        return content.remove(o);
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }
}
