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

import com.google.common.base.Objects;
import org.springframework.hateoas.Identifiable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

/**
 * {@code BeanIdentifier} introduces concept of generic and unique identifier object for beans defined
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class BeanIdentifier<T>
        implements Identifiable<String>,
                   Serializable {
    private static final long     serialVersionUID = 9012389461082648740L;
    private static final String   IDENTIFIER       = "BI";
    private              Class<?> beanClass        = null;
    private              T        identifier       = null;

    public static <T extends Serializable> BeanIdentifier<T> newIdentifier(final Class<?> beanClass, final T identifier) {
        return new BeanIdentifier<T>().setBeanClass(beanClass).setIdentifier(identifier);
    }

    public BeanIdentifier<T> setBeanClass(final Class<?> beanClass) {
        this.beanClass = beanClass;
        return this;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public BeanIdentifier<T> setIdentifier(final T identifier) {
        this.identifier = identifier;
        return this;
    }

    public T getIdentifier() {
        return identifier;
    }

    @Override
    public String getId() {
        return String.format("%s:%s:%s", this.getIdentifierInternalId(), ClassUtils.getShortName(this.beanClass), ObjectUtils
                .getIdentityHexString(this.identifier)).toUpperCase();
    }

    protected String getIdentifierInternalId() {
        return IDENTIFIER;
    }

    @Override
    public String toString() {
        return this.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BeanIdentifier that = (BeanIdentifier) o;

        return Objects.equal(this.beanClass, that.beanClass) &&
                Objects.equal(this.identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serialVersionUID, beanClass, identifier);
    }
}
