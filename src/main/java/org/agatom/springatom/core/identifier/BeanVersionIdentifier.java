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

package org.agatom.springatom.core.identifier;

import java.io.Serializable;

/**
 * {@code BeanIdentifier} introduces concept of generic and unique identifier object for beans defined
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class BeanVersionIdentifier<T>
        extends BeanIdentifier<T> {
    private static final long   serialVersionUID = 9012389461082648740L;
    private static final String IDENTIFIER       = "BVI";

    private Long version;

    public static <T extends Serializable> BeanIdentifier<T> newIdentifier(final Class<?> beanClass, final T identifier, final Long version) {
        return new BeanVersionIdentifier<T>().setVersion(version).setBeanClass(beanClass).setIdentifier(identifier);
    }

    public BeanVersionIdentifier<T> setVersion(final Long version) {
        this.version = version;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String getId() {
        return String.format("%s:%s", super.getId(), this.version).toUpperCase();
    }

    @Override
    protected String getIdentifierInternalId() {
        return IDENTIFIER;
    }

}
