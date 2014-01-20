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

package org.agatom.springatom.web.rbuilder.bean;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Set;

/**
 * {@code RBuilderEntityAssociations} is a plain {@code JavaBean} carrying information about
 * single {@link RBuilderEntity} and all possible associations
 * that can be made with it.
 * <p/>
 * <b>Association</b> means that given {@link org.springframework.data.domain.Persistable} used to create {@link
 * RBuilderEntity} can be linked in either {@link javax.persistence.OneToMany} or {@link
 * javax.persistence.ManyToOne} with another {@link org.springframework.data.domain.Persistable}.
 * <p/>
 * <b>Information about one entity being in association with another</b> is carried by values retrieved from {@link RBuilderBean#getId()}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class RBuilderEntityAssociations
implements Serializable {

    private static final long      serialVersionUID = -8970556120241893260L;
    private              Long      master           = -1l;
    private              Set<Long> children         = Sets.newHashSet();

    public RBuilderEntityAssociations setMaster(final Long master) {
        this.master = master;
        return this;
    }

    public RBuilderEntityAssociations setChildren(final Set<Long> children) {
        this.children = children;
        return this;
    }

    public Long getMaster() {
        return master;
    }

    public Set<Long> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(master)
                      .addValue(children)
                      .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RBuilderEntityAssociations that = (RBuilderEntityAssociations) o;

        return Objects.equal(this.master, that.master) &&
                Objects.equal(this.children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(master, children);
    }
}
