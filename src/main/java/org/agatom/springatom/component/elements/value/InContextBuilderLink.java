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
import org.joor.Reflect;
import org.springframework.hateoas.Link;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InContextBuilderLink
        implements ComponentValue {
    private final Serializable contextKey;
    private final Class<?>     contextClass;
    private final String       builderId;
    private final Link         link;

    public InContextBuilderLink(final String builderId,
                                final Class<?> contextClass,
                                final Serializable contextKey,
                                final Link link) {
        this.builderId = builderId;
        this.contextClass = contextClass;
        this.contextKey = contextKey;
        this.link = link;
    }

    public static InContextBuilderLink fromRequest(final HttpServletRequest request) {
        if (request != null) {
            return new InContextBuilderLink(
                    request.getParameter(Parameters.BUILDER_ID),
                    (Class<?>) Reflect.on(request.getParameter(Parameters.CONTEXT_CLASS)).get(),
                    request.getParameter(Parameters.CONTEXT_KEY),
                    null
            );
        }
        return null;
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

        InContextBuilderLink that = (InContextBuilderLink) o;

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

    public static class Parameters {
        public static final String CONTEXT_KEY   = "contextKey";
        public static final String CONTEXT_CLASS = "contextClass";
        public static final String BUILDER_ID    = "builderId";
    }
}
