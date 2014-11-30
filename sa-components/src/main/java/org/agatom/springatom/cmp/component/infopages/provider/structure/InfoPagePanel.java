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

package org.agatom.springatom.cmp.component.infopages.provider.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.springframework.hateoas.Identifiable;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

/**
 * {@code InfoPagePanel} describes the structure of a single panel visible on the {@link org.agatom.springatom.web.component.infopages.provider.structure.InfoPage}
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoPagePanel
        implements Identifiable<String>, Serializable, Iterable<InfoPageAttribute>, Comparable<InfoPagePanel> {
    private static final long                   serialVersionUID = -4966053815380618681L;
    private              String                 id               = null;
    private              InfoPageTitle          title            = null;
    private              InfoPageIcon           icon             = null;
    private              Set<InfoPageAttribute> attributes       = null;
    private              int                    position         = 0;

    @Override
    public String getId() {
        return id;
    }

    public InfoPagePanel setId(final String id) {
        this.id = id;
        return this;
    }

    public InfoPageTitle getTitle() {
        return title;
    }

    public InfoPagePanel setTitle(final InfoPageTitle title) {
        this.title = title;
        return this;
    }

    public InfoPageIcon getIcon() {
        return icon;
    }

    public InfoPagePanel setIcon(final InfoPageIcon icon) {
        this.icon = icon;
        return this;
    }

    public Set<InfoPageAttribute> getAttributes() {
        return attributes;
    }

    public InfoPagePanel setAttributes(final Set<InfoPageAttribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public InfoPagePanel setPosition(final int position) {
        this.position = position;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public int compareTo(@Nonnull final InfoPagePanel o) {
        return ComparisonChain.start()
                .compare(this.position, o.position)
                .compare(this.id, o.id)
                .result();
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<InfoPageAttribute> iterator() {
        return this.attributes.iterator();
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, icon, attributes, position);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoPagePanel that = (InfoPagePanel) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.title, that.title) &&
                Objects.equal(this.icon, that.icon) &&
                Objects.equal(this.attributes, that.attributes) &&
                Objects.equal(this.position, that.position);
    }
}
