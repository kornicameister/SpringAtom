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

package org.agatom.springatom.web.component.core.builders.multi;

import com.google.common.collect.Sets;

import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-22</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class MultiComponentDescriptor {
    private String               alias            = null;
    private Collection<Class<?>> supportedClasses = null;

    public String getAlias() {
        return alias;
    }

    public MultiComponentDescriptor setAlias(final String alias) {
        this.alias = alias;
        return this;
    }

    public Collection<Class<?>> getSupportedClasses() {
        return supportedClasses;
    }

    public MultiComponentDescriptor setSupportedClasses(final Collection<Class<?>> supportedClasses) {
        this.supportedClasses = supportedClasses;
        return this;
    }

    public MultiComponentDescriptor setSupportedClasses(final Class<?>... supportedClasses) {
        this.supportedClasses = Sets.newHashSet(supportedClasses);
        return this;
    }
}
