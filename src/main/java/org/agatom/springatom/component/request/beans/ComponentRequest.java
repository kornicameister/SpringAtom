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

package org.agatom.springatom.component.request.beans;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ComponentRequest
        implements Serializable {
    protected String   contextKey;
    protected String   builderId;
    protected Class<?> contextClass;

    public String getContextKey() {
        return contextKey;
    }

    public ComponentRequest setContextKey(final String contextKey) {
        this.contextKey = contextKey;
        return this;
    }

    public String getBuilderId() {
        return builderId;
    }

    public ComponentRequest setBuilderId(final String builderId) {
        this.builderId = builderId;
        return this;
    }

    public Class<?> getContextClass() {
        return contextClass;
    }

    public ComponentRequest setContextClass(final Class<?> contextClass) {
        this.contextClass = contextClass;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contextKey, builderId, contextClass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComponentRequest that = (ComponentRequest) o;

        return Objects.equal(this.contextKey, that.contextKey) &&
                Objects.equal(this.builderId, that.builderId) &&
                Objects.equal(this.contextClass, that.contextClass);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(contextKey)
                      .addValue(builderId)
                      .addValue(contextClass)
                      .toString();
    }
}
