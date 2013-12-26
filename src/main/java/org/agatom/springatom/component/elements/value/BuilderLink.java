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

package org.agatom.springatom.component.elements.value;

import com.google.common.base.Objects;
import org.agatom.springatom.component.ComponentValue;
import org.springframework.hateoas.Link;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class BuilderLink
implements ComponentValue {
    private final Serializable contextKey;
    private final Class<?>     contextClass;
    private final String       builderId;
    private final Link         link;

    public BuilderLink(final String builderId,
                       final Class<?> contextClass,
                       final Serializable contextKey,
                       final Link link) {
        this.builderId = builderId;
        this.contextClass = contextClass;
        this.contextKey = contextKey;
        this.link = link;
    }

    public Serializable getContextKey() {
        return contextKey;
    }

    public Class<?> getContextClass() {
        return contextClass;
    }

    public String getContextClassName() {
        return contextClass.getName();
    }

    public String getBuilderId() {
        return builderId;
    }

    public Link getLink() {
        return this.link;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(builderId, contextClass, contextKey);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuilderLink that = (BuilderLink) o;

        return Objects.equal(this.builderId, that.builderId) &&
                Objects.equal(this.contextClass, that.contextClass) &&
                Objects.equal(this.contextKey, that.contextKey);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(builderId)
                      .addValue(contextClass)
                      .addValue(contextKey)
                      .toString();
    }
}
